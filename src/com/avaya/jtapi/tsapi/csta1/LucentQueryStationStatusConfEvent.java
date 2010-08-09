package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public final class LucentQueryStationStatusConfEvent extends LucentPrivateData {
	boolean stationStatus;
	static final int PDU = 23;

	static LucentQueryStationStatusConfEvent decode(InputStream in) {
		LucentQueryStationStatusConfEvent _this = new LucentQueryStationStatusConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		stationStatus = ASNBoolean.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 23;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentQueryStationStatusConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNBoolean.print(stationStatus, "stationStatus", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentQueryStationStatusConfEvent JD-Core
 * Version: 0.5.4
 */