package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAConnectionID extends ASNSequence implements
		LucentConnectionID {
	public static final short STATIC_ID = 0;
	public static final short DYNAMIC_ID = 1;

	public static CSTAConnectionID decode(InputStream in) {
		CSTAConnectionID _this = new CSTAConnectionID();
		_this.doDecode(in);

		return _this;
	}

	public static void encode(CSTAConnectionID _this, OutputStream out) {
		if (_this == null) {
			_this = new CSTAConnectionID();
		}
		_this.encode(out);
	}

	public static short getDYNAMIC_ID() {
		return 1;
	}

	public static short getSTATIC_ID() {
		return 0;
	}

	public static Collection<String> print(CSTAConnectionID _this, String name,
			String _indent) {
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

		lines.addAll(ASNInteger.print(_this.callID, "callID", indent));
		lines.addAll(ASNIA5String.print(_this.deviceID, "deviceID", indent));
		lines.addAll(ConnectionIDDevice.print(_this.devIDType, "devIDType",
				indent));

		lines.add(_indent + "}");
		return lines;
	}

	int callID;

	String deviceID;

	short devIDType;

	public CSTAConnectionID() {
	}

	public CSTAConnectionID(int _callID, String _deviceID, short _devIDType) {
		callID = _callID;
		deviceID = _deviceID;
		devIDType = _devIDType;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		callID = ASNInteger.decode(memberStream);
		deviceID = ASNIA5String.decode(memberStream);
		devIDType = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(callID, memberStream);
		ASNIA5String.encode(deviceID, memberStream);
		ASNEnumerated.encode(devIDType, memberStream);
	}

	@Override
	public boolean equals(Object anObject) {
		if (anObject instanceof CSTAConnectionID) {
			CSTAConnectionID anotherConnID = (CSTAConnectionID) anObject;
			if (deviceID == null) {
				return (callID == anotherConnID.callID)
						&& (devIDType == anotherConnID.devIDType)
						&& (anotherConnID.deviceID == null);
			}

			return (callID == anotherConnID.callID)
					&& (devIDType == anotherConnID.devIDType)
					&& (deviceID.equals(anotherConnID.deviceID));
		}

		return false;
	}

	public int getCallID() {
		return callID;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public int getDevIDType() {
		return devIDType;
	}

	@Override
	public int hashCode() {
		if (deviceID == null) {
			return callID + (devIDType << 31);
		}

		return callID + deviceID.hashCode() + (devIDType << 31);
	}

	public void setCallID(int callID) {
		this.callID = callID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public void setdevIDType(short devIDType) {
		this.devIDType = devIDType;
	}

	@Override
	public String toString() {
		return "ConnectionID(" + callID + "," + deviceID + "," + devIDType
				+ ")";
	}
}

