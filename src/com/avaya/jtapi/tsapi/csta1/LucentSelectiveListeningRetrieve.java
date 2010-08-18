package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public final class LucentSelectiveListeningRetrieve extends LucentPrivateData {
	public static LucentSelectiveListeningRetrieve decode(final InputStream in) {
		final LucentSelectiveListeningRetrieve _this = new LucentSelectiveListeningRetrieve();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID subjectConnection;
	boolean allParties;
	CSTAConnectionID selectedParty;

	public static final int PDU = 69;

	public LucentSelectiveListeningRetrieve() {
	}

	public LucentSelectiveListeningRetrieve(
			final CSTAConnectionID _subjectConnection,
			final boolean _allParties, final CSTAConnectionID _selectedParty) {
		subjectConnection = _subjectConnection;
		allParties = _allParties;
		selectedParty = _selectedParty;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		subjectConnection = CSTAConnectionID.decode(memberStream);
		allParties = ASNBoolean.decode(memberStream);
		selectedParty = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(subjectConnection, memberStream);
		ASNBoolean.encode(allParties, memberStream);
		CSTAConnectionID.encode(selectedParty, memberStream);
	}

	@Override
	public int getPDU() {
		return 69;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSelectiveListeningRetrieve ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(subjectConnection,
				"subjectConnection", indent));
		lines.addAll(ASNBoolean.print(allParties, "allParties", indent));
		lines.addAll(CSTAConnectionID.print(selectedParty, "selectedParty",
				indent));

		lines.add("}");
		return lines;
	}
}
