 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentV7ConnectionClearedEvent extends LucentV6ConnectionClearedEvent
 {
   private LucentDeviceHistoryEntry[] deviceHistory;
   static final int PDU = 134;
 
   public static LucentConnectionClearedEvent decode(InputStream in)
   {
     LucentV7ConnectionClearedEvent _this = new LucentV7ConnectionClearedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     super.decodeMembers(memberStream);
     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     super.encodeMembers(memberStream);
     CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentV7ConnectionClearedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 134;
   }
 
   public LucentDeviceHistoryEntry[] getDeviceHistory()
   {
     return this.deviceHistory;
   }
 
   public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
     this.deviceHistory = deviceHistory;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7ConnectionClearedEvent
 * JD-Core Version:    0.5.4
 */