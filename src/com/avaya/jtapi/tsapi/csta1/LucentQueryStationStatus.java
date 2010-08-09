 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentQueryStationStatus extends LucentPrivateData
 {
   String device;
   static final int PDU = 22;
 
   LucentQueryStationStatus(String _device)
   {
     this.device = _device;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     DeviceID.encode(this.device, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentQueryStationStatus ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.device, "device", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 22;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryStationStatus
 * JD-Core Version:    0.5.4
 */