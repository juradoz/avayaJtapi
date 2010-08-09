 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBitString;
 import java.io.OutputStream;
 import java.util.Collection;
 
 public final class CSTAMaintenanceFilter extends ASNBitString
 {
   static final int MF_BACK_IN_SERVICE = -2147483648;
   static final int MF_OUT_OF_SERVICE = 1073741824;
   static final int numBits = 2;
 
   static void encode(int bits, OutputStream out)
   {
     encode(bits, 2, out);
   }
 
   static Collection<String> print(int bits, String name, String indent)
   {
     String str = " ";
 
     if ((bits & 0x80000000) != 0) {
       str = str + "MF_BACK_IN_SERVICE ";
     }
     if ((bits & 0x40000000) != 0) {
       str = str + "MF_OUT_OF_SERVICE ";
     }
 
     return print(bits, str, name, indent);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMaintenanceFilter
 * JD-Core Version:    0.5.4
 */