/*      */ package com.avaya.jtapi.tsapi.csta1;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ 
/*      */ public final class CSTAGetAPICapsConfEvent extends CSTAConfirmation
/*      */ {
/*      */   int alternateCall;
/*      */   int answerCall;
/*      */   int callCompletion;
/*      */   int clearCall;
/*      */   int clearConnection;
/*      */   int conferenceCall;
/*      */   int consultationCall;
/*      */   int deflectCall;
/*      */   int pickupCall;
/*      */   int groupPickupCall;
/*      */   int holdCall;
/*      */   int makeCall;
/*      */   int makePredictiveCall;
/*      */   int queryMwi;
/*      */   int queryDnd;
/*      */   int queryFwd;
/*      */   int queryAgentState;
/*      */   int queryLastNumber;
/*      */   int queryDeviceInfo;
/*      */   int reconnectCall;
/*      */   int retrieveCall;
/*      */   int setMwi;
/*      */   int setDnd;
/*      */   int setFwd;
/*      */   int setAgentState;
/*      */   int transferCall;
/*      */   int eventReport;
/*      */   int callClearedEvent;
/*      */   int conferencedEvent;
/*      */   int connectionClearedEvent;
/*      */   int deliveredEvent;
/*      */   int divertedEvent;
/*      */   int establishedEvent;
/*      */   int failedEvent;
/*      */   int heldEvent;
/*      */   int networkReachedEvent;
/*      */   int originatedEvent;
/*      */   int queuedEvent;
/*      */   int retrievedEvent;
/*      */   int serviceInitiatedEvent;
/*      */   int transferredEvent;
/*      */   int callInformationEvent;
/*      */   int doNotDisturbEvent;
/*      */   int forwardingEvent;
/*      */   int messageWaitingEvent;
/*      */   int loggedOnEvent;
/*      */   int loggedOffEvent;
/*      */   int notReadyEvent;
/*      */   int readyEvent;
/*      */   int workNotReadyEvent;
/*      */   int workReadyEvent;
/*      */   int backInServiceEvent;
/*      */   int outOfServiceEvent;
/*      */   int privateEvent;
/*      */   int routeRequestEvent;
/*      */   int reRoute;
/*      */   int routeSelect;
/*      */   int routeUsedEvent;
/*      */   int routeEndEvent;
/*      */   int monitorDevice;
/*      */   int monitorCall;
/*      */   int monitorCallsViaDevice;
/*      */   int changeMonitorFilter;
/*      */   int monitorStop;
/*      */   int monitorEnded;
/*      */   int snapshotDeviceReq;
/*      */   int snapshotCallReq;
/*      */   int escapeService;
/*      */   int privateStatusEvent;
/*      */   int escapeServiceEvent;
/*      */   int escapeServiceConf;
/*      */   int sendPrivateEvent;
/*      */   int sysStatReq;
/*      */   int sysStatStart;
/*      */   int sysStatStop;
/*      */   int changeSysStatFilter;
/*      */   int sysStatReqEvent;
/*      */   int sysStatReqConf;
/*      */   int sysStatEvent;
/*      */   public static final int PDU = 125;
/*      */ 
/*      */   public static CSTAGetAPICapsConfEvent decode(InputStream in)
/*      */   {
/*   95 */     CSTAGetAPICapsConfEvent _this = new CSTAGetAPICapsConfEvent();
/*   96 */     _this.doDecode(in);
/*      */ 
/*   98 */     return _this;
/*      */   }
/*      */ 
/*      */   public void decodeMembers(InputStream memberStream)
/*      */   {
/*  103 */     this.alternateCall = ASNInteger.decode(memberStream);
/*  104 */     this.answerCall = ASNInteger.decode(memberStream);
/*  105 */     this.callCompletion = ASNInteger.decode(memberStream);
/*  106 */     this.clearCall = ASNInteger.decode(memberStream);
/*  107 */     this.clearConnection = ASNInteger.decode(memberStream);
/*  108 */     this.conferenceCall = ASNInteger.decode(memberStream);
/*  109 */     this.consultationCall = ASNInteger.decode(memberStream);
/*  110 */     this.deflectCall = ASNInteger.decode(memberStream);
/*  111 */     this.pickupCall = ASNInteger.decode(memberStream);
/*  112 */     this.groupPickupCall = ASNInteger.decode(memberStream);
/*  113 */     this.holdCall = ASNInteger.decode(memberStream);
/*  114 */     this.makeCall = ASNInteger.decode(memberStream);
/*  115 */     this.makePredictiveCall = ASNInteger.decode(memberStream);
/*  116 */     this.queryMwi = ASNInteger.decode(memberStream);
/*  117 */     this.queryDnd = ASNInteger.decode(memberStream);
/*  118 */     this.queryFwd = ASNInteger.decode(memberStream);
/*  119 */     this.queryAgentState = ASNInteger.decode(memberStream);
/*  120 */     this.queryLastNumber = ASNInteger.decode(memberStream);
/*  121 */     this.queryDeviceInfo = ASNInteger.decode(memberStream);
/*  122 */     this.reconnectCall = ASNInteger.decode(memberStream);
/*  123 */     this.retrieveCall = ASNInteger.decode(memberStream);
/*  124 */     this.setMwi = ASNInteger.decode(memberStream);
/*  125 */     this.setDnd = ASNInteger.decode(memberStream);
/*  126 */     this.setFwd = ASNInteger.decode(memberStream);
/*  127 */     this.setAgentState = ASNInteger.decode(memberStream);
/*  128 */     this.transferCall = ASNInteger.decode(memberStream);
/*  129 */     this.eventReport = ASNInteger.decode(memberStream);
/*  130 */     this.callClearedEvent = ASNInteger.decode(memberStream);
/*  131 */     this.conferencedEvent = ASNInteger.decode(memberStream);
/*  132 */     this.connectionClearedEvent = ASNInteger.decode(memberStream);
/*  133 */     this.deliveredEvent = ASNInteger.decode(memberStream);
/*  134 */     this.divertedEvent = ASNInteger.decode(memberStream);
/*  135 */     this.establishedEvent = ASNInteger.decode(memberStream);
/*  136 */     this.failedEvent = ASNInteger.decode(memberStream);
/*  137 */     this.heldEvent = ASNInteger.decode(memberStream);
/*  138 */     this.networkReachedEvent = ASNInteger.decode(memberStream);
/*  139 */     this.originatedEvent = ASNInteger.decode(memberStream);
/*  140 */     this.queuedEvent = ASNInteger.decode(memberStream);
/*  141 */     this.retrievedEvent = ASNInteger.decode(memberStream);
/*  142 */     this.serviceInitiatedEvent = ASNInteger.decode(memberStream);
/*  143 */     this.transferredEvent = ASNInteger.decode(memberStream);
/*  144 */     this.callInformationEvent = ASNInteger.decode(memberStream);
/*  145 */     this.doNotDisturbEvent = ASNInteger.decode(memberStream);
/*  146 */     this.forwardingEvent = ASNInteger.decode(memberStream);
/*  147 */     this.messageWaitingEvent = ASNInteger.decode(memberStream);
/*  148 */     this.loggedOnEvent = ASNInteger.decode(memberStream);
/*  149 */     this.loggedOffEvent = ASNInteger.decode(memberStream);
/*  150 */     this.notReadyEvent = ASNInteger.decode(memberStream);
/*  151 */     this.readyEvent = ASNInteger.decode(memberStream);
/*  152 */     this.workNotReadyEvent = ASNInteger.decode(memberStream);
/*  153 */     this.workReadyEvent = ASNInteger.decode(memberStream);
/*  154 */     this.backInServiceEvent = ASNInteger.decode(memberStream);
/*  155 */     this.outOfServiceEvent = ASNInteger.decode(memberStream);
/*  156 */     this.privateEvent = ASNInteger.decode(memberStream);
/*  157 */     this.routeRequestEvent = ASNInteger.decode(memberStream);
/*  158 */     this.reRoute = ASNInteger.decode(memberStream);
/*  159 */     this.routeSelect = ASNInteger.decode(memberStream);
/*  160 */     this.routeUsedEvent = ASNInteger.decode(memberStream);
/*  161 */     this.routeEndEvent = ASNInteger.decode(memberStream);
/*  162 */     this.monitorDevice = ASNInteger.decode(memberStream);
/*  163 */     this.monitorCall = ASNInteger.decode(memberStream);
/*  164 */     this.monitorCallsViaDevice = ASNInteger.decode(memberStream);
/*  165 */     this.changeMonitorFilter = ASNInteger.decode(memberStream);
/*  166 */     this.monitorStop = ASNInteger.decode(memberStream);
/*  167 */     this.monitorEnded = ASNInteger.decode(memberStream);
/*  168 */     this.snapshotDeviceReq = ASNInteger.decode(memberStream);
/*  169 */     this.snapshotCallReq = ASNInteger.decode(memberStream);
/*  170 */     this.escapeService = ASNInteger.decode(memberStream);
/*  171 */     this.privateStatusEvent = ASNInteger.decode(memberStream);
/*  172 */     this.escapeServiceEvent = ASNInteger.decode(memberStream);
/*  173 */     this.escapeServiceConf = ASNInteger.decode(memberStream);
/*  174 */     this.sendPrivateEvent = ASNInteger.decode(memberStream);
/*  175 */     this.sysStatReq = ASNInteger.decode(memberStream);
/*  176 */     this.sysStatStart = ASNInteger.decode(memberStream);
/*  177 */     this.sysStatStop = ASNInteger.decode(memberStream);
/*  178 */     this.changeSysStatFilter = ASNInteger.decode(memberStream);
/*  179 */     this.sysStatReqEvent = ASNInteger.decode(memberStream);
/*  180 */     this.sysStatReqConf = ASNInteger.decode(memberStream);
/*  181 */     this.sysStatEvent = ASNInteger.decode(memberStream);
/*      */   }
/*      */ 
/*      */   public Collection<String> print()
/*      */   {
/*  186 */     Collection lines = new ArrayList();
/*  187 */     lines.add("CSTAGetAPICapsConfEvent ::=");
/*  188 */     lines.add("{");
/*      */ 
/*  190 */     String indent = "  ";
/*      */ 
/*  192 */     lines.addAll(ASNInteger.print(this.alternateCall, "alternateCall", indent));
/*  193 */     lines.addAll(ASNInteger.print(this.answerCall, "answerCall", indent));
/*  194 */     lines.addAll(ASNInteger.print(this.callCompletion, "callCompletion", indent));
/*  195 */     lines.addAll(ASNInteger.print(this.clearCall, "clearCall", indent));
/*  196 */     lines.addAll(ASNInteger.print(this.clearConnection, "clearConnection", indent));
/*  197 */     lines.addAll(ASNInteger.print(this.conferenceCall, "conferenceCall", indent));
/*  198 */     lines.addAll(ASNInteger.print(this.consultationCall, "consultationCall", indent));
/*  199 */     lines.addAll(ASNInteger.print(this.deflectCall, "deflectCall", indent));
/*  200 */     lines.addAll(ASNInteger.print(this.pickupCall, "pickupCall", indent));
/*  201 */     lines.addAll(ASNInteger.print(this.groupPickupCall, "groupPickupCall", indent));
/*  202 */     lines.addAll(ASNInteger.print(this.holdCall, "holdCall", indent));
/*  203 */     lines.addAll(ASNInteger.print(this.makeCall, "makeCall", indent));
/*  204 */     lines.addAll(ASNInteger.print(this.makePredictiveCall, "makePredictiveCall", indent));
/*  205 */     lines.addAll(ASNInteger.print(this.queryMwi, "queryMwi", indent));
/*  206 */     lines.addAll(ASNInteger.print(this.queryDnd, "queryDnd", indent));
/*  207 */     lines.addAll(ASNInteger.print(this.queryFwd, "queryFwd", indent));
/*  208 */     lines.addAll(ASNInteger.print(this.queryAgentState, "queryAgentState", indent));
/*  209 */     lines.addAll(ASNInteger.print(this.queryLastNumber, "queryLastNumber", indent));
/*  210 */     lines.addAll(ASNInteger.print(this.queryDeviceInfo, "queryDeviceInfo", indent));
/*  211 */     lines.addAll(ASNInteger.print(this.reconnectCall, "reconnectCall", indent));
/*  212 */     lines.addAll(ASNInteger.print(this.retrieveCall, "retrieveCall", indent));
/*  213 */     lines.addAll(ASNInteger.print(this.setMwi, "setMwi", indent));
/*  214 */     lines.addAll(ASNInteger.print(this.setDnd, "setDnd", indent));
/*  215 */     lines.addAll(ASNInteger.print(this.setFwd, "setFwd", indent));
/*  216 */     lines.addAll(ASNInteger.print(this.setAgentState, "setAgentState", indent));
/*  217 */     lines.addAll(ASNInteger.print(this.transferCall, "transferCall", indent));
/*  218 */     lines.addAll(ASNInteger.print(this.eventReport, "eventReport", indent));
/*  219 */     lines.addAll(ASNInteger.print(this.callClearedEvent, "callClearedEvent", indent));
/*  220 */     lines.addAll(ASNInteger.print(this.conferencedEvent, "conferencedEvent", indent));
/*  221 */     lines.addAll(ASNInteger.print(this.connectionClearedEvent, "connectionClearedEvent", indent));
/*  222 */     lines.addAll(ASNInteger.print(this.deliveredEvent, "deliveredEvent", indent));
/*  223 */     lines.addAll(ASNInteger.print(this.divertedEvent, "divertedEvent", indent));
/*  224 */     lines.addAll(ASNInteger.print(this.establishedEvent, "establishedEvent", indent));
/*  225 */     lines.addAll(ASNInteger.print(this.failedEvent, "failedEvent", indent));
/*  226 */     lines.addAll(ASNInteger.print(this.heldEvent, "heldEvent", indent));
/*  227 */     lines.addAll(ASNInteger.print(this.networkReachedEvent, "networkReachedEvent", indent));
/*  228 */     lines.addAll(ASNInteger.print(this.originatedEvent, "originatedEvent", indent));
/*  229 */     lines.addAll(ASNInteger.print(this.queuedEvent, "queuedEvent", indent));
/*  230 */     lines.addAll(ASNInteger.print(this.retrievedEvent, "retrievedEvent", indent));
/*  231 */     lines.addAll(ASNInteger.print(this.serviceInitiatedEvent, "serviceInitiatedEvent", indent));
/*  232 */     lines.addAll(ASNInteger.print(this.transferredEvent, "transferredEvent", indent));
/*  233 */     lines.addAll(ASNInteger.print(this.callInformationEvent, "callInformationEvent", indent));
/*  234 */     lines.addAll(ASNInteger.print(this.doNotDisturbEvent, "doNotDisturbEvent", indent));
/*  235 */     lines.addAll(ASNInteger.print(this.forwardingEvent, "forwardingEvent", indent));
/*  236 */     lines.addAll(ASNInteger.print(this.messageWaitingEvent, "messageWaitingEvent", indent));
/*  237 */     lines.addAll(ASNInteger.print(this.loggedOnEvent, "loggedOnEvent", indent));
/*  238 */     lines.addAll(ASNInteger.print(this.loggedOffEvent, "loggedOffEvent", indent));
/*  239 */     lines.addAll(ASNInteger.print(this.notReadyEvent, "notReadyEvent", indent));
/*  240 */     lines.addAll(ASNInteger.print(this.readyEvent, "readyEvent", indent));
/*  241 */     lines.addAll(ASNInteger.print(this.workNotReadyEvent, "workNotReadyEvent", indent));
/*  242 */     lines.addAll(ASNInteger.print(this.workReadyEvent, "workReadyEvent", indent));
/*  243 */     lines.addAll(ASNInteger.print(this.backInServiceEvent, "backInServiceEvent", indent));
/*  244 */     lines.addAll(ASNInteger.print(this.outOfServiceEvent, "outOfServiceEvent", indent));
/*  245 */     lines.addAll(ASNInteger.print(this.privateEvent, "privateEvent", indent));
/*  246 */     lines.addAll(ASNInteger.print(this.routeRequestEvent, "routeRequestEvent", indent));
/*  247 */     lines.addAll(ASNInteger.print(this.reRoute, "reRoute", indent));
/*  248 */     lines.addAll(ASNInteger.print(this.routeSelect, "routeSelect", indent));
/*  249 */     lines.addAll(ASNInteger.print(this.routeUsedEvent, "routeUsedEvent", indent));
/*  250 */     lines.addAll(ASNInteger.print(this.routeEndEvent, "routeEndEvent", indent));
/*  251 */     lines.addAll(ASNInteger.print(this.monitorDevice, "monitorDevice", indent));
/*  252 */     lines.addAll(ASNInteger.print(this.monitorCall, "monitorCall", indent));
/*  253 */     lines.addAll(ASNInteger.print(this.monitorCallsViaDevice, "monitorCallsViaDevice", indent));
/*  254 */     lines.addAll(ASNInteger.print(this.changeMonitorFilter, "changeMonitorFilter", indent));
/*  255 */     lines.addAll(ASNInteger.print(this.monitorStop, "monitorStop", indent));
/*  256 */     lines.addAll(ASNInteger.print(this.monitorEnded, "monitorEnded", indent));
/*  257 */     lines.addAll(ASNInteger.print(this.snapshotDeviceReq, "snapshotDeviceReq", indent));
/*  258 */     lines.addAll(ASNInteger.print(this.snapshotCallReq, "snapshotCallReq", indent));
/*  259 */     lines.addAll(ASNInteger.print(this.escapeService, "escapeService", indent));
/*  260 */     lines.addAll(ASNInteger.print(this.privateStatusEvent, "privateStatusEvent", indent));
/*  261 */     lines.addAll(ASNInteger.print(this.escapeServiceEvent, "escapeServiceEvent", indent));
/*  262 */     lines.addAll(ASNInteger.print(this.escapeServiceConf, "escapeServiceConf", indent));
/*  263 */     lines.addAll(ASNInteger.print(this.sendPrivateEvent, "sendPrivateEvent", indent));
/*  264 */     lines.addAll(ASNInteger.print(this.sysStatReq, "sysStatReq", indent));
/*  265 */     lines.addAll(ASNInteger.print(this.sysStatStart, "sysStatStart", indent));
/*  266 */     lines.addAll(ASNInteger.print(this.sysStatStop, "sysStatStop", indent));
/*  267 */     lines.addAll(ASNInteger.print(this.changeSysStatFilter, "changeSysStatFilter", indent));
/*  268 */     lines.addAll(ASNInteger.print(this.sysStatReqEvent, "sysStatReqEvent", indent));
/*  269 */     lines.addAll(ASNInteger.print(this.sysStatReqConf, "sysStatReqConf", indent));
/*  270 */     lines.addAll(ASNInteger.print(this.sysStatEvent, "sysStatEvent", indent));
/*      */ 
/*  272 */     lines.add("}");
/*  273 */     return lines;
/*      */   }
/*      */ 
/*      */   public int getPDU()
/*      */   {
/*  278 */     return 125;
/*      */   }
/*      */ 
/*      */   public int getAlternateCall()
/*      */   {
/*  284 */     return this.alternateCall;
/*      */   }
/*      */ 
/*      */   public int getAnswerCall()
/*      */   {
/*  292 */     return this.answerCall;
/*      */   }
/*      */ 
/*      */   public int getBackInServiceEvent()
/*      */   {
/*  300 */     return this.backInServiceEvent;
/*      */   }
/*      */ 
/*      */   public int getCallClearedEvent()
/*      */   {
/*  308 */     return this.callClearedEvent;
/*      */   }
/*      */ 
/*      */   public int getCallCompletion()
/*      */   {
/*  316 */     return this.callCompletion;
/*      */   }
/*      */ 
/*      */   public int getCallInformationEvent()
/*      */   {
/*  324 */     return this.callInformationEvent;
/*      */   }
/*      */ 
/*      */   public int getChangeMonitorFilter()
/*      */   {
/*  332 */     return this.changeMonitorFilter;
/*      */   }
/*      */ 
/*      */   public int getChangeSysStatFilter()
/*      */   {
/*  340 */     return this.changeSysStatFilter;
/*      */   }
/*      */ 
/*      */   public int getClearCall()
/*      */   {
/*  348 */     return this.clearCall;
/*      */   }
/*      */ 
/*      */   public int getClearConnection()
/*      */   {
/*  356 */     return this.clearConnection;
/*      */   }
/*      */ 
/*      */   public int getConferenceCall()
/*      */   {
/*  364 */     return this.conferenceCall;
/*      */   }
/*      */ 
/*      */   public int getConferencedEvent()
/*      */   {
/*  372 */     return this.conferencedEvent;
/*      */   }
/*      */ 
/*      */   public int getConnectionClearedEvent()
/*      */   {
/*  380 */     return this.connectionClearedEvent;
/*      */   }
/*      */ 
/*      */   public int getConsultationCall()
/*      */   {
/*  388 */     return this.consultationCall;
/*      */   }
/*      */ 
/*      */   public int getDeflectCall()
/*      */   {
/*  396 */     return this.deflectCall;
/*      */   }
/*      */ 
/*      */   public int getDeliveredEvent()
/*      */   {
/*  404 */     return this.deliveredEvent;
/*      */   }
/*      */ 
/*      */   public int getDivertedEvent()
/*      */   {
/*  412 */     return this.divertedEvent;
/*      */   }
/*      */ 
/*      */   public int getDoNotDisturbEvent()
/*      */   {
/*  420 */     return this.doNotDisturbEvent;
/*      */   }
/*      */ 
/*      */   public int getEscapeService()
/*      */   {
/*  428 */     return this.escapeService;
/*      */   }
/*      */ 
/*      */   public int getEscapeServiceConf()
/*      */   {
/*  436 */     return this.escapeServiceConf;
/*      */   }
/*      */ 
/*      */   public int getEscapeServiceEvent()
/*      */   {
/*  444 */     return this.escapeServiceEvent;
/*      */   }
/*      */ 
/*      */   public int getEstablishedEvent()
/*      */   {
/*  452 */     return this.establishedEvent;
/*      */   }
/*      */ 
/*      */   public int getEventReport()
/*      */   {
/*  460 */     return this.eventReport;
/*      */   }
/*      */ 
/*      */   public int getFailedEvent()
/*      */   {
/*  468 */     return this.failedEvent;
/*      */   }
/*      */ 
/*      */   public int getForwardingEvent()
/*      */   {
/*  476 */     return this.forwardingEvent;
/*      */   }
/*      */ 
/*      */   public int getGroupPickupCall()
/*      */   {
/*  484 */     return this.groupPickupCall;
/*      */   }
/*      */ 
/*      */   public int getHeldEvent()
/*      */   {
/*  492 */     return this.heldEvent;
/*      */   }
/*      */ 
/*      */   public int getHoldCall()
/*      */   {
/*  500 */     return this.holdCall;
/*      */   }
/*      */ 
/*      */   public int getLoggedOffEvent()
/*      */   {
/*  508 */     return this.loggedOffEvent;
/*      */   }
/*      */ 
/*      */   public int getLoggedOnEvent()
/*      */   {
/*  516 */     return this.loggedOnEvent;
/*      */   }
/*      */ 
/*      */   public int getMakeCall()
/*      */   {
/*  524 */     return this.makeCall;
/*      */   }
/*      */ 
/*      */   public int getMakePredictiveCall()
/*      */   {
/*  532 */     return this.makePredictiveCall;
/*      */   }
/*      */ 
/*      */   public int getMessageWaitingEvent()
/*      */   {
/*  540 */     return this.messageWaitingEvent;
/*      */   }
/*      */ 
/*      */   public int getMonitorCall()
/*      */   {
/*  548 */     return this.monitorCall;
/*      */   }
/*      */ 
/*      */   public int getMonitorCallsViaDevice()
/*      */   {
/*  556 */     return this.monitorCallsViaDevice;
/*      */   }
/*      */ 
/*      */   public int getMonitorDevice()
/*      */   {
/*  564 */     return this.monitorDevice;
/*      */   }
/*      */ 
/*      */   public int getMonitorEnded()
/*      */   {
/*  572 */     return this.monitorEnded;
/*      */   }
/*      */ 
/*      */   public int getMonitorStop()
/*      */   {
/*  580 */     return this.monitorStop;
/*      */   }
/*      */ 
/*      */   public int getNetworkReachedEvent()
/*      */   {
/*  588 */     return this.networkReachedEvent;
/*      */   }
/*      */ 
/*      */   public int getNotReadyEvent()
/*      */   {
/*  596 */     return this.notReadyEvent;
/*      */   }
/*      */ 
/*      */   public int getOriginatedEvent()
/*      */   {
/*  604 */     return this.originatedEvent;
/*      */   }
/*      */ 
/*      */   public int getOutOfServiceEvent()
/*      */   {
/*  612 */     return this.outOfServiceEvent;
/*      */   }
/*      */ 
/*      */   public int getPickupCall()
/*      */   {
/*  620 */     return this.pickupCall;
/*      */   }
/*      */ 
/*      */   public int getPrivateEvent()
/*      */   {
/*  628 */     return this.privateEvent;
/*      */   }
/*      */ 
/*      */   public int getPrivateStatusEvent()
/*      */   {
/*  636 */     return this.privateStatusEvent;
/*      */   }
/*      */ 
/*      */   public int getQueryAgentState()
/*      */   {
/*  644 */     return this.queryAgentState;
/*      */   }
/*      */ 
/*      */   public int getQueryDeviceInfo()
/*      */   {
/*  652 */     return this.queryDeviceInfo;
/*      */   }
/*      */ 
/*      */   public int getQueryDnd()
/*      */   {
/*  660 */     return this.queryDnd;
/*      */   }
/*      */ 
/*      */   public int getQueryFwd()
/*      */   {
/*  668 */     return this.queryFwd;
/*      */   }
/*      */ 
/*      */   public int getQueryLastNumber()
/*      */   {
/*  676 */     return this.queryLastNumber;
/*      */   }
/*      */ 
/*      */   public int getQueryMwi()
/*      */   {
/*  684 */     return this.queryMwi;
/*      */   }
/*      */ 
/*      */   public int getQueuedEvent()
/*      */   {
/*  692 */     return this.queuedEvent;
/*      */   }
/*      */ 
/*      */   public int getReadyEvent()
/*      */   {
/*  700 */     return this.readyEvent;
/*      */   }
/*      */ 
/*      */   public int getReconnectCall()
/*      */   {
/*  708 */     return this.reconnectCall;
/*      */   }
/*      */ 
/*      */   public int getReRoute()
/*      */   {
/*  716 */     return this.reRoute;
/*      */   }
/*      */ 
/*      */   public int getRetrieveCall()
/*      */   {
/*  724 */     return this.retrieveCall;
/*      */   }
/*      */ 
/*      */   public int getRetrievedEvent()
/*      */   {
/*  732 */     return this.retrievedEvent;
/*      */   }
/*      */ 
/*      */   public int getRouteEndEvent()
/*      */   {
/*  740 */     return this.routeEndEvent;
/*      */   }
/*      */ 
/*      */   public int getRouteRequestEvent()
/*      */   {
/*  748 */     return this.routeRequestEvent;
/*      */   }
/*      */ 
/*      */   public int getRouteSelect()
/*      */   {
/*  756 */     return this.routeSelect;
/*      */   }
/*      */ 
/*      */   public int getRouteUsedEvent()
/*      */   {
/*  764 */     return this.routeUsedEvent;
/*      */   }
/*      */ 
/*      */   public int getSendPrivateEvent()
/*      */   {
/*  772 */     return this.sendPrivateEvent;
/*      */   }
/*      */ 
/*      */   public int getServiceInitiatedEvent()
/*      */   {
/*  780 */     return this.serviceInitiatedEvent;
/*      */   }
/*      */ 
/*      */   public int getSetAgentState()
/*      */   {
/*  788 */     return this.setAgentState;
/*      */   }
/*      */ 
/*      */   public int getSetDnd()
/*      */   {
/*  796 */     return this.setDnd;
/*      */   }
/*      */ 
/*      */   public int getSetFwd()
/*      */   {
/*  804 */     return this.setFwd;
/*      */   }
/*      */ 
/*      */   public int getSetMwi()
/*      */   {
/*  812 */     return this.setMwi;
/*      */   }
/*      */ 
/*      */   public int getSnapshotCallReq()
/*      */   {
/*  820 */     return this.snapshotCallReq;
/*      */   }
/*      */ 
/*      */   public int getSnapshotDeviceReq()
/*      */   {
/*  828 */     return this.snapshotDeviceReq;
/*      */   }
/*      */ 
/*      */   public int getSysStatEvent()
/*      */   {
/*  836 */     return this.sysStatEvent;
/*      */   }
/*      */ 
/*      */   public int getSysStatReq()
/*      */   {
/*  844 */     return this.sysStatReq;
/*      */   }
/*      */ 
/*      */   public int getSysStatReqConf()
/*      */   {
/*  852 */     return this.sysStatReqConf;
/*      */   }
/*      */ 
/*      */   public int getSysStatReqEvent()
/*      */   {
/*  860 */     return this.sysStatReqEvent;
/*      */   }
/*      */ 
/*      */   public int getSysStatStart()
/*      */   {
/*  868 */     return this.sysStatStart;
/*      */   }
/*      */ 
/*      */   public int getSysStatStop()
/*      */   {
/*  876 */     return this.sysStatStop;
/*      */   }
/*      */ 
/*      */   public int getTransferCall()
/*      */   {
/*  884 */     return this.transferCall;
/*      */   }
/*      */ 
/*      */   public int getTransferredEvent()
/*      */   {
/*  892 */     return this.transferredEvent;
/*      */   }
/*      */ 
/*      */   public int getWorkNotReadyEvent()
/*      */   {
/*  900 */     return this.workNotReadyEvent;
/*      */   }
/*      */ 
/*      */   public int getWorkReadyEvent()
/*      */   {
/*  908 */     return this.workReadyEvent;
/*      */   }
/*      */ 
/*      */   public void setReadyEvent(int readyEvent)
/*      */   {
/*  916 */     this.readyEvent = readyEvent;
/*      */   }
/*      */ 
/*      */   public void setAlternateCall(int alternateCall)
/*      */   {
/*  924 */     this.alternateCall = alternateCall;
/*      */   }
/*      */ 
/*      */   public void setAnswerCall(int answerCall)
/*      */   {
/*  932 */     this.answerCall = answerCall;
/*      */   }
/*      */ 
/*      */   public void setCallCompletion(int callCompletion)
/*      */   {
/*  940 */     this.callCompletion = callCompletion;
/*      */   }
/*      */ 
/*      */   public void setClearCall(int clearCall)
/*      */   {
/*  948 */     this.clearCall = clearCall;
/*      */   }
/*      */ 
/*      */   public void setClearConnection(int clearConnection)
/*      */   {
/*  956 */     this.clearConnection = clearConnection;
/*      */   }
/*      */ 
/*      */   public void setConferenceCall(int conferenceCall)
/*      */   {
/*  964 */     this.conferenceCall = conferenceCall;
/*      */   }
/*      */ 
/*      */   public void setConsultationCall(int consultationCall)
/*      */   {
/*  972 */     this.consultationCall = consultationCall;
/*      */   }
/*      */ 
/*      */   public void setDeflectCall(int deflectCall)
/*      */   {
/*  980 */     this.deflectCall = deflectCall;
/*      */   }
/*      */ 
/*      */   public void setPickupCall(int pickupCall)
/*      */   {
/*  989 */     this.pickupCall = pickupCall;
/*      */   }
/*      */ 
/*      */   public void setGroupPickupCall(int groupPickupCall)
/*      */   {
/*  997 */     this.groupPickupCall = groupPickupCall;
/*      */   }
/*      */ 
/*      */   public void setHoldCall(int holdCall)
/*      */   {
/* 1005 */     this.holdCall = holdCall;
/*      */   }
/*      */ 
/*      */   public void setMakeCall(int makeCall)
/*      */   {
/* 1013 */     this.makeCall = makeCall;
/*      */   }
/*      */ 
/*      */   public void setMakePredictiveCall(int makePredictiveCall)
/*      */   {
/* 1021 */     this.makePredictiveCall = makePredictiveCall;
/*      */   }
/*      */ 
/*      */   public void setQueryMwi(int queryMwi)
/*      */   {
/* 1029 */     this.queryMwi = queryMwi;
/*      */   }
/*      */ 
/*      */   public void setQueryDnd(int queryDnd)
/*      */   {
/* 1037 */     this.queryDnd = queryDnd;
/*      */   }
/*      */ 
/*      */   public void setQueryFwd(int queryFwd)
/*      */   {
/* 1045 */     this.queryFwd = queryFwd;
/*      */   }
/*      */ 
/*      */   public void setQueryAgentState(int queryAgentState)
/*      */   {
/* 1053 */     this.queryAgentState = queryAgentState;
/*      */   }
/*      */ 
/*      */   public void setQueryLastNumber(int queryLastNumber)
/*      */   {
/* 1061 */     this.queryLastNumber = queryLastNumber;
/*      */   }
/*      */ 
/*      */   public void setQueryDeviceInfo(int queryDeviceInfo)
/*      */   {
/* 1069 */     this.queryDeviceInfo = queryDeviceInfo;
/*      */   }
/*      */ 
/*      */   public void setReconnectCall(int reconnectCall)
/*      */   {
/* 1077 */     this.reconnectCall = reconnectCall;
/*      */   }
/*      */ 
/*      */   public void setRetrieveCall(int retrieveCall)
/*      */   {
/* 1085 */     this.retrieveCall = retrieveCall;
/*      */   }
/*      */ 
/*      */   public void setSetMwi(int setMwi)
/*      */   {
/* 1093 */     this.setMwi = setMwi;
/*      */   }
/*      */ 
/*      */   public void setSetDnd(int setDnd)
/*      */   {
/* 1101 */     this.setDnd = setDnd;
/*      */   }
/*      */ 
/*      */   public void setSetFwd(int setFwd)
/*      */   {
/* 1109 */     this.setFwd = setFwd;
/*      */   }
/*      */ 
/*      */   public void setSetAgentState(int setAgentState)
/*      */   {
/* 1117 */     this.setAgentState = setAgentState;
/*      */   }
/*      */ 
/*      */   public void setTransferCall(int transferCall)
/*      */   {
/* 1125 */     this.transferCall = transferCall;
/*      */   }
/*      */ 
/*      */   public void setEventReport(int eventReport)
/*      */   {
/* 1133 */     this.eventReport = eventReport;
/*      */   }
/*      */ 
/*      */   public void setCallClearedEvent(int callClearedEvent)
/*      */   {
/* 1141 */     this.callClearedEvent = callClearedEvent;
/*      */   }
/*      */ 
/*      */   public void setConferencedEvent(int conferencedEvent)
/*      */   {
/* 1149 */     this.conferencedEvent = conferencedEvent;
/*      */   }
/*      */ 
/*      */   public void setConnectionClearedEvent(int connectionClearedEvent)
/*      */   {
/* 1157 */     this.connectionClearedEvent = connectionClearedEvent;
/*      */   }
/*      */ 
/*      */   public void setDeliveredEvent(int deliveredEvent)
/*      */   {
/* 1165 */     this.deliveredEvent = deliveredEvent;
/*      */   }
/*      */ 
/*      */   public void setDivertedEvent(int divertedEvent)
/*      */   {
/* 1173 */     this.divertedEvent = divertedEvent;
/*      */   }
/*      */ 
/*      */   public void setEstablishedEvent(int establishedEvent)
/*      */   {
/* 1181 */     this.establishedEvent = establishedEvent;
/*      */   }
/*      */ 
/*      */   public void setFailedEvent(int failedEvent)
/*      */   {
/* 1189 */     this.failedEvent = failedEvent;
/*      */   }
/*      */ 
/*      */   public void setHeldEvent(int heldEvent)
/*      */   {
/* 1197 */     this.heldEvent = heldEvent;
/*      */   }
/*      */ 
/*      */   public void setNetworkReachedEvent(int networkReachedEvent)
/*      */   {
/* 1205 */     this.networkReachedEvent = networkReachedEvent;
/*      */   }
/*      */ 
/*      */   public void setOriginatedEvent(int originatedEvent)
/*      */   {
/* 1213 */     this.originatedEvent = originatedEvent;
/*      */   }
/*      */ 
/*      */   public void setQueuedEvent(int queuedEvent)
/*      */   {
/* 1221 */     this.queuedEvent = queuedEvent;
/*      */   }
/*      */ 
/*      */   public void setRetrievedEvent(int retrievedEvent)
/*      */   {
/* 1229 */     this.retrievedEvent = retrievedEvent;
/*      */   }
/*      */ 
/*      */   public void setServiceInitiatedEvent(int serviceInitiatedEvent)
/*      */   {
/* 1237 */     this.serviceInitiatedEvent = serviceInitiatedEvent;
/*      */   }
/*      */ 
/*      */   public void setTransferredEvent(int transferredEvent)
/*      */   {
/* 1245 */     this.transferredEvent = transferredEvent;
/*      */   }
/*      */ 
/*      */   public void setCallInformationEvent(int callInformationEvent)
/*      */   {
/* 1253 */     this.callInformationEvent = callInformationEvent;
/*      */   }
/*      */ 
/*      */   public void setDoNotDisturbEvent(int doNotDisturbEvent)
/*      */   {
/* 1261 */     this.doNotDisturbEvent = doNotDisturbEvent;
/*      */   }
/*      */ 
/*      */   public void setForwardingEvent(int forwardingEvent)
/*      */   {
/* 1269 */     this.forwardingEvent = forwardingEvent;
/*      */   }
/*      */ 
/*      */   public void setMessageWaitingEvent(int messageWaitingEvent)
/*      */   {
/* 1277 */     this.messageWaitingEvent = messageWaitingEvent;
/*      */   }
/*      */ 
/*      */   public void setLoggedOnEvent(int loggedOnEvent)
/*      */   {
/* 1285 */     this.loggedOnEvent = loggedOnEvent;
/*      */   }
/*      */ 
/*      */   public void setLoggedOffEvent(int loggedOffEvent)
/*      */   {
/* 1293 */     this.loggedOffEvent = loggedOffEvent;
/*      */   }
/*      */ 
/*      */   public void setNotReadyEvent(int notReadyEvent)
/*      */   {
/* 1301 */     this.notReadyEvent = notReadyEvent;
/*      */   }
/*      */ 
/*      */   public void setWorkNotReadyEvent(int workNotReadyEvent)
/*      */   {
/* 1309 */     this.workNotReadyEvent = workNotReadyEvent;
/*      */   }
/*      */ 
/*      */   public void setWorkReadyEvent(int workReadyEvent)
/*      */   {
/* 1317 */     this.workReadyEvent = workReadyEvent;
/*      */   }
/*      */ 
/*      */   public void setBackInServiceEvent(int backInServiceEvent)
/*      */   {
/* 1325 */     this.backInServiceEvent = backInServiceEvent;
/*      */   }
/*      */ 
/*      */   public void setOutOfServiceEvent(int outOfServiceEvent)
/*      */   {
/* 1333 */     this.outOfServiceEvent = outOfServiceEvent;
/*      */   }
/*      */ 
/*      */   public void setPrivateEvent(int privateEvent)
/*      */   {
/* 1341 */     this.privateEvent = privateEvent;
/*      */   }
/*      */ 
/*      */   public void setRouteRequestEvent(int routeRequestEvent)
/*      */   {
/* 1349 */     this.routeRequestEvent = routeRequestEvent;
/*      */   }
/*      */ 
/*      */   public void setReRoute(int reRoute)
/*      */   {
/* 1357 */     this.reRoute = reRoute;
/*      */   }
/*      */ 
/*      */   public void setRouteSelect(int routeSelect)
/*      */   {
/* 1365 */     this.routeSelect = routeSelect;
/*      */   }
/*      */ 
/*      */   public void setRouteUsedEvent(int routeUsedEvent)
/*      */   {
/* 1373 */     this.routeUsedEvent = routeUsedEvent;
/*      */   }
/*      */ 
/*      */   public void setRouteEndEvent(int routeEndEvent)
/*      */   {
/* 1381 */     this.routeEndEvent = routeEndEvent;
/*      */   }
/*      */ 
/*      */   public void setMonitorDevice(int monitorDevice)
/*      */   {
/* 1389 */     this.monitorDevice = monitorDevice;
/*      */   }
/*      */ 
/*      */   public void setMonitorCall(int monitorCall)
/*      */   {
/* 1397 */     this.monitorCall = monitorCall;
/*      */   }
/*      */ 
/*      */   public void setMonitorCallsViaDevice(int monitorCallsViaDevice)
/*      */   {
/* 1405 */     this.monitorCallsViaDevice = monitorCallsViaDevice;
/*      */   }
/*      */ 
/*      */   public void setChangeMonitorFilter(int changeMonitorFilter)
/*      */   {
/* 1413 */     this.changeMonitorFilter = changeMonitorFilter;
/*      */   }
/*      */ 
/*      */   public void setMonitorStop(int monitorStop)
/*      */   {
/* 1421 */     this.monitorStop = monitorStop;
/*      */   }
/*      */ 
/*      */   public void setMonitorEnded(int monitorEnded)
/*      */   {
/* 1429 */     this.monitorEnded = monitorEnded;
/*      */   }
/*      */ 
/*      */   public void setSnapshotDeviceReq(int snapshotDeviceReq)
/*      */   {
/* 1437 */     this.snapshotDeviceReq = snapshotDeviceReq;
/*      */   }
/*      */ 
/*      */   public void setSnapshotCallReq(int snapshotCallReq)
/*      */   {
/* 1445 */     this.snapshotCallReq = snapshotCallReq;
/*      */   }
/*      */ 
/*      */   public void setEscapeService(int escapeService)
/*      */   {
/* 1453 */     this.escapeService = escapeService;
/*      */   }
/*      */ 
/*      */   public void setPrivateStatusEvent(int privateStatusEvent)
/*      */   {
/* 1461 */     this.privateStatusEvent = privateStatusEvent;
/*      */   }
/*      */ 
/*      */   public void setEscapeServiceEvent(int escapeServiceEvent)
/*      */   {
/* 1469 */     this.escapeServiceEvent = escapeServiceEvent;
/*      */   }
/*      */ 
/*      */   public void setEscapeServiceConf(int escapeServiceConf)
/*      */   {
/* 1477 */     this.escapeServiceConf = escapeServiceConf;
/*      */   }
/*      */ 
/*      */   public void setSendPrivateEvent(int sendPrivateEvent)
/*      */   {
/* 1485 */     this.sendPrivateEvent = sendPrivateEvent;
/*      */   }
/*      */ 
/*      */   public void setSysStatReq(int sysStatReq)
/*      */   {
/* 1493 */     this.sysStatReq = sysStatReq;
/*      */   }
/*      */ 
/*      */   public void setSysStatStart(int sysStatStart)
/*      */   {
/* 1501 */     this.sysStatStart = sysStatStart;
/*      */   }
/*      */ 
/*      */   public void setSysStatStop(int sysStatStop)
/*      */   {
/* 1509 */     this.sysStatStop = sysStatStop;
/*      */   }
/*      */ 
/*      */   public void setChangeSysStatFilter(int changeSysStatFilter)
/*      */   {
/* 1517 */     this.changeSysStatFilter = changeSysStatFilter;
/*      */   }
/*      */ 
/*      */   public void setSysStatReqEvent(int sysStatReqEvent)
/*      */   {
/* 1525 */     this.sysStatReqEvent = sysStatReqEvent;
/*      */   }
/*      */ 
/*      */   public void setSysStatReqConf(int sysStatReqConf)
/*      */   {
/* 1533 */     this.sysStatReqConf = sysStatReqConf;
/*      */   }
/*      */ 
/*      */   public void setSysStatEvent(int sysStatEvent)
/*      */   {
/* 1541 */     this.sysStatEvent = sysStatEvent;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAGetAPICapsConfEvent
 * JD-Core Version:    0.5.4
 */