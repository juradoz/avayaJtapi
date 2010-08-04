/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSRequestPrivileges extends ACSRequest
/*    */ {
/*    */   public static final int PDU = 17;
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 17 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 22 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public static ACSRequestPrivileges decode(InputStream in)
/*    */   {
/* 27 */     ACSRequestPrivileges _this = new ACSRequestPrivileges();
/* 28 */     _this.doDecode(in);
/*    */ 
/* 30 */     return _this;
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 36 */     Collection lines = new ArrayList();
/* 37 */     lines.add("ACSRequestPrivileges ::=");
/* 38 */     lines.add("{");
/* 39 */     lines.add("}");
/* 40 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 45 */     return 17;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSRequestPrivileges
 * JD-Core Version:    0.5.4
 */