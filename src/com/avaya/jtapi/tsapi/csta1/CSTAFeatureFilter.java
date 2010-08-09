 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBitString;
 import java.io.OutputStream;
 import java.util.Collection;
 
 public final class CSTAFeatureFilter extends ASNBitString
 {
   static final int FF_CALL_INFORMATION = -2147483648;
   static final int FF_DO_NOT_DISTURB = 1073741824;
   static final int FF_FORWARDING = 536870912;
   static final int FF_MESSAGE_WAITING = 268435456;
   static final int numBits = 4;
 
   static void encode(int bits, OutputStream out)
   {
     encode(bits, 4, out);
   }
 
   static Collection<String> print(int bits, String name, String indent)
   {
     String str = " ";
 
     if ((bits & 0x80000000) != 0) {
       str = str + "FF_CALL_INFORMATION ";
     }
     if ((bits & 0x40000000) != 0) {
       str = str + "FF_DO_NOT_DISTURB ";
     }
     if ((bits & 0x20000000) != 0) {
       str = str + "FF_FORWARDING ";
     }
     if ((bits & 0x10000000) != 0) {
       str = str + "FF_MESSAGE_WAITING ";
     }
 
     return print(bits, str, name, indent);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAFeatureFilter
 * JD-Core Version:    0.5.4
 */