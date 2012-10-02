package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSOpenStreamConfEvent extends ACSConfirmation {
	String apiVer;
	String libVer;
	String tsrvVer;
	String drvrVer;
	public static final int PDU = 2;

	public ACSOpenStreamConfEvent() {
	}

	public ACSOpenStreamConfEvent(String _apiVer, String _libVer,
			String _tsrvVer, String _drvrVer) {
		this.apiVer = _apiVer;
		this.libVer = _libVer;
		this.tsrvVer = _tsrvVer;
		this.drvrVer = _drvrVer;
	}

	public static ACSOpenStreamConfEvent decode(InputStream in) {
		ACSOpenStreamConfEvent _this = new ACSOpenStreamConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
		Version.encode(this.apiVer, memberStream);
		Version.encode(this.libVer, memberStream);
		Version.encode(this.tsrvVer, memberStream);
		Version.encode(this.drvrVer, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		this.apiVer = Version.decode(memberStream);
		this.libVer = Version.decode(memberStream);
		this.tsrvVer = Version.decode(memberStream);
		this.drvrVer = Version.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSOpenStreamConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(Version.print(this.apiVer, "apiVer", indent));
		lines.addAll(Version.print(this.libVer, "libVer", indent));
		lines.addAll(Version.print(this.tsrvVer, "tsrvVer", indent));
		lines.addAll(Version.print(this.drvrVer, "drvrVer", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 2;
	}

	public String getApiVer() {
		return this.apiVer;
	}

	public String getDrvrVer() {
		return this.drvrVer;
	}

	public String getLibVer() {
		return this.libVer;
	}

	public String getTsrvVer() {
		return this.tsrvVer;
	}
}