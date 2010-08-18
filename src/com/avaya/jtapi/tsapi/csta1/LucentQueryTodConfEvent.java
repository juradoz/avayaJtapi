package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentQueryTodConfEvent extends LucentPrivateData {
	int year;
	int month;
	int day;
	int hour;
	int minute;
	int second;
	static final int PDU = 25;

	public static LucentQueryTodConfEvent decode(InputStream in) {
		LucentQueryTodConfEvent _this = new LucentQueryTodConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		year = ASNInteger.decode(memberStream);
		month = ASNInteger.decode(memberStream);
		day = ASNInteger.decode(memberStream);
		hour = ASNInteger.decode(memberStream);
		minute = ASNInteger.decode(memberStream);
		second = ASNInteger.decode(memberStream);
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getMonth() {
		return month;
	}

	@Override
	public int getPDU() {
		return 25;
	}

	public int getSecond() {
		return second;
	}

	public int getYear() {
		return year;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryTodConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(year, "year", indent));
		lines.addAll(ASNInteger.print(month, "month", indent));
		lines.addAll(ASNInteger.print(day, "day", indent));
		lines.addAll(ASNInteger.print(hour, "hour", indent));
		lines.addAll(ASNInteger.print(minute, "minute", indent));
		lines.addAll(ASNInteger.print(second, "second", indent));

		lines.add("}");
		return lines;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public void setYear(int year) {
		this.year = year;
	}
}

