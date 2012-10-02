package com.avaya.jtapi.tsapi.tsapiInterface.oio;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiChannel;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiChannelReadHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.streams.IntelByteArrayInputStream;
import com.avaya.jtapi.tsapi.tsapiInterface.streams.IntelSocketInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;
import org.apache.log4j.Logger;

public class TsapiChannelOio implements TsapiChannel {
	private static Logger log = Logger.getLogger(TsapiChannelOio.class);
	private OioChannelThread thread;
	private TsapiChannelReadHandler handler;
	private Socket sock;
	private IntelSocketInputStream in;
	private OutputStream out;
	private static GenericBrowser browser;

	public TsapiChannelOio(InetSocketAddress addr, SocketFactory sf)
			throws IOException {
		log.info("browser: " + browser.getName());

		this.sock = trySocket(addr, sf);
		this.in = new IntelSocketInputStream(this.sock.getInputStream());
		this.out = this.sock.getOutputStream();
		this.thread = new OioChannelThread("GetEventThread");
		this.thread.start();
	}

	public void write(ByteArrayOutputStream msg) throws IOException {
		msg.writeTo(this.out);
	}

	public void setReadHandler(TsapiChannelReadHandler _handler) {
		this.handler = _handler;
	}

	public void close() {
		try {
			this.thread.stopRunning();
			this.out.flush();
			this.sock.close();
		} catch (IOException e) {
			log.error("Exception when closing TsapiChannel: " + e);
		}
	}

	public InetSocketAddress getInetSocketAddress() {
		return (InetSocketAddress) this.sock.getLocalSocketAddress();
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
		}
		throw new TsapiPlatformException(4, 0, "connection to address <" + addr
				+ "> timed out");
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
			} catch (Exception e1) {
				browser = new GenericBrowser();
			}
		}
	}

	public static GenericBrowser getBrowser() {
		return browser;
	}

	static {
		determineBrowser();
	}

	private class OioChannelThread extends Thread {
		private boolean keepRunning = true;

		public OioChannelThread(String name) {
			super();
		}

		public void run() {
			while (this.keepRunning)
				try {
					int msgLen = TsapiChannelOio.this.in.readInt();
					byte[] msgBuf = new byte[msgLen];
					TsapiChannelOio.this.in.readFully(msgBuf);
					IntelByteArrayInputStream msg = new IntelByteArrayInputStream(
							msgBuf);
					TsapiChannelOio.this.handler.handleRead(msg);
				} catch (IOException e) {
					TsapiChannelOio.this.handler.handleException(e);
				}
		}

		public void stopRunning() {
			this.keepRunning = false;
		}
	}
}