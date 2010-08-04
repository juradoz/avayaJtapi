/*     */ package com.avaya.jtapi.tsapi.impl;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ConnectionID;
/*     */ import com.avaya.jtapi.tsapi.ITsapiConnIDPrivate;
/*     */ import com.avaya.jtapi.tsapi.ITsapiTerminalConnection;
/*     */ import com.avaya.jtapi.tsapi.LucentConnection;
/*     */ import com.avaya.jtapi.tsapi.LucentTerminalConnection;
/*     */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*     */ import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
/*     */ import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
/*     */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*     */ import com.avaya.jtapi.tsapi.TsapiPrivate;
/*     */ import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
/*     */ import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
/*     */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentClearConnection;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV6ClearConnection;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import java.net.URL;
/*     */ import javax.telephony.Address;
/*     */ import javax.telephony.InvalidArgumentException;
/*     */ import javax.telephony.PlatformException;
/*     */ import javax.telephony.Terminal;
/*     */ import javax.telephony.capabilities.TerminalConnectionCapabilities;
/*     */ import javax.telephony.privatedata.PrivateData;
/*     */ 
/*     */ class TsapiTerminalConnection
/*     */   implements ITsapiTerminalConnection, PrivateData, ITsapiConnIDPrivate
/*     */ {
/*     */   TSConnection tsConnection;
/* 682 */   CSTAPrivate privData = null;
/*     */ 
/*     */   public final int getState()
/*     */   {
/*  52 */     TsapiTrace.traceEntry("getState[]", this);
/*     */     try
/*     */     {
/*  55 */       int state = this.tsConnection.getTerminalConnectionState();
/*  56 */       TsapiTrace.traceExit("getState[]", this);
/*  57 */       return state;
/*     */     }
/*     */     finally
/*     */     {
/*  61 */       this.privData = null; }  } 
/*     */   // ERROR //
/*     */   public final Terminal getTerminal() { // Byte code:
/*     */     //   0: ldc 7
/*     */     //   2: aload_0
/*     */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   6: aload_0
/*     */     //   7: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:tsConnection	Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
/*     */     //   10: invokevirtual 8	com/avaya/jtapi/tsapi/impl/core/TSConnection:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*     */     //   13: astore_1
/*     */     //   14: aload_1
/*     */     //   15: ifnull +25 -> 40
/*     */     //   18: aload_1
/*     */     //   19: iconst_0
/*     */     //   20: invokestatic 9	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*     */     //   23: checkcast 10	javax/telephony/Terminal
/*     */     //   26: astore_2
/*     */     //   27: ldc 7
/*     */     //   29: aload_0
/*     */     //   30: invokestatic 5	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   33: aload_2
/*     */     //   34: astore_3
/*     */     //   35: jsr +25 -> 60
/*     */     //   38: aload_3
/*     */     //   39: areturn
/*     */     //   40: new 11	com/avaya/jtapi/tsapi/TsapiPlatformException
/*     */     //   43: dup
/*     */     //   44: iconst_4
/*     */     //   45: iconst_0
/*     */     //   46: ldc 12
/*     */     //   48: invokespecial 13	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*     */     //   51: athrow
/*     */     //   52: astore 4
/*     */     //   54: jsr +6 -> 60
/*     */     //   57: aload 4
/*     */     //   59: athrow
/*     */     //   60: astore 5
/*     */     //   62: aload_0
/*     */     //   63: aconst_null
/*     */     //   64: putfield 6	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*     */     //   67: ret 5
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	38	52	finally
/*     */     //   40	57	52	finally } 
/*     */   // ERROR //
/*     */   public final javax.telephony.Connection getConnection() { // Byte code:
/*     */     //   0: ldc 14
/*     */     //   2: aload_0
/*     */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   6: aload_0
/*     */     //   7: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:tsConnection	Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
/*     */     //   10: invokevirtual 15	com/avaya/jtapi/tsapi/impl/core/TSConnection:getTSConnection	()Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
/*     */     //   13: astore_1
/*     */     //   14: aload_1
/*     */     //   15: ifnull +25 -> 40
/*     */     //   18: aload_1
/*     */     //   19: iconst_1
/*     */     //   20: invokestatic 9	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*     */     //   23: checkcast 16	javax/telephony/Connection
/*     */     //   26: astore_2
/*     */     //   27: ldc 14
/*     */     //   29: aload_0
/*     */     //   30: invokestatic 5	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   33: aload_2
/*     */     //   34: astore_3
/*     */     //   35: jsr +25 -> 60
/*     */     //   38: aload_3
/*     */     //   39: areturn
/*     */     //   40: new 11	com/avaya/jtapi/tsapi/TsapiPlatformException
/*     */     //   43: dup
/*     */     //   44: iconst_4
/*     */     //   45: iconst_0
/*     */     //   46: ldc 17
/*     */     //   48: invokespecial 13	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*     */     //   51: athrow
/*     */     //   52: astore 4
/*     */     //   54: jsr +6 -> 60
/*     */     //   57: aload 4
/*     */     //   59: athrow
/*     */     //   60: astore 5
/*     */     //   62: aload_0
/*     */     //   63: aconst_null
/*     */     //   64: putfield 6	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*     */     //   67: ret 5
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	38	52	finally
/*     */     //   40	57	52	finally } 
/* 124 */   public final void answer() throws TsapiPrivilegeViolationException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException, TsapiInvalidStateException { TsapiTrace.traceEntry("answer[]", this);
/*     */     try
/*     */     {
/* 127 */       this.tsConnection.answer(this.privData);
/*     */     }
/*     */     finally
/*     */     {
/* 131 */       this.privData = null;
/*     */     }
/* 133 */     TsapiTrace.traceExit("answer[]", this); }
/*     */ 
/*     */ 
/*     */   public final TerminalConnectionCapabilities getCapabilities()
/*     */   {
/* 138 */     TsapiTrace.traceEntry("getCapabilities[]", this);
/*     */     try
/*     */     {
/* 141 */       TerminalConnectionCapabilities caps = this.tsConnection.getTsapiTermConnCapabilities();
/* 142 */       TsapiTrace.traceExit("getCapabilities[]", this);
/* 143 */       return caps;
/*     */     }
/*     */     finally
/*     */     {
/* 147 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final TerminalConnectionCapabilities getTerminalConnectionCapabilities(Terminal terminal, Address address)
/*     */     throws InvalidArgumentException, PlatformException
/*     */   {
/* 155 */     TsapiTrace.traceEntry("getTerminalConnectionCapabilities[Terminal terminal, Address address]", this);
/* 156 */     TerminalConnectionCapabilities caps = getCapabilities();
/* 157 */     TsapiTrace.traceExit("getTerminalConnectionCapabilities[Terminal terminal, Address address]", this);
/* 158 */     return caps;
/*     */   }
/*     */ 
/*     */   public final int getCallControlState()
/*     */   {
/* 166 */     TsapiTrace.traceEntry("getCallControlState[]", this);
/*     */     try
/*     */     {
/* 169 */       int state = this.tsConnection.getCallControlTerminalConnectionState();
/* 170 */       TsapiTrace.traceExit("getCallControlState[]", this);
/* 171 */       return state;
/*     */     }
/*     */     finally
/*     */     {
/* 175 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void hold()
/*     */     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*     */   {
/* 189 */     TsapiTrace.traceEntry("hold[]", this);
/*     */     try
/*     */     {
/* 192 */       this.tsConnection.hold(this.privData);
/*     */     }
/*     */     finally
/*     */     {
/* 196 */       this.privData = null;
/*     */     }
/* 198 */     TsapiTrace.traceExit("hold[]", this);
/*     */   }
/*     */ 
/*     */   public final void unhold()
/*     */     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*     */   {
/* 211 */     TsapiTrace.traceEntry("unhold[]", this);
/*     */     try
/*     */     {
/* 214 */       this.tsConnection.unhold(this.privData);
/*     */     }
/*     */     finally
/*     */     {
/* 218 */       this.privData = null;
/*     */     }
/* 220 */     TsapiTrace.traceExit("unhold[]", this);
/*     */   }
/*     */ 
/*     */   public final void listenHold(LucentTerminalConnection partyToHold)
/*     */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*     */   {
/* 233 */     TsapiTrace.traceEntry("listenHold[LucentTerminalConnection partyToHold]", this);
/*     */     try
/*     */     {
/* 236 */       TSConnection party = (partyToHold == null) ? null : ((TsapiTerminalConnection)partyToHold).tsConnection;
/*     */ 
/* 238 */       this.tsConnection.listenHold(party);
/*     */     }
/*     */     finally
/*     */     {
/* 242 */       this.privData = null;
/*     */     }
/* 244 */     TsapiTrace.traceExit("listenHold[LucentTerminalConnection partyToHold]", this);
/*     */   }
/*     */ 
/*     */   public final void listenUnhold(LucentTerminalConnection partyToUnhold)
/*     */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*     */   {
/* 257 */     TsapiTrace.traceEntry("listenUnhold[LucentTerminalConnection partyToUnhold]", this);
/*     */     try
/*     */     {
/* 260 */       TSConnection party = (partyToUnhold == null) ? null : ((TsapiTerminalConnection)partyToUnhold).tsConnection;
/*     */ 
/* 262 */       this.tsConnection.listenUnhold(party);
/*     */     }
/*     */     finally
/*     */     {
/* 266 */       this.privData = null;
/*     */     }
/* 268 */     TsapiTrace.traceExit("listenUnhold[LucentTerminalConnection partyToUnhold]", this);
/*     */   }
/*     */ 
/*     */   public final void listenHold(LucentConnection partyToHold)
/*     */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*     */   {
/* 281 */     TsapiTrace.traceEntry("listenHold[LucentTerminalConnection partyToHold]", this);
/*     */     try
/*     */     {
/* 284 */       TSConnection party = (partyToHold == null) ? null : ((TsapiConnection)partyToHold).tsConnection;
/*     */ 
/* 286 */       this.tsConnection.listenHold(party);
/*     */     }
/*     */     finally
/*     */     {
/* 290 */       this.privData = null;
/*     */     }
/* 292 */     TsapiTrace.traceExit("listenHold[LucentTerminalConnection partyToHold]", this);
/*     */   }
/*     */ 
/*     */   public final void listenUnhold(LucentConnection partyToUnhold)
/*     */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*     */   {
/* 305 */     TsapiTrace.traceEntry("listenUnhold[LucentTerminalConnection partyToUnhold]", this);
/*     */     try
/*     */     {
/* 308 */       TSConnection party = (partyToUnhold == null) ? null : ((TsapiConnection)partyToUnhold).tsConnection;
/*     */ 
/* 310 */       this.tsConnection.listenUnhold(party);
/*     */     }
/*     */     finally
/*     */     {
/* 314 */       this.privData = null;
/*     */     }
/* 316 */     TsapiTrace.traceExit("listenUnhold[LucentTerminalConnection partyToUnhold]", this);
/*     */   }
/*     */ 
/*     */   public final void join()
/*     */     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*     */   {
/* 329 */     TsapiTrace.traceEntry("join[]", this);
/*     */     try
/*     */     {
/* 332 */       this.tsConnection.join(this.privData);
/*     */     }
/*     */     finally
/*     */     {
/* 336 */       this.privData = null;
/*     */     }
/* 338 */     TsapiTrace.traceExit("join[]", this);
/*     */   }
/*     */ 
/*     */   public final void leave()
/*     */     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*     */   {
/* 351 */     TsapiTrace.traceEntry("leave[]", this);
/*     */     try
/*     */     {
/* 354 */       this.tsConnection.leave(this.privData);
/*     */     }
/*     */     finally
/*     */     {
/* 358 */       this.privData = null;
/*     */     }
/* 360 */     TsapiTrace.traceExit("leave[]", this);
/*     */   }
/*     */ 
/*     */   private LucentClearConnection createLucentClearConnection(short dropRes, UserToUserInfo uui)
/*     */   {
/* 366 */     TsapiTrace.traceEntry("createLucentClearConnection[short dropRes, UserToUserInfo uui]", this);
/* 367 */     TSProviderImpl provider = this.tsConnection.getTSProviderImpl();
/* 368 */     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(uui);
/*     */ 
/* 370 */     LucentClearConnection conn = null;
/* 371 */     if (provider != null)
/*     */     {
/* 373 */       if (provider.isLucentV6())
/*     */       {
/* 375 */         conn = new LucentV6ClearConnection(dropRes, asn_uui);
/*     */       }
/*     */       else
/*     */       {
/* 379 */         conn = new LucentClearConnection(dropRes, asn_uui);
/*     */       }
/*     */     }
/* 382 */     TsapiTrace.traceExit("createLucentClearConnection[short dropRes, UserToUserInfo uui]", this);
/* 383 */     return conn;
/*     */   }
/*     */ 
/*     */   public final void leave(short dropResource, UserToUserInfo userInfo)
/*     */     throws TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*     */   {
/* 391 */     TsapiTrace.traceEntry("leave[short dropResource, UserToUserInfo userInfo]", this);
/* 392 */     LucentClearConnection lcc = createLucentClearConnection(dropResource, userInfo);
/* 393 */     this.privData = lcc.makeTsapiPrivate();
/* 394 */     leave();
/* 395 */     TsapiTrace.traceExit("leave[short dropResource, UserToUserInfo userInfo]", this);
/*     */   }
/*     */ 
/*     */   public final void generateDtmf(String digits)
/*     */     throws TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiInvalidStateException
/*     */   {
/* 406 */     TsapiTrace.traceEntry("generateDtmf[String digits]", this);
/*     */     try
/*     */     {
/* 409 */       this.tsConnection.generateDtmf(digits);
/*     */     }
/*     */     finally
/*     */     {
/* 413 */       this.privData = null;
/*     */     }
/* 415 */     TsapiTrace.traceExit("generateDtmf[String digits]", this);
/*     */   }
/*     */ 
/*     */   public final void generateDtmf(String digits, int toneDuration, int pauseDuration)
/*     */     throws TsapiMethodNotSupportedException, TsapiResourceUnavailableException, TsapiInvalidStateException
/*     */   {
/* 426 */     TsapiTrace.traceEntry("generateDtmf[String digits, int toneDuration, int pauseDuration]", this);
/*     */     try
/*     */     {
/* 429 */       this.tsConnection.generateDtmf(digits, toneDuration, pauseDuration);
/*     */     }
/*     */     finally
/*     */     {
/* 433 */       this.privData = null;
/*     */     }
/* 435 */     TsapiTrace.traceExit("generateDtmf[String digits, int toneDuration, int pauseDuration]", this);
/*     */   }
/*     */ 
/*     */   public final int getMediaAvailability()
/*     */   {
/* 440 */     TsapiTrace.traceEntry("getMediaAvailability[]", this);
/*     */     try
/*     */     {
/* 443 */       TsapiTrace.traceExit("getMediaAvailability[]", this);
/* 444 */       return 129;
/*     */     }
/*     */     finally
/*     */     {
/* 448 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final int getMediaState()
/*     */   {
/* 454 */     TsapiTrace.traceEntry("getMediaState[]", this);
/*     */     try
/*     */     {
/* 457 */       TsapiTrace.traceExit("getMediaState[]", this);
/* 458 */       return 0;
/*     */     }
/*     */     finally
/*     */     {
/* 462 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void useDefaultSpeaker()
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/* 469 */     TsapiTrace.traceEntry("useDefaultSpeaker[]", this);
/*     */     try
/*     */     {
/* 472 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*     */     }
/*     */     finally
/*     */     {
/* 476 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void useRecordURL(URL url)
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/* 483 */     TsapiTrace.traceEntry("useRecordURL[URL url]", this);
/*     */     try
/*     */     {
/* 486 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*     */     }
/*     */     finally
/*     */     {
/* 490 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void useDefaultMicrophone()
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/* 497 */     TsapiTrace.traceEntry("useDefaultMicrophone[]", this);
/*     */     try
/*     */     {
/* 500 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*     */     }
/*     */     finally
/*     */     {
/* 504 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void usePlayURL(URL url)
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/* 511 */     TsapiTrace.traceEntry("usePlayURL[URL url]", this);
/*     */     try
/*     */     {
/* 514 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*     */     }
/*     */     finally
/*     */     {
/* 518 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void startPlaying()
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/* 525 */     TsapiTrace.traceEntry("startPlaying[]", this);
/*     */     try
/*     */     {
/* 528 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*     */     }
/*     */     finally
/*     */     {
/* 532 */       this.privData = null; }  } 
/*     */   // ERROR //
/*     */   public final void stopPlaying() { // Byte code:
/*     */     //   0: ldc 67
/*     */     //   2: aload_0
/*     */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   6: jsr +12 -> 18
/*     */     //   9: goto +17 -> 26
/*     */     //   12: astore_1
/*     */     //   13: jsr +5 -> 18
/*     */     //   16: aload_1
/*     */     //   17: athrow
/*     */     //   18: astore_2
/*     */     //   19: aload_0
/*     */     //   20: aconst_null
/*     */     //   21: putfield 6	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*     */     //   24: ret 2
/*     */     //   26: ldc 67
/*     */     //   28: aload_0
/*     */     //   29: invokestatic 5	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   32: return
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	9	12	finally
/*     */     //   12	16	12	finally } 
/* 553 */   public final void startRecording() throws TsapiMethodNotSupportedException { TsapiTrace.traceEntry("startRecording[]", this);
/*     */     try
/*     */     {
/* 556 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*     */     }
/*     */     finally
/*     */     {
/* 560 */       this.privData = null; }  } 
/*     */   // ERROR //
/*     */   public final void stopRecording() { // Byte code:
/*     */     //   0: ldc 69
/*     */     //   2: aload_0
/*     */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   6: jsr +12 -> 18
/*     */     //   9: goto +17 -> 26
/*     */     //   12: astore_1
/*     */     //   13: jsr +5 -> 18
/*     */     //   16: aload_1
/*     */     //   17: athrow
/*     */     //   18: astore_2
/*     */     //   19: aload_0
/*     */     //   20: aconst_null
/*     */     //   21: putfield 6	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*     */     //   24: ret 2
/*     */     //   26: ldc 69
/*     */     //   28: aload_0
/*     */     //   29: invokestatic 5	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   32: return
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	9	12	finally
/*     */     //   12	16	12	finally } 
/* 581 */   public final void setDtmfDetection(boolean enable) throws TsapiMethodNotSupportedException { TsapiTrace.traceEntry("setDtmfDetection[boolean enable]", this);
/*     */     try
/*     */     {
/* 584 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*     */     }
/*     */     finally
/*     */     {
/* 588 */       this.privData = null;
/*     */     } }
/*     */ 
/*     */ 
/*     */   public final void setPrivateData(Object data)
/*     */   {
/* 595 */     TsapiTrace.traceEntry("setPrivateData[Object data]", this);
/*     */     try
/*     */     {
/* 598 */       this.privData = TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data);
/*     */     }
/*     */     catch (ClassCastException e)
/*     */     {
/* 602 */       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
/*     */     }
/*     */ 
/* 605 */     TsapiTrace.traceExit("setPrivateData[Object data]", this);
/*     */   }
/*     */ 
/*     */   public final Object getPrivateData()
/*     */   {
/* 610 */     TsapiTrace.traceEntry("getPrivateData[]", this);
/* 611 */     Object obj = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate)this.tsConnection.getTermConnPrivateData());
/* 612 */     TsapiTrace.traceExit("getPrivateData[]", this);
/* 613 */     return obj;
/*     */   }
/*     */ 
/*     */   public final Object sendPrivateData(Object data)
/*     */   {
/* 618 */     TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
/*     */     try
/*     */     {
/* 621 */       Object obj = this.tsConnection.sendPrivateData(TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data));
/* 622 */       TsapiTrace.traceExit("sendPrivateData[Object data]", this);
/* 623 */       return obj;
/*     */     }
/*     */     catch (ClassCastException e)
/*     */     {
/* 627 */       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
/*     */     }
/*     */   }
/*     */ 
/*     */   public final ConnectionID getTsapiConnectionID()
/*     */   {
/* 634 */     TsapiTrace.traceEntry("getTsapiConnectionID[]", this);
/*     */     try
/*     */     {
/* 637 */       CSTAConnectionID cstaConnectionID = this.tsConnection.getConnID();
/* 638 */       ConnectionID id = new ConnectionID(cstaConnectionID.getCallID(), cstaConnectionID.getDeviceID(), (short)cstaConnectionID.getDevIDType());
/*     */ 
/* 641 */       TsapiTrace.traceExit("getTsapiConnectionID[]", this);
/* 642 */       return id;
/*     */     }
/*     */     finally
/*     */     {
/* 646 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final int hashCode()
/*     */   {
/* 652 */     return this.tsConnection.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 658 */     if (obj instanceof TsapiTerminalConnection)
/*     */     {
/* 660 */       return this.tsConnection.equals(((TsapiTerminalConnection)obj).tsConnection);
/*     */     }
/*     */ 
/* 664 */     return false;
/*     */   }
/*     */ 
/*     */   TsapiTerminalConnection(TSConnection _tsConnection)
/*     */   {
/* 687 */     this.tsConnection = _tsConnection;
/* 688 */     TsapiTrace.traceConstruction(this, TsapiTerminalConnection.class);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 694 */     super.finalize();
/* 695 */     TsapiTrace.traceDestruction(this, TsapiTerminalConnection.class);
/*     */   }
/*     */ 
/*     */   final TSConnection getTSConnection()
/*     */   {
/* 703 */     TsapiTrace.traceEntry("getTSConnection[]", this);
/* 704 */     TsapiTrace.traceExit("getTSConnection[]", this);
/* 705 */     return this.tsConnection;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiTerminalConnection
 * JD-Core Version:    0.5.4
 */