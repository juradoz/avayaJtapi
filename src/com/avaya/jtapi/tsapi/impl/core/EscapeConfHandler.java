/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEscapeSvcConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ 
/*      */ class EscapeConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSProviderImpl prov;
/*      */   ConfHandler extraHandler;
/*      */   protected Object privateData;
/*      */ 
/*      */   EscapeConfHandler(TSProviderImpl _prov, ConfHandler _extraHandler)
/*      */   {
/* 3627 */     this.prov = _prov;
/* 3628 */     this.extraHandler = _extraHandler;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event) {
/* 3632 */     Object privData = null;
/*      */     try {
/* 3634 */       if ((event == null) || (!(event.getEvent() instanceof CSTAEscapeSvcConfEvent)))
/*      */       {
/* 3636 */         return;
/*      */       }
/*      */ 
/* 3639 */       privData = event.getPrivData();
/* 3640 */       this.prov.replyPriv = privData;
/* 3641 */       this.privateData = privData;
/*      */     } finally {
/* 3643 */       if (this.extraHandler != null)
/* 3644 */         this.extraHandler.handleConf(event);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object getPrivateData()
/*      */   {
/* 3651 */     return this.privateData;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.EscapeConfHandler
 * JD-Core Version:    0.5.4
 */