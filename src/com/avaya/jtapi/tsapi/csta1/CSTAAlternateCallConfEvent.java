/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAAlternateCallConfEvent extends CSTAConfirmation
/*    */ {
/*    */   public static final int PDU = 2;
/*    */ 
/*    */   public static CSTAAlternateCallConfEvent decode(InputStream in)
/*    */   {
/* 16 */     CSTAAlternateCallConfEvent _this = new CSTAAlternateCallConfEvent();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 25 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 31 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 37 */     Collection lines = new ArrayList();
/* 38 */     lines.add("CSTAAlternateCallConfEvent ::=");
/* 39 */     lines.add("{");
/*    */ 
/* 41 */     String indent = "  ";
/*    */ 
/* 43 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 45 */     lines.add("}");
/* 46 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 51 */     return 2;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAAlternateCallConfEvent
 * JD-Core Version:    0.5.4
 */