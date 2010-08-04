/*    */ package com.avaya.jtapi.tsapi.impl.events.termConn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.ITsapiCallInfo;
/*    */ import javax.telephony.callcontrol.events.CallCtlTermConnRingingEv;
/*    */ 
/*    */ public class TsapiTermConnRingingEventCC extends TsapiCallCtlTermConnEvent
/*    */   implements CallCtlTermConnRingingEv, ITsapiCallInfo
/*    */ {
/*    */   public final int getID()
/*    */   {
/* 18 */     return 218;
/*    */   }
/*    */ 
/*    */   public TsapiTermConnRingingEventCC(TermConnEventParams params)
/*    */   {
/* 26 */     super(params);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnRingingEventCC
 * JD-Core Version:    0.5.4
 */