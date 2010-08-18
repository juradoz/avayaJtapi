package javax.telephony.callcontrol;

public class CallControlForwarding {
	private String destAddress;
	private String caller;
	private int type;
	private int whichCalls;
	public static final int ALL_CALLS = 1;
	public static final int INTERNAL_CALLS = 2;
	public static final int EXTERNAL_CALLS = 3;
	public static final int SPECIFIC_ADDRESS = 4;
	public static final int FORWARD_UNCONDITIONALLY = 1;
	public static final int FORWARD_ON_BUSY = 2;
	public static final int FORWARD_ON_NOANSWER = 3;

	public CallControlForwarding(String destAddress) {
		this.destAddress = destAddress;
		type = 1;
		caller = null;
		whichCalls = 1;
	}

	public CallControlForwarding(String destAddress, int type) {
		this.destAddress = destAddress;
		this.type = type;
		caller = null;
		whichCalls = 1;
	}

	public CallControlForwarding(String destAddress, int type,
			boolean internalCalls) {
		this.destAddress = destAddress;
		this.type = type;
		caller = null;
		if (internalCalls) {
			whichCalls = 2;
		} else {
			whichCalls = 3;
		}
	}

	public CallControlForwarding(String destAddress, int type, String caller) {
		this.destAddress = destAddress;
		this.type = type;
		this.caller = caller;
		whichCalls = 4;
	}

	public String getDestinationAddress() {
		return destAddress;
	}

	public int getFilter() {
		return whichCalls;
	}

	public String getSpecificCaller() {
		return caller;
	}

	public int getType() {
		return type;
	}
}

