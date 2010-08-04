/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSCloseStream extends ACSRequest
/*    */ {
/*    */   public static final int PDU = 3;
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 14 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public static ACSCloseStream decode(InputStream in)
/*    */   {
/* 19 */     ACSCloseStream _this = new ACSCloseStream();
/* 20 */     _this.doDecode(in);
/*    */ 
/* 22 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 28 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 34 */     Collection lines = new ArrayList();
/* 35 */     lines.add("ACSCloseStream ::=");
/* 36 */     lines.add("{");
/*    */ 
/* 38 */     String indent = "  ";
/*    */ 
/* 40 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 42 */     lines.add("}");
/* 43 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 48 */     return 3;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSCloseStream
 * JD-Core Version:    0.5.4
 */