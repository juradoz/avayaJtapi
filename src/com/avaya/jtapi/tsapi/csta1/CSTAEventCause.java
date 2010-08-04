/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAEventCause extends ASNEnumerated
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
/*     */   public static Collection<String> print(short value, String name, String indent)
/*     */   {
/*     */     String str;
/* 181 */     switch (value)
/*     */     {
/*     */     case -1:
/* 184 */       str = "EC_NONE";
/* 185 */       break;
/*     */     case 1:
/* 188 */       str = "EC_ACTIVE_MONITOR";
/* 189 */       break;
/*     */     case 2:
/* 192 */       str = "EC_ALTERNATE";
/* 193 */       break;
/*     */     case 3:
/* 196 */       str = "EC_BUSY";
/* 197 */       break;
/*     */     case 4:
/* 200 */       str = "EC_CALL_BACK";
/* 201 */       break;
/*     */     case 5:
/* 204 */       str = "EC_CALL_CANCELLED";
/* 205 */       break;
/*     */     case 6:
/* 208 */       str = "EC_CALL_FORWARD_ALWAYS";
/* 209 */       break;
/*     */     case 7:
/* 212 */       str = "EC_CALL_FORWARD_BUSY";
/* 213 */       break;
/*     */     case 8:
/* 216 */       str = "EC_CALL_FORWARD_NO_ANSWER";
/* 217 */       break;
/*     */     case 9:
/* 220 */       str = "EC_CALL_FORWARD";
/* 221 */       break;
/*     */     case 10:
/* 224 */       str = "EC_CALL_NOT_ANSWERED";
/* 225 */       break;
/*     */     case 11:
/* 228 */       str = "EC_CALL_PICKUP";
/* 229 */       break;
/*     */     case 12:
/* 232 */       str = "EC_CAMP_ON";
/* 233 */       break;
/*     */     case 13:
/* 236 */       str = "EC_DEST_NOT_OBTAINABLE";
/* 237 */       break;
/*     */     case 14:
/* 240 */       str = "EC_DO_NOT_DISTURB";
/* 241 */       break;
/*     */     case 15:
/* 244 */       str = "EC_INCOMPATIBLE_DESTINATION";
/* 245 */       break;
/*     */     case 16:
/* 248 */       str = "EC_INVALID_ACCOUNT_CODE";
/* 249 */       break;
/*     */     case 17:
/* 252 */       str = "EC_KEY_CONFERENCE";
/* 253 */       break;
/*     */     case 18:
/* 256 */       str = "EC_LOCKOUT";
/* 257 */       break;
/*     */     case 19:
/* 260 */       str = "EC_MAINTENANCE";
/* 261 */       break;
/*     */     case 20:
/* 264 */       str = "EC_NETWORK_CONGESTION";
/* 265 */       break;
/*     */     case 21:
/* 268 */       str = "EC_NETWORK_NOT_OBTAINABLE";
/* 269 */       break;
/*     */     case 22:
/* 272 */       str = "EC_NEW_CALL";
/* 273 */       break;
/*     */     case 23:
/* 276 */       str = "EC_NO_AVAILABLE_AGENTS";
/* 277 */       break;
/*     */     case 24:
/* 280 */       str = "EC_OVERRIDE";
/* 281 */       break;
/*     */     case 25:
/* 284 */       str = "EC_PARK";
/* 285 */       break;
/*     */     case 26:
/* 288 */       str = "EC_OVERFLOW";
/* 289 */       break;
/*     */     case 27:
/* 292 */       str = "EC_RECALL";
/* 293 */       break;
/*     */     case 28:
/* 296 */       str = "EC_REDIRECTED";
/* 297 */       break;
/*     */     case 29:
/* 300 */       str = "EC_REORDER_TONE";
/* 301 */       break;
/*     */     case 30:
/* 304 */       str = "EC_RESOURCES_NOT_AVAILABLE";
/* 305 */       break;
/*     */     case 31:
/* 308 */       str = "EC_SILENT_MONITOR";
/* 309 */       break;
/*     */     case 52:
/* 312 */       str = "EC_SINGLE_STEP_TRANSFER";
/* 313 */       break;
/*     */     case 32:
/* 315 */       str = "EC_TRANSFER";
/* 316 */       break;
/*     */     case 33:
/* 319 */       str = "EC_TRUNKS_BUSY";
/* 320 */       break;
/*     */     case 34:
/* 323 */       str = "EC_VOICE_UNIT_INITIATOR";
/* 324 */       break;
/*     */     case 46:
/* 327 */       str = "EC_NETWORK_SIGNAL";
/* 328 */       break;
/*     */     case 60:
/* 331 */       str = "EC_ALERT_TIME_EXPIRED";
/* 332 */       break;
/*     */     case 65:
/* 335 */       str = "EC_DESTINATION_OUT_OF_ORDER";
/* 336 */       break;
/*     */     case 80:
/* 339 */       str = "EC_NOT_SUPPORTED_BEARER_SERVICE";
/* 340 */       break;
/*     */     case 81:
/* 343 */       str = "EC_UNASSIGNED_NUMBER";
/* 344 */       break;
/*     */     case 87:
/* 347 */       str = "EC_INCOMPATIBLE_BEARER_SERVICE";
/* 348 */       break;
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
/* 352 */       str = "?? " + value + " ??";
/*     */     }
/*     */ 
/* 356 */     return print(value, str, name, indent);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAEventCause
 * JD-Core Version:    0.5.4
 */