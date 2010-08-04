/*    */ package com.avaya.jtapi.tsapi.asn1;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public abstract class ASNInteger extends ASN1
/*    */ {
/*    */   public static final int decode(InputStream in)
/*    */   {
/*    */     int value;
/*    */     try
/*    */     {
/* 17 */       if (in.read() != 2)
/*    */       {
/* 19 */         throw new ASN1Exception("Decoder: expected INTEGER tag");
/*    */       }
/* 21 */       value = decodeInt(in);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 25 */       throw new ASN1Exception("Decoder: INTEGER got unexpected EOF");
/*    */     }
/* 27 */     return value;
/*    */   }
/*    */ 
/*    */   public static final void encode(int value, OutputStream out)
/*    */   {
/*    */     try
/*    */     {
/* 34 */       out.write(2);
/* 35 */       encodeInt(value, out);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 39 */       throw new ASN1Exception("Encoder: INTEGER got unexpected IO error");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(int value, String name, String indent)
/*    */   {
/* 45 */     Collection lines = new ArrayList();
/* 46 */     StringBuffer buffer = new StringBuffer();
/* 47 */     buffer.append(indent);
/* 48 */     if (name != null) {
/* 49 */       buffer.append(name + " ");
/*    */     }
/* 51 */     buffer.append(value);
/* 52 */     lines.add(buffer.toString());
/* 53 */     return lines;
/*    */   }
/*    */ 
/*    */   public static final Collection<String> print(String str, String name, String indent)
/*    */   {
/* 58 */     Collection lines = new ArrayList();
/* 59 */     StringBuffer buffer = new StringBuffer();
/* 60 */     buffer.append(indent);
/* 61 */     if (name != null) {
/* 62 */       buffer.append(name + " ");
/*    */     }
/* 64 */     buffer.append(str);
/* 65 */     lines.add(buffer.toString());
/* 66 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASNInteger
 * JD-Core Version:    0.5.4
 */