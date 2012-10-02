package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV10QueryStationStatusConfEvent extends
		LucentQueryStationStatusConfEvent {
	short serviceState;
	static final int PDU = 151;

	public short getServiceState() {
		return this.serviceState;
	}

	public void setServiceState(short serviceState) {
		this.serviceState = serviceState;
	}

	static LucentV10QueryStationStatusConfEvent decode(InputStream in) {
		LucentV10QueryStationStatusConfEvent _this = new LucentV10QueryStationStatusConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.serviceState = LucentServiceState.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		LucentServiceState.encode(this.serviceState, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV10QueryStationStatusConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNBoolean.print(this.stationStatus, "stationStatus",
				indent));
		lines.addAll(LucentServiceState.print(this.serviceState,
				"serviceState", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 151;
	}
}