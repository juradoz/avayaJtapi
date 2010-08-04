/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAGroupPickupCallConfEvent extends CSTAConfirmation
/*    */ {
/*    */   public static final int PDU = 20;
/*    */ 
/*    */   public static CSTAGroupPickupCallConfEvent decode(InputStream in)
/*    */   {
/* 15 */     CSTAGroupPickupCallConfEvent _this = new CSTAGroupPickupCallConfEvent();
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
/* 29 */     lines.add("CSTAGroupPickupCallConfEvent ::=");
/* 30 */     lines.add("{");
/*    */ 
/* 32 */     String indent = "  ";
/*    */ 
/* 34 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 36 */     lines.add("}");
/* 37 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 42 */     return 20;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAGroupPickupCallConfEvent
 * JD-Core Version:    0.5.4
 */