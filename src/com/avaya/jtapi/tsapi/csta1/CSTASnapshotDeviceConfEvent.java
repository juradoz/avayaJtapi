package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTASnapshotDeviceConfEvent extends CSTAConfirmation {
	CSTASnapshotDeviceResponseInfo[] snapshotData;
	public static final int PDU = 123;

	public CSTASnapshotDeviceConfEvent() {
	}

	public CSTASnapshotDeviceConfEvent(
			CSTASnapshotDeviceResponseInfo[] _snapshotData) {
		this.snapshotData = _snapshotData;
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTASnapshotDeviceData.encode(this.snapshotData, memberStream);
	}

	public static CSTASnapshotDeviceConfEvent decode(InputStream in) {
		CSTASnapshotDeviceConfEvent _this = new CSTASnapshotDeviceConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.snapshotData = CSTASnapshotDeviceData.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASnapshotDeviceConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTASnapshotDeviceData.print(this.snapshotData,
				"snapshotData", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 123;
	}

	public CSTASnapshotDeviceResponseInfo[] getSnapshotData() {
		return this.snapshotData;
	}
}