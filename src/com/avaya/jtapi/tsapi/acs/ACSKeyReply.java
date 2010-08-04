/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSKeyReply extends ACSConfirmation
/*    */ {
/*    */   int objectID;
/*    */   byte[] key;
/*    */   public static final int PDU = 9;
/*    */ 
/*    */   public static ACSKeyReply decode(InputStream in)
/*    */   {
/* 18 */     ACSKeyReply _this = new ACSKeyReply();
/* 19 */     _this.doDecode(in);
/*    */ 
/* 21 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 26 */     this.objectID = ASNInteger.decode(memberStream);
/* 27 */     this.key = ChallengeKey.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 33 */     Collection lines = new ArrayList();
/* 34 */     lines.add("ACSKeyReply ::=");
/* 35 */     lines.add("{");
/*    */ 
/* 37 */     String indent = "  ";
/*    */ 
/* 39 */     lines.addAll(ASNInteger.print(this.objectID, "objectID", indent));
/* 40 */     lines.addAll(ChallengeKey.print(this.key, "key", indent));
/*    */ 
/* 42 */     lines.add("}");
/* 43 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 48 */     return 9;
/*    */   }
/*    */ 
/*    */   public byte[] getKey()
/*    */   {
/* 54 */     return this.key;
/*    */   }
/*    */ 
/*    */   public int getObjectID()
/*    */   {
/* 62 */     return this.objectID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSKeyReply
 * JD-Core Version:    0.5.4
 */