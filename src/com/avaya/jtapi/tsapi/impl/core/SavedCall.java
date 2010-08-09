 package com.avaya.jtapi.tsapi.impl.core;
 
 final class SavedCall
 {
   TSCall call;
   long saveTime;
 
   SavedCall(TSCall _call)
   {
     this.call = _call;
     this.saveTime = System.currentTimeMillis();
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.SavedCall
 * JD-Core Version:    0.5.4
 */