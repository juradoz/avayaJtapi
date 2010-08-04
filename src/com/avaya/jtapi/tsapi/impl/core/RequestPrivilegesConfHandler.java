/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.acs.ACSRequestPrivilegesConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ 
/*      */ final class RequestPrivilegesConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSProviderImpl provider;
/*      */   String nonce;
/*      */ 
/*      */   RequestPrivilegesConfHandler(TSProviderImpl provider)
/*      */   {
/* 3768 */     this.provider = provider;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 3778 */     if ((event == null) || (!(event.getEvent() instanceof ACSRequestPrivilegesConfEvent)))
/*      */     {
/* 3780 */       return;
/*      */     }
/* 3782 */     this.nonce = ((ACSRequestPrivilegesConfEvent)event.getEvent()).get_nonce();
/*      */   }
/*      */ 
/*      */   public String get_nonce() {
/* 3786 */     return this.nonce;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.RequestPrivilegesConfHandler
 * JD-Core Version:    0.5.4
 */