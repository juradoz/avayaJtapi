package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentUserCollectCode extends LucentPrivateData {
	short type;
	int digitsToBeCollected;
	int timeout;
	CSTAConnectionID collectParty;
	short specificEvent;

	public LucentUserCollectCode() {
		this.type = 0;
	}

	public LucentUserCollectCode(short _type, int _digitsToBeCollected,
			int _timeout, CSTAConnectionID _collectParty, short _specificEvent) {
		this.type = _type;
		this.digitsToBeCollected = _digitsToBeCollected;
		this.timeout = _timeout;
		this.collectParty = _collectParty;
		this.specificEvent = _specificEvent;
	}

	public static void encode(LucentUserCollectCode _this, OutputStream out) {
		if (_this == null) {
			_this = new LucentUserCollectCode();
		}
		_this.encode(out);
	}

	public void encodeMembers(OutputStream memberStream) {
		CollectCodeType.encode(this.type, memberStream);
		ASNInteger.encode(this.digitsToBeCollected, memberStream);
		ASNInteger.encode(this.timeout, memberStream);
		CSTAConnectionID.encode(this.collectParty, memberStream);
		SpecificEvent.encode(this.specificEvent, memberStream);
	}

	public static Collection<String> print(LucentUserCollectCode _this,
			String name, String _indent) {
		Collection<String> lines = new ArrayList<String>();

		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null) {
			lines.add(_indent + name);
		}
		lines.add(_indent + "{");

		String indent = _indent + "  ";

		lines.addAll(CollectCodeType.print(_this.type, "type", indent));
		lines.addAll(ASNInteger.print(_this.digitsToBeCollected,
				"digitsToBeCollected", indent));
		lines.addAll(ASNInteger.print(_this.timeout, "timeout", indent));
		lines.addAll(CSTAConnectionID.print(_this.collectParty, "collectParty",
				indent));
		lines.addAll(SpecificEvent.print(_this.specificEvent, "specificEvent",
				indent));

		lines.add(_indent + "}");
		return lines;
	}
}