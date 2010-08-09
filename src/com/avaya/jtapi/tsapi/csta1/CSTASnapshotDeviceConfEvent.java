package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTASnapshotDeviceConfEvent extends CSTAConfirmation {
	public static CSTASnapshotDeviceConfEvent decode(InputStream in) {
		CSTASnapshotDeviceConfEvent _this = new CSTASnapshotDeviceConfEvent();
		_this.doDecode(in);

		return _this;
	}

	CSTASnapshotDeviceResponseInfo[] snapshotData;

	public static final int PDU = 123;

	public CSTASnapshotDeviceConfEvent() {
	}

	public CSTASnapshotDeviceConfEvent(
			CSTASnapshotDeviceResponseInfo[] _snapshotData) {
		snapshotData = _snapshotData;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		snapshotData = CSTASnapshotDeviceData.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();

		lines.add("CSTASnapshotDeviceConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTASnapshotDeviceData.print(snapshotData, "snapshotData",
				indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceConfEvent JD-Core Version:
 * 0.5.4
 */