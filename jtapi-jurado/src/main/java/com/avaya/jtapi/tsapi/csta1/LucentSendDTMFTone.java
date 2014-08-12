package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentSendDTMFTone extends LucentPrivateData {
	CSTAConnectionID sender;
	CSTAConnectionID[] receivers;
	String tones;
	int toneDuration;
	int pauseDuration;
	static final int PDU = 8;

	public LucentSendDTMFTone() {
	}

	public LucentSendDTMFTone(CSTAConnectionID _sender,
			CSTAConnectionID[] _receivers, String _tones, int _toneDuration,
			int _pauseDuration) {
		this.sender = _sender;
		this.receivers = _receivers;
		this.tones = _tones;
		this.toneDuration = _toneDuration;
		this.pauseDuration = _pauseDuration;
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.sender, memberStream);
		LucentConnIDList.encode(this.receivers, memberStream);
		ASNIA5String.encode(this.tones, memberStream);
		ASNInteger.encode(this.toneDuration, memberStream);
		ASNInteger.encode(this.pauseDuration, memberStream);
	}

	public static LucentSendDTMFTone decode(InputStream in) {
		LucentSendDTMFTone _this = new LucentSendDTMFTone();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.sender = CSTAConnectionID.decode(memberStream);
		this.receivers = LucentConnIDList.decode(memberStream);
		this.tones = ASNIA5String.decode(memberStream);
		this.toneDuration = ASNInteger.decode(memberStream);
		this.pauseDuration = ASNInteger.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSendDTMFTone ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.sender, "sender", indent));
		lines.addAll(LucentConnIDList
				.print(this.receivers, "receivers", indent));
		lines.addAll(ASNIA5String.print(this.tones, "tones", indent));
		lines.addAll(ASNInteger
				.print(this.toneDuration, "toneDuration", indent));
		lines.addAll(ASNInteger.print(this.pauseDuration, "pauseDuration",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 8;
	}

	public int getPauseDuration() {
		return this.pauseDuration;
	}

	public CSTAConnectionID[] getReceivers() {
		return this.receivers;
	}

	public CSTAConnectionID getSender() {
		return this.sender;
	}

	public int getToneDuration() {
		return this.toneDuration;
	}

	public String getTones() {
		return this.tones;
	}
}