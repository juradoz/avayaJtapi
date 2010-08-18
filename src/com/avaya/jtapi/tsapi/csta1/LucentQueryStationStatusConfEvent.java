package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public final class LucentQueryStationStatusConfEvent extends LucentPrivateData {
	boolean stationStatus;
	static final int PDU = 23;

	static LucentQueryStationStatusConfEvent decode(final InputStream in) {
		final LucentQueryStationStatusConfEvent _this = new LucentQueryStationStatusConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		stationStatus = ASNBoolean.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 23;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryStationStatusConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNBoolean.print(stationStatus, "stationStatus", indent));

		lines.add("}");
		return lines;
	}
}
