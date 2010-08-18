package com.avaya.jtapi.tsapi;

import javax.telephony.callcenter.ACDManagerAddress;

public final class UserEnteredCode {
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
	ACDManagerAddress collectVDN;

	public ACDManagerAddress getCollectVDN() {
		return collectVDN;
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

	public void setCollectVDN(ACDManagerAddress _collectVDN) {
		collectVDN = _collectVDN;
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

