/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.LookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
/*     */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*     */ import com.avaya.jtapi.tsapi.UserEnteredCode;
/*     */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTACallOriginatorInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentLookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentUserEnteredCode;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiRouteMonitor;
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public final class TSRouteSession
/*     */ {
/*  23 */   private static Logger log = Logger.getLogger(TSRouteSession.class);
/*     */ 
/* 414 */   UserToUserInfo uui = null;
/* 415 */   LookaheadInfo lai = null;
/* 416 */   UserEnteredCode uec = null;
/*     */ 
/* 418 */   String ucid = null;
/*     */   CSTACallOriginatorInfo callOriginatorInfo;
/*     */   boolean flexibleBilling;
/* 423 */   V7DeviceHistoryEntry[] deviceHistory = null;
/*     */ 
/* 425 */   TSTrunk trunk = null;
/*     */   TSProviderImpl tsProvider;
/*     */   TSDevice tsDevice;
/*     */   int routingCrossRefID;
/*     */   public TSDevice currentRouteDevice;
/*     */   TSCall call;
/*     */   public TSDevice callingAddress;
/*     */   public TSDevice callingTerminal;
/*     */   public int routeSelectAlgorithm;
/*     */   public String isdnSetupMessage;
/*     */   public TSDevice routeUsedDevice;
/*     */   public boolean outOfDomain;
/*     */   int state;
/*     */   int cause;
/*     */   short csta_error;
/*     */ 
/*     */   void dump(String indent)
/*     */   {
/*  27 */     log.trace(indent + "***** TSRouteSession DUMP *****");
/*  28 */     log.trace(indent + "TSRouteSession: " + this);
/*  29 */     log.trace(indent + "***** TSRouteSession DUMP END *****");
/*     */   }
/*     */ 
/*     */   public TSProviderImpl getTSProviderImpl()
/*     */   {
/*  34 */     return this.tsProvider;
/*     */   }
/*     */ 
/*     */   public TSDevice getTSRouteDevice()
/*     */   {
/*  39 */     return this.tsDevice;
/*     */   }
/*     */ 
/*     */   public synchronized void tsSelectRoute(String[] routeSelected, CSTAPrivate reqPriv)
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/*  45 */     if (this.tsProvider.getCapabilities().getRouteSelect() == 0)
/*     */     {
/*  47 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*     */     }
/*     */ 
/*  54 */     if (this.state == 3)
/*     */     {
/*  56 */       int cstaError = 17;
/*     */ 
/*  58 */       String errorString = "Route already ended.";
/*  59 */       throw new TsapiPlatformException(2, cstaError, errorString);
/*     */     }
/*     */ 
/*  62 */     for (int i = 0; i < routeSelected.length; ++i)
/*     */     {
/*  64 */       this.tsProvider.tsapi.selectRoute(this.tsDevice.getRegisterReqID(), this.routingCrossRefID, routeSelected[i], -2, "", true, reqPriv);
/*     */       try
/*     */       {
/*  76 */         super.wait(TSProviderImpl.TSAPI_RESPONSE_TIME);
/*     */       }
/*     */       catch (InterruptedException e)
/*     */       {
/*  80 */         return;
/*     */       }
/*  82 */       if (this.state != 4)
/*     */       {
/*  85 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void tsEndRoute(int errorValue, CSTAPrivate reqPriv)
/*     */   {
/*     */     int cstaError;
/*  92 */     switch (errorValue)
/*     */     {
/*     */     case 2:
/*  95 */       cstaError = 33;
/*  96 */       break;
/*     */     case 3:
/*  98 */       cstaError = 34;
/*  99 */       break;
/*     */     case 1:
/*     */     default:
/* 102 */       cstaError = 0;
/*     */     }
/*     */ 
/* 105 */     this.tsProvider.tsapi.routeEnd(this.tsDevice.getRegisterReqID(), this.routingCrossRefID, (short)cstaError, reqPriv);
/*     */ 
/* 109 */     TSEvent routeEndEvent = setState(3);
/* 110 */     this.tsDevice.getTSRouteCallback().deliverEvent(routeEndEvent);
/*     */   }
/*     */ 
/*     */   public int tsGetState()
/*     */   {
/* 115 */     return this.state;
/*     */   }
/*     */ 
/*     */   public int tsGetCause()
/*     */   {
/* 120 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public UserToUserInfo getUUI()
/*     */   {
/* 125 */     return this.uui;
/*     */   }
/*     */ 
/*     */   public LookaheadInfo getLAI()
/*     */   {
/* 130 */     return this.lai;
/*     */   }
/*     */ 
/*     */   public UserEnteredCode getUEC()
/*     */   {
/* 135 */     return this.uec;
/*     */   }
/*     */ 
/*     */   public String getUCID()
/*     */   {
/* 140 */     return this.ucid;
/*     */   }
/*     */ 
/*     */   public TSCall getCall()
/*     */   {
/* 145 */     return this.call;
/*     */   }
/*     */ 
/*     */   public int getCallOriginatorType()
/*     */   {
/* 150 */     if (hasCallOriginatorType()) {
/* 151 */       return this.callOriginatorInfo.getCallOriginatorType();
/*     */     }
/* 153 */     return -1;
/*     */   }
/*     */ 
/*     */   public boolean hasCallOriginatorType()
/*     */   {
/* 158 */     return this.callOriginatorInfo != null;
/*     */   }
/*     */ 
/*     */   public boolean canSetBillRate()
/*     */   {
/* 163 */     return this.flexibleBilling;
/*     */   }
/*     */ 
/*     */   public TSTrunk getTrunk()
/*     */   {
/* 168 */     return this.trunk;
/*     */   }
/*     */ 
/*     */   public synchronized TSEvent setState(int _state)
/*     */   {
/* 173 */     this.state = _state;
/* 174 */     switch (_state)
/*     */     {
/*     */     case 1:
/* 177 */       return new TSEvent(62, this);
/*     */     case 4:
/* 179 */       super.notify();
/* 180 */       return new TSEvent(59, this);
/*     */     case 2:
/* 182 */       super.notify();
/* 183 */       return new TSEvent(63, this);
/*     */     case 3:
/* 185 */       super.notify();
/* 186 */       this.tsDevice.deleteSession(this.routingCrossRefID);
/* 187 */       return new TSEvent(61, this);
/*     */     }
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */   public int getRtRegID()
/*     */   {
/* 195 */     return this.tsDevice.getRegisterReqID();
/*     */   }
/*     */ 
/*     */   public int getRtXrefID()
/*     */   {
/* 200 */     return this.routingCrossRefID;
/*     */   }
/*     */ 
/*     */   TSRouteSession(TSProviderImpl _TSProviderImpl, TSDevice _tsDevice, int _routingCrossRefID, TSDevice _currentRouteDevice, TSDevice _callingDevice, TSCall _call, int _routeSelectAlgorithm, String _isdnSetupMessage)
/*     */   {
/* 213 */     this.tsProvider = _TSProviderImpl;
/* 214 */     this.tsDevice = _tsDevice;
/* 215 */     this.routingCrossRefID = _routingCrossRefID;
/* 216 */     this.currentRouteDevice = _currentRouteDevice;
/* 217 */     if (_callingDevice.isTerminal())
/* 218 */       this.callingTerminal = _callingDevice;
/* 219 */     this.callingAddress = _callingDevice;
/* 220 */     this.call = _call;
/* 221 */     switch (_routeSelectAlgorithm)
/*     */     {
/*     */     case 0:
/* 224 */       this.routeSelectAlgorithm = 0;
/* 225 */       break;
/*     */     case 1:
/* 227 */       this.routeSelectAlgorithm = 1;
/* 228 */       break;
/*     */     case 2:
/* 230 */       this.routeSelectAlgorithm = 2;
/* 231 */       break;
/*     */     case 3:
/* 233 */       this.routeSelectAlgorithm = 3;
/* 234 */       break;
/*     */     case 4:
/* 236 */       this.routeSelectAlgorithm = 4;
/* 237 */       break;
/*     */     default:
/* 239 */       this.routeSelectAlgorithm = 0;
/*     */     }
/*     */ 
/* 242 */     this.isdnSetupMessage = _isdnSetupMessage;
/* 243 */     this.state = 1;
/* 244 */     this.cause = 100;
/* 245 */     this.tsDevice.addSession(this.routingCrossRefID, this);
/* 246 */     log.info("Constructing route session " + this + " with xrefID " + this.routingCrossRefID + " for " + this.tsProvider);
/*     */   }
/*     */ 
/*     */   void setCallingDevice(TSDevice _callingDevice)
/*     */   {
/* 251 */     if (_callingDevice.isTerminal())
/* 252 */       this.callingTerminal = _callingDevice;
/* 253 */     this.callingAddress = _callingDevice;
/*     */   }
/*     */ 
/*     */   void setRouteUsedDevice(TSDevice _routeUsedDevice)
/*     */   {
/* 258 */     this.routeUsedDevice = _routeUsedDevice;
/*     */   }
/*     */ 
/*     */   void setDomain(boolean _outOfDomain)
/*     */   {
/* 263 */     this.outOfDomain = _outOfDomain;
/*     */   }
/*     */ 
/*     */   void setCause(int error)
/*     */   {
/* 268 */     this.csta_error = (short)error;
/* 269 */     switch (error)
/*     */     {
/*     */     case 0:
/* 272 */       this.cause = 100;
/* 273 */       break;
/*     */     case 14:
/* 275 */       this.cause = 103;
/* 276 */       break;
/*     */     case 24:
/*     */     case 28:
/* 279 */       this.cause = 104;
/* 280 */       break;
/*     */     case 5:
/*     */     case 8:
/*     */     case 18:
/*     */     case 22:
/*     */     case 33:
/*     */     case 52:
/*     */     default:
/* 288 */       this.cause = 105;
/*     */     }
/*     */   }
/*     */ 
/*     */   void setUUI(LucentUserToUserInfo _uui)
/*     */   {
/* 306 */     if (_uui == null)
/*     */       return;
/* 308 */     this.uui = TsapiPromoter.promoteUserToUserInfo(_uui);
/*     */   }
/*     */ 
/*     */   void setLAI(LucentLookaheadInfo _lai)
/*     */   {
/* 319 */     if (_lai == null)
/*     */       return;
/* 321 */     this.lai = TsapiPromoter.promoteLookaheadInfo(_lai);
/*     */   }
/*     */ 
/*     */   void setUEC(LucentUserEnteredCode _uec)
/*     */   {
/* 332 */     if (_uec == null)
/*     */       return;
/* 334 */     this.uec = TsapiPromoter.promoteUserEnteredCode(this.tsProvider, _uec);
/*     */   }
/*     */ 
/*     */   void setUUI(UserToUserInfo _uui)
/*     */   {
/* 340 */     if (_uui == null)
/*     */       return;
/* 342 */     this.uui = _uui;
/*     */   }
/*     */ 
/*     */   void setLAI(LookaheadInfo _lai)
/*     */   {
/* 348 */     if (_lai == null)
/*     */       return;
/* 350 */     this.lai = _lai;
/*     */   }
/*     */ 
/*     */   void setUEC(UserEnteredCode _uec)
/*     */   {
/* 356 */     if (_uec == null)
/*     */       return;
/* 358 */     this.uec = _uec;
/*     */   }
/*     */ 
/*     */   void setUCID(String _ucid)
/*     */   {
/* 364 */     if (_ucid == null)
/*     */       return;
/* 366 */     this.ucid = _ucid;
/*     */   }
/*     */ 
/*     */   void setCallOriginatorInfo(CSTACallOriginatorInfo _callOriginatorInfo)
/*     */   {
/* 372 */     this.callOriginatorInfo = _callOriginatorInfo;
/*     */   }
/*     */ 
/*     */   void setFlexibleBilling(boolean _flexibleBilling)
/*     */   {
/* 377 */     this.flexibleBilling = _flexibleBilling;
/*     */   }
/*     */ 
/*     */   void setTrunk(TSTrunk _trunk)
/*     */   {
/* 382 */     if (_trunk == null)
/*     */       return;
/* 384 */     this.trunk = _trunk;
/*     */   }
/*     */ 
/*     */   public short getCSTAErrorCode()
/*     */   {
/* 397 */     return this.csta_error;
/*     */   }
/*     */ 
/*     */   public V7DeviceHistoryEntry[] getDeviceHistory()
/*     */   {
/* 404 */     return this.deviceHistory;
/*     */   }
/*     */ 
/*     */   void setDeviceHistory(V7DeviceHistoryEntry[] deviceHistory)
/*     */   {
/* 411 */     this.deviceHistory = deviceHistory;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSRouteSession
 * JD-Core Version:    0.5.4
 */