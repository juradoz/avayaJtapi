package com.avaya.jtapi.tsapi.tsapiInterface.oio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

final class IeBr extends GenericBrowser {
	IeBr() {
		super("IE 4.X");
	}

	@Override
	InputStream findProperties() {
		// PolicyEngine.assertPermission(PermissionID.FILEIO);

		return super.findProperties();
	}

	@Override
	Socket trySocket(final InetSocketAddress addr, final SocketFactory sf)
			throws UnknownHostException, IOException {
		// PolicyEngine.assertPermission(PermissionID.NETIO);

		return super.trySocket(addr, sf);
	}
}
