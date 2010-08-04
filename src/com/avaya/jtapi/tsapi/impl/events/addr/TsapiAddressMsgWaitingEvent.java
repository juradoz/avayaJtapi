/*    */ package com.avaya.jtapi.tsapi.impl.events.addr;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.ITsapiAddressMsgWaitingEvent;
/*    */ import javax.telephony.Address;
/*    */ 
/*    */ public class TsapiAddressMsgWaitingEvent extends TsapiCallCtlAddressEvent
/*    */   implements ITsapiAddressMsgWaitingEvent
/*    */ {
/* 32 */   int mwBits = 0;
/*    */ 
/*    */   public final boolean getMessageWaitingState()
/*    */   {
/* 18 */     return this.mwBits != 0;
/*    */   }
/*    */ 
/*    */   public final int getMessageWaitingBits()
/*    */   {
/* 23 */     return this.mwBits;
/*    */   }
/*    */ 
/*    */   public final int getID()
/*    */   {
/* 29 */     return 202;
/*    */   }
/*    */ 
/*    */   public TsapiAddressMsgWaitingEvent(Address _device, int _mwBits, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 39 */     super(_device, _cause, _metaCode, _privateData);
/* 40 */     this.mwBits = _mwBits;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddressMsgWaitingEvent
 * JD-Core Version:    0.5.4
 */