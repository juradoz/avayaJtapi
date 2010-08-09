package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public final class LucentSelectiveListeningHold extends LucentPrivateData {
	public static LucentSelectiveListeningHold decode(InputStream in) {
		LucentSelectiveListeningHold _this = new LucentSelectiveListeningHold();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID subjectConnection;
	boolean allParties;
	CSTAConnectionID selectedParty;

	public static final int PDU = 67;

	public LucentSelectiveListeningHold() {
	}

	public LucentSelectiveListeningHold(CSTAConnectionID _subjectConnection,
			boolean _allParties, CSTAConnectionID _selectedParty) {
		subjectConnection = _subjectConnection;
		allParties = _allParties;
		selectedParty = _selectedParty;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		subjectConnection = CSTAConnectionID.decode(memberStream);
		allParties = ASNBoolean.decode(memberStream);
		selectedParty = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(subjectConnection, memberStream);
		ASNBoolean.encode(allParties, memberStream);
		CSTAConnectionID.encode(selectedParty, memberStream);
	}

	@Override
	public int getPDU() {
		return 67;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentSelectiveListeningHold ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(subjectConnection,
				"subjectConnection", indent));
		lines.addAll(ASNBoolean.print(allParties, "allParties", indent));
		lines.addAll(CSTAConnectionID.print(selectedParty, "selectedParty",
				indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentSelectiveListeningHold JD-Core Version:
 * 0.5.4
 */