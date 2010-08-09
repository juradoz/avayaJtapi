 package com.avaya.jtapi.tsapi.acs;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class ACSSetPrivilegesConfEvent extends ACSConfirmation
 {
   public static final int PDU = 20;
 
   public static ACSSetPrivilegesConfEvent decode(InputStream in)
   {
     ACSSetPrivilegesConfEvent _this = new ACSSetPrivilegesConfEvent();
 
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
   }
 
   public void decodeMembers(InputStream memberStream)
   {
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("ACSSetPrivilegesConfEvent ::=");
     lines.add("{");
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 20;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSSetPrivilegesConfEvent
 * JD-Core Version:    0.5.4
 */