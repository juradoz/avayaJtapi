package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTASnapshotDevice extends CSTARequest {
	public static CSTASnapshotDevice decode(InputStream in) {
		CSTASnapshotDevice _this = new CSTASnapshotDevice();
		_this.doDecode(in);

		return _this;
	}

	String snapshotObject;

	public static final int PDU = 122;

	public CSTASnapshotDevice() {
	}

	public CSTASnapshotDevice(String _snapshotObject) {
		snapshotObject = _snapshotObject;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		snapshotObject = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(snapshotObject, memberStream);
	}

	@Override
	public int getPDU() {
		return 122;
	}

	public String getSnapshotObject() {
		return snapshotObject;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASnapshotDevice ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(snapshotObject, "snapshotObject",
				indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTASnapshotDevice JD-Core Version: 0.5.4
 */