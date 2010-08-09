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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.CallListener JD-Core Version: 0.5.4
 */