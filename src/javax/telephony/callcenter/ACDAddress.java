package javax.telephony.callcenter;

import javax.telephony.Call;
import javax.telephony.MethodNotSupportedException;

public abstract interface ACDAddress extends CallCenterAddress {
	public abstract Agent[] getLoggedOnAgents()
			throws MethodNotSupportedException;

	public abstract int getNumberQueued() throws MethodNotSupportedException;

	public abstract Call getOldestCallQueued()
			throws MethodNotSupportedException;

	public abstract int getRelativeQueueLoad()
			throws MethodNotSupportedException;

	public abstract int getQueueWaitTime() throws MethodNotSupportedException;

	public abstract ACDManagerAddress getACDManagerAddress()
			throws MethodNotSupportedException;
}