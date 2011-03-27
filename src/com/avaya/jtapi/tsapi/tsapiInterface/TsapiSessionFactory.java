package com.avaya.jtapi.tsapi.tsapiInterface;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.acs.ACSNameAddr;
import com.avaya.jtapi.tsapi.acs.ACSNameSrvReply;
import com.avaya.jtapi.tsapi.acs.ACSNameSrvRequest;
import com.avaya.jtapi.tsapi.asn1.TsapiRequest;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;

public abstract class TsapiSessionFactory {
	private static Logger log = Logger.getLogger(TsapiSessionFactory.class);
	public static final String FACTORY_KEY = "com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory.sessionFactory";

	@SuppressWarnings("unchecked")
	public static TsapiSessionFactory getTsapiSessionFactory(
			final Properties props) {
		String className = "com.avaya.jtapi.tsapi.tsapiInterface.oio.TsapiSessionFactoryOio";

		if (props != null
				&& props.get("com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory.sessionFactory") != null)
			className = (String) props
					.get("com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory.sessionFactory");
		TsapiSessionFactory factory = null;
		try {
			final Class theClass = Class.forName(className);
			factory = (TsapiSessionFactory) theClass.newInstance();
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException("class not found", e);
		} catch (final InstantiationException e) {
			throw new RuntimeException("Could not instantiate", e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException("Could not access", e);
		}

		factory.configure(props);

		return factory;
	}

	protected abstract void configure(Properties paramProperties);

	public Vector<ACSNameAddr> enumServices(
			final Vector<InetSocketAddress> servers, final boolean useTLinkIP) {
		return enumServices(servers, useTLinkIP, Tsapi.getGetServicesTimeout());
	}

	public Vector<ACSNameAddr> enumServices(
			final Vector<InetSocketAddress> servers, final boolean useTLinkIP,
			final int timeout) {
		final Enumeration<InetSocketAddress> eserv = servers.elements();
		final Vector<ACSNameAddr> services = new Vector<ACSNameAddr>();
		while (eserv.hasMoreElements()) {
			InetSocketAddress addr;
			try {
				addr = eserv.nextElement();
			} catch (final NoSuchElementException e) {
				TsapiSessionFactory.log.error(e.getMessage(), e);
				continue;
			}

			TsapiSession session = null;
			try {
				session = getLightweightTsapiSession(addr);

				final TsapiRequest req = new ACSNameSrvRequest((short) 1);

				final byte[] inetAddr = addr.getAddress().getAddress();
				ACSNameSrvReply reply;
				do {
					final CSTAEvent event = session.send(req, null, timeout);

					if (!(event.getEvent() instanceof ACSNameSrvReply)) {
						TsapiSessionFactory.log
								.info("unexpected reply from name server <"
										+ addr + ">");
						throw new TsapiPlatformException(4, 0,
								"unexpected reply from name server");
					}

					reply = (ACSNameSrvReply) event.getEvent();
					final ACSNameAddr[] replyList = reply.getList();
					TsapiSessionFactory.log
							.debug("Listing services available on server <"
									+ addr + ">");
					for (int i = 0; i < replyList.length; ++i) {
						final byte[] serverAddr = replyList[i].getServerAddr();
						if (!useTLinkIP) {
							serverAddr[4] = inetAddr[0];
							serverAddr[5] = inetAddr[1];
							serverAddr[6] = inetAddr[2];
							serverAddr[7] = inetAddr[3];
						}
						TsapiSessionFactory.log.debug("SERVICE[" + i + "]: "
								+ replyList[i].getServerName());
						services.addElement(replyList[i]);
					}
				} while (reply.isMore());
			} catch (final Exception e) {
				TsapiSessionFactory.log.error("enumServices() <" + addr + ">: "
						+ e);
			} finally {
				if (session != null)
					session.close();
			}
		}
		if (services.size() == 0)
			TsapiSessionFactory.log.warn("No valid telephony servers found");
		return services;
	}

	public ACSNameAddr findTlink(final String tlink,
			final Vector<InetSocketAddress> servers, final boolean useTLinkIP) {
		final TsapiAlternateTlinkEntriesList alternateTlinkEntriesList = TsapiAlternateTlinkEntriesList
				.Instance();

		boolean foundAlternate = false;
		int alternateIndex = -1;
		ACSNameAddr alternateACSNameAddr = new ACSNameAddr();

		final Enumeration<ACSNameAddr> services = enumServices(servers,
				useTLinkIP).elements();
		while (services.hasMoreElements()) {
			ACSNameAddr nameAddr;
			try {
				nameAddr = services.nextElement();
			} catch (final NoSuchElementException e) {
				TsapiSessionFactory.log.error(e.getMessage(), e);
				continue;
			}

			if (nameAddr.getServerName().equalsIgnoreCase(tlink))
				return nameAddr;

			final int index = alternateTlinkEntriesList.getAlternateTlinkIndex(
					tlink, nameAddr.getServerName());

			if (index >= 0 && (!foundAlternate || index < alternateIndex)) {
				foundAlternate = true;
				alternateIndex = index;
				alternateACSNameAddr = nameAddr;
			}

		}

		if (foundAlternate == true)
			return alternateACSNameAddr;

		throw new TsapiPlatformException(4, 0, "server "
				+ new ArrayList<InetSocketAddress>(servers) + " with tlink '"
				+ tlink + "' not found");
	}

	public abstract TsapiSession getLightweightTsapiSession(
			InetSocketAddress paramInetSocketAddress) throws IOException;

	public abstract TsapiSession getTsapiSession(
			InetSocketAddress paramInetSocketAddress) throws IOException;

	public abstract TsapiSession getTsapiSession(
			InetSocketAddress paramInetSocketAddress, String paramString)
			throws IOException;

	public abstract void setDebugID(String paramString);
}
