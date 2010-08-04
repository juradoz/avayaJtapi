/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ public final class TSCapabilities
/*     */ {
/* 926 */   boolean isLucentVar = false;
/*     */ 
/* 929 */   int lucentPDV = 0;
/*     */   int addParty;
/*     */   int alternateCall;
/*     */   int answerCall;
/*     */   int callCompletion;
/*     */   int clearCall;
/*     */   int clearConnection;
/*     */   int conferenceCall;
/*     */   int consultationCall;
/*     */   int deflectCall;
/*     */   int pickupCall;
/*     */   int groupPickupCall;
/*     */   int holdCall;
/*     */   int makeCall;
/*     */   int makePredictiveCall;
/*     */   int queryMwi;
/*     */   int queryDnd;
/*     */   int queryFwd;
/*     */   int queryAgentState;
/*     */   int queryLastNumber;
/*     */   int queryDeviceInfo;
/*     */   int reconnectCall;
/*     */   int retrieveCall;
/*     */   int setMwi;
/*     */   int setDnd;
/*     */   int setFwd;
/*     */   int setAgentState;
/*     */   int transferCall;
/*     */   int eventReport;
/*     */   int callClearedEvent;
/*     */   int conferencedEvent;
/*     */   int connectionClearedEvent;
/*     */   int deliveredEvent;
/*     */   int divertedEvent;
/*     */   int establishedEvent;
/*     */   int failedEvent;
/*     */   int heldEvent;
/*     */   int networkReachedEvent;
/*     */   int originatedEvent;
/*     */   int queuedEvent;
/*     */   int retrievedEvent;
/*     */   int serviceInitiatedEvent;
/*     */   int transferredEvent;
/*     */   int callInformationEvent;
/*     */   int doNotDisturbEvent;
/*     */   int forwardingEvent;
/*     */   int messageWaitingEvent;
/*     */   int loggedOnEvent;
/*     */   int loggedOffEvent;
/*     */   int notReadyEvent;
/*     */   int readyEvent;
/*     */   int workNotReadyEvent;
/*     */   int workReadyEvent;
/*     */   int backInServiceEvent;
/*     */   int outOfServiceEvent;
/*     */   int privateEvent;
/*     */   int routeRequestEvent;
/*     */   int reRoute;
/*     */   int routeSelect;
/*     */   int routeUsedEvent;
/*     */   int routeEndEvent;
/*     */   int monitorDevice;
/*     */   int monitorCall;
/*     */   int monitorCallsViaDevice;
/*     */   int changeMonitorFilter;
/*     */   int monitorStop;
/*     */   int monitorEnded;
/*     */   int snapshotDeviceReq;
/*     */   int snapshotCallReq;
/*     */   int escapeService;
/*     */   int privateStatusEvent;
/*     */   int escapeServiceEvent;
/*     */   int escapeServiceConf;
/*     */   int sendPrivateEvent;
/*     */   int sysStatReq;
/*     */   int sysStatStart;
/*     */   int sysStatStop;
/*     */   int changeSysStatFilter;
/*     */   int sysStatReqEvent;
/*     */   int sysStatReqConf;
/*     */   int sysStatEvent;
/*     */ 
/*     */   public int getAddParty()
/*     */   {
/*  22 */     return this.addParty;
/*     */   }
/*     */ 
/*     */   void setAddParty(int _addParty)
/*     */   {
/*  27 */     this.addParty = _addParty;
/*     */   }
/*     */ 
/*     */   public int getAlternateCall()
/*     */   {
/*  32 */     return this.alternateCall;
/*     */   }
/*     */ 
/*     */   void setAlternateCall(int _alternateCall)
/*     */   {
/*  37 */     this.alternateCall = _alternateCall;
/*     */   }
/*     */ 
/*     */   public int getAnswerCall()
/*     */   {
/*  42 */     return this.answerCall;
/*     */   }
/*     */ 
/*     */   void setAnswerCall(int _answerCall)
/*     */   {
/*  47 */     this.answerCall = _answerCall;
/*     */   }
/*     */ 
/*     */   public int getCallCompletion()
/*     */   {
/*  52 */     return this.callCompletion;
/*     */   }
/*     */ 
/*     */   void setCallCompletion(int _callCompletion)
/*     */   {
/*  57 */     this.callCompletion = _callCompletion;
/*     */   }
/*     */ 
/*     */   public int getClearCall()
/*     */   {
/*  62 */     return this.clearCall;
/*     */   }
/*     */ 
/*     */   void setClearCall(int _clearCall)
/*     */   {
/*  67 */     this.clearCall = _clearCall;
/*     */   }
/*     */ 
/*     */   public int getClearConnection()
/*     */   {
/*  72 */     return this.clearConnection;
/*     */   }
/*     */ 
/*     */   void setClearConnection(int _clearConnection)
/*     */   {
/*  77 */     this.clearConnection = _clearConnection;
/*     */   }
/*     */ 
/*     */   public int getConferenceCall()
/*     */   {
/*  82 */     return this.conferenceCall;
/*     */   }
/*     */ 
/*     */   void setConferenceCall(int _conferenceCall)
/*     */   {
/*  87 */     this.conferenceCall = _conferenceCall;
/*     */   }
/*     */ 
/*     */   public int getConsultationCall()
/*     */   {
/*  92 */     return this.consultationCall;
/*     */   }
/*     */ 
/*     */   void setConsultationCall(int _consultationCall)
/*     */   {
/*  97 */     this.consultationCall = _consultationCall;
/*     */   }
/*     */ 
/*     */   public int getDeflectCall()
/*     */   {
/* 102 */     return this.deflectCall;
/*     */   }
/*     */ 
/*     */   void setDeflectCall(int _deflectCall)
/*     */   {
/* 107 */     this.deflectCall = _deflectCall;
/*     */   }
/*     */ 
/*     */   public int getPickupCall()
/*     */   {
/* 112 */     return this.pickupCall;
/*     */   }
/*     */ 
/*     */   void setPickupCall(int _pickupCall)
/*     */   {
/* 117 */     this.pickupCall = _pickupCall;
/*     */   }
/*     */ 
/*     */   public int getGroupPickupCall()
/*     */   {
/* 123 */     return this.groupPickupCall;
/*     */   }
/*     */ 
/*     */   void setGroupPickupCall(int _groupPickupCall)
/*     */   {
/* 128 */     this.groupPickupCall = _groupPickupCall;
/*     */   }
/*     */ 
/*     */   public int getHoldCall()
/*     */   {
/* 133 */     return this.holdCall;
/*     */   }
/*     */ 
/*     */   void setHoldCall(int _holdCall)
/*     */   {
/* 138 */     this.holdCall = _holdCall;
/*     */   }
/*     */ 
/*     */   public int getMakeCall()
/*     */   {
/* 143 */     return this.makeCall;
/*     */   }
/*     */ 
/*     */   void setMakeCall(int _makeCall)
/*     */   {
/* 148 */     this.makeCall = _makeCall;
/*     */   }
/*     */ 
/*     */   public int getMakePredictiveCall()
/*     */   {
/* 153 */     return this.makePredictiveCall;
/*     */   }
/*     */ 
/*     */   void setMakePredictiveCall(int _makePredictiveCall)
/*     */   {
/* 158 */     this.makePredictiveCall = _makePredictiveCall;
/*     */   }
/*     */ 
/*     */   public int getQueryMwi()
/*     */   {
/* 163 */     return this.queryMwi;
/*     */   }
/*     */ 
/*     */   void setQueryMwi(int _queryMwi)
/*     */   {
/* 168 */     this.queryMwi = _queryMwi;
/*     */   }
/*     */ 
/*     */   public int getQueryDnd()
/*     */   {
/* 173 */     return this.queryDnd;
/*     */   }
/*     */ 
/*     */   void setQueryDnd(int _queryDnd)
/*     */   {
/* 178 */     this.queryDnd = _queryDnd;
/*     */   }
/*     */ 
/*     */   public int getQueryFwd()
/*     */   {
/* 183 */     return this.queryFwd;
/*     */   }
/*     */ 
/*     */   void setQueryFwd(int _queryFwd)
/*     */   {
/* 188 */     this.queryFwd = _queryFwd;
/*     */   }
/*     */ 
/*     */   public int getQueryAgentState()
/*     */   {
/* 193 */     return this.queryAgentState;
/*     */   }
/*     */ 
/*     */   void setQueryAgentState(int _queryAgentState)
/*     */   {
/* 198 */     this.queryAgentState = _queryAgentState;
/*     */   }
/*     */ 
/*     */   public int getQueryLastNumber()
/*     */   {
/* 203 */     return this.queryLastNumber;
/*     */   }
/*     */ 
/*     */   void setQueryLastNumber(int _queryLastNumber)
/*     */   {
/* 208 */     this.queryLastNumber = _queryLastNumber;
/*     */   }
/*     */ 
/*     */   public int getQueryDeviceInfo()
/*     */   {
/* 213 */     return this.queryDeviceInfo;
/*     */   }
/*     */ 
/*     */   void setQueryDeviceInfo(int _queryDeviceInfo)
/*     */   {
/* 218 */     this.queryDeviceInfo = _queryDeviceInfo;
/*     */   }
/*     */ 
/*     */   public int getReconnectCall()
/*     */   {
/* 223 */     return this.reconnectCall;
/*     */   }
/*     */ 
/*     */   void setReconnectCall(int _reconnectCall)
/*     */   {
/* 228 */     this.reconnectCall = _reconnectCall;
/*     */   }
/*     */ 
/*     */   public int getRetrieveCall()
/*     */   {
/* 233 */     return this.retrieveCall;
/*     */   }
/*     */ 
/*     */   void setRetrieveCall(int _retrieveCall)
/*     */   {
/* 238 */     this.retrieveCall = _retrieveCall;
/*     */   }
/*     */ 
/*     */   public int getSetMwi()
/*     */   {
/* 243 */     return this.setMwi;
/*     */   }
/*     */ 
/*     */   void setSetMwi(int _setMwi)
/*     */   {
/* 248 */     this.setMwi = _setMwi;
/*     */   }
/*     */ 
/*     */   public int getSetDnd()
/*     */   {
/* 253 */     return this.setDnd;
/*     */   }
/*     */ 
/*     */   void setSetDnd(int _setDnd)
/*     */   {
/* 258 */     this.setDnd = _setDnd;
/*     */   }
/*     */ 
/*     */   public int getSetFwd()
/*     */   {
/* 263 */     return this.setFwd;
/*     */   }
/*     */ 
/*     */   void setSetFwd(int _setFwd)
/*     */   {
/* 268 */     this.setFwd = _setFwd;
/*     */   }
/*     */ 
/*     */   public int getSetAgentState()
/*     */   {
/* 273 */     return this.setAgentState;
/*     */   }
/*     */ 
/*     */   void setSetAgentState(int _setAgentState)
/*     */   {
/* 278 */     this.setAgentState = _setAgentState;
/*     */   }
/*     */ 
/*     */   public int getTransferCall()
/*     */   {
/* 283 */     return this.transferCall;
/*     */   }
/*     */ 
/*     */   void setTransferCall(int _transferCall)
/*     */   {
/* 288 */     this.transferCall = _transferCall;
/*     */   }
/*     */ 
/*     */   public int getEventReport()
/*     */   {
/* 293 */     return this.eventReport;
/*     */   }
/*     */ 
/*     */   void setEventReport(int _eventReport)
/*     */   {
/* 298 */     this.eventReport = _eventReport;
/*     */   }
/*     */ 
/*     */   public int getCallClearedEvent()
/*     */   {
/* 303 */     return this.callClearedEvent;
/*     */   }
/*     */ 
/*     */   void setCallClearedEvent(int _callClearedEvent)
/*     */   {
/* 308 */     this.callClearedEvent = _callClearedEvent;
/*     */   }
/*     */ 
/*     */   public int getConferencedEvent()
/*     */   {
/* 313 */     return this.conferencedEvent;
/*     */   }
/*     */ 
/*     */   void setConferencedEvent(int _conferencedEvent)
/*     */   {
/* 318 */     this.conferencedEvent = _conferencedEvent;
/*     */   }
/*     */ 
/*     */   public int getConnectionClearedEvent()
/*     */   {
/* 323 */     return this.connectionClearedEvent;
/*     */   }
/*     */ 
/*     */   void setConnectionClearedEvent(int _connectionClearedEvent)
/*     */   {
/* 328 */     this.connectionClearedEvent = _connectionClearedEvent;
/*     */   }
/*     */ 
/*     */   public int getDeliveredEvent()
/*     */   {
/* 333 */     return this.deliveredEvent;
/*     */   }
/*     */ 
/*     */   void setDeliveredEvent(int _deliveredEvent)
/*     */   {
/* 338 */     this.deliveredEvent = _deliveredEvent;
/*     */   }
/*     */ 
/*     */   public int getDivertedEvent()
/*     */   {
/* 343 */     return this.divertedEvent;
/*     */   }
/*     */ 
/*     */   void setDivertedEvent(int _divertedEvent)
/*     */   {
/* 348 */     this.divertedEvent = _divertedEvent;
/*     */   }
/*     */ 
/*     */   public int getEstablishedEvent()
/*     */   {
/* 353 */     return this.establishedEvent;
/*     */   }
/*     */ 
/*     */   void setEstablishedEvent(int _establishedEvent)
/*     */   {
/* 358 */     this.establishedEvent = _establishedEvent;
/*     */   }
/*     */ 
/*     */   public int getFailedEvent()
/*     */   {
/* 363 */     return this.failedEvent;
/*     */   }
/*     */ 
/*     */   void setFailedEvent(int _failedEvent)
/*     */   {
/* 368 */     this.failedEvent = _failedEvent;
/*     */   }
/*     */ 
/*     */   public int getHeldEvent()
/*     */   {
/* 373 */     return this.heldEvent;
/*     */   }
/*     */ 
/*     */   void setHeldEvent(int _heldEvent)
/*     */   {
/* 378 */     this.heldEvent = _heldEvent;
/*     */   }
/*     */ 
/*     */   public int getNetworkReachedEvent()
/*     */   {
/* 383 */     return this.networkReachedEvent;
/*     */   }
/*     */ 
/*     */   void setNetworkReachedEvent(int _networkReachedEvent)
/*     */   {
/* 388 */     this.networkReachedEvent = _networkReachedEvent;
/*     */   }
/*     */ 
/*     */   public int getOriginatedEvent()
/*     */   {
/* 393 */     return this.originatedEvent;
/*     */   }
/*     */ 
/*     */   void setOriginatedEvent(int _originatedEvent)
/*     */   {
/* 398 */     this.originatedEvent = _originatedEvent;
/*     */   }
/*     */ 
/*     */   public int getQueuedEvent()
/*     */   {
/* 403 */     return this.queuedEvent;
/*     */   }
/*     */ 
/*     */   void setQueuedEvent(int _queuedEvent)
/*     */   {
/* 408 */     this.queuedEvent = _queuedEvent;
/*     */   }
/*     */ 
/*     */   public int getRetrievedEvent()
/*     */   {
/* 413 */     return this.retrievedEvent;
/*     */   }
/*     */ 
/*     */   void setRetrievedEvent(int _retrievedEvent)
/*     */   {
/* 418 */     this.retrievedEvent = _retrievedEvent;
/*     */   }
/*     */ 
/*     */   public int getServiceInitiatedEvent()
/*     */   {
/* 423 */     return this.serviceInitiatedEvent;
/*     */   }
/*     */ 
/*     */   void setServiceInitiatedEvent(int _serviceInitiatedEvent)
/*     */   {
/* 428 */     this.serviceInitiatedEvent = _serviceInitiatedEvent;
/*     */   }
/*     */ 
/*     */   public int getTransferredEvent()
/*     */   {
/* 433 */     return this.transferredEvent;
/*     */   }
/*     */ 
/*     */   void setTransferredEvent(int _transferredEvent)
/*     */   {
/* 438 */     this.transferredEvent = _transferredEvent;
/*     */   }
/*     */ 
/*     */   public int getCallInformationEvent()
/*     */   {
/* 443 */     return this.callInformationEvent;
/*     */   }
/*     */ 
/*     */   void setCallInformationEvent(int _callInformationEvent)
/*     */   {
/* 448 */     this.callInformationEvent = _callInformationEvent;
/*     */   }
/*     */ 
/*     */   public int getDoNotDisturbEvent()
/*     */   {
/* 453 */     return this.doNotDisturbEvent;
/*     */   }
/*     */ 
/*     */   void setDoNotDisturbEvent(int _doNotDisturbEvent)
/*     */   {
/* 458 */     this.doNotDisturbEvent = _doNotDisturbEvent;
/*     */   }
/*     */ 
/*     */   public int getForwardingEvent()
/*     */   {
/* 463 */     return this.forwardingEvent;
/*     */   }
/*     */ 
/*     */   void setForwardingEvent(int _forwardingEvent)
/*     */   {
/* 468 */     this.forwardingEvent = _forwardingEvent;
/*     */   }
/*     */ 
/*     */   public int getMessageWaitingEvent()
/*     */   {
/* 473 */     return this.messageWaitingEvent;
/*     */   }
/*     */ 
/*     */   void setMessageWaitingEvent(int _messageWaitingEvent)
/*     */   {
/* 478 */     this.messageWaitingEvent = _messageWaitingEvent;
/*     */   }
/*     */ 
/*     */   public int getLoggedOnEvent()
/*     */   {
/* 483 */     return this.loggedOnEvent;
/*     */   }
/*     */ 
/*     */   void setLoggedOnEvent(int _loggedOnEvent)
/*     */   {
/* 488 */     this.loggedOnEvent = _loggedOnEvent;
/*     */   }
/*     */ 
/*     */   public int getLoggedOffEvent()
/*     */   {
/* 493 */     return this.loggedOffEvent;
/*     */   }
/*     */ 
/*     */   void setLoggedOffEvent(int _loggedOffEvent)
/*     */   {
/* 498 */     this.loggedOffEvent = _loggedOffEvent;
/*     */   }
/*     */ 
/*     */   public int getNotReadyEvent()
/*     */   {
/* 503 */     return this.notReadyEvent;
/*     */   }
/*     */ 
/*     */   void setNotReadyEvent(int _notReadyEvent)
/*     */   {
/* 508 */     this.notReadyEvent = _notReadyEvent;
/*     */   }
/*     */ 
/*     */   public int getReadyEvent()
/*     */   {
/* 513 */     return this.readyEvent;
/*     */   }
/*     */ 
/*     */   void setReadyEvent(int _readyEvent)
/*     */   {
/* 518 */     this.readyEvent = _readyEvent;
/*     */   }
/*     */ 
/*     */   public int getWorkNotReadyEvent()
/*     */   {
/* 523 */     return this.workNotReadyEvent;
/*     */   }
/*     */ 
/*     */   void setWorkNotReadyEvent(int _workNotReadyEvent)
/*     */   {
/* 528 */     this.workNotReadyEvent = _workNotReadyEvent;
/*     */   }
/*     */ 
/*     */   public int getWorkReadyEvent()
/*     */   {
/* 533 */     return this.workReadyEvent;
/*     */   }
/*     */ 
/*     */   void setWorkReadyEvent(int _workReadyEvent)
/*     */   {
/* 538 */     this.workReadyEvent = _workReadyEvent;
/*     */   }
/*     */ 
/*     */   public int getBackInServiceEvent()
/*     */   {
/* 543 */     return this.backInServiceEvent;
/*     */   }
/*     */ 
/*     */   void setBackInServiceEvent(int _backInServiceEvent)
/*     */   {
/* 548 */     this.backInServiceEvent = _backInServiceEvent;
/*     */   }
/*     */ 
/*     */   public int getOutOfServiceEvent()
/*     */   {
/* 553 */     return this.outOfServiceEvent;
/*     */   }
/*     */ 
/*     */   void setOutOfServiceEvent(int _outOfServiceEvent)
/*     */   {
/* 558 */     this.outOfServiceEvent = _outOfServiceEvent;
/*     */   }
/*     */ 
/*     */   public int getPrivateEvent()
/*     */   {
/* 563 */     return this.privateEvent;
/*     */   }
/*     */ 
/*     */   void setPrivateEvent(int _privateEvent)
/*     */   {
/* 568 */     this.privateEvent = _privateEvent;
/*     */   }
/*     */ 
/*     */   public int getRouteRequestEvent()
/*     */   {
/* 573 */     return this.routeRequestEvent;
/*     */   }
/*     */ 
/*     */   void setRouteRequestEvent(int _routeRequestEvent)
/*     */   {
/* 578 */     this.routeRequestEvent = _routeRequestEvent;
/*     */   }
/*     */ 
/*     */   public int getReRoute()
/*     */   {
/* 583 */     return this.reRoute;
/*     */   }
/*     */ 
/*     */   void setReRoute(int _reRoute)
/*     */   {
/* 588 */     this.reRoute = _reRoute;
/*     */   }
/*     */ 
/*     */   public int getRouteSelect()
/*     */   {
/* 593 */     return this.routeSelect;
/*     */   }
/*     */ 
/*     */   void setRouteSelect(int _routeSelect)
/*     */   {
/* 598 */     this.routeSelect = _routeSelect;
/*     */   }
/*     */ 
/*     */   public int getRouteUsedEvent()
/*     */   {
/* 603 */     return this.routeUsedEvent;
/*     */   }
/*     */ 
/*     */   void setRouteUsedEvent(int _routeUsedEvent)
/*     */   {
/* 608 */     this.routeUsedEvent = _routeUsedEvent;
/*     */   }
/*     */ 
/*     */   public int getRouteEndEvent()
/*     */   {
/* 613 */     return this.routeEndEvent;
/*     */   }
/*     */ 
/*     */   void setRouteEndEvent(int _routeEndEvent)
/*     */   {
/* 618 */     this.routeEndEvent = _routeEndEvent;
/*     */   }
/*     */ 
/*     */   public int getMonitorDevice()
/*     */   {
/* 623 */     return this.monitorDevice;
/*     */   }
/*     */ 
/*     */   void setMonitorDevice(int _monitorDevice)
/*     */   {
/* 628 */     this.monitorDevice = _monitorDevice;
/*     */   }
/*     */ 
/*     */   public int getMonitorCall()
/*     */   {
/* 633 */     return this.monitorCall;
/*     */   }
/*     */ 
/*     */   void setMonitorCall(int _monitorCall)
/*     */   {
/* 638 */     this.monitorCall = _monitorCall;
/*     */   }
/*     */ 
/*     */   public int getMonitorCallsViaDevice()
/*     */   {
/* 643 */     return this.monitorCallsViaDevice;
/*     */   }
/*     */ 
/*     */   void setMonitorCallsViaDevice(int _monitorCallsViaDevice)
/*     */   {
/* 648 */     this.monitorCallsViaDevice = _monitorCallsViaDevice;
/*     */   }
/*     */ 
/*     */   public int getChangeMonitorFilter()
/*     */   {
/* 653 */     return this.changeMonitorFilter;
/*     */   }
/*     */ 
/*     */   void setChangeMonitorFilter(int _changeMonitorFilter)
/*     */   {
/* 658 */     this.changeMonitorFilter = _changeMonitorFilter;
/*     */   }
/*     */ 
/*     */   public int getMonitorStop()
/*     */   {
/* 663 */     return this.monitorStop;
/*     */   }
/*     */ 
/*     */   void setMonitorStop(int _monitorStop)
/*     */   {
/* 668 */     this.monitorStop = _monitorStop;
/*     */   }
/*     */ 
/*     */   public int getMonitorEnded()
/*     */   {
/* 673 */     return this.monitorEnded;
/*     */   }
/*     */ 
/*     */   void setMonitorEnded(int _monitorEnded)
/*     */   {
/* 678 */     this.monitorEnded = _monitorEnded;
/*     */   }
/*     */ 
/*     */   public int getSnapshotDeviceReq()
/*     */   {
/* 683 */     return this.snapshotDeviceReq;
/*     */   }
/*     */ 
/*     */   void setSnapshotDeviceReq(int _snapshotDeviceReq)
/*     */   {
/* 688 */     this.snapshotDeviceReq = _snapshotDeviceReq;
/*     */   }
/*     */ 
/*     */   public int getSnapshotCallReq()
/*     */   {
/* 693 */     return this.snapshotCallReq;
/*     */   }
/*     */ 
/*     */   void setSnapshotCallReq(int _snapshotCallReq)
/*     */   {
/* 698 */     this.snapshotCallReq = _snapshotCallReq;
/*     */   }
/*     */ 
/*     */   public int getEscapeService()
/*     */   {
/* 703 */     return this.escapeService;
/*     */   }
/*     */ 
/*     */   void setEscapeService(int _escapeService)
/*     */   {
/* 708 */     this.escapeService = _escapeService;
/*     */   }
/*     */ 
/*     */   public int getPrivateStatusEvent()
/*     */   {
/* 713 */     return this.privateStatusEvent;
/*     */   }
/*     */ 
/*     */   void setPrivateStatusEvent(int _privateStatusEvent)
/*     */   {
/* 718 */     this.privateStatusEvent = _privateStatusEvent;
/*     */   }
/*     */ 
/*     */   public int getEscapeServiceEvent()
/*     */   {
/* 723 */     return this.escapeServiceEvent;
/*     */   }
/*     */ 
/*     */   void setEscapeServiceEvent(int _escapeServiceEvent)
/*     */   {
/* 728 */     this.escapeServiceEvent = _escapeServiceEvent;
/*     */   }
/*     */ 
/*     */   public int getEscapeServiceConf()
/*     */   {
/* 733 */     return this.escapeServiceConf;
/*     */   }
/*     */ 
/*     */   void setEscapeServiceConf(int _escapeServiceConf)
/*     */   {
/* 738 */     this.escapeServiceConf = _escapeServiceConf;
/*     */   }
/*     */ 
/*     */   public int getSendPrivateEvent()
/*     */   {
/* 743 */     return this.sendPrivateEvent;
/*     */   }
/*     */ 
/*     */   void setSendPrivateEvent(int _sendPrivateEvent)
/*     */   {
/* 748 */     this.sendPrivateEvent = _sendPrivateEvent;
/*     */   }
/*     */ 
/*     */   public int getSysStatReq()
/*     */   {
/* 753 */     return this.sysStatReq;
/*     */   }
/*     */ 
/*     */   void setSysStatReq(int _sysStatReq)
/*     */   {
/* 758 */     this.sysStatReq = _sysStatReq;
/*     */   }
/*     */ 
/*     */   public int getSysStatStart()
/*     */   {
/* 763 */     return this.sysStatStart;
/*     */   }
/*     */ 
/*     */   void setSysStatStart(int _sysStatStart)
/*     */   {
/* 768 */     this.sysStatStart = _sysStatStart;
/*     */   }
/*     */ 
/*     */   public int getSysStatStop()
/*     */   {
/* 773 */     return this.sysStatStop;
/*     */   }
/*     */ 
/*     */   void setSysStatStop(int _sysStatStop)
/*     */   {
/* 778 */     this.sysStatStop = _sysStatStop;
/*     */   }
/*     */ 
/*     */   public int getChangeSysStatFilter()
/*     */   {
/* 783 */     return this.changeSysStatFilter;
/*     */   }
/*     */ 
/*     */   void setChangeSysStatFilter(int _changeSysStatFilter)
/*     */   {
/* 788 */     this.changeSysStatFilter = _changeSysStatFilter;
/*     */   }
/*     */ 
/*     */   public int getSysStatReqEvent()
/*     */   {
/* 793 */     return this.sysStatReqEvent;
/*     */   }
/*     */ 
/*     */   void setSysStatReqEvent(int _sysStatReqEvent)
/*     */   {
/* 798 */     this.sysStatReqEvent = _sysStatReqEvent;
/*     */   }
/*     */ 
/*     */   public int getSysStatReqConf()
/*     */   {
/* 803 */     return this.sysStatReqConf;
/*     */   }
/*     */ 
/*     */   void setSysStatReqConf(int _sysStatReqConf)
/*     */   {
/* 808 */     this.sysStatReqConf = _sysStatReqConf;
/*     */   }
/*     */ 
/*     */   public int getSysStatEvent()
/*     */   {
/* 813 */     return this.sysStatEvent;
/*     */   }
/*     */ 
/*     */   void setSysStatEvent(int _sysStatEvent)
/*     */   {
/* 818 */     this.sysStatEvent = _sysStatEvent;
/*     */   }
/*     */ 
/*     */   public boolean isLucent()
/*     */   {
/* 824 */     return this.isLucentVar;
/*     */   }
/*     */ 
/*     */   public boolean isLucentV8()
/*     */   {
/* 831 */     return (this.isLucentVar) && (this.lucentPDV >= 8);
/*     */   }
/*     */ 
/*     */   void setLucent(int pdv)
/*     */   {
/* 836 */     this.isLucentVar = true;
/* 837 */     this.lucentPDV = pdv;
/*     */   }
/*     */ 
/*     */   void setAll()
/*     */   {
/* 842 */     this.addParty = 1;
/* 843 */     this.alternateCall = 1;
/* 844 */     this.answerCall = 1;
/* 845 */     this.callCompletion = 1;
/* 846 */     this.clearCall = 1;
/* 847 */     this.clearConnection = 1;
/* 848 */     this.conferenceCall = 1;
/* 849 */     this.consultationCall = 1;
/* 850 */     this.deflectCall = 1;
/* 851 */     this.pickupCall = 1;
/* 852 */     this.groupPickupCall = 1;
/* 853 */     this.holdCall = 1;
/* 854 */     this.makeCall = 1;
/* 855 */     this.makePredictiveCall = 1;
/* 856 */     this.queryMwi = 1;
/* 857 */     this.queryDnd = 1;
/* 858 */     this.queryFwd = 1;
/* 859 */     this.queryAgentState = 1;
/* 860 */     this.queryLastNumber = 1;
/* 861 */     this.queryDeviceInfo = 1;
/* 862 */     this.reconnectCall = 1;
/* 863 */     this.retrieveCall = 1;
/* 864 */     this.setMwi = 1;
/* 865 */     this.setDnd = 1;
/* 866 */     this.setFwd = 1;
/* 867 */     this.setAgentState = 1;
/* 868 */     this.transferCall = 1;
/* 869 */     this.eventReport = 1;
/* 870 */     this.callClearedEvent = 1;
/* 871 */     this.conferencedEvent = 1;
/* 872 */     this.connectionClearedEvent = 1;
/* 873 */     this.deliveredEvent = 1;
/* 874 */     this.divertedEvent = 1;
/* 875 */     this.establishedEvent = 1;
/* 876 */     this.failedEvent = 1;
/* 877 */     this.heldEvent = 1;
/* 878 */     this.networkReachedEvent = 1;
/* 879 */     this.originatedEvent = 1;
/* 880 */     this.queuedEvent = 1;
/* 881 */     this.retrievedEvent = 1;
/* 882 */     this.serviceInitiatedEvent = 1;
/* 883 */     this.transferredEvent = 1;
/* 884 */     this.callInformationEvent = 1;
/* 885 */     this.doNotDisturbEvent = 1;
/* 886 */     this.forwardingEvent = 1;
/* 887 */     this.messageWaitingEvent = 1;
/* 888 */     this.loggedOnEvent = 1;
/* 889 */     this.loggedOffEvent = 1;
/* 890 */     this.notReadyEvent = 1;
/* 891 */     this.readyEvent = 1;
/* 892 */     this.workNotReadyEvent = 1;
/* 893 */     this.workReadyEvent = 1;
/* 894 */     this.backInServiceEvent = 1;
/* 895 */     this.outOfServiceEvent = 1;
/* 896 */     this.privateEvent = 1;
/* 897 */     this.routeRequestEvent = 1;
/* 898 */     this.reRoute = 1;
/* 899 */     this.routeSelect = 1;
/* 900 */     this.routeUsedEvent = 1;
/* 901 */     this.routeEndEvent = 1;
/* 902 */     this.monitorDevice = 1;
/* 903 */     this.monitorCall = 1;
/* 904 */     this.monitorCallsViaDevice = 1;
/* 905 */     this.changeMonitorFilter = 1;
/* 906 */     this.monitorStop = 1;
/* 907 */     this.monitorEnded = 1;
/* 908 */     this.snapshotDeviceReq = 1;
/* 909 */     this.snapshotCallReq = 1;
/* 910 */     this.escapeService = 1;
/* 911 */     this.privateStatusEvent = 1;
/* 912 */     this.escapeServiceEvent = 1;
/* 913 */     this.escapeServiceConf = 1;
/* 914 */     this.sendPrivateEvent = 1;
/* 915 */     this.sysStatReq = 1;
/* 916 */     this.sysStatStart = 1;
/* 917 */     this.sysStatStop = 1;
/* 918 */     this.changeSysStatFilter = 1;
/* 919 */     this.sysStatReqEvent = 1;
/* 920 */     this.sysStatReqConf = 1;
/* 921 */     this.sysStatEvent = 1;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSCapabilities
 * JD-Core Version:    0.5.4
 */