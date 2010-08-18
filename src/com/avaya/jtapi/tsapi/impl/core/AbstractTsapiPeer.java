package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;

public abstract class AbstractTsapiPeer {
	protected Vector<TsapiVendor> vendors;

	public AbstractTsapiPeer() {
		vendors = null;
	}

	public void addVendor(String name, String versions) {
		if (vendors == null) {
			vendors = new Vector<TsapiVendor>();
		}
		vendors.addElement(new TsapiVendor(name, versions));
	}

	public String[] getServices() {
		return Tsapi.getServices();
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.AbstractTsapiPeer JD-Core Version: 0.5.4
 */