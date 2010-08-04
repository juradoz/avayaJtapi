/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ public class NetworkProgressInfo
/*    */ {
/*    */   public static final short PL_USER = 0;
/*    */   public static final short PL_PUB_LOCAL = 1;
/*    */   public static final short PL_PUB_REMOTE = 4;
/*    */   public static final short PL_PRIV_REMOTE = 5;
/*    */   public static final short PD_CALL_OFF_ISDN = 1;
/*    */   public static final short PD_DEST_NOT_ISDN = 2;
/*    */   public static final short PD_ORIG_NOT_ISDN = 3;
/*    */   public static final short PD_CALL_ON_ISDN = 4;
/*    */   public static final short PD_INBAND = 8;
/*    */   public short progressLocation;
/*    */   public short progressDescription;
/*    */ 
/*    */   NetworkProgressInfo(short location, short description)
/*    */   {
/* 42 */     this.progressLocation = location;
/* 43 */     this.progressDescription = description;
/*    */   }
/*    */ 
/*    */   public NetworkProgressInfo()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.NetworkProgressInfo
 * JD-Core Version:    0.5.4
 */