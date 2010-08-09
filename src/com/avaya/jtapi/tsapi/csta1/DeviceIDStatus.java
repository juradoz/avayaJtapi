 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
 import java.util.Collection;
 
 public final class DeviceIDStatus extends ASNEnumerated
 {
   public static final short ID_PROVIDED = 0;
   public static final short ID_NOT_KNOWN = 1;
   public static final short ID_NOT_REQUIRED = 2;
 
   static Collection<String> print(short value, String name, String indent)
   {
     String str;
     switch (value)
     {
     case 0:
       str = "ID_PROVIDED";
       break;
     case 1:
       str = "ID_NOT_KNOWN";
       break;
     case 2:
       str = "ID_NOT_REQUIRED";
       break;
     default:
       str = "?? " + value + " ??";
     }
 
     return print(value, str, name, indent);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.DeviceIDStatus
 * JD-Core Version:    0.5.4
 */