/*    */ package com.avaya.jtapi.tsapi.asn1;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public abstract class ASNIA5String extends ASN1
/*    */ {
/*    */   public static final String decode(InputStream in)
/*    */   {
/*    */     byte[] buf;
/*    */     try
/*    */     {
/* 17 */       if (in.read() != 22)
/*    */       {
/* 19 */         throw new ASN1Exception("Decoder: expected IA5String tag");
/*    */       }
/* 21 */       int length = decodeLength(in);
/*    */ 
/* 23 */       if (length == 0) return null;
/*    */ 
/* 25 */       buf = new byte[length];
/* 26 */       in.read(buf, 0, length);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 30 */       throw new ASN1Exception("Decoder: IA5String got unexpected EOF");
/*    */     }
/* 32 */     return new String(buf);
/*    */   }
/*    */ 
/*    */   public static final void encode(String str, OutputStream out)
/*    */   {
/*    */     try
/*    */     {
/* 39 */       out.write(22);
/* 40 */       if (str == null)
/*    */       {
/* 42 */         encodeLength(out, 0);
/*    */       }
/*    */       else
/*    */       {
/* 46 */         int length = str.length();
/*    */ 
/* 48 */         encodeLength(out, length);
/* 49 */         byte[] buf = new byte[length];
/* 50 */         buf = str.getBytes();
/* 51 */         out.write(buf);
/*    */       }
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 56 */       throw new ASN1Exception("Encoder: IA5String got unexpected IO error");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static final Collection<String> print(String str, String name, String indent)
/*    */   {
/* 62 */     Collection lines = new ArrayList();
/* 63 */     StringBuffer buffer = new StringBuffer();
/* 64 */     buffer.append(indent);
/* 65 */     if (name != null) {
/* 66 */       buffer.append(name + " ");
/*    */     }
/* 68 */     if (str == null)
/*    */     {
/* 70 */       lines.add(buffer.toString() + "<null>");
/*    */     }
/*    */     else
/*    */     {
/* 74 */       lines.add(buffer.toString() + "\"" + str + "\"");
/*    */     }
/* 76 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASNIA5String
 * JD-Core Version:    0.5.4
 */