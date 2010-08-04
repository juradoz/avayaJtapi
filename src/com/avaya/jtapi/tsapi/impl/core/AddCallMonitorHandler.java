/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import java.util.Vector;
/*      */ 
/*      */ final class AddCallMonitorHandler
/*      */   implements SnapshotCallExtraConfHandler
/*      */ {
/*      */   TSCall call;
/*      */   int monitorCrossRefID;
/*      */ 
/*      */   AddCallMonitorHandler(TSCall _call, int _monitorCrossRefID)
/*      */   {
/* 6544 */     this.call = _call;
/* 6545 */     this.monitorCrossRefID = _monitorCrossRefID;
/*      */   }
/*      */ 
/*      */   public Object handleConf(boolean rc, Vector<TSEvent> eventList, Object privateData)
/*      */   {
/* 6550 */     this.call.monitorCrossRefID = this.monitorCrossRefID;
/* 6551 */     this.call.getTSProviderImpl().addMonitor(this.monitorCrossRefID, this.call);
/*      */ 
/* 6553 */     if (this.call.internalDeviceMonitor != null)
/*      */     {
/* 6555 */       this.call.internalDeviceMonitor.removeInternalMonitor(this.call);
/* 6556 */       this.call.internalDeviceMonitor = null;
/*      */     }
/* 6558 */     return null;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.AddCallMonitorHandler
 * JD-Core Version:    0.5.4
 */