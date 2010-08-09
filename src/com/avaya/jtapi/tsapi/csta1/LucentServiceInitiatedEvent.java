 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentServiceInitiatedEvent extends LucentPrivateData
   implements HasUCID
 {
   String ucid;
   static final int PDU = 95;
 
   public static LucentServiceInitiatedEvent decode(InputStream in)
   {
     LucentServiceInitiatedEvent _this = new LucentServiceInitiatedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.ucid = UCID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     UCID.encode(this.ucid, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentServiceInitiatedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(UCID.print(this.ucid, "ucid", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 95;
   }
 
   public String getUcid()
   {
     return this.ucid;
   }
 
   public void setUcid(String ucid) {
     this.ucid = ucid;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentServiceInitiatedEvent
 * JD-Core Version:    0.5.4
 */