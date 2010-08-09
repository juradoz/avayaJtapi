package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTATrunkInfo extends ASNSequence {
	public static CSTATrunkInfo decode(InputStream in) {
		CSTATrunkInfo _this = new CSTATrunkInfo();
		_this.doDecode(in);
		return _this;
	}

	public static Collection<String> print(CSTATrunkInfo _this, String name,
			String _indent) {
		Collection lines = new ArrayList();

		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null) {
			lines.add(_indent + name);
		}
		lines.add(_indent + "{");

		String indent = _indent + "  ";

		lines.addAll(CSTAConnectionID.print(_this.connection_asn, "connection",
				indent));
		lines
				.addAll(ASNIA5String.print(_this.trunkGroup, "trunkGroup",
						indent));
		lines.addAll(ASNIA5String.print(_this.trunkMember, "trunkMember",
				indent));

		lines.add(_indent + "}");
		return lines;
	}

	CSTAConnectionID connection_asn;

	String trunkGroup;

	String trunkMember;

	@Override
	public void decodeMembers(InputStream memberStream) {
		connection_asn = CSTAConnectionID.decode(memberStream);
		trunkGroup = ASNIA5String.decode(memberStream);
		trunkMember = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(connection_asn, memberStream);
		ASNIA5String.encode(trunkGroup, memberStream);
		ASNIA5String.encode(trunkMember, memberStream);
	}

	public CSTAConnectionID getConnection_asn() {
		return connection_asn;
	}

	public String getTrunkGroup() {
		return trunkGroup;
	}

	public String getTrunkMember() {
		return trunkMember;
	}

	public void setConnection_asn(CSTAConnectionID _connection_asn) {
		connection_asn = _connection_asn;
	}

	public void setTrunkGroup(String _trunkGroup) {
		trunkGroup = _trunkGroup;
	}

	public void setTrunkMember(String _trunkMember) {
		trunkMember = _trunkMember;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTATrunkInfo JD-Core Version: 0.5.4
 */