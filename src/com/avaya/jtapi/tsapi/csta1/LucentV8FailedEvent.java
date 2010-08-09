 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentV8FailedEvent extends LucentFailedEvent
 {
   CSTAExtendedDeviceID callingDevice_asn;
   static final int PDU = 141;
 
   public LucentV8FailedEvent()
   {
     this.callingDevice_asn = null;
   }
 
   static LucentFailedEvent decode(InputStream in)
   {
     LucentV8FailedEvent _this = new LucentV8FailedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     super.decodeMembers(memberStream);
     this.callingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     super.encodeMembers(memberStream);
     CSTAExtendedDeviceID.encode(this.callingDevice_asn, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("LucentV8FailedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice_asn, "callingDevice", indent));
     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 141;
   }
 
   public CSTAExtendedDeviceID getCallingDevice()
   {
     return this.callingDevice_asn;
   }
   public void setCallingDevice(CSTAExtendedDeviceID device) {
     this.callingDevice_asn = device;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV8FailedEvent
 * JD-Core Version:    0.5.4
 */