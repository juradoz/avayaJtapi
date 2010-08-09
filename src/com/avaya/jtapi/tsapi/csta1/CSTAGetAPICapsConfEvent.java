 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAGetAPICapsConfEvent extends CSTAConfirmation
 {
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
   public static final int PDU = 125;
 
   public static CSTAGetAPICapsConfEvent decode(InputStream in)
   {
     CSTAGetAPICapsConfEvent _this = new CSTAGetAPICapsConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.alternateCall = ASNInteger.decode(memberStream);
     this.answerCall = ASNInteger.decode(memberStream);
     this.callCompletion = ASNInteger.decode(memberStream);
     this.clearCall = ASNInteger.decode(memberStream);
     this.clearConnection = ASNInteger.decode(memberStream);
     this.conferenceCall = ASNInteger.decode(memberStream);
     this.consultationCall = ASNInteger.decode(memberStream);
     this.deflectCall = ASNInteger.decode(memberStream);
     this.pickupCall = ASNInteger.decode(memberStream);
     this.groupPickupCall = ASNInteger.decode(memberStream);
     this.holdCall = ASNInteger.decode(memberStream);
     this.makeCall = ASNInteger.decode(memberStream);
     this.makePredictiveCall = ASNInteger.decode(memberStream);
     this.queryMwi = ASNInteger.decode(memberStream);
     this.queryDnd = ASNInteger.decode(memberStream);
     this.queryFwd = ASNInteger.decode(memberStream);
     this.queryAgentState = ASNInteger.decode(memberStream);
     this.queryLastNumber = ASNInteger.decode(memberStream);
     this.queryDeviceInfo = ASNInteger.decode(memberStream);
     this.reconnectCall = ASNInteger.decode(memberStream);
     this.retrieveCall = ASNInteger.decode(memberStream);
     this.setMwi = ASNInteger.decode(memberStream);
     this.setDnd = ASNInteger.decode(memberStream);
     this.setFwd = ASNInteger.decode(memberStream);
     this.setAgentState = ASNInteger.decode(memberStream);
     this.transferCall = ASNInteger.decode(memberStream);
     this.eventReport = ASNInteger.decode(memberStream);
     this.callClearedEvent = ASNInteger.decode(memberStream);
     this.conferencedEvent = ASNInteger.decode(memberStream);
     this.connectionClearedEvent = ASNInteger.decode(memberStream);
     this.deliveredEvent = ASNInteger.decode(memberStream);
     this.divertedEvent = ASNInteger.decode(memberStream);
     this.establishedEvent = ASNInteger.decode(memberStream);
     this.failedEvent = ASNInteger.decode(memberStream);
     this.heldEvent = ASNInteger.decode(memberStream);
     this.networkReachedEvent = ASNInteger.decode(memberStream);
     this.originatedEvent = ASNInteger.decode(memberStream);
     this.queuedEvent = ASNInteger.decode(memberStream);
     this.retrievedEvent = ASNInteger.decode(memberStream);
     this.serviceInitiatedEvent = ASNInteger.decode(memberStream);
     this.transferredEvent = ASNInteger.decode(memberStream);
     this.callInformationEvent = ASNInteger.decode(memberStream);
     this.doNotDisturbEvent = ASNInteger.decode(memberStream);
     this.forwardingEvent = ASNInteger.decode(memberStream);
     this.messageWaitingEvent = ASNInteger.decode(memberStream);
     this.loggedOnEvent = ASNInteger.decode(memberStream);
     this.loggedOffEvent = ASNInteger.decode(memberStream);
     this.notReadyEvent = ASNInteger.decode(memberStream);
     this.readyEvent = ASNInteger.decode(memberStream);
     this.workNotReadyEvent = ASNInteger.decode(memberStream);
     this.workReadyEvent = ASNInteger.decode(memberStream);
     this.backInServiceEvent = ASNInteger.decode(memberStream);
     this.outOfServiceEvent = ASNInteger.decode(memberStream);
     this.privateEvent = ASNInteger.decode(memberStream);
     this.routeRequestEvent = ASNInteger.decode(memberStream);
     this.reRoute = ASNInteger.decode(memberStream);
     this.routeSelect = ASNInteger.decode(memberStream);
     this.routeUsedEvent = ASNInteger.decode(memberStream);
     this.routeEndEvent = ASNInteger.decode(memberStream);
     this.monitorDevice = ASNInteger.decode(memberStream);
     this.monitorCall = ASNInteger.decode(memberStream);
     this.monitorCallsViaDevice = ASNInteger.decode(memberStream);
     this.changeMonitorFilter = ASNInteger.decode(memberStream);
     this.monitorStop = ASNInteger.decode(memberStream);
     this.monitorEnded = ASNInteger.decode(memberStream);
     this.snapshotDeviceReq = ASNInteger.decode(memberStream);
     this.snapshotCallReq = ASNInteger.decode(memberStream);
     this.escapeService = ASNInteger.decode(memberStream);
     this.privateStatusEvent = ASNInteger.decode(memberStream);
     this.escapeServiceEvent = ASNInteger.decode(memberStream);
     this.escapeServiceConf = ASNInteger.decode(memberStream);
     this.sendPrivateEvent = ASNInteger.decode(memberStream);
     this.sysStatReq = ASNInteger.decode(memberStream);
     this.sysStatStart = ASNInteger.decode(memberStream);
     this.sysStatStop = ASNInteger.decode(memberStream);
     this.changeSysStatFilter = ASNInteger.decode(memberStream);
     this.sysStatReqEvent = ASNInteger.decode(memberStream);
     this.sysStatReqConf = ASNInteger.decode(memberStream);
     this.sysStatEvent = ASNInteger.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAGetAPICapsConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNInteger.print(this.alternateCall, "alternateCall", indent));
     lines.addAll(ASNInteger.print(this.answerCall, "answerCall", indent));
     lines.addAll(ASNInteger.print(this.callCompletion, "callCompletion", indent));
     lines.addAll(ASNInteger.print(this.clearCall, "clearCall", indent));
     lines.addAll(ASNInteger.print(this.clearConnection, "clearConnection", indent));
     lines.addAll(ASNInteger.print(this.conferenceCall, "conferenceCall", indent));
     lines.addAll(ASNInteger.print(this.consultationCall, "consultationCall", indent));
     lines.addAll(ASNInteger.print(this.deflectCall, "deflectCall", indent));
     lines.addAll(ASNInteger.print(this.pickupCall, "pickupCall", indent));
     lines.addAll(ASNInteger.print(this.groupPickupCall, "groupPickupCall", indent));
     lines.addAll(ASNInteger.print(this.holdCall, "holdCall", indent));
     lines.addAll(ASNInteger.print(this.makeCall, "makeCall", indent));
     lines.addAll(ASNInteger.print(this.makePredictiveCall, "makePredictiveCall", indent));
     lines.addAll(ASNInteger.print(this.queryMwi, "queryMwi", indent));
     lines.addAll(ASNInteger.print(this.queryDnd, "queryDnd", indent));
     lines.addAll(ASNInteger.print(this.queryFwd, "queryFwd", indent));
     lines.addAll(ASNInteger.print(this.queryAgentState, "queryAgentState", indent));
     lines.addAll(ASNInteger.print(this.queryLastNumber, "queryLastNumber", indent));
     lines.addAll(ASNInteger.print(this.queryDeviceInfo, "queryDeviceInfo", indent));
     lines.addAll(ASNInteger.print(this.reconnectCall, "reconnectCall", indent));
     lines.addAll(ASNInteger.print(this.retrieveCall, "retrieveCall", indent));
     lines.addAll(ASNInteger.print(this.setMwi, "setMwi", indent));
     lines.addAll(ASNInteger.print(this.setDnd, "setDnd", indent));
     lines.addAll(ASNInteger.print(this.setFwd, "setFwd", indent));
     lines.addAll(ASNInteger.print(this.setAgentState, "setAgentState", indent));
     lines.addAll(ASNInteger.print(this.transferCall, "transferCall", indent));
     lines.addAll(ASNInteger.print(this.eventReport, "eventReport", indent));
     lines.addAll(ASNInteger.print(this.callClearedEvent, "callClearedEvent", indent));
     lines.addAll(ASNInteger.print(this.conferencedEvent, "conferencedEvent", indent));
     lines.addAll(ASNInteger.print(this.connectionClearedEvent, "connectionClearedEvent", indent));
     lines.addAll(ASNInteger.print(this.deliveredEvent, "deliveredEvent", indent));
     lines.addAll(ASNInteger.print(this.divertedEvent, "divertedEvent", indent));
     lines.addAll(ASNInteger.print(this.establishedEvent, "establishedEvent", indent));
     lines.addAll(ASNInteger.print(this.failedEvent, "failedEvent", indent));
     lines.addAll(ASNInteger.print(this.heldEvent, "heldEvent", indent));
     lines.addAll(ASNInteger.print(this.networkReachedEvent, "networkReachedEvent", indent));
     lines.addAll(ASNInteger.print(this.originatedEvent, "originatedEvent", indent));
     lines.addAll(ASNInteger.print(this.queuedEvent, "queuedEvent", indent));
     lines.addAll(ASNInteger.print(this.retrievedEvent, "retrievedEvent", indent));
     lines.addAll(ASNInteger.print(this.serviceInitiatedEvent, "serviceInitiatedEvent", indent));
     lines.addAll(ASNInteger.print(this.transferredEvent, "transferredEvent", indent));
     lines.addAll(ASNInteger.print(this.callInformationEvent, "callInformationEvent", indent));
     lines.addAll(ASNInteger.print(this.doNotDisturbEvent, "doNotDisturbEvent", indent));
     lines.addAll(ASNInteger.print(this.forwardingEvent, "forwardingEvent", indent));
     lines.addAll(ASNInteger.print(this.messageWaitingEvent, "messageWaitingEvent", indent));
     lines.addAll(ASNInteger.print(this.loggedOnEvent, "loggedOnEvent", indent));
     lines.addAll(ASNInteger.print(this.loggedOffEvent, "loggedOffEvent", indent));
     lines.addAll(ASNInteger.print(this.notReadyEvent, "notReadyEvent", indent));
     lines.addAll(ASNInteger.print(this.readyEvent, "readyEvent", indent));
     lines.addAll(ASNInteger.print(this.workNotReadyEvent, "workNotReadyEvent", indent));
     lines.addAll(ASNInteger.print(this.workReadyEvent, "workReadyEvent", indent));
     lines.addAll(ASNInteger.print(this.backInServiceEvent, "backInServiceEvent", indent));
     lines.addAll(ASNInteger.print(this.outOfServiceEvent, "outOfServiceEvent", indent));
     lines.addAll(ASNInteger.print(this.privateEvent, "privateEvent", indent));
     lines.addAll(ASNInteger.print(this.routeRequestEvent, "routeRequestEvent", indent));
     lines.addAll(ASNInteger.print(this.reRoute, "reRoute", indent));
     lines.addAll(ASNInteger.print(this.routeSelect, "routeSelect", indent));
     lines.addAll(ASNInteger.print(this.routeUsedEvent, "routeUsedEvent", indent));
     lines.addAll(ASNInteger.print(this.routeEndEvent, "routeEndEvent", indent));
     lines.addAll(ASNInteger.print(this.monitorDevice, "monitorDevice", indent));
     lines.addAll(ASNInteger.print(this.monitorCall, "monitorCall", indent));
     lines.addAll(ASNInteger.print(this.monitorCallsViaDevice, "monitorCallsViaDevice", indent));
     lines.addAll(ASNInteger.print(this.changeMonitorFilter, "changeMonitorFilter", indent));
     lines.addAll(ASNInteger.print(this.monitorStop, "monitorStop", indent));
     lines.addAll(ASNInteger.print(this.monitorEnded, "monitorEnded", indent));
     lines.addAll(ASNInteger.print(this.snapshotDeviceReq, "snapshotDeviceReq", indent));
     lines.addAll(ASNInteger.print(this.snapshotCallReq, "snapshotCallReq", indent));
     lines.addAll(ASNInteger.print(this.escapeService, "escapeService", indent));
     lines.addAll(ASNInteger.print(this.privateStatusEvent, "privateStatusEvent", indent));
     lines.addAll(ASNInteger.print(this.escapeServiceEvent, "escapeServiceEvent", indent));
     lines.addAll(ASNInteger.print(this.escapeServiceConf, "escapeServiceConf", indent));
     lines.addAll(ASNInteger.print(this.sendPrivateEvent, "sendPrivateEvent", indent));
     lines.addAll(ASNInteger.print(this.sysStatReq, "sysStatReq", indent));
     lines.addAll(ASNInteger.print(this.sysStatStart, "sysStatStart", indent));
     lines.addAll(ASNInteger.print(this.sysStatStop, "sysStatStop", indent));
     lines.addAll(ASNInteger.print(this.changeSysStatFilter, "changeSysStatFilter", indent));
     lines.addAll(ASNInteger.print(this.sysStatReqEvent, "sysStatReqEvent", indent));
     lines.addAll(ASNInteger.print(this.sysStatReqConf, "sysStatReqConf", indent));
     lines.addAll(ASNInteger.print(this.sysStatEvent, "sysStatEvent", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 125;
   }
 
   public int getAlternateCall()
   {
     return this.alternateCall;
   }
 
   public int getAnswerCall()
   {
     return this.answerCall;
   }
 
   public int getBackInServiceEvent()
   {
     return this.backInServiceEvent;
   }
 
   public int getCallClearedEvent()
   {
     return this.callClearedEvent;
   }
 
   public int getCallCompletion()
   {
     return this.callCompletion;
   }
 
   public int getCallInformationEvent()
   {
     return this.callInformationEvent;
   }
 
   public int getChangeMonitorFilter()
   {
     return this.changeMonitorFilter;
   }
 
   public int getChangeSysStatFilter()
   {
     return this.changeSysStatFilter;
   }
 
   public int getClearCall()
   {
     return this.clearCall;
   }
 
   public int getClearConnection()
   {
     return this.clearConnection;
   }
 
   public int getConferenceCall()
   {
     return this.conferenceCall;
   }
 
   public int getConferencedEvent()
   {
     return this.conferencedEvent;
   }
 
   public int getConnectionClearedEvent()
   {
     return this.connectionClearedEvent;
   }
 
   public int getConsultationCall()
   {
     return this.consultationCall;
   }
 
   public int getDeflectCall()
   {
     return this.deflectCall;
   }
 
   public int getDeliveredEvent()
   {
     return this.deliveredEvent;
   }
 
   public int getDivertedEvent()
   {
     return this.divertedEvent;
   }
 
   public int getDoNotDisturbEvent()
   {
     return this.doNotDisturbEvent;
   }
 
   public int getEscapeService()
   {
     return this.escapeService;
   }
 
   public int getEscapeServiceConf()
   {
     return this.escapeServiceConf;
   }
 
   public int getEscapeServiceEvent()
   {
     return this.escapeServiceEvent;
   }
 
   public int getEstablishedEvent()
   {
     return this.establishedEvent;
   }
 
   public int getEventReport()
   {
     return this.eventReport;
   }
 
   public int getFailedEvent()
   {
     return this.failedEvent;
   }
 
   public int getForwardingEvent()
   {
     return this.forwardingEvent;
   }
 
   public int getGroupPickupCall()
   {
     return this.groupPickupCall;
   }
 
   public int getHeldEvent()
   {
     return this.heldEvent;
   }
 
   public int getHoldCall()
   {
     return this.holdCall;
   }
 
   public int getLoggedOffEvent()
   {
     return this.loggedOffEvent;
   }
 
   public int getLoggedOnEvent()
   {
     return this.loggedOnEvent;
   }
 
   public int getMakeCall()
   {
     return this.makeCall;
   }
 
   public int getMakePredictiveCall()
   {
     return this.makePredictiveCall;
   }
 
   public int getMessageWaitingEvent()
   {
     return this.messageWaitingEvent;
   }
 
   public int getMonitorCall()
   {
     return this.monitorCall;
   }
 
   public int getMonitorCallsViaDevice()
   {
     return this.monitorCallsViaDevice;
   }
 
   public int getMonitorDevice()
   {
     return this.monitorDevice;
   }
 
   public int getMonitorEnded()
   {
     return this.monitorEnded;
   }
 
   public int getMonitorStop()
   {
     return this.monitorStop;
   }
 
   public int getNetworkReachedEvent()
   {
     return this.networkReachedEvent;
   }
 
   public int getNotReadyEvent()
   {
     return this.notReadyEvent;
   }
 
   public int getOriginatedEvent()
   {
     return this.originatedEvent;
   }
 
   public int getOutOfServiceEvent()
   {
     return this.outOfServiceEvent;
   }
 
   public int getPickupCall()
   {
     return this.pickupCall;
   }
 
   public int getPrivateEvent()
   {
     return this.privateEvent;
   }
 
   public int getPrivateStatusEvent()
   {
     return this.privateStatusEvent;
   }
 
   public int getQueryAgentState()
   {
     return this.queryAgentState;
   }
 
   public int getQueryDeviceInfo()
   {
     return this.queryDeviceInfo;
   }
 
   public int getQueryDnd()
   {
     return this.queryDnd;
   }
 
   public int getQueryFwd()
   {
     return this.queryFwd;
   }
 
   public int getQueryLastNumber()
   {
     return this.queryLastNumber;
   }
 
   public int getQueryMwi()
   {
     return this.queryMwi;
   }
 
   public int getQueuedEvent()
   {
     return this.queuedEvent;
   }
 
   public int getReadyEvent()
   {
     return this.readyEvent;
   }
 
   public int getReconnectCall()
   {
     return this.reconnectCall;
   }
 
   public int getReRoute()
   {
     return this.reRoute;
   }
 
   public int getRetrieveCall()
   {
     return this.retrieveCall;
   }
 
   public int getRetrievedEvent()
   {
     return this.retrievedEvent;
   }
 
   public int getRouteEndEvent()
   {
     return this.routeEndEvent;
   }
 
   public int getRouteRequestEvent()
   {
     return this.routeRequestEvent;
   }
 
   public int getRouteSelect()
   {
     return this.routeSelect;
   }
 
   public int getRouteUsedEvent()
   {
     return this.routeUsedEvent;
   }
 
   public int getSendPrivateEvent()
   {
     return this.sendPrivateEvent;
   }
 
   public int getServiceInitiatedEvent()
   {
     return this.serviceInitiatedEvent;
   }
 
   public int getSetAgentState()
   {
     return this.setAgentState;
   }
 
   public int getSetDnd()
   {
     return this.setDnd;
   }
 
   public int getSetFwd()
   {
     return this.setFwd;
   }
 
   public int getSetMwi()
   {
     return this.setMwi;
   }
 
   public int getSnapshotCallReq()
   {
     return this.snapshotCallReq;
   }
 
   public int getSnapshotDeviceReq()
   {
     return this.snapshotDeviceReq;
   }
 
   public int getSysStatEvent()
   {
     return this.sysStatEvent;
   }
 
   public int getSysStatReq()
   {
     return this.sysStatReq;
   }
 
   public int getSysStatReqConf()
   {
     return this.sysStatReqConf;
   }
 
   public int getSysStatReqEvent()
   {
     return this.sysStatReqEvent;
   }
 
   public int getSysStatStart()
   {
     return this.sysStatStart;
   }
 
   public int getSysStatStop()
   {
     return this.sysStatStop;
   }
 
   public int getTransferCall()
   {
     return this.transferCall;
   }
 
   public int getTransferredEvent()
   {
     return this.transferredEvent;
   }
 
   public int getWorkNotReadyEvent()
   {
     return this.workNotReadyEvent;
   }
 
   public int getWorkReadyEvent()
   {
     return this.workReadyEvent;
   }
 
   public void setReadyEvent(int readyEvent)
   {
     this.readyEvent = readyEvent;
   }
 
   public void setAlternateCall(int alternateCall)
   {
     this.alternateCall = alternateCall;
   }
 
   public void setAnswerCall(int answerCall)
   {
     this.answerCall = answerCall;
   }
 
   public void setCallCompletion(int callCompletion)
   {
     this.callCompletion = callCompletion;
   }
 
   public void setClearCall(int clearCall)
   {
     this.clearCall = clearCall;
   }
 
   public void setClearConnection(int clearConnection)
   {
     this.clearConnection = clearConnection;
   }
 
   public void setConferenceCall(int conferenceCall)
   {
     this.conferenceCall = conferenceCall;
   }
 
   public void setConsultationCall(int consultationCall)
   {
     this.consultationCall = consultationCall;
   }
 
   public void setDeflectCall(int deflectCall)
   {
     this.deflectCall = deflectCall;
   }
 
   public void setPickupCall(int pickupCall)
   {
     this.pickupCall = pickupCall;
   }
 
   public void setGroupPickupCall(int groupPickupCall)
   {
     this.groupPickupCall = groupPickupCall;
   }
 
   public void setHoldCall(int holdCall)
   {
     this.holdCall = holdCall;
   }
 
   public void setMakeCall(int makeCall)
   {
     this.makeCall = makeCall;
   }
 
   public void setMakePredictiveCall(int makePredictiveCall)
   {
     this.makePredictiveCall = makePredictiveCall;
   }
 
   public void setQueryMwi(int queryMwi)
   {
     this.queryMwi = queryMwi;
   }
 
   public void setQueryDnd(int queryDnd)
   {
     this.queryDnd = queryDnd;
   }
 
   public void setQueryFwd(int queryFwd)
   {
     this.queryFwd = queryFwd;
   }
 
   public void setQueryAgentState(int queryAgentState)
   {
     this.queryAgentState = queryAgentState;
   }
 
   public void setQueryLastNumber(int queryLastNumber)
   {
     this.queryLastNumber = queryLastNumber;
   }
 
   public void setQueryDeviceInfo(int queryDeviceInfo)
   {
     this.queryDeviceInfo = queryDeviceInfo;
   }
 
   public void setReconnectCall(int reconnectCall)
   {
     this.reconnectCall = reconnectCall;
   }
 
   public void setRetrieveCall(int retrieveCall)
   {
     this.retrieveCall = retrieveCall;
   }
 
   public void setSetMwi(int setMwi)
   {
     this.setMwi = setMwi;
   }
 
   public void setSetDnd(int setDnd)
   {
     this.setDnd = setDnd;
   }
 
   public void setSetFwd(int setFwd)
   {
     this.setFwd = setFwd;
   }
 
   public void setSetAgentState(int setAgentState)
   {
     this.setAgentState = setAgentState;
   }
 
   public void setTransferCall(int transferCall)
   {
     this.transferCall = transferCall;
   }
 
   public void setEventReport(int eventReport)
   {
     this.eventReport = eventReport;
   }
 
   public void setCallClearedEvent(int callClearedEvent)
   {
     this.callClearedEvent = callClearedEvent;
   }
 
   public void setConferencedEvent(int conferencedEvent)
   {
     this.conferencedEvent = conferencedEvent;
   }
 
   public void setConnectionClearedEvent(int connectionClearedEvent)
   {
     this.connectionClearedEvent = connectionClearedEvent;
   }
 
   public void setDeliveredEvent(int deliveredEvent)
   {
     this.deliveredEvent = deliveredEvent;
   }
 
   public void setDivertedEvent(int divertedEvent)
   {
     this.divertedEvent = divertedEvent;
   }
 
   public void setEstablishedEvent(int establishedEvent)
   {
     this.establishedEvent = establishedEvent;
   }
 
   public void setFailedEvent(int failedEvent)
   {
     this.failedEvent = failedEvent;
   }
 
   public void setHeldEvent(int heldEvent)
   {
     this.heldEvent = heldEvent;
   }
 
   public void setNetworkReachedEvent(int networkReachedEvent)
   {
     this.networkReachedEvent = networkReachedEvent;
   }
 
   public void setOriginatedEvent(int originatedEvent)
   {
     this.originatedEvent = originatedEvent;
   }
 
   public void setQueuedEvent(int queuedEvent)
   {
     this.queuedEvent = queuedEvent;
   }
 
   public void setRetrievedEvent(int retrievedEvent)
   {
     this.retrievedEvent = retrievedEvent;
   }
 
   public void setServiceInitiatedEvent(int serviceInitiatedEvent)
   {
     this.serviceInitiatedEvent = serviceInitiatedEvent;
   }
 
   public void setTransferredEvent(int transferredEvent)
   {
     this.transferredEvent = transferredEvent;
   }
 
   public void setCallInformationEvent(int callInformationEvent)
   {
     this.callInformationEvent = callInformationEvent;
   }
 
   public void setDoNotDisturbEvent(int doNotDisturbEvent)
   {
     this.doNotDisturbEvent = doNotDisturbEvent;
   }
 
   public void setForwardingEvent(int forwardingEvent)
   {
     this.forwardingEvent = forwardingEvent;
   }
 
   public void setMessageWaitingEvent(int messageWaitingEvent)
   {
     this.messageWaitingEvent = messageWaitingEvent;
   }
 
   public void setLoggedOnEvent(int loggedOnEvent)
   {
     this.loggedOnEvent = loggedOnEvent;
   }
 
   public void setLoggedOffEvent(int loggedOffEvent)
   {
     this.loggedOffEvent = loggedOffEvent;
   }
 
   public void setNotReadyEvent(int notReadyEvent)
   {
     this.notReadyEvent = notReadyEvent;
   }
 
   public void setWorkNotReadyEvent(int workNotReadyEvent)
   {
     this.workNotReadyEvent = workNotReadyEvent;
   }
 
   public void setWorkReadyEvent(int workReadyEvent)
   {
     this.workReadyEvent = workReadyEvent;
   }
 
   public void setBackInServiceEvent(int backInServiceEvent)
   {
     this.backInServiceEvent = backInServiceEvent;
   }
 
   public void setOutOfServiceEvent(int outOfServiceEvent)
   {
     this.outOfServiceEvent = outOfServiceEvent;
   }
 
   public void setPrivateEvent(int privateEvent)
   {
     this.privateEvent = privateEvent;
   }
 
   public void setRouteRequestEvent(int routeRequestEvent)
   {
     this.routeRequestEvent = routeRequestEvent;
   }
 
   public void setReRoute(int reRoute)
   {
     this.reRoute = reRoute;
   }
 
   public void setRouteSelect(int routeSelect)
   {
     this.routeSelect = routeSelect;
   }
 
   public void setRouteUsedEvent(int routeUsedEvent)
   {
     this.routeUsedEvent = routeUsedEvent;
   }
 
   public void setRouteEndEvent(int routeEndEvent)
   {
     this.routeEndEvent = routeEndEvent;
   }
 
   public void setMonitorDevice(int monitorDevice)
   {
     this.monitorDevice = monitorDevice;
   }
 
   public void setMonitorCall(int monitorCall)
   {
     this.monitorCall = monitorCall;
   }
 
   public void setMonitorCallsViaDevice(int monitorCallsViaDevice)
   {
     this.monitorCallsViaDevice = monitorCallsViaDevice;
   }
 
   public void setChangeMonitorFilter(int changeMonitorFilter)
   {
     this.changeMonitorFilter = changeMonitorFilter;
   }
 
   public void setMonitorStop(int monitorStop)
   {
     this.monitorStop = monitorStop;
   }
 
   public void setMonitorEnded(int monitorEnded)
   {
     this.monitorEnded = monitorEnded;
   }
 
   public void setSnapshotDeviceReq(int snapshotDeviceReq)
   {
     this.snapshotDeviceReq = snapshotDeviceReq;
   }
 
   public void setSnapshotCallReq(int snapshotCallReq)
   {
     this.snapshotCallReq = snapshotCallReq;
   }
 
   public void setEscapeService(int escapeService)
   {
     this.escapeService = escapeService;
   }
 
   public void setPrivateStatusEvent(int privateStatusEvent)
   {
     this.privateStatusEvent = privateStatusEvent;
   }
 
   public void setEscapeServiceEvent(int escapeServiceEvent)
   {
     this.escapeServiceEvent = escapeServiceEvent;
   }
 
   public void setEscapeServiceConf(int escapeServiceConf)
   {
     this.escapeServiceConf = escapeServiceConf;
   }
 
   public void setSendPrivateEvent(int sendPrivateEvent)
   {
     this.sendPrivateEvent = sendPrivateEvent;
   }
 
   public void setSysStatReq(int sysStatReq)
   {
     this.sysStatReq = sysStatReq;
   }
 
   public void setSysStatStart(int sysStatStart)
   {
     this.sysStatStart = sysStatStart;
   }
 
   public void setSysStatStop(int sysStatStop)
   {
     this.sysStatStop = sysStatStop;
   }
 
   public void setChangeSysStatFilter(int changeSysStatFilter)
   {
     this.changeSysStatFilter = changeSysStatFilter;
   }
 
   public void setSysStatReqEvent(int sysStatReqEvent)
   {
     this.sysStatReqEvent = sysStatReqEvent;
   }
 
   public void setSysStatReqConf(int sysStatReqConf)
   {
     this.sysStatReqConf = sysStatReqConf;
   }
 
   public void setSysStatEvent(int sysStatEvent)
   {
     this.sysStatEvent = sysStatEvent;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAGetAPICapsConfEvent
 * JD-Core Version:    0.5.4
 */