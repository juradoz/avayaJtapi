/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentSingleStepTransferCallConfEvent extends LucentPrivateData
/*    */ {
/*    */   CSTAConnectionID transferredCall;
/*    */   static final int PDU = 143;
/*    */ 
/*    */   public LucentSingleStepTransferCallConfEvent(CSTAConnectionID _transferredCall)
/*    */   {
/* 13 */     this.transferredCall = _transferredCall;
/*    */   }
/*    */ 
/*    */   public LucentSingleStepTransferCallConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public static LucentSingleStepTransferCallConfEvent decode(InputStream in)
/*    */   {
/* 22 */     LucentSingleStepTransferCallConfEvent _this = new LucentSingleStepTransferCallConfEvent();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 25 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 30 */     CSTAConnectionID.encode(this.transferredCall, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 35 */     this.transferredCall = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 40 */     Collection lines = new ArrayList();
/*    */ 
/* 42 */     lines.add("LucentSingleStepTransferCallConfEvent ::=");
/* 43 */     lines.add("{");
/*    */ 
/* 45 */     String indent = "  ";
/*    */ 
/* 47 */     lines.addAll(CSTAConnectionID.print(this.transferredCall, "transferredCall", indent));
/*    */ 
/* 49 */     lines.add("}");
/* 50 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 56 */     return 143;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getTransferredCall()
/*    */   {
/* 64 */     return this.transferredCall;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSingleStepTransferCallConfEvent
 * JD-Core Version:    0.5.4
 */