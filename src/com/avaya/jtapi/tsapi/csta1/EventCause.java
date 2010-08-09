 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
 import java.util.Collection;
 
 class EventCause extends ASNEnumerated
 {
   public static final short EC_NONE = -1;
   public static final short EC_ACTIVE_MONITOR = 1;
   public static final short EC_ALTERNATE = 2;
   public static final short EC_BUSY = 3;
   public static final short EC_CALL_BACK = 4;
   public static final short EC_CALL_CANCELLED = 5;
   public static final short EC_CALL_FORWARD_ALWAYS = 6;
   public static final short EC_CALL_FORWARD_BUSY = 7;
   public static final short EC_CALL_FORWARD_NO_ANSWER = 8;
   public static final short EC_CALL_FORWARD = 9;
   public static final short EC_CALL_NOT_ANSWERED = 10;
   public static final short EC_CALL_PICKUP = 11;
   public static final short EC_CAMP_ON = 12;
   public static final short EC_DEST_NOT_OBTAINABLE = 13;
   public static final short EC_DO_NOT_DISTURB = 14;
   public static final short EC_INCOMPATIBLE_DESTINATION = 15;
   public static final short EC_INVALID_ACCOUNT_CODE = 16;
   public static final short EC_KEY_CONFERENCE = 17;
   public static final short EC_LOCKOUT = 18;
   public static final short EC_MAINTENANCE = 19;
   public static final short EC_NETWORK_CONGESTION = 20;
   public static final short EC_NETWORK_NOT_OBTAINABLE = 21;
   public static final short EC_NEW_CALL = 22;
   public static final short EC_NO_AVAILABLE_AGENTS = 23;
   public static final short EC_OVERRIDE = 24;
   public static final short EC_PARK = 25;
   public static final short EC_OVERFLOW = 26;
   public static final short EC_RECALL = 27;
   public static final short EC_REDIRECTED = 28;
   public static final short EC_REORDER_TONE = 29;
   public static final short EC_RESOURCES_NOT_AVAILABLE = 30;
   public static final short EC_SILENT_MONITOR = 31;
   public static final short EC_TRANSFER = 32;
   public static final short EC_TRUNKS_BUSY = 33;
   public static final short EC_VOICE_UNIT_INITIATOR = 34;
   public static final short EC_NETWORK_SIGNAL = 46;
   public static final short EC_SINGLE_STEP_TRANSFER = 52;
   public static final short EC_ALERT_TIME_EXPIRED = 60;
   public static final short EC_DESTINATION_OUT_OF_ORDER = 65;
   public static final short EC_NOT_SUPPORTED_BEARER_SERVICE = 80;
   public static final short EC_UNASSIGNED_NUMBER = 81;
   public static final short EC_INCOMPATIBLE_BEARER_SERVICE = 87;
 
   static Collection<String> print(short value, String name, String indent)
   {
     String str;
     switch (value)
     {
     case -1:
       str = "EC_NONE";
       break;
     case 1:
       str = "EC_ACTIVE_MONITOR";
       break;
     case 2:
       str = "EC_ALTERNATE";
       break;
     case 3:
       str = "EC_BUSY";
       break;
     case 4:
       str = "EC_CALL_BACK";
       break;
     case 5:
       str = "EC_CALL_CANCELLED";
       break;
     case 6:
       str = "EC_CALL_FORWARD_ALWAYS";
       break;
     case 7:
       str = "EC_CALL_FORWARD_BUSY";
       break;
     case 8:
       str = "EC_CALL_FORWARD_NO_ANSWER";
       break;
     case 9:
       str = "EC_CALL_FORWARD";
       break;
     case 10:
       str = "EC_CALL_NOT_ANSWERED";
       break;
     case 11:
       str = "EC_CALL_PICKUP";
       break;
     case 12:
       str = "EC_CAMP_ON";
       break;
     case 13:
       str = "EC_DEST_NOT_OBTAINABLE";
       break;
     case 14:
       str = "EC_DO_NOT_DISTURB";
       break;
     case 15:
       str = "EC_INCOMPATIBLE_DESTINATION";
       break;
     case 16:
       str = "EC_INVALID_ACCOUNT_CODE";
       break;
     case 17:
       str = "EC_KEY_CONFERENCE";
       break;
     case 18:
       str = "EC_LOCKOUT";
       break;
     case 19:
       str = "EC_MAINTENANCE";
       break;
     case 20:
       str = "EC_NETWORK_CONGESTION";
       break;
     case 21:
       str = "EC_NETWORK_NOT_OBTAINABLE";
       break;
     case 22:
       str = "EC_NEW_CALL";
       break;
     case 23:
       str = "EC_NO_AVAILABLE_AGENTS";
       break;
     case 24:
       str = "EC_OVERRIDE";
       break;
     case 25:
       str = "EC_PARK";
       break;
     case 26:
       str = "EC_OVERFLOW";
       break;
     case 27:
       str = "EC_RECALL";
       break;
     case 28:
       str = "EC_REDIRECTED";
       break;
     case 29:
       str = "EC_REORDER_TONE";
       break;
     case 30:
       str = "EC_RESOURCES_NOT_AVAILABLE";
       break;
     case 31:
       str = "EC_SILENT_MONITOR";
       break;
     case 52:
       str = "EC_SINGLE_STEP_TRANSFER";
       break;
     case 32:
       str = "EC_TRANSFER";
       break;
     case 33:
       str = "EC_TRUNKS_BUSY";
       break;
     case 34:
       str = "EC_VOICE_UNIT_INITIATOR";
       break;
     case 46:
       str = "EC_NETWORK_SIGNAL";
       break;
     case 60:
       str = "EC_ALERT_TIME_EXPIRED";
       break;
     case 65:
       str = "EC_DESTINATION_OUT_OF_ORDER";
       break;
     case 80:
       str = "EC_NOT_SUPPORTED_BEARER_SERVICE";
       break;
     case 81:
       str = "EC_UNASSIGNED_NUMBER";
       break;
     case 87:
       str = "EC_INCOMPATIBLE_BEARER_SERVICE";
       break;
     case 0:
     case 35:
     case 36:
     case 37:
     case 38:
     case 39:
     case 40:
     case 41:
     case 42:
     case 43:
     case 44:
     case 45:
     case 47:
     case 48:
     case 49:
     case 50:
     case 51:
     case 53:
     case 54:
     case 55:
     case 56:
     case 57:
     case 58:
     case 59:
     case 61:
     case 62:
     case 63:
     case 64:
     case 66:
     case 67:
     case 68:
     case 69:
     case 70:
     case 71:
     case 72:
     case 73:
     case 74:
     case 75:
     case 76:
     case 77:
     case 78:
     case 79:
     case 82:
     case 83:
     case 84:
     case 85:
     case 86:
     default:
       str = "?? " + value + " ??";
     }
 
     return print(value, str, name, indent);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.EventCause
 * JD-Core Version:    0.5.4
 */