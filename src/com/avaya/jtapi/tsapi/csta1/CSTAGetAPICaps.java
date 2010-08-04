/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAGetAPICaps extends CSTARequest
/*    */ {
/*    */   static final int PDU = 124;
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 13 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 18 */     Collection lines = new ArrayList();
/* 19 */     lines.add("CSTAGetAPICaps ::=");
/* 20 */     lines.add("{");
/*    */ 
/* 22 */     String indent = "  ";
/*    */ 
/* 24 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 26 */     lines.add("}");
/* 27 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 32 */     return 124;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAGetAPICaps
 * JD-Core Version:    0.5.4
 */