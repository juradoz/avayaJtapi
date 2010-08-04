/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTASysStatReq extends CSTARequest
/*    */ {
/*    */   public static final int PDU = 98;
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 21 */     return 98;
/*    */   }
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 24 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTASysStatReq decode(InputStream in) {
/* 28 */     CSTASysStatReq _this = new CSTASysStatReq();
/* 29 */     _this.doDecode(in);
/* 30 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 34 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 38 */     Collection lines = new ArrayList();
/* 39 */     lines.add("CSTASysStatReq ::=");
/* 40 */     lines.add("{");
/*    */ 
/* 42 */     String indent = "  ";
/*    */ 
/* 44 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 46 */     lines.add("}");
/* 47 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASysStatReq
 * JD-Core Version:    0.5.4
 */