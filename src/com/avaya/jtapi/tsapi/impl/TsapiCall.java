/*      */ package com.avaya.jtapi.tsapi.impl;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.ITsapiCall;
/*      */ import com.avaya.jtapi.tsapi.ITsapiCallIDPrivate;
/*      */ import com.avaya.jtapi.tsapi.ITsapiTerminalConnection;
/*      */ import com.avaya.jtapi.tsapi.LookaheadInfo;
/*      */ import com.avaya.jtapi.tsapi.LucentAddress;
/*      */ import com.avaya.jtapi.tsapi.LucentAgent;
/*      */ import com.avaya.jtapi.tsapi.LucentTerminal;
/*      */ import com.avaya.jtapi.tsapi.LucentTerminalConnection;
/*      */ import com.avaya.jtapi.tsapi.LucentV7Call;
/*      */ import com.avaya.jtapi.tsapi.OriginalCallInfo;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
/*      */ import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPrivate;
/*      */ import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
/*      */ import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
/*      */ import com.avaya.jtapi.tsapi.UserEnteredCode;
/*      */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*      */ import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentConsultationCall;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentDirectAgentCall;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentMakeCall;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentSupervisorAssistCall;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV6ConsultationCall;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV6DirectAgentCall;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV6MakeCall;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV6SupervisorAssistCall;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSCall;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import javax.telephony.Address;
/*      */ import javax.telephony.Call;
/*      */ import javax.telephony.CallListener;
/*      */ import javax.telephony.CallObserver;
/*      */ import javax.telephony.Connection;
/*      */ import javax.telephony.InvalidArgumentException;
/*      */ import javax.telephony.PlatformException;
/*      */ import javax.telephony.ResourceUnavailableException;
/*      */ import javax.telephony.Terminal;
/*      */ import javax.telephony.TerminalConnection;
/*      */ import javax.telephony.callcenter.ACDAddress;
/*      */ import javax.telephony.callcenter.CallCenterAddress;
/*      */ import javax.telephony.callcenter.CallCenterTrunk;
/*      */ import javax.telephony.capabilities.CallCapabilities;
/*      */ import javax.telephony.privatedata.PrivateData;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class TsapiCall
/*      */   implements ITsapiCall, PrivateData, ITsapiCallIDPrivate, LucentV7Call
/*      */ {
/*   74 */   private static Logger log = Logger.getLogger(TsapiCall.class);
/*      */   TSCall tsCall;
/* 1955 */   CSTAPrivate privData = null;
/*      */ 
/*      */   // ERROR //
/*      */   public final Connection[] getConnections()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: ldc 1
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aconst_null
/*      */     //   7: astore_1
/*      */     //   8: aload_0
/*      */     //   9: aload_0
/*      */     //   10: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   13: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   16: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   19: aload_0
/*      */     //   20: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   23: invokevirtual 5	com/avaya/jtapi/tsapi/impl/core/TSCall:getTSConnections	()Ljava/util/Vector;
/*      */     //   26: astore_1
/*      */     //   27: aload_1
/*      */     //   28: ifnonnull +16 -> 44
/*      */     //   31: ldc 1
/*      */     //   33: aload_0
/*      */     //   34: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   37: aconst_null
/*      */     //   38: astore_2
/*      */     //   39: jsr +109 -> 148
/*      */     //   42: aload_2
/*      */     //   43: areturn
/*      */     //   44: aload_1
/*      */     //   45: dup
/*      */     //   46: astore_2
/*      */     //   47: monitorenter
/*      */     //   48: aload_1
/*      */     //   49: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   52: ifne +18 -> 70
/*      */     //   55: ldc 1
/*      */     //   57: aload_0
/*      */     //   58: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   61: aconst_null
/*      */     //   62: astore_3
/*      */     //   63: aload_2
/*      */     //   64: monitorexit
/*      */     //   65: jsr +83 -> 148
/*      */     //   68: aload_3
/*      */     //   69: areturn
/*      */     //   70: aload_1
/*      */     //   71: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   74: anewarray 8	javax/telephony/Connection
/*      */     //   77: astore_3
/*      */     //   78: iconst_0
/*      */     //   79: istore 4
/*      */     //   81: iload 4
/*      */     //   83: aload_1
/*      */     //   84: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   87: if_icmpge +29 -> 116
/*      */     //   90: aload_3
/*      */     //   91: iload 4
/*      */     //   93: aload_1
/*      */     //   94: iload 4
/*      */     //   96: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
/*      */     //   99: checkcast 10	com/avaya/jtapi/tsapi/impl/core/TSConnection
/*      */     //   102: iconst_1
/*      */     //   103: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   106: checkcast 8	javax/telephony/Connection
/*      */     //   109: aastore
/*      */     //   110: iinc 4 1
/*      */     //   113: goto -32 -> 81
/*      */     //   116: ldc 1
/*      */     //   118: aload_0
/*      */     //   119: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   122: aload_3
/*      */     //   123: astore 4
/*      */     //   125: aload_2
/*      */     //   126: monitorexit
/*      */     //   127: jsr +21 -> 148
/*      */     //   130: aload 4
/*      */     //   132: areturn
/*      */     //   133: astore 5
/*      */     //   135: aload_2
/*      */     //   136: monitorexit
/*      */     //   137: aload 5
/*      */     //   139: athrow
/*      */     //   140: astore 6
/*      */     //   142: jsr +6 -> 148
/*      */     //   145: aload 6
/*      */     //   147: athrow
/*      */     //   148: astore 7
/*      */     //   150: aload_0
/*      */     //   151: aconst_null
/*      */     //   152: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   155: ret 7
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   48	65	133	finally
/*      */     //   70	127	133	finally
/*      */     //   133	137	133	finally
/*      */     //   6	42	140	finally
/*      */     //   44	68	140	finally
/*      */     //   70	130	140	finally
/*      */     //   133	145	140	finally
/*      */   }
/*      */ 
/*      */   // ERROR //
/*      */   public final javax.telephony.Provider getProvider()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: ldc 13
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_0
/*      */     //   7: aload_0
/*      */     //   8: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   11: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   14: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   17: aload_0
/*      */     //   18: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   21: invokevirtual 14	com/avaya/jtapi/tsapi/impl/core/TSCall:getTSProviderImpl	()Lcom/avaya/jtapi/tsapi/impl/core/TSProviderImpl;
/*      */     //   24: astore_1
/*      */     //   25: aload_1
/*      */     //   26: ifnull +25 -> 51
/*      */     //   29: aload_1
/*      */     //   30: iconst_0
/*      */     //   31: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   34: checkcast 15	javax/telephony/Provider
/*      */     //   37: astore_2
/*      */     //   38: ldc 13
/*      */     //   40: aload_0
/*      */     //   41: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   44: aload_2
/*      */     //   45: astore_3
/*      */     //   46: jsr +25 -> 71
/*      */     //   49: aload_3
/*      */     //   50: areturn
/*      */     //   51: new 16	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   54: dup
/*      */     //   55: iconst_4
/*      */     //   56: iconst_0
/*      */     //   57: ldc 17
/*      */     //   59: invokespecial 18	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   62: athrow
/*      */     //   63: astore 4
/*      */     //   65: jsr +6 -> 71
/*      */     //   68: aload 4
/*      */     //   70: athrow
/*      */     //   71: astore 5
/*      */     //   73: aload_0
/*      */     //   74: aconst_null
/*      */     //   75: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   78: ret 5
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   6	49	63	finally
/*      */     //   51	68	63	finally
/*      */   }
/*      */ 
/*      */   public final int getState()
/*      */   {
/*  152 */     TsapiTrace.traceEntry("getState[]", this);
/*      */     try
/*      */     {
/*  155 */       this.tsCall = this.tsCall.getHandOff();
/*  156 */       int state = this.tsCall.getState();
/*  157 */       TsapiTrace.traceExit("getState[]", this);
/*  158 */       return state;
/*      */     }
/*      */     finally
/*      */     {
/*  162 */       this.privData = null; }  } 
/*      */   // ERROR //
/*      */   public final Connection[] connect(Terminal origterm, Address origaddr, String dialedDigits) throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException { // Byte code:
/*      */     //   0: ldc 21
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_1
/*      */     //   7: instanceof 22
/*      */     //   10: ifne +15 -> 25
/*      */     //   13: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   16: dup
/*      */     //   17: iconst_3
/*      */     //   18: iconst_0
/*      */     //   19: ldc 24
/*      */     //   21: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   24: athrow
/*      */     //   25: aload_2
/*      */     //   26: instanceof 26
/*      */     //   29: ifne +15 -> 44
/*      */     //   32: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   35: dup
/*      */     //   36: iconst_3
/*      */     //   37: iconst_0
/*      */     //   38: ldc 27
/*      */     //   40: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   43: athrow
/*      */     //   44: aconst_null
/*      */     //   45: astore 4
/*      */     //   47: aload_2
/*      */     //   48: checkcast 28	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   51: invokevirtual 29	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   54: astore 5
/*      */     //   56: aload_1
/*      */     //   57: checkcast 30	com/avaya/jtapi/tsapi/impl/TsapiTerminal
/*      */     //   60: invokevirtual 31	com/avaya/jtapi/tsapi/impl/TsapiTerminal:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   63: astore 6
/*      */     //   65: aload 5
/*      */     //   67: ifnull +60 -> 127
/*      */     //   70: aload 6
/*      */     //   72: ifnull +55 -> 127
/*      */     //   75: aload 5
/*      */     //   77: aload 6
/*      */     //   79: invokevirtual 32	java/lang/Object:equals	(Ljava/lang/Object;)Z
/*      */     //   82: ifeq +33 -> 115
/*      */     //   85: aload_0
/*      */     //   86: aload_0
/*      */     //   87: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   90: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   93: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   96: aload_0
/*      */     //   97: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   100: aload 5
/*      */     //   102: aload_3
/*      */     //   103: aload_0
/*      */     //   104: getfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   107: invokevirtual 33	com/avaya/jtapi/tsapi/impl/core/TSCall:connect	(Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Ljava/lang/String;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Ljava/util/Vector;
/*      */     //   110: astore 4
/*      */     //   112: goto +27 -> 139
/*      */     //   115: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   118: dup
/*      */     //   119: iconst_3
/*      */     //   120: iconst_0
/*      */     //   121: ldc 34
/*      */     //   123: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   126: athrow
/*      */     //   127: new 16	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   130: dup
/*      */     //   131: iconst_4
/*      */     //   132: iconst_0
/*      */     //   133: ldc 35
/*      */     //   135: invokespecial 18	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   138: athrow
/*      */     //   139: aload 4
/*      */     //   141: ifnonnull +18 -> 159
/*      */     //   144: ldc 21
/*      */     //   146: aload_0
/*      */     //   147: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   150: aconst_null
/*      */     //   151: astore 7
/*      */     //   153: jsr +124 -> 277
/*      */     //   156: aload 7
/*      */     //   158: areturn
/*      */     //   159: aload 4
/*      */     //   161: dup
/*      */     //   162: astore 7
/*      */     //   164: monitorenter
/*      */     //   165: aload 4
/*      */     //   167: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   170: ifne +21 -> 191
/*      */     //   173: ldc 21
/*      */     //   175: aload_0
/*      */     //   176: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   179: aconst_null
/*      */     //   180: astore 8
/*      */     //   182: aload 7
/*      */     //   184: monitorexit
/*      */     //   185: jsr +92 -> 277
/*      */     //   188: aload 8
/*      */     //   190: areturn
/*      */     //   191: aload 4
/*      */     //   193: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   196: anewarray 8	javax/telephony/Connection
/*      */     //   199: astore 8
/*      */     //   201: iconst_0
/*      */     //   202: istore 9
/*      */     //   204: iload 9
/*      */     //   206: aload 4
/*      */     //   208: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   211: if_icmpge +31 -> 242
/*      */     //   214: aload 8
/*      */     //   216: iload 9
/*      */     //   218: aload 4
/*      */     //   220: iload 9
/*      */     //   222: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
/*      */     //   225: checkcast 10	com/avaya/jtapi/tsapi/impl/core/TSConnection
/*      */     //   228: iconst_1
/*      */     //   229: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   232: checkcast 8	javax/telephony/Connection
/*      */     //   235: aastore
/*      */     //   236: iinc 9 1
/*      */     //   239: goto -35 -> 204
/*      */     //   242: ldc 21
/*      */     //   244: aload_0
/*      */     //   245: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   248: aload 8
/*      */     //   250: astore 9
/*      */     //   252: aload 7
/*      */     //   254: monitorexit
/*      */     //   255: jsr +22 -> 277
/*      */     //   258: aload 9
/*      */     //   260: areturn
/*      */     //   261: astore 10
/*      */     //   263: aload 7
/*      */     //   265: monitorexit
/*      */     //   266: aload 10
/*      */     //   268: athrow
/*      */     //   269: astore 11
/*      */     //   271: jsr +6 -> 277
/*      */     //   274: aload 11
/*      */     //   276: athrow
/*      */     //   277: astore 12
/*      */     //   279: aload_0
/*      */     //   280: aconst_null
/*      */     //   281: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   284: ret 12
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   165	185	261	finally
/*      */     //   191	255	261	finally
/*      */     //   261	266	261	finally
/*      */     //   6	156	269	finally
/*      */     //   159	188	269	finally
/*      */     //   191	258	269	finally
/*      */     //   261	274	269	finally } 
/*  239 */   private LucentMakeCall createLucentMakeCall(String destRoute, boolean priorityCall, UserToUserInfo userInfo) { TsapiTrace.traceEntry("createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]", this);
/*  240 */     TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
/*  241 */     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(userInfo);
/*      */ 
/*  244 */     if (TSProviderImpl != null)
/*      */     {
/*  246 */       if (TSProviderImpl.isLucentV6()) {
/*  247 */         LucentV6MakeCall call = new LucentV6MakeCall(destRoute, priorityCall, asn_uui);
/*  248 */         TsapiTrace.traceExit("createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]", this);
/*  249 */         return call;
/*      */       }
/*      */ 
/*  252 */       LucentMakeCall call = new LucentMakeCall(destRoute, priorityCall, asn_uui);
/*  253 */       TsapiTrace.traceExit("createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]", this);
/*  254 */       return call;
/*      */     }
/*      */ 
/*  258 */     TsapiTrace.traceExit("createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]", this);
/*  259 */     return null; }
/*      */ 
/*      */ 
/*      */   public final Connection[] connect(LucentTerminal origterm, LucentAddress origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo)
/*      */     throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException
/*      */   {
/*  269 */     TsapiTrace.traceEntry("connect[LucentTerminal origterm, LucentAddress origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo]", this);
/*  270 */     if (origterm == null) {
/*  271 */       throw new TsapiInvalidArgumentException(3, 0, "orig Terminal is null");
/*      */     }
/*      */ 
/*  274 */     if (origaddr == null) {
/*  275 */       throw new TsapiInvalidArgumentException(3, 0, "orig Address is null");
/*      */     }
/*      */ 
/*  278 */     LucentMakeCall lmc = createLucentMakeCall(null, priorityCall, userInfo);
/*  279 */     this.privData = lmc.makeTsapiPrivate();
/*  280 */     Connection[] conns = connect(origterm, origaddr, dialedDigits);
/*  281 */     TsapiTrace.traceExit("connect[LucentTerminal origterm, LucentAddress origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo]", this);
/*  282 */     return conns;
/*      */   }
/*      */ 
/*      */   public final Connection[] connectDirectAgent(LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo)
/*      */     throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException
/*      */   {
/*  291 */     TsapiTrace.traceEntry("connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
/*  292 */     if (calledAgent == null) {
/*  293 */       throw new TsapiInvalidArgumentException(3, 0, "called Agent is null");
/*      */     }
/*      */ 
/*  309 */     if (calledAgent.getACDAddress() != null)
/*      */     {
/*  311 */       LucentDirectAgentCall lda = createLucentDirectAgentCall(calledAgent.getACDAddress().getName(), priorityCall, userInfo);
/*  312 */       this.privData = lda.makeTsapiPrivate();
/*  313 */       Connection[] conns = connect(origterm, origaddr, calledAgent.getAgentAddress().getName());
/*  314 */       TsapiTrace.traceExit("connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
/*  315 */       return conns;
/*      */     }
/*      */ 
/*  319 */     log.info("*****connectDirectAgent: ACDAddress is NULL, using default Skill (ACD)");
/*  320 */     Connection[] conns = connect(origterm, origaddr, calledAgent.getAgentID());
/*  321 */     TsapiTrace.traceExit("connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
/*  322 */     return conns;
/*      */   }
/*      */ 
/*      */   public final Connection[] connectDirectAgent(LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress)
/*      */     throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException
/*      */   {
/*  335 */     TsapiTrace.traceEntry("connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
/*  336 */     if (calledAgent == null) {
/*  337 */       throw new TsapiInvalidArgumentException(3, 0, "called Agent is null");
/*      */     }
/*      */ 
/*  340 */     if (acdaddress != null)
/*      */     {
/*  342 */       LucentDirectAgentCall lda = createLucentDirectAgentCall(acdaddress.getName(), priorityCall, userInfo);
/*  343 */       this.privData = lda.makeTsapiPrivate();
/*  344 */       Connection[] conns = connect(origterm, origaddr, calledAgent.getAgentAddress().getName());
/*  345 */       TsapiTrace.traceExit("connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
/*  346 */       return conns;
/*      */     }
/*      */ 
/*  350 */     Connection[] conns = connectDirectAgent(origterm, origaddr, calledAgent, priorityCall, userInfo);
/*  351 */     TsapiTrace.traceExit("connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
/*  352 */     return conns;
/*      */   }
/*      */ 
/*      */   private LucentSupervisorAssistCall createLucentSupervisorAssistCall(String split, UserToUserInfo userInfo)
/*      */   {
/*  362 */     TsapiTrace.traceEntry("createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]", this);
/*  363 */     TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
/*  364 */     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(userInfo);
/*      */ 
/*  367 */     if (TSProviderImpl != null)
/*      */     {
/*  369 */       if (TSProviderImpl.isLucentV6()) {
/*  370 */         LucentV6SupervisorAssistCall call = new LucentV6SupervisorAssistCall(split, asn_uui);
/*  371 */         TsapiTrace.traceExit("createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]", this);
/*  372 */         return call;
/*      */       }
/*      */ 
/*  375 */       LucentSupervisorAssistCall call = new LucentSupervisorAssistCall(split, asn_uui);
/*  376 */       TsapiTrace.traceExit("createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]", this);
/*  377 */       return call;
/*      */     }
/*      */ 
/*  381 */     TsapiTrace.traceExit("createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]", this);
/*  382 */     return null;
/*      */   }
/*      */ 
/*      */   public final Connection[] connectSupervisorAssist(LucentAgent callingAgent, String dialedDigits, UserToUserInfo userInfo)
/*      */     throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException
/*      */   {
/*  392 */     TsapiTrace.traceEntry("connectSupervisorAssist[LucentAgent callingAgent, String dialedDigits, UserToUserInfo userInfo]", this);
/*  393 */     if (callingAgent == null) {
/*  394 */       throw new TsapiInvalidArgumentException(3, 0, "calling Agent is null");
/*      */     }
/*      */ 
/*  397 */     LucentSupervisorAssistCall lsa = createLucentSupervisorAssistCall(callingAgent.getACDAddress().getName(), userInfo);
/*  398 */     this.privData = lsa.makeTsapiPrivate();
/*  399 */     Connection[] conns = connect(callingAgent.getAgentTerminal(), callingAgent.getAgentAddress(), dialedDigits);
/*  400 */     TsapiTrace.traceExit("connectSupervisorAssist[LucentAgent callingAgent, String dialedDigits, UserToUserInfo userInfo]", this);
/*  401 */     return conns;
/*      */   }
/*      */ 
/*      */   public void addObserver(CallObserver observer)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/*  410 */     TsapiTrace.traceEntry("addObserver[CallObserver observer]", this);
/*      */     try {
/*  412 */       addTsapiCallEventMonitor(observer, null);
/*      */     } catch (Exception e) {
/*  414 */       log.error(e.getMessage(), e);
/*      */     }
/*  416 */     TsapiTrace.traceExit("addObserver[CallObserver observer]", this);
/*      */   }
/*      */ 
/*      */   private void addTsapiCallEventMonitor(CallObserver observer, CallListener listener)
/*      */     throws Exception
/*      */   {
/*  426 */     TsapiTrace.traceEntry("addTsapiCallEventMonitor(CallObserver observer, CallListener listener)", this);
/*  427 */     if ((observer != null) && (listener != null))
/*  428 */       throw new Exception("Invalid call to add event monitor. At a time either a listener or an observer can be added");
/*      */     try
/*      */     {
/*  431 */       this.tsCall = this.tsCall.getHandOff();
/*  432 */       TSProviderImpl prov = this.tsCall.getTSProviderImpl();
/*      */ 
/*  434 */       if (prov == null)
/*      */       {
/*  436 */         throw new TsapiPlatformException(4, 0, "could not locate provider");
/*      */       }
/*      */ 
/*  441 */       Vector observers = prov.getCallMonitorThreads();
/*      */ 
/*  443 */       TsapiCallMonitor obs = null;
/*  444 */       TsapiCallMonitor obsToUse = null;
/*      */ 
/*  446 */       synchronized (observers)
/*      */       {
/*  448 */         for (int i = 0; i < observers.size(); ++i)
/*      */         {
/*  450 */           obs = (TsapiCallMonitor)observers.elementAt(i);
/*  451 */           if (observer != null) {
/*  452 */             if (obs.getObserver() != observer)
/*      */               continue;
/*  454 */             obsToUse = obs;
/*  455 */             break;
/*      */           }
/*  457 */           if ((listener == null) || 
/*  458 */             (obs.getListener() != listener)) continue;
/*  459 */           obsToUse = obs;
/*  460 */           break;
/*      */         }
/*      */ 
/*  465 */         if (obsToUse == null)
/*      */         {
/*  467 */           if (observer != null)
/*  468 */             obsToUse = new TsapiCallMonitor(prov, observer);
/*  469 */           else if (listener != null)
/*  470 */             obsToUse = new TsapiCallMonitor(prov, listener);
/*  471 */           if (obsToUse == null)
/*      */           {
/*  473 */             throw new TsapiPlatformException(4, 0, "could not allocate Monitor wrapper");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  494 */       this.tsCall.addCallMonitor(obsToUse);
/*      */     }
/*      */     finally
/*      */     {
/*  498 */       this.privData = null;
/*      */     }
/*  500 */     TsapiTrace.traceExit("addTsapiCallEventMonitor(CallObserver observer, CallListener listener)", this);
/*      */   }
/*      */ 
/*      */   public CallObserver[] getObservers()
/*      */   {
/*  508 */     TsapiTrace.traceEntry("getObservers[]", this);
/*      */     try
/*      */     {
/*  511 */       this.tsCall = this.tsCall.getHandOff();
/*  512 */       Vector tsapiCallObservers = this.tsCall.getCallObservers();
/*      */ 
/*  514 */       if ((tsapiCallObservers == null) || (tsapiCallObservers.size() == 0))
/*      */       {
/*  516 */         TsapiTrace.traceExit("getObservers[]", this);
/*  517 */         return null;
/*      */       }
/*      */ 
/*  520 */       ArrayList observers = new ArrayList();
/*      */ 
/*  522 */       for (Iterator i$ = tsapiCallObservers.iterator(); i$.hasNext(); ) { TsapiCallMonitor obs = (TsapiCallMonitor)i$.next();
/*  523 */         if (obs.getObserver() != null)
/*  524 */           observers.add(obs.getObserver()); }
/*      */ 
/*      */       TsapiCallMonitor obs;
/*  526 */       TsapiTrace.traceExit("getObservers[]", this);
/*  527 */       CallObserver[] observerArray = new CallObserver[observers.size()];
/*  528 */       return (CallObserver[])observers.toArray(observerArray);
/*      */     }
/*      */     finally
/*      */     {
/*  532 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeObserver(CallObserver observer)
/*      */   {
/*  542 */     TsapiTrace.traceEntry("removeObserver[CallObserver observer]", this);
/*      */     try
/*      */     {
/*  545 */       this.tsCall = this.tsCall.getHandOff();
/*  546 */       Vector tsapiCallObservers = this.tsCall.getCallObservers();
/*      */ 
/*  548 */       if ((tsapiCallObservers == null) || (tsapiCallObservers.size() == 0))
/*      */       {
/*  550 */         TsapiTrace.traceExit("removeObserver[CallObserver observer]", this);
/*  551 */         return;
/*      */       }
/*      */ 
/*  554 */       for (int i = 0; i < tsapiCallObservers.size(); ++i)
/*      */       {
/*  556 */         TsapiCallMonitor obs = (TsapiCallMonitor)tsapiCallObservers.elementAt(i);
/*  557 */         if (obs.getObserver() != observer)
/*      */           continue;
/*  559 */         this.tsCall = this.tsCall.getHandOff();
/*  560 */         this.tsCall.removeCallMonitor(obs);
/*  561 */         TsapiTrace.traceExit("removeObserver[CallObserver observer]", this);
/*  562 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  568 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final CallCapabilities getCapabilities(Terminal terminal, Address address)
/*      */   {
/*  577 */     TsapiTrace.traceEntry("getCapabilities[Terminal terminal, Address address]", this);
/*      */     try
/*      */     {
/*  581 */       this.tsCall = this.tsCall.getHandOff();
/*  582 */       CallCapabilities caps = this.tsCall.getTsapiCallCapabilities();
/*  583 */       TsapiTrace.traceExit("getCapabilities[Terminal terminal, Address address]", this);
/*  584 */       return caps;
/*      */     }
/*      */     finally
/*      */     {
/*  588 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final CallCapabilities getCallCapabilities(Terminal term, Address addr)
/*      */     throws InvalidArgumentException, PlatformException
/*      */   {
/*  596 */     TsapiTrace.traceEntry("getCallCapabilities[Terminal term, Address addr]", this);
/*  597 */     CallCapabilities caps = getCapabilities(null, null);
/*  598 */     TsapiTrace.traceExit("getCallCapabilities[Terminal term, Address addr]", this);
/*  599 */     return caps;
/*      */   }
/*      */ 
/*      */   public final Address getCallingAddress()
/*      */   {
/*  606 */     TsapiTrace.traceEntry("getCallingAddress[]", this);
/*      */     try
/*      */     {
/*  609 */       this.tsCall = this.tsCall.getHandOff();
/*  610 */       TSDevice tsDevice = this.tsCall.getCallingAddress();
/*      */       Address addr;
/*  611 */       if (tsDevice != null)
/*      */       {
/*  613 */         addr = (Address)TsapiCreateObject.getTsapiObject(tsDevice, true);
/*  614 */         TsapiTrace.traceExit("getCallingAddress[]", this);
/*  615 */         return addr;
/*      */       }
/*      */ 
/*  619 */       TsapiTrace.traceExit("getCallingAddress[]", this);
/*  620 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*  625 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Terminal getCallingTerminal()
/*      */   {
/*  631 */     TsapiTrace.traceEntry("getCallingTerminal[]", this);
/*      */     try
/*      */     {
/*  634 */       this.tsCall = this.tsCall.getHandOff();
/*  635 */       TSDevice tsDevice = this.tsCall.getCallingTerminal();
/*      */       Terminal term;
/*  636 */       if (tsDevice != null)
/*      */       {
/*  638 */         term = (Terminal)TsapiCreateObject.getTsapiObject(tsDevice, false);
/*  639 */         TsapiTrace.traceExit("getCallingTerminal[]", this);
/*  640 */         return term;
/*      */       }
/*      */ 
/*  644 */       TsapiTrace.traceExit("getCallingTerminal[]", this);
/*  645 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*  650 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Address getCalledAddress()
/*      */   {
/*  656 */     TsapiTrace.traceEntry("getCalledAddress[]", this);
/*      */     try
/*      */     {
/*  659 */       this.tsCall = this.tsCall.getHandOff();
/*  660 */       TSDevice tsDevice = this.tsCall.getCalledDevice();
/*      */       Address addr;
/*  661 */       if (tsDevice != null)
/*      */       {
/*  663 */         addr = (Address)TsapiCreateObject.getTsapiObject(tsDevice, true);
/*  664 */         TsapiTrace.traceExit("getCalledAddress[]", this);
/*  665 */         return addr;
/*      */       }
/*      */ 
/*  669 */       TsapiTrace.traceExit("getCalledAddress[]", this);
/*  670 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*  675 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Address getLastRedirectedAddress()
/*      */   {
/*  681 */     TsapiTrace.traceEntry("getLastRedirectedAddress[]", this);
/*      */     try
/*      */     {
/*  684 */       this.tsCall = this.tsCall.getHandOff();
/*  685 */       TSDevice tsDevice = this.tsCall.getLastRedirectionDevice();
/*      */       Address addr;
/*  686 */       if (tsDevice != null)
/*      */       {
/*  688 */         addr = (Address)TsapiCreateObject.getTsapiObject(tsDevice, true);
/*  689 */         TsapiTrace.traceExit("getLastRedirectedAddress[]", this);
/*  690 */         return addr;
/*      */       }
/*      */ 
/*  694 */       TsapiTrace.traceExit("getLastRedirectedAddress[]", this);
/*  695 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*  700 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Connection offHook(Address origaddress, Terminal origterminal)
/*      */     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  708 */     TsapiTrace.traceEntry("offHook[Address origaddress, Terminal origterminal]", this);
/*      */     try
/*      */     {
/*  711 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*      */     }
/*      */     finally
/*      */     {
/*  715 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Connection addParty(String newParty, boolean active)
/*      */     throws TsapiInvalidStateException, TsapiInvalidPartyException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  725 */     TsapiTrace.traceEntry("addParty[String newParty, boolean active]", this);
/*      */     try
/*      */     {
/*  728 */       this.tsCall = this.tsCall.getHandOff();
/*  729 */       TSConnection tsConn = this.tsCall.addParty(newParty, active);
/*      */       Connection conn;
/*  730 */       if (tsConn != null)
/*      */       {
/*  732 */         conn = (Connection)TsapiCreateObject.getTsapiObject(tsConn, true);
/*  733 */         TsapiTrace.traceExit("addParty[String newParty, boolean active]", this);
/*  734 */         return conn;
/*      */       }
/*      */ 
/*  738 */       TsapiTrace.traceExit("addParty[String newParty, boolean active]", this);
/*  739 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*  744 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Connection addParty(String newParty)
/*      */     throws TsapiInvalidStateException, TsapiInvalidPartyException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  754 */     TsapiTrace.traceEntry("addParty[String newParty]", this);
/*  755 */     Connection conn = addParty(newParty, true);
/*  756 */     TsapiTrace.traceExit("addParty[String newParty]", this);
/*  757 */     return conn;
/*      */   }
/*      */ 
/*      */   public final void drop()
/*      */     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  765 */     TsapiTrace.traceEntry("drop[]", this);
/*      */     try
/*      */     {
/*  768 */       this.tsCall = this.tsCall.getHandOff();
/*  769 */       this.tsCall.drop(this.privData);
/*      */     }
/*      */     finally
/*      */     {
/*  773 */       this.privData = null;
/*      */     }
/*  775 */     TsapiTrace.traceExit("drop[]", this);
/*      */   }
/*      */ 
/*      */   public final void conference(Call otherCall)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  784 */     TsapiTrace.traceEntry("conference[Call otherCall]", this);
/*      */     try
/*      */     {
/*  787 */       if (!(otherCall instanceof LucentV7Call)) {
/*  788 */         throw new TsapiInvalidArgumentException(3, 0, "other Call is not an instanceof ITsapiCall");
/*      */       }
/*      */ 
/*  791 */       TSCall oCall = ((TsapiCall)otherCall).getTSCall();
/*  792 */       if (oCall != null)
/*      */       {
/*  794 */         this.tsCall = this.tsCall.getHandOff();
/*  795 */         this.tsCall.conference(oCall, this.privData);
/*      */       }
/*      */       else
/*      */       {
/*  799 */         throw new TsapiPlatformException(4, 0, "could not locate other call");
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  804 */       this.privData = null;
/*      */     }
/*  806 */     TsapiTrace.traceExit("conference[Call otherCall]", this);
/*      */   }
/*      */ 
/*      */   public final void transfer(Call otherCall)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  815 */     TsapiTrace.traceEntry("transfer[Call otherCall]", this);
/*      */     try
/*      */     {
/*  818 */       if (!(otherCall instanceof LucentV7Call)) {
/*  819 */         throw new TsapiInvalidArgumentException(3, 0, "other Call is not an instanceof ITsapiCall");
/*      */       }
/*      */ 
/*  822 */       TSCall oCall = ((TsapiCall)otherCall).getTSCall();
/*  823 */       if (oCall != null)
/*      */       {
/*  825 */         this.tsCall = this.tsCall.getHandOff();
/*  826 */         this.tsCall.transfer(oCall, this.privData);
/*      */       }
/*      */       else
/*      */       {
/*  830 */         throw new TsapiPlatformException(4, 0, "could not locate other call");
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  835 */       this.privData = null;
/*      */     }
/*  837 */     TsapiTrace.traceExit("transfer[Call otherCall]", this);
/*      */   }
/*      */ 
/*      */   public final Connection transfer(String address)
/*      */     throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiInvalidPartyException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  846 */     TsapiTrace.traceEntry("transfer[String address]", this);
/*      */     try
/*      */     {
/*  849 */       if ((address == null) || (address.equals("")))
/*      */       {
/*  851 */         throw new TsapiInvalidArgumentException(3, 0, "address null or an empty string");
/*      */       }
/*      */ 
/*  856 */       this.tsCall = this.tsCall.getHandOff();
/*  857 */       TSConnection tsConn = this.tsCall.transfer(address, this.privData);
/*      */       Connection conn;
/*  858 */       if (tsConn != null)
/*      */       {
/*  860 */         conn = (Connection)TsapiCreateObject.getTsapiObject(tsConn, true);
/*  861 */         TsapiTrace.traceExit("transfer[String address]", this);
/*  862 */         return conn;
/*      */       }
/*      */ 
/*  866 */       TsapiTrace.traceExit("transfer[String address]", this);
/*  867 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*  873 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void setConferenceController(TerminalConnection termconn)
/*      */     throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException
/*      */   {
/*      */     try
/*      */     {
/*  884 */       TsapiTrace.traceEntry("setConferenceController[TerminalConnection termconn]", this);
/*  885 */       if (!(termconn instanceof ITsapiTerminalConnection)) {
/*  886 */         throw new TsapiInvalidArgumentException(3, 0, "The given TerminalConnection is not an instanceof ITsapiTerminalConnection");
/*      */       }
/*      */ 
/*  889 */       TSConnection tsConn = ((TsapiTerminalConnection)termconn).getTSConnection();
/*  890 */       if (tsConn != null)
/*      */       {
/*  892 */         this.tsCall = this.tsCall.getHandOff();
/*  893 */         this.tsCall.setConfController(tsConn);
/*      */       }
/*      */       else
/*      */       {
/*  897 */         throw new TsapiPlatformException(4, 0, "could not locate terminal connection");
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  902 */       this.privData = null;
/*      */     }
/*  904 */     TsapiTrace.traceExit("setConferenceController[TerminalConnection termconn]", this);
/*      */   }
/*      */ 
/*      */   public final TerminalConnection getConferenceController()
/*      */   {
/*  910 */     TsapiTrace.traceEntry("getConferenceController[]", this);
/*      */     try
/*      */     {
/*  913 */       this.tsCall = this.tsCall.getHandOff();
/*  914 */       TSConnection tsConn = this.tsCall.getConfController();
/*      */       TerminalConnection conn;
/*  915 */       if (tsConn != null)
/*      */       {
/*  917 */         conn = (TerminalConnection)TsapiCreateObject.getTsapiObject(tsConn, false);
/*  918 */         TsapiTrace.traceExit("getConferenceController[]", this);
/*  919 */         return conn;
/*      */       }
/*      */ 
/*  923 */       TsapiTrace.traceExit("getConferenceController[]", this);
/*  924 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*  929 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void setTransferController(TerminalConnection termconn)
/*      */     throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException
/*      */   {
/*  938 */     TsapiTrace.traceEntry("setTransferController[TerminalConnection termconn]", this);
/*      */     try
/*      */     {
/*  941 */       if (!(termconn instanceof ITsapiTerminalConnection)) {
/*  942 */         throw new TsapiInvalidArgumentException(3, 0, "The given TerminalConnection is not an instanceof ITsapiTerminalConnection");
/*      */       }
/*      */ 
/*  945 */       TSConnection tsConn = ((TsapiTerminalConnection)termconn).getTSConnection();
/*  946 */       if (tsConn != null)
/*      */       {
/*  948 */         this.tsCall = this.tsCall.getHandOff();
/*  949 */         this.tsCall.setXferController(tsConn);
/*      */       }
/*      */       else
/*      */       {
/*  953 */         throw new TsapiPlatformException(4, 0, "could not locate terminal connection");
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  958 */       this.privData = null;
/*      */     }
/*  960 */     TsapiTrace.traceExit("setTransferController[TerminalConnection termconn]", this);
/*      */   }
/*      */ 
/*      */   public final TerminalConnection getTransferController()
/*      */   {
/*  966 */     TsapiTrace.traceEntry("getTransferController[]", this);
/*      */     try
/*      */     {
/*  969 */       this.tsCall = this.tsCall.getHandOff();
/*  970 */       TSConnection tsConn = this.tsCall.getXferController();
/*      */       TerminalConnection obj;
/*  971 */       if (tsConn != null)
/*      */       {
/*  973 */         obj = (TerminalConnection)TsapiCreateObject.getTsapiObject(tsConn, false);
/*  974 */         TsapiTrace.traceExit("getTransferController[]", this);
/*  975 */         return obj;
/*      */       }
/*      */ 
/*  979 */       TsapiTrace.traceExit("getTransferController[]", this);
/*  980 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*  985 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void setConferenceEnable(boolean enable)
/*      */     throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException
/*      */   {
/*  994 */     TsapiTrace.traceEntry("setConferenceEnable[boolean enable]", this);
/*      */     try
/*      */     {
/*  997 */       this.tsCall = this.tsCall.getHandOff();
/*  998 */       this.tsCall.setConfEnable(enable);
/*      */     }
/*      */     finally
/*      */     {
/* 1002 */       this.privData = null;
/*      */     }
/* 1004 */     TsapiTrace.traceExit("setConferenceEnable[boolean enable]", this);
/*      */   }
/*      */ 
/*      */   public final boolean getConferenceEnable()
/*      */   {
/* 1010 */     TsapiTrace.traceEntry("getConferenceEnable[]", this);
/*      */     try
/*      */     {
/* 1013 */       this.tsCall = this.tsCall.getHandOff();
/* 1014 */       boolean enable = this.tsCall.getConfEnable();
/* 1015 */       TsapiTrace.traceExit("getConferenceEnable[]", this);
/* 1016 */       return enable;
/*      */     }
/*      */     finally
/*      */     {
/* 1020 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void setTransferEnable(boolean enable)
/*      */     throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException
/*      */   {
/* 1029 */     TsapiTrace.traceEntry("setTransferEnable[boolean enable]", this);
/*      */     try
/*      */     {
/* 1032 */       this.tsCall = this.tsCall.getHandOff();
/* 1033 */       this.tsCall.setXferEnable(enable);
/*      */     }
/*      */     finally
/*      */     {
/* 1037 */       this.privData = null;
/*      */     }
/* 1039 */     TsapiTrace.traceExit("setTransferEnable[boolean enable]", this);
/*      */   }
/*      */ 
/*      */   public final boolean getTransferEnable()
/*      */   {
/* 1045 */     TsapiTrace.traceEntry("getTransferEnable[]", this);
/*      */     try
/*      */     {
/* 1048 */       this.tsCall = this.tsCall.getHandOff();
/* 1049 */       boolean enabled = this.tsCall.getXferEnable();
/* 1050 */       TsapiTrace.traceExit("getTransferEnable[]", this);
/* 1051 */       return enabled;
/*      */     }
/*      */     finally
/*      */     {
/* 1055 */       this.privData = null; }  } 
/*      */   // ERROR //
/*      */   public final Connection[] consult(TerminalConnection termconn, String address) throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiPrivilegeViolationException { // Byte code:
/*      */     //   0: ldc 162
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_1
/*      */     //   7: instanceof 141
/*      */     //   10: ifne +15 -> 25
/*      */     //   13: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   16: dup
/*      */     //   17: iconst_3
/*      */     //   18: iconst_0
/*      */     //   19: ldc 142
/*      */     //   21: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   24: athrow
/*      */     //   25: aconst_null
/*      */     //   26: astore_3
/*      */     //   27: aload_1
/*      */     //   28: checkcast 143	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection
/*      */     //   31: invokevirtual 144	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:getTSConnection	()Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
/*      */     //   34: astore 4
/*      */     //   36: aload 4
/*      */     //   38: ifnull +32 -> 70
/*      */     //   41: aload_0
/*      */     //   42: aload_0
/*      */     //   43: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   46: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   49: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   52: aload_0
/*      */     //   53: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   56: aload 4
/*      */     //   58: aload_2
/*      */     //   59: aload_0
/*      */     //   60: getfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   63: invokevirtual 163	com/avaya/jtapi/tsapi/impl/core/TSCall:consult	(Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;Ljava/lang/String;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Ljava/util/Vector;
/*      */     //   66: astore_3
/*      */     //   67: goto +15 -> 82
/*      */     //   70: new 16	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   73: dup
/*      */     //   74: iconst_4
/*      */     //   75: iconst_0
/*      */     //   76: ldc 146
/*      */     //   78: invokespecial 18	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   81: athrow
/*      */     //   82: aload_3
/*      */     //   83: ifnonnull +18 -> 101
/*      */     //   86: ldc 162
/*      */     //   88: aload_0
/*      */     //   89: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   92: aconst_null
/*      */     //   93: astore 5
/*      */     //   95: jsr +119 -> 214
/*      */     //   98: aload 5
/*      */     //   100: areturn
/*      */     //   101: aload_3
/*      */     //   102: dup
/*      */     //   103: astore 5
/*      */     //   105: monitorenter
/*      */     //   106: aload_3
/*      */     //   107: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   110: ifne +21 -> 131
/*      */     //   113: ldc 162
/*      */     //   115: aload_0
/*      */     //   116: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   119: aconst_null
/*      */     //   120: astore 6
/*      */     //   122: aload 5
/*      */     //   124: monitorexit
/*      */     //   125: jsr +89 -> 214
/*      */     //   128: aload 6
/*      */     //   130: areturn
/*      */     //   131: aload_3
/*      */     //   132: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   135: anewarray 8	javax/telephony/Connection
/*      */     //   138: astore 6
/*      */     //   140: iconst_0
/*      */     //   141: istore 7
/*      */     //   143: iload 7
/*      */     //   145: aload_3
/*      */     //   146: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   149: if_icmpge +30 -> 179
/*      */     //   152: aload 6
/*      */     //   154: iload 7
/*      */     //   156: aload_3
/*      */     //   157: iload 7
/*      */     //   159: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
/*      */     //   162: checkcast 10	com/avaya/jtapi/tsapi/impl/core/TSConnection
/*      */     //   165: iconst_1
/*      */     //   166: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   169: checkcast 8	javax/telephony/Connection
/*      */     //   172: aastore
/*      */     //   173: iinc 7 1
/*      */     //   176: goto -33 -> 143
/*      */     //   179: ldc 162
/*      */     //   181: aload_0
/*      */     //   182: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   185: aload 6
/*      */     //   187: astore 7
/*      */     //   189: aload 5
/*      */     //   191: monitorexit
/*      */     //   192: jsr +22 -> 214
/*      */     //   195: aload 7
/*      */     //   197: areturn
/*      */     //   198: astore 8
/*      */     //   200: aload 5
/*      */     //   202: monitorexit
/*      */     //   203: aload 8
/*      */     //   205: athrow
/*      */     //   206: astore 9
/*      */     //   208: jsr +6 -> 214
/*      */     //   211: aload 9
/*      */     //   213: athrow
/*      */     //   214: astore 10
/*      */     //   216: aload_0
/*      */     //   217: aconst_null
/*      */     //   218: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   221: ret 10
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   106	125	198	finally
/*      */     //   131	192	198	finally
/*      */     //   198	203	198	finally
/*      */     //   6	98	206	finally
/*      */     //   101	128	206	finally
/*      */     //   131	195	206	finally
/*      */     //   198	211	206	finally } 
/* 1118 */   public final Connection consult(TerminalConnection termconn) throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiPrivilegeViolationException { TsapiTrace.traceEntry("consult[TerminalConnection termconn]", this);
/*      */     try
/*      */     {
/* 1122 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*      */     }
/*      */     finally
/*      */     {
/* 1126 */       this.privData = null;
/*      */     } }
/*      */ 
/*      */ 
/*      */   private LucentConsultationCall createLucentConsultationCall(String destRoute, boolean priorityCall, UserToUserInfo userInfo)
/*      */   {
/* 1133 */     TsapiTrace.traceEntry("createLucentConsultationCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]", this);
/* 1134 */     TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
/* 1135 */     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(userInfo);
/*      */ 
/* 1137 */     LucentConsultationCall call = null;
/* 1138 */     if (TSProviderImpl != null)
/*      */     {
/* 1140 */       if (TSProviderImpl.isLucentV6()) {
/* 1141 */         call = new LucentV6ConsultationCall(destRoute, priorityCall, asn_uui);
/*      */       }
/*      */       else {
/* 1144 */         call = new LucentConsultationCall(destRoute, priorityCall, asn_uui);
/*      */       }
/*      */     }
/* 1147 */     TsapiTrace.traceExit("createLucentConsultationCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]", this);
/* 1148 */     return call;
/*      */   }
/*      */ 
/*      */   public final Connection[] consult(LucentTerminalConnection termconn, String address, boolean priorityCall, UserToUserInfo userInfo)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiPrivilegeViolationException
/*      */   {
/* 1157 */     TsapiTrace.traceEntry("consult[LucentTerminalConnection termconn, String address, boolean priorityCall, UserToUserInfo userInfo]", this);
/* 1158 */     if (termconn == null) {
/* 1159 */       throw new TsapiInvalidArgumentException(3, 0, "The given TerminalConnection is null");
/*      */     }
/*      */ 
/* 1162 */     LucentConsultationCall lcc = createLucentConsultationCall(null, priorityCall, userInfo);
/* 1163 */     this.privData = lcc.makeTsapiPrivate();
/* 1164 */     Connection[] conns = consult(termconn, address);
/* 1165 */     TsapiTrace.traceExit("consult[LucentTerminalConnection termconn, String address, boolean priorityCall, UserToUserInfo userInfo]", this);
/* 1166 */     return conns;
/*      */   }
/*      */ 
/*      */   private LucentDirectAgentCall createLucentDirectAgentCall(String split, boolean priorityCall, UserToUserInfo userInfo)
/*      */   {
/* 1172 */     TsapiTrace.traceEntry("createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]", this);
/* 1173 */     TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
/* 1174 */     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(userInfo);
/*      */ 
/* 1177 */     if (TSProviderImpl != null)
/*      */     {
/* 1179 */       if (TSProviderImpl.isLucentV6()) {
/* 1180 */         LucentV6DirectAgentCall call = new LucentV6DirectAgentCall(split, priorityCall, asn_uui);
/* 1181 */         TsapiTrace.traceExit("createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]", this);
/* 1182 */         return call;
/*      */       }
/*      */ 
/* 1185 */       LucentDirectAgentCall call = new LucentDirectAgentCall(split, priorityCall, asn_uui);
/* 1186 */       TsapiTrace.traceExit("createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]", this);
/* 1187 */       return call;
/*      */     }
/*      */ 
/* 1191 */     TsapiTrace.traceExit("createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]", this);
/* 1192 */     return null;
/*      */   }
/*      */ 
/*      */   public final Connection[] consultDirectAgent(LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiPrivilegeViolationException
/*      */   {
/* 1202 */     TsapiTrace.traceEntry("consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
/* 1203 */     if (termconn == null) {
/* 1204 */       throw new TsapiInvalidArgumentException(3, 0, "The given TerminalConnection is null");
/*      */     }
/*      */ 
/* 1207 */     if (calledAgent == null) {
/* 1208 */       throw new TsapiInvalidArgumentException(3, 0, "called Agent is null");
/*      */     }
/*      */ 
/* 1213 */     if (calledAgent.getACDAddress() != null)
/*      */     {
/* 1215 */       LucentDirectAgentCall lda = createLucentDirectAgentCall(calledAgent.getACDAddress().getName(), priorityCall, userInfo);
/* 1216 */       this.privData = lda.makeTsapiPrivate();
/* 1217 */       Connection[] conns = consult(termconn, calledAgent.getAgentAddress().getName());
/* 1218 */       TsapiTrace.traceExit("consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
/* 1219 */       return conns;
/*      */     }
/*      */ 
/* 1223 */     log.info("*****consultDirectAgent: ACDAddress is Null, using default skill(ACD)");
/* 1224 */     Connection[] conns = consult(termconn, calledAgent.getAgentID());
/* 1225 */     TsapiTrace.traceExit("consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
/* 1226 */     return conns;
/*      */   }
/*      */ 
/*      */   public final Connection[] consultDirectAgent(LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiPrivilegeViolationException
/*      */   {
/* 1239 */     TsapiTrace.traceEntry("consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress]", this);
/* 1240 */     if (termconn == null) {
/* 1241 */       throw new TsapiInvalidArgumentException(3, 0, "The given TerminalConnection is null");
/*      */     }
/*      */ 
/* 1244 */     if (calledAgent == null) {
/* 1245 */       throw new TsapiInvalidArgumentException(3, 0, "called Agent is null");
/*      */     }
/*      */ 
/* 1248 */     if (acdaddress != null)
/*      */     {
/* 1250 */       LucentDirectAgentCall lda = createLucentDirectAgentCall(acdaddress.getName(), priorityCall, userInfo);
/* 1251 */       this.privData = lda.makeTsapiPrivate();
/* 1252 */       Connection[] conns = consult(termconn, calledAgent.getAgentAddress().getName());
/* 1253 */       TsapiTrace.traceExit("consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress]", this);
/* 1254 */       return conns;
/*      */     }
/*      */ 
/* 1258 */     Connection[] conns = consultDirectAgent(termconn, calledAgent, priorityCall, userInfo);
/* 1259 */     TsapiTrace.traceExit("consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress]", this);
/* 1260 */     return conns;
/*      */   }
/*      */ 
/*      */   public final Connection[] consultSupervisorAssist(LucentTerminalConnection termconn, ACDAddress split, String address, UserToUserInfo userInfo)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiPrivilegeViolationException
/*      */   {
/* 1274 */     TsapiTrace.traceEntry("consultSupervisorAssist[LucentTerminalConnection termconn, ACDAddress split, String address, UserToUserInfo userInfo]", this);
/* 1275 */     if (termconn == null) {
/* 1276 */       throw new TsapiInvalidArgumentException(3, 0, "The given TerminalConnection is null");
/*      */     }
/*      */ 
/* 1279 */     if (!(split instanceof LucentAddress)) {
/* 1280 */       throw new TsapiInvalidArgumentException(3, 0, "The given ACD Address is not an instanceof LucentAddress");
/*      */     }
/*      */ 
/* 1283 */     LucentSupervisorAssistCall lsa = createLucentSupervisorAssistCall(split.getName(), userInfo);
/* 1284 */     this.privData = lsa.makeTsapiPrivate();
/* 1285 */     Connection[] conns = consult(termconn, address);
/* 1286 */     TsapiTrace.traceExit("consultSupervisorAssist[LucentTerminalConnection termconn, ACDAddress split, String address, UserToUserInfo userInfo]", this);
/* 1287 */     return conns; } 
/*      */   // ERROR //
/*      */   public final Connection[] connectPredictive(Terminal originatorTerminal, Address origAddress, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType) throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException { // Byte code:
/*      */     //   0: ldc 187
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_1
/*      */     //   7: ifnull +22 -> 29
/*      */     //   10: aload_1
/*      */     //   11: instanceof 22
/*      */     //   14: ifne +15 -> 29
/*      */     //   17: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   20: dup
/*      */     //   21: iconst_3
/*      */     //   22: iconst_0
/*      */     //   23: ldc 188
/*      */     //   25: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   28: athrow
/*      */     //   29: aload_2
/*      */     //   30: instanceof 26
/*      */     //   33: ifne +15 -> 48
/*      */     //   36: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   39: dup
/*      */     //   40: iconst_3
/*      */     //   41: iconst_0
/*      */     //   42: ldc 189
/*      */     //   44: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   47: athrow
/*      */     //   48: aconst_null
/*      */     //   49: astore 8
/*      */     //   51: aload_2
/*      */     //   52: checkcast 28	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   55: invokevirtual 29	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   58: astore 9
/*      */     //   60: aconst_null
/*      */     //   61: astore 10
/*      */     //   63: aload_1
/*      */     //   64: ifnull +12 -> 76
/*      */     //   67: aload_1
/*      */     //   68: checkcast 30	com/avaya/jtapi/tsapi/impl/TsapiTerminal
/*      */     //   71: invokevirtual 31	com/avaya/jtapi/tsapi/impl/TsapiTerminal:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   74: astore 10
/*      */     //   76: aload 9
/*      */     //   78: ifnull +71 -> 149
/*      */     //   81: aload 10
/*      */     //   83: ifnull +25 -> 108
/*      */     //   86: aload 9
/*      */     //   88: aload 10
/*      */     //   90: invokevirtual 32	java/lang/Object:equals	(Ljava/lang/Object;)Z
/*      */     //   93: ifne +15 -> 108
/*      */     //   96: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   99: dup
/*      */     //   100: iconst_3
/*      */     //   101: iconst_0
/*      */     //   102: ldc 190
/*      */     //   104: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   107: athrow
/*      */     //   108: aload_0
/*      */     //   109: aload_0
/*      */     //   110: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   113: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   116: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   119: aload_0
/*      */     //   120: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   123: aload 9
/*      */     //   125: aload_3
/*      */     //   126: iload 4
/*      */     //   128: iload 5
/*      */     //   130: iload 6
/*      */     //   132: iload 7
/*      */     //   134: aconst_null
/*      */     //   135: iconst_0
/*      */     //   136: aconst_null
/*      */     //   137: aload_0
/*      */     //   138: getfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   141: invokevirtual 191	com/avaya/jtapi/tsapi/impl/core/TSCall:connectPredictive	(Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Ljava/lang/String;IIIILjava/lang/String;ZLcom/avaya/jtapi/tsapi/UserToUserInfo;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Ljava/util/Vector;
/*      */     //   144: astore 8
/*      */     //   146: goto +15 -> 161
/*      */     //   149: new 16	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   152: dup
/*      */     //   153: iconst_4
/*      */     //   154: iconst_0
/*      */     //   155: ldc 35
/*      */     //   157: invokespecial 18	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   160: athrow
/*      */     //   161: aload 8
/*      */     //   163: ifnonnull +18 -> 181
/*      */     //   166: ldc 187
/*      */     //   168: aload_0
/*      */     //   169: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   172: aconst_null
/*      */     //   173: astore 11
/*      */     //   175: jsr +124 -> 299
/*      */     //   178: aload 11
/*      */     //   180: areturn
/*      */     //   181: aload 8
/*      */     //   183: dup
/*      */     //   184: astore 11
/*      */     //   186: monitorenter
/*      */     //   187: aload 8
/*      */     //   189: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   192: ifne +21 -> 213
/*      */     //   195: ldc 187
/*      */     //   197: aload_0
/*      */     //   198: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   201: aconst_null
/*      */     //   202: astore 12
/*      */     //   204: aload 11
/*      */     //   206: monitorexit
/*      */     //   207: jsr +92 -> 299
/*      */     //   210: aload 12
/*      */     //   212: areturn
/*      */     //   213: aload 8
/*      */     //   215: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   218: anewarray 8	javax/telephony/Connection
/*      */     //   221: astore 12
/*      */     //   223: iconst_0
/*      */     //   224: istore 13
/*      */     //   226: iload 13
/*      */     //   228: aload 8
/*      */     //   230: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   233: if_icmpge +31 -> 264
/*      */     //   236: aload 12
/*      */     //   238: iload 13
/*      */     //   240: aload 8
/*      */     //   242: iload 13
/*      */     //   244: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
/*      */     //   247: checkcast 10	com/avaya/jtapi/tsapi/impl/core/TSConnection
/*      */     //   250: iconst_1
/*      */     //   251: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   254: checkcast 8	javax/telephony/Connection
/*      */     //   257: aastore
/*      */     //   258: iinc 13 1
/*      */     //   261: goto -35 -> 226
/*      */     //   264: ldc 187
/*      */     //   266: aload_0
/*      */     //   267: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   270: aload 12
/*      */     //   272: astore 13
/*      */     //   274: aload 11
/*      */     //   276: monitorexit
/*      */     //   277: jsr +22 -> 299
/*      */     //   280: aload 13
/*      */     //   282: areturn
/*      */     //   283: astore 14
/*      */     //   285: aload 11
/*      */     //   287: monitorexit
/*      */     //   288: aload 14
/*      */     //   290: athrow
/*      */     //   291: astore 15
/*      */     //   293: jsr +6 -> 299
/*      */     //   296: aload 15
/*      */     //   298: athrow
/*      */     //   299: astore 16
/*      */     //   301: aload_0
/*      */     //   302: aconst_null
/*      */     //   303: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   306: ret 16
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   187	207	283	finally
/*      */     //   213	277	283	finally
/*      */     //   283	288	283	finally
/*      */     //   6	178	291	finally
/*      */     //   181	210	291	finally
/*      */     //   213	280	291	finally
/*      */     //   283	296	291	finally } 
/*      */   // ERROR //
/*      */   public final Connection[] connectPredictive(LucentTerminal originatorTerminal, LucentAddress origAddress, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType, boolean priorityCall, UserToUserInfo userInfo) throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException { // Byte code:
/*      */     //   0: ldc 192
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_2
/*      */     //   7: ifnonnull +15 -> 22
/*      */     //   10: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   13: dup
/*      */     //   14: iconst_3
/*      */     //   15: iconst_0
/*      */     //   16: ldc 193
/*      */     //   18: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   21: athrow
/*      */     //   22: aconst_null
/*      */     //   23: astore 10
/*      */     //   25: aload_2
/*      */     //   26: checkcast 28	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   29: invokevirtual 29	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   32: astore 11
/*      */     //   34: aconst_null
/*      */     //   35: astore 12
/*      */     //   37: aload_1
/*      */     //   38: ifnull +12 -> 50
/*      */     //   41: aload_1
/*      */     //   42: checkcast 30	com/avaya/jtapi/tsapi/impl/TsapiTerminal
/*      */     //   45: invokevirtual 31	com/avaya/jtapi/tsapi/impl/TsapiTerminal:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   48: astore 12
/*      */     //   50: aload 11
/*      */     //   52: ifnull +73 -> 125
/*      */     //   55: aload 12
/*      */     //   57: ifnull +25 -> 82
/*      */     //   60: aload 11
/*      */     //   62: aload 12
/*      */     //   64: invokevirtual 32	java/lang/Object:equals	(Ljava/lang/Object;)Z
/*      */     //   67: ifne +15 -> 82
/*      */     //   70: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   73: dup
/*      */     //   74: iconst_3
/*      */     //   75: iconst_0
/*      */     //   76: ldc 194
/*      */     //   78: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   81: athrow
/*      */     //   82: aload_0
/*      */     //   83: aload_0
/*      */     //   84: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   87: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   90: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   93: aload_0
/*      */     //   94: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   97: aload 11
/*      */     //   99: aload_3
/*      */     //   100: iload 4
/*      */     //   102: iload 5
/*      */     //   104: iload 6
/*      */     //   106: iload 7
/*      */     //   108: aconst_null
/*      */     //   109: iload 8
/*      */     //   111: aload 9
/*      */     //   113: aload_0
/*      */     //   114: getfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   117: invokevirtual 191	com/avaya/jtapi/tsapi/impl/core/TSCall:connectPredictive	(Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Ljava/lang/String;IIIILjava/lang/String;ZLcom/avaya/jtapi/tsapi/UserToUserInfo;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Ljava/util/Vector;
/*      */     //   120: astore 10
/*      */     //   122: goto +15 -> 137
/*      */     //   125: new 16	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   128: dup
/*      */     //   129: iconst_4
/*      */     //   130: iconst_0
/*      */     //   131: ldc 35
/*      */     //   133: invokespecial 18	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   136: athrow
/*      */     //   137: aload 10
/*      */     //   139: ifnonnull +18 -> 157
/*      */     //   142: ldc 192
/*      */     //   144: aload_0
/*      */     //   145: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   148: aconst_null
/*      */     //   149: astore 13
/*      */     //   151: jsr +124 -> 275
/*      */     //   154: aload 13
/*      */     //   156: areturn
/*      */     //   157: aload 10
/*      */     //   159: dup
/*      */     //   160: astore 13
/*      */     //   162: monitorenter
/*      */     //   163: aload 10
/*      */     //   165: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   168: ifne +21 -> 189
/*      */     //   171: ldc 192
/*      */     //   173: aload_0
/*      */     //   174: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   177: aconst_null
/*      */     //   178: astore 14
/*      */     //   180: aload 13
/*      */     //   182: monitorexit
/*      */     //   183: jsr +92 -> 275
/*      */     //   186: aload 14
/*      */     //   188: areturn
/*      */     //   189: aload 10
/*      */     //   191: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   194: anewarray 8	javax/telephony/Connection
/*      */     //   197: astore 14
/*      */     //   199: iconst_0
/*      */     //   200: istore 15
/*      */     //   202: iload 15
/*      */     //   204: aload 10
/*      */     //   206: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   209: if_icmpge +31 -> 240
/*      */     //   212: aload 14
/*      */     //   214: iload 15
/*      */     //   216: aload 10
/*      */     //   218: iload 15
/*      */     //   220: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
/*      */     //   223: checkcast 10	com/avaya/jtapi/tsapi/impl/core/TSConnection
/*      */     //   226: iconst_1
/*      */     //   227: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   230: checkcast 8	javax/telephony/Connection
/*      */     //   233: aastore
/*      */     //   234: iinc 15 1
/*      */     //   237: goto -35 -> 202
/*      */     //   240: ldc 192
/*      */     //   242: aload_0
/*      */     //   243: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   246: aload 14
/*      */     //   248: astore 15
/*      */     //   250: aload 13
/*      */     //   252: monitorexit
/*      */     //   253: jsr +22 -> 275
/*      */     //   256: aload 15
/*      */     //   258: areturn
/*      */     //   259: astore 16
/*      */     //   261: aload 13
/*      */     //   263: monitorexit
/*      */     //   264: aload 16
/*      */     //   266: athrow
/*      */     //   267: astore 17
/*      */     //   269: jsr +6 -> 275
/*      */     //   272: aload 17
/*      */     //   274: athrow
/*      */     //   275: astore 18
/*      */     //   277: aload_0
/*      */     //   278: aconst_null
/*      */     //   279: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   282: ret 18
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   163	183	259	finally
/*      */     //   189	253	259	finally
/*      */     //   259	264	259	finally
/*      */     //   6	154	267	finally
/*      */     //   157	186	267	finally
/*      */     //   189	256	267	finally
/*      */     //   259	272	267	finally } 
/* 1444 */   public final void setApplicationData(Object data) throws TsapiMethodNotSupportedException { TsapiTrace.traceEntry("setApplicationData[Object data]", this);
/*      */     try
/*      */     {
/* 1447 */       throw new TsapiMethodNotSupportedException(0, 0, "unsupported by implementation");
/*      */     }
/*      */     finally
/*      */     {
/* 1451 */       this.privData = null;
/*      */     } }
/*      */ 
/*      */ 
/*      */   public final Object getApplicationData()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/* 1459 */     TsapiTrace.traceEntry("getApplicationData[]", this);
/*      */     try
/*      */     {
/* 1462 */       throw new TsapiMethodNotSupportedException(0, 0, "unsupported by implementation");
/*      */     }
/*      */     finally
/*      */     {
/* 1466 */       this.privData = null; }  } 
/*      */   // ERROR //
/*      */   public final CallCenterTrunk[] getTrunks() { // Byte code:
/*      */     //   0: ldc 197
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aconst_null
/*      */     //   7: astore_1
/*      */     //   8: aload_0
/*      */     //   9: aload_0
/*      */     //   10: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   13: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   16: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   19: aload_0
/*      */     //   20: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   23: invokevirtual 198	com/avaya/jtapi/tsapi/impl/core/TSCall:getTSTrunks	()Ljava/util/Vector;
/*      */     //   26: astore_1
/*      */     //   27: aload_1
/*      */     //   28: ifnonnull +16 -> 44
/*      */     //   31: ldc 197
/*      */     //   33: aload_0
/*      */     //   34: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   37: aconst_null
/*      */     //   38: astore_2
/*      */     //   39: jsr +106 -> 145
/*      */     //   42: aload_2
/*      */     //   43: areturn
/*      */     //   44: aload_1
/*      */     //   45: dup
/*      */     //   46: astore_2
/*      */     //   47: monitorenter
/*      */     //   48: aload_1
/*      */     //   49: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   52: ifne +18 -> 70
/*      */     //   55: ldc 197
/*      */     //   57: aload_0
/*      */     //   58: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   61: aconst_null
/*      */     //   62: astore_3
/*      */     //   63: aload_2
/*      */     //   64: monitorexit
/*      */     //   65: jsr +80 -> 145
/*      */     //   68: aload_3
/*      */     //   69: areturn
/*      */     //   70: aload_1
/*      */     //   71: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   74: anewarray 199	com/avaya/jtapi/tsapi/TsapiTrunk
/*      */     //   77: astore_3
/*      */     //   78: iconst_0
/*      */     //   79: istore 4
/*      */     //   81: iload 4
/*      */     //   83: aload_1
/*      */     //   84: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   87: if_icmpge +26 -> 113
/*      */     //   90: aload_3
/*      */     //   91: iload 4
/*      */     //   93: aload_1
/*      */     //   94: iload 4
/*      */     //   96: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
/*      */     //   99: iconst_0
/*      */     //   100: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   103: checkcast 199	com/avaya/jtapi/tsapi/TsapiTrunk
/*      */     //   106: aastore
/*      */     //   107: iinc 4 1
/*      */     //   110: goto -29 -> 81
/*      */     //   113: ldc 197
/*      */     //   115: aload_0
/*      */     //   116: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   119: aload_3
/*      */     //   120: astore 4
/*      */     //   122: aload_2
/*      */     //   123: monitorexit
/*      */     //   124: jsr +21 -> 145
/*      */     //   127: aload 4
/*      */     //   129: areturn
/*      */     //   130: astore 5
/*      */     //   132: aload_2
/*      */     //   133: monitorexit
/*      */     //   134: aload 5
/*      */     //   136: athrow
/*      */     //   137: astore 6
/*      */     //   139: jsr +6 -> 145
/*      */     //   142: aload 6
/*      */     //   144: athrow
/*      */     //   145: astore 7
/*      */     //   147: aload_0
/*      */     //   148: aconst_null
/*      */     //   149: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   152: ret 7
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   48	65	130	finally
/*      */     //   70	124	130	finally
/*      */     //   130	134	130	finally
/*      */     //   6	42	137	finally
/*      */     //   44	68	137	finally
/*      */     //   70	127	137	finally
/*      */     //   130	142	137	finally } 
/* 1510 */   public final CallCenterTrunk getTrunk() { TsapiTrace.traceEntry("getTrunk[]", this);
/*      */     try
/*      */     {
/* 1513 */       CallCenterTrunk[] trks = getTrunks();
/* 1514 */       if ((trks == null) || (trks.length == 0))
/*      */       {
/* 1516 */         TsapiTrace.traceExit("getTrunk[]", this);
/* 1517 */         return null;
/*      */       }
/* 1519 */       CallCenterTrunk trunk = trks[0];
/* 1520 */       TsapiTrace.traceExit("getTrunk[]", this);
/* 1521 */       return trunk;
/*      */     }
/*      */     finally
/*      */     {
/* 1525 */       this.privData = null;
/*      */     } }
/*      */ 
/*      */ 
/*      */   public final CallCenterAddress getDistributingAddress()
/*      */   {
/* 1531 */     TsapiTrace.traceEntry("getDistributingAddress[]", this);
/*      */     try
/*      */     {
/* 1534 */       this.tsCall = this.tsCall.getHandOff();
/* 1535 */       TSDevice tsDevice = this.tsCall.getDistributingDevice();
/* 1536 */       if (tsDevice == null)
/*      */       {
/* 1538 */         TsapiTrace.traceExit("getDistributingAddress[]", this);
/* 1539 */         return null;
/*      */       }
/* 1541 */       CallCenterAddress addr = (CallCenterAddress)TsapiCreateObject.getTsapiObject(tsDevice, true);
/* 1542 */       TsapiTrace.traceExit("getDistributingAddress[]", this);
/* 1543 */       return addr;
/*      */     }
/*      */     finally
/*      */     {
/* 1547 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final CallCenterAddress getDistributingVDNAddress()
/*      */   {
/* 1554 */     TsapiTrace.traceEntry("getDistributingVDNAddress[]", this);
/*      */     try
/*      */     {
/* 1557 */       this.tsCall = this.tsCall.getHandOff();
/* 1558 */       TSDevice tsDevice = this.tsCall.getDistributingVDN();
/* 1559 */       if (tsDevice == null)
/*      */       {
/* 1561 */         TsapiTrace.traceExit("getDistributingVDNAddress[]", this);
/* 1562 */         return null;
/*      */       }
/* 1564 */       CallCenterAddress addr = (CallCenterAddress)TsapiCreateObject.getTsapiObject(tsDevice, true);
/* 1565 */       TsapiTrace.traceExit("getDistributingVDNAddress[]", this);
/* 1566 */       return addr;
/*      */     }
/*      */     finally
/*      */     {
/* 1570 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final ACDAddress getDeliveringACDAddress()
/*      */   {
/* 1576 */     TsapiTrace.traceEntry("getDeliveringACDAddress[]", this);
/*      */     try
/*      */     {
/* 1579 */       this.tsCall = this.tsCall.getHandOff();
/* 1580 */       TSDevice tsDevice = this.tsCall.getDeliveringACDDevice();
/* 1581 */       if (tsDevice == null)
/*      */       {
/* 1583 */         TsapiTrace.traceExit("getDeliveringACDAddress[]", this);
/* 1584 */         return null;
/*      */       }
/* 1586 */       ACDAddress addr = (ACDAddress)TsapiCreateObject.getTsapiObject(tsDevice, true);
/* 1587 */       TsapiTrace.traceExit("getDeliveringACDAddress[]", this);
/* 1588 */       return addr;
/*      */     }
/*      */     finally
/*      */     {
/* 1592 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final UserToUserInfo getUserToUserInfo()
/*      */   {
/* 1599 */     TsapiTrace.traceEntry("getUserToUserInfo[]", this);
/*      */     try
/*      */     {
/* 1602 */       this.tsCall = this.tsCall.getHandOff();
/* 1603 */       UserToUserInfo uui = this.tsCall.getUUI();
/* 1604 */       TsapiTrace.traceExit("getUserToUserInfo[]", this);
/* 1605 */       return uui;
/*      */     }
/*      */     finally
/*      */     {
/* 1609 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final LookaheadInfo getLookaheadInfo()
/*      */   {
/* 1616 */     TsapiTrace.traceEntry("getLookaheadInfo[]", this);
/*      */     try
/*      */     {
/* 1619 */       this.tsCall = this.tsCall.getHandOff();
/* 1620 */       LookaheadInfo lai = this.tsCall.getLAI();
/* 1621 */       TsapiTrace.traceExit("getLookaheadInfo[]", this);
/* 1622 */       return lai;
/*      */     }
/*      */     finally
/*      */     {
/* 1626 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final UserEnteredCode getUserEnteredCode()
/*      */   {
/* 1633 */     TsapiTrace.traceEntry("getUserEnteredCode[]", this);
/*      */     try
/*      */     {
/* 1636 */       this.tsCall = this.tsCall.getHandOff();
/* 1637 */       UserEnteredCode uec = this.tsCall.getUEC();
/* 1638 */       TsapiTrace.traceExit("getUserEnteredCode[]", this);
/* 1639 */       return uec;
/*      */     }
/*      */     finally
/*      */     {
/* 1643 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final OriginalCallInfo getOriginalCallInfo()
/*      */   {
/* 1650 */     TsapiTrace.traceEntry("getOriginalCallInfo[]", this);
/*      */     try
/*      */     {
/* 1653 */       this.tsCall = this.tsCall.getHandOff();
/* 1654 */       OriginalCallInfo oci = this.tsCall.getOCI();
/* 1655 */       TsapiTrace.traceExit("getOriginalCallInfo[]", this);
/* 1656 */       return oci;
/*      */     }
/*      */     finally
/*      */     {
/* 1660 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final short getReason()
/*      */   {
/* 1667 */     TsapiTrace.traceEntry("getReason[]", this);
/*      */     try
/*      */     {
/* 1670 */       this.tsCall = this.tsCall.getHandOff();
/* 1671 */       short reason = this.tsCall.getReason();
/* 1672 */       TsapiTrace.traceExit("getReason[]", this);
/* 1673 */       return reason;
/*      */     }
/*      */     finally
/*      */     {
/* 1677 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final String getUCID()
/*      */   {
/* 1684 */     TsapiTrace.traceEntry("getUCID[]", this);
/*      */     try
/*      */     {
/* 1687 */       this.tsCall = this.tsCall.getHandOff();
/* 1688 */       String ucid = this.tsCall.getUCID();
/* 1689 */       TsapiTrace.traceExit("getUCID[]", this);
/* 1690 */       return ucid;
/*      */     }
/*      */     finally
/*      */     {
/* 1694 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final int getCallOriginatorType()
/*      */   {
/* 1701 */     TsapiTrace.traceEntry("getCallOriginatorType[]", this);
/*      */     try
/*      */     {
/* 1704 */       this.tsCall = this.tsCall.getHandOff();
/* 1705 */       int type = this.tsCall.getCallOriginatorType();
/* 1706 */       TsapiTrace.traceExit("getCallOriginatorType[]", this);
/* 1707 */       return type;
/*      */     }
/*      */     finally
/*      */     {
/* 1711 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final boolean hasCallOriginatorType()
/*      */   {
/* 1718 */     TsapiTrace.traceEntry("hasCallOriginatorType[]", this);
/*      */     try
/*      */     {
/* 1721 */       this.tsCall = this.tsCall.getHandOff();
/* 1722 */       boolean has = this.tsCall.hasCallOriginatorType();
/* 1723 */       TsapiTrace.traceExit("hasCallOriginatorType[]", this);
/* 1724 */       return has;
/*      */     }
/*      */     finally
/*      */     {
/* 1728 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final boolean canSetBillRate()
/*      */   {
/* 1735 */     TsapiTrace.traceEntry("canSetBillRate[]", this);
/*      */     try
/*      */     {
/* 1738 */       this.tsCall = this.tsCall.getHandOff();
/* 1739 */       boolean can = this.tsCall.canSetBillRate();
/* 1740 */       TsapiTrace.traceExit("canSetBillRate[]", this);
/* 1741 */       return can;
/*      */     }
/*      */     finally
/*      */     {
/* 1745 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void setBillRate(short billType, float billRate)
/*      */     throws TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException
/*      */   {
/* 1752 */     TsapiTrace.traceEntry("setBillRate[short billType, float billRate]", this);
/*      */     try
/*      */     {
/* 1755 */       this.tsCall = this.tsCall.getHandOff();
/* 1756 */       this.tsCall.setBillRate(billType, billRate);
/*      */     }
/*      */     finally
/*      */     {
/* 1760 */       this.privData = null;
/*      */     }
/* 1762 */     TsapiTrace.traceExit("setBillRate[short billType, float billRate]", this);
/*      */   }
/*      */ 
/*      */   public final void setPrivateData(Object data)
/*      */   {
/* 1768 */     TsapiTrace.traceEntry("setPrivateData[Object data]", this);
/*      */     try
/*      */     {
/* 1771 */       this.privData = TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data);
/*      */     }
/*      */     catch (ClassCastException e)
/*      */     {
/* 1775 */       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
/*      */     }
/*      */ 
/* 1778 */     TsapiTrace.traceExit("setPrivateData[Object data]", this);
/*      */   }
/*      */ 
/*      */   public final Object getPrivateData()
/*      */   {
/* 1783 */     TsapiTrace.traceEntry("getPrivateData[]", this);
/* 1784 */     this.tsCall = this.tsCall.getHandOff();
/* 1785 */     Object obj = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate)this.tsCall.getPrivateData());
/* 1786 */     TsapiTrace.traceExit("getPrivateData[]", this);
/* 1787 */     return obj;
/*      */   }
/*      */ 
/*      */   public final Object sendPrivateData(Object data)
/*      */   {
/* 1792 */     TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
/*      */     try
/*      */     {
/* 1795 */       this.tsCall = this.tsCall.getHandOff();
/* 1796 */       Object obj = this.tsCall.sendPrivateData(TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data));
/* 1797 */       TsapiTrace.traceExit("sendPrivateData[Object data]", this);
/* 1798 */       return obj;
/*      */     }
/*      */     catch (ClassCastException e)
/*      */     {
/* 1802 */       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
/*      */     }
/*      */   }
/*      */ 
/*      */   public final int getTsapiCallID()
/*      */   {
/* 1809 */     TsapiTrace.traceEntry("getTsapiCallID[]", this);
/*      */     try
/*      */     {
/* 1812 */       this.tsCall = this.tsCall.getHandOff();
/* 1813 */       int id = this.tsCall.getCallID();
/* 1814 */       TsapiTrace.traceExit("getTsapiCallID[]", this);
/* 1815 */       return id;
/*      */     }
/*      */     finally
/*      */     {
/* 1819 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addCallListener(CallListener listener)
/*      */     throws ResourceUnavailableException
/*      */   {
/* 1828 */     TsapiTrace.traceEntry("addCallListener(CallListener listener)", this);
/*      */     try {
/* 1830 */       addTsapiCallEventMonitor(null, listener);
/*      */     } catch (Exception e) {
/* 1832 */       log.error(e.getMessage(), e);
/*      */     }
/* 1834 */     TsapiTrace.traceExit("addCallListener(CallListener listener)", this);
/*      */   }
/*      */ 
/*      */   public CallListener[] getCallListeners() {
/* 1838 */     TsapiTrace.traceEntry("getCallListeners[]", this);
/*      */     try {
/* 1840 */       this.tsCall = this.tsCall.getHandOff();
/* 1841 */       Vector tsapiCallObservers = this.tsCall.getCallObservers();
/*      */ 
/* 1843 */       if ((tsapiCallObservers == null) || (tsapiCallObservers.size() == 0))
/*      */       {
/* 1845 */         TsapiTrace.traceExit("getCallListeners[]", this);
/* 1846 */         return null;
/*      */       }
/* 1848 */       ArrayList callListeners = new ArrayList();
/*      */ 
/* 1850 */       synchronized (tsapiCallObservers) {
/* 1851 */         for (Object obs : tsapiCallObservers) {
/* 1852 */           CallListener listener = ((TsapiCallMonitor) obs).getListener();
/* 1853 */           if (listener != null)
/* 1854 */             callListeners.add(listener);
/*      */         }
/*      */       }
/* 1857 */       CallListener[] callListener = new CallListener[callListeners.size()];
/* 1858 */       TsapiTrace.traceExit("getCallListeners[]", this);
/* 1859 */       return (CallListener[])callListeners.toArray(callListener);
/*      */     } finally {
/* 1861 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeCallListener(CallListener listener) {
/* 1866 */     TsapiTrace.traceEntry("removeCallListener[CallListener listener]", this);
/*      */     try
/*      */     {
/* 1869 */       this.tsCall = this.tsCall.getHandOff();
/* 1870 */       Vector tsapiCallObservers = this.tsCall.getCallObservers();
/*      */ 
/* 1873 */       if ((tsapiCallObservers == null) || (tsapiCallObservers.size() == 0))
/*      */       {
/* 1875 */         TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
/*      */ 
/* 1878 */         return;
/*      */       }
/*      */ 
/* 1881 */       for (Object obs : tsapiCallObservers)
/* 1882 */         if (((TsapiCallMonitor) obs).getListener() == listener) {
/* 1883 */           this.tsCall = this.tsCall.getHandOff();
/* 1884 */           this.tsCall.removeCallMonitor((TsapiCallMonitor) obs);
/* 1885 */           TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
/*      */ 
/* 1887 */           return;
/*      */         }
/*      */     }
/*      */     finally {
/* 1891 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public V7DeviceHistoryEntry[] getDeviceHistory()
/*      */   {
/* 1900 */     TsapiTrace.traceEntry("getDeviceHistory[]", this);
/*      */     try
/*      */     {
/* 1903 */       this.tsCall = this.tsCall.getHandOff();
/* 1904 */       V7DeviceHistoryEntry[] history = this.tsCall.getDeviceHistory();
/* 1905 */       TsapiTrace.traceExit("getDeviceHistory[]", this);
/* 1906 */       return history;
/*      */     }
/*      */     finally
/*      */     {
/* 1910 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final int hashCode()
/*      */   {
/* 1921 */     this.tsCall = this.tsCall.getHandOff();
/*      */ 
/* 1925 */     TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
/* 1926 */     return TSProviderImpl.hashCode();
/*      */   }
/*      */ 
/*      */   public boolean equals(Object obj)
/*      */   {
/* 1932 */     if (obj instanceof TsapiCall)
/*      */     {
/* 1934 */       this.tsCall = this.tsCall.getHandOff();
/* 1935 */       return this.tsCall.equals(((TsapiCall)obj).getTSCall());
/*      */     }
/*      */ 
/* 1939 */     return false;
/*      */   }
/*      */ 
/*      */   TsapiCall(TsapiProvider _provider)
/*      */   {
/* 1960 */     this(_provider, 0);
/*      */   }
/*      */ 
/*      */   TsapiCall(TsapiProvider _provider, CSTAConnectionID connID)
/*      */   {
/* 1966 */     this(_provider, connID.getCallID());
/*      */   }
/*      */ 
/*      */   TsapiCall(TsapiProvider _provider, int callID)
/*      */   {
/* 1972 */     TSProviderImpl tsProv = _provider.getTSProviderImpl();
/* 1973 */     if (tsProv != null)
/*      */     {
/* 1975 */       this.tsCall = tsProv.createTSCall(callID);
/* 1976 */       if (this.tsCall == null)
/*      */       {
/* 1978 */         throw new TsapiPlatformException(4, 0, "could not create call");
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1983 */       throw new TsapiPlatformException(4, 0, "could not locate provider");
/*      */     }
/* 1985 */     this.tsCall.referenced();
/* 1986 */     TsapiTrace.traceConstruction(this, TsapiCall.class);
/*      */   }
/*      */ 
/*      */   TsapiCall(TSProviderImpl _provider, CSTAConnectionID connID)
/*      */   {
/* 1992 */     this.tsCall = _provider.createTSCall(connID.getCallID());
/* 1993 */     if (this.tsCall == null)
/*      */     {
/* 1995 */       throw new TsapiPlatformException(4, 0, "could not create call");
/*      */     }
/*      */ 
/* 1998 */     this.tsCall.referenced();
/* 1999 */     TsapiTrace.traceConstruction(this, TsapiCall.class);
/*      */   }
/*      */ 
/*      */   TsapiCall(TSCall _tscall)
/*      */   {
/* 2005 */     _tscall = _tscall.getHandOff();
/* 2006 */     this.tsCall = _tscall;
/* 2007 */     this.tsCall.referenced();
/* 2008 */     TsapiTrace.traceConstruction(this, TsapiCall.class);
/*      */   }
/*      */ 
/*      */   protected void finalize()
/*      */     throws Throwable
/*      */   {
/* 2014 */     super.finalize();
/* 2015 */     if (this.tsCall != null)
/*      */     {
/* 2017 */       this.tsCall.unreferenced();
/* 2018 */       this.tsCall = null;
/*      */     }
/* 2020 */     TsapiTrace.traceDestruction(this, TsapiCall.class);
/*      */   }
/*      */ 
/*      */   public final TSCall getTSCall()
/*      */   {
/* 2029 */     TsapiTrace.traceEntry("getTSCall[]", this);
/* 2030 */     this.tsCall = this.tsCall.getHandOff();
/* 2031 */     TsapiTrace.traceExit("getTSCall[]", this);
/* 2032 */     return this.tsCall;
/*      */   }
/*      */ 
/*      */   // ERROR //
/*      */   public final Connection fastConnect(Terminal origterm, Address origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo, String destRoute)
/*      */     throws TsapiResourceUnavailableException, TsapiPrivilegeViolationException, TsapiInvalidPartyException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: ldc_w 263
/*      */     //   3: aload_0
/*      */     //   4: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   7: aload_1
/*      */     //   8: instanceof 22
/*      */     //   11: ifne +15 -> 26
/*      */     //   14: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   17: dup
/*      */     //   18: iconst_3
/*      */     //   19: iconst_0
/*      */     //   20: ldc 24
/*      */     //   22: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   25: athrow
/*      */     //   26: aload_2
/*      */     //   27: instanceof 26
/*      */     //   30: ifne +15 -> 45
/*      */     //   33: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   36: dup
/*      */     //   37: iconst_3
/*      */     //   38: iconst_0
/*      */     //   39: ldc 27
/*      */     //   41: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   44: athrow
/*      */     //   45: aconst_null
/*      */     //   46: astore 7
/*      */     //   48: aload_2
/*      */     //   49: checkcast 28	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   52: invokevirtual 29	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   55: astore 8
/*      */     //   57: aload_1
/*      */     //   58: checkcast 30	com/avaya/jtapi/tsapi/impl/TsapiTerminal
/*      */     //   61: invokevirtual 31	com/avaya/jtapi/tsapi/impl/TsapiTerminal:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   64: astore 9
/*      */     //   66: aload 8
/*      */     //   68: ifnull +104 -> 172
/*      */     //   71: aload 9
/*      */     //   73: ifnull +99 -> 172
/*      */     //   76: aload 8
/*      */     //   78: aload 9
/*      */     //   80: invokevirtual 32	java/lang/Object:equals	(Ljava/lang/Object;)Z
/*      */     //   83: ifeq +77 -> 160
/*      */     //   86: aload 6
/*      */     //   88: ifnonnull +21 -> 109
/*      */     //   91: iload 4
/*      */     //   93: ifne +16 -> 109
/*      */     //   96: aload 5
/*      */     //   98: ifnonnull +11 -> 109
/*      */     //   101: aload_0
/*      */     //   102: aconst_null
/*      */     //   103: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   106: goto +24 -> 130
/*      */     //   109: aload_0
/*      */     //   110: aload 6
/*      */     //   112: iload 4
/*      */     //   114: aload 5
/*      */     //   116: invokespecial 46	com/avaya/jtapi/tsapi/impl/TsapiCall:createLucentMakeCall	(Ljava/lang/String;ZLcom/avaya/jtapi/tsapi/UserToUserInfo;)Lcom/avaya/jtapi/tsapi/csta1/LucentMakeCall;
/*      */     //   119: astore 10
/*      */     //   121: aload_0
/*      */     //   122: aload 10
/*      */     //   124: invokevirtual 47	com/avaya/jtapi/tsapi/csta1/LucentMakeCall:makeTsapiPrivate	()Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   127: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   130: aload_0
/*      */     //   131: aload_0
/*      */     //   132: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   135: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSCall:getHandOff	()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   138: putfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   141: aload_0
/*      */     //   142: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiCall:tsCall	Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
/*      */     //   145: aload 8
/*      */     //   147: aload_3
/*      */     //   148: aload_0
/*      */     //   149: getfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   152: invokevirtual 264	com/avaya/jtapi/tsapi/impl/core/TSCall:fastConnect	(Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Ljava/lang/String;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Ljava/util/Vector;
/*      */     //   155: astore 7
/*      */     //   157: goto +27 -> 184
/*      */     //   160: new 23	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   163: dup
/*      */     //   164: iconst_3
/*      */     //   165: iconst_0
/*      */     //   166: ldc 34
/*      */     //   168: invokespecial 25	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   171: athrow
/*      */     //   172: new 16	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   175: dup
/*      */     //   176: iconst_4
/*      */     //   177: iconst_0
/*      */     //   178: ldc 35
/*      */     //   180: invokespecial 18	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   183: athrow
/*      */     //   184: aload 7
/*      */     //   186: ifnonnull +19 -> 205
/*      */     //   189: ldc_w 263
/*      */     //   192: aload_0
/*      */     //   193: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   196: aconst_null
/*      */     //   197: astore 10
/*      */     //   199: jsr +93 -> 292
/*      */     //   202: aload 10
/*      */     //   204: areturn
/*      */     //   205: aload 7
/*      */     //   207: dup
/*      */     //   208: astore 10
/*      */     //   210: monitorenter
/*      */     //   211: aload 7
/*      */     //   213: invokevirtual 7	java/util/Vector:size	()I
/*      */     //   216: ifne +22 -> 238
/*      */     //   219: ldc_w 263
/*      */     //   222: aload_0
/*      */     //   223: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   226: aconst_null
/*      */     //   227: astore 11
/*      */     //   229: aload 10
/*      */     //   231: monitorexit
/*      */     //   232: jsr +60 -> 292
/*      */     //   235: aload 11
/*      */     //   237: areturn
/*      */     //   238: aload 7
/*      */     //   240: iconst_0
/*      */     //   241: invokevirtual 9	java/util/Vector:elementAt	(I)Ljava/lang/Object;
/*      */     //   244: checkcast 10	com/avaya/jtapi/tsapi/impl/core/TSConnection
/*      */     //   247: iconst_1
/*      */     //   248: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   251: checkcast 8	javax/telephony/Connection
/*      */     //   254: astore 11
/*      */     //   256: ldc_w 263
/*      */     //   259: aload_0
/*      */     //   260: invokestatic 6	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   263: aload 11
/*      */     //   265: astore 12
/*      */     //   267: aload 10
/*      */     //   269: monitorexit
/*      */     //   270: jsr +22 -> 292
/*      */     //   273: aload 12
/*      */     //   275: areturn
/*      */     //   276: astore 13
/*      */     //   278: aload 10
/*      */     //   280: monitorexit
/*      */     //   281: aload 13
/*      */     //   283: athrow
/*      */     //   284: astore 14
/*      */     //   286: jsr +6 -> 292
/*      */     //   289: aload 14
/*      */     //   291: athrow
/*      */     //   292: astore 15
/*      */     //   294: aload_0
/*      */     //   295: aconst_null
/*      */     //   296: putfield 12	com/avaya/jtapi/tsapi/impl/TsapiCall:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   299: ret 15
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   211	232	276	finally
/*      */     //   238	270	276	finally
/*      */     //   276	281	276	finally
/*      */     //   7	202	284	finally
/*      */     //   205	235	284	finally
/*      */     //   238	273	284	finally
/*      */     //   276	289	284	finally
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiCall
 * JD-Core Version:    0.5.4
 */