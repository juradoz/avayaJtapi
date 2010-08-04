/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTASendPrivateEv extends CSTARequest
/*    */ {
/*    */   public static final int PDU = 95;
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 13 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 18 */     Collection lines = new ArrayList();
/*    */ 
/* 20 */     lines.add("CSTASendPrivateEv ::=");
/* 21 */     lines.add("{");
/*    */ 
/* 23 */     String indent = "  ";
/*    */ 
/* 25 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 27 */     lines.add("}");
/* 28 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 33 */     return 95;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASendPrivateEv
 * JD-Core Version:    0.5.4
 */