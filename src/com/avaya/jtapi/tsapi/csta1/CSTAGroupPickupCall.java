package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class CSTAGroupPickupCall extends CSTARequest {
	CSTAConnectionID deflectCall;
	String pickupDevice;
	static final int PDU = 19;

	public CSTAGroupPickupCall(final CSTAConnectionID _deflectCall,
			final String _pickupDevice) {
		deflectCall = _deflectCall;
		pickupDevice = _pickupDevice;
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(deflectCall, memberStream);
		ASNIA5String.encode(pickupDevice, memberStream);
	}

	public CSTAConnectionID getDeflectCall() {
		return deflectCall;
	}

	@Override
	public int getPDU() {
		return 19;
	}

	public String getPickupDevice() {
		return pickupDevice;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAGroupPickupCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines
				.addAll(CSTAConnectionID.print(deflectCall, "deflectCall",
						indent));
		lines.addAll(ASNIA5String.print(pickupDevice, "pickupDevice", indent));

		lines.add("}");
		return lines;
	}
}
