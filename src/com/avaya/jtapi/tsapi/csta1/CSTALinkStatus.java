package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public class CSTALinkStatus extends ASNSequence {
	static CSTALinkStatus decode(InputStream in) {
		CSTALinkStatus _this = new CSTALinkStatus();
		_this.doDecode(in);

		return _this;
	}

	public static Collection<String> print(CSTALinkStatus _this, String name,
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

		lines.addAll(ASNInteger.print(_this.linkID, "linkID", indent));
		lines.addAll(LinkState.print(_this.linkState, "linkState", indent));

		lines.add(_indent + "}");
		return lines;
	}

	private int linkID;

	private short linkState;

	@Override
	public void decodeMembers(InputStream memberStream) {
		linkID = ASNInteger.decode(memberStream);
		linkState = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(linkID, memberStream);
		ASNEnumerated.encode(linkState, memberStream);
	}

	public int getLinkID() {
		return linkID;
	}

	public short getLinkState() {
		return linkState;
	}

	public void setLinkID(int linkID) {
		this.linkID = linkID;
	}

	public void setLinkState(short linkState) {
		this.linkState = linkState;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTALinkStatus JD-Core Version: 0.5.4
 */