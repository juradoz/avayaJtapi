/*    */ package com.avaya.jtapi.tsapi.impl.events.addr;
/*    */ 
/*    */ import javax.telephony.Address;
/*    */ 
/*    */ public abstract class TsapiCallCtlAddressEvent extends TsapiAddressEvent
/*    */ {
/*    */   public TsapiCallCtlAddressEvent(Address _device, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 22 */     super(_device, _cause, _metaCode, _privateData, 1);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.TsapiCallCtlAddressEvent
 * JD-Core Version:    0.5.4
 */