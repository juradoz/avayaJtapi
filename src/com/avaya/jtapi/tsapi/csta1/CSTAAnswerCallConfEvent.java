/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAAnswerCallConfEvent extends CSTAConfirmation
/*    */ {
/*    */   public static final int PDU = 4;
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 16 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAAnswerCallConfEvent decode(InputStream in)
/*    */   {
/* 21 */     CSTAAnswerCallConfEvent _this = new CSTAAnswerCallConfEvent();
/* 22 */     _this.doDecode(in);
/*    */ 
/* 24 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 30 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 36 */     Collection lines = new ArrayList();
/* 37 */     lines.add("CSTAAnswerCallConfEvent ::=");
/* 38 */     lines.add("{");
/*    */ 
/* 40 */     String indent = "  ";
/*    */ 
/* 42 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 44 */     lines.add("}");
/* 45 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 50 */     return 4;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAAnswerCallConfEvent
 * JD-Core Version:    0.5.4
 */