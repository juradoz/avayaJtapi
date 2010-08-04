/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTADeflectCallConfEvent extends CSTAConfirmation
/*    */ {
/*    */   public static final int PDU = 16;
/*    */ 
/*    */   public static CSTADeflectCallConfEvent decode(InputStream in)
/*    */   {
/* 16 */     CSTADeflectCallConfEvent _this = new CSTADeflectCallConfEvent();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 24 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 28 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 33 */     Collection lines = new ArrayList();
/* 34 */     lines.add("CSTADeflectCallConfEvent ::=");
/* 35 */     lines.add("{");
/*    */ 
/* 37 */     String indent = "  ";
/*    */ 
/* 39 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 41 */     lines.add("}");
/* 42 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 47 */     return 16;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTADeflectCallConfEvent
 * JD-Core Version:    0.5.4
 */