/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.ITsapiCallInfo;
/*    */ import javax.telephony.callcontrol.events.CallCtlConnDisconnectedEv;
/*    */ 
/*    */ public class TsapiConnDisconnectedEventCC extends TsapiCallCtlConnEvent
/*    */   implements CallCtlConnDisconnectedEv, ITsapiCallInfo
/*    */ {
/*    */   public final int getID()
/*    */   {
/* 18 */     return 205;
/*    */   }
/*    */ 
/*    */   public TsapiConnDisconnectedEventCC(ConnEventParams params)
/*    */   {
/* 26 */     super(params);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnDisconnectedEventCC
 * JD-Core Version:    0.5.4
 */