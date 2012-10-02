package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
import java.util.Vector;

public abstract class AbstractTsapiPeer {
	protected Vector<TsapiVendor> vendors = null;

	public void addVendor(String name, String versions) {
		if (this.vendors == null)
			this.vendors = new Vector<TsapiVendor>();
		this.vendors.addElement(new TsapiVendor(name, versions));
	}

	public String[] getServices() {
		return Tsapi.getServices();
	}
}