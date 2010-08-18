package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNOctetString;

public class LucentUserToUserInfo extends LucentPrivateData {
	public static LucentUserToUserInfo decode(InputStream in) {
		LucentUserToUserInfo _this = new LucentUserToUserInfo();
		_this.doDecode(in);
		if ((_this.type == -1) || (_this.data == null)) {
			return null;
		}
		return _this;
	}

	public static void encode(LucentUserToUserInfo _this, OutputStream out) {
		if (_this == null) {
			_this = new LucentUserToUserInfo();
		}
		_this.encode(out);
	}

	public static Collection<String> print(LucentUserToUserInfo _this,
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

		lines.addAll(UUIProtocolType.print(_this.type, "type", indent));
		if (_this.type == 4) {
			lines.addAll(ASNIA5String.print(new String(_this.data), "data",
					indent));
		} else {
			lines.addAll(ASNOctetString.print(_this.data, "data", indent));
		}

		lines.add(_indent + "}");
		return lines;
	}

	protected short type;

	protected byte[] data;

	public LucentUserToUserInfo() {
		type = -1;
	}

	public LucentUserToUserInfo(byte[] _data) {
		type = 0;
		data = _data;
	}

	public LucentUserToUserInfo(byte[] _data, short _type) {
		type = _type;
		data = _data;
	}

	public LucentUserToUserInfo(String _data) {
		type = 4;
		data = _data.getBytes();
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		type = ASNEnumerated.decode(memberStream);
		data = ASNOctetString.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(type, memberStream);
		ASNOctetString.encode(data, memberStream);
	}

	public byte[] getBytes() {
		return data;
	}

	public String getString() {
		return new String(data);
	}

	public short getType() {
		return type;
	}

	public boolean isAscii() {
		return type == 4;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo JD-Core Version: 0.5.4
 */