 package com.avaya.jtapi.tsapi.impl.core;
 
 import java.util.Vector;
 
 final class AddCallMonitorHandler
   implements SnapshotCallExtraConfHandler
 {
   TSCall call;
   int monitorCrossRefID;
 
   AddCallMonitorHandler(TSCall _call, int _monitorCrossRefID)
   {
     this.call = _call;
     this.monitorCrossRefID = _monitorCrossRefID;
   }
 
   public Object handleConf(boolean rc, Vector<TSEvent> eventList, Object privateData)
   {
     this.call.monitorCrossRefID = this.monitorCrossRefID;
     this.call.getTSProviderImpl().addMonitor(this.monitorCrossRefID, this.call);
 
     if (this.call.internalDeviceMonitor != null)
     {
       this.call.internalDeviceMonitor.removeInternalMonitor(this.call);
       this.call.internalDeviceMonitor = null;
     }
     return null;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.AddCallMonitorHandler
 * JD-Core Version:    0.5.4
 */