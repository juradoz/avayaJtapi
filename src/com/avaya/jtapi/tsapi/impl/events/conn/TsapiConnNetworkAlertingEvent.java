/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.ITsapiCallInfo;
/*    */ import javax.telephony.callcontrol.events.CallCtlConnNetworkAlertingEv;
/*    */ 
/*    */ public class TsapiConnNetworkAlertingEvent extends TsapiCallCtlConnEvent
/*    */   implements CallCtlConnNetworkAlertingEv, ITsapiCallInfo
/*    */ {
/*    */   public final int getID()
/*    */   {
/* 18 */     return 209;
/*    */   }
/*    */ 
/*    */   public TsapiConnNetworkAlertingEvent(ConnEventParams params)
/*    */   {
/* 26 */     super(params);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnNetworkAlertingEvent
 * JD-Core Version:    0.5.4
 */