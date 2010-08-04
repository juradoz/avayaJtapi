/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSAuthReply extends ACSConfirmation
/*    */ {
/*    */   int objectID;
/*    */   byte[] key;
/*    */   ACSAuthInfo authInfo;
/*    */   public static final int PDU = 12;
/*    */ 
/*    */   public ACSAuthInfo getAuthInfo()
/*    */   {
/* 21 */     return this.authInfo;
/*    */   }
/*    */ 
/*    */   public byte[] getKey()
/*    */   {
/* 29 */     return this.key;
/*    */   }
/*    */ 
/*    */   public int getObjectID()
/*    */   {
/* 37 */     return this.objectID;
/*    */   }
/*    */ 
/*    */   public static ACSAuthReply decode(InputStream in)
/*    */   {
/* 43 */     ACSAuthReply _this = new ACSAuthReply();
/* 44 */     _this.doDecode(in);
/*    */ 
/* 46 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 51 */     this.objectID = ASNInteger.decode(memberStream);
/* 52 */     this.key = ChallengeKey.decode(memberStream);
/* 53 */     this.authInfo = ACSAuthInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 59 */     Collection lines = new ArrayList();
/* 60 */     lines.add("ACSAuthReply ::=");
/* 61 */     lines.add("{");
/*    */ 
/* 63 */     String indent = "  ";
/*    */ 
/* 65 */     lines.addAll(ASNInteger.print(this.objectID, "objectID", indent));
/* 66 */     lines.addAll(ChallengeKey.print(this.key, "key", indent));
/* 67 */     lines.addAll(ACSAuthInfo.print(this.authInfo, "authInfo", indent));
/*    */ 
/* 69 */     lines.add("}");
/* 70 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 75 */     return 12;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSAuthReply
 * JD-Core Version:    0.5.4
 */