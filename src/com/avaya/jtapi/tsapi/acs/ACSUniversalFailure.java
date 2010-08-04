/*     */ package com.avaya.jtapi.tsapi.acs;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class ACSUniversalFailure extends ASNEnumerated
/*     */ {
/*     */   public static final short TSERVER_STREAM_FAILED = 0;
/*     */   public static final short TSERVER_NO_THREAD = 1;
/*     */   public static final short TSERVER_BAD_DRIVER_ID = 2;
/*     */   public static final short TSERVER_DEAD_DRIVER = 3;
/*     */   public static final short TSERVER_MESSAGE_HIGH_WATER_MARK = 4;
/*     */   public static final short TSERVER_FREE_BUFFER_FAILED = 5;
/*     */   public static final short TSERVER_SEND_TO_DRIVER = 6;
/*     */   public static final short TSERVER_RECEIVE_FROM_DRIVER = 7;
/*     */   public static final short TSERVER_REGISTRATION_FAILED = 8;
/*     */   public static final short TSERVER_SPX_FAILED = 9;
/*     */   public static final short TSERVER_TRACE = 10;
/*     */   public static final short TSERVER_NO_MEMORY = 11;
/*     */   public static final short TSERVER_ENCODE_FAILED = 12;
/*     */   public static final short TSERVER_DECODE_FAILED = 13;
/*     */   public static final short TSERVER_BAD_CONNECTION = 14;
/*     */   public static final short TSERVER_BAD_PDU = 15;
/*     */   public static final short TSERVER_NO_VERSION = 16;
/*     */   public static final short TSERVER_ECB_MAX_EXCEEDED = 17;
/*     */   public static final short TSERVER_NO_ECBS = 18;
/*     */   public static final short TSERVER_NO_SDB = 19;
/*     */   public static final short TSERVER_NO_SDB_CHECK_NEEDED = 20;
/*     */   public static final short TSERVER_SDB_CHECK_NEEDED = 21;
/*     */   public static final short TSERVER_BAD_SDB_LEVEL = 22;
/*     */   public static final short TSERVER_BAD_SERVERID = 23;
/*     */   public static final short TSERVER_BAD_STREAM_TYPE = 24;
/*     */   public static final short TSERVER_BAD_PASSWORD_OR_LOGIN = 25;
/*     */   public static final short TSERVER_NO_USER_RECORD = 26;
/*     */   public static final short TSERVER_NO_DEVICE_RECORD = 27;
/*     */   public static final short TSERVER_DEVICE_NOT_ON_LIST = 28;
/*     */   public static final short TSERVER_USERS_RESTRICTED_HOME = 30;
/*     */   public static final short TSERVER_NO_AWAYPERMISSION = 31;
/*     */   public static final short TSERVER_NO_HOMEPERMISSION = 32;
/*     */   public static final short TSERVER_NO_AWAY_WORKTOP = 33;
/*     */   public static final short TSERVER_BAD_DEVICE_RECORD = 34;
/*     */   public static final short TSERVER_DEVICE_NOT_SUPPORTED = 35;
/*     */   public static final short TSERVER_INSUFFICIENT_PERMISSION = 36;
/*     */   public static final short TSERVER_NO_RESOURCE_TAG = 37;
/*     */   public static final short TSERVER_INVALID_MESSAGE = 38;
/*     */   public static final short TSERVER_EXCEPTION_LIST = 39;
/*     */   public static final short TSERVER_NOT_ON_OAM_LIST = 40;
/*     */   public static final short TSERVER_PBX_ID_NOT_IN_SDB = 41;
/*     */   public static final short TSERVER_USER_LICENSES_EXCEEDED = 42;
/*     */   public static final short TSERVER_OAM_DROP_CONNECTION = 43;
/*     */   public static final short TSERVER_NO_VERSION_RECORD = 44;
/*     */   public static final short TSERVER_OLD_VERSION_RECORD = 45;
/*     */   public static final short TSERVER_BAD_PACKET = 46;
/*     */   public static final short TSERVER_OPEN_FAILED = 47;
/*     */   public static final short TSERVER_OAM_IN_USE = 48;
/*     */   public static final short TSERVER_DEVICE_NOT_ON_HOME_LIST = 49;
/*     */   public static final short TSERVER_DEVICE_NOT_ON_CALL_CONTROL_LIST = 50;
/*     */   public static final short TSERVER_DEVICE_NOT_ON_AWAY_LIST = 51;
/*     */   public static final short TSERVER_DEVICE_NOT_ON_ROUTE_LIST = 52;
/*     */   public static final short TSERVER_DEVICE_NOT_ON_MONITOR_DEVICE_LIST = 53;
/*     */   public static final short TSERVER_DEVICE_NOT_ON_MONITOR_CALL_DEVICE_LIST = 54;
/*     */   public static final short TSERVER_NO_CALL_CALL_MONITOR_PERMISSION = 55;
/*     */   public static final short TSERVER_HOME_DEVICE_LIST_EMPTY = 56;
/*     */   public static final short TSERVER_CALL_CONTROL_LIST_EMPTY = 57;
/*     */   public static final short TSERVER_AWAY_LIST_EMPTY = 58;
/*     */   public static final short TSERVER_ROUTE_LIST_EMPTY = 59;
/*     */   public static final short TSERVER_MONITOR_DEVICE_LIST_EMPTY = 60;
/*     */   public static final short TSERVER_MONITOR_CALL_DEVICE_LIST_EMPTY = 61;
/*     */   public static final short TSERVER_USER_AT_HOME_WORKTOP = 62;
/*     */   public static final short TSERVER_DEVICE_LIST_EMPTY = 63;
/*     */   public static final short TSERVER_BAD_GET_DEVICE_LEVEL = 64;
/*     */   public static final short TSERVER_DRIVER_UNREGISTERED = 65;
/*     */   public static final short TSERVER_NO_ACS_STREAM = 66;
/*     */   public static final short TSERVER_DROP_OAM = 67;
/*     */   public static final short TSERVER_ECB_TIMEOUT = 68;
/*     */   public static final short TSERVER_BAD_ECB = 69;
/*     */   public static final short TSERVER_ADVERTISE_FAILED = 70;
/*     */   public static final short TSERVER_NETWARE_FAILURE = 71;
/*     */   public static final short TSERVER_TDI_QUEUE_FAULT = 72;
/*     */   public static final short TSERVER_DRIVER_CONGESTION = 73;
/*     */   public static final short TSERVER_NO_TDI_BUFFERS = 74;
/*     */   public static final short TSERVER_OLD_INVOKEID = 75;
/*     */   public static final short TSERVER_HWMARK_TO_LARGE = 76;
/*     */   public static final short TSERVER_SET_ECB_TO_LOW = 77;
/*     */   public static final short TSERVER_NO_RECORD_IN_FILE = 78;
/*     */   public static final short TSERVER_ECB_OVERDUE = 79;
/*     */   public static final short TSERVER_BAD_PW_ENCRYPTION = 80;
/*     */   public static final short TSERVER_BAD_TSERV_PROTOCOL = 81;
/*     */   public static final short TSERVER_BAD_DRIVER_PROTOCOL = 82;
/*     */   public static final short TSERVER_BAD_TRANSPORT_TYPE = 83;
/*     */   public static final short TSERVER_PDU_VERSION_MISMATCH = 84;
/*     */   public static final short TSERVER_VERSION_MISMATCH = 85;
/*     */   public static final short TSERVER_LICENSE_MISMATCH = 86;
/*     */   public static final short TSERVER_BAD_ATTRIBUTE_LIST = 87;
/*     */   public static final short TSERVER_BAD_TLIST_TYPE = 88;
/*     */   public static final short TSERVER_BAD_PROTOCOL_FORMAT = 89;
/*     */   public static final short TSERVER_OLD_TSLIB = 90;
/*     */   public static final short TSERVER_BAD_LICENSE_FILE = 91;
/*     */   public static final short TSERVER_NO_PATCHES = 92;
/*     */   public static final short TSERVER_SYSTEM_ERROR = 93;
/*     */   public static final short TSERVER_OAM_LIST_EMPTY = 94;
/*     */   public static final short TSERVER_TCP_FAILED = 95;
/*     */   public static final short TSERVER_SPX_DISABLED = 96;
/*     */   public static final short TSERVER_TCP_DISABLED = 97;
/*     */   public static final short TSERVER_REQUIRED_MODULES_NOT_LOADED = 98;
/*     */   public static final short TSERVER_TRANSPORT_IN_USE_BY_OAM = 99;
/*     */   public static final short TSERVER_NO_NDS_OAM_PERMISSION = 100;
/*     */   public static final short TSERVER_OPEN_SDB_LOG_FAILED = 101;
/*     */   public static final short TSERVER_INVALID_LOG_SIZE = 102;
/*     */   public static final short TSERVER_WRITE_SDB_LOG_FAILED = 103;
/*     */   public static final short TSERVER_NT_FAILURE = 104;
/*     */   public static final short TSERVER_LOAD_LIB_FAILED = 105;
/*     */   public static final short TSERVER_INVALID_DRIVER = 106;
/*     */   public static final short TSERVER_REGISTRY_ERROR = 107;
/*     */   public static final short TSERVER_DUPLICATE_ENTRY = 108;
/*     */   public static final short TSERVER_DRIVER_LOADED = 109;
/*     */   public static final short TSERVER_DRIVER_NOT_LOADED = 110;
/*     */   public static final short TSERVER_NO_LOGON_PERMISSION = 111;
/*     */   public static final short TSERVER_ACCOUNT_DISABLED = 112;
/*     */   public static final short TSERVER_NO_NETLOGON = 113;
/*     */   public static final short TSERVER_ACCT_RESTRICTED = 114;
/*     */   public static final short TSERVER_INVALID_LOGON_TIME = 115;
/*     */   public static final short TSERVER_INVALID_WORKSTATION = 116;
/*     */   public static final short TSERVER_ACCT_LOCKED_OUT = 117;
/*     */   public static final short TSERVER_PASSWORD_EXPIRED = 118;
/*     */   public static final short TSERVER_INVALID_HEARTBEAT_INTERVAL = 119;
/*     */   public static final short TSERVER_UNAUTHORIZED_CONNECTION = 120;
/*     */   public static final short TSERVER_INVALID_NONCE = 121;
/*     */   public static final short TSERVER_INVALID_CERTIFICATE = 122;
/*     */   public static final short TSERVER_INVALID_SIGNATURE = 123;
/*     */   public static final short TSERVER_UNKNOWN_APPLICATION = 124;
/*     */   public static final short TSERVER_CERTIFICATE_EXPIRED = 125;
/*     */   public static final short TSERVER_INVALID_PRIV_MESSAGE = 126;
/*     */   public static final short TSERVER_NOT_FIRST_MESSAGE = 127;
/*     */   public static final short TSERVER_NOT_SECOND_MESSAGE = 128;
/*     */   public static final short DRIVER_DUPLICATE_ACSHANDLE = 1000;
/*     */   public static final short DRIVER_INVALID_ACS_REQUEST = 1001;
/*     */   public static final short DRIVER_ACS_HANDLE_REJECTION = 1002;
/*     */   public static final short DRIVER_INVALID_CLASS_REJECTION = 1003;
/*     */   public static final short DRIVER_GENERIC_REJECTION = 1004;
/*     */   public static final short DRIVER_RESOURCE_LIMITATION = 1005;
/*     */   public static final short DRIVER_ACSHANDLE_TERMINATION = 1006;
/*     */   public static final short DRIVER_LINK_UNAVAILABLE = 1007;
/*     */   public static final short DRIVER_OAM_IN_USE = 1008;
/*     */ 
/*     */   static Collection<String> print(short value, String name, String indent)
/*     */   {
/* 150 */     Collection lines = new ArrayList();
/*     */     String str;
/* 153 */     switch (value)
/*     */     {
/*     */     case 0:
/* 156 */       str = "TSERVER_STREAM_FAILED";
/* 157 */       break;
/*     */     case 1:
/* 160 */       str = "TSERVER_NO_THREAD";
/* 161 */       break;
/*     */     case 2:
/* 164 */       str = "TSERVER_BAD_DRIVER_ID";
/* 165 */       break;
/*     */     case 3:
/* 168 */       str = "TSERVER_DEAD_DRIVER";
/* 169 */       break;
/*     */     case 4:
/* 172 */       str = "TSERVER_MESSAGE_HIGH_WATER_MARK";
/* 173 */       break;
/*     */     case 5:
/* 176 */       str = "TSERVER_FREE_BUFFER_FAILED";
/* 177 */       break;
/*     */     case 6:
/* 180 */       str = "TSERVER_SEND_TO_DRIVER";
/* 181 */       break;
/*     */     case 7:
/* 184 */       str = "TSERVER_RECEIVE_FROM_DRIVER";
/* 185 */       break;
/*     */     case 8:
/* 188 */       str = "TSERVER_REGISTRATION_FAILED";
/* 189 */       break;
/*     */     case 9:
/* 192 */       str = "TSERVER_SPX_FAILED";
/* 193 */       break;
/*     */     case 10:
/* 196 */       str = "TSERVER_TRACE";
/* 197 */       break;
/*     */     case 11:
/* 200 */       str = "TSERVER_NO_MEMORY";
/* 201 */       break;
/*     */     case 12:
/* 204 */       str = "TSERVER_ENCODE_FAILED";
/* 205 */       break;
/*     */     case 13:
/* 208 */       str = "TSERVER_DECODE_FAILED";
/* 209 */       break;
/*     */     case 14:
/* 212 */       str = "TSERVER_BAD_CONNECTION";
/* 213 */       break;
/*     */     case 15:
/* 216 */       str = "TSERVER_BAD_PDU";
/* 217 */       break;
/*     */     case 16:
/* 220 */       str = "TSERVER_NO_VERSION";
/* 221 */       break;
/*     */     case 17:
/* 224 */       str = "TSERVER_ECB_MAX_EXCEEDED";
/* 225 */       break;
/*     */     case 18:
/* 228 */       str = "TSERVER_NO_ECBS";
/* 229 */       break;
/*     */     case 19:
/* 232 */       str = "TSERVER_NO_SDB";
/* 233 */       break;
/*     */     case 20:
/* 236 */       str = "TSERVER_NO_SDB_CHECK_NEEDED";
/* 237 */       break;
/*     */     case 21:
/* 240 */       str = "TSERVER_SDB_CHECK_NEEDED";
/* 241 */       break;
/*     */     case 22:
/* 244 */       str = "TSERVER_BAD_SDB_LEVEL";
/* 245 */       break;
/*     */     case 23:
/* 248 */       str = "TSERVER_BAD_SERVERID";
/* 249 */       break;
/*     */     case 24:
/* 252 */       str = "TSERVER_BAD_STREAM_TYPE";
/* 253 */       break;
/*     */     case 25:
/* 256 */       str = "TSERVER_BAD_PASSWORD_OR_LOGIN";
/* 257 */       break;
/*     */     case 26:
/* 260 */       str = "TSERVER_NO_USER_RECORD";
/* 261 */       break;
/*     */     case 27:
/* 264 */       str = "TSERVER_NO_DEVICE_RECORD";
/* 265 */       break;
/*     */     case 28:
/* 268 */       str = "TSERVER_DEVICE_NOT_ON_LIST";
/* 269 */       break;
/*     */     case 30:
/* 272 */       str = "TSERVER_USERS_RESTRICTED_HOME";
/* 273 */       break;
/*     */     case 31:
/* 276 */       str = "TSERVER_NO_AWAYPERMISSION";
/* 277 */       break;
/*     */     case 32:
/* 280 */       str = "TSERVER_NO_HOMEPERMISSION";
/* 281 */       break;
/*     */     case 33:
/* 284 */       str = "TSERVER_NO_AWAY_WORKTOP";
/* 285 */       break;
/*     */     case 34:
/* 288 */       str = "TSERVER_BAD_DEVICE_RECORD";
/* 289 */       break;
/*     */     case 35:
/* 292 */       str = "TSERVER_DEVICE_NOT_SUPPORTED";
/* 293 */       break;
/*     */     case 36:
/* 296 */       str = "TSERVER_INSUFFICIENT_PERMISSION";
/* 297 */       break;
/*     */     case 37:
/* 300 */       str = "TSERVER_NO_RESOURCE_TAG";
/* 301 */       break;
/*     */     case 38:
/* 304 */       str = "TSERVER_INVALID_MESSAGE";
/* 305 */       break;
/*     */     case 39:
/* 308 */       str = "TSERVER_EXCEPTION_LIST";
/* 309 */       break;
/*     */     case 40:
/* 312 */       str = "TSERVER_NOT_ON_OAM_LIST";
/* 313 */       break;
/*     */     case 41:
/* 316 */       str = "TSERVER_PBX_ID_NOT_IN_SDB";
/* 317 */       break;
/*     */     case 42:
/* 320 */       str = "TSERVER_USER_LICENSES_EXCEEDED";
/* 321 */       break;
/*     */     case 43:
/* 324 */       str = "TSERVER_OAM_DROP_CONNECTION";
/* 325 */       break;
/*     */     case 44:
/* 328 */       str = "TSERVER_NO_VERSION_RECORD";
/* 329 */       break;
/*     */     case 45:
/* 332 */       str = "TSERVER_OLD_VERSION_RECORD";
/* 333 */       break;
/*     */     case 46:
/* 336 */       str = "TSERVER_BAD_PACKET";
/* 337 */       break;
/*     */     case 47:
/* 340 */       str = "TSERVER_OPEN_FAILED";
/* 341 */       break;
/*     */     case 48:
/* 344 */       str = "TSERVER_OAM_IN_USE";
/* 345 */       break;
/*     */     case 49:
/* 348 */       str = "TSERVER_DEVICE_NOT_ON_HOME_LIST";
/* 349 */       break;
/*     */     case 50:
/* 352 */       str = "TSERVER_DEVICE_NOT_ON_CALL_CONTROL_LIST";
/* 353 */       break;
/*     */     case 51:
/* 356 */       str = "TSERVER_DEVICE_NOT_ON_AWAY_LIST";
/* 357 */       break;
/*     */     case 52:
/* 360 */       str = "TSERVER_DEVICE_NOT_ON_ROUTE_LIST";
/* 361 */       break;
/*     */     case 53:
/* 364 */       str = "TSERVER_DEVICE_NOT_ON_MONITOR_DEVICE_LIST";
/* 365 */       break;
/*     */     case 54:
/* 368 */       str = "TSERVER_DEVICE_NOT_ON_MONITOR_CALL_DEVICE_LIST";
/* 369 */       break;
/*     */     case 55:
/* 372 */       str = "TSERVER_NO_CALL_CALL_MONITOR_PERMISSION";
/* 373 */       break;
/*     */     case 56:
/* 376 */       str = "TSERVER_HOME_DEVICE_LIST_EMPTY";
/* 377 */       break;
/*     */     case 57:
/* 380 */       str = "TSERVER_CALL_CONTROL_LIST_EMPTY";
/* 381 */       break;
/*     */     case 58:
/* 384 */       str = "TSERVER_AWAY_LIST_EMPTY";
/* 385 */       break;
/*     */     case 59:
/* 388 */       str = "TSERVER_ROUTE_LIST_EMPTY";
/* 389 */       break;
/*     */     case 60:
/* 392 */       str = "TSERVER_MONITOR_DEVICE_LIST_EMPTY";
/* 393 */       break;
/*     */     case 61:
/* 396 */       str = "TSERVER_MONITOR_CALL_DEVICE_LIST_EMPTY";
/* 397 */       break;
/*     */     case 62:
/* 400 */       str = "TSERVER_USER_AT_HOME_WORKTOP";
/* 401 */       break;
/*     */     case 63:
/* 404 */       str = "TSERVER_DEVICE_LIST_EMPTY";
/* 405 */       break;
/*     */     case 64:
/* 408 */       str = "TSERVER_BAD_GET_DEVICE_LEVEL";
/* 409 */       break;
/*     */     case 65:
/* 412 */       str = "TSERVER_DRIVER_UNREGISTERED";
/* 413 */       break;
/*     */     case 66:
/* 416 */       str = "TSERVER_NO_ACS_STREAM";
/* 417 */       break;
/*     */     case 67:
/* 420 */       str = "TSERVER_DROP_OAM";
/* 421 */       break;
/*     */     case 68:
/* 424 */       str = "TSERVER_ECB_TIMEOUT";
/* 425 */       break;
/*     */     case 69:
/* 428 */       str = "TSERVER_BAD_ECB";
/* 429 */       break;
/*     */     case 70:
/* 432 */       str = "TSERVER_ADVERTISE_FAILED";
/* 433 */       break;
/*     */     case 71:
/* 436 */       str = "TSERVER_NETWARE_FAILURE";
/* 437 */       break;
/*     */     case 72:
/* 440 */       str = "TSERVER_TDI_QUEUE_FAULT";
/* 441 */       break;
/*     */     case 73:
/* 444 */       str = "TSERVER_DRIVER_CONGESTION";
/* 445 */       break;
/*     */     case 74:
/* 448 */       str = "TSERVER_NO_TDI_BUFFERS";
/* 449 */       break;
/*     */     case 75:
/* 452 */       str = "TSERVER_OLD_INVOKEID";
/* 453 */       break;
/*     */     case 76:
/* 456 */       str = "TSERVER_HWMARK_TO_LARGE";
/* 457 */       break;
/*     */     case 77:
/* 460 */       str = "TSERVER_SET_ECB_TO_LOW";
/* 461 */       break;
/*     */     case 78:
/* 464 */       str = "TSERVER_NO_RECORD_IN_FILE";
/* 465 */       break;
/*     */     case 79:
/* 468 */       str = "TSERVER_ECB_OVERDUE";
/* 469 */       break;
/*     */     case 80:
/* 472 */       str = "TSERVER_BAD_PW_ENCRYPTION";
/* 473 */       break;
/*     */     case 81:
/* 476 */       str = "TSERVER_BAD_TSERV_PROTOCOL";
/* 477 */       break;
/*     */     case 82:
/* 480 */       str = "TSERVER_BAD_DRIVER_PROTOCOL";
/* 481 */       break;
/*     */     case 83:
/* 484 */       str = "TSERVER_BAD_TRANSPORT_TYPE";
/* 485 */       break;
/*     */     case 84:
/* 488 */       str = "TSERVER_PDU_VERSION_MISMATCH";
/* 489 */       break;
/*     */     case 85:
/* 492 */       str = "TSERVER_VERSION_MISMATCH";
/* 493 */       break;
/*     */     case 86:
/* 496 */       str = "TSERVER_LICENSE_MISMATCH";
/* 497 */       break;
/*     */     case 87:
/* 500 */       str = "TSERVER_BAD_ATTRIBUTE_LIST";
/* 501 */       break;
/*     */     case 88:
/* 504 */       str = "TSERVER_BAD_TLIST_TYPE";
/* 505 */       break;
/*     */     case 89:
/* 508 */       str = "TSERVER_BAD_PROTOCOL_FORMAT";
/* 509 */       break;
/*     */     case 90:
/* 512 */       str = "TSERVER_OLD_TSLIB";
/* 513 */       break;
/*     */     case 91:
/* 516 */       str = "TSERVER_BAD_LICENSE_FILE";
/* 517 */       break;
/*     */     case 92:
/* 520 */       str = "TSERVER_NO_PATCHES";
/* 521 */       break;
/*     */     case 93:
/* 524 */       str = "TSERVER_SYSTEM_ERROR";
/* 525 */       break;
/*     */     case 94:
/* 528 */       str = "TSERVER_OAM_LIST_EMPTY";
/* 529 */       break;
/*     */     case 95:
/* 532 */       str = "TSERVER_TCP_FAILED";
/* 533 */       break;
/*     */     case 96:
/* 536 */       str = "TSERVER_SPX_DISABLED";
/* 537 */       break;
/*     */     case 97:
/* 540 */       str = "TSERVER_TCP_DISABLED";
/* 541 */       break;
/*     */     case 98:
/* 544 */       str = "TSERVER_REQUIRED_MODULES_NOT_LOADED";
/* 545 */       break;
/*     */     case 99:
/* 548 */       str = "TSERVER_TRANSPORT_IN_USE_BY_OAM";
/* 549 */       break;
/*     */     case 100:
/* 552 */       str = "TSERVER_NO_NDS_OAM_PERMISSION";
/* 553 */       break;
/*     */     case 101:
/* 556 */       str = "TSERVER_OPEN_SDB_LOG_FAILED";
/* 557 */       break;
/*     */     case 102:
/* 560 */       str = "TSERVER_INVALID_LOG_SIZE";
/* 561 */       break;
/*     */     case 103:
/* 564 */       str = "TSERVER_WRITE_SDB_LOG_FAILED";
/* 565 */       break;
/*     */     case 104:
/* 568 */       str = "TSERVER_NT_FAILURE";
/* 569 */       break;
/*     */     case 105:
/* 572 */       str = "TSERVER_LOAD_LIB_FAILED";
/* 573 */       break;
/*     */     case 106:
/* 576 */       str = "TSERVER_INVALID_DRIVER";
/* 577 */       break;
/*     */     case 107:
/* 580 */       str = "TSERVER_REGISTRY_ERROR";
/* 581 */       break;
/*     */     case 108:
/* 584 */       str = "TSERVER_DUPLICATE_ENTRY";
/* 585 */       break;
/*     */     case 109:
/* 588 */       str = "TSERVER_DRIVER_LOADED";
/* 589 */       break;
/*     */     case 110:
/* 592 */       str = "TSERVER_DRIVER_NOT_LOADED";
/* 593 */       break;
/*     */     case 111:
/* 596 */       str = "TSERVER_NO_LOGON_PERMISSION";
/* 597 */       break;
/*     */     case 112:
/* 600 */       str = "TSERVER_ACCOUNT_DISABLED";
/* 601 */       break;
/*     */     case 113:
/* 604 */       str = "TSERVER_NO_NETLOGON";
/* 605 */       break;
/*     */     case 114:
/* 608 */       str = "TSERVER_ACCT_RESTRICTED";
/* 609 */       break;
/*     */     case 115:
/* 612 */       str = "TSERVER_INVALID_LOGON_TIME";
/* 613 */       break;
/*     */     case 116:
/* 616 */       str = "TSERVER_INVALID_WORKSTATION";
/* 617 */       break;
/*     */     case 117:
/* 620 */       str = "TSERVER_ACCT_LOCKED_OUT";
/* 621 */       break;
/*     */     case 118:
/* 624 */       str = "TSERVER_PASSWORD_EXPIRED";
/* 625 */       break;
/*     */     case 119:
/* 628 */       str = "TSERVER_INVALID_HEARTBEAT_INTERVAL";
/* 629 */       break;
/*     */     case 120:
/* 632 */       str = "TSERVER_UNAUTHORIZED_CONNECTION";
/* 633 */       break;
/*     */     case 121:
/* 636 */       str = "TSERVER_INVALID_NONCE";
/* 637 */       break;
/*     */     case 122:
/* 640 */       str = "TSERVER_INVALID_CERTIFICATE";
/* 641 */       break;
/*     */     case 123:
/* 644 */       str = "TSERVER_INVALID_SIGNATURE";
/* 645 */       break;
/*     */     case 124:
/* 648 */       str = "TSERVER_UNKNOWN_APPLICATION";
/* 649 */       break;
/*     */     case 125:
/* 652 */       str = "TSERVER_CERTIFICATE_EXPIRED";
/* 653 */       break;
/*     */     case 126:
/* 656 */       str = "TSERVER_INVALID_PRIV_MESSAGE";
/* 657 */       break;
/*     */     case 127:
/* 660 */       str = "TSERVER_NOT_FIRST_MESSAGE";
/* 661 */       break;
/*     */     case 128:
/* 664 */       str = "TSERVER_NOT_SECOND_MESSAGE";
/* 665 */       break;
/*     */     case 1000:
/* 668 */       str = "DRIVER_DUPLICATE_ACSHANDLE";
/* 669 */       break;
/*     */     case 1001:
/* 672 */       str = "DRIVER_INVALID_ACS_REQUEST";
/* 673 */       break;
/*     */     case 1002:
/* 676 */       str = "DRIVER_ACS_HANDLE_REJECTION";
/* 677 */       break;
/*     */     case 1003:
/* 680 */       str = "DRIVER_INVALID_CLASS_REJECTION";
/* 681 */       break;
/*     */     case 1004:
/* 684 */       str = "DRIVER_GENERIC_REJECTION";
/* 685 */       break;
/*     */     case 1005:
/* 688 */       str = "DRIVER_RESOURCE_LIMITATION";
/* 689 */       break;
/*     */     case 1006:
/* 692 */       str = "DRIVER_ACSHANDLE_TERMINATION";
/* 693 */       break;
/*     */     case 1007:
/* 696 */       str = "DRIVER_LINK_UNAVAILABLE";
/* 697 */       break;
/*     */     case 1008:
/* 700 */       str = "DRIVER_OAM_IN_USE";
/* 701 */       break;
/*     */     default:
/* 704 */       str = "?? " + value + " ??";
/*     */     }
/*     */ 
/* 708 */     lines.addAll(print(value, str, name, indent));
/* 709 */     return lines;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSUniversalFailure
 * JD-Core Version:    0.5.4
 */