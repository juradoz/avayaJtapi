 package com.avaya.jtapi.tsapi.acs;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class ACSSetPrivileges extends ACSRequest
 {
   String xmlData;
   public static final int PDU = 19;
 
   public ACSSetPrivileges(String _xmlData)
   {
     this.xmlData = _xmlData;
   }
 
   public ACSSetPrivileges() {
   }
 
   public void encodeMembers(OutputStream memberStream) {
     XmlData.encode(this.xmlData, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.xmlData = XmlData.decode(memberStream);
   }
 
   public static ACSSetPrivileges decode(InputStream in)
   {
     ACSSetPrivileges _this = new ACSSetPrivileges();
     _this.doDecode(in);
 
     return _this;
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("ACSSetPrivileges ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(XmlData.print(this.xmlData, "xmlData", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 19;
   }
 
   public String getxmlData() {
     return this.xmlData;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSSetPrivileges
 * JD-Core Version:    0.5.4
 */