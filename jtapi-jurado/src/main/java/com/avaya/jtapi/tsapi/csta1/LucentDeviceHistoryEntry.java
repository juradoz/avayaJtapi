package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentDeviceHistoryEntry extends LucentPrivateData {
	private String oldDeviceID;
	private short eventCause;
	private CSTAConnectionID oldConnectionID;

	LucentDeviceHistoryEntry() {
	}

	public LucentDeviceHistoryEntry(String _oldDeviceID, short _eventCause,
			CSTAConnectionID _oldConnectionID) {
		this.oldDeviceID = _oldDeviceID;
		this.eventCause = _eventCause;
		this.oldConnectionID = _oldConnectionID;
	}

	static void encode(LucentDeviceHistoryEntry _this, OutputStream out) {
		if (_this == null) {
			_this = new LucentDeviceHistoryEntry();
		}
		_this.encode(out);
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.oldDeviceID, memberStream);
		EventCause.encode(this.eventCause, memberStream);
		CSTAConnectionID.encode(this.oldConnectionID, memberStream);
	}

	public static LucentDeviceHistoryEntry decode(InputStream in) {
		LucentDeviceHistoryEntry _this = new LucentDeviceHistoryEntry();
		_this.doDecode(in);
		if ((_this.oldDeviceID == null) && (_this.oldConnectionID == null)) {
			return null;
		}

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.oldDeviceID = DeviceID.decode(memberStream);
		this.eventCause = EventCause.decode(memberStream);
		this.oldConnectionID = CSTAConnectionID.decode(memberStream);
	}

	public String getOldDeviceID() {
		return this.oldDeviceID;
	}

	public short getEventCause() {
		return this.eventCause;
	}

	public CSTAConnectionID getOldConnectionID() {
		return this.oldConnectionID;
	}

	public static Collection<String> print(LucentDeviceHistoryEntry _this,
			String name, String _indent) {
		Collection<String> lines = new ArrayList<String>();
		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null) {
			lines.add(_indent + name);
		}
		lines.add(_indent + "{");

		String indent = _indent + "  ";

		lines.addAll(DeviceID.print(_this.oldDeviceID, "oldDeviceID", indent));
		lines.addAll(EventCause.print(_this.eventCause, "eventCause", indent));
		lines.addAll(CSTAConnectionID.print(_this.oldConnectionID,
				"oldConnectionID", indent));

		lines.add(_indent + "}");
		return lines;
	}
}