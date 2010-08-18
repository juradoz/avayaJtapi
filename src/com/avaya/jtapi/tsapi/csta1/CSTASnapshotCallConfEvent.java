package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTASnapshotCallConfEvent extends CSTAConfirmation {
	public static CSTASnapshotCallConfEvent decode(final InputStream in) {
		final CSTASnapshotCallConfEvent _this = new CSTASnapshotCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	CSTASnapshotCallResponseInfo[] snapshotData;

	public static final int PDU = 121;

	public CSTASnapshotCallConfEvent() {
	}

	public CSTASnapshotCallConfEvent(
			final CSTASnapshotCallResponseInfo[] _snapshotData) {
		snapshotData = _snapshotData;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		snapshotData = CSTASnapshotCallData.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTASnapshotCallData.encode(snapshotData, memberStream);
	}

	@Override
	public int getPDU() {
		return 121;
	}

	public CSTASnapshotCallResponseInfo[] getSnapshotData() {
		return snapshotData;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASnapshotCallConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTASnapshotCallData.print(snapshotData, "snapshotData",
				indent));

		lines.add("}");
		return lines;
	}
}
