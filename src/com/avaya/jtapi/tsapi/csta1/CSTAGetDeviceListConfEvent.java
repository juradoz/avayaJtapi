package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class CSTAGetDeviceListConfEvent extends CSTAConfirmation {
	short driverSdbLevel;
	short level;
	int index;
	String[] devList;
	public static final int PDU = 127;
	public static final int NO_SDB_CHECKING = -1;
	public static final int ACS_ONLY = 1;

	public static CSTAGetDeviceListConfEvent decode(InputStream in) {
		CSTAGetDeviceListConfEvent _this = new CSTAGetDeviceListConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		driverSdbLevel = ASNEnumerated.decode(memberStream);
		level = ASNEnumerated.decode(memberStream);
		index = ASNInteger.decode(memberStream);
		devList = DeviceList.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(driverSdbLevel, memberStream);
		ASNEnumerated.encode(level, memberStream);
		ASNInteger.encode(index, memberStream);
		DeviceList.encode(devList, memberStream);
	}

	public String[] getDevList() {
		return devList;
	}

	public short getDriverSdbLevel() {
		return driverSdbLevel;
	}

	public int getIndex() {
		return index;
	}

	public short getLevel() {
		return level;
	}

	@Override
	public int getPDU() {
		return 127;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();
		lines.add("CSTAGetDeviceListConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(SDBLevel.print(driverSdbLevel, "driverSdbLevel", indent));
		lines.addAll(CSTALevel.print(level, "level", indent));
		lines.addAll(ASNInteger.print(index, "index", indent));
		lines.addAll(DeviceList.print(devList, "devList", indent));

		lines.add("}");
		return lines;
	}

	public void setDevList(String[] devList) {
		this.devList = devList;
	}

	public void setDriverSdbLevel(short driverSdbLevel) {
		this.driverSdbLevel = driverSdbLevel;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setLevel(short level) {
		this.level = level;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAGetDeviceListConfEvent JD-Core Version: 0.5.4
 */