package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAMonitorFilter extends ASNSequence {
	int call;
	int feature;
	int agent;
	int maintenance;
	int privateFilter;

	public CSTAMonitorFilter() {
	}

	public CSTAMonitorFilter(int _call, int _feature, int _agent,
			int _maintenance, int _privateFilter) {
		this.call = _call;
		this.feature = _feature;
		this.agent = _agent;
		this.maintenance = _maintenance;
		this.privateFilter = _privateFilter;
	}

	public static CSTAMonitorFilter decode(InputStream in) {
		CSTAMonitorFilter _this = new CSTAMonitorFilter();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.call = CSTACallFilter.decode(memberStream);
		this.feature = CSTAFeatureFilter.decode(memberStream);
		this.agent = CSTAAgentFilter.decode(memberStream);
		this.maintenance = CSTAMaintenanceFilter.decode(memberStream);
		this.privateFilter = ASNInteger.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTACallFilter.encode(this.call, memberStream);
		CSTAFeatureFilter.encode(this.feature, memberStream);
		CSTAAgentFilter.encode(this.agent, memberStream);
		CSTAMaintenanceFilter.encode(this.maintenance, memberStream);
		ASNInteger.encode(this.privateFilter, memberStream);
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

	public int getAgent() {
		return this.agent;
	}

	public int getCall() {
		return this.call;
	}

	public int getFeature() {
		return this.feature;
	}

	public int getMaintenance() {
		return this.maintenance;
	}

	public int getPrivateFilter() {
		return this.privateFilter;
	}
}