package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentQueryStationStatusConfEvent extends LucentPrivateData {
	boolean stationStatus;
	static final int PDU = 23;

	static LucentQueryStationStatusConfEvent decode(InputStream in) {
		LucentQueryStationStatusConfEvent _this = new LucentQueryStationStatusConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.stationStatus = ASNBoolean.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		ASNBoolean.encode(this.stationStatus, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryStationStatusConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNBoolean.print(this.stationStatus, "stationStatus",
				indent));

		lines.add("}");
		return lines;
	}

	public boolean getStationStatus() {
		return this.stationStatus;
	}

	public void setStationStatus(boolean status) {
		this.stationStatus = status;
	}

	public int getPDU() {
		return 23;
	}
}