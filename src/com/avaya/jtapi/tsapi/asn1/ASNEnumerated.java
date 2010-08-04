/*    */ package com.avaya.jtapi.tsapi.asn1;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public abstract class ASNEnumerated extends ASN1
/*    */ {
/*    */   public static final short decode(InputStream in)
/*    */   {
/*    */     short value;
/*    */     try
/*    */     {
/* 17 */       if (in.read() != 10)
/*    */       {
/* 19 */         throw new ASN1Exception("Decoder: expected ENUMERATED tag");
/*    */       }
/* 21 */       value = (short)decodeInt(in);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 25 */       throw new ASN1Exception("Decoder: ENUMERATED got unexpected EOF");
/*    */     }
/* 27 */     return value;
/*    */   }
/*    */ 
/*    */   public static final void encode(short value, OutputStream out)
/*    */   {
/*    */     try
/*    */     {
/* 34 */       out.write(10);
/* 35 */       encodeInt(value, out);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 39 */       throw new ASN1Exception("Encoder: ENUMERATED got unexpected IO error");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(short value, String str, String name, String indent)
/*    */   {
/* 45 */     Collection lines = new ArrayList();
/* 46 */     StringBuffer buffer = new StringBuffer();
/* 47 */     buffer.append(indent);
/* 48 */     if (name != null) {
/* 49 */       buffer.append(name + " ");
/*    */     }
/* 51 */     buffer.append(value + " < " + str + " >");
/* 52 */     lines.add(buffer.toString());
/* 53 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASNEnumerated
 * JD-Core Version:    0.5.4
 */