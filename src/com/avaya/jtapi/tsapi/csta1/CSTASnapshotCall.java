package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTASnapshotCall extends CSTARequest {
	CSTAConnectionID snapshotObject;
	public static final int PDU = 120;

	public CSTASnapshotCall(CSTAConnectionID _snapshotObject) {
		this.snapshotObject = _snapshotObject;
	}

	public CSTASnapshotCall() {
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.snapshotObject, memberStream);
	}

	public static CSTASnapshotCall decode(InputStream in) {
		CSTASnapshotCall _this = new CSTASnapshotCall();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.snapshotObject = CSTAConnectionID.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASnapshotCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.snapshotObject,
				"snapshotObject", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 120;
	}

	public CSTAConnectionID getSnapshotObject() {
		return this.snapshotObject;
	}
}