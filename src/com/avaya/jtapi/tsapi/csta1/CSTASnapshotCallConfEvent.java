package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTASnapshotCallConfEvent extends CSTAConfirmation {
	CSTASnapshotCallResponseInfo[] snapshotData;
	public static final int PDU = 121;

	public CSTASnapshotCallConfEvent() {
	}

	public CSTASnapshotCallConfEvent(
			CSTASnapshotCallResponseInfo[] _snapshotData) {
		this.snapshotData = _snapshotData;
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTASnapshotCallData.encode(this.snapshotData, memberStream);
	}

	public static CSTASnapshotCallConfEvent decode(InputStream in) {
		CSTASnapshotCallConfEvent _this = new CSTASnapshotCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.snapshotData = CSTASnapshotCallData.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASnapshotCallConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTASnapshotCallData.print(this.snapshotData,
				"snapshotData", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 121;
	}

	public CSTASnapshotCallResponseInfo[] getSnapshotData() {
		return this.snapshotData;
	}
}