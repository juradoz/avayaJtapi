 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
 import java.util.Collection;
 
 public final class LucentDeliveredType extends ASNEnumerated
 {
   public static final short DELIVERED_TO_ACD = 1;
   public static final short DELIVERED_TO_STATION = 2;
   public static final short DELIVERED_OTHER = 3;
 
   public static Collection<String> print(short value, String name, String indent)
   {
     String str;
     switch (value)
     {
     case 1:
       str = "DELIVERED_TO_ACD";
       break;
     case 2:
       str = "DELIVERED_TO_STATION";
       break;
     case 3:
       str = "DELIVERED_OTHER";
       break;
     default:
       str = "?? " + value + " ??";
     }
 
     return print(value, str, name, indent);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentDeliveredType
 * JD-Core Version:    0.5.4
 */