package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class LucentNetworkProgressInfo extends LucentPrivateData {
	public static final short PL_USER = 0;
	public static final short PL_PUB_LOCAL = 1;
	public static final short PL_PUB_REMOTE = 4;
	public static final short PL_PRIV_REMOTE = 5;
	public static final short PD_CALL_OFF_ISDN = 1;
	public static final short PD_DEST_NOT_ISDN = 2;
	public static final short PD_ORIG_NOT_ISDN = 3;
	public static final short PD_CALL_ON_ISDN = 4;
	public static final short PD_INBAND = 8;
	public short progressLocation;
	public short progressDescription;
	static final int PDU = 40;

	static LucentNetworkProgressInfo decode(InputStream in) {
		LucentNetworkProgressInfo _this = new LucentNetworkProgressInfo();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		progressLocation = ASNEnumerated.decode(memberStream);
		progressDescription = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(progressLocation, memberStream);
		ASNEnumerated.encode(progressDescription, memberStream);
	}

	@Override
	public int getPDU() {
		return 40;
	}

	public short getProgressDescription() {
		return progressDescription;
	}

	public short getProgressLocation() {
		return progressLocation;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("NetworkProgressInfo ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ProgressLocation.print(progressLocation,
				"progressLocation", indent));
		lines.addAll(ProgressDescription.print(progressDescription,
				"progressDescription", indent));

		lines.add("}");
		return lines;
	}

	public void setProgressDescription(short progressDescription) {
		this.progressDescription = progressDescription;
	}

	public void setProgressLocation(short progressLocation) {
		this.progressLocation = progressLocation;
	}
}

