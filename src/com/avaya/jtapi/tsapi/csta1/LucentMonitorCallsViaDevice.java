package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public class LucentMonitorCallsViaDevice extends LucentPrivateData {
	boolean flowPredictiveCallEvents;
	int privateFilter;
	static final int PDU = 144;

	public LucentMonitorCallsViaDevice(final boolean _flowPredictiveCallEvents,
			final int _privateFilter) {
		flowPredictiveCallEvents = _flowPredictiveCallEvents;
		privateFilter = _privateFilter;
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		LucentPrivateFilter.encode(privateFilter, memberStream);
		ASNBoolean.encode(flowPredictiveCallEvents, memberStream);
	}

	@Override
	public int getPDU() {
		return 144;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentMonitorCallsViaDevice ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentPrivateFilter.print(privateFilter, "privateFilter",
				indent));
		lines.addAll(ASNBoolean.print(flowPredictiveCallEvents,
				"flowPredictiveCallEvents", indent));

		lines.add("}");
		return lines;
	}
}
