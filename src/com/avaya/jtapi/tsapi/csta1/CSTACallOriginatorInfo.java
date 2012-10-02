package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTACallOriginatorInfo extends ASNSequence {
	boolean hasInfo;
	int callOriginatorType;

	public static CSTACallOriginatorInfo decode(InputStream in) {
		CSTACallOriginatorInfo _this = new CSTACallOriginatorInfo();
		_this.doDecode(in);

		if (_this.hasInfo) {
			return _this;
		}
		return null;
	}

	public void encodeMembers(OutputStream memberStream) {
		ASNBoolean.encode(this.hasInfo, memberStream);
		ASNInteger.encode(this.callOriginatorType, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		this.hasInfo = ASNBoolean.decode(memberStream);
		this.callOriginatorType = ASNInteger.decode(memberStream);
	}

	public static Collection<String> print(CSTACallOriginatorInfo _this,
			String name, String _indent) {
		Collection<String> lines = new ArrayList<String>();

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

	public int getCallOriginatorType() {
		return this.callOriginatorType;
	}

	public void setCallOriginatorType(int _callOriginatorType) {
		this.callOriginatorType = _callOriginatorType;
	}

	public void setHasInfo(boolean hasInfo) {
		this.hasInfo = hasInfo;
	}

	public boolean isHasInfo() {
		return this.hasInfo;
	}
}