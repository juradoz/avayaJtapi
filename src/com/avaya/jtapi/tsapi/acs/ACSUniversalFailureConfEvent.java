/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSUniversalFailureConfEvent extends ACSConfirmation
/*    */ {
/*    */   short error;
/*    */   public static final int PDU = 6;
/*    */ 
/*    */   public ACSUniversalFailureConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ACSUniversalFailureConfEvent(short _error)
/*    */   {
/* 15 */     this.error = _error;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 19 */     ACSUniversalFailure.encode(this.error, memberStream);
/*    */   }
/*    */ 
/*    */   public static ACSUniversalFailureConfEvent decode(InputStream in)
/*    */   {
/* 24 */     ACSUniversalFailureConfEvent _this = new ACSUniversalFailureConfEvent();
/* 25 */     _this.doDecode(in);
/*    */ 
/* 27 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 32 */     this.error = ACSUniversalFailure.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 38 */     Collection lines = new ArrayList();
/* 39 */     lines.add("ACSUniversalFailureConfEvent ::=");
/* 40 */     lines.add("{");
/*    */ 
/* 42 */     String indent = "  ";
/*    */ 
/* 44 */     lines.addAll(ACSUniversalFailure.print(this.error, "error", indent));
/*    */ 
/* 46 */     lines.add("}");
/* 47 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 52 */     return 6;
/*    */   }
/*    */ 
/*    */   public short getError()
/*    */   {
/* 58 */     return this.error;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSUniversalFailureConfEvent
 * JD-Core Version:    0.5.4
 */