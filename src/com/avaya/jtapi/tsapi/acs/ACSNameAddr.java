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
	public static ACSNameAddr decode(final InputStream in) {
		final ACSNameAddr _this = new ACSNameAddr();
		_this.doDecode(in);

		return _this;
	}

	public static Collection<String> print(final ACSNameAddr _this,
			final String name, final String _indent) {
		final Collection<String> lines = new ArrayList<String>();
		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null)
			lines.add(_indent + name);
		lines.add(_indent + "{");

		final String indent = _indent + "  ";

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

	public ACSNameAddr(final String _serverName, final byte[] _serverAddr) {
		serverName = _serverName;
		serverAddr = _serverAddr;
	}

	public InetSocketAddress createInetSocketAddress() {
		final String hostname = (serverAddr[4] & 0xFF) + "."
				+ (serverAddr[5] & 0xFF) + "." + (serverAddr[6] & 0xFF) + "."
				+ (serverAddr[7] & 0xFF);

		final int port = ((serverAddr[2] & 0xFF) << 8) + (serverAddr[3] & 0xFF);

		return new InetSocketAddress(hostname, port);
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		serverName = ASNIA5String.decode(memberStream);
		serverAddr = ASNOctetString.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
