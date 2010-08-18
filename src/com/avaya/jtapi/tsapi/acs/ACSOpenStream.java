package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNOctetString;

public final class ACSOpenStream extends ACSRequest {
	public static ACSOpenStream decode(final InputStream in) {
		final ACSOpenStream _this = new ACSOpenStream();
		_this.doDecode(in);

		return _this;
	}

	short streamType;
	String serverID;
	String loginID;
	byte[] cryptPass;
	String applicationName;
	short level;
	String apiVer;
	String libVer;
	String tsrvVer;

	public static final int PDU = 1;

	public ACSOpenStream() {
	}

	public ACSOpenStream(final short _streamType, final String _serverID,
			final String _loginID, final byte[] _cryptPass,
			final String _applicationName, final short _level,
			final String _apiVer, final String _libVer, final String _tsrvVer) {
		streamType = _streamType;
		serverID = _serverID;
		loginID = _loginID;
		cryptPass = _cryptPass;
		applicationName = _applicationName;
		level = _level;
		apiVer = _apiVer;
		libVer = _libVer;
		tsrvVer = _tsrvVer;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		streamType = ASNEnumerated.decode(memberStream);
		serverID = ASNIA5String.decode(memberStream);
		loginID = ASNIA5String.decode(memberStream);
		cryptPass = ASNOctetString.decode(memberStream);
		applicationName = ASNIA5String.decode(memberStream);
		level = ASNEnumerated.decode(memberStream);
		apiVer = ASNIA5String.decode(memberStream);
		libVer = ASNIA5String.decode(memberStream);
		tsrvVer = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(streamType, memberStream);
		ASNIA5String.encode(serverID, memberStream);
		ASNIA5String.encode(loginID, memberStream);
		ASNOctetString.encode(cryptPass, memberStream);
		ASNIA5String.encode(applicationName, memberStream);
		ASNEnumerated.encode(level, memberStream);
		ASNIA5String.encode(apiVer, memberStream);
		ASNIA5String.encode(libVer, memberStream);
		ASNIA5String.encode(tsrvVer, memberStream);
	}

	public synchronized String getApiVer() {
		return apiVer;
	}

	public synchronized String getApplicationName() {
		return applicationName;
	}

	public synchronized byte[] getCryptPass() {
		return cryptPass;
	}

	public synchronized short getLevel() {
		return level;
	}

	public synchronized String getLibVer() {
		return libVer;
	}

	public synchronized String getLoginID() {
		return loginID;
	}

	@Override
	public int getPDU() {
		return 1;
	}

	public synchronized String getServerID() {
		return serverID;
	}

	public synchronized short getStreamType() {
		return streamType;
	}

	public synchronized String getTsrvVer() {
		return tsrvVer;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("ACSOpenStream ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(StreamType.print(streamType, "streamType", indent));
		lines.addAll(ASNIA5String.print(serverID, "serverID", indent));
		lines.addAll(ASNIA5String.print(loginID, "loginID", indent));
		lines.addAll(ASNOctetString.print(cryptPass, "cryptPass", indent));
		lines.addAll(ASNIA5String.print(applicationName, "applicationName",
				indent));
		lines.addAll(Level.print(level, "level", indent));
		lines.addAll(ASNIA5String.print(apiVer, "apiVer", indent));
		lines.addAll(ASNIA5String.print(libVer, "libVer", indent));
		lines.addAll(ASNIA5String.print(tsrvVer, "tsrvVer", indent));

		lines.add("}");
		return lines;
	}
}
