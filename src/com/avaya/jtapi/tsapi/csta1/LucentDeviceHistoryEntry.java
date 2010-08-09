package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentDeviceHistoryEntry extends LucentPrivateData {
	public static LucentDeviceHistoryEntry decode(InputStream in) {
		LucentDeviceHistoryEntry _this = new LucentDeviceHistoryEntry();
		_this.doDecode(in);
		if ((_this.oldDeviceID == null) && (_this.oldConnectionID == null)) {
			return null;
		}

		return _this;
	}

	static void encode(LucentDeviceHistoryEntry _this, OutputStream out) {
		if (_this == null) {
			_this = new LucentDeviceHistoryEntry();
		}
		_this.encode(out);
	}

	public static Collection<String> print(LucentDeviceHistoryEntry _this,
			String name, String _indent) {
		Collection lines = new ArrayList();
		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null) {
			lines.add(_indent + name);
		}
		lines.add(_indent + "{");

		String indent = _indent + "  ";

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

	public LucentDeviceHistoryEntry(String _oldDeviceID, short _eventCause,
			CSTAConnectionID _oldConnectionID) {
		oldDeviceID = _oldDeviceID;
		eventCause = _eventCause;
		oldConnectionID = _oldConnectionID;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		oldDeviceID = ASNIA5String.decode(memberStream);
		eventCause = ASNEnumerated.decode(memberStream);
		oldConnectionID = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentDeviceHistoryEntry JD-Core Version: 0.5.4
 */