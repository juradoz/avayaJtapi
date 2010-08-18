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
		return addParty;
	}

	public int getAlternateCall() {
		return alternateCall;
	}

	public int getAnswerCall() {
		return answerCall;
	}

	public int getBackInServiceEvent() {
		return backInServiceEvent;
	}

	public int getCallClearedEvent() {
		return callClearedEvent;
	}

	public int getCallCompletion() {
		return callCompletion;
	}

	public int getCallInformationEvent() {
		return callInformationEvent;
	}

	public int getChangeMonitorFilter() {
		return changeMonitorFilter;
	}

	public int getChangeSysStatFilter() {
		return changeSysStatFilter;
	}

	public int getClearCall() {
		return clearCall;
	}

	public int getClearConnection() {
		return clearConnection;
	}

	public int getConferenceCall() {
		return conferenceCall;
	}

	public int getConferencedEvent() {
		return conferencedEvent;
	}

	public int getConnectionClearedEvent() {
		return connectionClearedEvent;
	}

	public int getConsultationCall() {
		return consultationCall;
	}

	public int getDeflectCall() {
		return deflectCall;
	}

	public int getDeliveredEvent() {
		return deliveredEvent;
	}

	public int getDivertedEvent() {
		return divertedEvent;
	}

	public int getDoNotDisturbEvent() {
		return doNotDisturbEvent;
	}

	public int getEscapeService() {
		return escapeService;
	}

	public int getEscapeServiceConf() {
		return escapeServiceConf;
	}

	public int getEscapeServiceEvent() {
		return escapeServiceEvent;
	}

	public int getEstablishedEvent() {
		return establishedEvent;
	}

	public int getEventReport() {
		return eventReport;
	}

	public int getFailedEvent() {
		return failedEvent;
	}

	public int getForwardingEvent() {
		return forwardingEvent;
	}

	public int getGroupPickupCall() {
		return groupPickupCall;
	}

	public int getHeldEvent() {
		return heldEvent;
	}

	public int getHoldCall() {
		return holdCall;
	}

	public int getLoggedOffEvent() {
		return loggedOffEvent;
	}

	public int getLoggedOnEvent() {
		return loggedOnEvent;
	}

	public int getMakeCall() {
		return makeCall;
	}

	public int getMakePredictiveCall() {
		return makePredictiveCall;
	}

	public int getMessageWaitingEvent() {
		return messageWaitingEvent;
	}

	public int getMonitorCall() {
		return monitorCall;
	}

	public int getMonitorCallsViaDevice() {
		return monitorCallsViaDevice;
	}

	public int getMonitorDevice() {
		return monitorDevice;
	}

	public int getMonitorEnded() {
		return monitorEnded;
	}

	public int getMonitorStop() {
		return monitorStop;
	}

	public int getNetworkReachedEvent() {
		return networkReachedEvent;
	}

	public int getNotReadyEvent() {
		return notReadyEvent;
	}

	public int getOriginatedEvent() {
		return originatedEvent;
	}

	public int getOutOfServiceEvent() {
		return outOfServiceEvent;
	}

	public int getPickupCall() {
		return pickupCall;
	}

	public int getPrivateEvent() {
		return privateEvent;
	}

	public int getPrivateStatusEvent() {
		return privateStatusEvent;
	}

	public int getQueryAgentState() {
		return queryAgentState;
	}

	public int getQueryDeviceInfo() {
		return queryDeviceInfo;
	}

	public int getQueryDnd() {
		return queryDnd;
	}

	public int getQueryFwd() {
		return queryFwd;
	}

	public int getQueryLastNumber() {
		return queryLastNumber;
	}

	public int getQueryMwi() {
		return queryMwi;
	}

	public int getQueuedEvent() {
		return queuedEvent;
	}

	public int getReadyEvent() {
		return readyEvent;
	}

	public int getReconnectCall() {
		return reconnectCall;
	}

	public int getReRoute() {
		return reRoute;
	}

	public int getRetrieveCall() {
		return retrieveCall;
	}

	public int getRetrievedEvent() {
		return retrievedEvent;
	}

	public int getRouteEndEvent() {
		return routeEndEvent;
	}

	public int getRouteRequestEvent() {
		return routeRequestEvent;
	}

	public int getRouteSelect() {
		return routeSelect;
	}

	public int getRouteUsedEvent() {
		return routeUsedEvent;
	}

	public int getSendPrivateEvent() {
		return sendPrivateEvent;
	}

	public int getServiceInitiatedEvent() {
		return serviceInitiatedEvent;
	}

	public int getSetAgentState() {
		return setAgentState;
	}

	public int getSetDnd() {
		return setDnd;
	}

	public int getSetFwd() {
		return setFwd;
	}

	public int getSetMwi() {
		return setMwi;
	}

	public int getSnapshotCallReq() {
		return snapshotCallReq;
	}

	public int getSnapshotDeviceReq() {
		return snapshotDeviceReq;
	}

	public int getSysStatEvent() {
		return sysStatEvent;
	}

	public int getSysStatReq() {
		return sysStatReq;
	}

	public int getSysStatReqConf() {
		return sysStatReqConf;
	}

	public int getSysStatReqEvent() {
		return sysStatReqEvent;
	}

	public int getSysStatStart() {
		return sysStatStart;
	}

	public int getSysStatStop() {
		return sysStatStop;
	}

	public int getTransferCall() {
		return transferCall;
	}

	public int getTransferredEvent() {
		return transferredEvent;
	}

	public int getWorkNotReadyEvent() {
		return workNotReadyEvent;
	}

	public int getWorkReadyEvent() {
		return workReadyEvent;
	}

	public boolean isLucent() {
		return isLucentVar;
	}

	public boolean isLucentV8() {
		return (isLucentVar) && (lucentPDV >= 8);
	}

	void setAddParty(int _addParty) {
		addParty = _addParty;
	}

	void setAll() {
		addParty = 1;
		alternateCall = 1;
		answerCall = 1;
		callCompletion = 1;
		clearCall = 1;
		clearConnection = 1;
		conferenceCall = 1;
		consultationCall = 1;
		deflectCall = 1;
		pickupCall = 1;
		groupPickupCall = 1;
		holdCall = 1;
		makeCall = 1;
		makePredictiveCall = 1;
		queryMwi = 1;
		queryDnd = 1;
		queryFwd = 1;
		queryAgentState = 1;
		queryLastNumber = 1;
		queryDeviceInfo = 1;
		reconnectCall = 1;
		retrieveCall = 1;
		setMwi = 1;
		setDnd = 1;
		setFwd = 1;
		setAgentState = 1;
		transferCall = 1;
		eventReport = 1;
		callClearedEvent = 1;
		conferencedEvent = 1;
		connectionClearedEvent = 1;
		deliveredEvent = 1;
		divertedEvent = 1;
		establishedEvent = 1;
		failedEvent = 1;
		heldEvent = 1;
		networkReachedEvent = 1;
		originatedEvent = 1;
		queuedEvent = 1;
		retrievedEvent = 1;
		serviceInitiatedEvent = 1;
		transferredEvent = 1;
		callInformationEvent = 1;
		doNotDisturbEvent = 1;
		forwardingEvent = 1;
		messageWaitingEvent = 1;
		loggedOnEvent = 1;
		loggedOffEvent = 1;
		notReadyEvent = 1;
		readyEvent = 1;
		workNotReadyEvent = 1;
		workReadyEvent = 1;
		backInServiceEvent = 1;
		outOfServiceEvent = 1;
		privateEvent = 1;
		routeRequestEvent = 1;
		reRoute = 1;
		routeSelect = 1;
		routeUsedEvent = 1;
		routeEndEvent = 1;
		monitorDevice = 1;
		monitorCall = 1;
		monitorCallsViaDevice = 1;
		changeMonitorFilter = 1;
		monitorStop = 1;
		monitorEnded = 1;
		snapshotDeviceReq = 1;
		snapshotCallReq = 1;
		escapeService = 1;
		privateStatusEvent = 1;
		escapeServiceEvent = 1;
		escapeServiceConf = 1;
		sendPrivateEvent = 1;
		sysStatReq = 1;
		sysStatStart = 1;
		sysStatStop = 1;
		changeSysStatFilter = 1;
		sysStatReqEvent = 1;
		sysStatReqConf = 1;
		sysStatEvent = 1;
	}

	void setAlternateCall(int _alternateCall) {
		alternateCall = _alternateCall;
	}

	void setAnswerCall(int _answerCall) {
		answerCall = _answerCall;
	}

	void setBackInServiceEvent(int _backInServiceEvent) {
		backInServiceEvent = _backInServiceEvent;
	}

	void setCallClearedEvent(int _callClearedEvent) {
		callClearedEvent = _callClearedEvent;
	}

	void setCallCompletion(int _callCompletion) {
		callCompletion = _callCompletion;
	}

	void setCallInformationEvent(int _callInformationEvent) {
		callInformationEvent = _callInformationEvent;
	}

	void setChangeMonitorFilter(int _changeMonitorFilter) {
		changeMonitorFilter = _changeMonitorFilter;
	}

	void setChangeSysStatFilter(int _changeSysStatFilter) {
		changeSysStatFilter = _changeSysStatFilter;
	}

	void setClearCall(int _clearCall) {
		clearCall = _clearCall;
	}

	void setClearConnection(int _clearConnection) {
		clearConnection = _clearConnection;
	}

	void setConferenceCall(int _conferenceCall) {
		conferenceCall = _conferenceCall;
	}

	void setConferencedEvent(int _conferencedEvent) {
		conferencedEvent = _conferencedEvent;
	}

	void setConnectionClearedEvent(int _connectionClearedEvent) {
		connectionClearedEvent = _connectionClearedEvent;
	}

	void setConsultationCall(int _consultationCall) {
		consultationCall = _consultationCall;
	}

	void setDeflectCall(int _deflectCall) {
		deflectCall = _deflectCall;
	}

	void setDeliveredEvent(int _deliveredEvent) {
		deliveredEvent = _deliveredEvent;
	}

	void setDivertedEvent(int _divertedEvent) {
		divertedEvent = _divertedEvent;
	}

	void setDoNotDisturbEvent(int _doNotDisturbEvent) {
		doNotDisturbEvent = _doNotDisturbEvent;
	}

	void setEscapeService(int _escapeService) {
		escapeService = _escapeService;
	}

	void setEscapeServiceConf(int _escapeServiceConf) {
		escapeServiceConf = _escapeServiceConf;
	}

	void setEscapeServiceEvent(int _escapeServiceEvent) {
		escapeServiceEvent = _escapeServiceEvent;
	}

	void setEstablishedEvent(int _establishedEvent) {
		establishedEvent = _establishedEvent;
	}

	void setEventReport(int _eventReport) {
		eventReport = _eventReport;
	}

	void setFailedEvent(int _failedEvent) {
		failedEvent = _failedEvent;
	}

	void setForwardingEvent(int _forwardingEvent) {
		forwardingEvent = _forwardingEvent;
	}

	void setGroupPickupCall(int _groupPickupCall) {
		groupPickupCall = _groupPickupCall;
	}

	void setHeldEvent(int _heldEvent) {
		heldEvent = _heldEvent;
	}

	void setHoldCall(int _holdCall) {
		holdCall = _holdCall;
	}

	void setLoggedOffEvent(int _loggedOffEvent) {
		loggedOffEvent = _loggedOffEvent;
	}

	void setLoggedOnEvent(int _loggedOnEvent) {
		loggedOnEvent = _loggedOnEvent;
	}

	void setLucent(int pdv) {
		isLucentVar = true;
		lucentPDV = pdv;
	}

	void setMakeCall(int _makeCall) {
		makeCall = _makeCall;
	}

	void setMakePredictiveCall(int _makePredictiveCall) {
		makePredictiveCall = _makePredictiveCall;
	}

	void setMessageWaitingEvent(int _messageWaitingEvent) {
		messageWaitingEvent = _messageWaitingEvent;
	}

	void setMonitorCall(int _monitorCall) {
		monitorCall = _monitorCall;
	}

	void setMonitorCallsViaDevice(int _monitorCallsViaDevice) {
		monitorCallsViaDevice = _monitorCallsViaDevice;
	}

	void setMonitorDevice(int _monitorDevice) {
		monitorDevice = _monitorDevice;
	}

	void setMonitorEnded(int _monitorEnded) {
		monitorEnded = _monitorEnded;
	}

	void setMonitorStop(int _monitorStop) {
		monitorStop = _monitorStop;
	}

	void setNetworkReachedEvent(int _networkReachedEvent) {
		networkReachedEvent = _networkReachedEvent;
	}

	void setNotReadyEvent(int _notReadyEvent) {
		notReadyEvent = _notReadyEvent;
	}

	void setOriginatedEvent(int _originatedEvent) {
		originatedEvent = _originatedEvent;
	}

	void setOutOfServiceEvent(int _outOfServiceEvent) {
		outOfServiceEvent = _outOfServiceEvent;
	}

	void setPickupCall(int _pickupCall) {
		pickupCall = _pickupCall;
	}

	void setPrivateEvent(int _privateEvent) {
		privateEvent = _privateEvent;
	}

	void setPrivateStatusEvent(int _privateStatusEvent) {
		privateStatusEvent = _privateStatusEvent;
	}

	void setQueryAgentState(int _queryAgentState) {
		queryAgentState = _queryAgentState;
	}

	void setQueryDeviceInfo(int _queryDeviceInfo) {
		queryDeviceInfo = _queryDeviceInfo;
	}

	void setQueryDnd(int _queryDnd) {
		queryDnd = _queryDnd;
	}

	void setQueryFwd(int _queryFwd) {
		queryFwd = _queryFwd;
	}

	void setQueryLastNumber(int _queryLastNumber) {
		queryLastNumber = _queryLastNumber;
	}

	void setQueryMwi(int _queryMwi) {
		queryMwi = _queryMwi;
	}

	void setQueuedEvent(int _queuedEvent) {
		queuedEvent = _queuedEvent;
	}

	void setReadyEvent(int _readyEvent) {
		readyEvent = _readyEvent;
	}

	void setReconnectCall(int _reconnectCall) {
		reconnectCall = _reconnectCall;
	}

	void setReRoute(int _reRoute) {
		reRoute = _reRoute;
	}

	void setRetrieveCall(int _retrieveCall) {
		retrieveCall = _retrieveCall;
	}

	void setRetrievedEvent(int _retrievedEvent) {
		retrievedEvent = _retrievedEvent;
	}

	void setRouteEndEvent(int _routeEndEvent) {
		routeEndEvent = _routeEndEvent;
	}

	void setRouteRequestEvent(int _routeRequestEvent) {
		routeRequestEvent = _routeRequestEvent;
	}

	void setRouteSelect(int _routeSelect) {
		routeSelect = _routeSelect;
	}

	void setRouteUsedEvent(int _routeUsedEvent) {
		routeUsedEvent = _routeUsedEvent;
	}

	void setSendPrivateEvent(int _sendPrivateEvent) {
		sendPrivateEvent = _sendPrivateEvent;
	}

	void setServiceInitiatedEvent(int _serviceInitiatedEvent) {
		serviceInitiatedEvent = _serviceInitiatedEvent;
	}

	void setSetAgentState(int _setAgentState) {
		setAgentState = _setAgentState;
	}

	void setSetDnd(int _setDnd) {
		setDnd = _setDnd;
	}

	void setSetFwd(int _setFwd) {
		setFwd = _setFwd;
	}

	void setSetMwi(int _setMwi) {
		setMwi = _setMwi;
	}

	void setSnapshotCallReq(int _snapshotCallReq) {
		snapshotCallReq = _snapshotCallReq;
	}

	void setSnapshotDeviceReq(int _snapshotDeviceReq) {
		snapshotDeviceReq = _snapshotDeviceReq;
	}

	void setSysStatEvent(int _sysStatEvent) {
		sysStatEvent = _sysStatEvent;
	}

	void setSysStatReq(int _sysStatReq) {
		sysStatReq = _sysStatReq;
	}

	void setSysStatReqConf(int _sysStatReqConf) {
		sysStatReqConf = _sysStatReqConf;
	}

	void setSysStatReqEvent(int _sysStatReqEvent) {
		sysStatReqEvent = _sysStatReqEvent;
	}

	void setSysStatStart(int _sysStatStart) {
		sysStatStart = _sysStatStart;
	}

	void setSysStatStop(int _sysStatStop) {
		sysStatStop = _sysStatStop;
	}

	void setTransferCall(int _transferCall) {
		transferCall = _transferCall;
	}

	void setTransferredEvent(int _transferredEvent) {
		transferredEvent = _transferredEvent;
	}

	void setWorkNotReadyEvent(int _workNotReadyEvent) {
		workNotReadyEvent = _workNotReadyEvent;
	}

	void setWorkReadyEvent(int _workReadyEvent) {
		workReadyEvent = _workReadyEvent;
	}
}

