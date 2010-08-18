package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentUserEnteredCode extends LucentPrivateData {
	public static final short UE_ANY = 0;
	public static final short UE_LOGIN_DIGITS = 2;
	public static final short UE_CALL_PROMPTER = 5;
	public static final short UE_DATA_BASE_PROVIDED = 17;
	public static final short UE_TONE_DETECTOR = 32;
	public static final short UE_COLLECT = 0;
	public static final short UE_ENTERED = 1;

	public static LucentUserEnteredCode decode(InputStream in) {
		LucentUserEnteredCode _this = new LucentUserEnteredCode();
		_this.doDecode(in);
		if (_this.type == -1) {
			return null;
		}
		return _this;
	}

	public static void encode(LucentUserEnteredCode _this, OutputStream out) {
		if (_this == null) {
			_this = new LucentUserEnteredCode();
		}
		_this.encode(out);
	}

	public static Collection<String> print(LucentUserEnteredCode _this,
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

		lines.addAll(UserEnteredCodeType.print(_this.type, "type", indent));
		lines.addAll(UserEnteredCodeIndicator.print(_this.indicator,
				"indicator", indent));
		lines.addAll(ASNIA5String.print(_this.data, "data", indent));
		lines.addAll(ASNIA5String.print(_this.collectVDN_asn, "collectVDN",
				indent));

		lines.add(_indent + "}");
		return lines;
	}

	short type;

	short indicator;

	String data;

	String collectVDN_asn;

	@Override
	public void decodeMembers(InputStream memberStream) {
		type = ASNEnumerated.decode(memberStream);
		indicator = ASNEnumerated.decode(memberStream);
		data = ASNIA5String.decode(memberStream);
		collectVDN_asn = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(type, memberStream);
		ASNEnumerated.encode(indicator, memberStream);
		ASNIA5String.encode(data, memberStream);
		ASNIA5String.encode(collectVDN_asn, memberStream);
	}

	public String getCollectVDN_asn() {
		return collectVDN_asn;
	}

	public String getDigits() {
		return data;
	}

	public short getIndicator() {
		return indicator;
	}

	public short getType() {
		return type;
	}

	public void setCollectVDN_asn(String _collectVDN_asn) {
		collectVDN_asn = _collectVDN_asn;
	}

	public void setData(String _data) {
		data = _data;
	}

	public void setIndicator(short _indicator) {
		indicator = _indicator;
	}

	public void setType(short _type) {
		type = _type;
	}
}

