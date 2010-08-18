package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class ACSNameAddr extends ASNSequence {
	public static ACSNameAddr decode(InputStream in) {
		ACSNameAddr _this = new ACSNameAddr();
		_this.doDecode(in);

		return _this;
	}

	public static Collection<String> print(ACSNameAddr _this, String name,
			String _indent) {
		Collection<String> lines = new ArrayList<String>();
		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null) {
			lines.add(_indent + name);
		}
		lines.add(_indent + "{");

		String indent = _indent + "  ";

		lines
				.addAll(ASNIA5String.print(_this.serverName, "serverName",
						indent));
		lines.addAll(ASNOctetString.print(_this.serverAddr, "serverAddr",
				indent));

		lines.add(_indent + "}");
		return lines;
	}

	String serverName;

	byte[] serverAddr;

	public ACSNameAddr() {
	}

	public ACSNameAddr(String _serverName, byte[] _serverAddr) {
		serverName = _serverName;
		serverAddr = _serverAddr;
	}

	public InetSocketAddress createInetSocketAddress() {
		String hostname = (serverAddr[4] & 0xFF) + "." + (serverAddr[5] & 0xFF)
				+ "." + (serverAddr[6] & 0xFF) + "." + (serverAddr[7] & 0xFF);

		int port = ((serverAddr[2] & 0xFF) << 8) + (serverAddr[3] & 0xFF);

		return new InetSocketAddress(hostname, port);
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		serverName = ASNIA5String.decode(memberStream);
		serverAddr = ASNOctetString.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(serverName, memberStream);
		ASNOctetString.encode(serverAddr, memberStream);
	}

	public byte[] getServerAddr() {
		return serverAddr;
	}

	public String getServerName() {
		return serverName;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.acs.ACSNameAddr JD-Core Version: 0.5.4
 */