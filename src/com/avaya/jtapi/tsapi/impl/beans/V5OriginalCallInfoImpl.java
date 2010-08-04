/*    */ package com.avaya.jtapi.tsapi.impl.beans;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.V5OriginalCallInfo;
/*    */ 
/*    */ public class V5OriginalCallInfoImpl extends OriginalCallInfoImpl
/*    */   implements V5OriginalCallInfo
/*    */ {
/*    */   private String ucid;
/*    */   int callOriginatorType;
/*    */   private boolean hasCallOriginatorType;
/*    */   private boolean flexibleBilling;
/*    */ 
/*    */   public V5OriginalCallInfoImpl()
/*    */   {
/* 11 */     this.callOriginatorType = -1;
/* 12 */     this.hasCallOriginatorType = false;
/* 13 */     this.flexibleBilling = false;
/*    */   }
/*    */ 
/*    */   public String getUCID()
/*    */   {
/* 20 */     return this.ucid;
/*    */   }
/*    */ 
/*    */   public void setCallOriginatorType(int callOriginatorType) {
/* 24 */     this.callOriginatorType = callOriginatorType;
/*    */   }
/*    */ 
/*    */   public int getCallOriginatorType()
/*    */   {
/* 61 */     return this.callOriginatorType;
/*    */   }
/*    */ 
/*    */   public void setHasCallOriginatorType(boolean hasCallOriginatorType) {
/* 65 */     this.hasCallOriginatorType = hasCallOriginatorType;
/*    */   }
/*    */ 
/*    */   public boolean hasCallOriginatorType()
/*    */   {
/* 73 */     return this.hasCallOriginatorType;
/*    */   }
/*    */ 
/*    */   public boolean canSetBillRate()
/*    */   {
/* 82 */     return this.flexibleBilling;
/*    */   }
/*    */ 
/*    */   public void setUCID(String ucid)
/*    */   {
/* 87 */     this.ucid = ucid;
/*    */   }
/*    */ 
/*    */   public void setFlexibleBilling(boolean _flexibleBilling) {
/* 91 */     this.flexibleBilling = _flexibleBilling;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.beans.V5OriginalCallInfoImpl
 * JD-Core Version:    0.5.4
 */