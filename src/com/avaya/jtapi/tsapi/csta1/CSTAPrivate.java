package com.avaya.jtapi.tsapi.csta1;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNOctetString;

public class CSTAPrivate {
	private static Logger log = Logger.getLogger(CSTAPrivate.class);

	public static void translatePrivateData(final CSTAEvent event,
			final String debugID) {
		try {
			if (event.getPrivData() instanceof CSTAPrivate) {
				final CSTAPrivate priv = (CSTAPrivate) event.getPrivData();

				if (priv.data != null && priv.data.length > 0) {
					if (LucentPrivateData.isAvayaVendor(priv.vendor)) {
						final LucentPrivateData luPriv = LucentPrivateData
								.create(priv, event.getEventHeader()
										.getEventType());

						if (luPriv != null)
							event.setPrivData(luPriv);
					}

				} else
					event.setPrivData(null);
			}
		} catch (final Exception e) {
			CSTAPrivate.log.error("tsapi.translatePrivateData() failure: for "
					+ debugID);
			CSTAPrivate.log.error(e.getMessage(), e);
		}
	}

	public String vendor;
	public byte[] data;

	public int tsType;

	public CSTAPrivate(final byte[] _data) {
		this(_data, false);
	}

	public CSTAPrivate(final byte[] _data, final boolean waitForResponse) {
		data = _data;
		if (waitForResponse)
			tsType = 89;
		else
			tsType = 95;
	}

	public CSTAPrivate(final String _vendor, final byte[] _data,
			final int _tsType) {
		vendor = _vendor;
		data = _data;
		tsType = _tsType;
	}

	public byte[] getData() {
		return data;
	}

	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("TsapiPrivate ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(vendor, "vendor", indent));
		lines.addAll(ASNOctetString.print(data, "data", indent));
		lines.addAll(ASNInteger.print(tsType, "tsType", indent));

		lines.add("}");
		return lines;
	}
}
