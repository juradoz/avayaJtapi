/*    */ package com.avaya.jtapi.tsapi.asn1;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public abstract class ASNReal extends ASN1
/*    */ {
/*    */   public static final float decode(InputStream in)
/*    */   {
/* 15 */     throw new ASN1Exception("Decoder: REAL unimplemented");
/*    */   }
/*    */ 
/*    */   public static final void encode(float value, OutputStream out)
/*    */   {
/*    */     try
/*    */     {
/* 37 */       out.write(9);
/*    */ 
/* 43 */       if ((value == 0.0F) || ((value > -1.E-032D) && (value < 1.E-032D)))
/*    */       {
/* 45 */         encodeLength(out, 0);
/*    */       }
/* 47 */       else if ((value > 1.E+032D) || (value == (1.0F / 1.0F)))
/*    */       {
/* 49 */         encodeLength(out, 1);
/* 50 */         out.write(64);
/*    */       }
/* 52 */       else if ((value < -1.E+032D) || (value == (1.0F / -1.0F)))
/*    */       {
/* 54 */         encodeLength(out, 1);
/* 55 */         out.write(65);
/*    */       }
/*    */       else
/*    */       {
/* 59 */         int bits = Float.floatToIntBits(value);
/*    */ 
/* 61 */         int s = ((bits & 0x80000000) == 0) ? 0 : 64;
/* 62 */         int e = (bits >>> 23 & 0xFF) - 150;
/* 63 */         int m = (e == 0) ? (bits & 0x7FFFFF) << 1 : bits & 0x7FFFFF | 0x800000;
/*    */ 
/* 67 */         int length = 3;
/* 68 */         while ((m & 0xFF) == 0)
/*    */         {
/* 70 */           m >>>= 8;
/* 71 */           e += 8;
/* 72 */           --length;
/*    */         }
/* 74 */         encodeLength(out, length + 2);
/* 75 */         out.write(0x80 | s);
/* 76 */         out.write(e);
/* 77 */         while (length-- > 0)
/*    */         {
/* 79 */           out.write(m >>> length * 8 & 0xFF);
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 85 */       throw new ASN1Exception("Encoder: REAL got unexpected IO error");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(float value, String name, String indent)
/*    */   {
/* 91 */     Collection lines = new ArrayList();
/* 92 */     StringBuffer buffer = new StringBuffer();
/* 93 */     buffer.append(indent);
/* 94 */     if (name != null) {
/* 95 */       buffer.append(name + " ");
/*    */     }
/* 97 */     buffer.append(value);
/* 98 */     lines.add(buffer.toString());
/* 99 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASNReal
 * JD-Core Version:    0.5.4
 */