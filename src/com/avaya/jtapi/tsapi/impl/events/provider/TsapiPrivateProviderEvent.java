/*    */ package com.avaya.jtapi.tsapi.impl.events.provider;
/*    */ 
/*    */ import javax.telephony.Provider;
/*    */ import javax.telephony.privatedata.events.PrivateProvEv;
/*    */ 
/*    */ public final class TsapiPrivateProviderEvent extends TsapiProvEvent
/*    */   implements PrivateProvEv
/*    */ {
/*    */   public int getID()
/*    */   {
/* 18 */     return 602;
/*    */   }
/*    */ 
/*    */   public TsapiPrivateProviderEvent(Provider _provider, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 27 */     super(_provider, _cause, _metaCode, _privateData, 5);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.provider.TsapiPrivateProviderEvent
 * JD-Core Version:    0.5.4
 */