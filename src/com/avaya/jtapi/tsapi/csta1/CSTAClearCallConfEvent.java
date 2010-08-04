/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAClearCallConfEvent extends CSTAConfirmation
/*    */ {
/*    */   public static final int PDU = 8;
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 15 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAClearCallConfEvent decode(InputStream in)
/*    */   {
/* 20 */     CSTAClearCallConfEvent _this = new CSTAClearCallConfEvent();
/* 21 */     _this.doDecode(in);
/*    */ 
/* 23 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 28 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 33 */     Collection lines = new ArrayList();
/* 34 */     lines.add("CSTAClearCallConfEvent ::=");
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
/* 47 */     return 8;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAClearCallConfEvent
 * JD-Core Version:    0.5.4
 */