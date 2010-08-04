/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ConnectionID;
/*     */ import com.avaya.jtapi.tsapi.LookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
/*     */ import com.avaya.jtapi.tsapi.NetworkProgressInfo;
/*     */ import com.avaya.jtapi.tsapi.OriginalCallInfo;
/*     */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*     */ import com.avaya.jtapi.tsapi.TsapiPrivate;
/*     */ import com.avaya.jtapi.tsapi.TsapiTrunk;
/*     */ import com.avaya.jtapi.tsapi.UserEnteredCode;
/*     */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTACallOriginatorInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentChargeAdvice;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentConferencedEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentConnectionClearedEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentDeliveredEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentDeviceHistoryEntry;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentEstablishedEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentLookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentNetworkProgressInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentOriginalCallInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentTransferredEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentUserEnteredCode;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV5ConferencedEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV5DeliveredEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV5EstablishedEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV5TransferredEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.LucentACDAddressImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.LucentACDManagerAddressImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.LucentCallImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.LucentTerminalImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.LucentV7CallImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.TsapiAddress;
/*     */ import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
/*     */ import com.avaya.jtapi.tsapi.impl.TsapiTrunkImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.beans.LucentChargeAdviceEventImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.beans.V7DeviceHistoryEntryImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.call.PrivateDataParams;
/*     */ 
/*     */ public final class TsapiPromoter
/*     */ {
/*     */   static V7DeviceHistoryEntry[] promoteDeviceHistory(LucentDeviceHistoryEntry[] array)
/*     */   {
/*  56 */     if ((array == null) || (array.length == 0))
/*     */     {
/*  58 */       return null;
/*     */     }
/*  60 */     V7DeviceHistoryEntry[] local = new V7DeviceHistoryEntry[array.length];
/*     */ 
/*  62 */     for (int i = 0; i < array.length; ++i)
/*     */     {
/*  64 */       CSTAConnectionID cid = array[i].getOldConnectionID();
/*  65 */       local[i] = new V7DeviceHistoryEntryImpl(array[i].getOldDeviceID(), array[i].getEventCause(), new ConnectionID(cid.getCallID(), cid.getDeviceID(), (short)cid.getDevIDType()));
/*     */     }
/*     */ 
/*  72 */     return local;
/*     */   }
/*     */ 
/*     */   static TSDevice promoteExtendedDeviceIDToTSDevice(TSProviderImpl provider, CSTAExtendedDeviceID id)
/*     */   {
/*  81 */     if (id == null)
/*     */     {
/*  83 */       return null;
/*     */     }
/*     */ 
/*  87 */     return provider.createDevice(id);
/*     */   }
/*     */ 
/*     */   static TsapiAddress promoteDeviceIDToAddress(TSProviderImpl provider, CSTAExtendedDeviceID deviceID)
/*     */   {
/*  93 */     TSDevice device = provider.createDevice(deviceID);
/*  94 */     return (TsapiAddress)TsapiCreateObject.getTsapiObject(device, true);
/*     */   }
/*     */ 
/*     */   static TsapiTrunkImpl promoteTrunk(TSProviderImpl provider, String trunkGroup, String trunkMember, int type)
/*     */   {
/* 100 */     String trkName = TsapiTrunk.makeTrunkName(trunkGroup, trunkMember);
/* 101 */     if (trkName != null)
/*     */     {
/* 103 */       TSTrunk tsTrunk = provider.createTrunk(trkName, type);
/* 104 */       if (tsTrunk != null)
/*     */       {
/* 107 */         tsTrunk.setGroupName(trunkGroup);
/* 108 */         tsTrunk.setMemberName(trunkMember);
/*     */ 
/* 110 */         return (TsapiTrunkImpl)TsapiCreateObject.getTsapiObject(tsTrunk, false);
/*     */       }
/*     */     }
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   static TsapiTrunkImpl promoteTrunk(TSProviderImpl provider, String trunkGroup, String trunkMember)
/*     */   {
/* 119 */     return promoteTrunk(provider, trunkGroup, trunkMember, 1);
/*     */   }
/*     */ 
/*     */   static LucentTerminalImpl promoteTerminal(TSProviderImpl provider, String terminalID) {
/* 123 */     if (terminalID == null)
/* 124 */       return null;
/*     */     try
/*     */     {
/* 127 */       return new LucentTerminalImpl(provider, terminalID);
/*     */     } catch (TsapiInvalidArgumentException e) {
/*     */     }
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */   static LucentACDAddressImpl promoteACDAddress(TSProviderImpl provider, String split)
/*     */   {
/* 136 */     if (split == null)
/* 137 */       return null;
/*     */     try
/*     */     {
/* 140 */       return new LucentACDAddressImpl(provider, split);
/*     */     } catch (TsapiInvalidArgumentException e) {
/*     */     }
/* 143 */     return null;
/*     */   }
/*     */ 
/*     */   static LucentCallImpl promoteConnection(TSProviderImpl provider, CSTAConnectionID connectionID)
/*     */   {
/* 150 */     if (connectionID == null) {
/* 151 */       return null;
/*     */     }
/* 153 */     return new LucentCallImpl(provider, connectionID);
/*     */   }
/*     */ 
/*     */   static OriginalCallInfo promoteOriginalCallInfo(TSProviderImpl provider, LucentOriginalCallInfo oci_csta)
/*     */   {
/* 171 */     return JtapiOriginalCallInfoFactory.createOriginalCallInfo(provider, oci_csta);
/*     */   }
/*     */ 
/*     */   static UserToUserInfo promoteUserToUserInfo(LucentUserToUserInfo csta_obj)
/*     */   {
/* 179 */     return JtapiUserToUserInfoFactory.createUserToUserInfo(csta_obj);
/*     */   }
/*     */ 
/*     */   public static LucentUserToUserInfo demoteUserToUserInfo(UserToUserInfo uui)
/*     */   {
/* 188 */     return JtapiUserToUserInfoFactory.createUserToUserInfo(uui);
/*     */   }
/*     */ 
/*     */   static LookaheadInfo promoteLookaheadInfo(LucentLookaheadInfo csta_obj)
/*     */   {
/* 197 */     return JtapiLookaheadInfoFactory.createLookaheadInfo(csta_obj);
/*     */   }
/*     */ 
/*     */   static UserEnteredCode promoteUserEnteredCode(TSProviderImpl provider, LucentUserEnteredCode csta_obj)
/*     */   {
/* 207 */     if (csta_obj != null)
/*     */     {
/* 209 */       UserEnteredCode uec = new UserEnteredCode();
/*     */ 
/* 211 */       uec.setData(csta_obj.getDigits());
/* 212 */       uec.setIndicator(csta_obj.getIndicator());
/* 213 */       uec.setType(csta_obj.getType());
/*     */       try
/*     */       {
/* 216 */         if (csta_obj.getCollectVDN_asn() != null)
/*     */         {
/* 218 */           uec.setCollectVDN(new LucentACDManagerAddressImpl(provider, csta_obj.getCollectVDN_asn()));
/*     */         }
/*     */       }
/*     */       catch (TsapiInvalidArgumentException e) {
/*     */       }
/* 223 */       return uec;
/*     */     }
/* 225 */     return null;
/*     */   }
/*     */ 
/*     */   public static Object promotePrivateEvent(TSProviderImpl provider, Object csta_obj)
/*     */   {
/* 241 */     if (csta_obj instanceof LucentChargeAdvice)
/*     */     {
/* 243 */       return promoteChargeAdvice(provider, (LucentChargeAdvice)csta_obj);
/*     */     }
/* 245 */     if (csta_obj instanceof LucentNetworkProgressInfo)
/*     */     {
/* 247 */       return promoteNetworkProgressInfo(provider, (LucentNetworkProgressInfo)csta_obj);
/*     */     }
/* 249 */     if (csta_obj instanceof CSTAPrivate)
/*     */     {
/* 251 */       return promoteTsapiPrivate((CSTAPrivate)csta_obj);
/*     */     }
/* 253 */     if (csta_obj instanceof LucentConferencedEvent)
/*     */     {
/* 255 */       return promoteLucentConferencedEvent(provider, (LucentConferencedEvent)csta_obj);
/*     */     }
/*     */ 
/* 258 */     if (csta_obj instanceof LucentConnectionClearedEvent)
/*     */     {
/* 260 */       return promoteLucentConnectionClearedEvent((LucentConnectionClearedEvent)csta_obj);
/*     */     }
/*     */ 
/* 263 */     if (csta_obj instanceof LucentDeliveredEvent)
/*     */     {
/* 265 */       return promoteLucentDeliveredEvent(provider, (LucentDeliveredEvent)csta_obj);
/*     */     }
/*     */ 
/* 268 */     if (csta_obj instanceof LucentEstablishedEvent)
/*     */     {
/* 270 */       return promoteLucentEstablishedEvent(provider, (LucentEstablishedEvent)csta_obj);
/*     */     }
/*     */ 
/* 273 */     if (csta_obj instanceof LucentTransferredEvent)
/*     */     {
/* 275 */       return promoteLucentTransferredEvent(provider, (LucentTransferredEvent)csta_obj);
/*     */     }
/*     */ 
/* 281 */     return csta_obj;
/*     */   }
/*     */ 
/*     */   private static PrivateDataParams promoteLucentConferencedEvent(TSProviderImpl provider, LucentConferencedEvent csta_obj)
/*     */   {
/* 289 */     PrivateDataParams params = new PrivateDataParams();
/* 290 */     params.setDistributingDevice(promoteDeviceIDToAddress(provider, csta_obj.getDistributingDevice_asn()));
/*     */ 
/* 292 */     params.setOriginalCallInfo(promoteOriginalCallInfo(provider, csta_obj.getOriginalCallInfo()));
/*     */ 
/* 295 */     if (csta_obj instanceof LucentV5ConferencedEvent) {
/* 296 */       params.setUcid(((LucentV5ConferencedEvent)csta_obj).getUcid());
/*     */     }
/*     */ 
/* 299 */     return params;
/*     */   }
/*     */ 
/*     */   private static PrivateDataParams promoteLucentConnectionClearedEvent(LucentConnectionClearedEvent csta_obj)
/*     */   {
/* 305 */     PrivateDataParams params = new PrivateDataParams();
/* 306 */     params.setUserToUserInfo(promoteUserToUserInfo(csta_obj.getUserInfo()));
/* 307 */     return params;
/*     */   }
/*     */ 
/*     */   private static PrivateDataParams promoteLucentDeliveredEvent(TSProviderImpl provider, LucentDeliveredEvent csta_obj)
/*     */   {
/* 314 */     PrivateDataParams params = new PrivateDataParams();
/*     */ 
/* 316 */     params.setDistributingDevice(promoteDeviceIDToAddress(provider, csta_obj.getDistributingDevice_asn()));
/*     */ 
/* 318 */     params.setLookaheadInfo(promoteLookaheadInfo(csta_obj.getLookaheadInfo()));
/* 319 */     params.setOriginalCallInfo(promoteOriginalCallInfo(provider, csta_obj.getOriginalCallInfo()));
/*     */ 
/* 321 */     params.setReason(csta_obj.getReason());
/* 322 */     params.setSplit(promoteACDAddress(provider, csta_obj.getSplit_asn()));
/* 323 */     params.setTrunk(promoteTrunk(provider, csta_obj.getTrunkGroup(), csta_obj.getTrunkMember()));
/* 324 */     params.setUserEnteredCode(promoteUserEnteredCode(provider, csta_obj.getUserEnteredCode()));
/* 325 */     params.setUserToUserInfo(promoteUserToUserInfo(csta_obj.getUserInfo()));
/*     */ 
/* 327 */     if (csta_obj instanceof LucentV5DeliveredEvent) {
/* 328 */       LucentV5DeliveredEvent v5Obj = (LucentV5DeliveredEvent)csta_obj;
/*     */ 
/* 331 */       params.setUcid(v5Obj.getUcid());
/* 332 */       params.setFlexibleBilling(v5Obj.isFlexibleBilling());
/*     */ 
/* 334 */       CSTACallOriginatorInfo info = v5Obj.getCallOriginatorInfo();
/* 335 */       if (info != null) {
/* 336 */         params.setHasCallOriginatorType(true);
/* 337 */         params.setCallOriginatorType(v5Obj.getCallOriginatorInfo().getCallOriginatorType());
/*     */       }
/*     */     }
/* 340 */     return params;
/*     */   }
/*     */ 
/*     */   private static PrivateDataParams promoteLucentEstablishedEvent(TSProviderImpl provider, LucentEstablishedEvent csta_obj)
/*     */   {
/* 347 */     PrivateDataParams params = new PrivateDataParams();
/* 348 */     params.setDistributingDevice(promoteDeviceIDToAddress(provider, csta_obj.getDistributingDevice_asn()));
/*     */ 
/* 350 */     params.setLookaheadInfo(promoteLookaheadInfo(csta_obj.getLookaheadInfo()));
/* 351 */     params.setOriginalCallInfo(promoteOriginalCallInfo(provider, csta_obj.getOriginalCallInfo()));
/*     */ 
/* 353 */     params.setReason(csta_obj.getReason());
/* 354 */     params.setSplit(promoteACDAddress(provider, csta_obj.getSplit_asn()));
/* 355 */     params.setTrunk(promoteTrunk(provider, csta_obj.getTrunkGroup(), csta_obj.getTrunkMember()));
/* 356 */     params.setUserEnteredCode(promoteUserEnteredCode(provider, csta_obj.getUserEnteredCode()));
/* 357 */     params.setUserToUserInfo(promoteUserToUserInfo(csta_obj.getUserInfo()));
/*     */ 
/* 359 */     if (csta_obj instanceof LucentV5EstablishedEvent) {
/* 360 */       LucentV5EstablishedEvent v5Obj = (LucentV5EstablishedEvent)csta_obj;
/*     */ 
/* 363 */       params.setUcid(v5Obj.getUcid());
/* 364 */       params.setFlexibleBilling(v5Obj.isFlexibleBilling());
/*     */ 
/* 366 */       CSTACallOriginatorInfo info = v5Obj.getCallOriginatorInfo();
/* 367 */       if (info != null) {
/* 368 */         params.setHasCallOriginatorType(true);
/* 369 */         params.setCallOriginatorType(v5Obj.getCallOriginatorInfo().getCallOriginatorType());
/*     */       }
/*     */     }
/* 372 */     return params;
/*     */   }
/*     */ 
/*     */   private static PrivateDataParams promoteLucentTransferredEvent(TSProviderImpl provider, LucentTransferredEvent csta_obj)
/*     */   {
/* 379 */     PrivateDataParams params = new PrivateDataParams();
/* 380 */     params.setDistributingDevice(promoteDeviceIDToAddress(provider, csta_obj.getDistributingDevice_asn()));
/*     */ 
/* 382 */     params.setOriginalCallInfo(promoteOriginalCallInfo(provider, csta_obj.getOriginalCallInfo()));
/*     */ 
/* 385 */     if (csta_obj instanceof LucentV5TransferredEvent) {
/* 386 */       params.setUcid(((LucentV5TransferredEvent)csta_obj).getUcid());
/*     */     }
/*     */ 
/* 389 */     return params;
/*     */   }
/*     */ 
/*     */   public static TsapiPrivate promoteTsapiPrivate(CSTAPrivate csta_obj)
/*     */   {
/* 398 */     return new TsapiPrivate(csta_obj.vendor, csta_obj.data, csta_obj.tsType);
/*     */   }
/*     */ 
/*     */   public static CSTAPrivate demoteTsapiPrivate(TsapiPrivate jtapi_obj)
/*     */   {
/* 407 */     return new CSTAPrivate(jtapi_obj.vendor, jtapi_obj.data, jtapi_obj.tsType);
/*     */   }
/*     */ 
/*     */   static LucentChargeAdviceEvent promoteChargeAdvice(TSProviderImpl provider, LucentChargeAdvice csta_obj)
/*     */   {
/* 416 */     CSTAConnectionID connection_asn = csta_obj.getConnection_asn();
/* 417 */     LucentV7CallImpl call = null;
/* 418 */     TsapiAddress calledDevice = null;
/* 419 */     String calledDevice_asn = csta_obj.getCalledDevice_asn();
/* 420 */     String chargingDevice_asn = csta_obj.getChargingDevice_asn();
/* 421 */     TsapiAddress chargingDevice = null;
/* 422 */     TsapiTrunk trunk = null;
/*     */ 
/* 424 */     if (connection_asn != null)
/*     */     {
/* 426 */       call = new LucentV7CallImpl(provider, connection_asn);
/*     */     }
/*     */ 
/* 429 */     if (calledDevice_asn != null)
/*     */     {
/* 431 */       TSDevice device = provider.createDevice(calledDevice_asn);
/* 432 */       calledDevice = (TsapiAddress)TsapiCreateObject.getTsapiObject(device, true);
/*     */     }
/* 434 */     if (chargingDevice_asn != null)
/*     */     {
/* 436 */       TSDevice device = provider.createDevice(chargingDevice_asn);
/* 437 */       chargingDevice = (TsapiAddress)TsapiCreateObject.getTsapiObject(device, true);
/*     */     }
/*     */ 
/* 440 */     trunk = promoteTrunk(provider, csta_obj.getTrunkGroup(), csta_obj.getTrunkMember(), 2);
/*     */ 
/* 445 */     return new LucentChargeAdviceEventImpl(csta_obj.getChargeType(), csta_obj.getCharge(), call, calledDevice, chargingDevice, csta_obj.getChargeError(), trunk);
/*     */   }
/*     */ 
/*     */   static NetworkProgressInfo promoteNetworkProgressInfo(TSProviderImpl provider, LucentNetworkProgressInfo csta_obj)
/*     */   {
/* 460 */     return JtapiNetworkProgressInfoFactory.createNetworkProgressInfo(provider, csta_obj);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TsapiPromoter
 * JD-Core Version:    0.5.4
 */