/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceResponseInfo;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import java.util.Vector;
/*      */ 
/*      */ final class ProviderSnapshotDeviceConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSProviderImpl provider;
/* 3671 */   Vector<TSCall> cv = new Vector();
/*      */ 
/*      */   ProviderSnapshotDeviceConfHandler(TSProviderImpl _provider) {
/* 3674 */     this.provider = _provider;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event) {
/* 3678 */     if ((event == null) || (!(event.getEvent() instanceof CSTASnapshotDeviceConfEvent)))
/*      */     {
/* 3680 */       return;
/*      */     }
/*      */ 
/* 3684 */     CSTASnapshotDeviceResponseInfo[] info = ((CSTASnapshotDeviceConfEvent)event.getEvent()).getSnapshotData();
/*      */ 
/* 3688 */     if (info != null) {
/* 3689 */       TSCall call = null;
/* 3690 */       for (int i = 0; i < info.length; ++i)
/*      */         try {
/* 3692 */           call = this.provider.createCall(info[i].getCallIdentifier().getCallID());
/*      */ 
/* 3694 */           if (call.getTSState() == 34)
/*      */           {
/* 3698 */             this.provider.dumpCall(info[i].getCallIdentifier().getCallID());
/*      */ 
/* 3700 */             call = this.provider.createCall(info[i].getCallIdentifier().getCallID());
/*      */           }
/*      */ 
/* 3708 */           this.cv.addElement(call);
/*      */         }
/*      */         catch (TsapiPlatformException e)
/*      */         {
/*      */         }
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.ProviderSnapshotDeviceConfHandler
 * JD-Core Version:    0.5.4
 */