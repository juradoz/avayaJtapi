package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentUserEnteredCode extends LucentPrivateData {
	public static final short UE_ANY = 0;
	public static final short UE_LOGIN_DIGITS = 2;
	public static final short UE_CALL_PROMPTER = 5;
	public static final short UE_DATA_BASE_PROVIDED = 17;
	public static final short UE_TONE_DETECTOR = 32;
	public static final short UE_COLLECT = 0;
	public static final short UE_ENTERED = 1;
	short type;
	short indicator;
	String data;
	String collectVDN_asn;

	public short getType() {
		return this.type;
	}

	public short getIndicator() {
		return this.indicator;
	}

	public String getDigits() {
		return this.data;
	}

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

	public void encodeMembers(OutputStream memberStream) {
		UserEnteredCodeType.encode(this.type, memberStream);
		UserEnteredCodeIndicator.encode(this.indicator, memberStream);
		ASNIA5String.encode(this.data, memberStream);
		DeviceID.encode(this.collectVDN_asn, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		this.type = UserEnteredCodeType.decode(memberStream);
		this.indicator = UserEnteredCodeIndicator.decode(memberStream);
		this.data = ASNIA5String.decode(memberStream);
		this.collectVDN_asn = DeviceID.decode(memberStream);
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
		lines.addAll(DeviceID.print(_this.collectVDN_asn, "collectVDN", indent));

		lines.add(_indent + "}");
		return lines;
	}

	public void setCollectVDN_asn(String _collectVDN_asn) {
		this.collectVDN_asn = _collectVDN_asn;
	}

	public void setData(String _data) {
		this.data = _data;
	}

	public void setIndicator(short _indicator) {
		this.indicator = _indicator;
	}

	public void setType(short _type) {
		this.type = _type;
	}

	public String getCollectVDN_asn() {
		return this.collectVDN_asn;
	}
}