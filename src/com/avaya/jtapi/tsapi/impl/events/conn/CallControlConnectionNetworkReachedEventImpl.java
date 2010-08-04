/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.ITsapiConnNetworkReachedListenerEvent;
/*    */ import com.avaya.jtapi.tsapi.NetworkProgressInfo;
/*    */ import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
/*    */ import javax.telephony.MetaEvent;
/*    */ 
/*    */ public class CallControlConnectionNetworkReachedEventImpl extends CallControlConnectionEventImpl
/*    */   implements ITsapiConnNetworkReachedListenerEvent
/*    */ {
/*    */   public CallControlConnectionNetworkReachedEventImpl(CallEventParams params, MetaEvent event, int eventId, int numInQueue, String digits)
/*    */   {
/* 30 */     super(params, event, eventId, numInQueue, digits);
/*    */   }
/*    */ 
/*    */   public NetworkProgressInfo getNetworkProgressInfo() {
/* 34 */     Object privateData = this.callEventParams.getPrivateData();
/* 35 */     if (privateData instanceof NetworkProgressInfo)
/* 36 */       return (NetworkProgressInfo)privateData;
/* 37 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.CallControlConnectionNetworkReachedEventImpl
 * JD-Core Version:    0.5.4
 */