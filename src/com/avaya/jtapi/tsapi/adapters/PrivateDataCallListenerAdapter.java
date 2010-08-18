package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.privatedata.PrivateDataCallListener;
import javax.telephony.privatedata.PrivateDataEvent;

public class PrivateDataCallListenerAdapter extends CallListenerAdapter
		implements PrivateDataCallListener {
	public void callPrivateData(PrivateDataEvent event) {
	}
}

