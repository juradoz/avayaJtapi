 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAQueryDeviceInfo extends CSTARequest
 {
   String device;
   public static final int PDU = 37;
 
   public CSTAQueryDeviceInfo(String _device)
   {
     this.device = _device;
   }
   public CSTAQueryDeviceInfo() {
   }
 
   public static CSTAQueryDeviceInfo decode(InputStream in) {
     CSTAQueryDeviceInfo _this = new CSTAQueryDeviceInfo();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     DeviceID.encode(this.device, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream) {
     this.device = DeviceID.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAQueryDeviceInfo ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.device, "device", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 37;
   }
 
   public String getDevice()
   {
     return this.device;
   }
   public void setDevice(String device) {
     this.device = device;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryDeviceInfo
 * JD-Core Version:    0.5.4
 */