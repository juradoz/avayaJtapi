package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentUserCollectCode extends LucentPrivateData {
	public static void encode(LucentUserCollectCode _this,
			final OutputStream out) {
		if (_this == null)
			_this = new LucentUserCollectCode();
		_this.encode(out);
	}

	public static Collection<String> print(final LucentUserCollectCode _this,
			final String name, final String _indent) {
		final Collection<String> lines = new ArrayList<String>();

		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null)
			lines.add(_indent + name);
		lines.add(_indent + "{");

		final String indent = _indent + "  ";

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

	short type;
	int digitsToBeCollected;
	int timeout;

	CSTAConnectionID collectParty;

	short specificEvent;

	public LucentUserCollectCode() {
		type = 0;
	}

	public LucentUserCollectCode(final short _type,
			final int _digitsToBeCollected, final int _timeout,
			final CSTAConnectionID _collectParty, final short _specificEvent) {
		type = _type;
		digitsToBeCollected = _digitsToBeCollected;
		timeout = _timeout;
		collectParty = _collectParty;
		specificEvent = _specificEvent;
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(type, memberStream);
		ASNInteger.encode(digitsToBeCollected, memberStream);
		ASNInteger.encode(timeout, memberStream);
		CSTAConnectionID.encode(collectParty, memberStream);
		ASNEnumerated.encode(specificEvent, memberStream);
	}
}
