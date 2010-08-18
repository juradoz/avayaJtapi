package com.avaya.jtapi.tsapi.tsapiInterface.oio;

import java.net.InetSocketAddress;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

import org.apache.log4j.Logger;

class TLSServerCertificateValidator {
	private static Logger log = Logger
			.getLogger(TLSServerCertificateValidator.class);
	private final SSLSocket socket;
	private X509Certificate[] certificates = null;
	private final X509Certificate certificate;
	private X509TrustManager trustManager = null;

	TLSServerCertificateValidator(final SSLSocket socket,
			final SSLSession session, final TrustManager[] trustManagers)
			throws CertificateException {
		Certificate[] peerCertificates = null;

		if (socket == null)
			throw new NullPointerException("Socket is null");

		this.socket = socket;
		try {
			peerCertificates = session.getPeerCertificates();
		} catch (final SSLPeerUnverifiedException e) {
			throw new CertificateException(e);
		}

		if (peerCertificates.length == 0)
			throw new CertificateException(
					"Cannot authenticate server; the server's certificate chain is empty.");

		if (!(peerCertificates[0] instanceof X509Certificate))
			throw new CertificateException(
					"Cannot authenticate server; the server certificate is not an X509 certificate.");

		certificates = (X509Certificate[]) peerCertificates;
		certificate = certificates[0];

		if (trustManagers != null)
			for (int i = 0; i < trustManagers.length; ++i) {
				final TrustManager tm = trustManagers[i];
				if (!(tm instanceof X509TrustManager))
					continue;
				trustManager = (X509TrustManager) tm;
				break;
			}

		if (trustManager != null)
			return;
		throw new CertificateException(
				"Cannot authenticate server; no X509 trust managers found.");
	}

	private void compareToResolvedName(final String commonName)
			throws CertificateException {
		final InetSocketAddress address = (InetSocketAddress) socket
				.getRemoteSocketAddress();

		TLSServerCertificateValidator.log
				.info("Verifying that the certificate's common name \""
						+ commonName + " matches the peer's hostname.");

		if (address.isUnresolved())
			throw new CertificateException(
					"Unable to validate peer certificate: " + address
							+ " could not be resolved to a host name.");

		if (address.getHostName().equalsIgnoreCase(commonName))
			return;
		throw new CertificateException(
				"The Common Name (CN) in the server's certificate ("
						+ commonName
						+ ") does not match the server's resolved host name ("
						+ address.getHostName() + ").");
	}

	private String getNameFromX509(final X509Certificate certificate) {
		String commonName = "";

		final X500Principal principal = certificate.getSubjectX500Principal();
		final String name = principal.getName("RFC1779");

		TLSServerCertificateValidator.log.info("X500Principal name = \"" + name
				+ "\"");

		final StringTokenizer tokenizer = new StringTokenizer(name);

		while (tokenizer.hasMoreTokens()) {
			final String token = tokenizer.nextToken();

			TLSServerCertificateValidator.log.info("token = \"" + token + "\"");

			if (token.startsWith("CN=")) {
				if (token.endsWith(",")) {
					commonName = token.substring(3, token.length() - 1);
					break;
				}

				commonName = token.substring(3, token.length());

				break;
			}
		}

		return commonName;
	}

	private String getNameFromX509v3(final Collection<List<?>> altNames) {
		String commonName = "";

		final Iterator<List<?>> iterator = altNames.iterator();
		while (iterator.hasNext()) {
			final List<?> indexAndNamePair = iterator.next();
			final Integer index = (Integer) indexAndNamePair.get(0);
			if (index.intValue() == 2) {
				commonName = (String) indexAndNamePair.get(1);
				break;
			}
		}

		return commonName;
	}

	public void validateAll() throws CertificateException {
		validateDateWindow();
		validateCommonName();
		validateServerCertificateChain();
	}

	public void validateCommonName() throws CertificateException {
		final Collection<List<?>> altNames = certificate
				.getSubjectAlternativeNames();
		String commonName = "";

		if (altNames == null) {
			TLSServerCertificateValidator.log
					.info("The peer's certificate is not X509v3.  Parsing the CN out of the certificate.");

			commonName = getNameFromX509(certificate);
		} else {
			TLSServerCertificateValidator.log
					.info("The peer's certificate is X509v3.  Examining subjectAltNames for dNSName.");

			commonName = getNameFromX509v3(altNames);

			if (commonName.equals("")) {
				TLSServerCertificateValidator.log
						.info("Didn't find dNSName in subjectAltNames.  Falling back to parsing the CN out of the certificate.");

				commonName = getNameFromX509(certificate);
			}
		}

		compareToResolvedName(commonName);
	}

	public void validateDateWindow() throws CertificateException {
		certificate.checkValidity();
	}

	public void validateServerCertificateChain() throws CertificateException {
		trustManager.checkServerTrusted(certificates, "RSA");
	}
}
