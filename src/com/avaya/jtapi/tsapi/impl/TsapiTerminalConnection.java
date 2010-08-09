 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.ConnectionID;
 import com.avaya.jtapi.tsapi.ITsapiConnIDPrivate;
 import com.avaya.jtapi.tsapi.ITsapiTerminalConnection;
 import com.avaya.jtapi.tsapi.LucentConnection;
 import com.avaya.jtapi.tsapi.LucentTerminalConnection;
 import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
 import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
 import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
 import com.avaya.jtapi.tsapi.TsapiPlatformException;
 import com.avaya.jtapi.tsapi.TsapiPrivate;
 import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
 import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
 import com.avaya.jtapi.tsapi.UserToUserInfo;
 import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
 import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
 import com.avaya.jtapi.tsapi.csta1.LucentClearConnection;
 import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
 import com.avaya.jtapi.tsapi.csta1.LucentV6ClearConnection;
 import com.avaya.jtapi.tsapi.impl.core.TSConnection;
 import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
 import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 import java.net.URL;
 import javax.telephony.Address;
 import javax.telephony.InvalidArgumentException;
 import javax.telephony.PlatformException;
 import javax.telephony.Terminal;
 import javax.telephony.capabilities.TerminalConnectionCapabilities;
 import javax.telephony.privatedata.PrivateData;
 
 class TsapiTerminalConnection
   implements ITsapiTerminalConnection, PrivateData, ITsapiConnIDPrivate
 {
   TSConnection tsConnection;
   CSTAPrivate privData = null;
 
   public final int getState()
   {
     TsapiTrace.traceEntry("getState[]", this);
     try
     {
       int state = this.tsConnection.getTerminalConnectionState();
       TsapiTrace.traceExit("getState[]", this);
       return state;
     }
     finally
     {
       this.privData = null; }  } 
   // ERROR //
   public final Terminal getTerminal() { return null;}// Byte code:
     //   0: ldc 7
     //   2: aload_0
     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
     //   6: aload_0
     //   7: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:tsConnection	Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
     //   10: invokevirtual 8	com/avaya/jtapi/tsapi/impl/core/TSConnection:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
     //   13: astore_1
     //   14: aload_1
     //   15: ifnull +25 -> 40
     //   18: aload_1
     //   19: iconst_0
     //   20: invokestatic 9	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
     //   23: checkcast 10	javax/telephony/Terminal
     //   26: astore_2
     //   27: ldc 7
     //   29: aload_0
     //   30: invokestatic 5	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   33: aload_2
     //   34: astore_3
     //   35: jsr +25 -> 60
     //   38: aload_3
     //   39: areturn
     //   40: new 11	com/avaya/jtapi/tsapi/TsapiPlatformException
     //   43: dup
     //   44: iconst_4
     //   45: iconst_0
     //   46: ldc 12
     //   48: invokespecial 13	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
     //   51: athrow
     //   52: astore 4
     //   54: jsr +6 -> 60
     //   57: aload 4
     //   59: athrow
     //   60: astore 5
     //   62: aload_0
     //   63: aconst_null
     //   64: putfield 6	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   67: ret 5
     //
     // Exception table:
     //   from	to	target	type
     //   6	38	52	finally
     //   40	57	52	finally } 
   // ERROR //
   public final javax.telephony.Connection getConnection() { return null;}// Byte code:
     //   0: ldc 14
     //   2: aload_0
     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
     //   6: aload_0
     //   7: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:tsConnection	Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
     //   10: invokevirtual 15	com/avaya/jtapi/tsapi/impl/core/TSConnection:getTSConnection	()Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
     //   13: astore_1
     //   14: aload_1
     //   15: ifnull +25 -> 40
     //   18: aload_1
     //   19: iconst_1
     //   20: invokestatic 9	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
     //   23: checkcast 16	javax/telephony/Connection
     //   26: astore_2
     //   27: ldc 14
     //   29: aload_0
     //   30: invokestatic 5	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   33: aload_2
     //   34: astore_3
     //   35: jsr +25 -> 60
     //   38: aload_3
     //   39: areturn
     //   40: new 11	com/avaya/jtapi/tsapi/TsapiPlatformException
     //   43: dup
     //   44: iconst_4
     //   45: iconst_0
     //   46: ldc 17
     //   48: invokespecial 13	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
     //   51: athrow
     //   52: astore 4
     //   54: jsr +6 -> 60
     //   57: aload 4
     //   59: athrow
     //   60: astore 5
     //   62: aload_0
     //   63: aconst_null
     //   64: putfield 6	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   67: ret 5
     //
     // Exception table:
     //   from	to	target	type
     //   6	38	52	finally
     //   40	57	52	finally } 
   public final void answer() throws TsapiPrivilegeViolationException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException, TsapiInvalidStateException { TsapiTrace.traceEntry("answer[]", this);
     try
     {
       this.tsConnection.answer(this.privData);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("answer[]", this); }
 
 
   public final TerminalConnectionCapabilities getCapabilities()
   {
     TsapiTrace.traceEntry("getCapabilities[]", this);
     try
     {
       TerminalConnectionCapabilities caps = this.tsConnection.getTsapiTermConnCapabilities();
       TsapiTrace.traceExit("getCapabilities[]", this);
       return caps;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final TerminalConnectionCapabilities getTerminalConnectionCapabilities(Terminal terminal, Address address)
     throws InvalidArgumentException, PlatformException
   {
     TsapiTrace.traceEntry("getTerminalConnectionCapabilities[Terminal terminal, Address address]", this);
     TerminalConnectionCapabilities caps = getCapabilities();
     TsapiTrace.traceExit("getTerminalConnectionCapabilities[Terminal terminal, Address address]", this);
     return caps;
   }
 
   public final int getCallControlState()
   {
     TsapiTrace.traceEntry("getCallControlState[]", this);
     try
     {
       int state = this.tsConnection.getCallControlTerminalConnectionState();
       TsapiTrace.traceExit("getCallControlState[]", this);
       return state;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final void hold()
     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("hold[]", this);
     try
     {
       this.tsConnection.hold(this.privData);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("hold[]", this);
   }
 
   public final void unhold()
     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("unhold[]", this);
     try
     {
       this.tsConnection.unhold(this.privData);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("unhold[]", this);
   }
 
   public final void listenHold(LucentTerminalConnection partyToHold)
     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("listenHold[LucentTerminalConnection partyToHold]", this);
     try
     {
       TSConnection party = (partyToHold == null) ? null : ((TsapiTerminalConnection)partyToHold).tsConnection;
 
       this.tsConnection.listenHold(party);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("listenHold[LucentTerminalConnection partyToHold]", this);
   }
 
   public final void listenUnhold(LucentTerminalConnection partyToUnhold)
     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("listenUnhold[LucentTerminalConnection partyToUnhold]", this);
     try
     {
       TSConnection party = (partyToUnhold == null) ? null : ((TsapiTerminalConnection)partyToUnhold).tsConnection;
 
       this.tsConnection.listenUnhold(party);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("listenUnhold[LucentTerminalConnection partyToUnhold]", this);
   }
 
   public final void listenHold(LucentConnection partyToHold)
     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("listenHold[LucentTerminalConnection partyToHold]", this);
     try
     {
       TSConnection party = (partyToHold == null) ? null : ((TsapiConnection)partyToHold).tsConnection;
 
       this.tsConnection.listenHold(party);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("listenHold[LucentTerminalConnection partyToHold]", this);
   }
 
   public final void listenUnhold(LucentConnection partyToUnhold)
     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("listenUnhold[LucentTerminalConnection partyToUnhold]", this);
     try
     {
       TSConnection party = (partyToUnhold == null) ? null : ((TsapiConnection)partyToUnhold).tsConnection;
 
       this.tsConnection.listenUnhold(party);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("listenUnhold[LucentTerminalConnection partyToUnhold]", this);
   }
 
   public final void join()
     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("join[]", this);
     try
     {
       this.tsConnection.join(this.privData);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("join[]", this);
   }
 
   public final void leave()
     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("leave[]", this);
     try
     {
       this.tsConnection.leave(this.privData);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("leave[]", this);
   }
 
   private LucentClearConnection createLucentClearConnection(short dropRes, UserToUserInfo uui)
   {
     TsapiTrace.traceEntry("createLucentClearConnection[short dropRes, UserToUserInfo uui]", this);
     TSProviderImpl provider = this.tsConnection.getTSProviderImpl();
     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(uui);
 
     LucentClearConnection conn = null;
     if (provider != null)
     {
       if (provider.isLucentV6())
       {
         conn = new LucentV6ClearConnection(dropRes, asn_uui);
       }
       else
       {
         conn = new LucentClearConnection(dropRes, asn_uui);
       }
     }
     TsapiTrace.traceExit("createLucentClearConnection[short dropRes, UserToUserInfo uui]", this);
     return conn;
   }
 
   public final void leave(short dropResource, UserToUserInfo userInfo)
     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("leave[short dropResource, UserToUserInfo userInfo]", this);
     LucentClearConnection lcc = createLucentClearConnection(dropResource, userInfo);
     this.privData = lcc.makeTsapiPrivate();
     leave();
     TsapiTrace.traceExit("leave[short dropResource, UserToUserInfo userInfo]", this);
   }
 
   public final void generateDtmf(String digits)
     throws TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiInvalidStateException
   {
     TsapiTrace.traceEntry("generateDtmf[String digits]", this);
     try
     {
       this.tsConnection.generateDtmf(digits);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("generateDtmf[String digits]", this);
   }
 
   public final void generateDtmf(String digits, int toneDuration, int pauseDuration)
     throws TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiInvalidStateException
   {
     TsapiTrace.traceEntry("generateDtmf[String digits, int toneDuration, int pauseDuration]", this);
     try
     {
       this.tsConnection.generateDtmf(digits, toneDuration, pauseDuration);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("generateDtmf[String digits, int toneDuration, int pauseDuration]", this);
   }
 
   public final int getMediaAvailability()
   {
     TsapiTrace.traceEntry("getMediaAvailability[]", this);
     try
     {
       TsapiTrace.traceExit("getMediaAvailability[]", this);
       return 129;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final int getMediaState()
   {
     TsapiTrace.traceEntry("getMediaState[]", this);
     try
     {
       TsapiTrace.traceExit("getMediaState[]", this);
       return 0;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final void useDefaultSpeaker()
     throws TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("useDefaultSpeaker[]", this);
     try
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final void useRecordURL(URL url)
     throws TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("useRecordURL[URL url]", this);
     try
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final void useDefaultMicrophone()
     throws TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("useDefaultMicrophone[]", this);
     try
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final void usePlayURL(URL url)
     throws TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("usePlayURL[URL url]", this);
     try
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final void startPlaying()
     throws TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("startPlaying[]", this);
     try
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
     }
     finally
     {
       this.privData = null; }  } 
   // ERROR //
   public final void stopPlaying() { }// Byte code:
     //   0: ldc 67
     //   2: aload_0
     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
     //   6: jsr +12 -> 18
     //   9: goto +17 -> 26
     //   12: astore_1
     //   13: jsr +5 -> 18
     //   16: aload_1
     //   17: athrow
     //   18: astore_2
     //   19: aload_0
     //   20: aconst_null
     //   21: putfield 6	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   24: ret 2
     //   26: ldc 67
     //   28: aload_0
     //   29: invokestatic 5	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   32: return
     //
     // Exception table:
     //   from	to	target	type
     //   6	9	12	finally
     //   12	16	12	finally } 
   public final void startRecording() throws TsapiMethodNotSupportedException { TsapiTrace.traceEntry("startRecording[]", this);
     try
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
     }
     finally
     {
       this.privData = null; }  } 
   // ERROR //
   public final void stopRecording() { }// Byte code:
     //   0: ldc 69
     //   2: aload_0
     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
     //   6: jsr +12 -> 18
     //   9: goto +17 -> 26
     //   12: astore_1
     //   13: jsr +5 -> 18
     //   16: aload_1
     //   17: athrow
     //   18: astore_2
     //   19: aload_0
     //   20: aconst_null
     //   21: putfield 6	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   24: ret 2
     //   26: ldc 69
     //   28: aload_0
     //   29: invokestatic 5	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   32: return
     //
     // Exception table:
     //   from	to	target	type
     //   6	9	12	finally
     //   12	16	12	finally } 
   public final void setDtmfDetection(boolean enable) throws TsapiMethodNotSupportedException { TsapiTrace.traceEntry("setDtmfDetection[boolean enable]", this);
     try
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
     }
     finally
     {
       this.privData = null;
     } }
 
 
   public final void setPrivateData(Object data)
   {
     TsapiTrace.traceEntry("setPrivateData[Object data]", this);
     try
     {
       this.privData = TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data);
     }
     catch (ClassCastException e)
     {
       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
     }
 
     TsapiTrace.traceExit("setPrivateData[Object data]", this);
   }
 
   public final Object getPrivateData()
   {
     TsapiTrace.traceEntry("getPrivateData[]", this);
     Object obj = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate)this.tsConnection.getTermConnPrivateData());
     TsapiTrace.traceExit("getPrivateData[]", this);
     return obj;
   }
 
   public final Object sendPrivateData(Object data)
   {
     TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
     try
     {
       Object obj = this.tsConnection.sendPrivateData(TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data));
       TsapiTrace.traceExit("sendPrivateData[Object data]", this);
       return obj;
     }
     catch (ClassCastException e)
     {
       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
     }
   }
 
   public final ConnectionID getTsapiConnectionID()
   {
     TsapiTrace.traceEntry("getTsapiConnectionID[]", this);
     try
     {
       CSTAConnectionID cstaConnectionID = this.tsConnection.getConnID();
       ConnectionID id = new ConnectionID(cstaConnectionID.getCallID(), cstaConnectionID.getDeviceID(), (short)cstaConnectionID.getDevIDType());
 
       TsapiTrace.traceExit("getTsapiConnectionID[]", this);
       return id;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final int hashCode()
   {
     return this.tsConnection.hashCode();
   }
 
   public boolean equals(Object obj)
   {
     if (obj instanceof TsapiTerminalConnection)
     {
       return this.tsConnection.equals(((TsapiTerminalConnection)obj).tsConnection);
     }
 
     return false;
   }
 
   TsapiTerminalConnection(TSConnection _tsConnection)
   {
     this.tsConnection = _tsConnection;
     TsapiTrace.traceConstruction(this, TsapiTerminalConnection.class);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, TsapiTerminalConnection.class);
   }
 
   final TSConnection getTSConnection()
   {
     TsapiTrace.traceEntry("getTSConnection[]", this);
     TsapiTrace.traceExit("getTSConnection[]", this);
     return this.tsConnection;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiTerminalConnection
 * JD-Core Version:    0.5.4
 */