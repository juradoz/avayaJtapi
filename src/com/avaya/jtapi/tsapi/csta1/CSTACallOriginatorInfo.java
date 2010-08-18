package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTACallOriginatorInfo extends ASNSequence {
	public static CSTACallOriginatorInfo decode(final InputStream in) {
		final CSTACallOriginatorInfo _this = new CSTACallOriginatorInfo();
		_this.doDecode(in);

		if (_this.hasInfo)
			return _this;
		return null;
	}

	public static Collection<String> print(final CSTACallOriginatorInfo _this,
			final String name, final String _indent) {
		final Collection<String> lines = new ArrayList<String>();

		if (_this == null) {
			lines.add(_indent + name + " <none>");
			return lines;
		}
		if (name != null)
			lines.add(_indent + name);
		lines.add(_indent + "{");

		final String indent = _indent + "  ";

		lines.addAll(ASNBoolean.print(_this.hasInfo, "hasInfo", indent));
		lines.addAll(ASNInteger.print(_this.callOriginatorType,
				"callOriginatorType", indent));

		lines.add(_indent + "}");
		return lines;
	}

	boolean hasInfo;

	int callOriginatorType;

	@Override
	public void decodeMembers(final InputStream memberStream) {
		hasInfo = ASNBoolean.decode(memberStream);
		callOriginatorType = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNBoolean.encode(hasInfo, memberStream);
		ASNInteger.encode(callOriginatorType, memberStream);
	}

	public int getCallOriginatorType() {
		return callOriginatorType;
	}

	public boolean isHasInfo() {
		return hasInfo;
	}

	public void setCallOriginatorType(final int _callOriginatorType) {
		callOriginatorType = _callOriginatorType;
	}

	public void setHasInfo(final boolean hasInfo) {
		this.hasInfo = hasInfo;
	}
}
