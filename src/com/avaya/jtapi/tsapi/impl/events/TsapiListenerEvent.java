/*    */ package com.avaya.jtapi.tsapi.impl.events;
/*    */ 
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.privatedata.PrivateDataEvent;
/*    */ 
/*    */ public abstract class TsapiListenerEvent
/*    */   implements PrivateDataEvent
/*    */ {
/*    */   protected final int cause;
/*    */   private final int eventId;
/*    */   private final Object source;
/*    */   private final MetaEvent metaEvent;
/*    */   private final Object privateData;
/*    */ 
/*    */   public final int getCause()
/*    */   {
/* 16 */     if ((this.cause == 101) || (this.cause == 102) || (this.cause == 103) || (this.cause == 104) || (this.cause == 105) || (this.cause == 106) || (this.cause == 107) || (this.cause == 108) || (this.cause == 109) || (this.cause == 110))
/*    */     {
/* 26 */       return this.cause;
/*    */     }
/* 28 */     return 100;
/*    */   }
/*    */ 
/*    */   public TsapiListenerEvent(int eventId, int _cause, MetaEvent metaEvent, Object source, Object privateData)
/*    */   {
/* 40 */     this.cause = _cause;
/* 41 */     this.metaEvent = metaEvent;
/* 42 */     this.eventId = eventId;
/* 43 */     this.source = source;
/* 44 */     this.privateData = privateData;
/*    */   }
/*    */ 
/*    */   public MetaEvent getMetaEvent()
/*    */   {
/* 49 */     return this.metaEvent;
/*    */   }
/*    */ 
/*    */   public Object getSource()
/*    */   {
/* 54 */     return this.source;
/*    */   }
/*    */ 
/*    */   public int getID()
/*    */   {
/* 59 */     return this.eventId;
/*    */   }
/*    */ 
/*    */   public Object getPrivateData() {
/* 63 */     return this.privateData;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 68 */     return "cause=" + this.cause + ";eventId=" + this.eventId + ";source=" + this.source + ";metaEvent=" + this.metaEvent;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.TsapiListenerEvent
 * JD-Core Version:    0.5.4
 */