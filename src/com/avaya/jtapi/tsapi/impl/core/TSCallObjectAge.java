/*    */ package com.avaya.jtapi.tsapi.impl.core;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
/*    */ import java.util.Date;
/*    */ 
/*    */ class TSCallObjectAge
/*    */ {
/*    */   static final String OLD_AGE_DUMP_ANNOTATION = "{AgeThresholdExceeded}";
/*    */   private long _gettime_stamp_milliseconds;
/*    */ 
/*    */   TSCallObjectAge()
/*    */   {
/* 23 */     this._gettime_stamp_milliseconds = new Date().getTime();
/*    */   }
/*    */ 
/*    */   long getAgeMinutes()
/*    */   {
/* 29 */     return (new Date().getTime() - this._gettime_stamp_milliseconds) / 60000L;
/*    */   }
/*    */ 
/*    */   boolean isOld()
/*    */   {
/* 37 */     return getAgeMinutes() > Tsapi.getAuditObjectAgeThreshold();
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 49 */     return getAgeMinutes() + ((isOld()) ? " minutes {AgeThresholdExceeded}" : " minutes");
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSCallObjectAge
 * JD-Core Version:    0.5.4
 */