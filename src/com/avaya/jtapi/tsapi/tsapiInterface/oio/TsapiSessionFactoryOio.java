package com.avaya.jtapi.tsapi.tsapiInterface.oio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Enumeration;
import java.util.Properties;

import javax.net.SocketFactory;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.tsapiInterface.TsapiChannel;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiLightweightUnsolicitedHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiSession;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory;

public class TsapiSessionFactoryOio extends TsapiSessionFactory {
	private static Logger log = Logger.getLogger(TsapiSessionFactoryOio.class);
	private String debugID;
	private static String trustStoreLocation = null;

	private static String trustStorePassword = null;

	private static boolean verifyServerCertificate = Boolean.valueOf("false")
			.booleanValue();

	static boolean isSecureTlink(final String tlink) {
		final String[] tokens = tlink.split("#");
		try {
			if (tokens[2].equalsIgnoreCase("CSTA-S"))
				return true;
		} catch (final ArrayIndexOutOfBoundsException e) {
			return false;
		}

		return false;
	}

	@Override
	public void configure(final Properties props) {
		final Enumeration<?> eprop = props.propertyNames();

		while (eprop.hasMoreElements()) {
			final String tsapiProperty = (String) eprop.nextElement();

			if (tsapiProperty.equalsIgnoreCase("trustStoreLocation")) {
				TsapiSessionFactoryOio.trustStoreLocation = props
						.getProperty(tsapiProperty);

				if (TsapiSessionFactoryOio.trustStoreLocation.length() == 0)
					TsapiSessionFactoryOio.trustStoreLocation = null;

				if (TsapiSessionFactoryOio.trustStoreLocation != null)
					TsapiSessionFactoryOio.log
							.info("Property \"trustStoreLocation\" is \""
									+ TsapiSessionFactoryOio.trustStoreLocation
									+ "\"");
				else
					TsapiSessionFactoryOio.log
							.info("Property \"trustStoreLocation\" is null");
			} else if (tsapiProperty.equalsIgnoreCase("trustStorePassword")) {
				TsapiSessionFactoryOio.trustStorePassword = props
						.getProperty(tsapiProperty);

				if (TsapiSessionFactoryOio.trustStorePassword != null)
					TsapiSessionFactoryOio.log
							.info("Property \"trustStorePassword\" is \"****\"");
				else
					TsapiSessionFactoryOio.log
							.info("Property \"trustStorePassword\" is null");
			} else if (tsapiProperty
					.equalsIgnoreCase("verifyServerCertificate")) {
				final String propertyValue = props.getProperty(tsapiProperty,
						"false");

				TsapiSessionFactoryOio.verifyServerCertificate = Boolean
						.valueOf(propertyValue).booleanValue();

				TsapiSessionFactoryOio.log
						.info("Property \"verifyServerCertificate\" is "
								+ TsapiSessionFactoryOio.verifyServerCertificate);
			}
		}
	}

	@Override
	public TsapiSession getLightweightTsapiSession(final InetSocketAddress addr)
			throws IOException {
		TsapiSessionFactoryOio.log.debug("Attempting to connect to server <"
				+ addr + ">");
		final TsapiChannel channel = new TsapiChannelOio(addr, SocketFactory
				.getDefault());

		TsapiSessionFactoryOio.log.debug("Successfully  connected to server <"
				+ addr + ">");

		final TsapiSession sess = new TsapiSession(channel, false, debugID);

		sess.setHandler(new TsapiLightweightUnsolicitedHandler(sess));

		return sess;
	}

	@Override
	public TsapiSession getTsapiSession(final InetSocketAddress addr)
			throws IOException {
		final TsapiChannel channel = new TsapiChannelOio(addr, SocketFactory
				.getDefault());

		return new TsapiSession(channel, true, debugID);
	}

	@Override
	public TsapiSession getTsapiSession(final InetSocketAddress addr,
			final String tlink) throws IOException {
		SocketFactory socketFactory;
		if (TsapiSessionFactoryOio.isSecureTlink(tlink)) {
			TsapiSSLContext.getInstance(
					TsapiSessionFactoryOio.trustStoreLocation,
					TsapiSessionFactoryOio.trustStorePassword,
					TsapiSessionFactoryOio.verifyServerCertificate);

			socketFactory = TsapiSSLContext.getSocketFactory();
		} else
			socketFactory = SocketFactory.getDefault();

		final TsapiChannel channel = new TsapiChannelOio(addr, socketFactory);

		return new TsapiSession(channel, true, debugID);
	}

	@Override
	public void setDebugID(final String _debugID) {
		debugID = _debugID;
	}
}
