 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentQueryMwiConfEvent extends LucentPrivateData
 {
   int applicationType;
   static final int PDU = 21;
 
   public int getApplicationType()
   {
     return this.applicationType;
   }
 
   public static LucentQueryMwiConfEvent decode(InputStream in)
   {
     LucentQueryMwiConfEvent _this = new LucentQueryMwiConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.applicationType = LucentMwiApplication.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentQueryMwiConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(LucentMwiApplication.print(this.applicationType, "applicationType", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 21;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryMwiConfEvent
 * JD-Core Version:    0.5.4
 */