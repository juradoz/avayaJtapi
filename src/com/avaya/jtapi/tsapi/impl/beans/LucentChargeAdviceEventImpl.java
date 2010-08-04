/*    */ package com.avaya.jtapi.tsapi.impl.beans;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.ITsapiAddress;
/*    */ import com.avaya.jtapi.tsapi.LucentAddress;
/*    */ import com.avaya.jtapi.tsapi.LucentCall;
/*    */ import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
/*    */ import com.avaya.jtapi.tsapi.TsapiTrunk;
/*    */ 
/*    */ public class LucentChargeAdviceEventImpl
/*    */   implements LucentChargeAdviceEvent
/*    */ {
/*    */   private short chargeType;
/*    */   private int charge;
/*    */   private short error;
/*    */   private LucentCall call;
/*    */   private ITsapiAddress calledDevice;
/*    */   private ITsapiAddress chargingDevice;
/*    */   private TsapiTrunk trunk;
/*    */ 
/*    */   public LucentChargeAdviceEventImpl(short _chargeType, int _charge, LucentCall _call, ITsapiAddress _calledDevice, ITsapiAddress _chargingDevice, short _error, TsapiTrunk _trunk)
/*    */   {
/* 34 */     this.chargeType = _chargeType;
/* 35 */     this.charge = _charge;
/* 36 */     this.call = _call;
/* 37 */     this.calledDevice = _calledDevice;
/* 38 */     this.chargingDevice = _chargingDevice;
/* 39 */     this.error = _error;
/* 40 */     this.trunk = _trunk;
/*    */   }
/*    */ 
/*    */   public final LucentCall getCall()
/*    */   {
/* 47 */     return this.call;
/*    */   }
/*    */ 
/*    */   public final LucentAddress getCalledAddress()
/*    */   {
/* 55 */     return (LucentAddress)this.calledDevice;
/*    */   }
/*    */ 
/*    */   public final LucentAddress getChargingAddress()
/*    */   {
/* 63 */     return (LucentAddress)this.chargingDevice;
/*    */   }
/*    */ 
/*    */   public final TsapiTrunk getTrunk()
/*    */   {
/* 71 */     return this.trunk;
/*    */   }
/*    */ 
/*    */   public final int getCharge()
/*    */   {
/* 79 */     return this.charge;
/*    */   }
/*    */ 
/*    */   public final short getChargeType()
/*    */   {
/* 88 */     return this.chargeType;
/*    */   }
/*    */ 
/*    */   public final short getChargeError()
/*    */   {
/* 97 */     return this.error;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.beans.LucentChargeAdviceEventImpl
 * JD-Core Version:    0.5.4
 */