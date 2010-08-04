/*     */ package com.avaya.jtapi.tsapi.asn1;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public abstract class ASN1
/*     */ {
/*     */   static final int BOOLEAN_TAG = 1;
/*     */   static final int INTEGER_TAG = 2;
/*     */   static final int BITSTRING_TAG = 3;
/*     */   static final int OCTETSTRING_TAG = 4;
/*     */   static final int NULL_TAG = 5;
/*     */   static final int REAL_TAG = 9;
/*     */   static final int ENUMERATED_TAG = 10;
/*     */   static final int SEQUENCE_TAG = 48;
/*     */   static final int IA5String_TAG = 22;
/*     */ 
/*     */   static final int decodeInt(InputStream stream)
/*     */     throws IOException
/*     */   {
/*  22 */     int length = decodeLength(stream);
/*  23 */     if (length > 4)
/*     */     {
/*  25 */       throw new ASN1Exception("Decoder: INTEGER/ENUMERATED value is too long");
/*     */     }
/*  27 */     int value = stream.read();
/*     */ 
/*  29 */     if ((value & 0x80) != 0)
/*     */     {
/*  31 */       value |= -256;
/*     */     }
/*  33 */     for (--length; length > 0; --length)
/*     */     {
/*  35 */       value = (value << 8) + stream.read();
/*     */     }
/*  37 */     return value;
/*     */   }
/*     */ 
/*     */   static final void encodeInt(int value, OutputStream stream)
/*     */     throws IOException
/*     */   {
/*  43 */     if ((value >= -128) && (value < 128))
/*     */     {
/*  45 */       encodeLength(stream, 1);
/*     */     }
/*  47 */     else if ((value >= -32768) && (value < 32768))
/*     */     {
/*  49 */       encodeLength(stream, 2);
/*  50 */       stream.write(value >>> 8 & 0xFF);
/*     */     }
/*  52 */     else if ((value >= -8388608) && (value < 8388608))
/*     */     {
/*  54 */       encodeLength(stream, 3);
/*  55 */       stream.write(value >>> 16 & 0xFF);
/*  56 */       stream.write(value >>> 8 & 0xFF);
/*     */     }
/*     */     else
/*     */     {
/*  60 */       encodeLength(stream, 4);
/*  61 */       stream.write(value >>> 24 & 0xFF);
/*  62 */       stream.write(value >>> 16 & 0xFF);
/*  63 */       stream.write(value >>> 8 & 0xFF);
/*     */     }
/*  65 */     stream.write(value >>> 0 & 0xFF);
/*     */   }
/*     */ 
/*     */   static final void encodeLength(OutputStream stream, int length)
/*     */     throws IOException
/*     */   {
/*  71 */     if (length < 128)
/*     */     {
/*  73 */       stream.write(length);
/*     */     }
/*  75 */     else if (length < 256)
/*     */     {
/*  77 */       stream.write(129);
/*  78 */       stream.write(length);
/*     */     }
/*     */     else
/*     */     {
/*  82 */       stream.write(130);
/*  83 */       stream.write(length >>> 8 & 0xFF);
/*  84 */       stream.write(length >>> 0 & 0xFF);
/*     */     }
/*     */   }
/*     */ 
/*     */   static final int decodeLength(InputStream stream)
/*     */     throws IOException
/*     */   {
/*  92 */     int length = stream.read();
/*     */ 
/*  94 */     if ((length & 0x80) != 0)
/*     */     {
/*  96 */       int numOctets = length & 0xFFFFFF7F;
/*  97 */       length = 0;
/*  98 */       for (; numOctets > 0; --numOctets)
/*     */       {
/* 100 */         length = (length << 8) + stream.read();
/*     */       }
/*     */     }
/* 103 */     return length;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASN1
 * JD-Core Version:    0.5.4
 */