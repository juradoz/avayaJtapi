package com.avaya.jtapi.tsapi.tsapiInterface.oio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

final class NsBr extends GenericBrowser {
	static boolean grantedUniversalConnect = false;
	static boolean askedForUniversalConnect = false;

	NsBr() {
		super("NS 4.X");
	}

	@Override
	InputStream findProperties() {
		try {
			return super.findProperties();
		}
		// catch (AppletSecurityException e)
		catch (Exception e) {
			// PrivilegeManager.enablePrivilege("UniversalPropertyRead");
			// PrivilegeManager.enablePrivilege("UniversalFileRead");
		}
		return super.findProperties();
	}

	@Override
	Socket trySocket(InetSocketAddress addr, SocketFactory sf)
			throws UnknownHostException, IOException {
		try {
			// if (grantedUniversalConnect)
			// PrivilegeManager.enablePrivilege("UniversalConnect");
			return super.trySocket(addr, sf);
		}
		// catch (AppletSecurityException e)
		catch (Exception e) {
			// if (!askedForUniversalConnect)
			{
				// askedForUniversalConnect = true;
				// PrivilegeManager.enablePrivilege("UniversalConnect");
				// grantedUniversalConnect = true;
				return super.trySocket(addr, sf);
			}
			// throw e;
		}
	}
}

