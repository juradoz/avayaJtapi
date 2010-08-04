/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import java.util.Vector;
/*      */ 
/*      */ final class DeviceObs
/*      */ {
/*      */   TsapiCallMonitor callback;
/* 5519 */   Vector<DevWithType> devWithTypeVector = new Vector();
/*      */ 
/*      */   DeviceObs(TsapiCallMonitor _callback) {
/* 5522 */     this.callback = _callback;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.DeviceObs
 * JD-Core Version:    0.5.4
 */