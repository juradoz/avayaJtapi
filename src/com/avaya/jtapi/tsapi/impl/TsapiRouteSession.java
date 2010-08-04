/*     */ package com.avaya.jtapi.tsapi.impl;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ITsapiCallInfo;
/*     */ import com.avaya.jtapi.tsapi.ITsapiRouteSession;
/*     */ import com.avaya.jtapi.tsapi.LookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.LucentAgent;
/*     */ import com.avaya.jtapi.tsapi.OriginalCallInfo;
/*     */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*     */ import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
/*     */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*     */ import com.avaya.jtapi.tsapi.TsapiPrivate;
/*     */ import com.avaya.jtapi.tsapi.TsapiTrunk;
/*     */ import com.avaya.jtapi.tsapi.UserEnteredCode;
/*     */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentRouteSelect;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentUserCollectCode;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentUserProvidedCode;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV6RouteSelect;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV7RouteSelect;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import javax.telephony.Address;
/*     */ import javax.telephony.Call;
/*     */ import javax.telephony.callcenter.ACDAddress;
/*     */ import javax.telephony.callcenter.CallCenterAddress;
/*     */ import javax.telephony.callcenter.CallCenterTrunk;
/*     */ import javax.telephony.privatedata.PrivateData;
/*     */ 
/*     */ public class TsapiRouteSession
/*     */   implements ITsapiRouteSession, ITsapiCallInfo, PrivateData
/*     */ {
/*     */   TSRouteSession tsRouteSession;
/* 663 */   CSTAPrivate privData = null;
/*     */ 
/*     */   // ERROR //
/*     */   public final javax.telephony.callcenter.RouteAddress getRouteAddress()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 1
/*     */     //   2: aload_0
/*     */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   6: aload_0
/*     */     //   7: getfield 3	com/avaya/jtapi/tsapi/impl/TsapiRouteSession:tsRouteSession	Lcom/avaya/jtapi/tsapi/impl/core/TSRouteSession;
/*     */     //   10: invokevirtual 4	com/avaya/jtapi/tsapi/impl/core/TSRouteSession:getTSRouteDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*     */     //   13: astore_1
/*     */     //   14: aload_1
/*     */     //   15: ifnull +25 -> 40
/*     */     //   18: aload_1
/*     */     //   19: iconst_1
/*     */     //   20: invokestatic 5	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*     */     //   23: checkcast 6	javax/telephony/callcenter/RouteAddress
/*     */     //   26: astore_2
/*     */     //   27: ldc 1
/*     */     //   29: aload_0
/*     */     //   30: invokestatic 7	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   33: aload_2
/*     */     //   34: astore_3
/*     */     //   35: jsr +25 -> 60
/*     */     //   38: aload_3
/*     */     //   39: areturn
/*     */     //   40: new 8	com/avaya/jtapi/tsapi/TsapiPlatformException
/*     */     //   43: dup
/*     */     //   44: iconst_4
/*     */     //   45: iconst_0
/*     */     //   46: ldc 9
/*     */     //   48: invokespecial 10	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*     */     //   51: athrow
/*     */     //   52: astore 4
/*     */     //   54: jsr +6 -> 60
/*     */     //   57: aload 4
/*     */     //   59: athrow
/*     */     //   60: astore 5
/*     */     //   62: aload_0
/*     */     //   63: aconst_null
/*     */     //   64: putfield 11	com/avaya/jtapi/tsapi/impl/TsapiRouteSession:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*     */     //   67: ret 5
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	38	52	finally
/*     */     //   40	57	52	finally
/*     */   }
/*     */ 
/*     */   public final void selectRoute(String[] routeSelected)
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/*  79 */     TsapiTrace.traceEntry("selectRoute[String[] routeSelected]", this);
/*     */     try
/*     */     {
/*  82 */       this.tsRouteSession.tsSelectRoute(routeSelected, this.privData);
/*     */     }
/*     */     finally
/*     */     {
/*  86 */       this.privData = null;
/*     */     }
/*  88 */     TsapiTrace.traceExit("selectRoute[String[] routeSelected]", this);
/*     */   }
/*     */ 
/*     */   private LucentRouteSelect createLucentRouteSelect(String callingDevice, String directAgentCallSplit, boolean priorityCalling, String destRoute, LucentUserCollectCode collectCode, LucentUserProvidedCode userProvidedCode, UserToUserInfo userInfo, short networkRedirectCallType)
/*     */   {
/* 103 */     TsapiTrace.traceEntry("createLucentRouteSelect[String callingDevice, String directAgentCallSplit, boolean priorityCalling, String destRoute, UserCollectCode collectCode, UserProvidedCode userProvidedCode, UserToUserInfo userInfo, short networkRedirectCallType]", this);
/* 104 */     TSProviderImpl provider = this.tsRouteSession.getTSProviderImpl();
/* 105 */     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(userInfo);
/*     */ 
/* 107 */     LucentRouteSelect route = null;
/* 108 */     if (provider != null)
/*     */     {
/* 110 */       if (provider.isLucentV7())
/*     */       {
/* 112 */         route = new LucentV7RouteSelect(callingDevice, directAgentCallSplit, priorityCalling, destRoute, collectCode, userProvidedCode, asn_uui, networkRedirectCallType);
/*     */       }
/* 124 */       else if (provider.isLucentV6()) {
/* 125 */         route = new LucentV6RouteSelect(callingDevice, directAgentCallSplit, priorityCalling, destRoute, collectCode, userProvidedCode, asn_uui);
/*     */       }
/*     */       else
/*     */       {
/* 135 */         route = new LucentRouteSelect(callingDevice, directAgentCallSplit, priorityCalling, destRoute, collectCode, userProvidedCode, asn_uui);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 147 */       route = null;
/*     */     }
/* 149 */     TsapiTrace.traceExit("createLucentRouteSelect[String callingDevice, String directAgentCallSplit, boolean priorityCalling, String destRoute, UserCollectCode collectCode, UserProvidedCode userProvidedCode, UserToUserInfo userInfo, short networkRedirectCallType]", this);
/* 150 */     return route;
/*     */   }
/*     */ 
/*     */   public final void selectRoute(String routeSelected, boolean priorityCall, UserToUserInfo userInfo)
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/* 156 */     TsapiTrace.traceEntry("selectRoute[String routeSelected, boolean priorityCall, UserToUserInfo userInfo]", this);
/* 157 */     String[] routes = new String[1];
/* 158 */     routes[0] = routeSelected;
/*     */     try
/*     */     {
/* 161 */       LucentRouteSelect lrs = createLucentRouteSelect(null, null, priorityCall, null, null, null, userInfo, (short) 0);
/* 162 */       this.privData = lrs.makeTsapiPrivate();
/* 163 */       this.tsRouteSession.tsSelectRoute(routes, this.privData);
/*     */     }
/*     */     finally
/*     */     {
/* 167 */       this.privData = null;
/*     */     }
/* 169 */     TsapiTrace.traceExit("selectRoute[String routeSelected, boolean priorityCall, UserToUserInfo userInfo]", this);
/*     */   }
/*     */ 
/*     */   public final void selectRouteDirectAgent(LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo)
/*     */     throws TsapiMethodNotSupportedException, TsapiInvalidArgumentException
/*     */   {
/* 175 */     TsapiTrace.traceEntry("selectRouteDirectAgent[LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
/*     */     try
/*     */     {
/* 178 */       if (calledAgent == null) {
/* 179 */         throw new TsapiInvalidArgumentException(3, 0, "called Agent is null");
/*     */       }
/*     */ 
/* 182 */       LucentRouteSelect lrs = createLucentRouteSelect(null, calledAgent.getACDAddress().getName(), priorityCall, null, null, null, userInfo, (short) 0);
/*     */ 
/* 184 */       this.privData = lrs.makeTsapiPrivate();
/* 185 */       String[] routes = new String[1];
/* 186 */       routes[0] = calledAgent.getAgentAddress().getName();
/* 187 */       this.tsRouteSession.tsSelectRoute(routes, this.privData);
/*     */     }
/*     */     finally
/*     */     {
/* 191 */       this.privData = null;
/*     */     }
/* 193 */     TsapiTrace.traceExit("selectRouteDirectAgent[LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]", this);
/*     */   }
/*     */ 
/*     */   public final void selectRouteAndCollect(String routeSelected, int digitsToBeCollected, int timeout, boolean priorityCall, UserToUserInfo userInfo)
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/* 200 */     TsapiTrace.traceEntry("selectRouteAndCollect[String routeSelected, int digitsToBeCollected, int timeout, boolean priorityCall, UserToUserInfo userInfo]", this);
/*     */     try
/*     */     {
/* 203 */       LucentUserCollectCode ucc = new LucentUserCollectCode((short)32, digitsToBeCollected, timeout, null, (short)11);
/*     */ 
/* 205 */       LucentRouteSelect lrs = createLucentRouteSelect(null, null, priorityCall, null, ucc, null, userInfo, (short)0);
/*     */ 
/* 207 */       this.privData = lrs.makeTsapiPrivate();
/* 208 */       String[] routes = new String[1];
/* 209 */       routes[0] = routeSelected;
/* 210 */       this.tsRouteSession.tsSelectRoute(routes, this.privData);
/*     */     }
/*     */     finally
/*     */     {
/* 214 */       this.privData = null;
/*     */     }
/* 216 */     TsapiTrace.traceExit("selectRouteAndCollect[String routeSelected, int digitsToBeCollected, int timeout, boolean priorityCall, UserToUserInfo userInfo]", this);
/*     */   }
/*     */ 
/*     */   public final void selectRouteWithDigits(String routeSelected, String digits, boolean priorityCall, UserToUserInfo userInfo)
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/* 223 */     TsapiTrace.traceEntry("selectRouteWithDigits[String routeSelected, String digits, boolean priorityCall, UserToUserInfo userInfo]", this);
/*     */     try
/*     */     {
/* 227 */       LucentUserProvidedCode upc;
/* 227 */       if (digits == null) upc = null; else {
/* 228 */         upc = new LucentUserProvidedCode((short) 17, digits);
/*     */       }
/* 230 */       LucentRouteSelect lrs = createLucentRouteSelect(null, null, priorityCall, null, null, upc, userInfo, (short) 0);
/*     */ 
/* 232 */       this.privData = lrs.makeTsapiPrivate();
/* 233 */       String[] routes = new String[1];
/* 234 */       routes[0] = routeSelected;
/* 235 */       this.tsRouteSession.tsSelectRoute(routes, this.privData);
/*     */     }
/*     */     finally
/*     */     {
/* 239 */       this.privData = null;
/*     */     }
/* 241 */     TsapiTrace.traceExit("selectRouteWithDigits[String routeSelected, String digits, boolean priorityCall, UserToUserInfo userInfo]", this);
/*     */   }
/*     */ 
/*     */   public final void endRoute(int errorValue)
/*     */   {
/* 251 */     TsapiTrace.traceEntry("endRoute[int errorValue]", this);
/*     */     try
/*     */     {
/* 254 */       this.tsRouteSession.tsEndRoute(errorValue, this.privData);
/*     */     }
/*     */     finally
/*     */     {
/* 258 */       this.privData = null;
/*     */     }
/* 260 */     TsapiTrace.traceExit("endRoute[int errorValue]", this);
/*     */   }
/*     */ 
/*     */   public final int getState()
/*     */   {
/* 270 */     TsapiTrace.traceEntry("getState[]", this);
/*     */     try
/*     */     {
/* 273 */       int state = this.tsRouteSession.tsGetState();
/* 274 */       TsapiTrace.traceExit("getState[]", this);
/* 275 */       return state;
/*     */     }
/*     */     finally
/*     */     {
/* 279 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final int getCause()
/*     */   {
/* 290 */     TsapiTrace.traceEntry("getCause[]", this);
/*     */     try
/*     */     {
/* 293 */       int i = this.tsRouteSession.tsGetCause();
/* 294 */       TsapiTrace.traceExit("getCause[]", this);
/* 295 */       return i;
/*     */     }
/*     */     finally
/*     */     {
/* 299 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final UserToUserInfo getUserToUserInfo()
/*     */   {
/* 306 */     TsapiTrace.traceEntry("getUserToUserInfo[]", this);
/*     */     try
/*     */     {
/* 309 */       UserToUserInfo uui = this.tsRouteSession.getUUI();
/* 310 */       TsapiTrace.traceExit("getUserToUserInfo[]", this);
/* 311 */       return uui;
/*     */     }
/*     */     finally
/*     */     {
/* 315 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final LookaheadInfo getLookaheadInfo()
/*     */   {
/* 322 */     TsapiTrace.traceEntry("getLookaheadInfo[]", this);
/*     */     try
/*     */     {
/* 325 */       LookaheadInfo lai = this.tsRouteSession.getLAI();
/* 326 */       TsapiTrace.traceExit("getLookaheadInfo[]", this);
/* 327 */       return lai;
/*     */     }
/*     */     finally
/*     */     {
/* 331 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final UserEnteredCode getUserEnteredCode()
/*     */   {
/* 338 */     TsapiTrace.traceEntry("getUserEnteredCode[]", this);
/*     */     try
/*     */     {
/* 341 */       UserEnteredCode uec = this.tsRouteSession.getUEC();
/* 342 */       TsapiTrace.traceExit("getUserEnteredCode[]", this);
/* 343 */       return uec;
/*     */     }
/*     */     finally
/*     */     {
/* 347 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final CallCenterTrunk getTrunk()
/*     */   {
/* 353 */     TsapiTrace.traceEntry("getTrunk[]", this);
/*     */     try
/*     */     {
/* 356 */       TSTrunk tsTrunk = this.tsRouteSession.getTrunk();
/*     */       CallCenterTrunk trunk;
/* 357 */       if (tsTrunk != null)
/*     */       {
/* 359 */         trunk = (TsapiTrunk)TsapiCreateObject.getTsapiObject(tsTrunk, false);
/* 360 */         TsapiTrace.traceExit("getTrunk[]", this);
/* 361 */         return trunk;
/*     */       }
/*     */ 
/* 365 */       TsapiTrace.traceExit("getTrunk[]", this);
/* 366 */       return null;
/*     */     }
/*     */     finally
/*     */     {
/* 371 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final OriginalCallInfo getOriginalCallInfo()
/*     */   {
/* 378 */     TsapiTrace.traceEntry("getOriginalCallInfo[]", this);
/*     */     try
/*     */     {
/* 381 */       TsapiTrace.traceExit("getOriginalCallInfo[]", this);
/* 382 */       return null;
/*     */     }
/*     */     finally
/*     */     {
/* 386 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final CallCenterAddress getDistributingAddress()
/*     */   {
/* 393 */     TsapiTrace.traceEntry("getDistributingAddress[]", this);
/*     */     try
/*     */     {
/* 396 */       TsapiTrace.traceExit("getDistributingAddress[]", this);
/* 397 */       return null;
/*     */     }
/*     */     finally
/*     */     {
/* 401 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final CallCenterAddress getDistributingVDNAddress()
/*     */   {
/* 408 */     TsapiTrace.traceEntry("getDistributingVDNAddress[]", this);
/*     */     try
/*     */     {
/* 411 */       TsapiTrace.traceExit("getDistributingVDNAddress[]", this);
/* 412 */       return null;
/*     */     }
/*     */     finally
/*     */     {
/* 416 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final ACDAddress getDeliveringACDAddress()
/*     */   {
/* 423 */     TsapiTrace.traceEntry("getDeliveringACDAddress[]", this);
/*     */     try
/*     */     {
/* 426 */       TsapiTrace.traceExit("getDeliveringACDAddress[]", this);
/* 427 */       return null;
/*     */     }
/*     */     finally
/*     */     {
/* 431 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final short getReason()
/*     */   {
/* 438 */     TsapiTrace.traceEntry("getReason[]", this);
/*     */     try
/*     */     {
/* 441 */       TsapiTrace.traceExit("getReason[]", this);
/* 442 */       return 0;
/*     */     }
/*     */     finally
/*     */     {
/* 446 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final String getUCID()
/*     */   {
/* 453 */     TsapiTrace.traceEntry("getUCID[]", this);
/*     */     try
/*     */     {
/* 456 */       String ucid = this.tsRouteSession.getUCID();
/* 457 */       TsapiTrace.traceExit("getUCID[]", this);
/* 458 */       return ucid;
/*     */     }
/*     */     finally
/*     */     {
/* 462 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final int getCallOriginatorType()
/*     */   {
/* 469 */     TsapiTrace.traceEntry("getCallOriginatorType[]", this);
/*     */     try
/*     */     {
/* 472 */       int type = this.tsRouteSession.getCallOriginatorType();
/* 473 */       TsapiTrace.traceExit("getCallOriginatorType[]", this);
/* 474 */       return type;
/*     */     }
/*     */     finally
/*     */     {
/* 478 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final Call getCall()
/*     */   {
/* 485 */     TsapiTrace.traceEntry("getCall[]", this);
/*     */     try
/*     */     {
/* 488 */       Call call = (Call)TsapiCreateObject.getTsapiObject(this.tsRouteSession.getCall(), false);
/* 489 */       TsapiTrace.traceExit("getCall[]", this);
/* 490 */       return call;
/*     */     }
/*     */     finally
/*     */     {
/* 494 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean hasCallOriginatorType()
/*     */   {
/* 501 */     TsapiTrace.traceEntry("hasCallOriginatorType[]", this);
/*     */     try
/*     */     {
/* 504 */       boolean has = this.tsRouteSession.hasCallOriginatorType();
/* 505 */       TsapiTrace.traceExit("hasCallOriginatorType[]", this);
/* 506 */       return has;
/*     */     }
/*     */     finally
/*     */     {
/* 510 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean canSetBillRate()
/*     */   {
/* 517 */     TsapiTrace.traceEntry("canSetBillRate[]", this);
/*     */     try
/*     */     {
/* 520 */       boolean can = this.tsRouteSession.canSetBillRate();
/* 521 */       TsapiTrace.traceExit("canSetBillRate[]", this);
/* 522 */       return can;
/*     */     }
/*     */     finally
/*     */     {
/* 526 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void selectRouteWithNetworkRedirection(String routeSelected, UserToUserInfo userInfo)
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/* 536 */     TsapiTrace.traceEntry("selectRouteWithNetworkRedirection[String routeSelected, UserToUserInfo userInfo]", this);
/* 537 */     String[] routes = new String[1];
/* 538 */     routes[0] = routeSelected;
/*     */     try
/*     */     {
/* 541 */       LucentRouteSelect lrs = createLucentRouteSelect(null, null, false, null, null, null, userInfo, (short) 1);
/* 542 */       this.privData = lrs.makeTsapiPrivate();
/* 543 */       this.tsRouteSession.tsSelectRoute(routes, this.privData);
/*     */     }
/*     */     finally
/*     */     {
/* 547 */       this.privData = null;
/*     */     }
/* 549 */     TsapiTrace.traceExit("selectRouteWithNetworkRedirection[String routeSelected, UserToUserInfo userInfo]", this);
/*     */   }
/*     */ 
/*     */   public short getCSTAErrorCode()
/*     */   {
/* 556 */     TsapiTrace.traceEntry("getCSTAErrorCode[]", this);
/* 557 */     short code = this.tsRouteSession.getCSTAErrorCode();
/* 558 */     TsapiTrace.traceExit("getCSTAErrorCode[]", this);
/* 559 */     return code;
/*     */   }
/*     */ 
/*     */   public final void setPrivateData(Object data)
/*     */   {
/* 566 */     TsapiTrace.traceEntry("setPrivateData[Object data]", this);
/*     */     try
/*     */     {
/* 569 */       this.privData = TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data);
/*     */     }
/*     */     catch (ClassCastException e)
/*     */     {
/* 573 */       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
/*     */     }
/*     */ 
/* 576 */     TsapiTrace.traceExit("setPrivateData[Object data]", this);
/*     */   }
/*     */ 
/*     */   public final Object getPrivateData()
/*     */   {
/* 581 */     TsapiTrace.traceEntry("getPrivateData[]", this);
/* 582 */     TsapiTrace.traceExit("getPrivateData[]", this);
/* 583 */     return null;
/*     */   }
/*     */ 
/*     */   public final Object sendPrivateData(Object data)
/*     */   {
/* 588 */     TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
/* 589 */     TsapiTrace.traceExit("sendPrivateData[Object data]", this);
/* 590 */     return null;
/*     */   }
/*     */ 
/*     */   public final int getRouteRegisterID()
/*     */   {
/* 595 */     TsapiTrace.traceEntry("getRouteRegisterID[]", this);
/*     */     try
/*     */     {
/* 598 */       int id = this.tsRouteSession.getRtRegID();
/* 599 */       TsapiTrace.traceExit("getRouteRegisterID[]", this);
/* 600 */       return id;
/*     */     }
/*     */     finally
/*     */     {
/* 604 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final int getRouteCrossRefID()
/*     */   {
/* 610 */     TsapiTrace.traceEntry("getRouteCrossRefID[]", this);
/*     */     try
/*     */     {
/* 613 */       int xref = this.tsRouteSession.getRtXrefID();
/* 614 */       TsapiTrace.traceExit("getRouteCrossRefID[]", this);
/* 615 */       return xref;
/*     */     }
/*     */     finally
/*     */     {
/* 619 */       this.privData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final int hashCode()
/*     */   {
/* 625 */     return this.tsRouteSession.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 631 */     if (obj instanceof TsapiRouteSession)
/*     */     {
/* 633 */       return this.tsRouteSession.equals(((TsapiRouteSession)obj).tsRouteSession);
/*     */     }
/*     */ 
/* 637 */     return false;
/*     */   }
/*     */ 
/*     */   TsapiRouteSession(TSRouteSession _tsRouteSession)
/*     */   {
/* 644 */     this.tsRouteSession = _tsRouteSession;
/* 645 */     TsapiTrace.traceConstruction(this, TsapiRouteSession.class);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 651 */     super.finalize();
/* 652 */     TsapiTrace.traceDestruction(this, TsapiRouteSession.class);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiRouteSession
 * JD-Core Version:    0.5.4
 */