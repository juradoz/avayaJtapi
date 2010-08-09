 package com.avaya.jtapi.tsapi.acs;
 
 import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class ACSAuthType extends ASNEnumerated
 {
   static final short REQUIRES_EXTERNAL_AUTH = -1;
   static final short AUTH_LOGIN_ID_ONLY = 0;
   static final short AUTH_LOGIN_ID_IS_DEFAULT = 1;
   static final short NEED_LOGIN_ID_AND_PASSWD = 2;
   static final short ANY_LOGIN_ID = 3;
 
   static Collection<String> print(short value, String name, String indent)
   {
     Collection lines = new ArrayList();
     String str;
     switch (value)
     {
     case -1:
       str = "REQUIRES_EXTERNAL_AUTH";
       break;
     case 0:
       str = "AUTH_LOGIN_ID_ONLY";
       break;
     case 1:
       str = "AUTH_LOGIN_ID_IS_DEFAULT";
       break;
     case 2:
       str = "NEED_LOGIN_ID_AND_PASSWD";
       break;
     case 3:
       str = "ANY_LOGIN_ID";
       break;
     default:
       str = "?? " + value + " ??";
     }
 
     lines.addAll(print(value, str, name, indent));
     return lines;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSAuthType
 * JD-Core Version:    0.5.4
 */