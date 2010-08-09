 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentSnapshotCallInfoConfEvent extends LucentPrivateData
 {
   LucentDeviceHistoryEntry[] deviceHistory;
   static final int PDU = 138;
 
   static LucentSnapshotCallInfoConfEvent decode(InputStream in)
   {
     LucentSnapshotCallInfoConfEvent _this = new LucentSnapshotCallInfoConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentSnapshotCallInfoConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 138;
   }
   public LucentDeviceHistoryEntry[] getDeviceHistory() {
     return this.deviceHistory;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSnapshotCallInfoConfEvent
 * JD-Core Version:    0.5.4
 */