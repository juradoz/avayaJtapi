package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public class LucentMonitorCallConfEvent extends LucentPrivateData {
	int usedFilter;
	CSTASnapshotCallResponseInfo[] snapshotCall;
	public static final int PDU = 33;

	static LucentMonitorCallConfEvent decode(InputStream in) {
		LucentMonitorCallConfEvent _this = new LucentMonitorCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		usedFilter = ASNBitString.decode(memberStream);
		snapshotCall = LucentSnapshotCall.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		LucentPrivateFilter.encode(usedFilter, memberStream);
		LucentSnapshotCall.encode(snapshotCall, memberStream);
	}

	@Override
	public int getPDU() {
		return 33;
	}

	public CSTASnapshotCallResponseInfo[] getSnapshotCall() {
		return snapshotCall;
	}

	public int getUsedFilter() {
		return usedFilter;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentMonitorCallConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentPrivateFilter
				.print(usedFilter, "usedFilter", indent));
		lines.addAll(LucentSnapshotCall.print(snapshotCall, "snapshotCall",
				indent));

		lines.add("}");
		return lines;
	}

	public void setSnapshotCall(CSTASnapshotCallResponseInfo[] snapshotCall) {
		this.snapshotCall = snapshotCall;
	}

	public void setUsedFilter(int usedFilter) {
		this.usedFilter = usedFilter;
	}
}

