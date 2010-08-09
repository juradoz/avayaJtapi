 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.TsapiPlatformException;
 import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
 import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
 import com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceConfEvent;
 import com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceResponseInfo;
 import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
 import java.util.Vector;
 
 final class ProviderSnapshotDeviceConfHandler
   implements ConfHandler
 {
   TSProviderImpl provider;
   Vector<TSCall> cv = new Vector();
 
   ProviderSnapshotDeviceConfHandler(TSProviderImpl _provider) {
     this.provider = _provider;
   }
 
   public void handleConf(CSTAEvent event) {
     if ((event == null) || (!(event.getEvent() instanceof CSTASnapshotDeviceConfEvent)))
     {
       return;
     }
 
     CSTASnapshotDeviceResponseInfo[] info = ((CSTASnapshotDeviceConfEvent)event.getEvent()).getSnapshotData();
 
     if (info != null) {
       TSCall call = null;
       for (int i = 0; i < info.length; ++i)
         try {
           call = this.provider.createCall(info[i].getCallIdentifier().getCallID());
 
           if (call.getTSState() == 34)
           {
             this.provider.dumpCall(info[i].getCallIdentifier().getCallID());
 
             call = this.provider.createCall(info[i].getCallIdentifier().getCallID());
           }
 
           this.cv.addElement(call);
         }
         catch (TsapiPlatformException e)
         {
         }
     }
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.ProviderSnapshotDeviceConfHandler
 * JD-Core Version:    0.5.4
 */