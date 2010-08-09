package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTACallOriginatorInfo extends ASNSequence {
	public static CSTACallOriginatorInfo decode(InputStream in) {
		CSTACallOriginatorInfo _this = new CSTACallOriginatorInfo();
		_this.doDecode(in);

		if (_this.hasInfo) {
			return _this;
		}
		return null;
	}

	public static Collection<String> print(CSTACallOriginatorInfo _this,
			String name, String _indent) {
		Collection lines = new ArrayList();

		if (_this == null) {
			lines.add(_indent + name + " <none>");
			return lines;
		}
		if (name != null) {
			lines.add(_indent + name);
		}
		lines.add(_indent + "{");

		String indent = _indent + "  ";

		lines.addAll(ASNBoolean.print(_this.hasInfo, "hasInfo", indent));
		lines.addAll(ASNInteger.print(_this.callOriginatorType,
				"callOriginatorType", indent));

		lines.add(_indent + "}");
		return lines;
	}

	boolean hasInfo;

	int callOriginatorType;

	@Override
	public void decodeMembers(InputStream memberStream) {
		hasInfo = ASNBoolean.decode(memberStream);
		callOriginatorType = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNBoolean.encode(hasInfo, memberStream);
		ASNInteger.encode(callOriginatorType, memberStream);
	}

	public int getCallOriginatorType() {
		return callOriginatorType;
	}

	public boolean isHasInfo() {
		return hasInfo;
	}

	public void setCallOriginatorType(int _callOriginatorType) {
		callOriginatorType = _callOriginatorType;
	}

	public void setHasInfo(boolean hasInfo) {
		this.hasInfo = hasInfo;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTACallOriginatorInfo JD-Core Version: 0.5.4
 */