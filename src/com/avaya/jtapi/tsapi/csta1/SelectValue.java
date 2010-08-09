 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
 import java.util.Collection;
 
 public final class SelectValue extends ASNEnumerated
 {
   public static final short SV_NORMAL = 0;
   public static final short SV_LEAST_COST = 1;
   public static final short SV_EMERGENCY = 2;
   public static final short SV_ACD = 3;
   public static final short SV_USER_DEFINED = 4;
 
   static Collection<String> print(short value, String name, String indent)
   {
     String str;
     switch (value)
     {
     case 0:
       str = "SV_NORMAL";
       break;
     case 1:
       str = "SV_LEAST_COST";
       break;
     case 2:
       str = "SV_EMERGENCY";
       break;
     case 3:
       str = "SV_ACD";
       break;
     case 4:
       str = "SV_USER_DEFINED";
       break;
     default:
       str = "?? " + value + " ??";
     }
 
     return print(value, str, name, indent);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.SelectValue
 * JD-Core Version:    0.5.4
 */