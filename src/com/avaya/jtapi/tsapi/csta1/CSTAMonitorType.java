 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
 import java.util.Collection;
 
 public final class CSTAMonitorType extends ASNEnumerated
 {
   public static final short MT_CALL = 0;
   public static final short MT_DEVICE = 1;
 
   public static Collection<String> print(short value, String name, String indent)
   {
     String str;
     switch (value)
     {
     case 0:
       str = "MT_CALL";
       break;
     case 1:
       str = "MT_DEVICE";
       break;
     default:
       str = "?? " + value + " ??";
     }
 
     return print(value, str, name, indent);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMonitorType
 * JD-Core Version:    0.5.4
 */