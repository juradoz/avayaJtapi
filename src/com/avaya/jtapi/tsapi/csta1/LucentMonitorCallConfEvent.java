 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentMonitorCallConfEvent extends LucentPrivateData
 {
   int usedFilter;
   CSTASnapshotCallResponseInfo[] snapshotCall;
   public static final int PDU = 33;
 
   static LucentMonitorCallConfEvent decode(InputStream in)
   {
     LucentMonitorCallConfEvent _this = new LucentMonitorCallConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.usedFilter = LucentPrivateFilter.decode(memberStream);
     this.snapshotCall = LucentSnapshotCall.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     LucentPrivateFilter.encode(this.usedFilter, memberStream);
     LucentSnapshotCall.encode(this.snapshotCall, memberStream);
   }
 
   public Collection<String> print() {
     Collection lines = new ArrayList();
 
     lines.add("LucentMonitorCallConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(LucentPrivateFilter.print(this.usedFilter, "usedFilter", indent));
     lines.addAll(LucentSnapshotCall.print(this.snapshotCall, "snapshotCall", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 33;
   }
   public CSTASnapshotCallResponseInfo[] getSnapshotCall() {
     return this.snapshotCall;
   }
 
   public void setSnapshotCall(CSTASnapshotCallResponseInfo[] snapshotCall) {
     this.snapshotCall = snapshotCall;
   }
 
   public int getUsedFilter() {
     return this.usedFilter;
   }
 
   public void setUsedFilter(int usedFilter) {
     this.usedFilter = usedFilter;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMonitorCallConfEvent
 * JD-Core Version:    0.5.4
 */