package com.avaya.jtapi.tsapi.tsapiInterface.oio;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.StringTokenizer;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.TsapiPlatformException;

final class TsapiSSLContext {
	private static Logger log = Logger.getLogger(TsapiSSLContext.class);

	private static TsapiSSLContext instance = null;

	private static KeyStore serverTrustStore = null;
	private static SSLContext sslContext = null;
	private static TrustManagerFactory trustManagerFactory = null;
	private static boolean verifyServerCertificate = false;

	private static File findTrustStore(final String tsFileName)
			throws TsapiPlatformException {
		try {
			File tsFile = new File(tsFileName);
			if (tsFile.isAbsolute()) {
				if (tsFile.canRead()) {
					TsapiSSLContext.log.info("Found trust store \""
							+ tsFileName + "\"");

					return tsFile.getCanonicalFile();
				}

			} else {
				final StringTokenizer classpath = new StringTokenizer(System
						.getProperty("java.class.path"), System
						.getProperty("path.separator"));
				do {
					if (!classpath.hasMoreTokens())
						// break label138;
						break;
					tsFile = new File(classpath.nextToken(), tsFileName);
				} while (!tsFile.canRead());

				TsapiSSLContext.log.info("Classpath search for trust store \""
						+ tsFileName + "\" succeeded");

				// label138:
				return tsFile.getCanonicalFile();
			}

		} catch (final Exception e) {
		}

		throw new TsapiPlatformException(4, 0, "Couldn't find trust store \""
				+ tsFileName + "\", or read access not permitted.");
	}

	public static synchronized TsapiSSLContext getInstance(
			final String trustStoreLocation, final String trustStorePassword,
			final boolean verifyServerCertificate) {
		if (TsapiSSLContext.instance == null)
			try {
				TsapiSSLContext.instance = new TsapiSSLContext(
						trustStoreLocation, trustStorePassword);

				TsapiSSLContext.verifyServerCertificate = verifyServerCertificate;
			} catch (final TsapiPlatformException e) {
				throw e;
			} catch (final Exception e) {
				throw new TsapiPlatformException(4, 0,
						"Could not initialize SSL context. " + e.getMessage());
			}

		return TsapiSSLContext.instance;
	}

	public static SSLSocketFactory getSocketFactory() {
		if (TsapiSSLContext.sslContext == null)
			return null;
		return TsapiSSLContext.sslContext.getSocketFactory();
	}

	public static TrustManager[] getTrustManagers() {
		if (TsapiSSLContext.trustManagerFactory == null)
			return null;
		return TsapiSSLContext.trustManagerFactory.getTrustManagers();
	}

	public static boolean getVerifyServerCertificate() {
		return TsapiSSLContext.verifyServerCertificate;
	}

	private static void setupServerTrustStore(final String trustStoreFile,
			final String trustStorePassword) throws TsapiPlatformException {
		File jksFile = null;
		try {
			jksFile = TsapiSSLContext.findTrustStore(trustStoreFile);

			TsapiSSLContext.log.info("Using trust store file \"" + jksFile
					+ "\"");
		} catch (final Exception e) {
			throw new TsapiPlatformException(4, 0,
					"Couldn't find trust store file \"" + trustStoreFile
							+ "\".");
		}

		try {
			final FileInputStream jksInputStream = new FileInputStream(jksFile);

			TsapiSSLContext.serverTrustStore = KeyStore.getInstance("JKS");
			TsapiSSLContext.serverTrustStore.load(jksInputStream,
					trustStorePassword.toCharArray());
		} catch (final Exception e) {
			TsapiSSLContext.serverTrustStore = null;
			throw new TsapiPlatformException(4, 0,
					"Couldn't set up server trust store \"" + trustStoreFile
							+ "\". " + e.getMessage());
		}
	}

	private static void validateTrustStoreLocation(
			final String trustStoreLocation) {
		if (trustStoreLocation == null)
			return;
		try {
			final File file = new File(trustStoreLocation);

			if (!file.isAbsolute())
				throw new TsapiPlatformException(4, 0,
						"Property setting \"trustStoreLocation="
								+ trustStoreLocation
								+ "\" does not specify an absolute path.");

			if (!file.isFile())
				throw new TsapiPlatformException(4, 0,
						"Property setting \"trustStoreLocation="
								+ trustStoreLocation
								+ "\" does not specify a normal file.");

			if (!file.canRead())
				throw new TsapiPlatformException(4, 0,
						"Property setting \"trustStoreLocation="
								+ trustStoreLocation
								+ "\" does not specify a readable file.");

		} catch (final NullPointerException e) {
			throw new TsapiPlatformException(4, 0,
					"Property setting \"trustStoreLocation="
							+ trustStoreLocation + "\" is not valid. "
							+ e.getMessage());
		}
	}

	private TsapiSSLContext(final String trustStoreLocation,
			final String trustStorePassword) {
		final SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextInt();

		if (trustStoreLocation != null) {
			TsapiSSLContext.validateTrustStoreLocation(trustStoreLocation);
			TsapiSSLContext.setupServerTrustStore(trustStoreLocation,
					trustStorePassword);
		} else
			TsapiSSLContext.setupServerTrustStore("avayaprca.jks", "password");

		try {
			TsapiSSLContext.trustManagerFactory = TrustManagerFactory
					.getInstance("SunX509");
		} catch (final Exception e) {
			throw new TsapiPlatformException(4, 0,
					"TrustManagerFactory.getInstance() failed. "
							+ e.getMessage());
		}

		try {
			TsapiSSLContext.trustManagerFactory
					.init(TsapiSSLContext.serverTrustStore);
		} catch (final Exception e) {
			throw new TsapiPlatformException(4, 0,
					"TrustManagerFactory.init() failed. " + e.getMessage());
		}

		try {
			TsapiSSLContext.sslContext = SSLContext.getInstance("TLS");
		} catch (final Exception e) {
			throw new TsapiPlatformException(4, 0,
					"SSLContext.getInstance() failed. " + e.getMessage());
		}

		try {
			TsapiSSLContext.sslContext.init(null,
					TsapiSSLContext.trustManagerFactory.getTrustManagers(),
					secureRandom);
		} catch (final Exception e) {
			throw new TsapiPlatformException(4, 0, "SSLContext.init() failed. "
					+ e.getMessage());
		}
	}
}
