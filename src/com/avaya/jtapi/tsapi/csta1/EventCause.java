/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*     */ import java.util.Collection;
/*     */ 
/*     */ class EventCause extends ASNEnumerated
/*     */ {
/*     */   public static final short EC_NONE = -1;
/*     */   public static final short EC_ACTIVE_MONITOR = 1;
/*     */   public static final short EC_ALTERNATE = 2;
/*     */   public static final short EC_BUSY = 3;
/*     */   public static final short EC_CALL_BACK = 4;
/*     */   public static final short EC_CALL_CANCELLED = 5;
/*     */   public static final short EC_CALL_FORWARD_ALWAYS = 6;
/*     */   public static final short EC_CALL_FORWARD_BUSY = 7;
/*     */   public static final short EC_CALL_FORWARD_NO_ANSWER = 8;
/*     */   public static final short EC_CALL_FORWARD = 9;
/*     */   public static final short EC_CALL_NOT_ANSWERED = 10;
/*     */   public static final short EC_CALL_PICKUP = 11;
/*     */   public static final short EC_CAMP_ON = 12;
/*     */   public static final short EC_DEST_NOT_OBTAINABLE = 13;
/*     */   public static final short EC_DO_NOT_DISTURB = 14;
/*     */   public static final short EC_INCOMPATIBLE_DESTINATION = 15;
/*     */   public static final short EC_INVALID_ACCOUNT_CODE = 16;
/*     */   public static final short EC_KEY_CONFERENCE = 17;
/*     */   public static final short EC_LOCKOUT = 18;
/*     */   public static final short EC_MAINTENANCE = 19;
/*     */   public static final short EC_NETWORK_CONGESTION = 20;
/*     */   public static final short EC_NETWORK_NOT_OBTAINABLE = 21;
/*     */   public static final short EC_NEW_CALL = 22;
/*     */   public static final short EC_NO_AVAILABLE_AGENTS = 23;
/*     */   public static final short EC_OVERRIDE = 24;
/*     */   public static final short EC_PARK = 25;
/*     */   public static final short EC_OVERFLOW = 26;
/*     */   public static final short EC_RECALL = 27;
/*     */   public static final short EC_REDIRECTED = 28;
/*     */   public static final short EC_REORDER_TONE = 29;
/*     */   public static final short EC_RESOURCES_NOT_AVAILABLE = 30;
/*     */   public static final short EC_SILENT_MONITOR = 31;
/*     */   public static final short EC_TRANSFER = 32;
/*     */   public static final short EC_TRUNKS_BUSY = 33;
/*     */   public static final short EC_VOICE_UNIT_INITIATOR = 34;
/*     */   public static final short EC_NETWORK_SIGNAL = 46;
/*     */   public static final short EC_SINGLE_STEP_TRANSFER = 52;
/*     */   public static final short EC_ALERT_TIME_EXPIRED = 60;
/*     */   public static final short EC_DESTINATION_OUT_OF_ORDER = 65;
/*     */   public static final short EC_NOT_SUPPORTED_BEARER_SERVICE = 80;
/*     */   public static final short EC_UNASSIGNED_NUMBER = 81;
/*     */   public static final short EC_INCOMPATIBLE_BEARER_SERVICE = 87;
/*     */ 
/*     */   static Collection<String> print(short value, String name, String indent)
/*     */   {
/*     */     String str;
/* 183 */     switch (value)
/*     */     {
/*     */     case -1:
/* 186 */       str = "EC_NONE";
/* 187 */       break;
/*     */     case 1:
/* 190 */       str = "EC_ACTIVE_MONITOR";
/* 191 */       break;
/*     */     case 2:
/* 194 */       str = "EC_ALTERNATE";
/* 195 */       break;
/*     */     case 3:
/* 198 */       str = "EC_BUSY";
/* 199 */       break;
/*     */     case 4:
/* 202 */       str = "EC_CALL_BACK";
/* 203 */       break;
/*     */     case 5:
/* 206 */       str = "EC_CALL_CANCELLED";
/* 207 */       break;
/*     */     case 6:
/* 210 */       str = "EC_CALL_FORWARD_ALWAYS";
/* 211 */       break;
/*     */     case 7:
/* 214 */       str = "EC_CALL_FORWARD_BUSY";
/* 215 */       break;
/*     */     case 8:
/* 218 */       str = "EC_CALL_FORWARD_NO_ANSWER";
/* 219 */       break;
/*     */     case 9:
/* 222 */       str = "EC_CALL_FORWARD";
/* 223 */       break;
/*     */     case 10:
/* 226 */       str = "EC_CALL_NOT_ANSWERED";
/* 227 */       break;
/*     */     case 11:
/* 230 */       str = "EC_CALL_PICKUP";
/* 231 */       break;
/*     */     case 12:
/* 234 */       str = "EC_CAMP_ON";
/* 235 */       break;
/*     */     case 13:
/* 238 */       str = "EC_DEST_NOT_OBTAINABLE";
/* 239 */       break;
/*     */     case 14:
/* 242 */       str = "EC_DO_NOT_DISTURB";
/* 243 */       break;
/*     */     case 15:
/* 246 */       str = "EC_INCOMPATIBLE_DESTINATION";
/* 247 */       break;
/*     */     case 16:
/* 250 */       str = "EC_INVALID_ACCOUNT_CODE";
/* 251 */       break;
/*     */     case 17:
/* 254 */       str = "EC_KEY_CONFERENCE";
/* 255 */       break;
/*     */     case 18:
/* 258 */       str = "EC_LOCKOUT";
/* 259 */       break;
/*     */     case 19:
/* 262 */       str = "EC_MAINTENANCE";
/* 263 */       break;
/*     */     case 20:
/* 266 */       str = "EC_NETWORK_CONGESTION";
/* 267 */       break;
/*     */     case 21:
/* 270 */       str = "EC_NETWORK_NOT_OBTAINABLE";
/* 271 */       break;
/*     */     case 22:
/* 274 */       str = "EC_NEW_CALL";
/* 275 */       break;
/*     */     case 23:
/* 278 */       str = "EC_NO_AVAILABLE_AGENTS";
/* 279 */       break;
/*     */     case 24:
/* 282 */       str = "EC_OVERRIDE";
/* 283 */       break;
/*     */     case 25:
/* 286 */       str = "EC_PARK";
/* 287 */       break;
/*     */     case 26:
/* 290 */       str = "EC_OVERFLOW";
/* 291 */       break;
/*     */     case 27:
/* 294 */       str = "EC_RECALL";
/* 295 */       break;
/*     */     case 28:
/* 298 */       str = "EC_REDIRECTED";
/* 299 */       break;
/*     */     case 29:
/* 302 */       str = "EC_REORDER_TONE";
/* 303 */       break;
/*     */     case 30:
/* 306 */       str = "EC_RESOURCES_NOT_AVAILABLE";
/* 307 */       break;
/*     */     case 31:
/* 310 */       str = "EC_SILENT_MONITOR";
/* 311 */       break;
/*     */     case 52:
/* 314 */       str = "EC_SINGLE_STEP_TRANSFER";
/* 315 */       break;
/*     */     case 32:
/* 318 */       str = "EC_TRANSFER";
/* 319 */       break;
/*     */     case 33:
/* 322 */       str = "EC_TRUNKS_BUSY";
/* 323 */       break;
/*     */     case 34:
/* 326 */       str = "EC_VOICE_UNIT_INITIATOR";
/* 327 */       break;
/*     */     case 46:
/* 330 */       str = "EC_NETWORK_SIGNAL";
/* 331 */       break;
/*     */     case 60:
/* 334 */       str = "EC_ALERT_TIME_EXPIRED";
/* 335 */       break;
/*     */     case 65:
/* 338 */       str = "EC_DESTINATION_OUT_OF_ORDER";
/* 339 */       break;
/*     */     case 80:
/* 342 */       str = "EC_NOT_SUPPORTED_BEARER_SERVICE";
/* 343 */       break;
/*     */     case 81:
/* 346 */       str = "EC_UNASSIGNED_NUMBER";
/* 347 */       break;
/*     */     case 87:
/* 350 */       str = "EC_INCOMPATIBLE_BEARER_SERVICE";
/* 351 */       break;
/*     */     case 0:
/*     */     case 35:
/*     */     case 36:
/*     */     case 37:
/*     */     case 38:
/*     */     case 39:
/*     */     case 40:
/*     */     case 41:
/*     */     case 42:
/*     */     case 43:
/*     */     case 44:
/*     */     case 45:
/*     */     case 47:
/*     */     case 48:
/*     */     case 49:
/*     */     case 50:
/*     */     case 51:
/*     */     case 53:
/*     */     case 54:
/*     */     case 55:
/*     */     case 56:
/*     */     case 57:
/*     */     case 58:
/*     */     case 59:
/*     */     case 61:
/*     */     case 62:
/*     */     case 63:
/*     */     case 64:
/*     */     case 66:
/*     */     case 67:
/*     */     case 68:
/*     */     case 69:
/*     */     case 70:
/*     */     case 71:
/*     */     case 72:
/*     */     case 73:
/*     */     case 74:
/*     */     case 75:
/*     */     case 76:
/*     */     case 77:
/*     */     case 78:
/*     */     case 79:
/*     */     case 82:
/*     */     case 83:
/*     */     case 84:
/*     */     case 85:
/*     */     case 86:
/*     */     default:
/* 355 */       str = "?? " + value + " ??";
/*     */     }
/*     */ 
/* 359 */     return print(value, str, name, indent);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.EventCause
 * JD-Core Version:    0.5.4
 */