package com.avaya.jtapi.tsapi.tsapiInterface.oio;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.impl.core.JtapiProperties;
import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
import com.avaya.jtapi.tsapi.util.JtapiUtils;

public class GenericBrowser {
	private static Logger log = Logger.getLogger(GenericBrowser.class);
	private String name;
	private static int SSL_HANDSHAKE_TIMEOUT = 5;
	public static final String SYSTEM_PROPERTIES_PREFIX = "com.avaya.jtapi.tsapi.";
	public static final String TELEPHONY_SERVERS_SYSTEM_PROPERTY = "com.avaya.jtapi.tsapi.servers";
	private boolean startUp = true;
	private String tsapiProLocation;
	private Properties sysSnapshot;

	GenericBrowser() {
		this.name = "GENERIC";
	}

	GenericBrowser(String _name) {
		this.name = _name;
	}

	Socket trySocket(InetSocketAddress addr, SocketFactory sf)
			throws UnknownHostException, IOException {
		Socket socket = sf.createSocket();

		TsapiHandshakeCompletedListener tsapiHandshakeCompletedListener = null;

		if ((socket instanceof SSLSocket)) {
			SSLSocket sslSocket = (SSLSocket) socket;

			setSslSocketProperties(sslSocket);

			if (TsapiSSLContext.getVerifyServerCertificate()) {
				try {
					tsapiHandshakeCompletedListener = new TsapiHandshakeCompletedListener();

					sslSocket
							.addHandshakeCompletedListener(tsapiHandshakeCompletedListener);
				} catch (Exception e) {
					throw new TsapiPlatformException(
							4,
							0,
							"Couldn't register HandshakeCompletedListener for secure connection.  SSLSocket.addHandshakeCompletedListener() failed; "
									+ e);
				}

			}

		}

		int timeout = Tsapi.getMaxTcpSocketWait();

		if ((timeout <= 0) || (timeout > 120)) {
			timeout = 20;
		}

		try {
			socket.connect(addr, timeout * 1000);
		} catch (SocketTimeoutException e) {
			throw new TsapiPlatformException(4, 0,
					"Couldn't connect to server " + addr
							+ ". Socket.connect() timed out after " + timeout
							+ " seconds.");
		}

		if (((socket instanceof SSLSocket))
				&& (tsapiHandshakeCompletedListener != null)) {
			trySslHandshake((SSLSocket) socket, tsapiHandshakeCompletedListener);
		}

		return socket;
	}

	private void setSslSocketProperties(SSLSocket sslSocket) {
		try {
			sslSocket.setUseClientMode(true);
		} catch (Exception e) {
			throw new TsapiPlatformException(
					4,
					0,
					"Couldn't initialize socket for secure client connection.  SSLSocket.setUseClientMode() failed. "
							+ e);
		}

		try {
			String[] protocols = { "TLSv1" };

			sslSocket.setEnabledProtocols(protocols);
		} catch (Exception e) {
			throw new TsapiPlatformException(
					4,
					0,
					"Couldn't initialize socket for secure client connection.  SSLSocket.setEnabledProtocols() failed. "
							+ e);
		}

		try {
			String[] cipherSuites = { "SSL_RSA_WITH_3DES_EDE_CBC_SHA",
					"TLS_RSA_WITH_AES_128_CBC_SHA" };

			sslSocket.setEnabledCipherSuites(cipherSuites);
		} catch (Exception e) {
			throw new TsapiPlatformException(
					4,
					0,
					"Couldn't initialize socket for secure client connection.  SSLSocket.setEnabledCipherSuites() failed. "
							+ e);
		}
	}

	private void trySslHandshake(SSLSocket sslSocket,
			TsapiHandshakeCompletedListener listener) {
		synchronized (listener) {
			try {
				sslSocket.startHandshake();
				listener.wait(SSL_HANDSHAKE_TIMEOUT * 1000);
			} catch (IOException ioe) {
				closeSocket(sslSocket);

				throw new TsapiPlatformException(
						4,
						0,
						"The TLS connection was closed because a network-level error occured during the SSL handshake; "
								+ ioe);
			} catch (InterruptedException e) {
				closeSocket(sslSocket);

				throw new TsapiPlatformException(
						4,
						0,
						"The TLS connection was closed because the SSL handshake did not complete within "
								+ SSL_HANDSHAKE_TIMEOUT + " seconds.");
			}

		}

		try {
			TLSServerCertificateValidator validator = new TLSServerCertificateValidator(
					sslSocket, listener.getSslSession(),
					TsapiSSLContext.getTrustManagers());

			validator.validateAll();
		} catch (Exception e) {
			closeSocket(sslSocket);

			throw new TsapiPlatformException(4, 0,
					"The TLS connection was closed because server certificate validation failed. "
							+ e);
		}
	}

	private void closeSocket(Socket socket) {
		try {
			socket.close();
		} catch (IOException ioe) {
			log.error("Couldn't close socket; " + ioe.getMessage(), ioe);
		}
	}

	private void closeStream(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (Exception e) {
				log.error("Error closing file:TSAPI.PRO");
			}
		}
	}

	InputStream findProperties() {
		InputStream is = null;
		try {
			is = findFileSystemProperties();
			if ((!this.startUp)
					&& (!checkIfTsapiProFileChanged(this.tsapiProLocation))
					&& (!systemPropertiesChanged())) {
				closeStream(is);
				return null;
			}
			Object fsProps = new JtapiProperties();
			if (is != null) {
				((Properties) fsProps).load(is);
				is.close();
			}

			Properties sysProps = findSystemProperties();
			this.sysSnapshot = sysProps;
			Properties merged = merge((Properties) fsProps, sysProps);
			return wrapInStream(merged);
		} catch (IOException e) {
			throw new TsapiPlatformException(4, 0, "can't find properties");
		} finally {
			closeStream(is);
		}
	}

	private boolean systemPropertiesChanged() {
		for (Map.Entry<Object, Object> entry : this.sysSnapshot.entrySet()) {
			String lhs = System.getProperty((String) entry.getKey());
			String rhs = (String) entry.getValue();
			if (((lhs != null) && (rhs == null))
					|| ((lhs == null) && (rhs != null))) {
				return true;
			}
			if ((lhs != null) && (!lhs.equals(rhs))) {
				return true;
			}
		}
		return false;
	}

	private InputStream wrapInStream(Properties props) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		props.store(out, "");
		out.flush();
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		out.close();
		return in;
	}

	private Properties merge(Properties fsProps, Properties sysProps) {
		Properties defaults = new Properties();
		defaults.putAll(fsProps);
		for (Map.Entry<Object, Object> entry : sysProps.entrySet()) {
			if (!defaults.containsKey((String) entry.getKey())) {
				defaults.put(entry.getKey(), entry.getValue());
			}
		}
		return defaults;
	}

	private Properties findSystemProperties() {
		Properties jtapiProps = new Properties();

		for (Iterator<?> i$ = System.getProperties().keySet().iterator(); i$
				.hasNext();) {
			Object o = i$.next();
			String key = (String) o;

			if ((key.toLowerCase().startsWith("com.avaya.jtapi.tsapi."))
					&& (!key.equalsIgnoreCase("com.avaya.jtapi.tsapi.servers"))) {
				jtapiProps.put(
						key.substring("com.avaya.jtapi.tsapi.".length()),
						System.getProperty(key));
			}
		}
		return jtapiProps;
	}

	private InputStream findFileSystemProperties() {
		InputStream in = null;
		for (int i = 0; i < 3; i++) {
			String resource;
			switch (i) {
			case 0:
			default:
				resource = "tsapi.pro";
				break;
			case 1:
				resource = "Tsapi.pro";
				break;
			case 2:
				resource = "TSAPI.PRO";
			}

			if (in != null)
				break;
			in = searchClasspath(resource);

			if (in != null)
				break;
			in = searchResources(resource);

			if (in != null)
				break;
			in = searchCodeBaseURL(resource);

			if (in != null)
				break;
			in = searchUserDir(resource);
		}

		return in;
	}

	private InputStream searchUserDir(String resource) {
		InputStream in = null;
		File f = new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + resource);
		if ((f.exists()) && (f.canRead())) {
			try {
				in = new BufferedInputStream(new FileInputStream(f));
			} catch (FileNotFoundException e) {
				log.error(e.getMessage(), e);
			}
		}
		if (in != null) {
			this.tsapiProLocation = f.getAbsolutePath();
			if (this.startUp) {
				info("Found '" + resource + "' at location '"
						+ this.tsapiProLocation + "'");
			}
			return in;
		}
		return in;
	}

	private InputStream searchCodeBaseURL(String resource) {
		InputStream in = null;
		URL myURL = getCodeBaseURL();
		if (myURL != null) {
			try {
				in = new URL(myURL, resource).openStream();
				if (in != null) {
					this.tsapiProLocation = myURL.toString();
					if (this.startUp) {
						info("Found '" + resource + "' at codeBaseURL '"
								+ this.tsapiProLocation + "'");
					}
					return in;
				}
			} catch (Exception e) {
			}
		}
		return in;
	}

	private static void info(String msg) {
		if (JtapiUtils.isLog4jConfigured())
			log.info(msg);
		else
			System.out.println(msg);
	}

	private InputStream searchResources(String resource) {
		InputStream in = null;
		try {
			in = getClass().getResourceAsStream("/" + resource);
			if (in != null) {
				this.tsapiProLocation = "<unknown>";
				if (getClass().getResource("/" + resource) != null) {
					this.tsapiProLocation = getClass().getResource(
							"/" + resource).getFile();
				}
				if (this.startUp) {
					info("Found '" + resource + "' as a resource at location '"
							+ this.tsapiProLocation + "'");
				}
				return in;
			}
		} catch (NoSuchMethodError e) {
			File propfile = new File(resource);
			try {
				if (propfile.canRead()) {
					in = new FileInputStream(propfile);
				}
			} catch (Exception e1) {
			}
			if (in != null) {
				this.tsapiProLocation = propfile.getAbsolutePath();
				info("Found '" + resource + "' as a file at location '"
						+ this.tsapiProLocation + "'");
				return in;
			}
		}
		return in;
	}

	private boolean checkIfTsapiProFileChanged(String location) {
		if (location != null) {
			File file = new File(location);
			long timeWhenLastChecked = System.currentTimeMillis()
					- Tsapi.getRefreshIntervalForTsapiPro() * 1000;
			if (file.lastModified() >= timeWhenLastChecked) {
				return true;
			}
		}
		return false;
	}

	private InputStream searchClasspath(String resource) {
		InputStream in = null;
		try {
			in = ClassLoader.getSystemResourceAsStream("/" + resource);
			if (in != null) {
				this.tsapiProLocation = ClassLoader.getSystemResource(
						"/" + resource).getFile();
				if (this.startUp) {
					info("Found '/" + resource
							+ "' as a system resource at location '"
							+ this.tsapiProLocation + "'");
				}
				return in;
			}

		} catch (NoSuchMethodError e) {
			File propfile = null;
			try {
				StringTokenizer classpath = new StringTokenizer(
						System.getProperty("java.class.path"),
						System.getProperty("path.separator"));

				while (classpath.hasMoreTokens()) {
					propfile = new File(classpath.nextToken(), resource);
					if (propfile.canRead()) {
						in = new FileInputStream(propfile);
					}
				}
			} catch (Exception e1) {
			}
			if (in != null) {
				this.tsapiProLocation = "<unknown>";
				if (propfile != null) {
					this.tsapiProLocation = propfile.getAbsolutePath();
				}
				info("Found '" + resource
						+ "' by manual classpath search at location '"
						+ this.tsapiProLocation + "'");
				return in;
			}
		}
		return in;
	}

	static URL getCodeBaseURL() {
		SecurityManager sm = System.getSecurityManager();
		if (sm == null) {
			return null;
		}

		Object context = sm.getSecurityContext();
		if ((context instanceof URL)) {
			return (URL) context;
		}

		return null;
	}

	public String getCodeBaseServer() {
		URL myURL = getCodeBaseURL();

		if (myURL != null) {
			info("get codebase URL succeeded");
			return myURL.getHost();
		}

		try {
			myURL = getClass().getResource(getClass().getName());
		} catch (NoSuchMethodError e) {
		}
		if (myURL != null) {
			info("get URL from class succeeded");
			return myURL.getHost();
		}

		throw new TsapiPlatformException(4, 0, "can't find code base");
	}

	public void setStartUp(boolean startUp) {
		this.startUp = startUp;
	}

	public String getName() {
		return this.name;
	}
}