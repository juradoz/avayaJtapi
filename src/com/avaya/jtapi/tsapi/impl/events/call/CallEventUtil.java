/*    */ package com.avaya.jtapi.tsapi.impl.events.call;
/*    */ 
/*    */ import javax.telephony.MetaEvent;
/*    */ 
/*    */ public class CallEventUtil
/*    */ {
/*    */   public static MetaEvent[] getListenerMetaObject(int cause, CallEventParams callParams, boolean isSnapshot)
/*    */   {
/* 25 */     int metaEventId = 210;
/* 26 */     boolean isMultiCallMeta = false;
/* 27 */     MetaEvent[] metaEventPair = null;
/*    */ 
/* 29 */     if (isSnapshot) {
/* 30 */       metaEventId = 212;
/* 31 */       metaEventPair = new SingleCallMetaEventImpl[2];
/* 32 */       metaEventPair[0] = new SingleCallMetaEventImpl(callParams, metaEventId);
/* 33 */       metaEventPair[1] = new SingleCallMetaEventImpl(callParams, metaEventId + 1);
/*    */     } else {
/* 35 */       switch (cause)
/*    */       {
/*    */       case 207:
/* 37 */         metaEventId = 200;
/* 38 */         isMultiCallMeta = true;
/* 39 */         break;
/*    */       case 212:
/* 41 */         metaEventId = 202;
/* 42 */         isMultiCallMeta = true;
/*    */       }
/*    */ 
/* 46 */       if (isMultiCallMeta) {
/* 47 */         metaEventPair = new MultiCallMetaEventImpl[2];
/* 48 */         metaEventPair[0] = new MultiCallMetaEventImpl(callParams, metaEventId);
/* 49 */         metaEventPair[1] = new MultiCallMetaEventImpl(callParams, metaEventId + 1);
/*    */       } else {
/* 51 */         metaEventPair = new SingleCallMetaEventImpl[2];
/* 52 */         metaEventPair[0] = new SingleCallMetaEventImpl(callParams, metaEventId);
/* 53 */         metaEventPair[1] = new SingleCallMetaEventImpl(callParams, metaEventId + 1);
/*    */       }
/*    */     }
/*    */ 
/* 57 */     return metaEventPair;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.CallEventUtil
 * JD-Core Version:    0.5.4
 */