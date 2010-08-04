/*    */ package com.avaya.jtapi.tsapi.impl.events.provider;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;
/*    */ import javax.telephony.Provider;
/*    */ import javax.telephony.privatedata.events.PrivateProvEv;
/*    */ 
/*    */ public abstract class TsapiProvEvent extends TsapiObserverEvent
/*    */   implements PrivateProvEv
/*    */ {
/* 21 */   Provider jtapi_provider = null;
/*    */ 
/*    */   public final Provider getProvider()
/*    */   {
/* 18 */     return this.jtapi_provider;
/*    */   }
/*    */ 
/*    */   public TsapiProvEvent(Provider _provider, int _cause, int _metaCode, Object _privateData, int _eventPackage)
/*    */   {
/* 27 */     super(_cause, _metaCode, _privateData, _eventPackage);
/* 28 */     this.jtapi_provider = _provider;
/*    */   }
/*    */ 
/*    */   TsapiProvEvent(Provider _provider, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 34 */     this(_provider, _cause, _metaCode, _privateData, 0);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.provider.TsapiProvEvent
 * JD-Core Version:    0.5.4
 */