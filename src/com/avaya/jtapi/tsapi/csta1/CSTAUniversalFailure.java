/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAUniversalFailure extends ASNEnumerated
/*     */ {
/*     */   public static final short GENERIC_UNSPECIFIED = 0;
/*     */   public static final short GENERIC_OPERATION = 1;
/*     */   public static final short REQUEST_INCOMPATIBLE_WITH_OBJECT = 2;
/*     */   public static final short VALUE_OUT_OF_RANGE = 3;
/*     */   public static final short OBJECT_NOT_KNOWN = 4;
/*     */   public static final short INVALID_CALLING_DEVICE = 5;
/*     */   public static final short INVALID_CALLED_DEVICE = 6;
/*     */   public static final short INVALID_FORWARDING_DESTINATION = 7;
/*     */   public static final short PRIVILEGE_VIOLATION_ON_SPECIFIED_DEVICE = 8;
/*     */   public static final short PRIVILEGE_VIOLATION_ON_CALLED_DEVICE = 9;
/*     */   public static final short PRIVILEGE_VIOLATION_ON_CALLING_DEVICE = 10;
/*     */   public static final short INVALID_CSTA_CALL_IDENTIFIER = 11;
/*     */   public static final short INVALID_CSTA_DEVICE_IDENTIFIER = 12;
/*     */   public static final short INVALID_CSTA_CONNECTION_IDENTIFIER = 13;
/*     */   public static final short INVALID_DESTINATION = 14;
/*     */   public static final short INVALID_FEATURE = 15;
/*     */   public static final short INVALID_ALLOCATION_STATE = 16;
/*     */   public static final short INVALID_CROSS_REF_ID = 17;
/*     */   public static final short INVALID_OBJECT_TYPE = 18;
/*     */   public static final short SECURITY_VIOLATION = 19;
/*     */   public static final short GENERIC_STATE_INCOMPATIBILITY = 21;
/*     */   public static final short INVALID_OBJECT_STATE = 22;
/*     */   public static final short INVALID_CONNECTION_ID_FOR_ACTIVE_CALL = 23;
/*     */   public static final short NO_ACTIVE_CALL = 24;
/*     */   public static final short NO_HELD_CALL = 25;
/*     */   public static final short NO_CALL_TO_CLEAR = 26;
/*     */   public static final short NO_CONNECTION_TO_CLEAR = 27;
/*     */   public static final short NO_CALL_TO_ANSWER = 28;
/*     */   public static final short NO_CALL_TO_COMPLETE = 29;
/*     */   public static final short GENERIC_SYSTEM_RESOURCE_AVAILABILITY = 31;
/*     */   public static final short SERVICE_BUSY = 32;
/*     */   public static final short RESOURCE_BUSY = 33;
/*     */   public static final short RESOURCE_OUT_OF_SERVICE = 34;
/*     */   public static final short NETWORK_BUSY = 35;
/*     */   public static final short NETWORK_OUT_OF_SERVICE = 36;
/*     */   public static final short OVERALL_MONITOR_LIMIT_EXCEEDED = 37;
/*     */   public static final short CONFERENCE_MEMBER_LIMIT_EXCEEDED = 38;
/*     */   public static final short GENERIC_SUBSCRIBED_RESOURCE_AVAILABILITY = 41;
/*     */   public static final short OBJECT_MONITOR_LIMIT_EXCEEDED = 42;
/*     */   public static final short EXTERNAL_TRUNK_LIMIT_EXCEEDED = 43;
/*     */   public static final short OUTSTANDING_REQUEST_LIMIT_EXCEEDED = 44;
/*     */   public static final short GENERIC_PERFORMANCE_MANAGEMENT = 51;
/*     */   public static final short PERFORMANCE_LIMIT_EXCEEDED = 52;
/*     */   public static final short UNSPECIFIED_SECURITY_ERROR = 60;
/*     */   public static final short SEQUENCE_NUMBER_VIOLATED = 61;
/*     */   public static final short TIME_STAMP_VIOLATED = 62;
/*     */   public static final short PAC_VIOLATED = 63;
/*     */   public static final short SEAL_VIOLATED = 64;
/*     */   public static final short GENERIC_UNSPECIFIED_REJECTION = 70;
/*     */   public static final short GENERIC_OPERATION_REJECTION = 71;
/*     */   public static final short DUPLICATE_INVOCATION_REJECTION = 72;
/*     */   public static final short UNRECOGNIZED_OPERATION_REJECTION = 73;
/*     */   public static final short MISTYPED_ARGUMENT_REJECTION = 74;
/*     */   public static final short RESOURCE_LIMITATION_REJECTION = 75;
/*     */   public static final short ACS_HANDLE_TERMINATION_REJECTION = 76;
/*     */   public static final short SERVICE_TERMINATION_REJECTION = 77;
/*     */   public static final short REQUEST_TIMEOUT_REJECTION = 78;
/*     */   public static final short REQUESTS_ON_DEVICE_EXCEEDED_REJECTION = 79;
/*     */   public static final short UNRECOGNIZED_APDU_REJECTION = 80;
/*     */   public static final short MISTYPED_APDU_REJECTION = 81;
/*     */   public static final short BADLY_STRUCTURED_APDU_REJECTION = 82;
/*     */   public static final short INITIATOR_RELEASING_REJECTION = 83;
/*     */   public static final short UNRECOGNIZED_LINKEDID_REJECTION = 84;
/*     */   public static final short LINKED_RESPONSE_UNEXPECTED_REJECTION = 85;
/*     */   public static final short UNEXPECTED_CHILD_OPERATION_REJECTION = 86;
/*     */   public static final short MISTYPED_RESULT_REJECTION = 87;
/*     */   public static final short UNRECOGNIZED_ERROR_REJECTION = 88;
/*     */   public static final short UNEXPECTED_ERROR_REJECTION = 89;
/*     */   public static final short MISTYPED_PARAMETER_REJECTION = 90;
/*     */   public static final short NON_STANDARD = 100;
/*     */ 
/*     */   static Collection<String> print(short value, String name, String indent)
/*     */   {
/*     */     String str;
/*  84 */     switch (value)
/*     */     {
/*     */     case 0:
/*  87 */       str = "GENERIC_UNSPECIFIED";
/*  88 */       break;
/*     */     case 1:
/*  91 */       str = "GENERIC_OPERATION";
/*  92 */       break;
/*     */     case 2:
/*  95 */       str = "REQUEST_INCOMPATIBLE_WITH_OBJECT";
/*  96 */       break;
/*     */     case 3:
/*  99 */       str = "VALUE_OUT_OF_RANGE";
/* 100 */       break;
/*     */     case 4:
/* 103 */       str = "OBJECT_NOT_KNOWN";
/* 104 */       break;
/*     */     case 5:
/* 107 */       str = "INVALID_CALLING_DEVICE";
/* 108 */       break;
/*     */     case 6:
/* 111 */       str = "INVALID_CALLED_DEVICE";
/* 112 */       break;
/*     */     case 7:
/* 115 */       str = "INVALID_FORWARDING_DESTINATION";
/* 116 */       break;
/*     */     case 8:
/* 119 */       str = "PRIVILEGE_VIOLATION_ON_SPECIFIED_DEVICE";
/* 120 */       break;
/*     */     case 9:
/* 123 */       str = "PRIVILEGE_VIOLATION_ON_CALLED_DEVICE";
/* 124 */       break;
/*     */     case 10:
/* 127 */       str = "PRIVILEGE_VIOLATION_ON_CALLING_DEVICE";
/* 128 */       break;
/*     */     case 11:
/* 131 */       str = "INVALID_CSTA_CALL_IDENTIFIER";
/* 132 */       break;
/*     */     case 12:
/* 135 */       str = "INVALID_CSTA_DEVICE_IDENTIFIER";
/* 136 */       break;
/*     */     case 13:
/* 139 */       str = "INVALID_CSTA_CONNECTION_IDENTIFIER";
/* 140 */       break;
/*     */     case 14:
/* 143 */       str = "INVALID_DESTINATION";
/* 144 */       break;
/*     */     case 15:
/* 147 */       str = "INVALID_FEATURE";
/* 148 */       break;
/*     */     case 16:
/* 151 */       str = "INVALID_ALLOCATION_STATE";
/* 152 */       break;
/*     */     case 17:
/* 155 */       str = "INVALID_CROSS_REF_ID";
/* 156 */       break;
/*     */     case 18:
/* 159 */       str = "INVALID_OBJECT_TYPE";
/* 160 */       break;
/*     */     case 19:
/* 163 */       str = "SECURITY_VIOLATION";
/* 164 */       break;
/*     */     case 21:
/* 167 */       str = "GENERIC_STATE_INCOMPATIBILITY";
/* 168 */       break;
/*     */     case 22:
/* 171 */       str = "INVALID_OBJECT_STATE";
/* 172 */       break;
/*     */     case 23:
/* 175 */       str = "INVALID_CONNECTION_ID_FOR_ACTIVE_CALL";
/* 176 */       break;
/*     */     case 24:
/* 179 */       str = "NO_ACTIVE_CALL";
/* 180 */       break;
/*     */     case 25:
/* 183 */       str = "NO_HELD_CALL";
/* 184 */       break;
/*     */     case 26:
/* 187 */       str = "NO_CALL_TO_CLEAR";
/* 188 */       break;
/*     */     case 27:
/* 191 */       str = "NO_CONNECTION_TO_CLEAR";
/* 192 */       break;
/*     */     case 28:
/* 195 */       str = "NO_CALL_TO_ANSWER";
/* 196 */       break;
/*     */     case 29:
/* 199 */       str = "NO_CALL_TO_COMPLETE";
/* 200 */       break;
/*     */     case 31:
/* 203 */       str = "GENERIC_SYSTEM_RESOURCE_AVAILABILITY";
/* 204 */       break;
/*     */     case 32:
/* 207 */       str = "SERVICE_BUSY";
/* 208 */       break;
/*     */     case 33:
/* 211 */       str = "RESOURCE_BUSY";
/* 212 */       break;
/*     */     case 34:
/* 215 */       str = "RESOURCE_OUT_OF_SERVICE";
/* 216 */       break;
/*     */     case 35:
/* 219 */       str = "NETWORK_BUSY";
/* 220 */       break;
/*     */     case 36:
/* 223 */       str = "NETWORK_OUT_OF_SERVICE";
/* 224 */       break;
/*     */     case 37:
/* 227 */       str = "OVERALL_MONITOR_LIMIT_EXCEEDED";
/* 228 */       break;
/*     */     case 38:
/* 231 */       str = "CONFERENCE_MEMBER_LIMIT_EXCEEDED";
/* 232 */       break;
/*     */     case 41:
/* 235 */       str = "GENERIC_SUBSCRIBED_RESOURCE_AVAILABILITY";
/* 236 */       break;
/*     */     case 42:
/* 239 */       str = "OBJECT_MONITOR_LIMIT_EXCEEDED";
/* 240 */       break;
/*     */     case 43:
/* 243 */       str = "EXTERNAL_TRUNK_LIMIT_EXCEEDED";
/* 244 */       break;
/*     */     case 44:
/* 247 */       str = "OUTSTANDING_REQUEST_LIMIT_EXCEEDED";
/* 248 */       break;
/*     */     case 51:
/* 251 */       str = "GENERIC_PERFORMANCE_MANAGEMENT";
/* 252 */       break;
/*     */     case 52:
/* 255 */       str = "PERFORMANCE_LIMIT_EXCEEDED";
/* 256 */       break;
/*     */     case 60:
/* 259 */       str = "UNSPECIFIED_SECURITY_ERROR";
/* 260 */       break;
/*     */     case 61:
/* 263 */       str = "SEQUENCE_NUMBER_VIOLATED";
/* 264 */       break;
/*     */     case 62:
/* 267 */       str = "TIME_STAMP_VIOLATED";
/* 268 */       break;
/*     */     case 63:
/* 271 */       str = "PAC_VIOLATED";
/* 272 */       break;
/*     */     case 64:
/* 275 */       str = "SEAL_VIOLATED";
/* 276 */       break;
/*     */     case 70:
/* 279 */       str = "GENERIC_UNSPECIFIED_REJECTION";
/* 280 */       break;
/*     */     case 71:
/* 283 */       str = "GENERIC_OPERATION_REJECTION";
/* 284 */       break;
/*     */     case 72:
/* 287 */       str = "DUPLICATE_INVOCATION_REJECTION";
/* 288 */       break;
/*     */     case 73:
/* 291 */       str = "UNRECOGNIZED_OPERATION_REJECTION";
/* 292 */       break;
/*     */     case 74:
/* 295 */       str = "MISTYPED_ARGUMENT_REJECTION";
/* 296 */       break;
/*     */     case 75:
/* 299 */       str = "RESOURCE_LIMITATION_REJECTION";
/* 300 */       break;
/*     */     case 76:
/* 303 */       str = "ACS_HANDLE_TERMINATION_REJECTION";
/* 304 */       break;
/*     */     case 77:
/* 307 */       str = "SERVICE_TERMINATION_REJECTION";
/* 308 */       break;
/*     */     case 78:
/* 311 */       str = "REQUEST_TIMEOUT_REJECTION";
/* 312 */       break;
/*     */     case 79:
/* 315 */       str = "REQUESTS_ON_DEVICE_EXCEEDED_REJECTION";
/* 316 */       break;
/*     */     case 80:
/* 319 */       str = "UNRECOGNIZED_APDU_REJECTION";
/* 320 */       break;
/*     */     case 81:
/* 323 */       str = "MISTYPED_APDU_REJECTION";
/* 324 */       break;
/*     */     case 82:
/* 327 */       str = "BADLY_STRUCTURED_APDU_REJECTION";
/* 328 */       break;
/*     */     case 83:
/* 331 */       str = "INITIATOR_RELEASING_REJECTION";
/* 332 */       break;
/*     */     case 84:
/* 335 */       str = "UNRECOGNIZED_LINKEDID_REJECTION";
/* 336 */       break;
/*     */     case 85:
/* 339 */       str = "LINKED_RESPONSE_UNEXPECTED_REJECTION";
/* 340 */       break;
/*     */     case 86:
/* 343 */       str = "UNEXPECTED_CHILD_OPERATION_REJECTION";
/* 344 */       break;
/*     */     case 87:
/* 347 */       str = "MISTYPED_RESULT_REJECTION";
/* 348 */       break;
/*     */     case 88:
/* 351 */       str = "UNRECOGNIZED_ERROR_REJECTION";
/* 352 */       break;
/*     */     case 89:
/* 355 */       str = "UNEXPECTED_ERROR_REJECTION";
/* 356 */       break;
/*     */     case 90:
/* 359 */       str = "MISTYPED_PARAMETER_REJECTION";
/* 360 */       break;
/*     */     case 100:
/* 363 */       str = "NON_STANDARD";
/* 364 */       break;
/*     */     case 20:
/*     */     case 30:
/*     */     case 39:
/*     */     case 40:
/*     */     case 45:
/*     */     case 46:
/*     */     case 47:
/*     */     case 48:
/*     */     case 49:
/*     */     case 50:
/*     */     case 53:
/*     */     case 54:
/*     */     case 55:
/*     */     case 56:
/*     */     case 57:
/*     */     case 58:
/*     */     case 59:
/*     */     case 65:
/*     */     case 66:
/*     */     case 67:
/*     */     case 68:
/*     */     case 69:
/*     */     case 91:
/*     */     case 92:
/*     */     case 93:
/*     */     case 94:
/*     */     case 95:
/*     */     case 96:
/*     */     case 97:
/*     */     case 98:
/*     */     case 99:
/*     */     default:
/* 367 */       str = "?? " + value + " ??";
/*     */     }
/*     */ 
/* 371 */     return print(value, str, name, indent);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAUniversalFailure
 * JD-Core Version:    0.5.4
 */