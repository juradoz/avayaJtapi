 package com.avaya.jtapi.tsapi.asn1;
 
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 
 public abstract class ASN1
 {
   static final int BOOLEAN_TAG = 1;
   static final int INTEGER_TAG = 2;
   static final int BITSTRING_TAG = 3;
   static final int OCTETSTRING_TAG = 4;
   static final int NULL_TAG = 5;
   static final int REAL_TAG = 9;
   static final int ENUMERATED_TAG = 10;
   static final int SEQUENCE_TAG = 48;
   static final int IA5String_TAG = 22;
 
   static final int decodeInt(InputStream stream)
     throws IOException
   {
     int length = decodeLength(stream);
     if (length > 4)
     {
       throw new ASN1Exception("Decoder: INTEGER/ENUMERATED value is too long");
     }
     int value = stream.read();
 
     if ((value & 0x80) != 0)
     {
       value |= -256;
     }
     for (--length; length > 0; --length)
     {
       value = (value << 8) + stream.read();
     }
     return value;
   }
 
   static final void encodeInt(int value, OutputStream stream)
     throws IOException
   {
     if ((value >= -128) && (value < 128))
     {
       encodeLength(stream, 1);
     }
     else if ((value >= -32768) && (value < 32768))
     {
       encodeLength(stream, 2);
       stream.write(value >>> 8 & 0xFF);
     }
     else if ((value >= -8388608) && (value < 8388608))
     {
       encodeLength(stream, 3);
       stream.write(value >>> 16 & 0xFF);
       stream.write(value >>> 8 & 0xFF);
     }
     else
     {
       encodeLength(stream, 4);
       stream.write(value >>> 24 & 0xFF);
       stream.write(value >>> 16 & 0xFF);
       stream.write(value >>> 8 & 0xFF);
     }
     stream.write(value >>> 0 & 0xFF);
   }
 
   static final void encodeLength(OutputStream stream, int length)
     throws IOException
   {
     if (length < 128)
     {
       stream.write(length);
     }
     else if (length < 256)
     {
       stream.write(129);
       stream.write(length);
     }
     else
     {
       stream.write(130);
       stream.write(length >>> 8 & 0xFF);
       stream.write(length >>> 0 & 0xFF);
     }
   }
 
   static final int decodeLength(InputStream stream)
     throws IOException
   {
     int length = stream.read();
 
     if ((length & 0x80) != 0)
     {
       int numOctets = length & 0xFFFFFF7F;
       length = 0;
       for (; numOctets > 0; --numOctets)
       {
         length = (length << 8) + stream.read();
       }
     }
     return length;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASN1
 * JD-Core Version:    0.5.4
 */