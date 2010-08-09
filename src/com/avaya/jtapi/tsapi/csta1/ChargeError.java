 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
 import java.util.Collection;
 
 public class ChargeError extends ASNEnumerated
 {
   public static final short CE_NONE = 0;
   public static final short CE_NO_FINAL_CHARGE = 1;
   public static final short CE_LESS_FINAL_CHARGE = 2;
   public static final short CE_CHARGE_TOO_LARGE = 3;
   public static final short CE_NETWORK_BUSY = 4;
 
   static Collection<String> print(short value, String name, String indent)
   {
     String str;
     switch (value)
     {
     case 0:
       str = "CE_NONE";
       break;
     case 1:
       str = "CE_NO_FINAL_CHARGE";
       break;
     case 2:
       str = "CE_LESS_FINAL_CHARGE";
       break;
     case 3:
       str = "CE_CHARGE_TOO_LARGE";
       break;
     case 4:
       str = "CE_NETWORK_BUSY";
       break;
     default:
       str = "?? " + value + " ??";
     }
 
     return print(value, str, name, indent);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.ChargeError
 * JD-Core Version:    0.5.4
 */