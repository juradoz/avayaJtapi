package com.avaya.jtapi.tsapi.impl.core;

public final class TSEvent {
	public static final int PROVIDERINSERVICEEVENT = 1;
	public static final int PROVIDEROUTOFSERVICEEVENT = 2;
	public static final int PROVIDERSHUTDOWNEVENT = 3;
	public static final int CALLACTIVEEVENT = 4;
	public static final int CALLINVALIDEVENT = 5;
	public static final int CONNECTIONCREATEDEVENT = 6;
	public static final int CONNECTIONCONNECTEDEVENT = 7;
	public static final int CONNECTIONINPROGRESSEVENT = 8;
	public static final int CONNECTIONALERTINGEVENT = 9;
	public static final int CONNECTIONDISCONNECTEDEVENT = 10;
	public static final int CONNECTIONFAILEDEVENT = 11;
	public static final int CONNECTIONUNKNOWNEVENT = 12;
	public static final int TERMINALCONNECTIONCREATEDEVENT = 13;
	public static final int TERMINALCONNECTIONACTIVEEVENT = 14;
	public static final int TERMINALCONNECTIONRINGINGEVENT = 15;
	public static final int TERMINALCONNECTIONPASSIVEEVENT = 16;
	public static final int TERMINALCONNECTIONDROPPEDEVENT = 17;
	public static final int TERMINALCONNECTIONUNKNOWNEVENT = 18;
	public static final int CONNECTIONOFFEREDEVENT = 19;
	public static final int CONNECTIONDIALINGEVENT = 20;
	public static final int CONNECTIONESTABLISHEDEVENT = 21;
	public static final int CONNECTIONNETWORKREACHEDEVENT = 22;
	public static final int CONNECTIONNETWORKALERTINGEVENT = 23;
	public static final int CONNECTIONINITIATEDEVENT = 24;
	public static final int CONNECTIONQUEUEDEVENT = 25;
	public static final int CONNECTIONALERTINGEVENT_CC = 26;
	public static final int CONNECTIONDISCONNECTEDEVENT_CC = 27;
	public static final int CONNECTIONFAILEDEVENT_CC = 28;
	public static final int CONNECTIONUNKNOWNEVENT_CC = 29;
	public static final int TERMINALCONNECTIONTALKINGEVENT = 30;
	public static final int TERMINALCONNECTIONHELDEVENT = 31;
	public static final int TERMINALCONNECTIONBRIDGEDEVENT = 32;
	public static final int TERMINALCONNECTIONINUSEEVENT = 33;
	public static final int TERMINALCONNECTIONDROPPEDEVENT_CC = 34;
	public static final int TERMINALCONNECTIONRINGINGEVENT_CC = 35;
	public static final int TERMINALCONNECTIONUNKNOWNEVENT_CC = 36;
	public static final int ADDRESSDONOTDISTURBEVENT = 37;
	public static final int ADDRESSMESSAGEWAITINGEVENT = 38;
	public static final int ADDRESSFORWARDEVENT = 39;
	public static final int ADDRESSLOGGEDONEVENT = 40;
	public static final int ADDRESSLOGGEDOFFEVENT = 41;
	public static final int ADDRESSREADYEVENT = 42;
	public static final int ADDRESSNOTREADYEVENT = 43;
	public static final int ADDRESSWORKREADYEVENT = 44;
	public static final int ADDRESSWORKNOTREADYEVENT = 45;
	public static final int ADDRESSBUSYEVENT = 46;
	public static final int TERMINALLOGGEDONEVENT = 47;
	public static final int TERMINALLOGGEDOFFEVENT = 48;
	public static final int TERMINALREADYEVENT = 49;
	public static final int TERMINALNOTREADYEVENT = 50;
	public static final int TERMINALWORKREADYEVENT = 51;
	public static final int TERMINALWORKNOTREADYEVENT = 52;
	public static final int TERMINALBUSYEVENT = 53;
	public static final int TRUNKVALIDEVENT = 54;
	public static final int TRUNKINVALIDEVENT = 55;
	public static final int CONNECTIONINPROGRESSEVENT_CC = 56;
	public static final int CALLDTMFEVENT = 57;
	public static final int TERMINALDONOTDISTURBEVENT = 58;
	public static final int REROUTEEVENT = 59;
	public static final int ROUTECALLBACKENDEDEVENT = 60;
	public static final int ROUTEENDEVENT = 61;
	public static final int ROUTEEVENT = 62;
	public static final int ROUTEUSEDEVENT = 63;
	public static final int PRIVATEEVENT = 9999;
	private TransferredEventParams transferredEventParams;
	Object eventTarget;
	int eventType;
	Object privateData;
	TSDevice skillDevice;
	private short snapshotCstaCause = -1;

	TSEvent(int _eventType, Object _eventTarget) {
		eventType = _eventType;
		eventTarget = _eventTarget;
		privateData = null;
	}

	public TSEvent(int _eventType, Object _eventTarget, Object _privateData) {
		this(_eventType, _eventTarget, _privateData, null);
	}

	public TSEvent(int _eventType, Object _eventTarget, Object _privateData,
			TSProviderImpl _provider) {
		eventType = _eventType;
		eventTarget = _eventTarget;

		privateData = TsapiPromoter
				.promotePrivateEvent(_provider, _privateData);
	}

	public Object getEventTarget() {
		return eventTarget;
	}

	public int getEventType() {
		return eventType;
	}

	public Object getPrivateData() {
		return privateData;
	}

	public TSDevice getSkillDevice() {
		return skillDevice;
	}

	public short getSnapshotCstaCause() {
		return snapshotCstaCause;
	}

	public TransferredEventParams getTransferredEventParams() {
		return transferredEventParams;
	}

	public void setPrivateData(Object _privateData) {
		privateData = _privateData;
	}

	public void setSkillDevice(TSDevice _skillDevice) {
		skillDevice = _skillDevice;
	}

	public void setSnapshotCstaCause(short snapshotCstaCause) {
		this.snapshotCstaCause = snapshotCstaCause;
	}

	public void setTransferredEventParams(
			TransferredEventParams transferredEventParams) {
		this.transferredEventParams = transferredEventParams;
	}

	@Override
	public String toString() {
		return "eventTarget=" + eventTarget + ";eventType=" + eventType;
	}
}

