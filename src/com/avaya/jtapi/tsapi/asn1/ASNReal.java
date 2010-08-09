 package com.avaya.jtapi.tsapi.asn1;
 
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public abstract class ASNReal extends ASN1
 {
   public static final float decode(InputStream in)
   {
     throw new ASN1Exception("Decoder: REAL unimplemented");
   }
 
   public static final void encode(float value, OutputStream out)
   {
     try
     {
       out.write(9);
 
       if ((value == 0.0F) || ((value > -1.E-032D) && (value < 1.E-032D)))
       {
         encodeLength(out, 0);
       }
       else if ((value > 1.E+032D) || (value == (1.0F / 1.0F)))
       {
         encodeLength(out, 1);
         out.write(64);
       }
       else if ((value < -1.E+032D) || (value == (1.0F / -1.0F)))
       {
         encodeLength(out, 1);
         out.write(65);
       }
       else
       {
         int bits = Float.floatToIntBits(value);
 
         int s = ((bits & 0x80000000) == 0) ? 0 : 64;
         int e = (bits >>> 23 & 0xFF) - 150;
         int m = (e == 0) ? (bits & 0x7FFFFF) << 1 : bits & 0x7FFFFF | 0x800000;
 
         int length = 3;
         while ((m & 0xFF) == 0)
         {
           m >>>= 8;
           e += 8;
           --length;
         }
         encodeLength(out, length + 2);
         out.write(0x80 | s);
         out.write(e);
         while (length-- > 0)
         {
           out.write(m >>> length * 8 & 0xFF);
         }
       }
     }
     catch (IOException e)
     {
       throw new ASN1Exception("Encoder: REAL got unexpected IO error");
     }
   }
 
   public static Collection<String> print(float value, String name, String indent)
   {
     Collection lines = new ArrayList();
     StringBuffer buffer = new StringBuffer();
     buffer.append(indent);
     if (name != null) {
       buffer.append(name + " ");
     }
     buffer.append(value);
     lines.add(buffer.toString());
     return lines;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASNReal
 * JD-Core Version:    0.5.4
 */