/*    */ package com.avaya.jtapi.tsapi.asn1;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public abstract class ASNSequenceOf extends ASN1
/*    */ {
/*    */   protected Vector<Object> vec;
/*    */ 
/*    */   public ASNSequenceOf()
/*    */   {
/* 16 */     this.vec = new Vector();
/*    */   }
/*    */ 
/*    */   public void doDecode(InputStream in)
/*    */   {
/*    */     try
/*    */     {
/* 23 */       if (in.read() != 48)
/*    */       {
/* 25 */         throw new ASN1Exception("Decoder: expected SEQUENCE tag");
/*    */       }
/* 27 */       int length = decodeLength(in);
/*    */ 
/* 29 */       byte[] buf = new byte[length];
/* 30 */       in.read(buf);
/* 31 */       ByteArrayInputStream memberStream = new ByteArrayInputStream(buf);
/*    */ 
/* 33 */       while (memberStream.available() > 0)
/*    */       {
/* 35 */         this.vec.addElement(decodeMember(memberStream));
/*    */       }
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 40 */       throw new ASN1Exception("Decoder: SEQUENCE OF got unexpected EOF");
/*    */     }
/*    */   }
/*    */ 
/*    */   public void doEncode(int numElements, OutputStream out)
/*    */   {
/*    */     try
/*    */     {
/* 48 */       ByteArrayOutputStream memberStream = new ByteArrayOutputStream();
/*    */ 
/* 50 */       for (int i = 0; i < numElements; ++i) {
/* 51 */         encodeMember(i, memberStream);
/*    */       }
/* 53 */       out.write(48);
/* 54 */       encodeLength(out, memberStream.size());
/* 55 */       memberStream.writeTo(out);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 59 */       throw new ASN1Exception("Encoder: SEQUENCE OF got unexpected IO error");
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object decodeMember(InputStream in)
/*    */   {
/* 65 */     return null;
/*    */   }
/*    */ 
/*    */   public void encodeMember(int index, OutputStream out)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASNSequenceOf
 * JD-Core Version:    0.5.4
 */