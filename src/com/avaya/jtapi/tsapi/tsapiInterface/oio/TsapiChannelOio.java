package com.avaya.jtapi.tsapi.tsapiInterface.oio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiChannel;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiChannelReadHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.streams.IntelByteArrayInputStream;
import com.avaya.jtapi.tsapi.tsapiInterface.streams.IntelSocketInputStream;

public class TsapiChannelOio implements TsapiChannel {
	private class OioChannelThread extends Thread {
		private boolean keepRunning = true;

		public OioChannelThread(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (keepRunning) {
				try {
					int msgLen = in.readInt();
					byte[] msgBuf = new byte[msgLen];
					in.readFully(msgBuf);
					IntelByteArrayInputStream msg = new IntelByteArrayInputStream(
							msgBuf);
					handler.handleRead(msg);
				} catch (IOException e) {
					handler.handleException(e);
				}
			}
		}

		public void stopRunning() {
			keepRunning = false;
		}
	}

	private static Logger log = Logger.getLogger(TsapiChannelOio.class);
	private OioChannelThread thread;
	private TsapiChannelReadHandler handler;
	private Socket sock;
	private IntelSocketInputStream in;
	private OutputStream out;

	private static GenericBrowser browser;

	static {
		determineBrowser();
	}

	private static void determineBrowser() {
		try {
			Class.forName("netscape.security.PrivilegeManager");

			Class.forName("netscape.security.AppletSecurityException");
			Class.forName("netscape.applet.AppletClassLoader");
			browser = (GenericBrowser) Class.forName(
					"com.avaya.jtapi.tsapi.NsBr").newInstance();
			return;
		} catch (Exception e) {
			try {
				Class.forName("com.ms.security.PermissionID");
				browser = (GenericBrowser) Class.forName(
						"com.avaya.jtapi.tsapi.IeBr").newInstance();
				return;
			} catch (Exception e2) {
				browser = new GenericBrowser();
			}
		}
	}

	public static GenericBrowser getBrowser() {
		return browser;
	}

	public static InputStream getProperties() {
		return browser.findProperties();
	}

	private static Socket trySocket(InetSocketAddress addr, SocketFactory sf) {
		try {
			return browser.trySocket(addr, sf);
		} catch (UnknownHostException e) {
			throw new TsapiPlatformException(4, 0, "address <" + addr
					+ "> not found");
		} catch (IOException e) {
			throw new TsapiPlatformException(4, 0, "connection to address <"
					+ addr + "> timed out");
		}
	}

	public TsapiChannelOio(InetSocketAddress addr, SocketFactory sf)
			throws IOException {
		log.info("browser: " + browser.getName());

		sock = trySocket(addr, sf);
		in = new IntelSocketInputStream(sock.getInputStream());
		out = sock.getOutputStream();
		thread = new OioChannelThread("GetEventThread");
		thread.start();
	}

	public void close() {
		try {
			thread.stopRunning();
			out.flush();
			sock.close();
		} catch (IOException e) {
			log.error("Exception when closing TsapiChannel: " + e);
		}
	}

	public InetSocketAddress getInetSocketAddress() {
		return (InetSocketAddress) sock.getLocalSocketAddress();
	}

	public void setReadHandler(TsapiChannelReadHandler _handler) {
		handler = _handler;
	}

	public void write(ByteArrayOutputStream msg) throws IOException {
		msg.writeTo(out);
	}
}

