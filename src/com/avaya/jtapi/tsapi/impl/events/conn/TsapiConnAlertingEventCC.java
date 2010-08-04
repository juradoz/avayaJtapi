/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.ITsapiCallInfo;
/*    */ import javax.telephony.callcontrol.events.CallCtlConnAlertingEv;
/*    */ 
/*    */ public class TsapiConnAlertingEventCC extends TsapiCallCtlConnEvent
/*    */   implements CallCtlConnAlertingEv, ITsapiCallInfo
/*    */ {
/*    */   public final int getID()
/*    */   {
/* 18 */     return 203;
/*    */   }
/*    */ 
/*    */   public TsapiConnAlertingEventCC(ConnEventParams params)
/*    */   {
/* 25 */     super(params);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnAlertingEventCC
 * JD-Core Version:    0.5.4
 */