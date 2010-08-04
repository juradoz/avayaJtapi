/*    */ package com.avaya.jtapi.tsapi.asn1;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public abstract class ASNNull extends ASN1
/*    */ {
/*    */   public static final void decode(InputStream in)
/*    */   {
/*    */     try
/*    */     {
/* 15 */       if (in.read() != 5)
/*    */       {
/* 17 */         throw new ASN1Exception("Decoder: expected NULL tag");
/*    */       }
/* 19 */       if (decodeLength(in) != 0)
/*    */       {
/* 21 */         throw new ASN1Exception("Decoder: expected NULL to be zero length");
/*    */       }
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 26 */       throw new ASN1Exception("Decoder: NULL got unexpected EOF");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static final void encode(OutputStream out)
/*    */   {
/*    */     try
/*    */     {
/* 34 */       out.write(5);
/* 35 */       encodeLength(out, 0);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 39 */       throw new ASN1Exception("Encoder: NULL got unexpected IO error");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static final Collection<String> print(String indent)
/*    */   {
/* 45 */     Collection lines = new ArrayList();
/* 46 */     lines.add(indent + "NULL");
/* 47 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASNNull
 * JD-Core Version:    0.5.4
 */