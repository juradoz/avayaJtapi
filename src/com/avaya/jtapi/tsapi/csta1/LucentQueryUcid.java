 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentQueryUcid extends LucentPrivateData
 {
   CSTAConnectionID call;
   public static final int PDU = 76;
 
   public LucentQueryUcid()
   {
   }
 
   public LucentQueryUcid(CSTAConnectionID _call)
   {
     this.call = _call;
   }
 
   public static LucentQueryUcid decode(InputStream in) {
     LucentQueryUcid _this = new LucentQueryUcid();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.call = CSTAConnectionID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTAConnectionID.encode(this.call, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentQueryUcid ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.call, "call", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 76;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryUcid
 * JD-Core Version:    0.5.4
 */