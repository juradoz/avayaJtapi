 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentQueryStationStatusConfEvent extends LucentPrivateData
 {
   boolean stationStatus;
   static final int PDU = 23;
 
   static LucentQueryStationStatusConfEvent decode(InputStream in)
   {
     LucentQueryStationStatusConfEvent _this = new LucentQueryStationStatusConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.stationStatus = ASNBoolean.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentQueryStationStatusConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNBoolean.print(this.stationStatus, "stationStatus", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 23;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryStationStatusConfEvent
 * JD-Core Version:    0.5.4
 */