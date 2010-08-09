 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.ITsapiException;
 import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
 import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
 import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
 import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
 import com.avaya.jtapi.tsapi.TsapiPlatformException;
 import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
 import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
 import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
 import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
 import com.avaya.jtapi.tsapi.csta1.LucentSelectiveListeningHold;
 import com.avaya.jtapi.tsapi.csta1.LucentSelectiveListeningRetrieve;
 import com.avaya.jtapi.tsapi.csta1.LucentSendDTMFTone;
 import com.avaya.jtapi.tsapi.csta1.LucentV5SendDTMFTone;
 import com.avaya.jtapi.tsapi.impl.TsapiConnCapabilities;
 import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
 import com.avaya.jtapi.tsapi.impl.TsapiTermConnCapabilities;
 import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
 import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
 import java.util.Vector;
 import org.apache.log4j.Logger;
 
 public final class TSConnection
 {
   private static Logger log = Logger.getLogger(TSConnection.class);
   TSProviderImpl provider;
   int connState;
   int termConnState;
   boolean isTermConn = false;
   TSConnection connection;
   Vector<TSConnection> termConns;
   Vector<TSConnection> staleTermConns;
   boolean haveNetworkReached = false;
   CSTAConnectionID connID;
   TSCall call;
   TSDevice device;
   Object replyTermConnPriv = null;
   Object replyConnPriv = null;
 
   boolean constructed = false;
 
   TSConnection acdManagerConn = null;
   Vector<TSConnection> acdConns = null;
 
   TSTrunk trunk = null;
 
   private TSCallObjectAge my_age = new TSCallObjectAge();
 
   private boolean doNotExpectConnectionClearedEvent = false;
 
   void dump(String indent)
   {
     log.trace(indent + "***** CONNECTION DUMP *****");
     log.trace(indent + "TSConnection: " + this);
     log.trace(indent + "TSConnection ID: " + this.connID);
     log.trace(indent + "TSConnection is terminal connection? " + this.isTermConn);
     log.trace(indent + "TSConnection age: " + this.my_age);
     log.trace(indent + "TSConnection conn state: " + this.connState);
     log.trace(indent + "TSConnection term conn state: " + this.termConnState);
     if (this.termConns != null)
     {
       log.trace(indent + "TSConnection terminal connections: ");
       synchronized (this.termConns)
       {
         for (int i = 0; i < this.termConns.size(); ++i)
         {
           TSConnection conn = (TSConnection)this.termConns.elementAt(i);
           conn.dump(indent + " ");
         }
       }
     }
     if (this.staleTermConns != null)
     {
       log.trace(indent + "TSConnection stale terminal connections: ");
       synchronized (this.staleTermConns)
       {
         for (int i = 0; i < this.staleTermConns.size(); ++i)
         {
           TSConnection conn = (TSConnection)this.staleTermConns.elementAt(i);
           conn.dump(indent + " ");
         }
       }
     }
     if (this.connection != null)
     {
       log.trace(indent + "TSConnection connection: " + this.connection);
     }
     if (this.trunk != null)
     {
       log.trace(indent + "TSTrunk trunk: " + this.trunk);
     }
     log.trace(indent + "***** CONNECTION DUMP END *****");
   }
 
   public int getConnectionState()
   {
     this.call.updateObject();
 
     return getTSConnState();
   }
 
   int getTSConnState()
   {
     int connectionState;
     if ((this.isTermConn) && (this.connection != null))
     {
       connectionState = this.connection.getCallControlConnState();
     }
     else {
       connectionState = this.connState;
     }
     switch (connectionState)
     {
     case 80:
       return 48;
     case 81:
     case 82:
       return 49;
     case 83:
       return 50;
     case 84:
     case 85:
     case 86:
     case 87:
     case 88:
       return 51;
     case 89:
       return 52;
     case 90:
       return 53;
     case 91:
     }
     return 54;
   }
 
   public int getTerminalConnectionState()
   {
     this.call.updateObject();
 
     return getTSTermConnState();
   }
 
   int getTSTermConnState()
   {
     switch (this.termConnState)
     {
     case 96:
       return 64;
     case 97:
       return 65;
     case 98:
     case 99:
       return 67;
     case 100:
     case 101:
       return 66;
     case 102:
       return 68;
     case 103:
     }
     return 69;
   }
 
   public TSProviderImpl getTSProviderImpl()
   {
     return this.provider;
   }
 
   public Vector<TSConnection> getTSTermConns()
   {
     if (getConnectionState() == 52)
     {
       return null;
     }
 
     return getTermConns();
   }
 
   Vector<TSConnection> getTermConns()
   {
     if ((this.provider.isLucent()) && (this.termConns != null))
     {
       return this.termConns;
     }
 
     Vector cv = new Vector();
 
     if (this.isTermConn)
     {
       cv.addElement(this);
     }
 
     return cv;
   }
 
   public TSConnection getTSConnection()
   {
     this.call.updateObject();
     return getTSConn();
   }
 
   TSConnection getTSConn()
   {
     if ((this.provider.isLucent()) && (this.isTermConn))
     {
       return this.connection;
     }
 
     return this;
   }
 
   public Object getTermConnPrivateData()
   {
     if (this.replyTermConnPriv instanceof CSTAPrivate)
       return this.replyTermConnPriv;
     return null;
   }
 
   public Object getConnPrivateData()
   {
     if (this.replyConnPriv instanceof CSTAPrivate)
       return this.replyConnPriv;
     return null;
   }
 
   public Object sendPrivateData(CSTAPrivate data)
   {
     try
     {
       return this.provider.sendPrivateData(data);
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "sendPrivateData failure");
       }
       throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
     }
   }
 
   public TSCall getTSCall()
   {
     return this.call;
   }
 
   public TSDevice getTSDevice()
   {
     return this.device;
   }
 
   public TSTrunk getTSTrunk()
   {
     return this.trunk;
   }
 
   public void disconnect(CSTAPrivate reqConnPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getClearConnection() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
     if (this.call.updateObject())
     {
       int state = getTSConnState();
       if ((state != 49) && (state != 50) && (state != 51) && (state != 53) && (state != 54))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, true), 2, state, "connection not in acceptable state");
       }
 
     }
 
     DisconnectedConfHandler handler = new DisconnectedConfHandler(this, 10);
     try
     {
       if ((this.provider.isLucent()) && (this.termConns != null))
       {
         Vector tcArray = new Vector(this.termConns);
 
         handler.handleIt = false;
         for (int i = 0; i < tcArray.size(); ++i)
         {
           TSConnection tc = (TSConnection)tcArray.elementAt(i);
           if (i == tcArray.size() - 1)
           {
             handler.handleIt = true;
           }
           this.provider.tsapi.clearConnection(tc.connID, reqConnPriv, handler);
         }
       }
       else
       {
         this.provider.tsapi.clearConnection(this.connID, reqConnPriv, handler);
       }
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       if (e.getErrorType() == 2)
       {
         switch (e.getErrorCode())
         {
         case 24:
         case 27:
           log.info("Conn " + this + ": clearConnection " + "Universal Failure with error " + e.getErrorCode() + " requires snapshot of " + this.call + " for " + this.provider);
 
           this.call.updateSuspiciousObject();
         }
 
       }
 
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "clearConnection failure");
       }
       throw new TsapiPlatformException(4, 0, "clearConnection failure");
     }
   }
 
   public void answer(CSTAPrivate reqTermConnPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getAnswerCall() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
     if (this.call.updateObject())
     {
       int state = getTSTermConnState();
       if ((state != 65) && (state != 69))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 5, state, "terminal connection not ringing");
       }
 
     }
 
     ConfHandler handler = new TalkingConfHandler(this, 4);
     try
     {
       this.provider.tsapi.answerCall(this.connID, reqTermConnPriv, handler);
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       if (e.getErrorType() == 2)
       {
         switch (e.getErrorCode())
         {
         case 13:
         case 28:
           log.info("Conn " + this + " answer UniversalFailure " + e.getErrorCode() + " requires snapshot of " + this.call + " for " + this.provider);
           this.call.updateSuspiciousObject();
         }
 
       }
 
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "answerCall failure");
       }
       throw new TsapiPlatformException(4, 0, "answerCall failure");
     }
   }
 
   public TsapiConnCapabilities getTsapiConnCapabilities()
   {
     return this.provider.getTsapiConnCapabilities();
   }
 
   public TsapiTermConnCapabilities getTsapiTermConnCapabilities()
   {
     return this.provider.getTsapiTermConnCapabilities();
   }
 
   public int getCallControlConnectionState()
   {
     this.call.updateObject();
 
     return getCallControlConnState();
   }
 
   int getCallControlConnState()
   {
     if ((this.isTermConn) && (this.connection != null))
     {
       return this.connection.getCallControlConnState();
     }
 
     return this.connState;
   }
 
   public int getCallControlTerminalConnectionState()
   {
     this.call.updateObject();
     return getCallControlTermConnState();
   }
 
   int getCallControlTermConnState()
   {
     return this.termConnState;
   }
 
   public TSConnection redirect(String destinationAddress, CSTAPrivate reqConnPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getDeflectCall() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
     if (this.call.updateObject())
     {
       int state = getCallControlConnState();
       if ((state != 81) && (state != 83) && (state != 91))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, true), 2, state, "connection not offering or alerting");
       }
 
     }
 
     ConfHandler handler = new DisconnectedConfHandler(this, 16);
     try
     {
       this.provider.tsapi.deflectCall(getConnID(), destinationAddress, reqConnPriv, handler);
     }
     catch (TsapiInvalidStateException e)
     {
       throw e;
     }
     catch (TsapiInvalidPartyException e)
     {
       throw e;
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "deflectCall failure");
       }
       throw new TsapiPlatformException(4, 0, "deflectCall failure");
     }
 
     return null;
   }
 
   public void hold(CSTAPrivate reqTermConnPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getHoldCall() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
     if (this.call.updateObject())
     {
       int state = getCallControlTermConnState();
       if ((state != 98) && (state != 103))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 5, state, "terminal connection not talking");
       }
 
     }
 
     ConfHandler handler = new HoldConfHandler(this);
     try
     {
       this.provider.tsapi.holdCall(this.connID, false, reqTermConnPriv, handler);
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       if (e.getErrorType() == 2)
       {
         switch (e.getErrorCode())
         {
         case 13:
         case 24:
           log.info("Conn " + this + " hold UniversalFailure " + e.getErrorCode() + " requires snapshot of " + this.call + " for " + this.provider);
           this.call.updateSuspiciousObject();
         }
 
       }
 
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "holdCall failure");
       }
       throw new TsapiPlatformException(4, 0, "holdCall failure");
     }
   }
 
   public void unhold(CSTAPrivate reqTermConnPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getRetrieveCall() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
     if (this.call.updateObject())
     {
       int state = getCallControlTermConnState();
       if ((state != 99) && (state != 103))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 5, state, "terminal connection not held");
       }
 
     }
 
     ConfHandler handler = new TalkingConfHandler(this, 42);
     try
     {
       this.provider.tsapi.retrieveCall(this.connID, reqTermConnPriv, handler);
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       if (e.getErrorType() == 2)
       {
         switch (e.getErrorCode())
         {
         case 13:
         case 24:
           log.info("Conn " + this + " unhold UniversalFailure " + e.getErrorCode() + " requires snapshot of " + this.call + " for " + this.provider);
           this.call.updateSuspiciousObject();
         }
 
       }
 
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "retrieveCall failure");
       }
       throw new TsapiPlatformException(4, 0, "retrieveCall failure");
     }
   }
 
   public void listenHold(TSConnection partyToHold)
     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     if (!this.provider.isLucentV5())
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
     if (this.termConns != null)
     {
       throw new TsapiInvalidArgumentException(3, 0, "subject Connection contains TerminalConnections");
     }
     if (this.connID == null)
     {
       throw new TsapiInvalidArgumentException(3, 0, "subject connID is null");
     }
 
     boolean allParties = true;
     CSTAConnectionID selectedParty = null;
 
     if (partyToHold != null)
     {
       selectedParty = partyToHold.connID;
       if (selectedParty == null)
       {
         throw new TsapiInvalidArgumentException(3, 0, "partyToHold connID is null");
       }
       allParties = false;
     }
 
     try
     {
       LucentSelectiveListeningHold slh = new LucentSelectiveListeningHold(this.connID, allParties, selectedParty);
 
       this.provider.sendPrivateData(slh.makeTsapiPrivate());
     }
     catch (TsapiInvalidStateException e)
     {
       throw e;
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "listenHold failure");
       }
       throw new TsapiPlatformException(4, 0, "listenHold failure");
     }
   }
 
   public void listenUnhold(TSConnection partyToUnhold)
     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     if (!this.provider.isLucentV5())
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
     if (this.connID == null)
     {
       throw new TsapiInvalidArgumentException(3, 0, "subject connID is null");
     }
 
     boolean allParties = true;
     CSTAConnectionID selectedParty = null;
 
     if (partyToUnhold != null)
     {
       selectedParty = partyToUnhold.connID;
       if (selectedParty == null)
       {
         throw new TsapiInvalidArgumentException(3, 0, "partyToUnhold connID is null");
       }
       allParties = false;
     }
 
     try
     {
       LucentSelectiveListeningRetrieve slr = new LucentSelectiveListeningRetrieve(this.connID, allParties, selectedParty);
 
       this.provider.sendPrivateData(slr.makeTsapiPrivate());
     }
     catch (TsapiInvalidStateException e)
     {
       throw e;
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "listenUnhold failure");
       }
       throw new TsapiPlatformException(4, 0, "listenUnhold failure");
     }
   }
 
   public void join(CSTAPrivate reqTermConnPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (!this.provider.isLucent()) {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
     if (this.call.updateObject())
     {
       int state = getCallControlTermConnState();
       if ((state != 100) && (state != 103))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 5, state, "terminal connection not bridged");
       }
 
     }
 
     ConfHandler handler = new TalkingConfHandler(this, 4);
     try
     {
       this.provider.tsapi.answerCall(this.connID, reqTermConnPriv, handler);
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       if (e.getErrorType() == 2)
       {
         switch (e.getErrorCode())
         {
         case 13:
         case 28:
           log.info("Conn " + this + " join UniversalFailure " + e.getErrorCode() + " requires snapshot of " + this.call + " for " + this.provider);
           this.call.updateSuspiciousObject();
         }
 
       }
 
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "join failure");
       }
       throw new TsapiPlatformException(4, 0, "join failure");
     }
   }
 
   public void leave(CSTAPrivate reqTermConnPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (!this.provider.isLucent()) {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
     if (this.call.updateObject())
     {
       int state = getCallControlTermConnState();
       if ((state != 98) && (state != 103))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 5, state, "terminal connection not talking");
       }
 
     }
 
     ConfHandler handler = new BridgedConfHandler(this);
     try
     {
       this.provider.tsapi.clearConnection(this.connID, reqTermConnPriv, handler);
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       if (e.getErrorType() == 2)
       {
         switch (e.getErrorCode())
         {
         case 24:
         case 27:
           log.info("Conn " + this + ": clearConnection " + "Universal Failure with error " + e.getErrorCode() + " requires snapshot of " + this.call + " for " + this.provider);
 
           this.call.updateSuspiciousObject();
         }
 
       }
 
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "clearConnection failure");
       }
       throw new TsapiPlatformException(4, 0, "clearConnection failure");
     }
   }
 
   public TSConnection getACDManagerConn()
   {
     return this.acdManagerConn;
   }
 
   public Vector<TSConnection> getACDConns()
   {
     return this.acdConns;
   }
 
   public void generateDtmf(String digits)
     throws TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     generateDtmf(digits, 0, 0);
   }
 
   public void generateDtmf(String digits, int toneDuration, int pauseDuration)
     throws TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (!this.provider.isLucent())
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     LucentSendDTMFTone dtmf;
     if (this.provider.isLucentV5())
       dtmf = new LucentV5SendDTMFTone(this.connID, null, digits, toneDuration, pauseDuration);
     else {
       dtmf = new LucentSendDTMFTone(this.connID, null, digits, toneDuration, pauseDuration);
     }
     try
     {
       this.provider.sendPrivateData(dtmf.makeTsapiPrivate());
     }
     catch (TsapiInvalidStateException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "sendPrivateData failure");
       }
       throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
     }
   }
 
   TSConnection(TSProviderImpl _provider, CSTAConnectionID _connID, TSDevice _device, boolean _wantTermConn)
   {
     this.constructed = false;
     this.provider = _provider;
     this.connID = _connID;
     this.device = _device;
     this.connState = 80;
     this.termConnState = 96;
     this.acdConns = new Vector();
 
     this.isTermConn = _wantTermConn;
 
     if (this.isTermConn)
     {
       if (this.device != null)
       {
         this.isTermConn = this.device.isTerminal();
       }
       else
       {
         this.isTermConn = false;
       }
     }
 
     if (this.connID != null)
     {
       this.call = this.provider.createCall(this.connID.getCallID());
       if (this.call.getTSState() == 34)
       {
         this.provider.dumpCall(this.connID.getCallID());
         this.call = this.provider.createCall(this.connID.getCallID());
       }
     }
 
     StringBuffer connForProviderString = new StringBuffer();
 
     connForProviderString.append(this).append(" for ").append(this.provider);
 
     if (this.provider.isLucent())
     {
       if (this.isTermConn)
       {
         log.info("Constructing Lucent termConn " + connForProviderString.toString());
       }
       else
       {
         log.info("Constructing Lucent conn " + connForProviderString.toString());
       }
 
     }
     else
       log.info("Constructing conn " + connForProviderString.toString());
   }
 
   synchronized void finishConstruction(TSDevice _connectionAddress, Vector<TSEvent> eventList)
   {
     boolean found = true;
     if ((this.isTermConn) && (this.provider.isLucent()) && (_connectionAddress != null))
     {
       this.device.addConnection(this);
       Vector connVector = new Vector(this.call.getConnections());
       TSConnection addressConnection = null;
       found = false;
       for (int i = 0; i < connVector.size(); ++i)
       {
         addressConnection = (TSConnection)connVector.elementAt(i);
         if (addressConnection.getTSDevice() != _connectionAddress)
           continue;
         this.connection = addressConnection;
         this.connection.addTerminalConnection(this, eventList);
         found = true;
         break;
       }
 
     }
     else if (this.call != null)
     {
       this.device.addConnection(this);
       this.call.addConnection(this, eventList);
     }
 
     this.constructed = true;
     super.notifyAll();
 
     if (!found)
     {
       this.connection = this.provider.createConnection(null, _connectionAddress, null);
       this.connection.addTerminalConnection(this, eventList);
       if (eventList != null)
         eventList.addElement(new TSEvent(6, this.connection));
     }
     if (eventList == null) {
       return;
     }
 
     if ((!this.isTermConn) || (!this.provider.isLucent()))
       eventList.addElement(new TSEvent(6, this));
     if (this.isTermConn)
       eventList.addElement(new TSEvent(13, this));
   }
 
   synchronized void waitForConstruction()
   {
     if (this.constructed)
       return;
     try
     {
       super.wait(TSProviderImpl.DEFAULT_TIMEOUT);
     } catch (InterruptedException e) {
     }
     if (this.constructed)
       return;
     throw new TsapiPlatformException(4, 0, "could not finish connection construction");
   }
 
   void setConnectionState(int _connState, Vector<TSEvent> eventList)
   {
     if ((this.isTermConn) && (this.provider.isLucent()))
     {
       if (this.connection != null)
       {
         this.connection.setConnectionState(_connState, eventList);
       }
       return;
     }
 
     int oldCoreState = getTSConnState();
 
     synchronized (this)
     {
       if ((this.haveNetworkReached) && (_connState == 83))
       {
         _connState = 87;
       }
       else if ((_connState == 82) && (this.device.getDeviceType() == 1))
       {
         _connState = 83;
       }
 
       if ((this.connState == _connState) || (this.connState == 89))
       {
         return;
       }
       this.connState = _connState;
     }
 
     switch (this.connState)
     {
     case 83:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 50)
       {
         eventList.addElement(new TSEvent(9, this));
       }
       eventList.addElement(new TSEvent(26, this)); break;
     case 88:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 51)
       {
         eventList.addElement(new TSEvent(7, this));
       }
       eventList.addElement(new TSEvent(21, this)); break;
     case 86:
       if (eventList != null)
       {
         if (oldCoreState != 51)
         {
           eventList.addElement(new TSEvent(7, this));
         }
         eventList.addElement(new TSEvent(22, this));
       }
       synchronized (this)
       {
         this.haveNetworkReached = true;
       }
       break;
     case 87:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 51)
       {
         eventList.addElement(new TSEvent(7, this));
       }
       eventList.addElement(new TSEvent(23, this)); break;
     case 84:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 51)
       {
         eventList.addElement(new TSEvent(7, this));
       }
       eventList.addElement(new TSEvent(24, this));
 
       if (this.provider.getCapabilities().getOriginatedEvent() != 0)
         return;
       setConnectionState(88, eventList); break;
     case 89:
       synchronized (this)
       {
         if (this.trunk != null)
         {
           this.call.removeTrunk(this.trunk, eventList);
           this.trunk = null;
         }
 
       }
 
       if (this.termConns != null)
       {
         Vector conn = new Vector(this.termConns);
         int i;
         for ( i = 0; i < conn.size(); ++i)
         {
           ((TSConnection)conn.elementAt(i)).setTermConnState(102, eventList);
         }
 
       }
       else
       {
         setTermConnState(102, eventList);
       }
 
       if (this.acdManagerConn != null)
       {
         TSConnection acdMgrConn = this.acdManagerConn;
         Vector acdConns = acdMgrConn.getACDConns();
         int j;
         for ( j = 0; j < acdConns.size(); ++j)
         {
           ((TSConnection)acdConns.elementAt(j)).setACDManagerConn(null);
           ((TSConnection)acdConns.elementAt(j)).setConnectionState(89, eventList);
         }
         acdMgrConn.setConnectionState(89, eventList);
       }
 
       if (eventList != null)
       {
         if (oldCoreState != 52)
         {
           eventList.addElement(new TSEvent(10, this));
         }
         eventList.addElement(new TSEvent(27, this));
       }
       this.device.removeConnection(this);
       this.call.removeConnection(this, eventList);
       break;
     case 90:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 53)
       {
         eventList.addElement(new TSEvent(11, this));
       }
       eventList.addElement(new TSEvent(28, this)); break;
     case 82:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 49)
       {
         eventList.addElement(new TSEvent(8, this));
         eventList.addElement(new TSEvent(56, this));
       }
       eventList.addElement(new TSEvent(25, this)); break;
     case 91:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 54)
       {
         eventList.addElement(new TSEvent(12, this));
       }
       eventList.addElement(new TSEvent(29, this)); break;
     case 81:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 49)
       {
         eventList.addElement(new TSEvent(8, this));
         eventList.addElement(new TSEvent(56, this));
       }
       eventList.addElement(new TSEvent(19, this)); break;
     case 85:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 51)
       {
         eventList.addElement(new TSEvent(7, this));
       }
       eventList.addElement(new TSEvent(20, this));
     }
   }
 
   void setTermConnState(int _termConnState, Vector<TSEvent> eventList)
   {
     if (!this.isTermConn)
     {
       return;
     }
 
     int oldCoreState = getTSTermConnState();
 
     synchronized (this)
     {
       if ((this.haveNetworkReached) && (_termConnState == 97))
       {
         _termConnState = 98;
       }
 
       if ((this.termConnState == _termConnState) || (this.termConnState == 102))
       {
         return;
       }
 
       this.termConnState = _termConnState;
     }
 
     switch (this.termConnState)
     {
     case 98:
       if (eventList != null)
       {
         if (oldCoreState != 67)
         {
           eventList.addElement(new TSEvent(14, this));
         }
         eventList.addElement(new TSEvent(30, this));
       }
 
       if ((this.connection == null) || 
         (this.connection.termConns == null))
         return;
       Vector conns = new Vector(this.connection.termConns);
       for (int i = 0; i < conns.size(); ++i)
       {
         TSConnection conn = (TSConnection)conns.elementAt(i);
         if (conn == this)
           continue;
         if (conn.termConnState != 97)
           continue;
         conn.setTermConnState(100, eventList);
       }
 
       break;
     case 99:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 67)
       {
         eventList.addElement(new TSEvent(14, this));
       }
       eventList.addElement(new TSEvent(31, this)); break;
     case 102:
       if (eventList != null)
       {
         if (oldCoreState != 68)
         {
           eventList.addElement(new TSEvent(17, this));
         }
         eventList.addElement(new TSEvent(34, this));
       }
       this.device.removeConnection(this);
       if (this.connection != null)
       {
         this.connection.removeTerminalConnection(this, eventList); return;
       }
 
       this.call.removeConnection(this, eventList);
 
       break;
     case 97:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 65)
       {
         eventList.addElement(new TSEvent(15, this));
       }
       eventList.addElement(new TSEvent(35, this)); break;
     case 100:
       boolean okToBridge = false;
       int i = 0;
       if ((this.connection != null) && 
         (this.connection.termConns != null))
       {
         synchronized (this.connection.termConns)
         {
           if (this.connection.termConns.size() == 1)
           {
             i = 1;
           }
           else
           {
             for ( i = 0; i < this.connection.termConns.size(); ++i)
             {
               TSConnection conn = (TSConnection)this.connection.termConns.elementAt(i);
               if (conn == this)
                 continue;
               if ((conn.termConnState != 98) && (conn.termConnState != 103)) {
                 continue;
               }
               okToBridge = true;
               break;
             }
           }
         }
 
       }
 
       if (okToBridge)
       {
         if (eventList == null) {
           return;
         }
         if (oldCoreState != 66)
         {
           eventList.addElement(new TSEvent(16, this));
         }
         eventList.addElement(new TSEvent(32, this)); return;
       }
 
       if (i != 0)
       {
         setTermConnState(97, eventList); return;
       }
       if (this.connection == null)
         return;
       this.connection.setConnectionState(89, eventList); break;
     case 101:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 66)
       {
         eventList.addElement(new TSEvent(16, this));
       }
       eventList.addElement(new TSEvent(33, this)); break;
     case 103:
       if (eventList == null) {
         return;
       }
       if (oldCoreState != 69)
       {
         eventList.addElement(new TSEvent(18, this));
       }
       eventList.addElement(new TSEvent(36, this));
     }
   }
 
   void addTerminalConnection(TSConnection termConn, Vector<TSEvent> eventList)
   {
     if (this.termConns == null)
     {
       this.termConns = new Vector();
       this.termConns.addElement(termConn);
       this.staleTermConns = new Vector();
       if (this.call == null)
       {
         this.call = termConn.call;
         this.device.addConnection(this);
         this.call.addConnection(this, eventList);
       }
       this.connID = null;
     }
     else
     {
       synchronized (this.termConns)
       {
         if (!this.termConns.contains(termConn))
         {
           this.termConns.addElement(termConn);
         }
       }
     }
 
     termConn.connection = this;
   }
 
   void removeTerminalConnection(TSConnection termConn, Vector<TSEvent> eventList)
   {
     if (this.termConns == null)
       return;
     if (!this.termConns.removeElement(termConn))
     {
       return;
     }
     synchronized (this.staleTermConns)
     {
       if (!this.staleTermConns.contains(termConn))
       {
         this.staleTermConns.addElement(termConn);
       }
     }
 
     if (this.termConns.size() != 0)
       return;
     setConnectionState(89, eventList);
   }
 
   void getSnapshot(Vector<TSEvent> eventList)
   {
     getSnapshot(eventList, true);
   }
 
   void getSnapshot(Vector<TSEvent> eventList, boolean includeCreated) {
     if ((!this.isTermConn) || (!this.provider.isLucent()))
     {
       if (includeCreated)
       {
         eventList.addElement(new TSEvent(6, this));
       }
       switch (this.connState)
       {
       case 83:
         eventList.addElement(new TSEvent(9, this));
         eventList.addElement(new TSEvent(26, this));
         break;
       case 88:
         eventList.addElement(new TSEvent(7, this));
         eventList.addElement(new TSEvent(21, this));
         break;
       case 86:
         eventList.addElement(new TSEvent(7, this));
         eventList.addElement(new TSEvent(22, this));
         break;
       case 87:
         eventList.addElement(new TSEvent(7, this));
         eventList.addElement(new TSEvent(23, this));
         break;
       case 84:
         eventList.addElement(new TSEvent(7, this));
         eventList.addElement(new TSEvent(24, this));
         break;
       case 89:
         eventList.addElement(new TSEvent(10, this));
         eventList.addElement(new TSEvent(27, this));
         break;
       case 90:
         eventList.addElement(new TSEvent(11, this));
         eventList.addElement(new TSEvent(28, this));
         break;
       case 82:
         eventList.addElement(new TSEvent(8, this));
         eventList.addElement(new TSEvent(56, this));
         eventList.addElement(new TSEvent(25, this));
         break;
       case 91:
         eventList.addElement(new TSEvent(12, this));
         eventList.addElement(new TSEvent(29, this));
         break;
       case 81:
         eventList.addElement(new TSEvent(8, this));
         eventList.addElement(new TSEvent(56, this));
         eventList.addElement(new TSEvent(19, this));
         break;
       case 85:
         eventList.addElement(new TSEvent(7, this));
         eventList.addElement(new TSEvent(20, this));
       }
 
       if ((this.provider.isLucent()) && (this.termConns != null))
       {
         synchronized (this.termConns)
         {
           for (int i = 0; i < this.termConns.size(); ++i)
           {
             ((TSConnection)this.termConns.elementAt(i)).getSnapshot(eventList, includeCreated);
           }
         }
       }
     }
     if (!this.isTermConn)
       return;
     if (includeCreated)
     {
       eventList.addElement(new TSEvent(13, this));
     }
     switch (this.termConnState)
     {
     case 98:
       eventList.addElement(new TSEvent(14, this));
       eventList.addElement(new TSEvent(30, this));
       break;
     case 99:
       eventList.addElement(new TSEvent(14, this));
       eventList.addElement(new TSEvent(31, this));
       break;
     case 102:
       eventList.addElement(new TSEvent(17, this));
       eventList.addElement(new TSEvent(34, this));
       break;
     case 97:
       eventList.addElement(new TSEvent(15, this));
       eventList.addElement(new TSEvent(35, this));
       break;
     case 100:
       eventList.addElement(new TSEvent(16, this));
       eventList.addElement(new TSEvent(32, this));
       break;
     case 101:
       eventList.addElement(new TSEvent(16, this));
       eventList.addElement(new TSEvent(33, this));
       break;
     case 103:
       eventList.addElement(new TSEvent(18, this));
       eventList.addElement(new TSEvent(36, this));
     }
   }
 
   void setStateFromLocalConnState(int localCallState)
   {
     switch (localCallState)
     {
     case 1:
       setConnectionState(84, null);
       setTermConnState(98, null);
       break;
     case 2:
       setConnectionState(83, null);
       setTermConnState(97, null);
       break;
     case 3:
       setConnectionState(88, null);
       setTermConnState(98, null);
       break;
     case 4:
       setConnectionState(88, null);
       setTermConnState(99, null);
       break;
     case 5:
       setConnectionState(82, null);
       break;
     case 6:
       setConnectionState(90, null);
       setTermConnState(102, null);
       break;
     case 0:
       if (!this.provider.isLucent())
//         break label195;
	break;
       log.info("NULL localCallState implies BRIDGED for " + this);
       setConnectionState(88, null);
       setTermConnState(100, null);
       break;
     case -1:
     default:
       setConnectionState(91, null);
       setTermConnState(103, null);
     }
   }
 
   public CSTAConnectionID getConnID()
   {
     synchronized (this)
     {
       if (this.connID != null)
       {
         return this.connID;
       }
     }
     if (this.termConns != null)
     {
       TSConnection tc = null;
       CSTAConnectionID tcConnID = null;
       synchronized (this.termConns)
       {
         for (int i = 0; i < this.termConns.size(); ++i)
         {
           tc = (TSConnection)this.termConns.elementAt(i);
           try
           {
             tcConnID = tc.getConnID();
           }
           catch (TsapiPlatformException e)
           {
             log.error("Ignoring exception: " + e);
           }
           if (tcConnID != null)
           {
             return tcConnID;
           }
         }
       }
     }
     if (this.staleTermConns != null)
     {
       TSConnection tc = null;
       CSTAConnectionID tcConnID = null;
       synchronized (this.staleTermConns)
       {
         for (int i = 0; i < this.staleTermConns.size(); ++i)
         {
           tc = (TSConnection)this.staleTermConns.elementAt(i);
           try
           {
             tcConnID = tc.getConnID();
           }
           catch (TsapiPlatformException e)
           {
             log.error("Ignoring exception: " + e);
           }
           if (tcConnID != null)
           {
             return tcConnID;
           }
         }
       }
     }
     throw new TsapiPlatformException(4, 0, "no connection id found!");
   }
 
   synchronized void setCall(TSCall newCall)
   {
     this.call = newCall;
     if (this.termConns == null)
       return;
     for (int i = 0; i < this.termConns.size(); ++i)
     {
       TSConnection tc = (TSConnection)this.termConns.elementAt(i);
       tc.setCall(newCall);
     }
   }
 
   synchronized void setConnID(CSTAConnectionID newConnID)
   {
     if ((newConnID != null) && (newConnID.equals(this.connID)))
     {
       return;
     }
 
     if ((this.isTermConn) && (newConnID == null))
     {
       return;
     }
 
     this.provider.deleteConnectionFromHash(this.connID);
 
     CSTAConnectionID oldConnID = this.connID;
     this.connID = newConnID;
 
     TSConnection saveConn = this.provider.addConnectionToHash(this);
 
     if (saveConn == null)
       return;
     if (oldConnID != null) {
       saveConn.connID = oldConnID;
       this.provider.addConnectionToHash(saveConn);
     }
     else
     {
       log.info("Replaced an older connection with a Conn that has null Conn ID. Not restoring the older connection.");
 
       log.trace("Dumping call (" + this.call + "):");
       this.call.dump("   ");
       log.trace("Dumping conn (" + this + "):");
       dump("   ");
       log.trace("Dumping provider (" + this.provider + "):");
       this.provider.dump("   ");
     }
   }
 
   void updateConnIDCallID(int newCallID)
   {
     if ((this.connID != null) && (newCallID == this.connID.getCallID()))
     {
       return;
     }
 
     if ((this.isTermConn) && (newCallID == 0))
     {
       return;
     }
 
     CSTAConnectionID newID = new CSTAConnectionID(newCallID, this.connID.getDeviceID(), (short)this.connID.getDevIDType());
 
     setConnID(newID);
   }
 
   public synchronized void setTrunk(TSTrunk _trunk)
   {
     if (_trunk == null)
       return;
     this.trunk = _trunk;
   }
 
   boolean isTerminalConnection()
   {
     return this.isTermConn;
   }
 
   void setTerminalConnection()
   {
     if (!this.provider.isLucent())
       this.isTermConn = true;
   }
 
   void addACDConns(TSConnection acdConn)
   {
     synchronized (this.acdConns)
     {
       if (!this.acdConns.contains(acdConn))
         this.acdConns.addElement(acdConn);
     }
   }
 
   void setACDManagerConn(TSConnection _acdManagerConn)
   {
     if (_acdManagerConn == null)
       return;
     this.acdManagerConn = _acdManagerConn;
   }
 
   void delete()
   {
     log.info("Connection object= " + this + " being deleted" + " for " + this.provider);
 
     if (this.connID != null)
     {
       this.provider.deleteConnectionFromHash(this.connID);
       this.provider.addConnectionToSaveHash(this);
     }
     if (this.staleTermConns == null)
       return;
     synchronized (this.staleTermConns)
     {
       for (int i = 0; i < this.staleTermConns.size(); ++i)
       {
         ((TSConnection)this.staleTermConns.elementAt(i)).delete();
       }
     }
   }
 
   public String toString()
   {
     return "TSConnection[" + getMyCustomString() + "]@" + Integer.toHexString(super.hashCode());
   }
 
   private String getMyCustomString()
   {
     StringBuffer accumulator = new StringBuffer();
 
     if (this.isTermConn)
     {
       accumulator.append("termConn:");
       addMyCustomStringConnectionID(accumulator);
     }
     else
     {
       accumulator.append("conn:");
       addMyCustomStringConnectionID(accumulator);
     }
 
     return accumulator.toString();
   }
 
   private void addMyCustomStringConnectionID(StringBuffer text)
   {
     text.append("(");
     addMyCustomStringCallID(text);
     text.append(",");
     addMyCustomStringDeviceID(text);
     text.append(")");
   }
 
   private void addMyCustomStringDeviceID(StringBuffer text)
   {
     if (this.connID == null)
     {
       text.append((this.device == null) ? "-" : this.device.getName());
     }
     else
     {
       text.append((this.connID.getDeviceID() == null) ? "-" : this.connID.getDeviceID());
     }
   }
 
   private void addMyCustomStringCallID(StringBuffer text)
   {
     if (this.connID == null)
     {
       text.append((this.call == null) ? "-" : Integer.toString(this.call.getCallID()));
     }
     else
     {
       text.append((this.connID.getCallID() == 0) ? "-" : Integer.toString(this.connID.getCallID()));
     }
   }
 
   public boolean isDoNotExpectConnectionClearedEvent() {
     return this.doNotExpectConnectionClearedEvent;
   }
 
   public void setDoNotExpectConnectionClearedEvent(boolean connBelongToDifferentDeviceIDType)
   {
     this.doNotExpectConnectionClearedEvent = connBelongToDifferentDeviceIDType;
     log.info("Conn " + this + ", setting flag 'connBelongToDifferentDeviceIDType'");
   }
 
   public TSCall getCall()
   {
     return this.call;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSConnection
 * JD-Core Version:    0.5.4
 */