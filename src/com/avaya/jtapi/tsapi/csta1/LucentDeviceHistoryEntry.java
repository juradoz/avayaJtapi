package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentDeviceHistoryEntry extends LucentPrivateData {
	public static LucentDeviceHistoryEntry decode(final InputStream in) {
		final LucentDeviceHistoryEntry _this = new LucentDeviceHistoryEntry();
		_this.doDecode(in);
		if (_this.oldDeviceID == null && _this.oldConnectionID == null)
			return null;

		return _this;
	}

	static void encode(LucentDeviceHistoryEntry _this, final OutputStream out) {
		if (_this == null)
			_this = new LucentDeviceHistoryEntry();
		_this.encode(out);
	}

	public static Collection<String> print(
			final LucentDeviceHistoryEntry _this, final String name,
			final String _indent) {
		final Collection<String> lines = new ArrayList<String>();
		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null)
			lines.add(_indent + name);
		lines.add(_indent + "{");

		final String indent = _indent + "  ";

		lines.addAll(ASNIA5String.print(_this.oldDeviceID, "oldDeviceID",
				indent));
		lines.addAll(EventCause.print(_this.eventCause, "eventCause", indent));
		lines.addAll(CSTAConnectionID.print(_this.oldConnectionID,
				"oldConnectionID", indent));

		lines.add(_indent + "}");
		return lines;
	}

	private String oldDeviceID;

	private short eventCause;

	private CSTAConnectionID oldConnectionID;

	LucentDeviceHistoryEntry() {
	}

	public LucentDeviceHistoryEntry(final String _oldDeviceID,
			final short _eventCause, final CSTAConnectionID _oldConnectionID) {
		oldDeviceID = _oldDeviceID;
		eventCause = _eventCause;
		oldConnectionID = _oldConnectionID;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		oldDeviceID = ASNIA5String.decode(memberStream);
		eventCause = ASNEnumerated.decode(memberStream);
		oldConnectionID = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(oldDeviceID, memberStream);
		ASNEnumerated.encode(eventCause, memberStream);
		CSTAConnectionID.encode(oldConnectionID, memberStream);
	}

	public short getEventCause() {
		return eventCause;
	}

	public CSTAConnectionID getOldConnectionID() {
		return oldConnectionID;
	}

	public String getOldDeviceID() {
		return oldDeviceID;
	}
}
