/*    */ package com.avaya.jtapi.tsapi.impl.events.provider;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.TsapiListenerEvent;
/*    */ import javax.telephony.Provider;
/*    */ import javax.telephony.ProviderEvent;
/*    */ 
/*    */ public class ProviderEventImpl extends TsapiListenerEvent
/*    */   implements ProviderEvent
/*    */ {
/*    */   private final Provider provider;
/*    */ 
/*    */   public ProviderEventImpl(ProviderEventParams params)
/*    */   {
/* 13 */     super(params.getId(), params.getCause(), params.getMetaEvent(), params.getSource(), params.getPrivateData());
/*    */ 
/* 15 */     this.provider = params.getProvider();
/*    */   }
/*    */ 
/*    */   public Provider getProvider() {
/* 19 */     return this.provider;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 24 */     return super.toString() + ";provider=" + this.provider;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.provider.ProviderEventImpl
 * JD-Core Version:    0.5.4
 */