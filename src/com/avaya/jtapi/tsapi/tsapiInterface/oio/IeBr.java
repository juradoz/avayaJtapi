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
	Socket trySocket(InetSocketAddress addr, SocketFactory sf)
			throws UnknownHostException, IOException {
		// PolicyEngine.assertPermission(PermissionID.NETIO);

		return super.trySocket(addr, sf);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.oio.IeBr JD-Core Version: 0.5.4
 */