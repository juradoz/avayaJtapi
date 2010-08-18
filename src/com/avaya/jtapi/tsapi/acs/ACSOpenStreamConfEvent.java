package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class ACSOpenStreamConfEvent extends ACSConfirmation {
	String apiVer;
	String libVer;
	String tsrvVer;
	String drvrVer;
	public static final int PDU = 2;

	public static ACSOpenStreamConfEvent decode(final InputStream in) {
		final ACSOpenStreamConfEvent _this = new ACSOpenStreamConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public ACSOpenStreamConfEvent() {
	}

	public ACSOpenStreamConfEvent(final String _apiVer, final String _libVer,
			final String _tsrvVer, final String _drvrVer) {
		apiVer = _apiVer;
		libVer = _libVer;
		tsrvVer = _tsrvVer;
		drvrVer = _drvrVer;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		apiVer = ASNIA5String.decode(memberStream);
		libVer = ASNIA5String.decode(memberStream);
		tsrvVer = ASNIA5String.decode(memberStream);
		drvrVer = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(apiVer, memberStream);
		ASNIA5String.encode(libVer, memberStream);
		ASNIA5String.encode(tsrvVer, memberStream);
		ASNIA5String.encode(drvrVer, memberStream);
	}

	public String getApiVer() {
		return apiVer;
	}

	public String getDrvrVer() {
		return drvrVer;
	}

	public String getLibVer() {
		return libVer;
	}

	@Override
	public int getPDU() {
		return 2;
	}

	public String getTsrvVer() {
		return tsrvVer;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("ACSOpenStreamConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(apiVer, "apiVer", indent));
		lines.addAll(ASNIA5String.print(libVer, "libVer", indent));
		lines.addAll(ASNIA5String.print(tsrvVer, "tsrvVer", indent));
		lines.addAll(ASNIA5String.print(drvrVer, "drvrVer", indent));

		lines.add("}");
		return lines;
	}
}
