package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public class LucentSendDTMFTone extends LucentPrivateData {
	public static LucentSendDTMFTone decode(InputStream in) {
		LucentSendDTMFTone _this = new LucentSendDTMFTone();
		_this.doDecode(in);

		return _this;
	}

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
		sender = _sender;
		receivers = _receivers;
		tones = _tones;
		toneDuration = _toneDuration;
		pauseDuration = _pauseDuration;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		sender = CSTAConnectionID.decode(memberStream);
		receivers = LucentConnIDList.decode(memberStream);
		tones = ASNIA5String.decode(memberStream);
		toneDuration = ASNInteger.decode(memberStream);
		pauseDuration = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(sender, memberStream);
		LucentConnIDList.encode(receivers, memberStream);
		ASNIA5String.encode(tones, memberStream);
		ASNInteger.encode(toneDuration, memberStream);
		ASNInteger.encode(pauseDuration, memberStream);
	}

	public int getPauseDuration() {
		return pauseDuration;
	}

	@Override
	public int getPDU() {
		return 8;
	}

	public CSTAConnectionID[] getReceivers() {
		return receivers;
	}

	public CSTAConnectionID getSender() {
		return sender;
	}

	public int getToneDuration() {
		return toneDuration;
	}

	public String getTones() {
		return tones;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentSendDTMFTone ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(sender, "sender", indent));
		lines.addAll(LucentConnIDList.print(receivers, "receivers", indent));
		lines.addAll(ASNIA5String.print(tones, "tones", indent));
		lines.addAll(ASNInteger.print(toneDuration, "toneDuration", indent));
		lines.addAll(ASNInteger.print(pauseDuration, "pauseDuration", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentSendDTMFTone JD-Core Version: 0.5.4
 */