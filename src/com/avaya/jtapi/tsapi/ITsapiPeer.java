package com.avaya.jtapi.tsapi;

import javax.telephony.JtapiPeer;

public abstract interface ITsapiPeer extends JtapiPeer {
	public abstract void addVendor(String paramString1, String paramString2);
}
