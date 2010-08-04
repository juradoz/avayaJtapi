/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ class TSDeviceStateDeleted extends TSDeviceState
/*      */ {
/*      */   void recreate(TSDevice _tsDevice)
/*      */   {
/* 4244 */     _tsDevice.internalRecreate();
/*      */   }
/*      */   public String toString() {
/* 4247 */     return "Deleted";
/*      */   }
/*      */   boolean wasDeleteDone() {
/* 4250 */     return true;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSDeviceStateDeleted
 * JD-Core Version:    0.5.4
 */