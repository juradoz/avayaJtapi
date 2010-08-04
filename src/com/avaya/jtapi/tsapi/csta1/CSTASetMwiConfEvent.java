/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTASetMwiConfEvent extends CSTAConfirmation
/*    */ {
/*    */   public static final int PDU = 44;
/*    */ 
/*    */   public static CSTASetMwiConfEvent decode(InputStream in)
/*    */   {
/* 15 */     CSTASetMwiConfEvent _this = new CSTASetMwiConfEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 23 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 28 */     Collection lines = new ArrayList();
/*    */ 
/* 30 */     lines.add("CSTASetMwiConfEvent ::=");
/* 31 */     lines.add("{");
/*    */ 
/* 33 */     String indent = "  ";
/*    */ 
/* 35 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 37 */     lines.add("}");
/* 38 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 43 */     return 44;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASetMwiConfEvent
 * JD-Core Version:    0.5.4
 */