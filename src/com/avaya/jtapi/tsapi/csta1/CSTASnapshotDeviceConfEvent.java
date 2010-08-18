package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTASnapshotDeviceConfEvent extends CSTAConfirmation {
	public static CSTASnapshotDeviceConfEvent decode(final InputStream in) {
		final CSTASnapshotDeviceConfEvent _this = new CSTASnapshotDeviceConfEvent();
		_this.doDecode(in);

		return _this;
	}

	CSTASnapshotDeviceResponseInfo[] snapshotData;

	public static final int PDU = 123;

	public CSTASnapshotDeviceConfEvent() {
	}

	public CSTASnapshotDeviceConfEvent(
			final CSTASnapshotDeviceResponseInfo[] _snapshotData) {
		snapshotData = _snapshotData;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		snapshotData = CSTASnapshotDeviceData.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTASnapshotDeviceData.encode(snapshotData, memberStream);
	}

	@Override
	public int getPDU() {
		return 123;
	}

	public CSTASnapshotDeviceResponseInfo[] getSnapshotData() {
		return snapshotData;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASnapshotDeviceConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTASnapshotDeviceData.print(snapshotData, "snapshotData",
				indent));

		lines.add("}");
		return lines;
	}
}
