package javax.telephony;

import java.util.EventListener;

public abstract interface CallListener extends EventListener {
	public abstract void callActive(CallEvent paramCallEvent);

	public abstract void callEventTransmissionEnded(CallEvent paramCallEvent);

	public abstract void callInvalid(CallEvent paramCallEvent);

	public abstract void multiCallMetaMergeEnded(MetaEvent paramMetaEvent);

	public abstract void multiCallMetaMergeStarted(MetaEvent paramMetaEvent);

	public abstract void multiCallMetaTransferEnded(MetaEvent paramMetaEvent);

	public abstract void multiCallMetaTransferStarted(MetaEvent paramMetaEvent);

	public abstract void singleCallMetaProgressEnded(MetaEvent paramMetaEvent);

	public abstract void singleCallMetaProgressStarted(MetaEvent paramMetaEvent);

	public abstract void singleCallMetaSnapshotEnded(MetaEvent paramMetaEvent);

	public abstract void singleCallMetaSnapshotStarted(MetaEvent paramMetaEvent);
}
