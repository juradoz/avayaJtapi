/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAEscapeSvc extends CSTARequest
/*    */ {
/*    */   public static final int PDU = 89;
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 14 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAEscapeSvc decode(InputStream in)
/*    */   {
/* 19 */     CSTAEscapeSvc _this = new CSTAEscapeSvc();
/* 20 */     _this.doDecode(in);
/*    */ 
/* 22 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 27 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 32 */     Collection lines = new ArrayList();
/* 33 */     lines.add("CSTAEscapeSvc ::=");
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
/* 46 */     return 89;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAEscapeSvc
 * JD-Core Version:    0.5.4
 */