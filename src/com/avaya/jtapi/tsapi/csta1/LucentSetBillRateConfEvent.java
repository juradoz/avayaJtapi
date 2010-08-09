 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNNull;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentSetBillRateConfEvent extends LucentPrivateData
 {
   static final int PDU = 75;
 
   static LucentSetBillRateConfEvent decode(InputStream in)
   {
     LucentSetBillRateConfEvent _this = new LucentSetBillRateConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     ASNNull.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentSetBillRateConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNNull.print(indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 75;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSetBillRateConfEvent
 * JD-Core Version:    0.5.4
 */