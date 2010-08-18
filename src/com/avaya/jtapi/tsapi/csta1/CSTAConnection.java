package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAConnection extends ASNSequence {
	public static CSTAConnection decode(final InputStream in) {
		final CSTAConnection _this = new CSTAConnection();
		_this.doDecode(in);

		return _this;
	}

	public static Collection<String> print(final CSTAConnection _this,
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

		lines.addAll(CSTAConnectionID.print(_this.party, "party", indent));
		lines.addAll(CSTAExtendedDeviceID.print(_this.staticDevice,
				"staticDevice", indent));

		lines.add(_indent + "}");
		return lines;
	}

	CSTAConnectionID party;

	CSTAExtendedDeviceID staticDevice;

	public CSTAConnection() {
	}

	public CSTAConnection(final CSTAConnectionID _party,
			final CSTAExtendedDeviceID _staticDevice) {
		party = _party;
		staticDevice = _staticDevice;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		party = CSTAConnectionID.decode(memberStream);
		staticDevice = CSTAExtendedDeviceID.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(party, memberStream);
		ASNSequence.encode(staticDevice, memberStream);
	}

	public CSTAConnectionID getParty() {
		return party;
	}

	public CSTAExtendedDeviceID getStaticDevice() {
		return staticDevice;
	}

	public void setParty(final CSTAConnectionID _party) {
		party = _party;
	}

	public void setStaticDevice(final CSTAExtendedDeviceID _staticDevice) {
		staticDevice = _staticDevice;
	}
}
