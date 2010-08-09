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

	public static TsapiSessionFactory getTsapiSessionFactory(Properties props) {
		String className = "com.avaya.jtapi.tsapi.tsapiInterface.oio.TsapiSessionFactoryOio";

		if ((props != null)
				&& (props
						.get("com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory.sessionFactory") != null)) {
			className = (String) props
					.get("com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory.sessionFactory");
		}
		TsapiSessionFactory factory = null;
		try {
			Class theClass = Class.forName(className);
			factory = (TsapiSessionFactory) theClass.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("class not found", e);
		} catch (InstantiationException e) {
			throw new RuntimeException("Could not instantiate", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Could not access", e);
		}

		factory.configure(props);

		return factory;
	}

	protected abstract void configure(Properties paramProperties);

	public Vector<ACSNameAddr> enumServices(Vector<InetSocketAddress> servers,
			boolean useTLinkIP) {
		return enumServices(servers, useTLinkIP, Tsapi.getGetServicesTimeout());
	}

	public Vector<ACSNameAddr> enumServices(Vector<InetSocketAddress> servers,
			boolean useTLinkIP, int timeout) {
		Enumeration eserv = servers.elements();
		Vector services = new Vector();
		while (eserv.hasMoreElements()) {
			InetSocketAddress addr;
			try {
				addr = (InetSocketAddress) eserv.nextElement();
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
				continue;
			}

			TsapiSession session = null;
			try {
				session = getLightweightTsapiSession(addr);

				TsapiRequest req = new ACSNameSrvRequest((short) 1);

				byte[] inetAddr = addr.getAddress().getAddress();
				ACSNameSrvReply reply;
				do {
					CSTAEvent event = session.send(req, null, timeout);

					if (!(event.getEvent() instanceof ACSNameSrvReply)) {
						log.info("unexpected reply from name server <" + addr
								+ ">");
						throw new TsapiPlatformException(4, 0,
								"unexpected reply from name server");
					}

					reply = (ACSNameSrvReply) event.getEvent();
					ACSNameAddr[] replyList = reply.getList();
					log.debug("Listing services available on server <" + addr
							+ ">");
					for (int i = 0; i < replyList.length; ++i) {
						byte[] serverAddr = replyList[i].getServerAddr();
						if (!useTLinkIP) {
							serverAddr[4] = inetAddr[0];
							serverAddr[5] = inetAddr[1];
							serverAddr[6] = inetAddr[2];
							serverAddr[7] = inetAddr[3];
						}
						log.debug("SERVICE[" + i + "]: "
								+ replyList[i].getServerName());
						services.addElement(replyList[i]);
					}
				} while (reply.isMore());
			} catch (Exception e) {
				log.error("enumServices() <" + addr + ">: " + e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}
		if (services.size() == 0) {
			log.warn("No valid telephony servers found");
		}
		return services;
	}

	public ACSNameAddr findTlink(String tlink,
			Vector<InetSocketAddress> servers, boolean useTLinkIP) {
		TsapiAlternateTlinkEntriesList alternateTlinkEntriesList = TsapiAlternateTlinkEntriesList
				.Instance();

		boolean foundAlternate = false;
		int alternateIndex = -1;
		ACSNameAddr alternateACSNameAddr = new ACSNameAddr();

		Enumeration services = enumServices(servers, useTLinkIP).elements();
		while (services.hasMoreElements()) {
			ACSNameAddr nameAddr;
			try {
				nameAddr = (ACSNameAddr) services.nextElement();
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
				continue;
			}

			if (nameAddr.getServerName().equalsIgnoreCase(tlink)) {
				return nameAddr;
			}

			int index = alternateTlinkEntriesList.getAlternateTlinkIndex(tlink,
					nameAddr.getServerName());

			if ((index >= 0)
					&& (((!foundAlternate) || (index < alternateIndex)))) {
				foundAlternate = true;
				alternateIndex = index;
				alternateACSNameAddr = nameAddr;
			}

		}

		if (foundAlternate == true) {
			return alternateACSNameAddr;
		}

		throw new TsapiPlatformException(4, 0, "server "
				+ new ArrayList(servers) + " with tlink '" + tlink
				+ "' not found");
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory JD-Core Version:
 * 0.5.4
 */