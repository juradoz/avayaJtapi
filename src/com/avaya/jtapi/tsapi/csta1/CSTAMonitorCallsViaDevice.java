 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAMonitorCallsViaDevice extends CSTARequest
 {
   String deviceID;
   CSTAMonitorFilter monitorFilter;
   public static final int PDU = 113;
 
   public CSTAMonitorCallsViaDevice(String _deviceID, CSTAMonitorFilter _monitorFilter)
   {
     this.deviceID = _deviceID;
     this.monitorFilter = _monitorFilter;
   }
 
   public CSTAMonitorCallsViaDevice() {
   }
 
   public void encodeMembers(OutputStream memberStream) {
     DeviceID.encode(this.deviceID, memberStream);
     CSTAMonitorFilter.encode(this.monitorFilter, memberStream);
   }
 
   public static CSTAMonitorCallsViaDevice decode(InputStream in)
   {
     CSTAMonitorCallsViaDevice _this = new CSTAMonitorCallsViaDevice();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.deviceID = DeviceID.decode(memberStream);
     this.monitorFilter = CSTAMonitorFilter.decode(memberStream);
   }
 
   public Collection<String> print() {
     Collection lines = new ArrayList();
 
     lines.add("CSTAMonitorCallsViaDevice ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.deviceID, "deviceID", indent));
     lines.addAll(CSTAMonitorFilter.print(this.monitorFilter, "monitorFilter", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 113;
   }
 
   public String getDeviceID()
   {
     return this.deviceID;
   }
 
   public CSTAMonitorFilter getMonitorFilter()
   {
     return this.monitorFilter;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMonitorCallsViaDevice
 * JD-Core Version:    0.5.4
 */