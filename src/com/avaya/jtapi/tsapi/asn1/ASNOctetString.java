/*    */ package com.avaya.jtapi.tsapi.asn1;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public abstract class ASNOctetString extends ASN1
/*    */ {
/*    */   public static final byte[] decode(InputStream in)
/*    */   {
/*    */     byte[] buf;
/*    */     try
/*    */     {
/* 18 */       if (in.read() != 4)
/*    */       {
/* 20 */         throw new ASN1Exception("Decoder: expected OCTETSTRING tag");
/*    */       }
/* 22 */       int length = decodeLength(in);
/*    */ 
/* 24 */       if (length == 0) return null;
/*    */ 
/* 26 */       buf = new byte[length];
/* 27 */       in.read(buf, 0, length);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 31 */       throw new ASN1Exception("Decoder: OCTETSTRING got unexpected EOF");
/*    */     }
/* 33 */     return buf;
/*    */   }
/*    */ 
/*    */   public static final void encode(byte[] str, OutputStream out)
/*    */   {
/*    */     try
/*    */     {
/* 40 */       out.write(4);
/* 41 */       if (str == null)
/*    */       {
/* 43 */         encodeLength(out, 0);
/*    */       }
/*    */       else
/*    */       {
/* 47 */         encodeLength(out, str.length);
/* 48 */         out.write(str);
/*    */       }
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 53 */       throw new ASN1Exception("Encoder: OCTETSTRING got unexpected IO error");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static final Collection<String> print(byte[] str, String name, String indent)
/*    */   {
/* 59 */     Collection lines = new ArrayList();
/* 60 */     StringBuffer buffer = new StringBuffer();
/* 61 */     buffer.append(indent);
/* 62 */     if (name != null) {
/* 63 */       buffer.append(name + " ");
/*    */     }
/* 65 */     if (str == null)
/*    */     {
/* 67 */       buffer.append("<null>");
/*    */     }
/*    */     else
/*    */     {
/* 71 */       for (int i = 0; i < str.length; ++i)
/*    */       {
/* 73 */         buffer.append(Integer.toHexString(str[i] & 0xFF) + " ");
/*    */       }
/*    */     }
/* 76 */     lines.add(buffer.toString());
/* 77 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASNOctetString
 * JD-Core Version:    0.5.4
 */