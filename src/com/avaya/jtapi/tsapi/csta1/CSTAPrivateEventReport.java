/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAPrivateEventReport extends CSTAEventReport
/*    */ {
/*    */   public static final int PDU = 93;
/*    */ 
/*    */   public static CSTAPrivateEventReport decode(InputStream in)
/*    */   {
/* 16 */     CSTAPrivateEventReport _this = new CSTAPrivateEventReport();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 24 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 28 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 32 */     Collection lines = new ArrayList();
/* 33 */     lines.add("CSTAPrivateEvent ::=");
/* 34 */     lines.add("{");
/*    */ 
/* 36 */     String indent = "  ";
/*    */ 
/* 38 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 40 */     lines.add("}");
/* 41 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 46 */     return 93;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAPrivateEventReport
 * JD-Core Version:    0.5.4
 */