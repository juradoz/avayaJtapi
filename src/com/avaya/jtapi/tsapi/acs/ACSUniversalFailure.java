 package com.avaya.jtapi.tsapi.acs;
 
 import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class ACSUniversalFailure extends ASNEnumerated
 {
   public static final short TSERVER_STREAM_FAILED = 0;
   public static final short TSERVER_NO_THREAD = 1;
   public static final short TSERVER_BAD_DRIVER_ID = 2;
   public static final short TSERVER_DEAD_DRIVER = 3;
   public static final short TSERVER_MESSAGE_HIGH_WATER_MARK = 4;
   public static final short TSERVER_FREE_BUFFER_FAILED = 5;
   public static final short TSERVER_SEND_TO_DRIVER = 6;
   public static final short TSERVER_RECEIVE_FROM_DRIVER = 7;
   public static final short TSERVER_REGISTRATION_FAILED = 8;
   public static final short TSERVER_SPX_FAILED = 9;
   public static final short TSERVER_TRACE = 10;
   public static final short TSERVER_NO_MEMORY = 11;
   public static final short TSERVER_ENCODE_FAILED = 12;
   public static final short TSERVER_DECODE_FAILED = 13;
   public static final short TSERVER_BAD_CONNECTION = 14;
   public static final short TSERVER_BAD_PDU = 15;
   public static final short TSERVER_NO_VERSION = 16;
   public static final short TSERVER_ECB_MAX_EXCEEDED = 17;
   public static final short TSERVER_NO_ECBS = 18;
   public static final short TSERVER_NO_SDB = 19;
   public static final short TSERVER_NO_SDB_CHECK_NEEDED = 20;
   public static final short TSERVER_SDB_CHECK_NEEDED = 21;
   public static final short TSERVER_BAD_SDB_LEVEL = 22;
   public static final short TSERVER_BAD_SERVERID = 23;
   public static final short TSERVER_BAD_STREAM_TYPE = 24;
   public static final short TSERVER_BAD_PASSWORD_OR_LOGIN = 25;
   public static final short TSERVER_NO_USER_RECORD = 26;
   public static final short TSERVER_NO_DEVICE_RECORD = 27;
   public static final short TSERVER_DEVICE_NOT_ON_LIST = 28;
   public static final short TSERVER_USERS_RESTRICTED_HOME = 30;
   public static final short TSERVER_NO_AWAYPERMISSION = 31;
   public static final short TSERVER_NO_HOMEPERMISSION = 32;
   public static final short TSERVER_NO_AWAY_WORKTOP = 33;
   public static final short TSERVER_BAD_DEVICE_RECORD = 34;
   public static final short TSERVER_DEVICE_NOT_SUPPORTED = 35;
   public static final short TSERVER_INSUFFICIENT_PERMISSION = 36;
   public static final short TSERVER_NO_RESOURCE_TAG = 37;
   public static final short TSERVER_INVALID_MESSAGE = 38;
   public static final short TSERVER_EXCEPTION_LIST = 39;
   public static final short TSERVER_NOT_ON_OAM_LIST = 40;
   public static final short TSERVER_PBX_ID_NOT_IN_SDB = 41;
   public static final short TSERVER_USER_LICENSES_EXCEEDED = 42;
   public static final short TSERVER_OAM_DROP_CONNECTION = 43;
   public static final short TSERVER_NO_VERSION_RECORD = 44;
   public static final short TSERVER_OLD_VERSION_RECORD = 45;
   public static final short TSERVER_BAD_PACKET = 46;
   public static final short TSERVER_OPEN_FAILED = 47;
   public static final short TSERVER_OAM_IN_USE = 48;
   public static final short TSERVER_DEVICE_NOT_ON_HOME_LIST = 49;
   public static final short TSERVER_DEVICE_NOT_ON_CALL_CONTROL_LIST = 50;
   public static final short TSERVER_DEVICE_NOT_ON_AWAY_LIST = 51;
   public static final short TSERVER_DEVICE_NOT_ON_ROUTE_LIST = 52;
   public static final short TSERVER_DEVICE_NOT_ON_MONITOR_DEVICE_LIST = 53;
   public static final short TSERVER_DEVICE_NOT_ON_MONITOR_CALL_DEVICE_LIST = 54;
   public static final short TSERVER_NO_CALL_CALL_MONITOR_PERMISSION = 55;
   public static final short TSERVER_HOME_DEVICE_LIST_EMPTY = 56;
   public static final short TSERVER_CALL_CONTROL_LIST_EMPTY = 57;
   public static final short TSERVER_AWAY_LIST_EMPTY = 58;
   public static final short TSERVER_ROUTE_LIST_EMPTY = 59;
   public static final short TSERVER_MONITOR_DEVICE_LIST_EMPTY = 60;
   public static final short TSERVER_MONITOR_CALL_DEVICE_LIST_EMPTY = 61;
   public static final short TSERVER_USER_AT_HOME_WORKTOP = 62;
   public static final short TSERVER_DEVICE_LIST_EMPTY = 63;
   public static final short TSERVER_BAD_GET_DEVICE_LEVEL = 64;
   public static final short TSERVER_DRIVER_UNREGISTERED = 65;
   public static final short TSERVER_NO_ACS_STREAM = 66;
   public static final short TSERVER_DROP_OAM = 67;
   public static final short TSERVER_ECB_TIMEOUT = 68;
   public static final short TSERVER_BAD_ECB = 69;
   public static final short TSERVER_ADVERTISE_FAILED = 70;
   public static final short TSERVER_NETWARE_FAILURE = 71;
   public static final short TSERVER_TDI_QUEUE_FAULT = 72;
   public static final short TSERVER_DRIVER_CONGESTION = 73;
   public static final short TSERVER_NO_TDI_BUFFERS = 74;
   public static final short TSERVER_OLD_INVOKEID = 75;
   public static final short TSERVER_HWMARK_TO_LARGE = 76;
   public static final short TSERVER_SET_ECB_TO_LOW = 77;
   public static final short TSERVER_NO_RECORD_IN_FILE = 78;
   public static final short TSERVER_ECB_OVERDUE = 79;
   public static final short TSERVER_BAD_PW_ENCRYPTION = 80;
   public static final short TSERVER_BAD_TSERV_PROTOCOL = 81;
   public static final short TSERVER_BAD_DRIVER_PROTOCOL = 82;
   public static final short TSERVER_BAD_TRANSPORT_TYPE = 83;
   public static final short TSERVER_PDU_VERSION_MISMATCH = 84;
   public static final short TSERVER_VERSION_MISMATCH = 85;
   public static final short TSERVER_LICENSE_MISMATCH = 86;
   public static final short TSERVER_BAD_ATTRIBUTE_LIST = 87;
   public static final short TSERVER_BAD_TLIST_TYPE = 88;
   public static final short TSERVER_BAD_PROTOCOL_FORMAT = 89;
   public static final short TSERVER_OLD_TSLIB = 90;
   public static final short TSERVER_BAD_LICENSE_FILE = 91;
   public static final short TSERVER_NO_PATCHES = 92;
   public static final short TSERVER_SYSTEM_ERROR = 93;
   public static final short TSERVER_OAM_LIST_EMPTY = 94;
   public static final short TSERVER_TCP_FAILED = 95;
   public static final short TSERVER_SPX_DISABLED = 96;
   public static final short TSERVER_TCP_DISABLED = 97;
   public static final short TSERVER_REQUIRED_MODULES_NOT_LOADED = 98;
   public static final short TSERVER_TRANSPORT_IN_USE_BY_OAM = 99;
   public static final short TSERVER_NO_NDS_OAM_PERMISSION = 100;
   public static final short TSERVER_OPEN_SDB_LOG_FAILED = 101;
   public static final short TSERVER_INVALID_LOG_SIZE = 102;
   public static final short TSERVER_WRITE_SDB_LOG_FAILED = 103;
   public static final short TSERVER_NT_FAILURE = 104;
   public static final short TSERVER_LOAD_LIB_FAILED = 105;
   public static final short TSERVER_INVALID_DRIVER = 106;
   public static final short TSERVER_REGISTRY_ERROR = 107;
   public static final short TSERVER_DUPLICATE_ENTRY = 108;
   public static final short TSERVER_DRIVER_LOADED = 109;
   public static final short TSERVER_DRIVER_NOT_LOADED = 110;
   public static final short TSERVER_NO_LOGON_PERMISSION = 111;
   public static final short TSERVER_ACCOUNT_DISABLED = 112;
   public static final short TSERVER_NO_NETLOGON = 113;
   public static final short TSERVER_ACCT_RESTRICTED = 114;
   public static final short TSERVER_INVALID_LOGON_TIME = 115;
   public static final short TSERVER_INVALID_WORKSTATION = 116;
   public static final short TSERVER_ACCT_LOCKED_OUT = 117;
   public static final short TSERVER_PASSWORD_EXPIRED = 118;
   public static final short TSERVER_INVALID_HEARTBEAT_INTERVAL = 119;
   public static final short TSERVER_UNAUTHORIZED_CONNECTION = 120;
   public static final short TSERVER_INVALID_NONCE = 121;
   public static final short TSERVER_INVALID_CERTIFICATE = 122;
   public static final short TSERVER_INVALID_SIGNATURE = 123;
   public static final short TSERVER_UNKNOWN_APPLICATION = 124;
   public static final short TSERVER_CERTIFICATE_EXPIRED = 125;
   public static final short TSERVER_INVALID_PRIV_MESSAGE = 126;
   public static final short TSERVER_NOT_FIRST_MESSAGE = 127;
   public static final short TSERVER_NOT_SECOND_MESSAGE = 128;
   public static final short DRIVER_DUPLICATE_ACSHANDLE = 1000;
   public static final short DRIVER_INVALID_ACS_REQUEST = 1001;
   public static final short DRIVER_ACS_HANDLE_REJECTION = 1002;
   public static final short DRIVER_INVALID_CLASS_REJECTION = 1003;
   public static final short DRIVER_GENERIC_REJECTION = 1004;
   public static final short DRIVER_RESOURCE_LIMITATION = 1005;
   public static final short DRIVER_ACSHANDLE_TERMINATION = 1006;
   public static final short DRIVER_LINK_UNAVAILABLE = 1007;
   public static final short DRIVER_OAM_IN_USE = 1008;
 
   static Collection<String> print(short value, String name, String indent)
   {
     Collection lines = new ArrayList();
     String str;
     switch (value)
     {
     case 0:
       str = "TSERVER_STREAM_FAILED";
       break;
     case 1:
       str = "TSERVER_NO_THREAD";
       break;
     case 2:
       str = "TSERVER_BAD_DRIVER_ID";
       break;
     case 3:
       str = "TSERVER_DEAD_DRIVER";
       break;
     case 4:
       str = "TSERVER_MESSAGE_HIGH_WATER_MARK";
       break;
     case 5:
       str = "TSERVER_FREE_BUFFER_FAILED";
       break;
     case 6:
       str = "TSERVER_SEND_TO_DRIVER";
       break;
     case 7:
       str = "TSERVER_RECEIVE_FROM_DRIVER";
       break;
     case 8:
       str = "TSERVER_REGISTRATION_FAILED";
       break;
     case 9:
       str = "TSERVER_SPX_FAILED";
       break;
     case 10:
       str = "TSERVER_TRACE";
       break;
     case 11:
       str = "TSERVER_NO_MEMORY";
       break;
     case 12:
       str = "TSERVER_ENCODE_FAILED";
       break;
     case 13:
       str = "TSERVER_DECODE_FAILED";
       break;
     case 14:
       str = "TSERVER_BAD_CONNECTION";
       break;
     case 15:
       str = "TSERVER_BAD_PDU";
       break;
     case 16:
       str = "TSERVER_NO_VERSION";
       break;
     case 17:
       str = "TSERVER_ECB_MAX_EXCEEDED";
       break;
     case 18:
       str = "TSERVER_NO_ECBS";
       break;
     case 19:
       str = "TSERVER_NO_SDB";
       break;
     case 20:
       str = "TSERVER_NO_SDB_CHECK_NEEDED";
       break;
     case 21:
       str = "TSERVER_SDB_CHECK_NEEDED";
       break;
     case 22:
       str = "TSERVER_BAD_SDB_LEVEL";
       break;
     case 23:
       str = "TSERVER_BAD_SERVERID";
       break;
     case 24:
       str = "TSERVER_BAD_STREAM_TYPE";
       break;
     case 25:
       str = "TSERVER_BAD_PASSWORD_OR_LOGIN";
       break;
     case 26:
       str = "TSERVER_NO_USER_RECORD";
       break;
     case 27:
       str = "TSERVER_NO_DEVICE_RECORD";
       break;
     case 28:
       str = "TSERVER_DEVICE_NOT_ON_LIST";
       break;
     case 30:
       str = "TSERVER_USERS_RESTRICTED_HOME";
       break;
     case 31:
       str = "TSERVER_NO_AWAYPERMISSION";
       break;
     case 32:
       str = "TSERVER_NO_HOMEPERMISSION";
       break;
     case 33:
       str = "TSERVER_NO_AWAY_WORKTOP";
       break;
     case 34:
       str = "TSERVER_BAD_DEVICE_RECORD";
       break;
     case 35:
       str = "TSERVER_DEVICE_NOT_SUPPORTED";
       break;
     case 36:
       str = "TSERVER_INSUFFICIENT_PERMISSION";
       break;
     case 37:
       str = "TSERVER_NO_RESOURCE_TAG";
       break;
     case 38:
       str = "TSERVER_INVALID_MESSAGE";
       break;
     case 39:
       str = "TSERVER_EXCEPTION_LIST";
       break;
     case 40:
       str = "TSERVER_NOT_ON_OAM_LIST";
       break;
     case 41:
       str = "TSERVER_PBX_ID_NOT_IN_SDB";
       break;
     case 42:
       str = "TSERVER_USER_LICENSES_EXCEEDED";
       break;
     case 43:
       str = "TSERVER_OAM_DROP_CONNECTION";
       break;
     case 44:
       str = "TSERVER_NO_VERSION_RECORD";
       break;
     case 45:
       str = "TSERVER_OLD_VERSION_RECORD";
       break;
     case 46:
       str = "TSERVER_BAD_PACKET";
       break;
     case 47:
       str = "TSERVER_OPEN_FAILED";
       break;
     case 48:
       str = "TSERVER_OAM_IN_USE";
       break;
     case 49:
       str = "TSERVER_DEVICE_NOT_ON_HOME_LIST";
       break;
     case 50:
       str = "TSERVER_DEVICE_NOT_ON_CALL_CONTROL_LIST";
       break;
     case 51:
       str = "TSERVER_DEVICE_NOT_ON_AWAY_LIST";
       break;
     case 52:
       str = "TSERVER_DEVICE_NOT_ON_ROUTE_LIST";
       break;
     case 53:
       str = "TSERVER_DEVICE_NOT_ON_MONITOR_DEVICE_LIST";
       break;
     case 54:
       str = "TSERVER_DEVICE_NOT_ON_MONITOR_CALL_DEVICE_LIST";
       break;
     case 55:
       str = "TSERVER_NO_CALL_CALL_MONITOR_PERMISSION";
       break;
     case 56:
       str = "TSERVER_HOME_DEVICE_LIST_EMPTY";
       break;
     case 57:
       str = "TSERVER_CALL_CONTROL_LIST_EMPTY";
       break;
     case 58:
       str = "TSERVER_AWAY_LIST_EMPTY";
       break;
     case 59:
       str = "TSERVER_ROUTE_LIST_EMPTY";
       break;
     case 60:
       str = "TSERVER_MONITOR_DEVICE_LIST_EMPTY";
       break;
     case 61:
       str = "TSERVER_MONITOR_CALL_DEVICE_LIST_EMPTY";
       break;
     case 62:
       str = "TSERVER_USER_AT_HOME_WORKTOP";
       break;
     case 63:
       str = "TSERVER_DEVICE_LIST_EMPTY";
       break;
     case 64:
       str = "TSERVER_BAD_GET_DEVICE_LEVEL";
       break;
     case 65:
       str = "TSERVER_DRIVER_UNREGISTERED";
       break;
     case 66:
       str = "TSERVER_NO_ACS_STREAM";
       break;
     case 67:
       str = "TSERVER_DROP_OAM";
       break;
     case 68:
       str = "TSERVER_ECB_TIMEOUT";
       break;
     case 69:
       str = "TSERVER_BAD_ECB";
       break;
     case 70:
       str = "TSERVER_ADVERTISE_FAILED";
       break;
     case 71:
       str = "TSERVER_NETWARE_FAILURE";
       break;
     case 72:
       str = "TSERVER_TDI_QUEUE_FAULT";
       break;
     case 73:
       str = "TSERVER_DRIVER_CONGESTION";
       break;
     case 74:
       str = "TSERVER_NO_TDI_BUFFERS";
       break;
     case 75:
       str = "TSERVER_OLD_INVOKEID";
       break;
     case 76:
       str = "TSERVER_HWMARK_TO_LARGE";
       break;
     case 77:
       str = "TSERVER_SET_ECB_TO_LOW";
       break;
     case 78:
       str = "TSERVER_NO_RECORD_IN_FILE";
       break;
     case 79:
       str = "TSERVER_ECB_OVERDUE";
       break;
     case 80:
       str = "TSERVER_BAD_PW_ENCRYPTION";
       break;
     case 81:
       str = "TSERVER_BAD_TSERV_PROTOCOL";
       break;
     case 82:
       str = "TSERVER_BAD_DRIVER_PROTOCOL";
       break;
     case 83:
       str = "TSERVER_BAD_TRANSPORT_TYPE";
       break;
     case 84:
       str = "TSERVER_PDU_VERSION_MISMATCH";
       break;
     case 85:
       str = "TSERVER_VERSION_MISMATCH";
       break;
     case 86:
       str = "TSERVER_LICENSE_MISMATCH";
       break;
     case 87:
       str = "TSERVER_BAD_ATTRIBUTE_LIST";
       break;
     case 88:
       str = "TSERVER_BAD_TLIST_TYPE";
       break;
     case 89:
       str = "TSERVER_BAD_PROTOCOL_FORMAT";
       break;
     case 90:
       str = "TSERVER_OLD_TSLIB";
       break;
     case 91:
       str = "TSERVER_BAD_LICENSE_FILE";
       break;
     case 92:
       str = "TSERVER_NO_PATCHES";
       break;
     case 93:
       str = "TSERVER_SYSTEM_ERROR";
       break;
     case 94:
       str = "TSERVER_OAM_LIST_EMPTY";
       break;
     case 95:
       str = "TSERVER_TCP_FAILED";
       break;
     case 96:
       str = "TSERVER_SPX_DISABLED";
       break;
     case 97:
       str = "TSERVER_TCP_DISABLED";
       break;
     case 98:
       str = "TSERVER_REQUIRED_MODULES_NOT_LOADED";
       break;
     case 99:
       str = "TSERVER_TRANSPORT_IN_USE_BY_OAM";
       break;
     case 100:
       str = "TSERVER_NO_NDS_OAM_PERMISSION";
       break;
     case 101:
       str = "TSERVER_OPEN_SDB_LOG_FAILED";
       break;
     case 102:
       str = "TSERVER_INVALID_LOG_SIZE";
       break;
     case 103:
       str = "TSERVER_WRITE_SDB_LOG_FAILED";
       break;
     case 104:
       str = "TSERVER_NT_FAILURE";
       break;
     case 105:
       str = "TSERVER_LOAD_LIB_FAILED";
       break;
     case 106:
       str = "TSERVER_INVALID_DRIVER";
       break;
     case 107:
       str = "TSERVER_REGISTRY_ERROR";
       break;
     case 108:
       str = "TSERVER_DUPLICATE_ENTRY";
       break;
     case 109:
       str = "TSERVER_DRIVER_LOADED";
       break;
     case 110:
       str = "TSERVER_DRIVER_NOT_LOADED";
       break;
     case 111:
       str = "TSERVER_NO_LOGON_PERMISSION";
       break;
     case 112:
       str = "TSERVER_ACCOUNT_DISABLED";
       break;
     case 113:
       str = "TSERVER_NO_NETLOGON";
       break;
     case 114:
       str = "TSERVER_ACCT_RESTRICTED";
       break;
     case 115:
       str = "TSERVER_INVALID_LOGON_TIME";
       break;
     case 116:
       str = "TSERVER_INVALID_WORKSTATION";
       break;
     case 117:
       str = "TSERVER_ACCT_LOCKED_OUT";
       break;
     case 118:
       str = "TSERVER_PASSWORD_EXPIRED";
       break;
     case 119:
       str = "TSERVER_INVALID_HEARTBEAT_INTERVAL";
       break;
     case 120:
       str = "TSERVER_UNAUTHORIZED_CONNECTION";
       break;
     case 121:
       str = "TSERVER_INVALID_NONCE";
       break;
     case 122:
       str = "TSERVER_INVALID_CERTIFICATE";
       break;
     case 123:
       str = "TSERVER_INVALID_SIGNATURE";
       break;
     case 124:
       str = "TSERVER_UNKNOWN_APPLICATION";
       break;
     case 125:
       str = "TSERVER_CERTIFICATE_EXPIRED";
       break;
     case 126:
       str = "TSERVER_INVALID_PRIV_MESSAGE";
       break;
     case 127:
       str = "TSERVER_NOT_FIRST_MESSAGE";
       break;
     case 128:
       str = "TSERVER_NOT_SECOND_MESSAGE";
       break;
     case 1000:
       str = "DRIVER_DUPLICATE_ACSHANDLE";
       break;
     case 1001:
       str = "DRIVER_INVALID_ACS_REQUEST";
       break;
     case 1002:
       str = "DRIVER_ACS_HANDLE_REJECTION";
       break;
     case 1003:
       str = "DRIVER_INVALID_CLASS_REJECTION";
       break;
     case 1004:
       str = "DRIVER_GENERIC_REJECTION";
       break;
     case 1005:
       str = "DRIVER_RESOURCE_LIMITATION";
       break;
     case 1006:
       str = "DRIVER_ACSHANDLE_TERMINATION";
       break;
     case 1007:
       str = "DRIVER_LINK_UNAVAILABLE";
       break;
     case 1008:
       str = "DRIVER_OAM_IN_USE";
       break;
     default:
       str = "?? " + value + " ??";
     }
 
     lines.addAll(print(value, str, name, indent));
     return lines;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSUniversalFailure
 * JD-Core Version:    0.5.4
 */