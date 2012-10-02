package com.avaya.jtapi.tsapi.acs;

import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSNameAddr extends ASNSequence {
	String serverName;
	byte[] serverAddr;

	public ACSNameAddr() {
	}

	public ACSNameAddr(String _serverName, byte[] _serverAddr) {
		this.serverName = _serverName;
		this.serverAddr = _serverAddr;
	}

	public static ACSNameAddr decode(InputStream in) {
		ACSNameAddr _this = new ACSNameAddr();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.serverName = ServerID.decode(memberStream);
		this.serverAddr = ASNOctetString.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		ServerID.encode(this.serverName, memberStream);
		ASNOctetString.encode(this.serverAddr, memberStream);
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

		lines.addAll(ServerID.print(_this.serverName, "serverName", indent));
		lines.addAll(ASNOctetString.print(_this.serverAddr, "serverAddr",
				indent));

		lines.add(_indent + "}");
		return lines;
	}

	public byte[] getServerAddr() {
		return this.serverAddr;
	}

	public String getServerName() {
		return this.serverName;
	}

	public InetSocketAddress createInetSocketAddress()
			throws UnknownHostException {
		String hostname = null;
		InetSocketAddress inetSocketAddress = null;
		int port = ((this.serverAddr[2] & 0xFF) << 8)
				+ (this.serverAddr[3] & 0xFF);

		int firstValue = this.serverAddr[0] & 0xFF;
		int secondValue = this.serverAddr[1] & 0xFF;

		if ((firstValue == 10) && (secondValue == 0)) {
			byte[] ipv6 = new byte[16];
			System.arraycopy(this.serverAddr, 8, ipv6, 0, 16);
			InetAddress inetAddr = InetAddress.getByAddress(ipv6);
			inetSocketAddress = new InetSocketAddress(inetAddr, port);
		} else {
			hostname = (this.serverAddr[4] & 0xFF) + "."
					+ (this.serverAddr[5] & 0xFF) + "."
					+ (this.serverAddr[6] & 0xFF) + "."
					+ (this.serverAddr[7] & 0xFF);

			inetSocketAddress = new InetSocketAddress(hostname, port);
		}

		return inetSocketAddress;
	}
}