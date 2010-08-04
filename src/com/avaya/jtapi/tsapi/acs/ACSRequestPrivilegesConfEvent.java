/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSRequestPrivilegesConfEvent extends ACSConfirmation
/*    */ {
/*    */   String nonce;
/*    */   public static final int PDU = 18;
/*    */ 
/*    */   public ACSRequestPrivilegesConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ACSRequestPrivilegesConfEvent(String nonce)
/*    */   {
/* 43 */     this.nonce = nonce;
/*    */   }
/*    */ 
/*    */   public static ACSRequestPrivilegesConfEvent decode(InputStream in)
/*    */   {
/* 48 */     ACSRequestPrivilegesConfEvent _this = new ACSRequestPrivilegesConfEvent();
/*    */ 
/* 50 */     _this.doDecode(in);
/*    */ 
/* 52 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 57 */     this.nonce = ASNIA5String.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 62 */     ASNIA5String.encode(this.nonce, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 68 */     Collection lines = new ArrayList();
/* 69 */     lines.add("ACSRequestPrivilegesConfEvent ::=");
/* 70 */     lines.add("{");
/*    */ 
/* 72 */     String indent = "  ";
/*    */ 
/* 74 */     lines.addAll(ASNIA5String.print(this.nonce, "nonce", indent));
/*    */ 
/* 76 */     lines.add("}");
/* 77 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 82 */     return 18;
/*    */   }
/*    */ 
/*    */   public synchronized String get_nonce()
/*    */   {
/* 89 */     return this.nonce;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSRequestPrivilegesConfEvent
 * JD-Core Version:    0.5.4
 */