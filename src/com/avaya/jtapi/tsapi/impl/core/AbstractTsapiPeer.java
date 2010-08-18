package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;

public abstract class AbstractTsapiPeer {
	protected Vector<TsapiVendor> vendors;

	public AbstractTsapiPeer() {
		vendors = null;
	}

	public void addVendor(final String name, final String versions) {
		if (vendors == null)
			vendors = new Vector<TsapiVendor>();
		vendors.addElement(new TsapiVendor(name, versions));
	}

	public String[] getServices() {
		return Tsapi.getServices();
	}
}
