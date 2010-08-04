/*    */ package com.avaya.jtapi.tsapi.impl.events.provider;
/*    */ 
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.Provider;
/*    */ 
/*    */ public class ProviderEventParams
/*    */ {
/*    */   private final Provider provider;
/*    */   private final int id;
/*    */   private final int cause;
/*    */   private final MetaEvent metaEvent;
/*    */   private final Object source;
/*    */   private final Object privateData;
/*    */ 
/*    */   public ProviderEventParams(Provider provider, int id, int cause, MetaEvent metaEvent, Object source, Object privateData)
/*    */   {
/* 16 */     this.provider = provider;
/* 17 */     this.id = id;
/* 18 */     this.cause = cause;
/* 19 */     this.metaEvent = metaEvent;
/* 20 */     this.source = source;
/* 21 */     this.privateData = privateData;
/*    */   }
/*    */ 
/*    */   public int getCause() {
/* 25 */     return this.cause;
/*    */   }
/*    */ 
/*    */   public MetaEvent getMetaEvent() {
/* 29 */     return this.metaEvent;
/*    */   }
/*    */ 
/*    */   public Provider getProvider() {
/* 33 */     return this.provider;
/*    */   }
/*    */ 
/*    */   public Object getSource() {
/* 37 */     return this.source;
/*    */   }
/*    */ 
/*    */   public int getId() {
/* 41 */     return this.id;
/*    */   }
/*    */ 
/*    */   public Object getPrivateData() {
/* 45 */     return this.privateData;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.provider.ProviderEventParams
 * JD-Core Version:    0.5.4
 */