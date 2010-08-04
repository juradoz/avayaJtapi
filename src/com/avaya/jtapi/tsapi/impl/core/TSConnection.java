/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.ITsapiException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
/*      */ import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
/*      */ import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentSelectiveListeningHold;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentSelectiveListeningRetrieve;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentSendDTMFTone;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV5SendDTMFTone;
/*      */ import com.avaya.jtapi.tsapi.impl.TsapiConnCapabilities;
/*      */ import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
/*      */ import com.avaya.jtapi.tsapi.impl.TsapiTermConnCapabilities;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
/*      */ import java.util.Vector;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public final class TSConnection
/*      */ {
/*   54 */   private static Logger log = Logger.getLogger(TSConnection.class);
/*      */   TSProviderImpl provider;
/*      */   int connState;
/*      */   int termConnState;
/* 2138 */   boolean isTermConn = false;
/*      */   TSConnection connection;
/*      */   Vector<TSConnection> termConns;
/*      */   Vector<TSConnection> staleTermConns;
/* 2146 */   boolean haveNetworkReached = false;
/*      */   CSTAConnectionID connID;
/*      */   TSCall call;
/*      */   TSDevice device;
/* 2154 */   Object replyTermConnPriv = null;
/* 2155 */   Object replyConnPriv = null;
/*      */ 
/* 2157 */   boolean constructed = false;
/*      */ 
/* 2160 */   TSConnection acdManagerConn = null;
/* 2161 */   Vector<TSConnection> acdConns = null;
/*      */ 
/* 2163 */   TSTrunk trunk = null;
/*      */ 
/* 2168 */   private TSCallObjectAge my_age = new TSCallObjectAge();
/*      */ 
/* 2171 */   private boolean doNotExpectConnectionClearedEvent = false;
/*      */ 
/*      */   void dump(String indent)
/*      */   {
/*   58 */     log.trace(indent + "***** CONNECTION DUMP *****");
/*   59 */     log.trace(indent + "TSConnection: " + this);
/*   60 */     log.trace(indent + "TSConnection ID: " + this.connID);
/*   61 */     log.trace(indent + "TSConnection is terminal connection? " + this.isTermConn);
/*   62 */     log.trace(indent + "TSConnection age: " + this.my_age);
/*   63 */     log.trace(indent + "TSConnection conn state: " + this.connState);
/*   64 */     log.trace(indent + "TSConnection term conn state: " + this.termConnState);
/*   65 */     if (this.termConns != null)
/*      */     {
/*   67 */       log.trace(indent + "TSConnection terminal connections: ");
/*   68 */       synchronized (this.termConns)
/*      */       {
/*   71 */         for (int i = 0; i < this.termConns.size(); ++i)
/*      */         {
/*   73 */           TSConnection conn = (TSConnection)this.termConns.elementAt(i);
/*   74 */           conn.dump(indent + " ");
/*      */         }
/*      */       }
/*      */     }
/*   78 */     if (this.staleTermConns != null)
/*      */     {
/*   80 */       log.trace(indent + "TSConnection stale terminal connections: ");
/*   81 */       synchronized (this.staleTermConns)
/*      */       {
/*   84 */         for (int i = 0; i < this.staleTermConns.size(); ++i)
/*      */         {
/*   86 */           TSConnection conn = (TSConnection)this.staleTermConns.elementAt(i);
/*   87 */           conn.dump(indent + " ");
/*      */         }
/*      */       }
/*      */     }
/*   91 */     if (this.connection != null)
/*      */     {
/*   93 */       log.trace(indent + "TSConnection connection: " + this.connection);
/*      */     }
/*   95 */     if (this.trunk != null)
/*      */     {
/*   97 */       log.trace(indent + "TSTrunk trunk: " + this.trunk);
/*      */     }
/*   99 */     log.trace(indent + "***** CONNECTION DUMP END *****");
/*      */   }
/*      */ 
/*      */   public int getConnectionState()
/*      */   {
/*  105 */     this.call.updateObject();
/*      */ 
/*  107 */     return getTSConnState();
/*      */   }
/*      */ 
/*      */   int getTSConnState()
/*      */   {
/*      */     int connectionState;
/*  114 */     if ((this.isTermConn) && (this.connection != null))
/*      */     {
/*  116 */       connectionState = this.connection.getCallControlConnState();
/*      */     }
/*      */     else {
/*  119 */       connectionState = this.connState;
/*      */     }
/*  121 */     switch (connectionState)
/*      */     {
/*      */     case 80:
/*  124 */       return 48;
/*      */     case 81:
/*      */     case 82:
/*  127 */       return 49;
/*      */     case 83:
/*  129 */       return 50;
/*      */     case 84:
/*      */     case 85:
/*      */     case 86:
/*      */     case 87:
/*      */     case 88:
/*  135 */       return 51;
/*      */     case 89:
/*  137 */       return 52;
/*      */     case 90:
/*  139 */       return 53;
/*      */     case 91:
/*      */     }
/*  142 */     return 54;
/*      */   }
/*      */ 
/*      */   public int getTerminalConnectionState()
/*      */   {
/*  149 */     this.call.updateObject();
/*      */ 
/*  151 */     return getTSTermConnState();
/*      */   }
/*      */ 
/*      */   int getTSTermConnState()
/*      */   {
/*  157 */     switch (this.termConnState)
/*      */     {
/*      */     case 96:
/*  160 */       return 64;
/*      */     case 97:
/*  162 */       return 65;
/*      */     case 98:
/*      */     case 99:
/*  165 */       return 67;
/*      */     case 100:
/*      */     case 101:
/*  168 */       return 66;
/*      */     case 102:
/*  170 */       return 68;
/*      */     case 103:
/*      */     }
/*  173 */     return 69;
/*      */   }
/*      */ 
/*      */   public TSProviderImpl getTSProviderImpl()
/*      */   {
/*  179 */     return this.provider;
/*      */   }
/*      */ 
/*      */   public Vector<TSConnection> getTSTermConns()
/*      */   {
/*  185 */     if (getConnectionState() == 52)
/*      */     {
/*  187 */       return null;
/*      */     }
/*      */ 
/*  190 */     return getTermConns();
/*      */   }
/*      */ 
/*      */   Vector<TSConnection> getTermConns()
/*      */   {
/*  196 */     if ((this.provider.isLucent()) && (this.termConns != null))
/*      */     {
/*  198 */       return this.termConns;
/*      */     }
/*      */ 
/*  201 */     Vector cv = new Vector();
/*      */ 
/*  203 */     if (this.isTermConn)
/*      */     {
/*  205 */       cv.addElement(this);
/*      */     }
/*      */ 
/*  208 */     return cv;
/*      */   }
/*      */ 
/*      */   public TSConnection getTSConnection()
/*      */   {
/*  214 */     this.call.updateObject();
/*  215 */     return getTSConn();
/*      */   }
/*      */ 
/*      */   TSConnection getTSConn()
/*      */   {
/*  221 */     if ((this.provider.isLucent()) && (this.isTermConn))
/*      */     {
/*  223 */       return this.connection;
/*      */     }
/*      */ 
/*  226 */     return this;
/*      */   }
/*      */ 
/*      */   public Object getTermConnPrivateData()
/*      */   {
/*  232 */     if (this.replyTermConnPriv instanceof CSTAPrivate)
/*  233 */       return this.replyTermConnPriv;
/*  234 */     return null;
/*      */   }
/*      */ 
/*      */   public Object getConnPrivateData()
/*      */   {
/*  240 */     if (this.replyConnPriv instanceof CSTAPrivate)
/*  241 */       return this.replyConnPriv;
/*  242 */     return null;
/*      */   }
/*      */ 
/*      */   public Object sendPrivateData(CSTAPrivate data)
/*      */   {
/*      */     try
/*      */     {
/*  250 */       return this.provider.sendPrivateData(data);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  254 */       if (e instanceof ITsapiException) {
/*  255 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "sendPrivateData failure");
/*      */       }
/*  257 */       throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public TSCall getTSCall()
/*      */   {
/*  264 */     return this.call;
/*      */   }
/*      */ 
/*      */   public TSDevice getTSDevice()
/*      */   {
/*  270 */     return this.device;
/*      */   }
/*      */ 
/*      */   public TSTrunk getTSTrunk()
/*      */   {
/*  276 */     return this.trunk;
/*      */   }
/*      */ 
/*      */   public void disconnect(CSTAPrivate reqConnPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  285 */     if (this.provider.getCapabilities().getClearConnection() == 0)
/*      */     {
/*  287 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*  289 */     if (this.call.updateObject())
/*      */     {
/*  291 */       int state = getTSConnState();
/*  292 */       if ((state != 49) && (state != 50) && (state != 51) && (state != 53) && (state != 54))
/*      */       {
/*  298 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, true), 2, state, "connection not in acceptable state");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  306 */     DisconnectedConfHandler handler = new DisconnectedConfHandler(this, 10);
/*      */     try
/*      */     {
/*  309 */       if ((this.provider.isLucent()) && (this.termConns != null))
/*      */       {
/*  311 */         Vector tcArray = new Vector(this.termConns);
/*      */ 
/*  313 */         handler.handleIt = false;
/*  314 */         for (int i = 0; i < tcArray.size(); ++i)
/*      */         {
/*  316 */           TSConnection tc = (TSConnection)tcArray.elementAt(i);
/*  317 */           if (i == tcArray.size() - 1)
/*      */           {
/*  319 */             handler.handleIt = true;
/*      */           }
/*  321 */           this.provider.tsapi.clearConnection(tc.connID, reqConnPriv, handler);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  326 */         this.provider.tsapi.clearConnection(this.connID, reqConnPriv, handler);
/*      */       }
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/*  331 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/*  335 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  342 */       if (e.getErrorType() == 2)
/*      */       {
/*  344 */         switch (e.getErrorCode())
/*      */         {
/*      */         case 24:
/*      */         case 27:
/*  348 */           log.info("Conn " + this + ": clearConnection " + "Universal Failure with error " + e.getErrorCode() + " requires snapshot of " + this.call + " for " + this.provider);
/*      */ 
/*  351 */           this.call.updateSuspiciousObject();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  356 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  360 */       if (e instanceof ITsapiException) {
/*  361 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "clearConnection failure");
/*      */       }
/*  363 */       throw new TsapiPlatformException(4, 0, "clearConnection failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void answer(CSTAPrivate reqTermConnPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  373 */     if (this.provider.getCapabilities().getAnswerCall() == 0)
/*      */     {
/*  375 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*  377 */     if (this.call.updateObject())
/*      */     {
/*  379 */       int state = getTSTermConnState();
/*  380 */       if ((state != 65) && (state != 69))
/*      */       {
/*  383 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 5, state, "terminal connection not ringing");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  390 */     ConfHandler handler = new TalkingConfHandler(this, 4);
/*      */     try
/*      */     {
/*  393 */       this.provider.tsapi.answerCall(this.connID, reqTermConnPriv, handler);
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/*  397 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/*  401 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  408 */       if (e.getErrorType() == 2)
/*      */       {
/*  410 */         switch (e.getErrorCode())
/*      */         {
/*      */         case 13:
/*      */         case 28:
/*  415 */           log.info("Conn " + this + " answer UniversalFailure " + e.getErrorCode() + " requires snapshot of " + this.call + " for " + this.provider);
/*  416 */           this.call.updateSuspiciousObject();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  421 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  425 */       if (e instanceof ITsapiException) {
/*  426 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "answerCall failure");
/*      */       }
/*  428 */       throw new TsapiPlatformException(4, 0, "answerCall failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public TsapiConnCapabilities getTsapiConnCapabilities()
/*      */   {
/*  435 */     return this.provider.getTsapiConnCapabilities();
/*      */   }
/*      */ 
/*      */   public TsapiTermConnCapabilities getTsapiTermConnCapabilities()
/*      */   {
/*  441 */     return this.provider.getTsapiTermConnCapabilities();
/*      */   }
/*      */ 
/*      */   public int getCallControlConnectionState()
/*      */   {
/*  449 */     this.call.updateObject();
/*      */ 
/*  451 */     return getCallControlConnState();
/*      */   }
/*      */ 
/*      */   int getCallControlConnState()
/*      */   {
/*  456 */     if ((this.isTermConn) && (this.connection != null))
/*      */     {
/*  458 */       return this.connection.getCallControlConnState();
/*      */     }
/*      */ 
/*  461 */     return this.connState;
/*      */   }
/*      */ 
/*      */   public int getCallControlTerminalConnectionState()
/*      */   {
/*  467 */     this.call.updateObject();
/*  468 */     return getCallControlTermConnState();
/*      */   }
/*      */ 
/*      */   int getCallControlTermConnState()
/*      */   {
/*  473 */     return this.termConnState;
/*      */   }
/*      */ 
/*      */   public TSConnection redirect(String destinationAddress, CSTAPrivate reqConnPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  483 */     if (this.provider.getCapabilities().getDeflectCall() == 0)
/*      */     {
/*  485 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*  487 */     if (this.call.updateObject())
/*      */     {
/*  489 */       int state = getCallControlConnState();
/*  490 */       if ((state != 81) && (state != 83) && (state != 91))
/*      */       {
/*  494 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, true), 2, state, "connection not offering or alerting");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  505 */     ConfHandler handler = new DisconnectedConfHandler(this, 16);
/*      */     try
/*      */     {
/*  508 */       this.provider.tsapi.deflectCall(getConnID(), destinationAddress, reqConnPriv, handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/*  512 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidPartyException e)
/*      */     {
/*  516 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/*  520 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/*  524 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  528 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  532 */       if (e instanceof ITsapiException) {
/*  533 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "deflectCall failure");
/*      */       }
/*  535 */       throw new TsapiPlatformException(4, 0, "deflectCall failure");
/*      */     }
/*      */ 
/*  542 */     return null;
/*      */   }
/*      */ 
/*      */   public void hold(CSTAPrivate reqTermConnPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  551 */     if (this.provider.getCapabilities().getHoldCall() == 0)
/*      */     {
/*  553 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*  555 */     if (this.call.updateObject())
/*      */     {
/*  557 */       int state = getCallControlTermConnState();
/*  558 */       if ((state != 98) && (state != 103))
/*      */       {
/*  561 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 5, state, "terminal connection not talking");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  568 */     ConfHandler handler = new HoldConfHandler(this);
/*      */     try
/*      */     {
/*  571 */       this.provider.tsapi.holdCall(this.connID, false, reqTermConnPriv, handler);
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/*  575 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/*  579 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  586 */       if (e.getErrorType() == 2)
/*      */       {
/*  588 */         switch (e.getErrorCode())
/*      */         {
/*      */         case 13:
/*      */         case 24:
/*  593 */           log.info("Conn " + this + " hold UniversalFailure " + e.getErrorCode() + " requires snapshot of " + this.call + " for " + this.provider);
/*  594 */           this.call.updateSuspiciousObject();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  599 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  603 */       if (e instanceof ITsapiException) {
/*  604 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "holdCall failure");
/*      */       }
/*  606 */       throw new TsapiPlatformException(4, 0, "holdCall failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void unhold(CSTAPrivate reqTermConnPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  616 */     if (this.provider.getCapabilities().getRetrieveCall() == 0)
/*      */     {
/*  618 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*  620 */     if (this.call.updateObject())
/*      */     {
/*  622 */       int state = getCallControlTermConnState();
/*  623 */       if ((state != 99) && (state != 103))
/*      */       {
/*  626 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 5, state, "terminal connection not held");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  634 */     ConfHandler handler = new TalkingConfHandler(this, 42);
/*      */     try
/*      */     {
/*  637 */       this.provider.tsapi.retrieveCall(this.connID, reqTermConnPriv, handler);
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/*  641 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/*  645 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  652 */       if (e.getErrorType() == 2)
/*      */       {
/*  654 */         switch (e.getErrorCode())
/*      */         {
/*      */         case 13:
/*      */         case 24:
/*  659 */           log.info("Conn " + this + " unhold UniversalFailure " + e.getErrorCode() + " requires snapshot of " + this.call + " for " + this.provider);
/*  660 */           this.call.updateSuspiciousObject();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  665 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  669 */       if (e instanceof ITsapiException) {
/*  670 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "retrieveCall failure");
/*      */       }
/*  672 */       throw new TsapiPlatformException(4, 0, "retrieveCall failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void listenHold(TSConnection partyToHold)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  681 */     if (!this.provider.isLucentV5())
/*      */     {
/*  683 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*  685 */     if (this.termConns != null)
/*      */     {
/*  687 */       throw new TsapiInvalidArgumentException(3, 0, "subject Connection contains TerminalConnections");
/*      */     }
/*  689 */     if (this.connID == null)
/*      */     {
/*  691 */       throw new TsapiInvalidArgumentException(3, 0, "subject connID is null");
/*      */     }
/*      */ 
/*  694 */     boolean allParties = true;
/*  695 */     CSTAConnectionID selectedParty = null;
/*      */ 
/*  697 */     if (partyToHold != null)
/*      */     {
/*  699 */       selectedParty = partyToHold.connID;
/*  700 */       if (selectedParty == null)
/*      */       {
/*  702 */         throw new TsapiInvalidArgumentException(3, 0, "partyToHold connID is null");
/*      */       }
/*  704 */       allParties = false;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  709 */       LucentSelectiveListeningHold slh = new LucentSelectiveListeningHold(this.connID, allParties, selectedParty);
/*      */ 
/*  711 */       this.provider.sendPrivateData(slh.makeTsapiPrivate());
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/*  715 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/*  719 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/*  723 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  727 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  731 */       if (e instanceof ITsapiException) {
/*  732 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "listenHold failure");
/*      */       }
/*  734 */       throw new TsapiPlatformException(4, 0, "listenHold failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void listenUnhold(TSConnection partyToUnhold)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  743 */     if (!this.provider.isLucentV5())
/*      */     {
/*  745 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*  747 */     if (this.connID == null)
/*      */     {
/*  749 */       throw new TsapiInvalidArgumentException(3, 0, "subject connID is null");
/*      */     }
/*      */ 
/*  752 */     boolean allParties = true;
/*  753 */     CSTAConnectionID selectedParty = null;
/*      */ 
/*  755 */     if (partyToUnhold != null)
/*      */     {
/*  757 */       selectedParty = partyToUnhold.connID;
/*  758 */       if (selectedParty == null)
/*      */       {
/*  760 */         throw new TsapiInvalidArgumentException(3, 0, "partyToUnhold connID is null");
/*      */       }
/*  762 */       allParties = false;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  767 */       LucentSelectiveListeningRetrieve slr = new LucentSelectiveListeningRetrieve(this.connID, allParties, selectedParty);
/*      */ 
/*  769 */       this.provider.sendPrivateData(slr.makeTsapiPrivate());
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/*  773 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/*  777 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/*  781 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  785 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  789 */       if (e instanceof ITsapiException) {
/*  790 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "listenUnhold failure");
/*      */       }
/*  792 */       throw new TsapiPlatformException(4, 0, "listenUnhold failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void join(CSTAPrivate reqTermConnPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  802 */     if (!this.provider.isLucent()) {
/*  803 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*  805 */     if (this.call.updateObject())
/*      */     {
/*  807 */       int state = getCallControlTermConnState();
/*  808 */       if ((state != 100) && (state != 103))
/*      */       {
/*  811 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 5, state, "terminal connection not bridged");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  819 */     ConfHandler handler = new TalkingConfHandler(this, 4);
/*      */     try
/*      */     {
/*  822 */       this.provider.tsapi.answerCall(this.connID, reqTermConnPriv, handler);
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/*  826 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/*  830 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  837 */       if (e.getErrorType() == 2)
/*      */       {
/*  839 */         switch (e.getErrorCode())
/*      */         {
/*      */         case 13:
/*      */         case 28:
/*  844 */           log.info("Conn " + this + " join UniversalFailure " + e.getErrorCode() + " requires snapshot of " + this.call + " for " + this.provider);
/*  845 */           this.call.updateSuspiciousObject();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  850 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  854 */       if (e instanceof ITsapiException) {
/*  855 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "join failure");
/*      */       }
/*  857 */       throw new TsapiPlatformException(4, 0, "join failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void leave(CSTAPrivate reqTermConnPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  867 */     if (!this.provider.isLucent()) {
/*  868 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*  870 */     if (this.call.updateObject())
/*      */     {
/*  872 */       int state = getCallControlTermConnState();
/*  873 */       if ((state != 98) && (state != 103))
/*      */       {
/*  876 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 5, state, "terminal connection not talking");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  884 */     ConfHandler handler = new BridgedConfHandler(this);
/*      */     try
/*      */     {
/*  887 */       this.provider.tsapi.clearConnection(this.connID, reqTermConnPriv, handler);
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/*  891 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/*  895 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  902 */       if (e.getErrorType() == 2)
/*      */       {
/*  904 */         switch (e.getErrorCode())
/*      */         {
/*      */         case 24:
/*      */         case 27:
/*  908 */           log.info("Conn " + this + ": clearConnection " + "Universal Failure with error " + e.getErrorCode() + " requires snapshot of " + this.call + " for " + this.provider);
/*      */ 
/*  911 */           this.call.updateSuspiciousObject();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  916 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  920 */       if (e instanceof ITsapiException) {
/*  921 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "clearConnection failure");
/*      */       }
/*  923 */       throw new TsapiPlatformException(4, 0, "clearConnection failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public TSConnection getACDManagerConn()
/*      */   {
/*  930 */     return this.acdManagerConn;
/*      */   }
/*      */ 
/*      */   public Vector<TSConnection> getACDConns()
/*      */   {
/*  936 */     return this.acdConns;
/*      */   }
/*      */ 
/*      */   public void generateDtmf(String digits)
/*      */     throws TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  944 */     generateDtmf(digits, 0, 0);
/*      */   }
/*      */ 
/*      */   public void generateDtmf(String digits, int toneDuration, int pauseDuration)
/*      */     throws TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  956 */     if (!this.provider.isLucent())
/*  957 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     LucentSendDTMFTone dtmf;
/*  961 */     if (this.provider.isLucentV5())
/*  962 */       dtmf = new LucentV5SendDTMFTone(this.connID, null, digits, toneDuration, pauseDuration);
/*      */     else {
/*  964 */       dtmf = new LucentSendDTMFTone(this.connID, null, digits, toneDuration, pauseDuration);
/*      */     }
/*      */     try
/*      */     {
/*  968 */       this.provider.sendPrivateData(dtmf.makeTsapiPrivate());
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/*  972 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/*  976 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  980 */       if (e instanceof ITsapiException) {
/*  981 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "sendPrivateData failure");
/*      */       }
/*  983 */       throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   TSConnection(TSProviderImpl _provider, CSTAConnectionID _connID, TSDevice _device, boolean _wantTermConn)
/*      */   {
/* 1001 */     this.constructed = false;
/* 1002 */     this.provider = _provider;
/* 1003 */     this.connID = _connID;
/* 1004 */     this.device = _device;
/* 1005 */     this.connState = 80;
/* 1006 */     this.termConnState = 96;
/* 1007 */     this.acdConns = new Vector();
/*      */ 
/* 1009 */     this.isTermConn = _wantTermConn;
/*      */ 
/* 1012 */     if (this.isTermConn)
/*      */     {
/* 1014 */       if (this.device != null)
/*      */       {
/* 1016 */         this.isTermConn = this.device.isTerminal();
/*      */       }
/*      */       else
/*      */       {
/* 1020 */         this.isTermConn = false;
/*      */       }
/*      */     }
/*      */ 
/* 1024 */     if (this.connID != null)
/*      */     {
/* 1026 */       this.call = this.provider.createCall(this.connID.getCallID());
/* 1027 */       if (this.call.getTSState() == 34)
/*      */       {
/* 1031 */         this.provider.dumpCall(this.connID.getCallID());
/* 1032 */         this.call = this.provider.createCall(this.connID.getCallID());
/*      */       }
/*      */     }
/*      */ 
/* 1036 */     StringBuffer connForProviderString = new StringBuffer();
/*      */ 
/* 1038 */     connForProviderString.append(this).append(" for ").append(this.provider);
/*      */ 
/* 1040 */     if (this.provider.isLucent())
/*      */     {
/* 1042 */       if (this.isTermConn)
/*      */       {
/* 1044 */         log.info("Constructing Lucent termConn " + connForProviderString.toString());
/*      */       }
/*      */       else
/*      */       {
/* 1048 */         log.info("Constructing Lucent conn " + connForProviderString.toString());
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/* 1053 */       log.info("Constructing conn " + connForProviderString.toString());
/*      */   }
/*      */ 
/*      */   synchronized void finishConstruction(TSDevice _connectionAddress, Vector<TSEvent> eventList)
/*      */   {
/* 1059 */     boolean found = true;
/* 1060 */     if ((this.isTermConn) && (this.provider.isLucent()) && (_connectionAddress != null))
/*      */     {
/* 1062 */       this.device.addConnection(this);
/* 1063 */       Vector connVector = new Vector(this.call.getConnections());
/* 1064 */       TSConnection addressConnection = null;
/* 1065 */       found = false;
/* 1066 */       for (int i = 0; i < connVector.size(); ++i)
/*      */       {
/* 1068 */         addressConnection = (TSConnection)connVector.elementAt(i);
/* 1069 */         if (addressConnection.getTSDevice() != _connectionAddress)
/*      */           continue;
/* 1071 */         this.connection = addressConnection;
/* 1072 */         this.connection.addTerminalConnection(this, eventList);
/* 1073 */         found = true;
/* 1074 */         break;
/*      */       }
/*      */ 
/*      */     }
/* 1079 */     else if (this.call != null)
/*      */     {
/* 1081 */       this.device.addConnection(this);
/* 1082 */       this.call.addConnection(this, eventList);
/*      */     }
/*      */ 
/* 1085 */     this.constructed = true;
/* 1086 */     super.notifyAll();
/*      */ 
/* 1088 */     if (!found)
/*      */     {
/* 1095 */       this.connection = this.provider.createConnection(null, _connectionAddress, null);
/* 1096 */       this.connection.addTerminalConnection(this, eventList);
/* 1097 */       if (eventList != null)
/* 1098 */         eventList.addElement(new TSEvent(6, this.connection));
/*      */     }
/* 1100 */     if (eventList == null) {
/*      */       return;
/*      */     }
/*      */ 
/* 1104 */     if ((!this.isTermConn) || (!this.provider.isLucent()))
/* 1105 */       eventList.addElement(new TSEvent(6, this));
/* 1106 */     if (this.isTermConn)
/* 1107 */       eventList.addElement(new TSEvent(13, this));
/*      */   }
/*      */ 
/*      */   synchronized void waitForConstruction()
/*      */   {
/* 1112 */     if (this.constructed)
/*      */       return;
/*      */     try
/*      */     {
/* 1116 */       super.wait(TSProviderImpl.DEFAULT_TIMEOUT);
/*      */     } catch (InterruptedException e) {
/*      */     }
/* 1119 */     if (this.constructed)
/*      */       return;
/* 1121 */     throw new TsapiPlatformException(4, 0, "could not finish connection construction");
/*      */   }
/*      */ 
/*      */   void setConnectionState(int _connState, Vector<TSEvent> eventList)
/*      */   {
/* 1135 */     if ((this.isTermConn) && (this.provider.isLucent()))
/*      */     {
/* 1139 */       if (this.connection != null)
/*      */       {
/* 1141 */         this.connection.setConnectionState(_connState, eventList);
/*      */       }
/* 1143 */       return;
/*      */     }
/*      */ 
/* 1146 */     int oldCoreState = getTSConnState();
/*      */ 
/* 1148 */     synchronized (this)
/*      */     {
/* 1150 */       if ((this.haveNetworkReached) && (_connState == 83))
/*      */       {
/* 1152 */         _connState = 87;
/*      */       }
/* 1154 */       else if ((_connState == 82) && (this.device.getDeviceType() == 1))
/*      */       {
/* 1156 */         _connState = 83;
/*      */       }
/*      */ 
/* 1160 */       if ((this.connState == _connState) || (this.connState == 89))
/*      */       {
/* 1162 */         return;
/*      */       }
/* 1164 */       this.connState = _connState;
/*      */     }
/*      */ 
/* 1167 */     switch (this.connState)
/*      */     {
/*      */     case 83:
/* 1170 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1173 */       if (oldCoreState != 50)
/*      */       {
/* 1175 */         eventList.addElement(new TSEvent(9, this));
/*      */       }
/* 1177 */       eventList.addElement(new TSEvent(26, this)); break;
/*      */     case 88:
/* 1181 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1184 */       if (oldCoreState != 51)
/*      */       {
/* 1186 */         eventList.addElement(new TSEvent(7, this));
/*      */       }
/* 1188 */       eventList.addElement(new TSEvent(21, this)); break;
/*      */     case 86:
/* 1192 */       if (eventList != null)
/*      */       {
/* 1195 */         if (oldCoreState != 51)
/*      */         {
/* 1197 */           eventList.addElement(new TSEvent(7, this));
/*      */         }
/* 1199 */         eventList.addElement(new TSEvent(22, this));
/*      */       }
/* 1201 */       synchronized (this)
/*      */       {
/* 1203 */         this.haveNetworkReached = true;
/*      */       }
/* 1205 */       break;
/*      */     case 87:
/* 1207 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1210 */       if (oldCoreState != 51)
/*      */       {
/* 1212 */         eventList.addElement(new TSEvent(7, this));
/*      */       }
/* 1214 */       eventList.addElement(new TSEvent(23, this)); break;
/*      */     case 84:
/* 1218 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1221 */       if (oldCoreState != 51)
/*      */       {
/* 1223 */         eventList.addElement(new TSEvent(7, this));
/*      */       }
/* 1225 */       eventList.addElement(new TSEvent(24, this));
/*      */ 
/* 1228 */       if (this.provider.getCapabilities().getOriginatedEvent() != 0)
/*      */         return;
/* 1230 */       setConnectionState(88, eventList); break;
/*      */     case 89:
/* 1236 */       synchronized (this)
/*      */       {
/* 1238 */         if (this.trunk != null)
/*      */         {
/* 1240 */           this.call.removeTrunk(this.trunk, eventList);
/* 1241 */           this.trunk = null;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1246 */       if (this.termConns != null)
/*      */       {
/* 1248 */         Vector conn = new Vector(this.termConns);
/*      */         int i;
/* 1249 */         for ( i = 0; i < conn.size(); ++i)
/*      */         {
/* 1251 */           ((TSConnection)conn.elementAt(i)).setTermConnState(102, eventList);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1257 */         setTermConnState(102, eventList);
/*      */       }
/*      */ 
/* 1261 */       if (this.acdManagerConn != null)
/*      */       {
/* 1264 */         TSConnection acdMgrConn = this.acdManagerConn;
/* 1265 */         Vector acdConns = acdMgrConn.getACDConns();
/*      */         int j;
/* 1266 */         for ( j = 0; j < acdConns.size(); ++j)
/*      */         {
/* 1268 */           ((TSConnection)acdConns.elementAt(j)).setACDManagerConn(null);
/* 1269 */           ((TSConnection)acdConns.elementAt(j)).setConnectionState(89, eventList);
/*      */         }
/* 1271 */         acdMgrConn.setConnectionState(89, eventList);
/*      */       }
/*      */ 
/* 1277 */       if (eventList != null)
/*      */       {
/* 1280 */         if (oldCoreState != 52)
/*      */         {
/* 1282 */           eventList.addElement(new TSEvent(10, this));
/*      */         }
/* 1284 */         eventList.addElement(new TSEvent(27, this));
/*      */       }
/* 1286 */       this.device.removeConnection(this);
/* 1287 */       this.call.removeConnection(this, eventList);
/* 1288 */       break;
/*      */     case 90:
/* 1290 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1293 */       if (oldCoreState != 53)
/*      */       {
/* 1295 */         eventList.addElement(new TSEvent(11, this));
/*      */       }
/* 1297 */       eventList.addElement(new TSEvent(28, this)); break;
/*      */     case 82:
/* 1301 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1304 */       if (oldCoreState != 49)
/*      */       {
/* 1306 */         eventList.addElement(new TSEvent(8, this));
/* 1307 */         eventList.addElement(new TSEvent(56, this));
/*      */       }
/* 1309 */       eventList.addElement(new TSEvent(25, this)); break;
/*      */     case 91:
/* 1313 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1316 */       if (oldCoreState != 54)
/*      */       {
/* 1318 */         eventList.addElement(new TSEvent(12, this));
/*      */       }
/* 1320 */       eventList.addElement(new TSEvent(29, this)); break;
/*      */     case 81:
/* 1324 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1327 */       if (oldCoreState != 49)
/*      */       {
/* 1329 */         eventList.addElement(new TSEvent(8, this));
/* 1330 */         eventList.addElement(new TSEvent(56, this));
/*      */       }
/* 1332 */       eventList.addElement(new TSEvent(19, this)); break;
/*      */     case 85:
/* 1336 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1339 */       if (oldCoreState != 51)
/*      */       {
/* 1341 */         eventList.addElement(new TSEvent(7, this));
/*      */       }
/* 1343 */       eventList.addElement(new TSEvent(20, this));
/*      */     }
/*      */   }
/*      */ 
/*      */   void setTermConnState(int _termConnState, Vector<TSEvent> eventList)
/*      */   {
/* 1363 */     if (!this.isTermConn)
/*      */     {
/* 1365 */       return;
/*      */     }
/*      */ 
/* 1368 */     int oldCoreState = getTSTermConnState();
/*      */ 
/* 1370 */     synchronized (this)
/*      */     {
/* 1372 */       if ((this.haveNetworkReached) && (_termConnState == 97))
/*      */       {
/* 1374 */         _termConnState = 98;
/*      */       }
/*      */ 
/* 1379 */       if ((this.termConnState == _termConnState) || (this.termConnState == 102))
/*      */       {
/* 1381 */         return;
/*      */       }
/*      */ 
/* 1384 */       this.termConnState = _termConnState;
/*      */     }
/*      */ 
/* 1387 */     switch (this.termConnState)
/*      */     {
/*      */     case 98:
/* 1390 */       if (eventList != null)
/*      */       {
/* 1393 */         if (oldCoreState != 67)
/*      */         {
/* 1395 */           eventList.addElement(new TSEvent(14, this));
/*      */         }
/* 1397 */         eventList.addElement(new TSEvent(30, this));
/*      */       }
/*      */ 
/* 1401 */       if ((this.connection == null) || 
/* 1404 */         (this.connection.termConns == null))
/*      */         return;
/* 1406 */       Vector conns = new Vector(this.connection.termConns);
/* 1407 */       for (int i = 0; i < conns.size(); ++i)
/*      */       {
/* 1409 */         TSConnection conn = (TSConnection)conns.elementAt(i);
/* 1410 */         if (conn == this)
/*      */           continue;
/* 1412 */         if (conn.termConnState != 97)
/*      */           continue;
/* 1414 */         conn.setTermConnState(100, eventList);
/*      */       }
/*      */ 
/* 1417 */       break;
/*      */     case 99:
/* 1421 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1424 */       if (oldCoreState != 67)
/*      */       {
/* 1426 */         eventList.addElement(new TSEvent(14, this));
/*      */       }
/* 1428 */       eventList.addElement(new TSEvent(31, this)); break;
/*      */     case 102:
/* 1432 */       if (eventList != null)
/*      */       {
/* 1435 */         if (oldCoreState != 68)
/*      */         {
/* 1437 */           eventList.addElement(new TSEvent(17, this));
/*      */         }
/* 1439 */         eventList.addElement(new TSEvent(34, this));
/*      */       }
/* 1441 */       this.device.removeConnection(this);
/* 1442 */       if (this.connection != null)
/*      */       {
/* 1444 */         this.connection.removeTerminalConnection(this, eventList); return;
/*      */       }
/*      */ 
/* 1448 */       this.call.removeConnection(this, eventList);
/*      */ 
/* 1450 */       break;
/*      */     case 97:
/* 1452 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1455 */       if (oldCoreState != 65)
/*      */       {
/* 1457 */         eventList.addElement(new TSEvent(15, this));
/*      */       }
/* 1459 */       eventList.addElement(new TSEvent(35, this)); break;
/*      */     case 100:
/* 1483 */       boolean okToBridge = false;
/* 1484 */       int i = 0;
/* 1485 */       if ((this.connection != null) && 
/* 1488 */         (this.connection.termConns != null))
/*      */       {
/* 1490 */         synchronized (this.connection.termConns)
/*      */         {
/* 1492 */           if (this.connection.termConns.size() == 1)
/*      */           {
/* 1494 */             i = 1;
/*      */           }
/*      */           else
/*      */           {
/* 1498 */             for ( i = 0; i < this.connection.termConns.size(); ++i)
/*      */             {
/* 1500 */               TSConnection conn = (TSConnection)this.connection.termConns.elementAt(i);
/* 1501 */               if (conn == this)
/*      */                 continue;
/* 1503 */               if ((conn.termConnState != 98) && (conn.termConnState != 103)) {
/*      */                 continue;
/*      */               }
/* 1506 */               okToBridge = true;
/* 1507 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1514 */       if (okToBridge)
/*      */       {
/* 1516 */         if (eventList == null) {
/*      */           return;
/*      */         }
/* 1519 */         if (oldCoreState != 66)
/*      */         {
/* 1521 */           eventList.addElement(new TSEvent(16, this));
/*      */         }
/* 1523 */         eventList.addElement(new TSEvent(32, this)); return;
/*      */       }
/*      */ 
/* 1526 */       if (i != 0)
/*      */       {
/* 1528 */         setTermConnState(97, eventList); return;
/*      */       }
/* 1530 */       if (this.connection == null)
/*      */         return;
/* 1532 */       this.connection.setConnectionState(89, eventList); break;
/*      */     case 101:
/* 1536 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1539 */       if (oldCoreState != 66)
/*      */       {
/* 1541 */         eventList.addElement(new TSEvent(16, this));
/*      */       }
/* 1543 */       eventList.addElement(new TSEvent(33, this)); break;
/*      */     case 103:
/* 1547 */       if (eventList == null) {
/*      */         return;
/*      */       }
/* 1550 */       if (oldCoreState != 69)
/*      */       {
/* 1552 */         eventList.addElement(new TSEvent(18, this));
/*      */       }
/* 1554 */       eventList.addElement(new TSEvent(36, this));
/*      */     }
/*      */   }
/*      */ 
/*      */   void addTerminalConnection(TSConnection termConn, Vector<TSEvent> eventList)
/*      */   {
/* 1564 */     if (this.termConns == null)
/*      */     {
/* 1566 */       this.termConns = new Vector();
/* 1567 */       this.termConns.addElement(termConn);
/* 1568 */       this.staleTermConns = new Vector();
/* 1569 */       if (this.call == null)
/*      */       {
/* 1571 */         this.call = termConn.call;
/* 1572 */         this.device.addConnection(this);
/* 1573 */         this.call.addConnection(this, eventList);
/*      */       }
/* 1575 */       this.connID = null;
/*      */     }
/*      */     else
/*      */     {
/* 1579 */       synchronized (this.termConns)
/*      */       {
/* 1581 */         if (!this.termConns.contains(termConn))
/*      */         {
/* 1583 */           this.termConns.addElement(termConn);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1588 */     termConn.connection = this;
/*      */   }
/*      */ 
/*      */   void removeTerminalConnection(TSConnection termConn, Vector<TSEvent> eventList)
/*      */   {
/* 1594 */     if (this.termConns == null)
/*      */       return;
/* 1596 */     if (!this.termConns.removeElement(termConn))
/*      */     {
/* 1598 */       return;
/*      */     }
/* 1600 */     synchronized (this.staleTermConns)
/*      */     {
/* 1602 */       if (!this.staleTermConns.contains(termConn))
/*      */       {
/* 1604 */         this.staleTermConns.addElement(termConn);
/*      */       }
/*      */     }
/*      */ 
/* 1608 */     if (this.termConns.size() != 0)
/*      */       return;
/* 1610 */     setConnectionState(89, eventList);
/*      */   }
/*      */ 
/*      */   void getSnapshot(Vector<TSEvent> eventList)
/*      */   {
/* 1622 */     getSnapshot(eventList, true);
/*      */   }
/*      */ 
/*      */   void getSnapshot(Vector<TSEvent> eventList, boolean includeCreated) {
/* 1626 */     if ((!this.isTermConn) || (!this.provider.isLucent()))
/*      */     {
/* 1628 */       if (includeCreated)
/*      */       {
/* 1630 */         eventList.addElement(new TSEvent(6, this));
/*      */       }
/* 1632 */       switch (this.connState)
/*      */       {
/*      */       case 83:
/* 1635 */         eventList.addElement(new TSEvent(9, this));
/* 1636 */         eventList.addElement(new TSEvent(26, this));
/* 1637 */         break;
/*      */       case 88:
/* 1639 */         eventList.addElement(new TSEvent(7, this));
/* 1640 */         eventList.addElement(new TSEvent(21, this));
/* 1641 */         break;
/*      */       case 86:
/* 1643 */         eventList.addElement(new TSEvent(7, this));
/* 1644 */         eventList.addElement(new TSEvent(22, this));
/* 1645 */         break;
/*      */       case 87:
/* 1647 */         eventList.addElement(new TSEvent(7, this));
/* 1648 */         eventList.addElement(new TSEvent(23, this));
/* 1649 */         break;
/*      */       case 84:
/* 1651 */         eventList.addElement(new TSEvent(7, this));
/* 1652 */         eventList.addElement(new TSEvent(24, this));
/* 1653 */         break;
/*      */       case 89:
/* 1655 */         eventList.addElement(new TSEvent(10, this));
/* 1656 */         eventList.addElement(new TSEvent(27, this));
/* 1657 */         break;
/*      */       case 90:
/* 1659 */         eventList.addElement(new TSEvent(11, this));
/* 1660 */         eventList.addElement(new TSEvent(28, this));
/* 1661 */         break;
/*      */       case 82:
/* 1663 */         eventList.addElement(new TSEvent(8, this));
/* 1664 */         eventList.addElement(new TSEvent(56, this));
/* 1665 */         eventList.addElement(new TSEvent(25, this));
/* 1666 */         break;
/*      */       case 91:
/* 1668 */         eventList.addElement(new TSEvent(12, this));
/* 1669 */         eventList.addElement(new TSEvent(29, this));
/* 1670 */         break;
/*      */       case 81:
/* 1672 */         eventList.addElement(new TSEvent(8, this));
/* 1673 */         eventList.addElement(new TSEvent(56, this));
/* 1674 */         eventList.addElement(new TSEvent(19, this));
/* 1675 */         break;
/*      */       case 85:
/* 1677 */         eventList.addElement(new TSEvent(7, this));
/* 1678 */         eventList.addElement(new TSEvent(20, this));
/*      */       }
/*      */ 
/* 1681 */       if ((this.provider.isLucent()) && (this.termConns != null))
/*      */       {
/* 1683 */         synchronized (this.termConns)
/*      */         {
/* 1685 */           for (int i = 0; i < this.termConns.size(); ++i)
/*      */           {
/* 1687 */             ((TSConnection)this.termConns.elementAt(i)).getSnapshot(eventList, includeCreated);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1692 */     if (!this.isTermConn)
/*      */       return;
/* 1694 */     if (includeCreated)
/*      */     {
/* 1696 */       eventList.addElement(new TSEvent(13, this));
/*      */     }
/* 1698 */     switch (this.termConnState)
/*      */     {
/*      */     case 98:
/* 1701 */       eventList.addElement(new TSEvent(14, this));
/* 1702 */       eventList.addElement(new TSEvent(30, this));
/* 1703 */       break;
/*      */     case 99:
/* 1705 */       eventList.addElement(new TSEvent(14, this));
/* 1706 */       eventList.addElement(new TSEvent(31, this));
/* 1707 */       break;
/*      */     case 102:
/* 1709 */       eventList.addElement(new TSEvent(17, this));
/* 1710 */       eventList.addElement(new TSEvent(34, this));
/* 1711 */       break;
/*      */     case 97:
/* 1713 */       eventList.addElement(new TSEvent(15, this));
/* 1714 */       eventList.addElement(new TSEvent(35, this));
/* 1715 */       break;
/*      */     case 100:
/* 1717 */       eventList.addElement(new TSEvent(16, this));
/* 1718 */       eventList.addElement(new TSEvent(32, this));
/* 1719 */       break;
/*      */     case 101:
/* 1721 */       eventList.addElement(new TSEvent(16, this));
/* 1722 */       eventList.addElement(new TSEvent(33, this));
/* 1723 */       break;
/*      */     case 103:
/* 1725 */       eventList.addElement(new TSEvent(18, this));
/* 1726 */       eventList.addElement(new TSEvent(36, this));
/*      */     }
/*      */   }
/*      */ 
/*      */   void setStateFromLocalConnState(int localCallState)
/*      */   {
/* 1738 */     switch (localCallState)
/*      */     {
/*      */     case 1:
/* 1741 */       setConnectionState(84, null);
/* 1742 */       setTermConnState(98, null);
/* 1743 */       break;
/*      */     case 2:
/* 1745 */       setConnectionState(83, null);
/* 1746 */       setTermConnState(97, null);
/* 1747 */       break;
/*      */     case 3:
/* 1749 */       setConnectionState(88, null);
/* 1750 */       setTermConnState(98, null);
/* 1751 */       break;
/*      */     case 4:
/* 1753 */       setConnectionState(88, null);
/* 1754 */       setTermConnState(99, null);
/* 1755 */       break;
/*      */     case 5:
/* 1757 */       setConnectionState(82, null);
/* 1758 */       break;
/*      */     case 6:
/* 1760 */       setConnectionState(90, null);
/* 1761 */       setTermConnState(102, null);
/* 1762 */       break;
/*      */     case 0:
/* 1764 */       if (!this.provider.isLucent())
///*      */         break label195;
	break;
/* 1766 */       log.info("NULL localCallState implies BRIDGED for " + this);
/* 1767 */       setConnectionState(88, null);
/* 1768 */       setTermConnState(100, null);
/* 1769 */       break;
/*      */     case -1:
/*      */     default:
/* 1774 */       setConnectionState(91, null);
/* 1775 */       setTermConnState(103, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public CSTAConnectionID getConnID()
/*      */   {
/* 1786 */     synchronized (this)
/*      */     {
/* 1788 */       if (this.connID != null)
/*      */       {
/* 1790 */         return this.connID;
/*      */       }
/*      */     }
/* 1793 */     if (this.termConns != null)
/*      */     {
/* 1795 */       TSConnection tc = null;
/* 1796 */       CSTAConnectionID tcConnID = null;
/* 1797 */       synchronized (this.termConns)
/*      */       {
/* 1799 */         for (int i = 0; i < this.termConns.size(); ++i)
/*      */         {
/* 1801 */           tc = (TSConnection)this.termConns.elementAt(i);
/*      */           try
/*      */           {
/* 1804 */             tcConnID = tc.getConnID();
/*      */           }
/*      */           catch (TsapiPlatformException e)
/*      */           {
/* 1808 */             log.error("Ignoring exception: " + e);
/*      */           }
/* 1810 */           if (tcConnID != null)
/*      */           {
/* 1812 */             return tcConnID;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1817 */     if (this.staleTermConns != null)
/*      */     {
/* 1819 */       TSConnection tc = null;
/* 1820 */       CSTAConnectionID tcConnID = null;
/* 1821 */       synchronized (this.staleTermConns)
/*      */       {
/* 1823 */         for (int i = 0; i < this.staleTermConns.size(); ++i)
/*      */         {
/* 1825 */           tc = (TSConnection)this.staleTermConns.elementAt(i);
/*      */           try
/*      */           {
/* 1828 */             tcConnID = tc.getConnID();
/*      */           }
/*      */           catch (TsapiPlatformException e)
/*      */           {
/* 1832 */             log.error("Ignoring exception: " + e);
/*      */           }
/* 1834 */           if (tcConnID != null)
/*      */           {
/* 1836 */             return tcConnID;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1841 */     throw new TsapiPlatformException(4, 0, "no connection id found!");
/*      */   }
/*      */ 
/*      */   synchronized void setCall(TSCall newCall)
/*      */   {
/* 1854 */     this.call = newCall;
/* 1855 */     if (this.termConns == null)
/*      */       return;
/* 1857 */     for (int i = 0; i < this.termConns.size(); ++i)
/*      */     {
/* 1859 */       TSConnection tc = (TSConnection)this.termConns.elementAt(i);
/* 1860 */       tc.setCall(newCall);
/*      */     }
/*      */   }
/*      */ 
/*      */   synchronized void setConnID(CSTAConnectionID newConnID)
/*      */   {
/* 1872 */     if ((newConnID != null) && (newConnID.equals(this.connID)))
/*      */     {
/* 1874 */       return;
/*      */     }
/*      */ 
/* 1878 */     if ((this.isTermConn) && (newConnID == null))
/*      */     {
/* 1880 */       return;
/*      */     }
/*      */ 
/* 1883 */     this.provider.deleteConnectionFromHash(this.connID);
/*      */ 
/* 1886 */     CSTAConnectionID oldConnID = this.connID;
/* 1887 */     this.connID = newConnID;
/*      */ 
/* 1890 */     TSConnection saveConn = this.provider.addConnectionToHash(this);
/*      */ 
/* 1894 */     if (saveConn == null)
/*      */       return;
/* 1896 */     if (oldConnID != null) {
/* 1897 */       saveConn.connID = oldConnID;
/* 1898 */       this.provider.addConnectionToHash(saveConn);
/*      */     }
/*      */     else
/*      */     {
/* 1911 */       log.info("Replaced an older connection with a Conn that has null Conn ID. Not restoring the older connection.");
/*      */ 
/* 1913 */       log.trace("Dumping call (" + this.call + "):");
/* 1914 */       this.call.dump("   ");
/* 1915 */       log.trace("Dumping conn (" + this + "):");
/* 1916 */       dump("   ");
/* 1917 */       log.trace("Dumping provider (" + this.provider + "):");
/* 1918 */       this.provider.dump("   ");
/*      */     }
/*      */   }
/*      */ 
/*      */   void updateConnIDCallID(int newCallID)
/*      */   {
/* 1931 */     if ((this.connID != null) && (newCallID == this.connID.getCallID()))
/*      */     {
/* 1933 */       return;
/*      */     }
/*      */ 
/* 1937 */     if ((this.isTermConn) && (newCallID == 0))
/*      */     {
/* 1939 */       return;
/*      */     }
/*      */ 
/* 1942 */     CSTAConnectionID newID = new CSTAConnectionID(newCallID, this.connID.getDeviceID(), (short)this.connID.getDevIDType());
/*      */ 
/* 1949 */     setConnID(newID);
/*      */   }
/*      */ 
/*      */   public synchronized void setTrunk(TSTrunk _trunk)
/*      */   {
/* 1961 */     if (_trunk == null)
/*      */       return;
/* 1963 */     this.trunk = _trunk;
/*      */   }
/*      */ 
/*      */   boolean isTerminalConnection()
/*      */   {
/* 1970 */     return this.isTermConn;
/*      */   }
/*      */ 
/*      */   void setTerminalConnection()
/*      */   {
/* 1977 */     if (!this.provider.isLucent())
/* 1978 */       this.isTermConn = true;
/*      */   }
/*      */ 
/*      */   void addACDConns(TSConnection acdConn)
/*      */   {
/* 1986 */     synchronized (this.acdConns)
/*      */     {
/* 1988 */       if (!this.acdConns.contains(acdConn))
/* 1989 */         this.acdConns.addElement(acdConn);
/*      */     }
/*      */   }
/*      */ 
/*      */   void setACDManagerConn(TSConnection _acdManagerConn)
/*      */   {
/* 1999 */     if (_acdManagerConn == null)
/*      */       return;
/* 2001 */     this.acdManagerConn = _acdManagerConn;
/*      */   }
/*      */ 
/*      */   void delete()
/*      */   {
/* 2012 */     log.info("Connection object= " + this + " being deleted" + " for " + this.provider);
/*      */ 
/* 2015 */     if (this.connID != null)
/*      */     {
/* 2017 */       this.provider.deleteConnectionFromHash(this.connID);
/* 2018 */       this.provider.addConnectionToSaveHash(this);
/*      */     }
/* 2020 */     if (this.staleTermConns == null)
/*      */       return;
/* 2022 */     synchronized (this.staleTermConns)
/*      */     {
/* 2024 */       for (int i = 0; i < this.staleTermConns.size(); ++i)
/*      */       {
/* 2026 */         ((TSConnection)this.staleTermConns.elementAt(i)).delete();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 2036 */     return "TSConnection[" + getMyCustomString() + "]@" + Integer.toHexString(super.hashCode());
/*      */   }
/*      */ 
/*      */   private String getMyCustomString()
/*      */   {
/* 2041 */     StringBuffer accumulator = new StringBuffer();
/*      */ 
/* 2046 */     if (this.isTermConn)
/*      */     {
/* 2050 */       accumulator.append("termConn:");
/* 2051 */       addMyCustomStringConnectionID(accumulator);
/*      */     }
/*      */     else
/*      */     {
/* 2057 */       accumulator.append("conn:");
/* 2058 */       addMyCustomStringConnectionID(accumulator);
/*      */     }
/*      */ 
/* 2061 */     return accumulator.toString();
/*      */   }
/*      */ 
/*      */   private void addMyCustomStringConnectionID(StringBuffer text)
/*      */   {
/* 2071 */     text.append("(");
/* 2072 */     addMyCustomStringCallID(text);
/* 2073 */     text.append(",");
/* 2074 */     addMyCustomStringDeviceID(text);
/* 2075 */     text.append(")");
/*      */   }
/*      */ 
/*      */   private void addMyCustomStringDeviceID(StringBuffer text)
/*      */   {
/* 2087 */     if (this.connID == null)
/*      */     {
/* 2089 */       text.append((this.device == null) ? "-" : this.device.getName());
/*      */     }
/*      */     else
/*      */     {
/* 2093 */       text.append((this.connID.getDeviceID() == null) ? "-" : this.connID.getDeviceID());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addMyCustomStringCallID(StringBuffer text)
/*      */   {
/* 2106 */     if (this.connID == null)
/*      */     {
/* 2108 */       text.append((this.call == null) ? "-" : Integer.toString(this.call.getCallID()));
/*      */     }
/*      */     else
/*      */     {
/* 2112 */       text.append((this.connID.getCallID() == 0) ? "-" : Integer.toString(this.connID.getCallID()));
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isDoNotExpectConnectionClearedEvent() {
/* 2117 */     return this.doNotExpectConnectionClearedEvent;
/*      */   }
/*      */ 
/*      */   public void setDoNotExpectConnectionClearedEvent(boolean connBelongToDifferentDeviceIDType)
/*      */   {
/* 2122 */     this.doNotExpectConnectionClearedEvent = connBelongToDifferentDeviceIDType;
/* 2123 */     log.info("Conn " + this + ", setting flag 'connBelongToDifferentDeviceIDType'");
/*      */   }
/*      */ 
/*      */   public TSCall getCall()
/*      */   {
/* 2174 */     return this.call;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSConnection
 * JD-Core Version:    0.5.4
 */