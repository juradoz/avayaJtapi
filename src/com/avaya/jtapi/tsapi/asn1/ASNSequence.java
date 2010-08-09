 package com.avaya.jtapi.tsapi.asn1;
 
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 import org.apache.log4j.Logger;
 
 public abstract class ASNSequence extends ASN1
 {
   private static Logger log = Logger.getLogger(ASNSequence.class);
 
   public final void doDecode(InputStream in)
   {
     try
     {
       if (in.read() != 48)
       {
         throw new ASN1Exception("Decoder: expected SEQUENCE tag");
       }
       decodeLength(in);
       decodeMembers(in);
     }
     catch (IOException e)
     {
       throw new ASN1Exception("Decoder: SEQUENCE OF got unexpected EOF");
     }
   }
 
   public final void encode(OutputStream out)
   {
     try
     {
       ByteArrayOutputStream memberStream = new ByteArrayOutputStream();
 
       encodeMembers(memberStream);
       out.write(48);
       encodeLength(out, memberStream.size());
       memberStream.writeTo(out);
     }
     catch (IOException e)
     {
       throw new ASN1Exception("Encoder: SEQUENCE got unexpected IO error");
     }
   }
 
   public static void encode(ASNSequence _this, OutputStream out)
   {
     if (_this == null)
     {
       log.error("Encoder: received null sequence");
       throw new ASN1Exception("Encoder: received null sequence");
     }
     _this.encode(out);
   }
   public void decodeMembers(InputStream memberStream) {
   }
   public void encodeMembers(OutputStream memberStream) {
   }
 
   public Collection<String> print() {
     return new ArrayList();
   }
   public int getPDU() { return 0; }
 
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASNSequence
 * JD-Core Version:    0.5.4
 */