/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConsultationCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMakeCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMakePredictiveCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentConsultationCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentMakeCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentMakePredictiveCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ 
/*      */ final class MakeCallConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSCall call;
/*      */   TSDevice device;
/*      */   String dialedDigits;
/*      */   CSTAConnectionID newCall;
/*      */   int pdu;
/*      */ 
/*      */   MakeCallConfHandler(TSCall _call, TSDevice _device, String _dialedDigits, int _pdu)
/*      */   {
/* 5591 */     this.call = _call;
/* 5592 */     this.device = _device;
/* 5593 */     this.dialedDigits = _dialedDigits;
/* 5594 */     this.pdu = _pdu;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 5599 */     if ((event == null) || (event.getEventHeader().getEventClass() != 5) || (event.getEventHeader().getEventType() != this.pdu))
/*      */     {
/* 5603 */       return;
/*      */     }
/*      */ 
/* 5606 */     this.call.replyPriv = event.getPrivData();
/*      */ 
/* 5608 */     switch (this.pdu)
/*      */     {
/*      */     case 24:
/* 5611 */       this.newCall = ((CSTAMakeCallConfEvent)event.getEvent()).getNewCall();
/* 5612 */       if (this.call.replyPriv instanceof LucentMakeCallConfEvent)
/*      */       {
/* 5614 */         this.call.setUCID(((LucentMakeCallConfEvent)this.call.replyPriv).getUcid()); } break;
/*      */     case 26:
/* 5619 */       this.newCall = ((CSTAMakePredictiveCallConfEvent)event.getEvent()).getNewCall();
/* 5620 */       if (this.call.replyPriv instanceof LucentMakePredictiveCallConfEvent)
/*      */       {
/* 5622 */         this.call.setUCID(((LucentMakePredictiveCallConfEvent)this.call.replyPriv).getUcid()); } break;
/*      */     case 14:
/* 5627 */       this.newCall = ((CSTAConsultationCallConfEvent)event.getEvent()).getNewCall();
/* 5628 */       if (this.call.replyPriv instanceof LucentConsultationCallConfEvent)
/*      */       {
/* 5630 */         this.call.setUCID(((LucentConsultationCallConfEvent)this.call.replyPriv).getUcid());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 5635 */     this.call.setCallID(this.newCall.getCallID());
/* 5636 */     this.call.setCallingDevices(this.device);
/* 5637 */     TSDevice dialedDevice = this.call.getTSProviderImpl().createDevice(this.dialedDigits);
/* 5638 */     this.call.setCalledDevice(dialedDevice);
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.MakeCallConfHandler
 * JD-Core Version:    0.5.4
 */