/*    */ package com.avaya.jtapi.tsapi.asn1;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public abstract class ASNSequence extends ASN1
/*    */ {
/* 13 */   private static Logger log = Logger.getLogger(ASNSequence.class);
/*    */ 
/*    */   public final void doDecode(InputStream in)
/*    */   {
/*    */     try
/*    */     {
/* 19 */       if (in.read() != 48)
/*    */       {
/* 21 */         throw new ASN1Exception("Decoder: expected SEQUENCE tag");
/*    */       }
/* 23 */       decodeLength(in);
/* 24 */       decodeMembers(in);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 28 */       throw new ASN1Exception("Decoder: SEQUENCE OF got unexpected EOF");
/*    */     }
/*    */   }
/*    */ 
/*    */   public final void encode(OutputStream out)
/*    */   {
/*    */     try
/*    */     {
/* 37 */       ByteArrayOutputStream memberStream = new ByteArrayOutputStream();
/*    */ 
/* 39 */       encodeMembers(memberStream);
/* 40 */       out.write(48);
/* 41 */       encodeLength(out, memberStream.size());
/* 42 */       memberStream.writeTo(out);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 46 */       throw new ASN1Exception("Encoder: SEQUENCE got unexpected IO error");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void encode(ASNSequence _this, OutputStream out)
/*    */   {
/* 53 */     if (_this == null)
/*    */     {
/* 55 */       log.error("Encoder: received null sequence");
/* 56 */       throw new ASN1Exception("Encoder: received null sequence");
/*    */     }
/* 58 */     _this.encode(out);
/*    */   }
/*    */   public void decodeMembers(InputStream memberStream) {
/*    */   }
/*    */   public void encodeMembers(OutputStream memberStream) {
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 66 */     return new ArrayList();
/*    */   }
/* 68 */   public int getPDU() { return 0; }
/*    */ 
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASNSequence
 * JD-Core Version:    0.5.4
 */