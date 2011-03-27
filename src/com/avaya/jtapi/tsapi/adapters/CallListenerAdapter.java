package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.CallEvent;
import javax.telephony.CallListener;
import javax.telephony.MetaEvent;

public class CallListenerAdapter implements CallListener {
	@Override
	public void callActive(final CallEvent event) {
	}

	@Override
	public void callEventTransmissionEnded(final CallEvent event) {
	}

	@Override
	public void callInvalid(final CallEvent event) {
	}

	@Override
	public void multiCallMetaMergeEnded(final MetaEvent event) {
	}

	@Override
	public void multiCallMetaMergeStarted(final MetaEvent event) {
	}

	@Override
	public void multiCallMetaTransferEnded(final MetaEvent event) {
	}

	@Override
	public void multiCallMetaTransferStarted(final MetaEvent event) {
	}

	@Override
	public void singleCallMetaProgressEnded(final MetaEvent event) {
	}

	@Override
	public void singleCallMetaProgressStarted(final MetaEvent event) {
	}

	@Override
	public void singleCallMetaSnapshotEnded(final MetaEvent event) {
	}

	@Override
	public void singleCallMetaSnapshotStarted(final MetaEvent event) {
	}
}
