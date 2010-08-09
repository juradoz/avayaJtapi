 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
 import java.util.Collection;
 
 public final class Interflow extends ASNEnumerated
 {
   public static final short LAI_NO_INTERFLOW = -1;
   public static final short LAI_ALL_INTERFLOW = 0;
   public static final short LAI_THRESHOLD_INTERFLOW = 1;
   public static final short LAI_VECTORING_INTERFLOW = 2;
 
   static Collection<String> print(short value, String name, String indent)
   {
     String str;
     switch (value)
     {
     case -1:
       str = "LAI_NO_INTERFLOW";
       break;
     case 0:
       str = "LAI_ALL_INTERFLOW";
       break;
     case 1:
       str = "LAI_THRESHOLD_INTERFLOW";
       break;
     case 2:
       str = "LAI_VECTORING_INTERFLOW";
       break;
     default:
       str = "?? " + value + " ??";
     }
 
     return print(value, str, name, indent);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.Interflow
 * JD-Core Version:    0.5.4
 */