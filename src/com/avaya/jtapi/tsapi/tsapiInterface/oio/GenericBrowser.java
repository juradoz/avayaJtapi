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
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
import com.avaya.jtapi.tsapi.util.JtapiUtils;

public class GenericBrowser {
	private static Logger log = Logger.getLogger(GenericBrowser.class);

	static URL getCodeBaseURL() {
		final SecurityManager sm = System.getSecurityManager();
		if (sm == null)
			return null;

		final Object context = sm.getSecurityContext();
		if (context instanceof URL)
			return (URL) context;

		return null;
	}

	private static void info(final String msg) {
		if (JtapiUtils.isLog4jConfigured())
			GenericBrowser.log.info(msg);
		else
			System.out.println(msg);
	}

	private final String name;
	private static int SSL_HANDSHAKE_TIMEOUT = 5;
	public static final String SYSTEM_PROPERTIES_PREFIX = "com.avaya.jtapi.tsapi.";
	public static final String TELEPHONY_SERVERS_SYSTEM_PROPERTY = "com.avaya.jtapi.tsapi.servers";
	private boolean startUp = true;

	private String tsapiProLocation;

	private Properties sysSnapshot;

	GenericBrowser() {
		name = "GENERIC";
	}

	GenericBrowser(final String _name) {
		name = _name;
	}

	private boolean checkIfTsapiProFileChanged(final String location) {
		if (location != null) {
			final File file = new File(location);
			final long timeWhenLastChecked = System.currentTimeMillis()
					- Tsapi.getRefreshIntervalForTsapiPro() * 1000;
			if (file.lastModified() >= timeWhenLastChecked)
				return true;
		}
		return false;
	}

	private void closeSocket(final Socket socket) {
		try {
			socket.close();
		} catch (final IOException ioe) {
			GenericBrowser.log.error("Couldn't close socket; "
					+ ioe.getMessage(), ioe);
		}
	}

	private InputStream findFileSystemProperties() {
		InputStream in = null;
		for (int i = 0; i < 3; ++i) {
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

	InputStream findProperties() {
		try {
			final InputStream is = findFileSystemProperties();
			if (!startUp && !checkIfTsapiProFileChanged(tsapiProLocation)
					&& !systemPropertiesChanged())
				return null;
			final Properties fsProps = new Properties();
			if (is != null) {
				fsProps.load(is);
				is.close();
			}

			final Properties sysProps = findSystemProperties();
			sysSnapshot = sysProps;
			final Properties merged = merge(fsProps, sysProps);
			return wrapInStream(merged);
		} catch (final IOException e) {
			throw new TsapiPlatformException(4, 0, "can't find properties");
		}
	}

	private Properties findSystemProperties() {
		final Properties jtapiProps = new Properties();

		for (final Object o : System.getProperties().keySet()) {
			final String key = (String) o;

			if (key.toLowerCase().startsWith("com.avaya.jtapi.tsapi.")
					&& !key.equalsIgnoreCase("com.avaya.jtapi.tsapi.servers"))
				jtapiProps.put(
						key.substring("com.avaya.jtapi.tsapi.".length()),
						System.getProperty(key));
		}

		return jtapiProps;
	}

	public String getCodeBaseServer() {
		URL myURL = GenericBrowser.getCodeBaseURL();

		if (myURL != null) {
			GenericBrowser.info("get codebase URL succeeded");
			return myURL.getHost();
		}

		try {
			myURL = super.getClass().getResource(super.getClass().getName());
		} catch (final NoSuchMethodError e) {
		}
		if (myURL != null) {
			GenericBrowser.info("get URL from class succeeded");
			return myURL.getHost();
		}

		throw new TsapiPlatformException(4, 0, "can't find code base");
	}

	public String getName() {
		return name;
	}

	private Properties merge(final Properties fsProps, final Properties sysProps) {
		final Properties defaults = new Properties();
		defaults.putAll(fsProps);
		for (final Map.Entry<Object, Object> entry : sysProps.entrySet())
			if (!defaults.containsKey(entry.getKey()))
				defaults.put(entry.getKey(), entry.getValue());
		return defaults;
	}

	private InputStream searchClasspath(final String resource) {
		InputStream in = null;
		try {
			in = ClassLoader.getSystemResourceAsStream("/" + resource);
			if (in != null) {
				tsapiProLocation = ClassLoader
						.getSystemResource("/" + resource).getFile();
				if (startUp)
					GenericBrowser.info("Found '/" + resource
							+ "' as a system resource at location '"
							+ tsapiProLocation + "'");
				return in;
			}

		} catch (final NoSuchMethodError e) {
			File propfile = null;
			try {
				final StringTokenizer classpath = new StringTokenizer(System
						.getProperty("java.class.path"), System
						.getProperty("path.separator"));
				do {
					if (!classpath.hasMoreTokens())
						// break label176;
						return in;
					propfile = new File(classpath.nextToken(), resource);
				} while (!propfile.canRead());

				in = new FileInputStream(propfile);
			} catch (final Exception e1) {
			}

			if (in != null) {
				tsapiProLocation = "<unknown>";
				if (propfile != null)
					tsapiProLocation = propfile.getAbsolutePath();
				GenericBrowser.info("Found '" + resource
						+ "' by manual classpath search at location '"
						+ tsapiProLocation + "'");
				return in;
			}
		}
		return in;
	}

	private InputStream searchCodeBaseURL(final String resource) {
		InputStream in = null;
		final URL myURL = GenericBrowser.getCodeBaseURL();
		if (myURL != null)
			try {
				in = new URL(myURL, resource).openStream();
				if (in != null) {
					tsapiProLocation = myURL.toString();
					if (startUp)
						GenericBrowser
								.info("Found '" + resource
										+ "' at codeBaseURL '"
										+ tsapiProLocation + "'");
					return in;
				}
			} catch (final Exception e) {
			}
		return in;
	}

	private InputStream searchResources(final String resource) {
		InputStream in = null;
		try {
			in = super.getClass().getResourceAsStream("/" + resource);
			if (in != null) {
				tsapiProLocation = "<unknown>";
				if (super.getClass().getResource("/" + resource) != null)
					tsapiProLocation = super.getClass().getResource(
							"/" + resource).getFile();
				if (startUp)
					GenericBrowser.info("Found '" + resource
							+ "' as a resource at location '"
							+ tsapiProLocation + "'");
				return in;
			}
		} catch (final NoSuchMethodError e) {
			final File propfile = new File(resource);
			try {
				if (propfile.canRead())
					in = new FileInputStream(propfile);
			} catch (final Exception e1) {
			}
			if (in != null) {
				tsapiProLocation = propfile.getAbsolutePath();
				GenericBrowser.info("Found '" + resource
						+ "' as a file at location '" + tsapiProLocation + "'");
				return in;
			}
		}
		return in;
	}

	private InputStream searchUserDir(final String resource) {
		InputStream in = null;
		final File f = new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + resource);
		if (f.exists() && f.canRead())
			try {
				in = new BufferedInputStream(new FileInputStream(f));
			} catch (final FileNotFoundException e) {
				GenericBrowser.log.error(e.getMessage(), e);
			}
		if (in != null) {
			tsapiProLocation = f.getAbsolutePath();
			if (startUp)
				GenericBrowser.info("Found '" + resource + "' at location '"
						+ tsapiProLocation + "'");
			return in;
		}
		return in;
	}

	private void setSslSocketProperties(final SSLSocket sslSocket) {
		try {
			sslSocket.setUseClientMode(true);
		} catch (final Exception e) {
			throw new TsapiPlatformException(
					4,
					0,
					"Couldn't initialize socket for secure client connection.  SSLSocket.setUseClientMode() failed. "
							+ e);
		}

		try {
			final String[] protocols = { "TLSv1" };

			sslSocket.setEnabledProtocols(protocols);
		} catch (final Exception e) {
			throw new TsapiPlatformException(
					4,
					0,
					"Couldn't initialize socket for secure client connection.  SSLSocket.setEnabledProtocols() failed. "
							+ e);
		}

		try {
			final String[] cipherSuites = { "SSL_RSA_WITH_3DES_EDE_CBC_SHA",
					"TLS_RSA_WITH_AES_128_CBC_SHA" };

			sslSocket.setEnabledCipherSuites(cipherSuites);
		} catch (final Exception e) {
			throw new TsapiPlatformException(
					4,
					0,
					"Couldn't initialize socket for secure client connection.  SSLSocket.setEnabledCipherSuites() failed. "
							+ e);
		}
	}

	public void setStartUp(final boolean startUp) {
		this.startUp = startUp;
	}

	private boolean systemPropertiesChanged() {
		for (final Map.Entry<Object, Object> entry : sysSnapshot.entrySet()) {
			final String lhs = System.getProperty((String) entry.getKey());
			final String rhs = (String) entry.getValue();
			if (lhs != null && rhs == null || lhs == null && rhs != null)
				return true;
			if (lhs != null && !lhs.equals(rhs))
				return true;
		}
		return false;
	}

	Socket trySocket(final InetSocketAddress addr, final SocketFactory sf)
			throws UnknownHostException, IOException {
		final Socket socket = sf.createSocket();

		TsapiHandshakeCompletedListener tsapiHandshakeCompletedListener = null;

		if (socket instanceof SSLSocket) {
			final SSLSocket sslSocket = (SSLSocket) socket;

			setSslSocketProperties(sslSocket);

			if (TsapiSSLContext.getVerifyServerCertificate())
				try {
					tsapiHandshakeCompletedListener = new TsapiHandshakeCompletedListener();

					sslSocket
							.addHandshakeCompletedListener(tsapiHandshakeCompletedListener);
				} catch (final Exception e) {
					throw new TsapiPlatformException(
							4,
							0,
							"Couldn't register HandshakeCompletedListener for secure connection.  SSLSocket.addHandshakeCompletedListener() failed; "
									+ e);
				}

		}

		int timeout = Tsapi.getMaxTcpSocketWait();

		if (timeout <= 0 || timeout > 120)
			timeout = 20;

		try {
			socket.connect(addr, timeout * 1000);
		} catch (final SocketTimeoutException e) {
			throw new TsapiPlatformException(4, 0,
					"Couldn't connect to server " + addr
							+ ". Socket.connect() timed out after " + timeout
							+ " seconds.");
		}

		if (socket instanceof SSLSocket
				&& tsapiHandshakeCompletedListener != null)
			trySslHandshake((SSLSocket) socket, tsapiHandshakeCompletedListener);

		return socket;
	}

	private void trySslHandshake(final SSLSocket sslSocket,
			final TsapiHandshakeCompletedListener listener) {
		synchronized (this) {
			try {
				sslSocket.startHandshake();
				listener.wait(GenericBrowser.SSL_HANDSHAKE_TIMEOUT * 1000);
			} catch (final IOException ioe) {
				closeSocket(sslSocket);

				throw new TsapiPlatformException(
						4,
						0,
						"The TLS connection was closed because a network-level error occured during the SSL handshake; "
								+ ioe);
			} catch (final InterruptedException e) {
				closeSocket(sslSocket);

				throw new TsapiPlatformException(
						4,
						0,
						"The TLS connection was closed because the SSL handshake did not complete within "
								+ GenericBrowser.SSL_HANDSHAKE_TIMEOUT
								+ " seconds.");
			}
		}
		try {
			final TLSServerCertificateValidator validator = new TLSServerCertificateValidator(
					sslSocket, listener.getSslSession(), TsapiSSLContext
							.getTrustManagers());

			validator.validateAll();
		} catch (final Exception e) {
			closeSocket(sslSocket);

			throw new TsapiPlatformException(4, 0,
					"The TLS connection was closed because server certificate validation failed. "
							+ e);
		}
	}

	private InputStream wrapInStream(final Properties props) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		props.store(out, "");
		out.flush();
		final ByteArrayInputStream in = new ByteArrayInputStream(out
				.toByteArray());
		out.close();
		return in;
	}
}
