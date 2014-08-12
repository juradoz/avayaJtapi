package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentLookaheadInfo extends LucentPrivateData {
	public static final short LAI_ALL_INTERFLOW = 0;
	public static final short LAI_THRESHOLD_INTERFLOW = 1;
	public static final short LAI_VECTORING_INTERFLOW = 2;
	public static final short LAI_NOT_IN_QUEUE = 0;
	public static final short LAI_LOW = 1;
	public static final short LAI_MEDIUM = 2;
	public static final short LAI_HIGH = 3;
	public static final short LAI_TOP = 4;
	private short type;
	private short priority;
	private int hours;
	private int minutes;
	private int seconds;
	protected String sourceVDN;

	public short getType() {
		return this.type;
	}

	public short getPriority() {
		return this.priority;
	}

	public int getHours() {
		return this.hours;
	}

	public int getMinutes() {
		return this.minutes;
	}

	public int getSeconds() {
		return this.seconds;
	}

	public String getSourceVDN() {
		return this.sourceVDN;
	}

	public void setSourceVDN(String _sourceVDN) {
		this.sourceVDN = _sourceVDN;
	}

	public static LucentLookaheadInfo decode(InputStream in) {
		LucentLookaheadInfo _this = new LucentLookaheadInfo();
		_this.doDecode(in);
		if (_this.type == -1) {
			return null;
		}
		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
		Interflow.encode(this.type, memberStream);
		Priority.encode(this.priority, memberStream);
		ASNInteger.encode(this.hours, memberStream);
		ASNInteger.encode(this.minutes, memberStream);
		ASNInteger.encode(this.seconds, memberStream);
		DeviceID.encode(this.sourceVDN, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		this.type = Interflow.decode(memberStream);
		this.priority = Priority.decode(memberStream);
		this.hours = ASNInteger.decode(memberStream);
		this.minutes = ASNInteger.decode(memberStream);
		this.seconds = ASNInteger.decode(memberStream);
		this.sourceVDN = DeviceID.decode(memberStream);
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
		lines.addAll(DeviceID.print(_this.sourceVDN, "sourceVDN", indent));

		lines.add(_indent + "}");
		return lines;
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

	public void setType(short type) {
		this.type = type;
	}
}