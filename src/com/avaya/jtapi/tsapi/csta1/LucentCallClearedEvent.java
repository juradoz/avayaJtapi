/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentCallClearedEvent extends LucentPrivateData
/*    */ {
/*    */   short reason;
/*    */   static final int PDU = 34;
/*    */ 
/*    */   public static LucentCallClearedEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentCallClearedEvent _this = new LucentCallClearedEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 23 */     this.reason = LucentReasonCode.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 28 */     LucentReasonCode.encode(this.reason, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 33 */     Collection lines = new ArrayList();
/*    */ 
/* 35 */     lines.add("LucentCallClearedEvent ::=");
/* 36 */     lines.add("{");
/*    */ 
/* 38 */     String indent = "  ";
/*    */ 
/* 40 */     lines.addAll(LucentReasonCode.print(this.reason, "reason", indent));
/*    */ 
/* 42 */     lines.add("}");
/* 43 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 48 */     return 34;
/*    */   }
/*    */ 
/*    */   public short getReason()
/*    */   {
/* 54 */     return this.reason;
/*    */   }
/*    */ 
/*    */   public void setReason(short reason) {
/* 58 */     this.reason = reason;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentCallClearedEvent
 * JD-Core Version:    0.5.4
 */