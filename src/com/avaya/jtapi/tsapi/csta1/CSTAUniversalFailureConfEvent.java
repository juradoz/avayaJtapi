/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAUniversalFailureConfEvent extends CSTAConfirmation
/*    */ {
/*    */   short error;
/*    */   public static final int PDU = 53;
/*    */ 
/*    */   public CSTAUniversalFailureConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAUniversalFailureConfEvent(short _error)
/*    */   {
/* 16 */     this.error = _error;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 20 */     CSTAUniversalFailure.encode(this.error, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAUniversalFailureConfEvent decode(InputStream in)
/*    */   {
/* 25 */     CSTAUniversalFailureConfEvent _this = new CSTAUniversalFailureConfEvent();
/* 26 */     _this.doDecode(in);
/*    */ 
/* 28 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 33 */     this.error = CSTAUniversalFailure.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 38 */     Collection lines = new ArrayList();
/*    */ 
/* 40 */     lines.add("CSTAUniversalFailureConfEvent ::=");
/* 41 */     lines.add("{");
/*    */ 
/* 43 */     String indent = "  ";
/*    */ 
/* 45 */     lines.addAll(CSTAUniversalFailure.print(this.error, "error", indent));
/*    */ 
/* 47 */     lines.add("}");
/* 48 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 53 */     return 53;
/*    */   }
/*    */ 
/*    */   public short getError()
/*    */   {
/* 59 */     return this.error;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAUniversalFailureConfEvent
 * JD-Core Version:    0.5.4
 */