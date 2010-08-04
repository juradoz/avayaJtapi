/*    */ package com.avaya.jtapi.tsapi.impl.events.call;
/*    */ 
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.privatedata.PrivateDataEvent;
/*    */ 
/*    */ public class PrivateDataCallEventImpl
/*    */   implements PrivateDataEvent
/*    */ {
/*    */   private MetaEvent metaEvent;
/*    */   private Object privateData;
/*    */   private Object source;
/*    */   private int cause;
/*    */ 
/*    */   public PrivateDataCallEventImpl(Object privateData, Object source, int cause, MetaEvent metaEvent)
/*    */   {
/* 21 */     this.privateData = privateData;
/* 22 */     this.source = source;
/* 23 */     this.cause = cause;
/* 24 */     this.metaEvent = metaEvent;
/*    */   }
/*    */ 
/*    */   public Object getPrivateData()
/*    */   {
/* 31 */     return this.privateData;
/*    */   }
/*    */ 
/*    */   public int getCause()
/*    */   {
/* 38 */     return this.cause;
/*    */   }
/*    */ 
/*    */   public int getID()
/*    */   {
/* 45 */     return 601;
/*    */   }
/*    */ 
/*    */   public MetaEvent getMetaEvent()
/*    */   {
/* 52 */     return this.metaEvent;
/*    */   }
/*    */ 
/*    */   public Object getSource()
/*    */   {
/* 59 */     return this.source;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.PrivateDataCallEventImpl
 * JD-Core Version:    0.5.4
 */