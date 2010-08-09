 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.ITsapiCall;
 import com.avaya.jtapi.tsapi.ITsapiCallIDPrivate;
 import com.avaya.jtapi.tsapi.ITsapiTerminalConnection;
 import com.avaya.jtapi.tsapi.LookaheadInfo;
 import com.avaya.jtapi.tsapi.LucentAddress;
 import com.avaya.jtapi.tsapi.LucentAgent;
 import com.avaya.jtapi.tsapi.LucentTerminal;
 import com.avaya.jtapi.tsapi.LucentTerminalConnection;
 import com.avaya.jtapi.tsapi.LucentV7Call;
 import com.avaya.jtapi.tsapi.OriginalCallInfo;
 import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
 import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
 import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
 import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
 import com.avaya.jtapi.tsapi.TsapiPlatformException;
 import com.avaya.jtapi.tsapi.TsapiPrivate;
 import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
 import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
 import com.avaya.jtapi.tsapi.UserEnteredCode;
 import com.avaya.jtapi.tsapi.UserToUserInfo;
 import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
 import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
 import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
 import com.avaya.jtapi.tsapi.csta1.LucentConsultationCall;
 import com.avaya.jtapi.tsapi.csta1.LucentDirectAgentCall;
 import com.avaya.jtapi.tsapi.csta1.LucentMakeCall;
 import com.avaya.jtapi.tsapi.csta1.LucentSupervisorAssistCall;
 import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
 import com.avaya.jtapi.tsapi.csta1.LucentV6ConsultationCall;
 import com.avaya.jtapi.tsapi.csta1.LucentV6DirectAgentCall;
 import com.avaya.jtapi.tsapi.csta1.LucentV6MakeCall;
 import com.avaya.jtapi.tsapi.csta1.LucentV6SupervisorAssistCall;
 import com.avaya.jtapi.tsapi.impl.core.TSCall;
 import com.avaya.jtapi.tsapi.impl.core.TSConnection;
 import com.avaya.jtapi.tsapi.impl.core.TSDevice;
 import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
 import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
 import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.Vector;
 import javax.telephony.Address;
 import javax.telephony.Call;
 import javax.telephony.CallListener;
 import javax.telephony.CallObserver;
 import javax.telephony.Connection;
 import javax.telephony.InvalidArgumentException;
 import javax.telephony.PlatformException;
 import javax.telephony.ResourceUnavailableException;
 import javax.telephony.Terminal;
 import javax.telephony.TerminalConnection;
 import javax.telephony.callcenter.ACDAddress;
 import javax.telephony.callcenter.CallCenterAddress;
 import javax.telephony.callcenter.CallCenterTrunk;
 import javax.telephony.capabilities.CallCapabilities;
 import javax.telephony.privatedata.PrivateData;
 import org.apache.log4j.Logger;
 
 public class TsapiCall
   implements ITsapiCall, PrivateData, ITsapiCallIDPrivate, LucentV7Call
 {
   private static Logger log = Logger.getLogger(TsapiCall.class);
   TSCall tsCall;
   CSTAPrivate privData = null;
 
   // ERROR //
   public final Connection[] getConnections()
   {return null;
     // Byte code:
     //   0: ldc 1
     //   2: aload_0
     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
     //   6: aconst_null
     //   7: astore_1
     //   8: aload_0
     //   9: aload_0
     //   10: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   13: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   16: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   19: aload_0
     //   20: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   23: invokevirtual 5	com/avaya/jtapi/tsapi/impl/core/TSCall:getTSConnections	()Ljava/util/Vector;
     //   26: astore_1
     //   27: aload_1
     //   28: ifnonnull +16 -> 44
     //   31: ldc 1
     //   33: aload_0
     //   34: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   37: aconst_null
     //   38: astore_2
     //   39: jsr +109 -> 148
     //   42: aload_2
     //   43: areturn
     //   44: aload_1
     //   45: dup
     //   46: astore_2
     //   47: monitorenter
     //   48: aload_1
     //   49: invokevirtual 7	java/util/Vector:size	()I
     //   52: ifne +18 -> 70
     //   55: ldc 1
     //   57: aload_0
     //   58: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   61: aconst_null
     //   62: astore_3
     //   63: aload_2
     //   64: monitorexit
     //   65: jsr +83 -> 148
     //   68: aload_3
     //   69: areturn
     //   70: aload_1
     //   71: invokevirtual 7	java/util/Vector:size	()I
     //   74: anewarray 8	javax/telephony/Connection
     //   77: astore_3
     //   78: iconst_0
     //   79: istore 4
     //   81: iload 4
     //   83: aload_1
     //   84: invokevirtual 7	java/util/Vector:size	()I
     //   87: if_icmpge +29 -> 116
     //   90: aload_3
     //   91: iload 4
     //   93: aload_1
     //   94: iload 4
     //   96: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
     //   99: checkcast 10	com/avaya/jtapi/tsapi/impl/core/TSConnection
     //   102: iconst_1
     //   103: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
     //   106: checkcast 8	javax/telephony/Connection
     //   109: aastore
     //   110: iinc 4 1
     //   113: goto -32 -> 81
     //   116: ldc 1
     //   118: aload_0
     //   119: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   122: aload_3
     //   123: astore 4
     //   125: aload_2
     //   126: monitorexit
     //   127: jsr +21 -> 148
     //   130: aload 4
     //   132: areturn
     //   133: astore 5
     //   135: aload_2
     //   136: monitorexit
     //   137: aload 5
     //   139: athrow
     //   140: astore 6
     //   142: jsr +6 -> 148
     //   145: aload 6
     //   147: athrow
     //   148: astore 7
     //   150: aload_0
     //   151: aconst_null
     //   152: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   155: ret 7
     //
     // Exception table:
     //   from	to	target	type
     //   48	65	133	finally
     //   70	127	133	finally
     //   133	137	133	finally
     //   6	42	140	finally
     //   44	68	140	finally
     //   70	130	140	finally
     //   133	145	140	finally
   }
 
   // ERROR //
   public final javax.telephony.Provider getProvider()
   {return null;
     // Byte code:
     //   0: ldc 13
     //   2: aload_0
     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
     //   6: aload_0
     //   7: aload_0
     //   8: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   11: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   14: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   17: aload_0
     //   18: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   21: invokevirtual 14	com/avaya/jtapi/tsapi/impl/core/TSCall:getTSProviderImpl	()Lcom/avaya/jtapi/tsapi/impl/core/TSProviderImpl;
     //   24: astore_1
     //   25: aload_1
     //   26: ifnull +25 -> 51
     //   29: aload_1
     //   30: iconst_0
     //   31: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
     //   34: checkcast 15	javax/telephony/Provider
     //   37: astore_2
     //   38: ldc 13
     //   40: aload_0
     //   41: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   44: aload_2
     //   45: astore_3
     //   46: jsr +25 -> 71
     //   49: aload_3
     //   50: areturn
     //   51: new 16	com/avaya/jtapi/tsapi/TsapiPlatformException
     //   54: dup
     //   55: iconst_4
     //   56: iconst_0
     //   57: ldc 17
     //   59: invokespecial 18	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
     //   62: athrow
     //   63: astore 4
     //   65: jsr +6 -> 71
     //   68: aload 4
     //   70: athrow
     //   71: astore 5
     //   73: aload_0
     //   74: aconst_null
     //   75: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   78: ret 5
     //
     // Exception table:
     //   from	to	target	type
     //   6	49	63	finally
     //   51	68	63	finally
   }
 
   public final int getState()
   {
     TsapiTrace.traceEntry("getState[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       int state = this.tsCall.getState();
       TsapiTrace.traceExit("getState[]", this);
       return state;
     }
     finally
     {
       this.privData = null; }  } 
   // ERROR //
   public final Connection[] connect(Terminal origterm, Address origaddr, String dialedDigits) throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException {return null;} // Byte code:
     //   0: ldc 21
     //   2: aload_0
     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
     //   6: aload_1
     //   7: instanceof 22
     //   10: ifne +15 -> 25
     //   13: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
     //   16: dup
     //   17: iconst_3
     //   18: iconst_0
     //   19: ldc 24
     //   21: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
     //   24: athrow
     //   25: aload_2
     //   26: instanceof 26
     //   29: ifne +15 -> 44
     //   32: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
     //   35: dup
     //   36: iconst_3
     //   37: iconst_0
     //   38: ldc 27
     //   40: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
     //   43: athrow
     //   44: aconst_null
     //   45: astore 4
     //   47: aload_2
     //   48: checkcast 28	com/avaya/jtapi/tsapi/impl/TsapiAddress
     //   51: invokevirtual 29	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
     //   54: astore 5
     //   56: aload_1
     //   57: checkcast 30	com/avaya/jtapi/tsapi/impl/TsapiTerminal
     //   60: invokevirtual 31	com/avaya/jtapi/tsapi/impl/TsapiTerminal:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
     //   63: astore 6
     //   65: aload 5
     //   67: ifnull +60 -> 127
     //   70: aload 6
     //   72: ifnull +55 -> 127
     //   75: aload 5
     //   77: aload 6
     //   79: invokevirtual 32	java/lang/Object:equals	(Ljava/lang/Object;)Z
     //   82: ifeq +33 -> 115
     //   85: aload_0
     //   86: aload_0
     //   87: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   90: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   93: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   96: aload_0
     //   97: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   100: aload 5
     //   102: aload_3
     //   103: aload_0
     //   104: getfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   107: invokevirtual 33	com/avaya/jtapi/tsapi/impl/core/TSCall:connect	(Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Ljava/lang/String;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Ljava/util/Vector;
     //   110: astore 4
     //   112: goto +27 -> 139
     //   115: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
     //   118: dup
     //   119: iconst_3
     //   120: iconst_0
     //   121: ldc 34
     //   123: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
     //   126: athrow
     //   127: new 16	com/avaya/jtapi/tsapi/TsapiPlatformException
     //   130: dup
     //   131: iconst_4
     //   132: iconst_0
     //   133: ldc 35
     //   135: invokespecial 18	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
     //   138: athrow
     //   139: aload 4
     //   141: ifnonnull +18 -> 159
     //   144: ldc 21
     //   146: aload_0
     //   147: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   150: aconst_null
     //   151: astore 7
     //   153: jsr +124 -> 277
     //   156: aload 7
     //   158: areturn
     //   159: aload 4
     //   161: dup
     //   162: astore 7
     //   164: monitorenter
     //   165: aload 4
     //   167: invokevirtual 7	java/util/Vector:size	()I
     //   170: ifne +21 -> 191
     //   173: ldc 21
     //   175: aload_0
     //   176: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   179: aconst_null
     //   180: astore 8
     //   182: aload 7
     //   184: monitorexit
     //   185: jsr +92 -> 277
     //   188: aload 8
     //   190: areturn
     //   191: aload 4
     //   193: invokevirtual 7	java/util/Vector:size	()I
     //   196: anewarray 8	javax/telephony/Connection
     //   199: astore 8
     //   201: iconst_0
     //   202: istore 9
     //   204: iload 9
     //   206: aload 4
     //   208: invokevirtual 7	java/util/Vector:size	()I
     //   211: if_icmpge +31 -> 242
     //   214: aload 8
     //   216: iload 9
     //   218: aload 4
     //   220: iload 9
     //   222: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
     //   225: checkcast 10	com/avaya/jtapi/tsapi/impl/core/TSConnection
     //   228: iconst_1
     //   229: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
     //   232: checkcast 8	javax/telephony/Connection
     //   235: aastore
     //   236: iinc 9 1
     //   239: goto -35 -> 204
     //   242: ldc 21
     //   244: aload_0
     //   245: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   248: aload 8
     //   250: astore 9
     //   252: aload 7
     //   254: monitorexit
     //   255: jsr +22 -> 277
     //   258: aload 9
     //   260: areturn
     //   261: astore 10
     //   263: aload 7
     //   265: monitorexit
     //   266: aload 10
     //   268: athrow
     //   269: astore 11
     //   271: jsr +6 -> 277
     //   274: aload 11
     //   276: athrow
     //   277: astore 12
     //   279: aload_0
     //   280: aconst_null
     //   281: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   284: ret 12
     //
     // Exception table:
     //   from	to	target	type
     //   165	185	261	finally
     //   191	255	261	finally
     //   261	266	261	finally
     //   6	156	269	finally
     //   159	188	269	finally
     //   191	258	269	finally
     //   261	274	269	finally } 
   private LucentMakeCall createLucentMakeCall(String destRoute, boolean priorityCall, UserToUserInfo userInfo) { TsapiTrace.traceEntry("createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]", this);
     TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(userInfo);
 
     if (TSProviderImpl != null)
     {
       if (TSProviderImpl.isLucentV6()) {
         LucentV6MakeCall call = new LucentV6MakeCall(destRoute, priorityCall, asn_uui);
         TsapiTrace.traceExit("createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]", this);
         return call;
       }
 
       LucentMakeCall call = new LucentMakeCall(destRoute, priorityCall, asn_uui);
       TsapiTrace.traceExit("createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]", this);
       return call;
     }
 
     TsapiTrace.traceExit("createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]", this);
     return null; }
 
 
   public final Connection[] connect(LucentTerminal origterm, LucentAddress origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo)
     throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("connect[LucentTerminal origterm, LucentAddress origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo]", this);
     if (origterm == null) {
       throw new TsapiInvalidArgumentException(3, 0, "orig Terminal is null");
     }
 
     if (origaddr == null) {
       throw new TsapiInvalidArgumentException(3, 0, "orig Address is null");
     }
 
     LucentMakeCall lmc = createLucentMakeCall(null, priorityCall, userInfo);
     this.privData = lmc.makeTsapiPrivate();
     Connection[] conns = connect(origterm, origaddr, dialedDigits);
     TsapiTrace.traceExit("connect[LucentTerminal origterm, LucentAddress origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo]", this);
     return conns;
   }
 
   public final Connection[] connectDirectAgent(LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo)
     throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
     if (calledAgent == null) {
       throw new TsapiInvalidArgumentException(3, 0, "called Agent is null");
     }
 
     if (calledAgent.getACDAddress() != null)
     {
       LucentDirectAgentCall lda = createLucentDirectAgentCall(calledAgent.getACDAddress().getName(), priorityCall, userInfo);
       this.privData = lda.makeTsapiPrivate();
       Connection[] conns = connect(origterm, origaddr, calledAgent.getAgentAddress().getName());
       TsapiTrace.traceExit("connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
       return conns;
     }
 
     log.info("*****connectDirectAgent: ACDAddress is NULL, using default Skill (ACD)");
     Connection[] conns = connect(origterm, origaddr, calledAgent.getAgentID());
     TsapiTrace.traceExit("connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
     return conns;
   }
 
   public final Connection[] connectDirectAgent(LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress)
     throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
     if (calledAgent == null) {
       throw new TsapiInvalidArgumentException(3, 0, "called Agent is null");
     }
 
     if (acdaddress != null)
     {
       LucentDirectAgentCall lda = createLucentDirectAgentCall(acdaddress.getName(), priorityCall, userInfo);
       this.privData = lda.makeTsapiPrivate();
       Connection[] conns = connect(origterm, origaddr, calledAgent.getAgentAddress().getName());
       TsapiTrace.traceExit("connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
       return conns;
     }
 
     Connection[] conns = connectDirectAgent(origterm, origaddr, calledAgent, priorityCall, userInfo);
     TsapiTrace.traceExit("connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
     return conns;
   }
 
   private LucentSupervisorAssistCall createLucentSupervisorAssistCall(String split, UserToUserInfo userInfo)
   {
     TsapiTrace.traceEntry("createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]", this);
     TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(userInfo);
 
     if (TSProviderImpl != null)
     {
       if (TSProviderImpl.isLucentV6()) {
         LucentV6SupervisorAssistCall call = new LucentV6SupervisorAssistCall(split, asn_uui);
         TsapiTrace.traceExit("createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]", this);
         return call;
       }
 
       LucentSupervisorAssistCall call = new LucentSupervisorAssistCall(split, asn_uui);
       TsapiTrace.traceExit("createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]", this);
       return call;
     }
 
     TsapiTrace.traceExit("createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]", this);
     return null;
   }
 
   public final Connection[] connectSupervisorAssist(LucentAgent callingAgent, String dialedDigits, UserToUserInfo userInfo)
     throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("connectSupervisorAssist[LucentAgent callingAgent, String dialedDigits, UserToUserInfo userInfo]", this);
     if (callingAgent == null) {
       throw new TsapiInvalidArgumentException(3, 0, "calling Agent is null");
     }
 
     LucentSupervisorAssistCall lsa = createLucentSupervisorAssistCall(callingAgent.getACDAddress().getName(), userInfo);
     this.privData = lsa.makeTsapiPrivate();
     Connection[] conns = connect(callingAgent.getAgentTerminal(), callingAgent.getAgentAddress(), dialedDigits);
     TsapiTrace.traceExit("connectSupervisorAssist[LucentAgent callingAgent, String dialedDigits, UserToUserInfo userInfo]", this);
     return conns;
   }
 
   public void addObserver(CallObserver observer)
     throws TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("addObserver[CallObserver observer]", this);
     try {
       addTsapiCallEventMonitor(observer, null);
     } catch (Exception e) {
       log.error(e.getMessage(), e);
     }
     TsapiTrace.traceExit("addObserver[CallObserver observer]", this);
   }
 
   private void addTsapiCallEventMonitor(CallObserver observer, CallListener listener)
     throws Exception
   {
     TsapiTrace.traceEntry("addTsapiCallEventMonitor(CallObserver observer, CallListener listener)", this);
     if ((observer != null) && (listener != null))
       throw new Exception("Invalid call to add event monitor. At a time either a listener or an observer can be added");
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       TSProviderImpl prov = this.tsCall.getTSProviderImpl();
 
       if (prov == null)
       {
         throw new TsapiPlatformException(4, 0, "could not locate provider");
       }
 
       Vector observers = prov.getCallMonitorThreads();
 
       TsapiCallMonitor obs = null;
       TsapiCallMonitor obsToUse = null;
 
       synchronized (observers)
       {
         for (int i = 0; i < observers.size(); ++i)
         {
           obs = (TsapiCallMonitor)observers.elementAt(i);
           if (observer != null) {
             if (obs.getObserver() != observer)
               continue;
             obsToUse = obs;
             break;
           }
           if ((listener == null) || 
             (obs.getListener() != listener)) continue;
           obsToUse = obs;
           break;
         }
 
         if (obsToUse == null)
         {
           if (observer != null)
             obsToUse = new TsapiCallMonitor(prov, observer);
           else if (listener != null)
             obsToUse = new TsapiCallMonitor(prov, listener);
           if (obsToUse == null)
           {
             throw new TsapiPlatformException(4, 0, "could not allocate Monitor wrapper");
           }
 
         }
 
       }
 
       this.tsCall.addCallMonitor(obsToUse);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("addTsapiCallEventMonitor(CallObserver observer, CallListener listener)", this);
   }
 
   public CallObserver[] getObservers()
   {
     TsapiTrace.traceEntry("getObservers[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       Vector tsapiCallObservers = this.tsCall.getCallObservers();
 
       if ((tsapiCallObservers == null) || (tsapiCallObservers.size() == 0))
       {
         TsapiTrace.traceExit("getObservers[]", this);
         return null;
       }
 
       ArrayList observers = new ArrayList();
 
       for (Iterator i$ = tsapiCallObservers.iterator(); i$.hasNext(); ) { TsapiCallMonitor obs = (TsapiCallMonitor)i$.next();
         if (obs.getObserver() != null)
           observers.add(obs.getObserver()); }
 
       TsapiCallMonitor obs;
       TsapiTrace.traceExit("getObservers[]", this);
       CallObserver[] observerArray = new CallObserver[observers.size()];
       return (CallObserver[])observers.toArray(observerArray);
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public void removeObserver(CallObserver observer)
   {
     TsapiTrace.traceEntry("removeObserver[CallObserver observer]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       Vector tsapiCallObservers = this.tsCall.getCallObservers();
 
       if ((tsapiCallObservers == null) || (tsapiCallObservers.size() == 0))
       {
         TsapiTrace.traceExit("removeObserver[CallObserver observer]", this);
         return;
       }
 
       for (int i = 0; i < tsapiCallObservers.size(); ++i)
       {
         TsapiCallMonitor obs = (TsapiCallMonitor)tsapiCallObservers.elementAt(i);
         if (obs.getObserver() != observer)
           continue;
         this.tsCall = this.tsCall.getHandOff();
         this.tsCall.removeCallMonitor(obs);
         TsapiTrace.traceExit("removeObserver[CallObserver observer]", this);
         return;
       }
 
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final CallCapabilities getCapabilities(Terminal terminal, Address address)
   {
     TsapiTrace.traceEntry("getCapabilities[Terminal terminal, Address address]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       CallCapabilities caps = this.tsCall.getTsapiCallCapabilities();
       TsapiTrace.traceExit("getCapabilities[Terminal terminal, Address address]", this);
       return caps;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final CallCapabilities getCallCapabilities(Terminal term, Address addr)
     throws InvalidArgumentException, PlatformException
   {
     TsapiTrace.traceEntry("getCallCapabilities[Terminal term, Address addr]", this);
     CallCapabilities caps = getCapabilities(null, null);
     TsapiTrace.traceExit("getCallCapabilities[Terminal term, Address addr]", this);
     return caps;
   }
 
   public final Address getCallingAddress()
   {
     TsapiTrace.traceEntry("getCallingAddress[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       TSDevice tsDevice = this.tsCall.getCallingAddress();
       Address addr;
       if (tsDevice != null)
       {
         addr = (Address)TsapiCreateObject.getTsapiObject(tsDevice, true);
         TsapiTrace.traceExit("getCallingAddress[]", this);
         return addr;
       }
 
       TsapiTrace.traceExit("getCallingAddress[]", this);
       return null;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final Terminal getCallingTerminal()
   {
     TsapiTrace.traceEntry("getCallingTerminal[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       TSDevice tsDevice = this.tsCall.getCallingTerminal();
       Terminal term;
       if (tsDevice != null)
       {
         term = (Terminal)TsapiCreateObject.getTsapiObject(tsDevice, false);
         TsapiTrace.traceExit("getCallingTerminal[]", this);
         return term;
       }
 
       TsapiTrace.traceExit("getCallingTerminal[]", this);
       return null;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final Address getCalledAddress()
   {
     TsapiTrace.traceEntry("getCalledAddress[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       TSDevice tsDevice = this.tsCall.getCalledDevice();
       Address addr;
       if (tsDevice != null)
       {
         addr = (Address)TsapiCreateObject.getTsapiObject(tsDevice, true);
         TsapiTrace.traceExit("getCalledAddress[]", this);
         return addr;
       }
 
       TsapiTrace.traceExit("getCalledAddress[]", this);
       return null;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final Address getLastRedirectedAddress()
   {
     TsapiTrace.traceEntry("getLastRedirectedAddress[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       TSDevice tsDevice = this.tsCall.getLastRedirectionDevice();
       Address addr;
       if (tsDevice != null)
       {
         addr = (Address)TsapiCreateObject.getTsapiObject(tsDevice, true);
         TsapiTrace.traceExit("getLastRedirectedAddress[]", this);
         return addr;
       }
 
       TsapiTrace.traceExit("getLastRedirectedAddress[]", this);
       return null;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final Connection offHook(Address origaddress, Terminal origterminal)
     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("offHook[Address origaddress, Terminal origterminal]", this);
     try
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final Connection addParty(String newParty, boolean active)
     throws TsapiInvalidStateException, TsapiInvalidPartyException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("addParty[String newParty, boolean active]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       TSConnection tsConn = this.tsCall.addParty(newParty, active);
       Connection conn;
       if (tsConn != null)
       {
         conn = (Connection)TsapiCreateObject.getTsapiObject(tsConn, true);
         TsapiTrace.traceExit("addParty[String newParty, boolean active]", this);
         return conn;
       }
 
       TsapiTrace.traceExit("addParty[String newParty, boolean active]", this);
       return null;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final Connection addParty(String newParty)
     throws TsapiInvalidStateException, TsapiInvalidPartyException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("addParty[String newParty]", this);
     Connection conn = addParty(newParty, true);
     TsapiTrace.traceExit("addParty[String newParty]", this);
     return conn;
   }
 
   public final void drop()
     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("drop[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       this.tsCall.drop(this.privData);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("drop[]", this);
   }
 
   public final void conference(Call otherCall)
     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("conference[Call otherCall]", this);
     try
     {
       if (!(otherCall instanceof LucentV7Call)) {
         throw new TsapiInvalidArgumentException(3, 0, "other Call is not an instanceof ITsapiCall");
       }
 
       TSCall oCall = ((TsapiCall)otherCall).getTSCall();
       if (oCall != null)
       {
         this.tsCall = this.tsCall.getHandOff();
         this.tsCall.conference(oCall, this.privData);
       }
       else
       {
         throw new TsapiPlatformException(4, 0, "could not locate other call");
       }
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("conference[Call otherCall]", this);
   }
 
   public final void transfer(Call otherCall)
     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("transfer[Call otherCall]", this);
     try
     {
       if (!(otherCall instanceof LucentV7Call)) {
         throw new TsapiInvalidArgumentException(3, 0, "other Call is not an instanceof ITsapiCall");
       }
 
       TSCall oCall = ((TsapiCall)otherCall).getTSCall();
       if (oCall != null)
       {
         this.tsCall = this.tsCall.getHandOff();
         this.tsCall.transfer(oCall, this.privData);
       }
       else
       {
         throw new TsapiPlatformException(4, 0, "could not locate other call");
       }
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("transfer[Call otherCall]", this);
   }
 
   public final Connection transfer(String address)
     throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiInvalidPartyException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("transfer[String address]", this);
     try
     {
       if ((address == null) || (address.equals("")))
       {
         throw new TsapiInvalidArgumentException(3, 0, "address null or an empty string");
       }
 
       this.tsCall = this.tsCall.getHandOff();
       TSConnection tsConn = this.tsCall.transfer(address, this.privData);
       Connection conn;
       if (tsConn != null)
       {
         conn = (Connection)TsapiCreateObject.getTsapiObject(tsConn, true);
         TsapiTrace.traceExit("transfer[String address]", this);
         return conn;
       }
 
       TsapiTrace.traceExit("transfer[String address]", this);
       return null;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final void setConferenceController(TerminalConnection termconn)
     throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException
   {
     try
     {
       TsapiTrace.traceEntry("setConferenceController[TerminalConnection termconn]", this);
       if (!(termconn instanceof ITsapiTerminalConnection)) {
         throw new TsapiInvalidArgumentException(3, 0, "The given TerminalConnection is not an instanceof ITsapiTerminalConnection");
       }
 
       TSConnection tsConn = ((TsapiTerminalConnection)termconn).getTSConnection();
       if (tsConn != null)
       {
         this.tsCall = this.tsCall.getHandOff();
         this.tsCall.setConfController(tsConn);
       }
       else
       {
         throw new TsapiPlatformException(4, 0, "could not locate terminal connection");
       }
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("setConferenceController[TerminalConnection termconn]", this);
   }
 
   public final TerminalConnection getConferenceController()
   {
     TsapiTrace.traceEntry("getConferenceController[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       TSConnection tsConn = this.tsCall.getConfController();
       TerminalConnection conn;
       if (tsConn != null)
       {
         conn = (TerminalConnection)TsapiCreateObject.getTsapiObject(tsConn, false);
         TsapiTrace.traceExit("getConferenceController[]", this);
         return conn;
       }
 
       TsapiTrace.traceExit("getConferenceController[]", this);
       return null;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final void setTransferController(TerminalConnection termconn)
     throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("setTransferController[TerminalConnection termconn]", this);
     try
     {
       if (!(termconn instanceof ITsapiTerminalConnection)) {
         throw new TsapiInvalidArgumentException(3, 0, "The given TerminalConnection is not an instanceof ITsapiTerminalConnection");
       }
 
       TSConnection tsConn = ((TsapiTerminalConnection)termconn).getTSConnection();
       if (tsConn != null)
       {
         this.tsCall = this.tsCall.getHandOff();
         this.tsCall.setXferController(tsConn);
       }
       else
       {
         throw new TsapiPlatformException(4, 0, "could not locate terminal connection");
       }
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("setTransferController[TerminalConnection termconn]", this);
   }
 
   public final TerminalConnection getTransferController()
   {
     TsapiTrace.traceEntry("getTransferController[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       TSConnection tsConn = this.tsCall.getXferController();
       TerminalConnection obj;
       if (tsConn != null)
       {
         obj = (TerminalConnection)TsapiCreateObject.getTsapiObject(tsConn, false);
         TsapiTrace.traceExit("getTransferController[]", this);
         return obj;
       }
 
       TsapiTrace.traceExit("getTransferController[]", this);
       return null;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final void setConferenceEnable(boolean enable)
     throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException
   {
     TsapiTrace.traceEntry("setConferenceEnable[boolean enable]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       this.tsCall.setConfEnable(enable);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("setConferenceEnable[boolean enable]", this);
   }
 
   public final boolean getConferenceEnable()
   {
     TsapiTrace.traceEntry("getConferenceEnable[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       boolean enable = this.tsCall.getConfEnable();
       TsapiTrace.traceExit("getConferenceEnable[]", this);
       return enable;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final void setTransferEnable(boolean enable)
     throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException
   {
     TsapiTrace.traceEntry("setTransferEnable[boolean enable]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       this.tsCall.setXferEnable(enable);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("setTransferEnable[boolean enable]", this);
   }
 
   public final boolean getTransferEnable()
   {
     TsapiTrace.traceEntry("getTransferEnable[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       boolean enabled = this.tsCall.getXferEnable();
       TsapiTrace.traceExit("getTransferEnable[]", this);
       return enabled;
     }
     finally
     {
       this.privData = null; }  } 
   // ERROR //
   public final Connection[] consult(TerminalConnection termconn, String address) throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiPrivilegeViolationException { return null;}// Byte code:
     //   0: ldc 162
     //   2: aload_0
     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
     //   6: aload_1
     //   7: instanceof 141
     //   10: ifne +15 -> 25
     //   13: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
     //   16: dup
     //   17: iconst_3
     //   18: iconst_0
     //   19: ldc 142
     //   21: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
     //   24: athrow
     //   25: aconst_null
     //   26: astore_3
     //   27: aload_1
     //   28: checkcast 143	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection
     //   31: invokevirtual 144	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:getTSConnection	()Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
     //   34: astore 4
     //   36: aload 4
     //   38: ifnull +32 -> 70
     //   41: aload_0
     //   42: aload_0
     //   43: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   46: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   49: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   52: aload_0
     //   53: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   56: aload 4
     //   58: aload_2
     //   59: aload_0
     //   60: getfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   63: invokevirtual 163	com/avaya/jtapi/tsapi/impl/core/TSCall:consult	(Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;Ljava/lang/String;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Ljava/util/Vector;
     //   66: astore_3
     //   67: goto +15 -> 82
     //   70: new 16	com/avaya/jtapi/tsapi/TsapiPlatformException
     //   73: dup
     //   74: iconst_4
     //   75: iconst_0
     //   76: ldc 146
     //   78: invokespecial 18	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
     //   81: athrow
     //   82: aload_3
     //   83: ifnonnull +18 -> 101
     //   86: ldc 162
     //   88: aload_0
     //   89: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   92: aconst_null
     //   93: astore 5
     //   95: jsr +119 -> 214
     //   98: aload 5
     //   100: areturn
     //   101: aload_3
     //   102: dup
     //   103: astore 5
     //   105: monitorenter
     //   106: aload_3
     //   107: invokevirtual 7	java/util/Vector:size	()I
     //   110: ifne +21 -> 131
     //   113: ldc 162
     //   115: aload_0
     //   116: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   119: aconst_null
     //   120: astore 6
     //   122: aload 5
     //   124: monitorexit
     //   125: jsr +89 -> 214
     //   128: aload 6
     //   130: areturn
     //   131: aload_3
     //   132: invokevirtual 7	java/util/Vector:size	()I
     //   135: anewarray 8	javax/telephony/Connection
     //   138: astore 6
     //   140: iconst_0
     //   141: istore 7
     //   143: iload 7
     //   145: aload_3
     //   146: invokevirtual 7	java/util/Vector:size	()I
     //   149: if_icmpge +30 -> 179
     //   152: aload 6
     //   154: iload 7
     //   156: aload_3
     //   157: iload 7
     //   159: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
     //   162: checkcast 10	com/avaya/jtapi/tsapi/impl/core/TSConnection
     //   165: iconst_1
     //   166: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
     //   169: checkcast 8	javax/telephony/Connection
     //   172: aastore
     //   173: iinc 7 1
     //   176: goto -33 -> 143
     //   179: ldc 162
     //   181: aload_0
     //   182: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   185: aload 6
     //   187: astore 7
     //   189: aload 5
     //   191: monitorexit
     //   192: jsr +22 -> 214
     //   195: aload 7
     //   197: areturn
     //   198: astore 8
     //   200: aload 5
     //   202: monitorexit
     //   203: aload 8
     //   205: athrow
     //   206: astore 9
     //   208: jsr +6 -> 214
     //   211: aload 9
     //   213: athrow
     //   214: astore 10
     //   216: aload_0
     //   217: aconst_null
     //   218: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   221: ret 10
     //
     // Exception table:
     //   from	to	target	type
     //   106	125	198	finally
     //   131	192	198	finally
     //   198	203	198	finally
     //   6	98	206	finally
     //   101	128	206	finally
     //   131	195	206	finally
     //   198	211	206	finally } 
   public final Connection consult(TerminalConnection termconn) throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiPrivilegeViolationException { TsapiTrace.traceEntry("consult[TerminalConnection termconn]", this);
     try
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
     }
     finally
     {
       this.privData = null;
     } }
 
 
   private LucentConsultationCall createLucentConsultationCall(String destRoute, boolean priorityCall, UserToUserInfo userInfo)
   {
     TsapiTrace.traceEntry("createLucentConsultationCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]", this);
     TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(userInfo);
 
     LucentConsultationCall call = null;
     if (TSProviderImpl != null)
     {
       if (TSProviderImpl.isLucentV6()) {
         call = new LucentV6ConsultationCall(destRoute, priorityCall, asn_uui);
       }
       else {
         call = new LucentConsultationCall(destRoute, priorityCall, asn_uui);
       }
     }
     TsapiTrace.traceExit("createLucentConsultationCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]", this);
     return call;
   }
 
   public final Connection[] consult(LucentTerminalConnection termconn, String address, boolean priorityCall, UserToUserInfo userInfo)
     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiPrivilegeViolationException
   {
     TsapiTrace.traceEntry("consult[LucentTerminalConnection termconn, String address, boolean priorityCall, UserToUserInfo userInfo]", this);
     if (termconn == null) {
       throw new TsapiInvalidArgumentException(3, 0, "The given TerminalConnection is null");
     }
 
     LucentConsultationCall lcc = createLucentConsultationCall(null, priorityCall, userInfo);
     this.privData = lcc.makeTsapiPrivate();
     Connection[] conns = consult(termconn, address);
     TsapiTrace.traceExit("consult[LucentTerminalConnection termconn, String address, boolean priorityCall, UserToUserInfo userInfo]", this);
     return conns;
   }
 
   private LucentDirectAgentCall createLucentDirectAgentCall(String split, boolean priorityCall, UserToUserInfo userInfo)
   {
     TsapiTrace.traceEntry("createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]", this);
     TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(userInfo);
 
     if (TSProviderImpl != null)
     {
       if (TSProviderImpl.isLucentV6()) {
         LucentV6DirectAgentCall call = new LucentV6DirectAgentCall(split, priorityCall, asn_uui);
         TsapiTrace.traceExit("createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]", this);
         return call;
       }
 
       LucentDirectAgentCall call = new LucentDirectAgentCall(split, priorityCall, asn_uui);
       TsapiTrace.traceExit("createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]", this);
       return call;
     }
 
     TsapiTrace.traceExit("createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]", this);
     return null;
   }
 
   public final Connection[] consultDirectAgent(LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo)
     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiPrivilegeViolationException
   {
     TsapiTrace.traceEntry("consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
     if (termconn == null) {
       throw new TsapiInvalidArgumentException(3, 0, "The given TerminalConnection is null");
     }
 
     if (calledAgent == null) {
       throw new TsapiInvalidArgumentException(3, 0, "called Agent is null");
     }
 
     if (calledAgent.getACDAddress() != null)
     {
       LucentDirectAgentCall lda = createLucentDirectAgentCall(calledAgent.getACDAddress().getName(), priorityCall, userInfo);
       this.privData = lda.makeTsapiPrivate();
       Connection[] conns = consult(termconn, calledAgent.getAgentAddress().getName());
       TsapiTrace.traceExit("consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
       return conns;
     }
 
     log.info("*****consultDirectAgent: ACDAddress is Null, using default skill(ACD)");
     Connection[] conns = consult(termconn, calledAgent.getAgentID());
     TsapiTrace.traceExit("consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
     return conns;
   }
 
   public final Connection[] consultDirectAgent(LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress)
     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiPrivilegeViolationException
   {
     TsapiTrace.traceEntry("consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress]", this);
     if (termconn == null) {
       throw new TsapiInvalidArgumentException(3, 0, "The given TerminalConnection is null");
     }
 
     if (calledAgent == null) {
       throw new TsapiInvalidArgumentException(3, 0, "called Agent is null");
     }
 
     if (acdaddress != null)
     {
       LucentDirectAgentCall lda = createLucentDirectAgentCall(acdaddress.getName(), priorityCall, userInfo);
       this.privData = lda.makeTsapiPrivate();
       Connection[] conns = consult(termconn, calledAgent.getAgentAddress().getName());
       TsapiTrace.traceExit("consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress]", this);
       return conns;
     }
 
     Connection[] conns = consultDirectAgent(termconn, calledAgent, priorityCall, userInfo);
     TsapiTrace.traceExit("consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress]", this);
     return conns;
   }
 
   public final Connection[] consultSupervisorAssist(LucentTerminalConnection termconn, ACDAddress split, String address, UserToUserInfo userInfo)
     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiPrivilegeViolationException
   {
     TsapiTrace.traceEntry("consultSupervisorAssist[LucentTerminalConnection termconn, ACDAddress split, String address, UserToUserInfo userInfo]", this);
     if (termconn == null) {
       throw new TsapiInvalidArgumentException(3, 0, "The given TerminalConnection is null");
     }
 
     if (!(split instanceof LucentAddress)) {
       throw new TsapiInvalidArgumentException(3, 0, "The given ACD Address is not an instanceof LucentAddress");
     }
 
     LucentSupervisorAssistCall lsa = createLucentSupervisorAssistCall(split.getName(), userInfo);
     this.privData = lsa.makeTsapiPrivate();
     Connection[] conns = consult(termconn, address);
     TsapiTrace.traceExit("consultSupervisorAssist[LucentTerminalConnection termconn, ACDAddress split, String address, UserToUserInfo userInfo]", this);
     return conns; } 
   // ERROR //
   public final Connection[] connectPredictive(Terminal originatorTerminal, Address origAddress, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType) throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException { return null;}// Byte code:
     //   0: ldc 187
     //   2: aload_0
     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
     //   6: aload_1
     //   7: ifnull +22 -> 29
     //   10: aload_1
     //   11: instanceof 22
     //   14: ifne +15 -> 29
     //   17: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
     //   20: dup
     //   21: iconst_3
     //   22: iconst_0
     //   23: ldc 188
     //   25: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
     //   28: athrow
     //   29: aload_2
     //   30: instanceof 26
     //   33: ifne +15 -> 48
     //   36: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
     //   39: dup
     //   40: iconst_3
     //   41: iconst_0
     //   42: ldc 189
     //   44: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
     //   47: athrow
     //   48: aconst_null
     //   49: astore 8
     //   51: aload_2
     //   52: checkcast 28	com/avaya/jtapi/tsapi/impl/TsapiAddress
     //   55: invokevirtual 29	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
     //   58: astore 9
     //   60: aconst_null
     //   61: astore 10
     //   63: aload_1
     //   64: ifnull +12 -> 76
     //   67: aload_1
     //   68: checkcast 30	com/avaya/jtapi/tsapi/impl/TsapiTerminal
     //   71: invokevirtual 31	com/avaya/jtapi/tsapi/impl/TsapiTerminal:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
     //   74: astore 10
     //   76: aload 9
     //   78: ifnull +71 -> 149
     //   81: aload 10
     //   83: ifnull +25 -> 108
     //   86: aload 9
     //   88: aload 10
     //   90: invokevirtual 32	java/lang/Object:equals	(Ljava/lang/Object;)Z
     //   93: ifne +15 -> 108
     //   96: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
     //   99: dup
     //   100: iconst_3
     //   101: iconst_0
     //   102: ldc 190
     //   104: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
     //   107: athrow
     //   108: aload_0
     //   109: aload_0
     //   110: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   113: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   116: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   119: aload_0
     //   120: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   123: aload 9
     //   125: aload_3
     //   126: iload 4
     //   128: iload 5
     //   130: iload 6
     //   132: iload 7
     //   134: aconst_null
     //   135: iconst_0
     //   136: aconst_null
     //   137: aload_0
     //   138: getfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   141: invokevirtual 191	com/avaya/jtapi/tsapi/impl/core/TSCall:connectPredictive	(Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Ljava/lang/String;IIIILjava/lang/String;ZLcom/avaya/jtapi/tsapi/UserToUserInfo;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Ljava/util/Vector;
     //   144: astore 8
     //   146: goto +15 -> 161
     //   149: new 16	com/avaya/jtapi/tsapi/TsapiPlatformException
     //   152: dup
     //   153: iconst_4
     //   154: iconst_0
     //   155: ldc 35
     //   157: invokespecial 18	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
     //   160: athrow
     //   161: aload 8
     //   163: ifnonnull +18 -> 181
     //   166: ldc 187
     //   168: aload_0
     //   169: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   172: aconst_null
     //   173: astore 11
     //   175: jsr +124 -> 299
     //   178: aload 11
     //   180: areturn
     //   181: aload 8
     //   183: dup
     //   184: astore 11
     //   186: monitorenter
     //   187: aload 8
     //   189: invokevirtual 7	java/util/Vector:size	()I
     //   192: ifne +21 -> 213
     //   195: ldc 187
     //   197: aload_0
     //   198: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   201: aconst_null
     //   202: astore 12
     //   204: aload 11
     //   206: monitorexit
     //   207: jsr +92 -> 299
     //   210: aload 12
     //   212: areturn
     //   213: aload 8
     //   215: invokevirtual 7	java/util/Vector:size	()I
     //   218: anewarray 8	javax/telephony/Connection
     //   221: astore 12
     //   223: iconst_0
     //   224: istore 13
     //   226: iload 13
     //   228: aload 8
     //   230: invokevirtual 7	java/util/Vector:size	()I
     //   233: if_icmpge +31 -> 264
     //   236: aload 12
     //   238: iload 13
     //   240: aload 8
     //   242: iload 13
     //   244: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
     //   247: checkcast 10	com/avaya/jtapi/tsapi/impl/core/TSConnection
     //   250: iconst_1
     //   251: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
     //   254: checkcast 8	javax/telephony/Connection
     //   257: aastore
     //   258: iinc 13 1
     //   261: goto -35 -> 226
     //   264: ldc 187
     //   266: aload_0
     //   267: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   270: aload 12
     //   272: astore 13
     //   274: aload 11
     //   276: monitorexit
     //   277: jsr +22 -> 299
     //   280: aload 13
     //   282: areturn
     //   283: astore 14
     //   285: aload 11
     //   287: monitorexit
     //   288: aload 14
     //   290: athrow
     //   291: astore 15
     //   293: jsr +6 -> 299
     //   296: aload 15
     //   298: athrow
     //   299: astore 16
     //   301: aload_0
     //   302: aconst_null
     //   303: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   306: ret 16
     //
     // Exception table:
     //   from	to	target	type
     //   187	207	283	finally
     //   213	277	283	finally
     //   283	288	283	finally
     //   6	178	291	finally
     //   181	210	291	finally
     //   213	280	291	finally
     //   283	296	291	finally } 
   // ERROR //
   public final Connection[] connectPredictive(LucentTerminal originatorTerminal, LucentAddress origAddress, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType, boolean priorityCall, UserToUserInfo userInfo) throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException { return null;}// Byte code:
     //   0: ldc 192
     //   2: aload_0
     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
     //   6: aload_2
     //   7: ifnonnull +15 -> 22
     //   10: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
     //   13: dup
     //   14: iconst_3
     //   15: iconst_0
     //   16: ldc 193
     //   18: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
     //   21: athrow
     //   22: aconst_null
     //   23: astore 10
     //   25: aload_2
     //   26: checkcast 28	com/avaya/jtapi/tsapi/impl/TsapiAddress
     //   29: invokevirtual 29	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
     //   32: astore 11
     //   34: aconst_null
     //   35: astore 12
     //   37: aload_1
     //   38: ifnull +12 -> 50
     //   41: aload_1
     //   42: checkcast 30	com/avaya/jtapi/tsapi/impl/TsapiTerminal
     //   45: invokevirtual 31	com/avaya/jtapi/tsapi/impl/TsapiTerminal:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
     //   48: astore 12
     //   50: aload 11
     //   52: ifnull +73 -> 125
     //   55: aload 12
     //   57: ifnull +25 -> 82
     //   60: aload 11
     //   62: aload 12
     //   64: invokevirtual 32	java/lang/Object:equals	(Ljava/lang/Object;)Z
     //   67: ifne +15 -> 82
     //   70: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
     //   73: dup
     //   74: iconst_3
     //   75: iconst_0
     //   76: ldc 194
     //   78: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
     //   81: athrow
     //   82: aload_0
     //   83: aload_0
     //   84: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   87: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   90: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   93: aload_0
     //   94: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   97: aload 11
     //   99: aload_3
     //   100: iload 4
     //   102: iload 5
     //   104: iload 6
     //   106: iload 7
     //   108: aconst_null
     //   109: iload 8
     //   111: aload 9
     //   113: aload_0
     //   114: getfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   117: invokevirtual 191	com/avaya/jtapi/tsapi/impl/core/TSCall:connectPredictive	(Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Ljava/lang/String;IIIILjava/lang/String;ZLcom/avaya/jtapi/tsapi/UserToUserInfo;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Ljava/util/Vector;
     //   120: astore 10
     //   122: goto +15 -> 137
     //   125: new 16	com/avaya/jtapi/tsapi/TsapiPlatformException
     //   128: dup
     //   129: iconst_4
     //   130: iconst_0
     //   131: ldc 35
     //   133: invokespecial 18	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
     //   136: athrow
     //   137: aload 10
     //   139: ifnonnull +18 -> 157
     //   142: ldc 192
     //   144: aload_0
     //   145: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   148: aconst_null
     //   149: astore 13
     //   151: jsr +124 -> 275
     //   154: aload 13
     //   156: areturn
     //   157: aload 10
     //   159: dup
     //   160: astore 13
     //   162: monitorenter
     //   163: aload 10
     //   165: invokevirtual 7	java/util/Vector:size	()I
     //   168: ifne +21 -> 189
     //   171: ldc 192
     //   173: aload_0
     //   174: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   177: aconst_null
     //   178: astore 14
     //   180: aload 13
     //   182: monitorexit
     //   183: jsr +92 -> 275
     //   186: aload 14
     //   188: areturn
     //   189: aload 10
     //   191: invokevirtual 7	java/util/Vector:size	()I
     //   194: anewarray 8	javax/telephony/Connection
     //   197: astore 14
     //   199: iconst_0
     //   200: istore 15
     //   202: iload 15
     //   204: aload 10
     //   206: invokevirtual 7	java/util/Vector:size	()I
     //   209: if_icmpge +31 -> 240
     //   212: aload 14
     //   214: iload 15
     //   216: aload 10
     //   218: iload 15
     //   220: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
     //   223: checkcast 10	com/avaya/jtapi/tsapi/impl/core/TSConnection
     //   226: iconst_1
     //   227: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
     //   230: checkcast 8	javax/telephony/Connection
     //   233: aastore
     //   234: iinc 15 1
     //   237: goto -35 -> 202
     //   240: ldc 192
     //   242: aload_0
     //   243: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   246: aload 14
     //   248: astore 15
     //   250: aload 13
     //   252: monitorexit
     //   253: jsr +22 -> 275
     //   256: aload 15
     //   258: areturn
     //   259: astore 16
     //   261: aload 13
     //   263: monitorexit
     //   264: aload 16
     //   266: athrow
     //   267: astore 17
     //   269: jsr +6 -> 275
     //   272: aload 17
     //   274: athrow
     //   275: astore 18
     //   277: aload_0
     //   278: aconst_null
     //   279: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   282: ret 18
     //
     // Exception table:
     //   from	to	target	type
     //   163	183	259	finally
     //   189	253	259	finally
     //   259	264	259	finally
     //   6	154	267	finally
     //   157	186	267	finally
     //   189	256	267	finally
     //   259	272	267	finally } 
   public final void setApplicationData(Object data) throws TsapiMethodNotSupportedException { TsapiTrace.traceEntry("setApplicationData[Object data]", this);
     try
     {
       throw new TsapiMethodNotSupportedException(0, 0, "unsupported by implementation");
     }
     finally
     {
       this.privData = null;
     } }
 
 
   public final Object getApplicationData()
     throws TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("getApplicationData[]", this);
     try
     {
       throw new TsapiMethodNotSupportedException(0, 0, "unsupported by implementation");
     }
     finally
     {
       this.privData = null; }  } 
   // ERROR //
   public final CallCenterTrunk[] getTrunks() { return null;}// Byte code:
     //   0: ldc 197
     //   2: aload_0
     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
     //   6: aconst_null
     //   7: astore_1
     //   8: aload_0
     //   9: aload_0
     //   10: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   13: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   16: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   19: aload_0
     //   20: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   23: invokevirtual 198	com/avaya/jtapi/tsapi/impl/core/TSCall:getTSTrunks	()Ljava/util/Vector;
     //   26: astore_1
     //   27: aload_1
     //   28: ifnonnull +16 -> 44
     //   31: ldc 197
     //   33: aload_0
     //   34: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   37: aconst_null
     //   38: astore_2
     //   39: jsr +106 -> 145
     //   42: aload_2
     //   43: areturn
     //   44: aload_1
     //   45: dup
     //   46: astore_2
     //   47: monitorenter
     //   48: aload_1
     //   49: invokevirtual 7	java/util/Vector:size	()I
     //   52: ifne +18 -> 70
     //   55: ldc 197
     //   57: aload_0
     //   58: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   61: aconst_null
     //   62: astore_3
     //   63: aload_2
     //   64: monitorexit
     //   65: jsr +80 -> 145
     //   68: aload_3
     //   69: areturn
     //   70: aload_1
     //   71: invokevirtual 7	java/util/Vector:size	()I
     //   74: anewarray 199	com/avaya/jtapi/tsapi/TsapiTrunk
     //   77: astore_3
     //   78: iconst_0
     //   79: istore 4
     //   81: iload 4
     //   83: aload_1
     //   84: invokevirtual 7	java/util/Vector:size	()I
     //   87: if_icmpge +26 -> 113
     //   90: aload_3
     //   91: iload 4
     //   93: aload_1
     //   94: iload 4
     //   96: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
     //   99: iconst_0
     //   100: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
     //   103: checkcast 199	com/avaya/jtapi/tsapi/TsapiTrunk
     //   106: aastore
     //   107: iinc 4 1
     //   110: goto -29 -> 81
     //   113: ldc 197
     //   115: aload_0
     //   116: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   119: aload_3
     //   120: astore 4
     //   122: aload_2
     //   123: monitorexit
     //   124: jsr +21 -> 145
     //   127: aload 4
     //   129: areturn
     //   130: astore 5
     //   132: aload_2
     //   133: monitorexit
     //   134: aload 5
     //   136: athrow
     //   137: astore 6
     //   139: jsr +6 -> 145
     //   142: aload 6
     //   144: athrow
     //   145: astore 7
     //   147: aload_0
     //   148: aconst_null
     //   149: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   152: ret 7
     //
     // Exception table:
     //   from	to	target	type
     //   48	65	130	finally
     //   70	124	130	finally
     //   130	134	130	finally
     //   6	42	137	finally
     //   44	68	137	finally
     //   70	127	137	finally
     //   130	142	137	finally } 
   public final CallCenterTrunk getTrunk() { TsapiTrace.traceEntry("getTrunk[]", this);
     try
     {
       CallCenterTrunk[] trks = getTrunks();
       if ((trks == null) || (trks.length == 0))
       {
         TsapiTrace.traceExit("getTrunk[]", this);
         return null;
       }
       CallCenterTrunk trunk = trks[0];
       TsapiTrace.traceExit("getTrunk[]", this);
       return trunk;
     }
     finally
     {
       this.privData = null;
     } }
 
 
   public final CallCenterAddress getDistributingAddress()
   {
     TsapiTrace.traceEntry("getDistributingAddress[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       TSDevice tsDevice = this.tsCall.getDistributingDevice();
       if (tsDevice == null)
       {
         TsapiTrace.traceExit("getDistributingAddress[]", this);
         return null;
       }
       CallCenterAddress addr = (CallCenterAddress)TsapiCreateObject.getTsapiObject(tsDevice, true);
       TsapiTrace.traceExit("getDistributingAddress[]", this);
       return addr;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final CallCenterAddress getDistributingVDNAddress()
   {
     TsapiTrace.traceEntry("getDistributingVDNAddress[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       TSDevice tsDevice = this.tsCall.getDistributingVDN();
       if (tsDevice == null)
       {
         TsapiTrace.traceExit("getDistributingVDNAddress[]", this);
         return null;
       }
       CallCenterAddress addr = (CallCenterAddress)TsapiCreateObject.getTsapiObject(tsDevice, true);
       TsapiTrace.traceExit("getDistributingVDNAddress[]", this);
       return addr;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final ACDAddress getDeliveringACDAddress()
   {
     TsapiTrace.traceEntry("getDeliveringACDAddress[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       TSDevice tsDevice = this.tsCall.getDeliveringACDDevice();
       if (tsDevice == null)
       {
         TsapiTrace.traceExit("getDeliveringACDAddress[]", this);
         return null;
       }
       ACDAddress addr = (ACDAddress)TsapiCreateObject.getTsapiObject(tsDevice, true);
       TsapiTrace.traceExit("getDeliveringACDAddress[]", this);
       return addr;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final UserToUserInfo getUserToUserInfo()
   {
     TsapiTrace.traceEntry("getUserToUserInfo[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       UserToUserInfo uui = this.tsCall.getUUI();
       TsapiTrace.traceExit("getUserToUserInfo[]", this);
       return uui;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final LookaheadInfo getLookaheadInfo()
   {
     TsapiTrace.traceEntry("getLookaheadInfo[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       LookaheadInfo lai = this.tsCall.getLAI();
       TsapiTrace.traceExit("getLookaheadInfo[]", this);
       return lai;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final UserEnteredCode getUserEnteredCode()
   {
     TsapiTrace.traceEntry("getUserEnteredCode[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       UserEnteredCode uec = this.tsCall.getUEC();
       TsapiTrace.traceExit("getUserEnteredCode[]", this);
       return uec;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final OriginalCallInfo getOriginalCallInfo()
   {
     TsapiTrace.traceEntry("getOriginalCallInfo[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       OriginalCallInfo oci = this.tsCall.getOCI();
       TsapiTrace.traceExit("getOriginalCallInfo[]", this);
       return oci;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final short getReason()
   {
     TsapiTrace.traceEntry("getReason[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       short reason = this.tsCall.getReason();
       TsapiTrace.traceExit("getReason[]", this);
       return reason;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final String getUCID()
   {
     TsapiTrace.traceEntry("getUCID[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       String ucid = this.tsCall.getUCID();
       TsapiTrace.traceExit("getUCID[]", this);
       return ucid;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final int getCallOriginatorType()
   {
     TsapiTrace.traceEntry("getCallOriginatorType[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       int type = this.tsCall.getCallOriginatorType();
       TsapiTrace.traceExit("getCallOriginatorType[]", this);
       return type;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final boolean hasCallOriginatorType()
   {
     TsapiTrace.traceEntry("hasCallOriginatorType[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       boolean has = this.tsCall.hasCallOriginatorType();
       TsapiTrace.traceExit("hasCallOriginatorType[]", this);
       return has;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final boolean canSetBillRate()
   {
     TsapiTrace.traceEntry("canSetBillRate[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       boolean can = this.tsCall.canSetBillRate();
       TsapiTrace.traceExit("canSetBillRate[]", this);
       return can;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final void setBillRate(short billType, float billRate)
     throws TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("setBillRate[short billType, float billRate]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       this.tsCall.setBillRate(billType, billRate);
     }
     finally
     {
       this.privData = null;
     }
     TsapiTrace.traceExit("setBillRate[short billType, float billRate]", this);
   }
 
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
     this.tsCall = this.tsCall.getHandOff();
     Object obj = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate)this.tsCall.getPrivateData());
     TsapiTrace.traceExit("getPrivateData[]", this);
     return obj;
   }
 
   public final Object sendPrivateData(Object data)
   {
     TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       Object obj = this.tsCall.sendPrivateData(TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data));
       TsapiTrace.traceExit("sendPrivateData[Object data]", this);
       return obj;
     }
     catch (ClassCastException e)
     {
       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
     }
   }
 
   public final int getTsapiCallID()
   {
     TsapiTrace.traceEntry("getTsapiCallID[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       int id = this.tsCall.getCallID();
       TsapiTrace.traceExit("getTsapiCallID[]", this);
       return id;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public void addCallListener(CallListener listener)
     throws ResourceUnavailableException
   {
     TsapiTrace.traceEntry("addCallListener(CallListener listener)", this);
     try {
       addTsapiCallEventMonitor(null, listener);
     } catch (Exception e) {
       log.error(e.getMessage(), e);
     }
     TsapiTrace.traceExit("addCallListener(CallListener listener)", this);
   }
 
   public CallListener[] getCallListeners() {
     TsapiTrace.traceEntry("getCallListeners[]", this);
     try {
       this.tsCall = this.tsCall.getHandOff();
       Vector tsapiCallObservers = this.tsCall.getCallObservers();
 
       if ((tsapiCallObservers == null) || (tsapiCallObservers.size() == 0))
       {
         TsapiTrace.traceExit("getCallListeners[]", this);
         return null;
       }
       ArrayList callListeners = new ArrayList();
 
       synchronized (tsapiCallObservers) {
         for (Object obs : tsapiCallObservers) {
           CallListener listener = ((TsapiCallMonitor) obs).getListener();
           if (listener != null)
             callListeners.add(listener);
         }
       }
       CallListener[] callListener = new CallListener[callListeners.size()];
       TsapiTrace.traceExit("getCallListeners[]", this);
       return (CallListener[])callListeners.toArray(callListener);
     } finally {
       this.privData = null;
     }
   }
 
   public void removeCallListener(CallListener listener) {
     TsapiTrace.traceEntry("removeCallListener[CallListener listener]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       Vector tsapiCallObservers = this.tsCall.getCallObservers();
 
       if ((tsapiCallObservers == null) || (tsapiCallObservers.size() == 0))
       {
         TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
 
         return;
       }
 
       for (Object obs : tsapiCallObservers)
         if (((TsapiCallMonitor) obs).getListener() == listener) {
           this.tsCall = this.tsCall.getHandOff();
           this.tsCall.removeCallMonitor((TsapiCallMonitor) obs);
           TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
 
           return;
         }
     }
     finally {
       this.privData = null;
     }
   }
 
   public V7DeviceHistoryEntry[] getDeviceHistory()
   {
     TsapiTrace.traceEntry("getDeviceHistory[]", this);
     try
     {
       this.tsCall = this.tsCall.getHandOff();
       V7DeviceHistoryEntry[] history = this.tsCall.getDeviceHistory();
       TsapiTrace.traceExit("getDeviceHistory[]", this);
       return history;
     }
     finally
     {
       this.privData = null;
     }
   }
 
   public final int hashCode()
   {
     this.tsCall = this.tsCall.getHandOff();
 
     TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
     return TSProviderImpl.hashCode();
   }
 
   public boolean equals(Object obj)
   {
     if (obj instanceof TsapiCall)
     {
       this.tsCall = this.tsCall.getHandOff();
       return this.tsCall.equals(((TsapiCall)obj).getTSCall());
     }
 
     return false;
   }
 
   TsapiCall(TsapiProvider _provider)
   {
     this(_provider, 0);
   }
 
   TsapiCall(TsapiProvider _provider, CSTAConnectionID connID)
   {
     this(_provider, connID.getCallID());
   }
 
   TsapiCall(TsapiProvider _provider, int callID)
   {
     TSProviderImpl tsProv = _provider.getTSProviderImpl();
     if (tsProv != null)
     {
       this.tsCall = tsProv.createTSCall(callID);
       if (this.tsCall == null)
       {
         throw new TsapiPlatformException(4, 0, "could not create call");
       }
     }
     else
     {
       throw new TsapiPlatformException(4, 0, "could not locate provider");
     }
     this.tsCall.referenced();
     TsapiTrace.traceConstruction(this, TsapiCall.class);
   }
 
   TsapiCall(TSProviderImpl _provider, CSTAConnectionID connID)
   {
     this.tsCall = _provider.createTSCall(connID.getCallID());
     if (this.tsCall == null)
     {
       throw new TsapiPlatformException(4, 0, "could not create call");
     }
 
     this.tsCall.referenced();
     TsapiTrace.traceConstruction(this, TsapiCall.class);
   }
 
   TsapiCall(TSCall _tscall)
   {
     _tscall = _tscall.getHandOff();
     this.tsCall = _tscall;
     this.tsCall.referenced();
     TsapiTrace.traceConstruction(this, TsapiCall.class);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     if (this.tsCall != null)
     {
       this.tsCall.unreferenced();
       this.tsCall = null;
     }
     TsapiTrace.traceDestruction(this, TsapiCall.class);
   }
 
   public final TSCall getTSCall()
   {
     TsapiTrace.traceEntry("getTSCall[]", this);
     this.tsCall = this.tsCall.getHandOff();
     TsapiTrace.traceExit("getTSCall[]", this);
     return this.tsCall;
   }
 
   // ERROR //
   public final Connection fastConnect(Terminal origterm, Address origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo, String destRoute)
     throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException
   {return null;
     // Byte code:
     //   0: ldc_w 263
     //   3: aload_0
     //   4: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
     //   7: aload_1
     //   8: instanceof 22
     //   11: ifne +15 -> 26
     //   14: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
     //   17: dup
     //   18: iconst_3
     //   19: iconst_0
     //   20: ldc 24
     //   22: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
     //   25: athrow
     //   26: aload_2
     //   27: instanceof 26
     //   30: ifne +15 -> 45
     //   33: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
     //   36: dup
     //   37: iconst_3
     //   38: iconst_0
     //   39: ldc 27
     //   41: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
     //   44: athrow
     //   45: aconst_null
     //   46: astore 7
     //   48: aload_2
     //   49: checkcast 28	com/avaya/jtapi/tsapi/impl/TsapiAddress
     //   52: invokevirtual 29	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
     //   55: astore 8
     //   57: aload_1
     //   58: checkcast 30	com/avaya/jtapi/tsapi/impl/TsapiTerminal
     //   61: invokevirtual 31	com/avaya/jtapi/tsapi/impl/TsapiTerminal:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
     //   64: astore 9
     //   66: aload 8
     //   68: ifnull +104 -> 172
     //   71: aload 9
     //   73: ifnull +99 -> 172
     //   76: aload 8
     //   78: aload 9
     //   80: invokevirtual 32	java/lang/Object:equals	(Ljava/lang/Object;)Z
     //   83: ifeq +77 -> 160
     //   86: aload 6
     //   88: ifnonnull +21 -> 109
     //   91: iload 4
     //   93: ifne +16 -> 109
     //   96: aload 5
     //   98: ifnonnull +11 -> 109
     //   101: aload_0
     //   102: aconst_null
     //   103: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   106: goto +24 -> 130
     //   109: aload_0
     //   110: aload 6
     //   112: iload 4
     //   114: aload 5
     //   116: invokespecial 46	com/avaya/jtapi/tsapi/impl/TsapiCall:createLucentMakeCall	(Ljava/lang/String;ZLcom/avaya/jtapi/tsapi/UserToUserInfo;)Lcom/avaya/jtapi/tsapi/csta1/LucentMakeCall;
     //   119: astore 10
     //   121: aload_0
     //   122: aload 10
     //   124: invokevirtual 47	com/avaya/jtapi/tsapi/csta1/LucentMakeCall:makeTsapiPrivate	()Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   127: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   130: aload_0
     //   131: aload_0
     //   132: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   135: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   138: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   141: aload_0
     //   142: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
     //   145: aload 8
     //   147: aload_3
     //   148: aload_0
     //   149: getfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   152: invokevirtual 264	com/avaya/jtapi/tsapi/impl/core/TSCall:fastConnect	(Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Ljava/lang/String;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Ljava/util/Vector;
     //   155: astore 7
     //   157: goto +27 -> 184
     //   160: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
     //   163: dup
     //   164: iconst_3
     //   165: iconst_0
     //   166: ldc 34
     //   168: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
     //   171: athrow
     //   172: new 16	com/avaya/jtapi/tsapi/TsapiPlatformException
     //   175: dup
     //   176: iconst_4
     //   177: iconst_0
     //   178: ldc 35
     //   180: invokespecial 18	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
     //   183: athrow
     //   184: aload 7
     //   186: ifnonnull +19 -> 205
     //   189: ldc_w 263
     //   192: aload_0
     //   193: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   196: aconst_null
     //   197: astore 10
     //   199: jsr +93 -> 292
     //   202: aload 10
     //   204: areturn
     //   205: aload 7
     //   207: dup
     //   208: astore 10
     //   210: monitorenter
     //   211: aload 7
     //   213: invokevirtual 7	java/util/Vector:size	()I
     //   216: ifne +22 -> 238
     //   219: ldc_w 263
     //   222: aload_0
     //   223: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   226: aconst_null
     //   227: astore 11
     //   229: aload 10
     //   231: monitorexit
     //   232: jsr +60 -> 292
     //   235: aload 11
     //   237: areturn
     //   238: aload 7
     //   240: iconst_0
     //   241: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
     //   244: checkcast 10	com/avaya/jtapi/tsapi/impl/core/TSConnection
     //   247: iconst_1
     //   248: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
     //   251: checkcast 8	javax/telephony/Connection
     //   254: astore 11
     //   256: ldc_w 263
     //   259: aload_0
     //   260: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
     //   263: aload 11
     //   265: astore 12
     //   267: aload 10
     //   269: monitorexit
     //   270: jsr +22 -> 292
     //   273: aload 12
     //   275: areturn
     //   276: astore 13
     //   278: aload 10
     //   280: monitorexit
     //   281: aload 13
     //   283: athrow
     //   284: astore 14
     //   286: jsr +6 -> 292
     //   289: aload 14
     //   291: athrow
     //   292: astore 15
     //   294: aload_0
     //   295: aconst_null
     //   296: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
     //   299: ret 15
     //
     // Exception table:
     //   from	to	target	type
     //   211	232	276	finally
     //   238	270	276	finally
     //   276	281	276	finally
     //   7	202	284	finally
     //   205	235	284	finally
     //   238	273	284	finally
     //   276	289	284	finally
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiCall
 * JD-Core Version:    0.5.4
 */