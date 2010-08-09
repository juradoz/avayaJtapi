 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
 import java.util.Collection;
 
 public final class LucentDropResource extends ASNEnumerated
 {
   public static final short DR_NONE = -1;
   public static final short DR_CALL_CLASSIFIER = 0;
   public static final short DR_TONE_GENERATOR = 1;
 
   static Collection<String> print(short value, String name, String indent)
   {
     String str;
     switch (value)
     {
     case -1:
       str = "DR_NONE";
       break;
     case 0:
       str = "DR_CALL_CLASSIFIER";
       break;
     case 1:
       str = "DR_TONE_GENERATOR";
       break;
     default:
       str = "?? " + value + " ??";
     }
 
     return print(value, str, name, indent);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentDropResource
 * JD-Core Version:    0.5.4
 */