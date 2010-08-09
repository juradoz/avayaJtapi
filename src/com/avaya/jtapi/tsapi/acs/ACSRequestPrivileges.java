 package com.avaya.jtapi.tsapi.acs;
 
 import com.avaya.jtapi.tsapi.asn1.ASNNull;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class ACSRequestPrivileges extends ACSRequest
 {
   public static final int PDU = 17;
 
   public void decodeMembers(InputStream memberStream)
   {
     ASNNull.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     ASNNull.encode(memberStream);
   }
 
   public static ACSRequestPrivileges decode(InputStream in)
   {
     ACSRequestPrivileges _this = new ACSRequestPrivileges();
     _this.doDecode(in);
 
     return _this;
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("ACSRequestPrivileges ::=");
     lines.add("{");
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 17;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSRequestPrivileges
 * JD-Core Version:    0.5.4
 */