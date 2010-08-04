/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.ITsapiCallInfo;
/*    */ import javax.telephony.callcontrol.events.CallCtlConnDialingEv;
/*    */ 
/*    */ public class TsapiConnDialingEvent extends TsapiCallCtlConnEvent
/*    */   implements CallCtlConnDialingEv, ITsapiCallInfo
/*    */ {
/* 27 */   private String dialedDigits = null;
/*    */ 
/*    */   public final int getID()
/*    */   {
/* 18 */     return 204;
/*    */   }
/*    */ 
/*    */   public final String getDigits()
/*    */   {
/* 23 */     return this.dialedDigits;
/*    */   }
/*    */ 
/*    */   public TsapiConnDialingEvent(ConnEventParams params)
/*    */   {
/* 32 */     super(params);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnDialingEvent
 * JD-Core Version:    0.5.4
 */