package com.avaya.jtapi.tsapi.tsapiInterface.oio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketPermission;
import java.net.UnknownHostException;
import java.security.AccessController;

import javax.net.SocketFactory;

final class SunBr extends GenericBrowser {
	SunBr() {
		super("Sun JVM");
	}

	@Override
	InputStream findProperties() {
		return super.findProperties();
	}

	@Override
	Socket trySocket(InetSocketAddress addr, SocketFactory sf)
			throws UnknownHostException, IOException {
		System.out.println("in SunBr  checkPermission\n");
		AccessController.checkPermission(new SocketPermission(addr.getAddress()
				.getHostAddress()
				+ ":" + addr.getPort(), "connect"));

		System.out.println("in SunBr  trySocket\n");
		return super.trySocket(addr, sf);
	}
}

