/*     */ package com.avaya.jtapi.tsapi.acs;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class ACSAuthReplyTwo extends ACSConfirmation
/*     */ {
/*     */   int objectID;
/*     */   byte[] key;
/*     */   ACSAuthInfo authInfo;
/*     */   short encodeType;
/*     */   String pipe;
/*     */   public static final int PDU = 13;
/*     */ 
/*     */   public ACSAuthReplyTwo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ACSAuthReplyTwo(int _objectID, byte[] _key, ACSAuthInfo _authInfo, short _encodeType, String _pipe)
/*     */   {
/*  21 */     this.objectID = _objectID;
/*  22 */     this.key = _key;
/*  23 */     this.authInfo = _authInfo;
/*  24 */     this.encodeType = _encodeType;
/*  25 */     this.pipe = _pipe;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  31 */     ASNInteger.encode(this.objectID, memberStream);
/*  32 */     ChallengeKey.encode(this.key, memberStream);
/*  33 */     ACSAuthInfo.encode(this.authInfo, memberStream);
/*  34 */     ACSEncodeType.encode(this.encodeType, memberStream);
/*  35 */     WinNTPipe.encode(this.pipe, memberStream);
/*     */   }
/*     */ 
/*     */   public static ACSAuthReplyTwo decode(InputStream in)
/*     */   {
/*  40 */     ACSAuthReplyTwo _this = new ACSAuthReplyTwo();
/*  41 */     _this.doDecode(in);
/*     */ 
/*  43 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  48 */     this.objectID = ASNInteger.decode(memberStream);
/*  49 */     this.key = ChallengeKey.decode(memberStream);
/*  50 */     this.authInfo = ACSAuthInfo.decode(memberStream);
/*  51 */     this.encodeType = ACSEncodeType.decode(memberStream);
/*  52 */     this.pipe = WinNTPipe.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  58 */     Collection lines = new ArrayList();
/*  59 */     lines.add("ACSAuthReplyTwo ::=");
/*  60 */     lines.add("{");
/*     */ 
/*  62 */     String indent = "  ";
/*     */ 
/*  64 */     lines.addAll(ASNInteger.print(this.objectID, "objectID", indent));
/*  65 */     lines.addAll(ChallengeKey.print(this.key, "key", indent));
/*  66 */     lines.addAll(ACSAuthInfo.print(this.authInfo, "authInfo", indent));
/*  67 */     lines.addAll(ACSEncodeType.print(this.encodeType, "encodeType", indent));
/*  68 */     lines.addAll(WinNTPipe.print(this.pipe, "pipe", indent));
/*     */ 
/*  70 */     lines.add("}");
/*  71 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  76 */     return 13;
/*     */   }
/*     */ 
/*     */   public ACSAuthInfo getAuthInfo()
/*     */   {
/*  82 */     return this.authInfo;
/*     */   }
/*     */ 
/*     */   public short getEncodeType()
/*     */   {
/*  90 */     return this.encodeType;
/*     */   }
/*     */ 
/*     */   public byte[] getKey()
/*     */   {
/*  98 */     return this.key;
/*     */   }
/*     */ 
/*     */   public int getObjectID()
/*     */   {
/* 106 */     return this.objectID;
/*     */   }
/*     */ 
/*     */   public String getPipe()
/*     */   {
/* 114 */     return this.pipe;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSAuthReplyTwo
 * JD-Core Version:    0.5.4
 */