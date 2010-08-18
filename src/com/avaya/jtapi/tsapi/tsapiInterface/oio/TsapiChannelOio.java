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

		public OioChannelThread(final String name) {
			super(name);
		}

		@Override
		public void run() {
			while (keepRunning)
				try {
					final int msgLen = in.readInt();
					final byte[] msgBuf = new byte[msgLen];
					in.readFully(msgBuf);
					final IntelByteArrayInputStream msg = new IntelByteArrayInputStream(
							msgBuf);
					handler.handleRead(msg);
				} catch (final IOException e) {
					handler.handleException(e);
				}
		}

		public void stopRunning() {
			keepRunning = false;
		}
	}

	private static Logger log = Logger.getLogger(TsapiChannelOio.class);
	private final OioChannelThread thread;
	private TsapiChannelReadHandler handler;
	private final Socket sock;
	private final IntelSocketInputStream in;
	private final OutputStream out;

	private static GenericBrowser browser;

	static {
		TsapiChannelOio.determineBrowser();
	}

	private static void determineBrowser() {
		try {
			Class.forName("netscape.security.PrivilegeManager");

			Class.forName("netscape.security.AppletSecurityException");
			Class.forName("netscape.applet.AppletClassLoader");
			TsapiChannelOio.browser = (GenericBrowser) Class.forName(
					"com.avaya.jtapi.tsapi.NsBr").newInstance();
			return;
		} catch (final Exception e) {
			try {
				Class.forName("com.ms.security.PermissionID");
				TsapiChannelOio.browser = (GenericBrowser) Class.forName(
						"com.avaya.jtapi.tsapi.IeBr").newInstance();
				return;
			} catch (final Exception e2) {
				TsapiChannelOio.browser = new GenericBrowser();
			}
		}
	}

	public static GenericBrowser getBrowser() {
		return TsapiChannelOio.browser;
	}

	public static InputStream getProperties() {
		return TsapiChannelOio.browser.findProperties();
	}

	private static Socket trySocket(final InetSocketAddress addr,
			final SocketFactory sf) {
		try {
			return TsapiChannelOio.browser.trySocket(addr, sf);
		} catch (final UnknownHostException e) {
			throw new TsapiPlatformException(4, 0, "address <" + addr
					+ "> not found");
		} catch (final IOException e) {
			throw new TsapiPlatformException(4, 0, "connection to address <"
					+ addr + "> timed out");
		}
	}

	public TsapiChannelOio(final InetSocketAddress addr, final SocketFactory sf)
			throws IOException {
		TsapiChannelOio.log.info("browser: "
				+ TsapiChannelOio.browser.getName());

		sock = TsapiChannelOio.trySocket(addr, sf);
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
		} catch (final IOException e) {
			TsapiChannelOio.log.error("Exception when closing TsapiChannel: "
					+ e);
		}
	}

	public InetSocketAddress getInetSocketAddress() {
		return (InetSocketAddress) sock.getLocalSocketAddress();
	}

	public void setReadHandler(final TsapiChannelReadHandler _handler) {
		handler = _handler;
	}

	public void write(final ByteArrayOutputStream msg) throws IOException {
		msg.writeTo(out);
	}
}
