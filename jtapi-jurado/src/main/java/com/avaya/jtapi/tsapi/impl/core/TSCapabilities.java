package com.avaya.jtapi.tsapi.impl.core;

public final class TSCapabilities {
	boolean isLucentVar = false;

	int lucentPDV = 0;
	int addParty;
	int alternateCall;
	int answerCall;
	int callCompletion;
	int clearCall;
	int clearConnection;
	int conferenceCall;
	int consultationCall;
	int deflectCall;
	int pickupCall;
	int groupPickupCall;
	int holdCall;
	int makeCall;
	int makePredictiveCall;
	int queryMwi;
	int queryDnd;
	int queryFwd;
	int queryAgentState;
	int queryLastNumber;
	int queryDeviceInfo;
	int reconnectCall;
	int retrieveCall;
	int setMwi;
	int setDnd;
	int setFwd;
	int setAgentState;
	int transferCall;
	int eventReport;
	int callClearedEvent;
	int conferencedEvent;
	int connectionClearedEvent;
	int deliveredEvent;
	int divertedEvent;
	int establishedEvent;
	int failedEvent;
	int heldEvent;
	int networkReachedEvent;
	int originatedEvent;
	int queuedEvent;
	int retrievedEvent;
	int serviceInitiatedEvent;
	int transferredEvent;
	int callInformationEvent;
	int doNotDisturbEvent;
	int forwardingEvent;
	int messageWaitingEvent;
	int loggedOnEvent;
	int loggedOffEvent;
	int notReadyEvent;
	int readyEvent;
	int workNotReadyEvent;
	int workReadyEvent;
	int backInServiceEvent;
	int outOfServiceEvent;
	int privateEvent;
	int routeRequestEvent;
	int reRoute;
	int routeSelect;
	int routeUsedEvent;
	int routeEndEvent;
	int monitorDevice;
	int monitorCall;
	int monitorCallsViaDevice;
	int changeMonitorFilter;
	int monitorStop;
	int monitorEnded;
	int snapshotDeviceReq;
	int snapshotCallReq;
	int escapeService;
	int privateStatusEvent;
	int escapeServiceEvent;
	int escapeServiceConf;
	int sendPrivateEvent;
	int sysStatReq;
	int sysStatStart;
	int sysStatStop;
	int changeSysStatFilter;
	int sysStatReqEvent;
	int sysStatReqConf;
	int sysStatEvent;

	public int getAddParty() {
		return this.addParty;
	}

	void setAddParty(int _addParty) {
		this.addParty = _addParty;
	}

	public int getAlternateCall() {
		return this.alternateCall;
	}

	void setAlternateCall(int _alternateCall) {
		this.alternateCall = _alternateCall;
	}

	public int getAnswerCall() {
		return this.answerCall;
	}

	void setAnswerCall(int _answerCall) {
		this.answerCall = _answerCall;
	}

	public int getCallCompletion() {
		return this.callCompletion;
	}

	void setCallCompletion(int _callCompletion) {
		this.callCompletion = _callCompletion;
	}

	public int getClearCall() {
		return this.clearCall;
	}

	void setClearCall(int _clearCall) {
		this.clearCall = _clearCall;
	}

	public int getClearConnection() {
		return this.clearConnection;
	}

	void setClearConnection(int _clearConnection) {
		this.clearConnection = _clearConnection;
	}

	public int getConferenceCall() {
		return this.conferenceCall;
	}

	void setConferenceCall(int _conferenceCall) {
		this.conferenceCall = _conferenceCall;
	}

	public int getConsultationCall() {
		return this.consultationCall;
	}

	void setConsultationCall(int _consultationCall) {
		this.consultationCall = _consultationCall;
	}

	public int getDeflectCall() {
		return this.deflectCall;
	}

	void setDeflectCall(int _deflectCall) {
		this.deflectCall = _deflectCall;
	}

	public int getPickupCall() {
		return this.pickupCall;
	}

	void setPickupCall(int _pickupCall) {
		this.pickupCall = _pickupCall;
	}

	public int getGroupPickupCall() {
		return this.groupPickupCall;
	}

	void setGroupPickupCall(int _groupPickupCall) {
		this.groupPickupCall = _groupPickupCall;
	}

	public int getHoldCall() {
		return this.holdCall;
	}

	void setHoldCall(int _holdCall) {
		this.holdCall = _holdCall;
	}

	public int getMakeCall() {
		return this.makeCall;
	}

	void setMakeCall(int _makeCall) {
		this.makeCall = _makeCall;
	}

	public int getMakePredictiveCall() {
		return this.makePredictiveCall;
	}

	void setMakePredictiveCall(int _makePredictiveCall) {
		this.makePredictiveCall = _makePredictiveCall;
	}

	public int getQueryMwi() {
		return this.queryMwi;
	}

	void setQueryMwi(int _queryMwi) {
		this.queryMwi = _queryMwi;
	}

	public int getQueryDnd() {
		return this.queryDnd;
	}

	void setQueryDnd(int _queryDnd) {
		this.queryDnd = _queryDnd;
	}

	public int getQueryFwd() {
		return this.queryFwd;
	}

	void setQueryFwd(int _queryFwd) {
		this.queryFwd = _queryFwd;
	}

	public int getQueryAgentState() {
		return this.queryAgentState;
	}

	void setQueryAgentState(int _queryAgentState) {
		this.queryAgentState = _queryAgentState;
	}

	public int getQueryLastNumber() {
		return this.queryLastNumber;
	}

	void setQueryLastNumber(int _queryLastNumber) {
		this.queryLastNumber = _queryLastNumber;
	}

	public int getQueryDeviceInfo() {
		return this.queryDeviceInfo;
	}

	void setQueryDeviceInfo(int _queryDeviceInfo) {
		this.queryDeviceInfo = _queryDeviceInfo;
	}

	public int getReconnectCall() {
		return this.reconnectCall;
	}

	void setReconnectCall(int _reconnectCall) {
		this.reconnectCall = _reconnectCall;
	}

	public int getRetrieveCall() {
		return this.retrieveCall;
	}

	void setRetrieveCall(int _retrieveCall) {
		this.retrieveCall = _retrieveCall;
	}

	public int getSetMwi() {
		return this.setMwi;
	}

	void setSetMwi(int _setMwi) {
		this.setMwi = _setMwi;
	}

	public int getSetDnd() {
		return this.setDnd;
	}

	void setSetDnd(int _setDnd) {
		this.setDnd = _setDnd;
	}

	public int getSetFwd() {
		return this.setFwd;
	}

	void setSetFwd(int _setFwd) {
		this.setFwd = _setFwd;
	}

	public int getSetAgentState() {
		return this.setAgentState;
	}

	void setSetAgentState(int _setAgentState) {
		this.setAgentState = _setAgentState;
	}

	public int getTransferCall() {
		return this.transferCall;
	}

	void setTransferCall(int _transferCall) {
		this.transferCall = _transferCall;
	}

	public int getEventReport() {
		return this.eventReport;
	}

	void setEventReport(int _eventReport) {
		this.eventReport = _eventReport;
	}

	public int getCallClearedEvent() {
		return this.callClearedEvent;
	}

	void setCallClearedEvent(int _callClearedEvent) {
		this.callClearedEvent = _callClearedEvent;
	}

	public int getConferencedEvent() {
		return this.conferencedEvent;
	}

	void setConferencedEvent(int _conferencedEvent) {
		this.conferencedEvent = _conferencedEvent;
	}

	public int getConnectionClearedEvent() {
		return this.connectionClearedEvent;
	}

	void setConnectionClearedEvent(int _connectionClearedEvent) {
		this.connectionClearedEvent = _connectionClearedEvent;
	}

	public int getDeliveredEvent() {
		return this.deliveredEvent;
	}

	void setDeliveredEvent(int _deliveredEvent) {
		this.deliveredEvent = _deliveredEvent;
	}

	public int getDivertedEvent() {
		return this.divertedEvent;
	}

	void setDivertedEvent(int _divertedEvent) {
		this.divertedEvent = _divertedEvent;
	}

	public int getEstablishedEvent() {
		return this.establishedEvent;
	}

	void setEstablishedEvent(int _establishedEvent) {
		this.establishedEvent = _establishedEvent;
	}

	public int getFailedEvent() {
		return this.failedEvent;
	}

	void setFailedEvent(int _failedEvent) {
		this.failedEvent = _failedEvent;
	}

	public int getHeldEvent() {
		return this.heldEvent;
	}

	void setHeldEvent(int _heldEvent) {
		this.heldEvent = _heldEvent;
	}

	public int getNetworkReachedEvent() {
		return this.networkReachedEvent;
	}

	void setNetworkReachedEvent(int _networkReachedEvent) {
		this.networkReachedEvent = _networkReachedEvent;
	}

	public int getOriginatedEvent() {
		return this.originatedEvent;
	}

	void setOriginatedEvent(int _originatedEvent) {
		this.originatedEvent = _originatedEvent;
	}

	public int getQueuedEvent() {
		return this.queuedEvent;
	}

	void setQueuedEvent(int _queuedEvent) {
		this.queuedEvent = _queuedEvent;
	}

	public int getRetrievedEvent() {
		return this.retrievedEvent;
	}

	void setRetrievedEvent(int _retrievedEvent) {
		this.retrievedEvent = _retrievedEvent;
	}

	public int getServiceInitiatedEvent() {
		return this.serviceInitiatedEvent;
	}

	void setServiceInitiatedEvent(int _serviceInitiatedEvent) {
		this.serviceInitiatedEvent = _serviceInitiatedEvent;
	}

	public int getTransferredEvent() {
		return this.transferredEvent;
	}

	void setTransferredEvent(int _transferredEvent) {
		this.transferredEvent = _transferredEvent;
	}

	public int getCallInformationEvent() {
		return this.callInformationEvent;
	}

	void setCallInformationEvent(int _callInformationEvent) {
		this.callInformationEvent = _callInformationEvent;
	}

	public int getDoNotDisturbEvent() {
		return this.doNotDisturbEvent;
	}

	void setDoNotDisturbEvent(int _doNotDisturbEvent) {
		this.doNotDisturbEvent = _doNotDisturbEvent;
	}

	public int getForwardingEvent() {
		return this.forwardingEvent;
	}

	void setForwardingEvent(int _forwardingEvent) {
		this.forwardingEvent = _forwardingEvent;
	}

	public int getMessageWaitingEvent() {
		return this.messageWaitingEvent;
	}

	void setMessageWaitingEvent(int _messageWaitingEvent) {
		this.messageWaitingEvent = _messageWaitingEvent;
	}

	public int getLoggedOnEvent() {
		return this.loggedOnEvent;
	}

	void setLoggedOnEvent(int _loggedOnEvent) {
		this.loggedOnEvent = _loggedOnEvent;
	}

	public int getLoggedOffEvent() {
		return this.loggedOffEvent;
	}

	void setLoggedOffEvent(int _loggedOffEvent) {
		this.loggedOffEvent = _loggedOffEvent;
	}

	public int getNotReadyEvent() {
		return this.notReadyEvent;
	}

	void setNotReadyEvent(int _notReadyEvent) {
		this.notReadyEvent = _notReadyEvent;
	}

	public int getReadyEvent() {
		return this.readyEvent;
	}

	void setReadyEvent(int _readyEvent) {
		this.readyEvent = _readyEvent;
	}

	public int getWorkNotReadyEvent() {
		return this.workNotReadyEvent;
	}

	void setWorkNotReadyEvent(int _workNotReadyEvent) {
		this.workNotReadyEvent = _workNotReadyEvent;
	}

	public int getWorkReadyEvent() {
		return this.workReadyEvent;
	}

	void setWorkReadyEvent(int _workReadyEvent) {
		this.workReadyEvent = _workReadyEvent;
	}

	public int getBackInServiceEvent() {
		return this.backInServiceEvent;
	}

	void setBackInServiceEvent(int _backInServiceEvent) {
		this.backInServiceEvent = _backInServiceEvent;
	}

	public int getOutOfServiceEvent() {
		return this.outOfServiceEvent;
	}

	void setOutOfServiceEvent(int _outOfServiceEvent) {
		this.outOfServiceEvent = _outOfServiceEvent;
	}

	public int getPrivateEvent() {
		return this.privateEvent;
	}

	void setPrivateEvent(int _privateEvent) {
		this.privateEvent = _privateEvent;
	}

	public int getRouteRequestEvent() {
		return this.routeRequestEvent;
	}

	void setRouteRequestEvent(int _routeRequestEvent) {
		this.routeRequestEvent = _routeRequestEvent;
	}

	public int getReRoute() {
		return this.reRoute;
	}

	void setReRoute(int _reRoute) {
		this.reRoute = _reRoute;
	}

	public int getRouteSelect() {
		return this.routeSelect;
	}

	void setRouteSelect(int _routeSelect) {
		this.routeSelect = _routeSelect;
	}

	public int getRouteUsedEvent() {
		return this.routeUsedEvent;
	}

	void setRouteUsedEvent(int _routeUsedEvent) {
		this.routeUsedEvent = _routeUsedEvent;
	}

	public int getRouteEndEvent() {
		return this.routeEndEvent;
	}

	void setRouteEndEvent(int _routeEndEvent) {
		this.routeEndEvent = _routeEndEvent;
	}

	public int getMonitorDevice() {
		return this.monitorDevice;
	}

	void setMonitorDevice(int _monitorDevice) {
		this.monitorDevice = _monitorDevice;
	}

	public int getMonitorCall() {
		return this.monitorCall;
	}

	void setMonitorCall(int _monitorCall) {
		this.monitorCall = _monitorCall;
	}

	public int getMonitorCallsViaDevice() {
		return this.monitorCallsViaDevice;
	}

	void setMonitorCallsViaDevice(int _monitorCallsViaDevice) {
		this.monitorCallsViaDevice = _monitorCallsViaDevice;
	}

	public int getChangeMonitorFilter() {
		return this.changeMonitorFilter;
	}

	void setChangeMonitorFilter(int _changeMonitorFilter) {
		this.changeMonitorFilter = _changeMonitorFilter;
	}

	public int getMonitorStop() {
		return this.monitorStop;
	}

	void setMonitorStop(int _monitorStop) {
		this.monitorStop = _monitorStop;
	}

	public int getMonitorEnded() {
		return this.monitorEnded;
	}

	void setMonitorEnded(int _monitorEnded) {
		this.monitorEnded = _monitorEnded;
	}

	public int getSnapshotDeviceReq() {
		return this.snapshotDeviceReq;
	}

	void setSnapshotDeviceReq(int _snapshotDeviceReq) {
		this.snapshotDeviceReq = _snapshotDeviceReq;
	}

	public int getSnapshotCallReq() {
		return this.snapshotCallReq;
	}

	void setSnapshotCallReq(int _snapshotCallReq) {
		this.snapshotCallReq = _snapshotCallReq;
	}

	public int getEscapeService() {
		return this.escapeService;
	}

	void setEscapeService(int _escapeService) {
		this.escapeService = _escapeService;
	}

	public int getPrivateStatusEvent() {
		return this.privateStatusEvent;
	}

	void setPrivateStatusEvent(int _privateStatusEvent) {
		this.privateStatusEvent = _privateStatusEvent;
	}

	public int getEscapeServiceEvent() {
		return this.escapeServiceEvent;
	}

	void setEscapeServiceEvent(int _escapeServiceEvent) {
		this.escapeServiceEvent = _escapeServiceEvent;
	}

	public int getEscapeServiceConf() {
		return this.escapeServiceConf;
	}

	void setEscapeServiceConf(int _escapeServiceConf) {
		this.escapeServiceConf = _escapeServiceConf;
	}

	public int getSendPrivateEvent() {
		return this.sendPrivateEvent;
	}

	void setSendPrivateEvent(int _sendPrivateEvent) {
		this.sendPrivateEvent = _sendPrivateEvent;
	}

	public int getSysStatReq() {
		return this.sysStatReq;
	}

	void setSysStatReq(int _sysStatReq) {
		this.sysStatReq = _sysStatReq;
	}

	public int getSysStatStart() {
		return this.sysStatStart;
	}

	void setSysStatStart(int _sysStatStart) {
		this.sysStatStart = _sysStatStart;
	}

	public int getSysStatStop() {
		return this.sysStatStop;
	}

	void setSysStatStop(int _sysStatStop) {
		this.sysStatStop = _sysStatStop;
	}

	public int getChangeSysStatFilter() {
		return this.changeSysStatFilter;
	}

	void setChangeSysStatFilter(int _changeSysStatFilter) {
		this.changeSysStatFilter = _changeSysStatFilter;
	}

	public int getSysStatReqEvent() {
		return this.sysStatReqEvent;
	}

	void setSysStatReqEvent(int _sysStatReqEvent) {
		this.sysStatReqEvent = _sysStatReqEvent;
	}

	public int getSysStatReqConf() {
		return this.sysStatReqConf;
	}

	void setSysStatReqConf(int _sysStatReqConf) {
		this.sysStatReqConf = _sysStatReqConf;
	}

	public int getSysStatEvent() {
		return this.sysStatEvent;
	}

	void setSysStatEvent(int _sysStatEvent) {
		this.sysStatEvent = _sysStatEvent;
	}

	public boolean isLucent() {
		return this.isLucentVar;
	}

	public boolean isLucentV8() {
		return (this.isLucentVar) && (this.lucentPDV >= 8);
	}

	void setLucent(int pdv) {
		this.isLucentVar = true;
		this.lucentPDV = pdv;
	}

	void setAll() {
		this.addParty = 1;
		this.alternateCall = 1;
		this.answerCall = 1;
		this.callCompletion = 1;
		this.clearCall = 1;
		this.clearConnection = 1;
		this.conferenceCall = 1;
		this.consultationCall = 1;
		this.deflectCall = 1;
		this.pickupCall = 1;
		this.groupPickupCall = 1;
		this.holdCall = 1;
		this.makeCall = 1;
		this.makePredictiveCall = 1;
		this.queryMwi = 1;
		this.queryDnd = 1;
		this.queryFwd = 1;
		this.queryAgentState = 1;
		this.queryLastNumber = 1;
		this.queryDeviceInfo = 1;
		this.reconnectCall = 1;
		this.retrieveCall = 1;
		this.setMwi = 1;
		this.setDnd = 1;
		this.setFwd = 1;
		this.setAgentState = 1;
		this.transferCall = 1;
		this.eventReport = 1;
		this.callClearedEvent = 1;
		this.conferencedEvent = 1;
		this.connectionClearedEvent = 1;
		this.deliveredEvent = 1;
		this.divertedEvent = 1;
		this.establishedEvent = 1;
		this.failedEvent = 1;
		this.heldEvent = 1;
		this.networkReachedEvent = 1;
		this.originatedEvent = 1;
		this.queuedEvent = 1;
		this.retrievedEvent = 1;
		this.serviceInitiatedEvent = 1;
		this.transferredEvent = 1;
		this.callInformationEvent = 1;
		this.doNotDisturbEvent = 1;
		this.forwardingEvent = 1;
		this.messageWaitingEvent = 1;
		this.loggedOnEvent = 1;
		this.loggedOffEvent = 1;
		this.notReadyEvent = 1;
		this.readyEvent = 1;
		this.workNotReadyEvent = 1;
		this.workReadyEvent = 1;
		this.backInServiceEvent = 1;
		this.outOfServiceEvent = 1;
		this.privateEvent = 1;
		this.routeRequestEvent = 1;
		this.reRoute = 1;
		this.routeSelect = 1;
		this.routeUsedEvent = 1;
		this.routeEndEvent = 1;
		this.monitorDevice = 1;
		this.monitorCall = 1;
		this.monitorCallsViaDevice = 1;
		this.changeMonitorFilter = 1;
		this.monitorStop = 1;
		this.monitorEnded = 1;
		this.snapshotDeviceReq = 1;
		this.snapshotCallReq = 1;
		this.escapeService = 1;
		this.privateStatusEvent = 1;
		this.escapeServiceEvent = 1;
		this.escapeServiceConf = 1;
		this.sendPrivateEvent = 1;
		this.sysStatReq = 1;
		this.sysStatStart = 1;
		this.sysStatStop = 1;
		this.changeSysStatFilter = 1;
		this.sysStatReqEvent = 1;
		this.sysStatReqConf = 1;
		this.sysStatEvent = 1;
	}
}