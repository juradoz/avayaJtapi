/*    */ package com.avaya.jtapi.tsapi.impl.core;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class TransferredEventParams
/*    */ {
/*    */   private ArrayList<TSCall> oldCalls;
/*    */ 
/*    */   public ArrayList<TSCall> getOldCalls()
/*    */   {
/* 20 */     return this.oldCalls;
/*    */   }
/*    */ 
/*    */   public TransferredEventParams()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setOldCalls(ArrayList<TSCall> oldCalls)
/*    */   {
/* 31 */     this.oldCalls = oldCalls;
/*    */   }
/*    */ 
/*    */   public TransferredEventParams(ArrayList<TSCall> oldCalls) {
/* 35 */     this.oldCalls = oldCalls;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TransferredEventParams
 * JD-Core Version:    0.5.4
 */