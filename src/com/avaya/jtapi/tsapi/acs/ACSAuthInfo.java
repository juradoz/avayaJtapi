/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSAuthInfo extends ASNSequence
/*    */ {
/*    */   short authType;
/*    */   String authLoginID;
/*    */ 
/*    */   ACSAuthInfo()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ACSAuthInfo(short _authType, String _authLoginID)
/*    */   {
/* 22 */     this.authType = _authType;
/* 23 */     this.authLoginID = _authLoginID;
/*    */   }
/*    */ 
/*    */   static ACSAuthInfo decode(InputStream in)
/*    */   {
/* 28 */     ACSAuthInfo _this = new ACSAuthInfo();
/* 29 */     _this.doDecode(in);
/*    */ 
/* 31 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 38 */     this.authType = ACSAuthType.decode(memberStream);
/* 39 */     this.authLoginID = LoginID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 44 */     ACSAuthType.encode(this.authType, memberStream);
/* 45 */     LoginID.encode(this.authLoginID, memberStream);
/*    */   }
/*    */ 
/*    */   static Collection<String> print(ACSAuthInfo _this, String name, String _indent)
/*    */   {
/* 50 */     Collection lines = new ArrayList();
/* 51 */     if (_this == null)
/*    */     {
/* 53 */       lines.add(_indent + name + " <null>");
/* 54 */       return lines;
/*    */     }
/* 56 */     if (name != null) {
/* 57 */       lines.add(_indent + name);
/*    */     }
/* 59 */     lines.add(_indent + "{");
/*    */ 
/* 61 */     String indent = _indent + "  ";
/*    */ 
/* 63 */     lines.addAll(ACSAuthType.print(_this.authType, "authType", indent));
/* 64 */     lines.addAll(LoginID.print(_this.authLoginID, "authLoginID", indent));
/*    */ 
/* 66 */     lines.add(_indent + "}");
/* 67 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSAuthInfo
 * JD-Core Version:    0.5.4
 */