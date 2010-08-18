package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.CallEvent;
import javax.telephony.CallListener;
import javax.telephony.MetaEvent;

public class CallListenerAdapter implements CallListener {
	public void callActive(final CallEvent event) {
	}

	public void callEventTransmissionEnded(final CallEvent event) {
	}

	public void callInvalid(final CallEvent event) {
	}

	public void multiCallMetaMergeEnded(final MetaEvent event) {
	}

	public void multiCallMetaMergeStarted(final MetaEvent event) {
	}

	public void multiCallMetaTransferEnded(final MetaEvent event) {
	}

	public void multiCallMetaTransferStarted(final MetaEvent event) {
	}

	public void singleCallMetaProgressEnded(final MetaEvent event) {
	}

	public void singleCallMetaProgressStarted(final MetaEvent event) {
	}

	public void singleCallMetaSnapshotEnded(final MetaEvent event) {
	}

	public void singleCallMetaSnapshotStarted(final MetaEvent event) {
	}
}
