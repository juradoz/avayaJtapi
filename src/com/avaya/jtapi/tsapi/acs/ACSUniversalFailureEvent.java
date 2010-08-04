/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSUniversalFailureEvent extends ACSUnsolicited
/*    */ {
/*    */   short error;
/*    */   public static final int PDU = 7;
/*    */ 
/*    */   public static ACSUniversalFailureEvent decode(InputStream in)
/*    */   {
/* 16 */     ACSUniversalFailureEvent _this = new ACSUniversalFailureEvent();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 24 */     this.error = ACSUniversalFailure.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 30 */     Collection lines = new ArrayList();
/* 31 */     lines.add("ACSUniversalFailureEvent ::=");
/* 32 */     lines.add("{");
/*    */ 
/* 34 */     String indent = "  ";
/*    */ 
/* 36 */     lines.addAll(ACSUniversalFailure.print(this.error, "error", indent));
/*    */ 
/* 38 */     lines.add("}");
/* 39 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 44 */     return 7;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSUniversalFailureEvent
 * JD-Core Version:    0.5.4
 */