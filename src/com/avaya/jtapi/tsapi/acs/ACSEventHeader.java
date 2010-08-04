/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ public final class ACSEventHeader
/*    */ {
/*    */   public static final int ACSREQUEST = 0;
/*    */   public static final int ACSUNSOLICITED = 1;
/*    */   public static final int ACSCONFIRMATION = 2;
/*    */   public static final int CSTAREQUEST = 3;
/*    */   public static final int CSTAUNSOLICITED = 4;
/*    */   public static final int CSTACONFIRMATION = 5;
/*    */   public static final int CSTAEVENTREPORT = 6;
/*    */   private int eventClass;
/*    */   private int eventType;
/*    */ 
/*    */   public int getEventClass()
/*    */   {
/* 22 */     return this.eventClass;
/*    */   }
/*    */ 
/*    */   public int getEventType()
/*    */   {
/* 30 */     return this.eventType;
/*    */   }
/*    */ 
/*    */   public ACSEventHeader(int _eventClass, int _eventType)
/*    */   {
/* 36 */     this.eventClass = _eventClass;
/* 37 */     this.eventType = _eventType;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSEventHeader
 * JD-Core Version:    0.5.4
 */