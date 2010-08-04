/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSKeyRequest extends ACSRequest
/*    */ {
/*    */   String loginID;
/*    */   public static final int PDU = 8;
/*    */ 
/*    */   public ACSKeyRequest(String _loginID)
/*    */   {
/* 16 */     this.loginID = _loginID;
/*    */   }
/*    */ 
/*    */   public ACSKeyRequest()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 24 */     LoginID.encode(this.loginID, memberStream);
/*    */   }
/*    */ 
/*    */   public static ACSKeyRequest decode(InputStream in)
/*    */   {
/* 29 */     ACSKeyRequest _this = new ACSKeyRequest();
/* 30 */     _this.doDecode(in);
/*    */ 
/* 32 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 37 */     this.loginID = LoginID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 43 */     Collection lines = new ArrayList();
/* 44 */     lines.add("ACSKeyRequest ::=");
/* 45 */     lines.add("{");
/*    */ 
/* 47 */     String indent = "  ";
/*    */ 
/* 49 */     lines.addAll(LoginID.print(this.loginID, "loginID", indent));
/*    */ 
/* 51 */     lines.add("}");
/* 52 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 57 */     return 8;
/*    */   }
/*    */ 
/*    */   public String getLoginID()
/*    */   {
/* 63 */     return this.loginID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSKeyRequest
 * JD-Core Version:    0.5.4
 */