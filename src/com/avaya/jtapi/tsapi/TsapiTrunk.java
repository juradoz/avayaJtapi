/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ import javax.telephony.Call;
/*    */ import javax.telephony.Connection;
/*    */ import javax.telephony.callcenter.CallCenterTrunk;
/*    */ 
/*    */ public abstract class TsapiTrunk
/*    */   implements CallCenterTrunk
/*    */ {
/*    */   public abstract String getName();
/*    */ 
/*    */   public abstract int getState();
/*    */ 
/*    */   public abstract int getType();
/*    */ 
/*    */   public abstract Call getCall();
/*    */ 
/*    */   public abstract int hashCode();
/*    */ 
/*    */   public abstract boolean equals(Object paramObject);
/*    */ 
/*    */   public abstract Connection getConnection();
/*    */ 
/*    */   public abstract String getGroupName();
/*    */ 
/*    */   public abstract String getMemberName();
/*    */ 
/*    */   public static String makeTrunkName(String groupName, String memberName)
/*    */   {
/* 53 */     if (groupName == null)
/* 54 */       return null;
/* 55 */     if (memberName == null) {
/* 56 */       return groupName + ":0";
/*    */     }
/* 58 */     return groupName + ":" + memberName;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiTrunk
 * JD-Core Version:    0.5.4
 */