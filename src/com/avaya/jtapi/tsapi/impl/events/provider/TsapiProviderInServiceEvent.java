/*    */ package com.avaya.jtapi.tsapi.impl.events.provider;
/*    */ 
/*    */ import javax.telephony.Provider;
/*    */ import javax.telephony.events.ProvInServiceEv;
/*    */ 
/*    */ public final class TsapiProviderInServiceEvent extends TsapiProvEvent
/*    */   implements ProvInServiceEv
/*    */ {
/*    */   public int getID()
/*    */   {
/* 17 */     return 111;
/*    */   }
/*    */ 
/*    */   public TsapiProviderInServiceEvent(Provider _provider, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 26 */     super(_provider, _cause, _metaCode, _privateData);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderInServiceEvent
 * JD-Core Version:    0.5.4
 */