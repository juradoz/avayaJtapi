package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentSelectiveListeningRetrieve extends LucentPrivateData {
	CSTAConnectionID subjectConnection;
	boolean allParties;
	CSTAConnectionID selectedParty;
	public static final int PDU = 69;

	public LucentSelectiveListeningRetrieve() {
	}

	public LucentSelectiveListeningRetrieve(
			CSTAConnectionID _subjectConnection, boolean _allParties,
			CSTAConnectionID _selectedParty) {
		this.subjectConnection = _subjectConnection;
		this.allParties = _allParties;
		this.selectedParty = _selectedParty;
	}

	public static LucentSelectiveListeningRetrieve decode(InputStream in) {
		LucentSelectiveListeningRetrieve _this = new LucentSelectiveListeningRetrieve();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.subjectConnection = CSTAConnectionID.decode(memberStream);
		this.allParties = ASNBoolean.decode(memberStream);
		this.selectedParty = CSTAConnectionID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.subjectConnection, memberStream);
		ASNBoolean.encode(this.allParties, memberStream);
		CSTAConnectionID.encode(this.selectedParty, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSelectiveListeningRetrieve ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.subjectConnection,
				"subjectConnection", indent));
		lines.addAll(ASNBoolean.print(this.allParties, "allParties", indent));
		lines.addAll(CSTAConnectionID.print(this.selectedParty,
				"selectedParty", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 69;
	}
}