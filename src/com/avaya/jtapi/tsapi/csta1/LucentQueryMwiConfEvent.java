package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public final class LucentQueryMwiConfEvent extends LucentPrivateData {
	public static LucentQueryMwiConfEvent decode(InputStream in) {
		LucentQueryMwiConfEvent _this = new LucentQueryMwiConfEvent();
		_this.doDecode(in);

		return _this;
	}

	int applicationType;

	static final int PDU = 21;

	@Override
	public void decodeMembers(InputStream memberStream) {
		applicationType = ASNBitString.decode(memberStream);
	}

	public int getApplicationType() {
		return applicationType;
	}

	@Override
	public int getPDU() {
		return 21;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentQueryMwiConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentMwiApplication.print(applicationType,
				"applicationType", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentQueryMwiConfEvent JD-Core Version: 0.5.4
 */