package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTASnapshotCall extends CSTARequest {
	public static CSTASnapshotCall decode(InputStream in) {
		CSTASnapshotCall _this = new CSTASnapshotCall();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID snapshotObject;

	public static final int PDU = 120;

	public CSTASnapshotCall() {
	}

	public CSTASnapshotCall(CSTAConnectionID _snapshotObject) {
		snapshotObject = _snapshotObject;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		snapshotObject = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(snapshotObject, memberStream);
	}

	@Override
	public int getPDU() {
		return 120;
	}

	public CSTAConnectionID getSnapshotObject() {
		return snapshotObject;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("CSTASnapshotCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(snapshotObject, "snapshotObject",
				indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTASnapshotCall JD-Core Version: 0.5.4
 */