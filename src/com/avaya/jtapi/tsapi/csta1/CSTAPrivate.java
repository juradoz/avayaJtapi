package com.avaya.jtapi.tsapi.csta1;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNOctetString;

public class CSTAPrivate {
	private static Logger log = Logger.getLogger(CSTAPrivate.class);

	public static void translatePrivateData(CSTAEvent event, String debugID) {
		try {
			if (event.getPrivData() instanceof CSTAPrivate) {
				CSTAPrivate priv = (CSTAPrivate) event.getPrivData();

				if ((priv.data != null) && (priv.data.length > 0)) {
					if (LucentPrivateData.isAvayaVendor(priv.vendor)) {
						LucentPrivateData luPriv = LucentPrivateData.create(
								priv, event.getEventHeader().getEventType());

						if (luPriv != null) {
							event.setPrivData(luPriv);
						}
					}

				} else {
					event.setPrivData(null);
				}
			}
		} catch (Exception e) {
			log.error("tsapi.translatePrivateData() failure: for " + debugID);
			log.error(e.getMessage(), e);
		}
	}

	public String vendor;
	public byte[] data;

	public int tsType;

	public CSTAPrivate(byte[] _data) {
		this(_data, false);
	}

	public CSTAPrivate(byte[] _data, boolean waitForResponse) {
		data = _data;
		if (waitForResponse) {
			tsType = 89;
		} else {
			tsType = 95;
		}
	}

	public CSTAPrivate(String _vendor, byte[] _data, int _tsType) {
		vendor = _vendor;
		data = _data;
		tsType = _tsType;
	}

	public byte[] getData() {
		return data;
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("TsapiPrivate ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(vendor, "vendor", indent));
		lines.addAll(ASNOctetString.print(data, "data", indent));
		lines.addAll(ASNInteger.print(tsType, "tsType", indent));

		lines.add("}");
		return lines;
	}
}

