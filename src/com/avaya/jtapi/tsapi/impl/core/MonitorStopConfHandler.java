/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ 
/*      */ final class MonitorStopConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSDevice device;
/*      */ 
/*      */   MonitorStopConfHandler(TSDevice _device)
/*      */   {
/* 4694 */     this.device = _device;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 4700 */     if (this.device.monitorDeviceXRefID != 0)
/*      */     {
/* 4702 */       this.device.getTSProviderImpl().deleteMonitor(this.device.monitorDeviceXRefID);
/* 4703 */       this.device.monitorDeviceXRefID = 0;
/*      */     }
/* 4705 */     this.device.testDelete();
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.MonitorStopConfHandler
 * JD-Core Version:    0.5.4
 */