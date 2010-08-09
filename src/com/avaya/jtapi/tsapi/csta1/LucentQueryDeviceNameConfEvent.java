 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentQueryDeviceNameConfEvent extends LucentPrivateData
 {
   short deviceType;
   String device_asn;
   String name;
   static final int PDU = 50;
 
   public static LucentQueryDeviceNameConfEvent decode(InputStream in)
   {
     LucentQueryDeviceNameConfEvent _this = new LucentQueryDeviceNameConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.deviceType = LucentDeviceType.decode(memberStream);
     this.device_asn = DeviceID.decode(memberStream);
     this.name = ASNIA5String.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     LucentDeviceType.encode(this.deviceType, memberStream);
     DeviceID.encode(this.device_asn, memberStream);
     ASNIA5String.encode(this.name, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentQueryDeviceNameConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(LucentDeviceType.print(this.deviceType, "deviceType", indent));
     lines.addAll(DeviceID.print(this.device_asn, "device", indent));
     lines.addAll(ASNIA5String.print(this.name, "name", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 50;
   }
 
   public String getDevice_asn()
   {
     return this.device_asn;
   }
 
   public short getDeviceType()
   {
     return this.deviceType;
   }
 
   public String getName()
   {
     return this.name;
   }
 
   public void setDevice_asn(String device_asn) {
     this.device_asn = device_asn;
   }
 
   public void setDeviceType(short deviceType) {
     this.deviceType = deviceType;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceNameConfEvent
 * JD-Core Version:    0.5.4
 */