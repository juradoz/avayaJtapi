package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class CSTAGroupPickupCall extends CSTARequest {
	CSTAConnectionID deflectCall;
	String pickupDevice;
	static final int PDU = 19;

	public CSTAGroupPickupCall(CSTAConnectionID _deflectCall,
			String _pickupDevice) {
		deflectCall = _deflectCall;
		pickupDevice = _pickupDevice;
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();
		lines.add("CSTAGroupPickupCall ::=");
		lines.add("{");

		String indent = "  ";

		lines
				.addAll(CSTAConnectionID.print(deflectCall, "deflectCall",
						indent));
		lines.addAll(ASNIA5String.print(pickupDevice, "pickupDevice", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAGroupPickupCall JD-Core Version: 0.5.4
 */