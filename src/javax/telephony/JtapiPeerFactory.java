package javax.telephony;

public class JtapiPeerFactory {
	private static String getDefaultJtapiPeerName() {
		final String JtapiPeerName = "DefaultJtapiPeer";
		return JtapiPeerName;
	}

	public static synchronized JtapiPeer getJtapiPeer(String jtapiPeerName)
			throws JtapiPeerUnavailableException {
		if (jtapiPeerName == null || jtapiPeerName.length() == 0)
			jtapiPeerName = JtapiPeerFactory.getDefaultJtapiPeerName();

		if (jtapiPeerName == null)
			throw new JtapiPeerUnavailableException();
		try {
			@SuppressWarnings("rawtypes")
			final Class jtapiPeerClass = Class.forName(jtapiPeerName);

			return (JtapiPeer) jtapiPeerClass.newInstance();
		} catch (final Exception e) {
			final String errmsg = "JtapiPeer: " + jtapiPeerName
					+ " could not be instantiated.";

			throw new JtapiPeerUnavailableException(errmsg);
		}
	}
}
