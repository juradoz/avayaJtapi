/*    */ package com.avaya.jtapi.tsapi.asn1;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public abstract class ASNBitString extends ASN1
/*    */ {
/*    */   public static final int decode(InputStream in)
/*    */   {
/* 13 */     int bits = 0;
/*    */     try
/*    */     {
/* 17 */       if (in.read() != 3)
/*    */       {
/* 19 */         throw new ASN1Exception("Decoder: expected BITSTRING tag");
/*    */       }
/* 21 */       int length = decodeLength(in) - 1;
/* 22 */       int unused = in.read();
/* 23 */       int numBits = length * 8 - unused;
/*    */ 
/* 25 */       for (int i = 0; i < 4; ++i)
/*    */       {
/* 27 */         bits = (bits << 8) + ((i < length) ? in.read() : 0);
/*    */       }
/* 29 */       bits &= ((1 << 32 - numBits) - 1 ^ 0xFFFFFFFF);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 33 */       throw new ASN1Exception("Decoder: BITSTRING got unexpected EOF");
/*    */     }
/* 35 */     return bits;
/*    */   }
/*    */ 
/*    */   public static final void encode(int bits, int numBits, OutputStream out)
/*    */   {
/*    */     try
/*    */     {
/* 42 */       int length = (numBits + 7) / 8;
/* 43 */       int unused = length * 8 - numBits;
/*    */ 
/* 45 */       out.write(3);
/* 46 */       encodeLength(out, length + 1);
/* 47 */       out.write(unused);
/*    */ 
/* 49 */       for (int i = 0; i < length; ++i)
/*    */       {
/* 51 */         out.write(bits >>> (3 - i) * 8 & 0xFF);
/*    */       }
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 56 */       throw new ASN1Exception("Encoder: BITSTRING got unexpected IO error");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(int bits, String str, String name, String indent)
/*    */   {
/* 62 */     Collection lines = new ArrayList();
/* 63 */     StringBuffer line = new StringBuffer(indent);
/* 64 */     if (name != null) {
/* 65 */       line.append(name + " ");
/*    */     }
/* 67 */     line.append("0x" + Long.toString(bits & 0xFFFFFFFF, 16) + " <" + str + ">");
/* 68 */     lines.add(line.toString());
/* 69 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASNBitString
 * JD-Core Version:    0.5.4
 */