package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public class LucentLookaheadInfo extends LucentPrivateData {
	public static final short LAI_ALL_INTERFLOW = 0;
	public static final short LAI_THRESHOLD_INTERFLOW = 1;
	public static final short LAI_VECTORING_INTERFLOW = 2;
	public static final short LAI_NOT_IN_QUEUE = 0;
	public static final short LAI_LOW = 1;
	public static final short LAI_MEDIUM = 2;
	public static final short LAI_HIGH = 3;
	public static final short LAI_TOP = 4;

	public static LucentLookaheadInfo decode(InputStream in) {
		LucentLookaheadInfo _this = new LucentLookaheadInfo();
		_this.doDecode(in);
		if (_this.type == -1) {
			return null;
		}
		return _this;
	}

	public static Collection<String> print(LucentLookaheadInfo _this,
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

		lines.addAll(Interflow.print(_this.type, "type", indent));
		lines.addAll(Priority.print(_this.priority, "priority", indent));
		lines.addAll(ASNInteger.print(_this.hours, "hours", indent));
		lines.addAll(ASNInteger.print(_this.minutes, "minutes", indent));
		lines.addAll(ASNInteger.print(_this.seconds, "seconds", indent));
		lines.addAll(ASNIA5String.print(_this.sourceVDN, "sourceVDN", indent));

		lines.add(_indent + "}");
		return lines;
	}

	private short type;
	private short priority;
	private int hours;
	private int minutes;

	private int seconds;

	protected String sourceVDN;

	@Override
	public void decodeMembers(InputStream memberStream) {
		type = ASNEnumerated.decode(memberStream);
		priority = ASNEnumerated.decode(memberStream);
		hours = ASNInteger.decode(memberStream);
		minutes = ASNInteger.decode(memberStream);
		seconds = ASNInteger.decode(memberStream);
		sourceVDN = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(type, memberStream);
		ASNEnumerated.encode(priority, memberStream);
		ASNInteger.encode(hours, memberStream);
		ASNInteger.encode(minutes, memberStream);
		ASNInteger.encode(seconds, memberStream);
		ASNIA5String.encode(sourceVDN, memberStream);
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public short getPriority() {
		return priority;
	}

	public int getSeconds() {
		return seconds;
	}

	public String getSourceVDN() {
		return sourceVDN;
	}

	public short getType() {
		return type;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public void setSourceVDN(String _sourceVDN) {
		sourceVDN = _sourceVDN;
	}

	public void setType(short type) {
		this.type = type;
	}
}

