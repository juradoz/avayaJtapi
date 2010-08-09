 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
 import java.util.Collection;
 
 public final class DeviceType extends ASNEnumerated
 {
   public static final short DT_STATION = 0;
   public static final short DT_LINE = 1;
   public static final short DT_BUTTON = 2;
   public static final short DT_ACD = 3;
   public static final short DT_TRUNK = 4;
   public static final short DT_OPERATOR = 5;
   public static final short DT_STATION_GROUP = 16;
   public static final short DT_LINE_GROUP = 17;
   public static final short DT_BUTTON_GROUP = 18;
   public static final short DT_ACD_GROUP = 19;
   public static final short DT_TRUNK_GROUP = 20;
   public static final short DT_OPERATOR_GROUP = 21;
   public static final short DT_OTHER = 255;
 
   static Collection<String> print(short value, String name, String indent)
   {
     String str;
     switch (value)
     {
     case 0:
       str = "DT_STATION";
       break;
     case 1:
       str = "DT_LINE";
       break;
     case 2:
       str = "DT_BUTTON";
       break;
     case 3:
       str = "DT_ACD";
       break;
     case 4:
       str = "DT_TRUNK";
       break;
     case 5:
       str = "DT_OPERATOR";
       break;
     case 16:
       str = "DT_STATION_GROUP";
       break;
     case 17:
       str = "DT_LINE_GROUP";
       break;
     case 18:
       str = "DT_BUTTON_GROUP";
       break;
     case 19:
       str = "DT_ACD_GROUP";
       break;
     case 20:
       str = "DT_TRUNK_GROUP";
       break;
     case 21:
       str = "DT_OPERATOR_GROUP";
       break;
     case 255:
       str = "DT_OTHER";
       break;
     default:
       str = "?? " + value + " ??";
     }
 
     return print(value, str, name, indent);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.DeviceType
 * JD-Core Version:    0.5.4
 */