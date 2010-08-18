package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAMonitorFilter extends ASNSequence {
	public static CSTAMonitorFilter decode(InputStream in) {
		CSTAMonitorFilter _this = new CSTAMonitorFilter();
		_this.doDecode(in);

		return _this;
	}

	public static Collection<String> print(CSTAMonitorFilter _this,
			String name, String _indent) {
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

		lines.addAll(CSTACallFilter.print(_this.call, "call", indent));
		lines.addAll(CSTAFeatureFilter.print(_this.feature, "feature", indent));
		lines.addAll(CSTAAgentFilter.print(_this.agent, "agent", indent));
		lines.addAll(CSTAMaintenanceFilter.print(_this.maintenance,
				"maintenance", indent));
		lines.addAll(ASNInteger.print(_this.privateFilter, "privateFilter",
				indent));

		lines.add(_indent + "}");
		return lines;
	}

	int call;
	int feature;
	int agent;

	int maintenance;

	int privateFilter;

	public CSTAMonitorFilter() {
	}

	public CSTAMonitorFilter(int _call, int _feature, int _agent,
			int _maintenance, int _privateFilter) {
		call = _call;
		feature = _feature;
		agent = _agent;
		maintenance = _maintenance;
		privateFilter = _privateFilter;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		call = ASNBitString.decode(memberStream);
		feature = ASNBitString.decode(memberStream);
		agent = ASNBitString.decode(memberStream);
		maintenance = ASNBitString.decode(memberStream);
		privateFilter = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTACallFilter.encode(call, memberStream);
		CSTAFeatureFilter.encode(feature, memberStream);
		CSTAAgentFilter.encode(agent, memberStream);
		CSTAMaintenanceFilter.encode(maintenance, memberStream);
		ASNInteger.encode(privateFilter, memberStream);
	}

	public int getAgent() {
		return agent;
	}

	public int getCall() {
		return call;
	}

	public int getFeature() {
		return feature;
	}

	public int getMaintenance() {
		return maintenance;
	}

	public int getPrivateFilter() {
		return privateFilter;
	}
}

