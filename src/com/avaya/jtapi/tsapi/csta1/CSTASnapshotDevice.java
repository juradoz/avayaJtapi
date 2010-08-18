package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTASnapshotDevice extends CSTARequest {
	public static CSTASnapshotDevice decode(final InputStream in) {
		final CSTASnapshotDevice _this = new CSTASnapshotDevice();
		_this.doDecode(in);

		return _this;
	}

	String snapshotObject;

	public static final int PDU = 122;

	public CSTASnapshotDevice() {
	}

	public CSTASnapshotDevice(final String _snapshotObject) {
		snapshotObject = _snapshotObject;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		snapshotObject = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASnapshotDevice ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(snapshotObject, "snapshotObject",
				indent));

		lines.add("}");
		return lines;
	}
}
