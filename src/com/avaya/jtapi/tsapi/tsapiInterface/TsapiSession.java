/*      */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.TSProvider;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
/*      */ import com.avaya.jtapi.tsapi.TsapiProviderUnavailableException;
/*      */ import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
/*      */ import com.avaya.jtapi.tsapi.TsapiSocketException;
/*      */ import com.avaya.jtapi.tsapi.TsapiUnableToSendException;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSAbortStream;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSAuthReply;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSAuthReplyTwo;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSClientHeartbeatEvent;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSCloseStreamConfEvent;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSConfirmation;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSKeyReply;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSKeyRequest;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSNameSrvReply;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSOpenStream;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSOpenStreamConfEvent;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSRequestPrivilegesConfEvent;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSSetHeartbeatIntervalConfEvent;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSSetPrivilegesConfEvent;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSUniversalFailureConfEvent;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSUniversalFailureEvent;
/*      */ import com.avaya.jtapi.tsapi.asn1.TsapiPDU;
/*      */ import com.avaya.jtapi.tsapi.asn1.TsapiRequest;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAAlternateCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAAnswerCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTACallClearedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTACallCompletionConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAClearCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAClearConnectionConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConferenceCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConferencedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConfirmation;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionClearedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConsultationCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTADeflectCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTADeliveredEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTADivertedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTADoNotDisturbEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEscapeSvcConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEstablishedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAFailedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAForwardingEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAGetAPICapsConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAGetDeviceListConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAGroupPickupCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAHeldEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAHoldCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTALoggedOffEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTALoggedOnEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMakeCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMakePredictiveCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMessageWaitingEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMonitorConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMonitorEndedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMonitorStopConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTANetworkReachedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTANotReadyEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAOriginatedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAPickupCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivateEventReport;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivateStatusEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryAgentStateConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryCallMonitorConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryDeviceInfoConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryDndConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryFwdConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryMwiConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueuedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAReRouteRequest;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAReadyEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAReconnectCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARetrieveCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARetrievedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteEndEventReport;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterAbortEventReport;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterCancelConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterReqConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteRequestEv;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteRequestExtEv;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteUsedEventReport;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteUsedExtEventReport;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAServiceInitiatedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASetAgentStateConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASetDndConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASetFwdConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASetMwiConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASnapshotCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASysStatEventReport;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASysStatReq;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASysStatReqConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASysStatStart;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASysStatStartConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASysStatStop;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASysStatStopConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTATransferCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTATransferredEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAUniversalFailureConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAUnsolicited;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAWorkNotReadyEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAWorkReadyEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentPrivateData;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.streams.IntelByteArrayInputStream;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.streams.IntelByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Vector;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class TsapiSession
/*      */   implements TsapiChannelReadHandler
/*      */ {
/*  160 */   private static Logger log = Logger.getLogger(TsapiSession.class);
/*      */   private TsapiInvokeIDTable invokeTable;
/*      */   private TsapiChannel channel;
/*      */   private TsapiEventHandler eventHandler;
/*      */   private TSProvider provider;
/*      */   private TsapiUnsolicitedHandler unsolicitedHandler;
/*      */   private TsapiHeartbeatStatus heartbeatStatus;
/*      */   private IntelByteArrayOutputStream out;
/*  169 */   private boolean inService = true;
/*      */   private String debugID;
/*      */   private static final int AC_BLOCK_VER = 1;
/*      */   private static final int AC_BLOCK_SIZE = 18;
/*      */   private static final int DEFAULT_TIMEOUT = 60000;
/*  176 */   private static int timeout = 60000;
/*      */   private String theVendor;
/*      */   private byte[] vendorVersion;
/*      */   private String apiVersion;
/*  182 */   private String switchName = "";
/*  183 */   private String serverID = "";
/*      */ 
/*  192 */   private boolean requestingTrustedApplicationStatus = false;
/*      */ 
/*      */   public TsapiSession(TsapiChannel _channel, boolean _asynchThread, String _debugID)
/*      */   {
/*  208 */     this.debugID = _debugID;
/*  209 */     this.channel = _channel;
/*  210 */     this.channel.setReadHandler(this);
/*      */ 
/*  212 */     this.invokeTable = new TsapiInvokeIDTable(this.debugID);
/*  213 */     this.out = new IntelByteArrayOutputStream();
/*  214 */     this.eventHandler = new TsapiEventDistributor(this.invokeTable, this.debugID);
/*  215 */     this.heartbeatStatus = new TsapiHeartbeatStatus();
/*      */ 
/*  223 */     if (_asynchThread)
/*  224 */       this.eventHandler = new TsapiEventQueue(this.eventHandler, this.debugID);
/*      */   }
/*      */ 
/*      */   public void startSession(String tlink, String login, String passwd, Vector<TsapiVendor> vendors, int timeout)
/*      */   {
/*      */     try
/*      */     {
/*  247 */       TsapiRequest req = new ACSKeyRequest(login);
/*      */ 
/*  249 */       byte[] kPriv = { -128, 1, 1, 1, 3, 1, 1 };
/*      */ 
/*  261 */       this.theVendor = "NT_TCP";
/*  262 */       CSTAPrivate keyPriv = new CSTAPrivate("NT_TCP", kPriv, 0);
/*      */ 
/*  266 */       CSTAEvent event = send(req, keyPriv, timeout);
/*      */ 
/*  274 */       byte[] cryptPass = null;
/*      */ 
/*  276 */       if (event.getEvent() instanceof ACSKeyReply)
/*      */       {
/*  278 */         ACSKeyReply reply = (ACSKeyReply)event.getEvent();
/*      */ 
/*  280 */         cryptPass = Crypt.scramblePassword(passwd, reply.getObjectID(), reply.getKey());
/*      */       }
/*  282 */       else if (event.getEvent() instanceof ACSAuthReply)
/*      */       {
/*  284 */         ACSAuthReply reply = (ACSAuthReply)event.getEvent();
/*      */ 
/*  286 */         cryptPass = Crypt.scramblePassword(passwd, reply.getObjectID(), reply.getKey());
/*      */       }
/*  288 */       else if (event.getEvent() instanceof ACSAuthReplyTwo)
/*      */       {
/*  290 */         ACSAuthReplyTwo reply = (ACSAuthReplyTwo)event.getEvent();
/*      */ 
/*  292 */         cryptPass = Crypt.encode(passwd, reply.getKey());
/*      */       }
/*      */       else
/*      */       {
/*  296 */         throw new TsapiPlatformException(4, 0, "unexpected reply on key request to <" + this.channel.getInetSocketAddress() + ">");
/*      */       }
/*      */ 
/*  303 */       req = new ACSOpenStream((isRequestingTrustedApplicationStatus()) ? (short)5 : (short)1, tlink, login, cryptPass, "Jtapi Client", (short)1, "TS1:2", "AES5.2.0.483", "");
/*      */ 
/*  308 */       CSTAPrivate openPriv = null;
/*      */ 
/*  310 */       String version_range = "4-8";
/*      */ 
/*  318 */       StringBuffer vendStr = new StringBuffer("#ECS#" + version_range + "#" + "AT&T Definity G3" + "#" + version_range);
/*      */ 
/*  320 */       if (vendors != null)
/*      */       {
/*  322 */         Enumeration vendEnum = vendors.elements();
/*      */ 
/*  324 */         while (vendEnum.hasMoreElements())
/*      */         {
/*      */           TsapiVendor vendor;
/*      */           try
/*      */           {
/*  329 */             vendor = (TsapiVendor)vendEnum.nextElement();
/*      */           }
/*      */           catch (NoSuchElementException e)
/*      */           {
/*  333 */             log.error(e.getMessage(), e);
continue;
/*  334 */           }
/*      */ 
/*  336 */           if (!LucentPrivateData.isAvayaVendor(vendor.name));
/*  337 */           vendStr.append("#" + vendor.name + "#" + vendor.versions);
/*      */         }
/*      */       }
/*  340 */       vendStr.append("#");
/*  341 */       byte[] buf = vendStr.toString().getBytes();
/*  342 */       this.theVendor = "VERSION";
/*  343 */       openPriv = new CSTAPrivate("VERSION", buf, 0);
/*  344 */       openPriv.data[0] = 0;
/*  345 */       openPriv.data[(openPriv.data.length - 1)] = 0;
/*      */ 
/*  347 */       event = send(req, openPriv, timeout);
/*      */ 
/*  349 */       if (event.getEvent() instanceof ACSUniversalFailureConfEvent)
/*      */       {
/*  351 */         TSErrorMap.throwACSException(((ACSUniversalFailureConfEvent)event.getEvent()).getError());
/*      */       }
/*  353 */       else if (!(event.getEvent() instanceof ACSOpenStreamConfEvent))
/*      */       {
/*  355 */         throw new TsapiPlatformException(4, 0, "unexpected reply on open stream");
/*      */       }
/*      */ 
/*  359 */       this.apiVersion = ((ACSOpenStreamConfEvent)event.getEvent()).getApiVer();
/*      */ 
/*  361 */       if (event.getPrivData() != null)
/*      */       {
/*  363 */         this.theVendor = ((CSTAPrivate)event.getPrivData()).vendor;
/*  364 */         this.vendorVersion = ((CSTAPrivate)event.getPrivData()).data;
/*      */       }
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  369 */       log.error("Tsapi<init>: " + e);
/*  370 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  374 */       log.error("Tsapi<init>: " + e);
/*  375 */       throw new TsapiPlatformException(4, 0, "initialization failed");
/*      */     }
/*      */ 
/*  380 */     storeServerID(tlink);
/*      */ 
/*  383 */     storeSwitchName(tlink);
/*      */ 
/*  385 */     this.inService = true;
/*      */   }
/*      */ 
/*      */   public synchronized void close()
/*      */   {
/*  395 */     if (!this.inService) {
/*  396 */       this.channel.close();
/*  397 */       return;
/*      */     }
/*      */ 
/*  400 */     TsapiRequest req = new ACSAbortStream();
/*      */     try
/*      */     {
/*  403 */       sendAsync(req, null);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  407 */       log.error("shutdown(): " + e);
/*      */     }
/*      */     finally
/*      */     {
/*  411 */       this.inService = false;
/*  412 */       this.invokeTable.shutdown();
/*  413 */       this.eventHandler.close();
/*  414 */       this.channel.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   public CSTAEvent send(TsapiRequest req, CSTAPrivate priv)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  426 */     return send(req, priv, true, null, timeout);
/*      */   }
/*      */ 
/*      */   public CSTAEvent send(TsapiRequest req, CSTAPrivate priv, int timeout)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  437 */     return send(req, priv, true, null, timeout);
/*      */   }
/*      */ 
/*      */   public void send(TsapiRequest req, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  448 */     send(req, priv, true, handler, timeout);
/*      */   }
/*      */ 
/*      */   public void sendAsync(TsapiRequest req, CSTAPrivate priv, ConfHandler handler)
/*      */   {
/*      */     try
/*      */     {
/*  455 */       send(req, priv, false, handler, timeout);
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  459 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  463 */       log.error("sendAsync: " + e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void sendAsync(TsapiRequest req, CSTAPrivate priv)
/*      */   {
/*  469 */     sendAsync(req, priv, null);
/*      */   }
/*      */ 
/*      */   private CSTAEvent send(TsapiRequest req, CSTAPrivate priv, boolean sync, ConfHandler handler, int timeout)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  502 */     if (!this.inService)
/*      */     {
/*  504 */       throw new TsapiUnableToSendException(4, 2, "client not in service");
/*      */     }
/*      */ 
/*  508 */     TSInvokeID invokeID = this.invokeTable.allocTSInvokeID(handler);
/*  509 */     req.setInvokeID(invokeID.getValue());
/*      */ 
/*  511 */     if (priv != null) {
/*  512 */       priv.vendor = this.theVendor;
/*      */     }
/*  514 */     invokeID.setServiceRequestTurnaroundTime(System.currentTimeMillis());
/*      */     try
/*      */     {
/*  517 */       sendMsg(req, priv);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  521 */       log.error("send: " + e);
/*  522 */       log.error(e.getMessage(), e);
/*  523 */       throw new TsapiSocketException(4, 0, "send request failed");
/*      */     }
/*      */ 
/*  527 */     if (sync)
/*      */     {
/*  529 */       CSTAEvent conf = invokeID.waitForConf(timeout);
/*      */ 
/*  531 */       if (conf == null)
/*      */       {
/*  533 */         throw new TsapiPlatformException(4, 0, "no conf event");
/*      */       }
/*      */ 
/*  537 */       if (conf.getEvent() instanceof CSTAUniversalFailureConfEvent)
/*      */       {
/*  539 */         TSErrorMap.throwCSTAException(((CSTAUniversalFailureConfEvent)conf.getEvent()).getError());
/*      */       }
/*  541 */       if (conf.getEvent() instanceof ACSUniversalFailureConfEvent)
/*      */       {
/*  543 */         TSErrorMap.throwACSException(((ACSUniversalFailureConfEvent)conf.getEvent()).getError());
/*      */       }
/*  545 */       CSTAPrivate.translatePrivateData(conf, this.debugID);
/*      */ 
/*  547 */       return conf;
/*      */     }
/*  549 */     if (handler == null)
/*      */     {
/*  555 */       this.invokeTable.deallocTSInvokeID(invokeID);
/*      */     }
/*      */ 
/*  558 */     return null;
/*      */   }
/*      */ 
/*      */   TSProvider getProvider()
/*      */   {
/*  569 */     return this.provider;
/*      */   }
/*      */ 
/*      */   public void setHandler(TsapiUnsolicitedHandler _handler)
/*      */   {
/*  582 */     this.unsolicitedHandler = _handler;
/*  583 */     this.eventHandler.setUnsolicitedHandler(this.unsolicitedHandler);
/*      */   }
/*      */ 
/*      */   public TsapiUnsolicitedHandler getHandler()
/*      */   {
/*  591 */     return this.unsolicitedHandler;
/*      */   }
/*      */ 
/*      */   private void sendMsg(TsapiRequest req, CSTAPrivate priv)
/*      */     throws IOException
/*      */   {
/*  607 */     synchronized (this.out)
/*      */     {
/*  609 */       IntelByteArrayOutputStream acBlock = new IntelByteArrayOutputStream(18);
/*  610 */       IntelByteArrayOutputStream encodeStream = new IntelByteArrayOutputStream();
/*  611 */       IntelByteArrayOutputStream privateData = new IntelByteArrayOutputStream((priv != null) ? 34 + priv.data.length : 0);
/*      */ 
/*  613 */       log.info("Sent InvokeID " + req.getInvokeID() + " for " + this.debugID);
/*  614 */       if (log.isDebugEnabled())
/*      */       {
/*  616 */         Collection lines = req.print();
/*  617 */         for (Object line : lines)
/*  618 */           log.debug(line);
/*      */       }
/*      */       try
/*      */       {
/*  622 */         req.encode(encodeStream);
/*      */       }
/*      */       catch (Exception e) {
/*  625 */         log.error("encode: " + e);
/*      */       }
/*      */ 
/*  628 */       if (priv != null)
/*      */       {
/*  630 */         if (log.isDebugEnabled()) {
/*  631 */           for (String str : priv.print()) {
/*  632 */             log.debug(str);
/*      */           }
/*      */         }
/*  635 */         int length = priv.vendor.length();
/*  636 */         byte[] vendor = priv.vendor.getBytes();
/*  637 */         for (int i = 0; i < 32; ++i)
/*      */         {
/*  639 */           privateData.write((i < length) ? vendor[i] : 0);
/*      */         }
/*  641 */         privateData.writeShort(priv.data.length);
/*  642 */         privateData.write(priv.data, 0, priv.data.length);
/*      */       }
/*      */ 
/*  646 */       acBlock.writeShort(1);
/*  647 */       acBlock.writeInt(req.getInvokeID());
/*  648 */       acBlock.writeInt(0);
/*  649 */       acBlock.writeShort(req.getPDUClass());
/*  650 */       acBlock.writeShort(req.getPDU());
/*  651 */       acBlock.writeShort(encodeStream.size());
/*  652 */       acBlock.writeShort(privateData.size());
/*      */ 
/*  654 */       this.out.writeInt(acBlock.size() + encodeStream.size() + privateData.size());
/*  655 */       acBlock.writeTo(this.out);
/*  656 */       encodeStream.writeTo(this.out);
/*  657 */       privateData.writeTo(this.out);
/*      */ 
/*  659 */       this.channel.write(this.out);
/*  660 */       this.out.reset();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void handleRead(IntelByteArrayInputStream msg)
/*      */   {
/*      */     try
/*      */     {
/*  678 */       if (msg.readShort() != 1)
/*      */       {
/*  680 */         throw new TsapiPlatformException(4, 0, "message has wrong acBlock version");
/*      */       }
/*      */ 
/*  683 */       int invokeID = msg.readInt();
/*  684 */       int monitorCrossRefID = msg.readInt();
/*  685 */       int eventClass = msg.readShort();
/*  686 */       int eventType = msg.readShort();
/*  687 */       msg.readShort();
/*  688 */       int privLength = msg.readShort();
/*      */ 
/*  690 */       ACSEventHeader eventHeader = new ACSEventHeader(eventClass, eventType);
/*      */ 
/*  692 */       TsapiPDU pdu = decodePDU(msg, eventClass, eventType);
/*      */ 
/*  694 */       if (pdu == null)
/*      */       {
/*  696 */         return;
/*      */       }
/*      */ 
/*  699 */       if (pdu instanceof CSTAUnsolicited)
/*      */       {
/*  701 */         ((CSTAUnsolicited)pdu).setMonitorCrossRefID(monitorCrossRefID);
/*  702 */         log.info("Received monitorCrossRefID " + monitorCrossRefID + " for " + this.debugID);
/*      */       }
/*  704 */       else if (pdu instanceof CSTAConfirmation)
/*      */       {
/*  706 */         ((CSTAConfirmation)pdu).setInvokeID(invokeID);
/*  707 */         log.info("Received invokeID " + invokeID + " for " + this.debugID);
/*      */       }
/*  709 */       else if (pdu instanceof ACSConfirmation)
/*      */       {
/*  711 */         ((ACSConfirmation)pdu).setInvokeID(invokeID);
/*  712 */         log.info("Received invokeID " + invokeID + " for " + this.debugID);
/*      */       }
/*      */ 
/*  717 */       if (log.isDebugEnabled()) {
/*  718 */         Collection lines = pdu.print();
/*  719 */         for (Object line : lines)
/*  720 */           log.debug(line);
/*      */       }
/*      */       CSTAPrivate priv;
/*  726 */       if (privLength > 0)
/*      */       {
/*  728 */         priv = getPrivate(msg, eventType);
/*      */ 
/*  730 */         if (log.isDebugEnabled()) {
/*  731 */           for (String str : priv.print()) {
/*  732 */             log.debug(str);
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  738 */         priv = null;
/*      */       }
/*      */ 
/*  741 */       if (this.eventHandler == null) {
/*  742 */         log.error("TsapiSession: no eventHandler for session, discarding message.");
/*      */ 
/*  744 */         return;
/*      */       }
/*  746 */       processEvent(new CSTAEvent(eventHeader, pdu, priv));
/*      */     }
/*      */     catch (Exception e) {
/*  749 */       if (this.unsolicitedHandler == null) {
/*  750 */         log.error("TsapiSession: no handler when Exception received, closing session. " + e);
/*      */ 
/*  752 */         log.error(e.getMessage(), e);
/*  753 */         close();
/*  754 */         return;
/*      */       }
/*  756 */       this.unsolicitedHandler.eventDistributorException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void processEvent(CSTAEvent event)
/*      */   {
/*  774 */     if (heartbeatIsEnabled())
/*      */     {
/*  776 */       this.heartbeatStatus.receivedEvent();
/*      */     }
/*      */ 
/*  779 */     switch (event.getEventHeader().getEventClass())
/*      */     {
/*      */     case 2:
/*  784 */       ACSConfirmation acsConf = (ACSConfirmation)event.getEvent();
/*  785 */       TSInvokeID invokeID = this.invokeTable.getTSInvokeID(acsConf.getInvokeID());
/*  786 */       if (invokeID == null)
/*      */         return;
/*  788 */       if ((invokeID.getConfHandler() != null) && (!(invokeID.getConfHandler() instanceof HandleConfOnCurrentThread)))
/*      */       {
/*  791 */         this.eventHandler.handleEvent(event); return;
/*      */       }
/*      */ 
/*  795 */       this.invokeTable.deallocTSInvokeID(invokeID);
/*  796 */       invokeID.setConf(event); break;
/*      */     case 5:
/*  802 */       switch (event.getEventHeader().getEventType())
/*      */       {
/*      */       case 38:
/*      */       case 53:
/*      */       case 90:
/*      */       case 99:
/*      */       case 101:
/*      */       case 103:
/*      */       case 114:
/*      */       case 118:
/*      */       case 125:
/*      */       case 127:
/*      */       case 129:
/*  817 */         CSTAConfirmation cstaConf = (CSTAConfirmation)event.getEvent();
/*  818 */         invokeID = this.invokeTable.getTSInvokeID(cstaConf.getInvokeID());
/*  819 */         if (invokeID == null)
/*      */           return;
/*  821 */         if ((invokeID.getConfHandler() != null) && (!(invokeID.getConfHandler() instanceof HandleConfOnCurrentThread)))
/*      */         {
/*  824 */           this.eventHandler.handleEvent(event); return;
/*      */         }
/*      */ 
/*  833 */         switch (event.getEventHeader().getEventType())
/*      */         {
/*      */         case 38:
/*      */         case 90:
/*  837 */           CSTAPrivate.translatePrivateData(event, this.debugID);
/*      */         }
/*      */ 
/*  843 */         this.invokeTable.deallocTSInvokeID(invokeID);
/*  844 */         invokeID.setConf(event); break;
/*      */       default:
/*  850 */         this.eventHandler.handleEvent(event);
/*  851 */       }break;
/*      */     case 1:
/*      */     case 3:
/*      */     case 4:
/*      */     case 6:
/*  858 */       this.eventHandler.handleEvent(event);
/*  859 */       break;
/*      */     default:
/*  861 */       log.info("WARNING: event class " + event.getEventHeader().getEventClass() + " not implemented");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void handleException(Exception e)
/*      */   {
/*  878 */     if (this.unsolicitedHandler == null) {
/*  879 */       log.error("Call Control: no handler for session");
/*  880 */       log.error("Exception received: " + e);
/*  881 */       log.error(e.getMessage(), e);
/*      */       try
/*      */       {
/*  886 */         close();
/*      */       } catch (Exception e2) {
/*      */       }
/*  889 */       return;
/*      */     }
/*  891 */     if (this.inService)
/*      */     {
/*      */       try
/*      */       {
/*  895 */         TsapiSocketException tse = new TsapiSocketException(4, 0, "read request failed");
/*  896 */         this.unsolicitedHandler.eventDistributorException(tse);
/*      */       }
/*      */       catch (Exception e1)
/*      */       {
/*      */         try
/*      */         {
/*  902 */           close();
/*      */         }
/*      */         catch (Exception e2)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */       try
/*      */       {
/*  912 */         close();
/*      */       }
/*      */       catch (Exception e2)
/*      */       {
/*      */       }
/*      */   }
/*      */ 
/*      */   private static CSTAPrivate getPrivate(IntelByteArrayInputStream msg, int eventType)
/*      */     throws IOException
/*      */   {
/*  933 */     byte[] vendBuf = new byte[32];
/*  934 */     msg.read(vendBuf);
/*      */ 
/*  937 */     int vendLen = 0;
/*  938 */     while (vendBuf[vendLen] != 0) {
/*  939 */       ++vendLen;
/*      */     }
/*  941 */     int length = msg.readShort();
/*  942 */     byte[] data = new byte[length];
/*  943 */     msg.read(data);
/*      */ 
/*  945 */     return new CSTAPrivate(new String(vendBuf, 0, vendLen), data, eventType);
/*      */   }
/*      */ 
/*      */   private TsapiPDU decodePDU(InputStream msg, int eventClass, int eventType)
/*      */   {
/*  961 */     switch (eventClass)
/*      */     {
/*  992 */     case 1:
/*      */     case 2:
/*  965 */       switch (eventType) { case 2:
/*  968 */         return ACSOpenStreamConfEvent.decode(msg);
/*      */       case 4:
/*  970 */         return ACSCloseStreamConfEvent.decode(msg);
/*      */       case 6:
/*  972 */         return ACSUniversalFailureConfEvent.decode(msg);
/*      */       case 7:
/*  974 */         return ACSUniversalFailureEvent.decode(msg);
/*      */       case 9:
/*  976 */         return ACSKeyReply.decode(msg);
/*      */       case 11:
/*  978 */         return ACSNameSrvReply.decode(msg);
/*      */       case 12:
/*  980 */         return ACSAuthReply.decode(msg);
/*      */       case 13:
/*  982 */         return ACSAuthReplyTwo.decode(msg);
/*      */       case 15:
/*  984 */         return ACSSetHeartbeatIntervalConfEvent.decode(msg);
/*      */       case 16:
/*  986 */         return ACSClientHeartbeatEvent.decode(msg);
/*      */       case 18:
/*  988 */         return ACSRequestPrivilegesConfEvent.decode(msg);
/*      */       case 20:
/*  990 */         return ACSSetPrivilegesConfEvent.decode(msg);
/*      */       case 3:
/*      */       case 5:
/*      */       case 8:
/*      */       case 10:
/*      */       case 14:
/*      */       case 17:
/*      */       case 19: } break;
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/* 1000 */       switch (eventType) { case 2:
/* 1003 */         return CSTAAlternateCallConfEvent.decode(msg);
/*      */       case 4:
/* 1005 */         return CSTAAnswerCallConfEvent.decode(msg);
/*      */       case 6:
/* 1007 */         return CSTACallCompletionConfEvent.decode(msg);
/*      */       case 8:
/* 1009 */         return CSTAClearCallConfEvent.decode(msg);
/*      */       case 10:
/* 1011 */         return CSTAClearConnectionConfEvent.decode(msg);
/*      */       case 12:
/* 1013 */         return CSTAConferenceCallConfEvent.decode(msg);
/*      */       case 14:
/* 1015 */         return CSTAConsultationCallConfEvent.decode(msg);
/*      */       case 16:
/* 1017 */         return CSTADeflectCallConfEvent.decode(msg);
/*      */       case 18:
/* 1019 */         return CSTAPickupCallConfEvent.decode(msg);
/*      */       case 20:
/* 1021 */         return CSTAGroupPickupCallConfEvent.decode(msg);
/*      */       case 22:
/* 1023 */         return CSTAHoldCallConfEvent.decode(msg);
/*      */       case 24:
/* 1025 */         return CSTAMakeCallConfEvent.decode(msg);
/*      */       case 26:
/* 1027 */         return CSTAMakePredictiveCallConfEvent.decode(msg);
/*      */       case 28:
/* 1029 */         return CSTAQueryMwiConfEvent.decode(msg);
/*      */       case 30:
/* 1031 */         return CSTAQueryDndConfEvent.decode(msg);
/*      */       case 32:
/* 1033 */         return CSTAQueryFwdConfEvent.decode(msg);
/*      */       case 34:
/* 1035 */         return CSTAQueryAgentStateConfEvent.decode(msg);
/*      */       case 38:
/* 1037 */         return CSTAQueryDeviceInfoConfEvent.decode(msg);
/*      */       case 40:
/* 1039 */         return CSTAReconnectCallConfEvent.decode(msg);
/*      */       case 42:
/* 1041 */         return CSTARetrieveCallConfEvent.decode(msg);
/*      */       case 44:
/* 1043 */         return CSTASetMwiConfEvent.decode(msg);
/*      */       case 46:
/* 1045 */         return CSTASetDndConfEvent.decode(msg);
/*      */       case 48:
/* 1047 */         return CSTASetFwdConfEvent.decode(msg);
/*      */       case 50:
/* 1049 */         return CSTASetAgentStateConfEvent.decode(msg);
/*      */       case 52:
/* 1051 */         return CSTATransferCallConfEvent.decode(msg);
/*      */       case 53:
/* 1053 */         return CSTAUniversalFailureConfEvent.decode(msg);
/*      */       case 54:
/* 1055 */         return CSTACallClearedEvent.decode(msg);
/*      */       case 55:
/* 1057 */         return CSTAConferencedEvent.decode(msg);
/*      */       case 56:
/* 1059 */         return CSTAConnectionClearedEvent.decode(msg);
/*      */       case 57:
/* 1061 */         return CSTADeliveredEvent.decode(msg);
/*      */       case 58:
/* 1063 */         return CSTADivertedEvent.decode(msg);
/*      */       case 59:
/* 1065 */         return CSTAEstablishedEvent.decode(msg);
/*      */       case 60:
/* 1067 */         return CSTAFailedEvent.decode(msg);
/*      */       case 61:
/* 1069 */         return CSTAHeldEvent.decode(msg);
/*      */       case 62:
/* 1071 */         return CSTANetworkReachedEvent.decode(msg);
/*      */       case 63:
/* 1073 */         return CSTAOriginatedEvent.decode(msg);
/*      */       case 64:
/* 1075 */         return CSTAQueuedEvent.decode(msg);
/*      */       case 65:
/* 1077 */         return CSTARetrievedEvent.decode(msg);
/*      */       case 66:
/* 1079 */         return CSTAServiceInitiatedEvent.decode(msg);
/*      */       case 67:
/* 1081 */         return CSTATransferredEvent.decode(msg);
/*      */       case 69:
/* 1083 */         return CSTADoNotDisturbEvent.decode(msg);
/*      */       case 70:
/* 1085 */         return CSTAForwardingEvent.decode(msg);
/*      */       case 71:
/* 1087 */         return CSTAMessageWaitingEvent.decode(msg);
/*      */       case 72:
/* 1089 */         return CSTALoggedOnEvent.decode(msg);
/*      */       case 73:
/* 1091 */         return CSTALoggedOffEvent.decode(msg);
/*      */       case 74:
/* 1093 */         return CSTANotReadyEvent.decode(msg);
/*      */       case 75:
/* 1095 */         return CSTAReadyEvent.decode(msg);
/*      */       case 76:
/* 1097 */         return CSTAWorkNotReadyEvent.decode(msg);
/*      */       case 77:
/* 1099 */         return CSTAWorkReadyEvent.decode(msg);
/*      */       case 79:
/* 1101 */         return CSTARouteRegisterReqConfEvent.decode(msg);
/*      */       case 81:
/* 1103 */         return CSTARouteRegisterCancelConfEvent.decode(msg);
/*      */       case 82:
/* 1105 */         return CSTARouteRegisterAbortEventReport.decode(msg);
/*      */       case 85:
/* 1107 */         return CSTAReRouteRequest.decode(msg);
/*      */       case 87:
/* 1109 */         return CSTARouteEndEventReport.decode(msg);
/*      */       case 90:
/* 1111 */         return CSTAEscapeSvcConfEvent.decode(msg);
/*      */       case 93:
/* 1113 */         return CSTAPrivateEventReport.decode(msg);
/*      */       case 94:
/* 1115 */         return CSTAPrivateStatusEvent.decode(msg);
/*      */       case 114:
/* 1117 */         return CSTAMonitorConfEvent.decode(msg);
/*      */       case 118:
/* 1119 */         return CSTAMonitorStopConfEvent.decode(msg);
/*      */       case 119:
/* 1121 */         return CSTAMonitorEndedEvent.decode(msg);
/*      */       case 121:
/* 1123 */         return CSTASnapshotCallConfEvent.decode(msg);
/*      */       case 123:
/* 1125 */         return CSTASnapshotDeviceConfEvent.decode(msg);
/*      */       case 125:
/* 1127 */         return CSTAGetAPICapsConfEvent.decode(msg);
/*      */       case 127:
/* 1129 */         return CSTAGetDeviceListConfEvent.decode(msg);
/*      */       case 129:
/* 1131 */         return CSTAQueryCallMonitorConfEvent.decode(msg);
/*      */       case 83:
/* 1133 */         return CSTARouteRequestEv.decode(msg);
/*      */       case 86:
/* 1135 */         return CSTARouteUsedEventReport.decode(msg);
/*      */       case 130:
/* 1137 */         return CSTARouteRequestExtEv.decode(msg);
/*      */       case 131:
/* 1139 */         return CSTARouteUsedExtEventReport.decode(msg);
/*      */       case 100:
/* 1141 */         return CSTASysStatStart.decode(msg);
/*      */       case 106:
/* 1143 */         return CSTASysStatEventReport.decode(msg);
/*      */       case 101:
/* 1145 */         return CSTASysStatStartConfEvent.decode(msg);
/*      */       case 102:
/* 1147 */         return CSTASysStatStop.decode(msg);
/*      */       case 103:
/* 1149 */         return CSTASysStatStopConfEvent.decode(msg);
/*      */       case 98:
/* 1151 */         return CSTASysStatReq.decode(msg);
/*      */       case 99:
/* 1153 */         return CSTASysStatReqConfEvent.decode(msg);
/*      */       case 3:
/*      */       case 5:
/*      */       case 7:
/*      */       case 9:
/*      */       case 11:
/*      */       case 13:
/*      */       case 15:
/*      */       case 17:
/*      */       case 19:
/*      */       case 21:
/*      */       case 23:
/*      */       case 25:
/*      */       case 27:
/*      */       case 29:
/*      */       case 31:
/*      */       case 33:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 39:
/*      */       case 41:
/*      */       case 43:
/*      */       case 45:
/*      */       case 47:
/*      */       case 49:
/*      */       case 51:
/*      */       case 68:
/*      */       case 78:
/*      */       case 80:
/*      */       case 84:
/*      */       case 88:
/*      */       case 89:
/*      */       case 91:
/*      */       case 92:
/*      */       case 95:
/*      */       case 96:
/*      */       case 97:
/*      */       case 104:
/*      */       case 105:
/*      */       case 107:
/*      */       case 108:
/*      */       case 109:
/*      */       case 110:
/*      */       case 111:
/*      */       case 112:
/*      */       case 113:
/*      */       case 115:
/*      */       case 116:
/*      */       case 117:
/*      */       case 120:
/*      */       case 122:
/*      */       case 124:
/*      */       case 126:
/*      */       case 128: }  } log.info("got unknown event class " + eventClass + ", event type " + eventType + " for " + this.debugID);
/* 1163 */     return null;
/*      */   }
/*      */ 
/*      */   private void storeServerID(String tLink)
/*      */   {
/* 1175 */     this.serverID = tLink.toUpperCase();
/*      */   }
/*      */ 
/*      */   private void storeSwitchName(String tLink)
/*      */   {
/* 1188 */     String[] tokens = tLink.split("#");
/*      */     try
/*      */     {
/* 1191 */       this.switchName = tokens[1];
/*      */     }
/*      */     catch (ArrayIndexOutOfBoundsException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void requestTimeOut(ConfHandler handler)
/*      */   {
/* 1205 */     this.invokeTable.requestTimeOut(handler);
/*      */   }
/*      */ 
/*      */   public boolean isInService()
/*      */   {
/* 1212 */     return this.inService;
/*      */   }
/*      */ 
/*      */   public String getTheVendor()
/*      */   {
/* 1219 */     return this.theVendor;
/*      */   }
/*      */ 
/*      */   public String getApiVersion()
/*      */   {
/* 1227 */     return this.apiVersion;
/*      */   }
/*      */ 
/*      */   public byte[] getVendorVersion()
/*      */   {
/* 1235 */     return this.vendorVersion;
/*      */   }
/*      */ 
/*      */   public synchronized String getServerID()
/*      */   {
/* 1242 */     return this.serverID;
/*      */   }
/*      */ 
/*      */   public synchronized String getSwitchName()
/*      */   {
/* 1249 */     return this.switchName;
/*      */   }
/*      */ 
/*      */   public boolean heartbeatIsEnabled()
/*      */   {
/* 1258 */     return this.heartbeatStatus.heartbeatIsEnabled();
/*      */   }
/*      */ 
/*      */   public void enableHeartbeat()
/*      */   {
/* 1266 */     this.heartbeatStatus.enableHeartbeat();
/*      */   }
/*      */ 
/*      */   public void disableHeartbeat()
/*      */   {
/* 1274 */     this.heartbeatStatus.disableHeartbeat();
/*      */   }
/*      */ 
/*      */   public void setClientHeartbeatInterval(short heartbeatInterval)
/*      */   {
/* 1287 */     this.heartbeatStatus.setHeartbeatInterval(heartbeatInterval);
/*      */   }
/*      */ 
/*      */   void setHeartbeatTimeoutListener(ITsapiHeartbeatTimeoutListener listener)
/*      */   {
/* 1298 */     this.heartbeatStatus.setHeartbeatTimeoutListener(listener);
/*      */   }
/*      */ 
/*      */   public boolean isRequestingTrustedApplicationStatus()
/*      */   {
/* 1305 */     return this.requestingTrustedApplicationStatus;
/*      */   }
/*      */ 
/*      */   public void setRequestingTrustedApplicationStatus(boolean requestingTrustedApplicationStatus)
/*      */   {
/* 1322 */     this.requestingTrustedApplicationStatus = requestingTrustedApplicationStatus;
/*      */   }
/*      */ 
/*      */   public static void setTimeout(int _timeout) {
/* 1326 */     timeout = _timeout;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiSession
 * JD-Core Version:    0.5.4
 */