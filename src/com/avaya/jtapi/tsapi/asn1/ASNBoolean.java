/*    */ package com.avaya.jtapi.tsapi.asn1;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public abstract class ASNBoolean extends ASN1
/*    */ {
/*    */   public static final boolean decode(InputStream in)
/*    */   {
/*    */     boolean value;
/*    */     try
/*    */     {
/* 17 */       if (in.read() != 1)
/*    */       {
/* 19 */         throw new ASN1Exception("Decoder: expected BOOLEAN tag");
/*    */       }
/* 21 */       if (decodeLength(in) != 1)
/*    */       {
/* 23 */         throw new ASN1Exception("Decoder: expected BOOLEAN to contain 1 octet");
/*    */       }
/*    */ 
/* 26 */       value = in.read() > 0;
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 30 */       throw new ASN1Exception("Decoder: BOOLEAN got unexpected EOF");
/*    */     }
/* 32 */     return value;
/*    */   }
/*    */ 
/*    */   public static final void encode(boolean value, OutputStream out)
/*    */   {
/*    */     try
/*    */     {
/* 39 */       out.write(1);
/* 40 */       encodeLength(out, 1);
/* 41 */       out.write((value) ? 255 : 0);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 45 */       throw new ASN1Exception("Encoder: BOOLEAN got unexpected IO error");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static final Collection<String> print(boolean value, String name, String indent)
/*    */   {
/* 51 */     Collection lines = new ArrayList();
/* 52 */     StringBuffer line = new StringBuffer(indent);
/* 53 */     if (name != null) {
/* 54 */       line.append(name + " ");
/*    */     }
/* 56 */     line.append((value) ? "TRUE" : "FALSE");
/* 57 */     lines.add(line.toString());
/* 58 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASNBoolean
 * JD-Core Version:    0.5.4
 */