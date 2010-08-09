 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentTransferredEvent extends LucentPrivateData
 {
   LucentOriginalCallInfo originalCallInfo;
   CSTAExtendedDeviceID distributingDevice_asn;
   static final int PDU = 62;
 
   public static LucentTransferredEvent decode(InputStream in)
   {
     LucentTransferredEvent _this = new LucentTransferredEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream) {
     encodeOCI(this.originalCallInfo, memberStream);
     CSTAExtendedDeviceID.encode(this.distributingDevice_asn, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream) {
     this.originalCallInfo = decodeOCI(memberStream);
     this.distributingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentTransferredEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(LucentOriginalCallInfo.print(this.originalCallInfo, "originalCallInfo", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 62;
   }
 
   public CSTAExtendedDeviceID getDistributingDevice_asn()
   {
     return this.distributingDevice_asn;
   }
   public void setDistributingDevice_asn(CSTAExtendedDeviceID _distributingDevice_asn) {
     this.distributingDevice_asn = _distributingDevice_asn;
   }
 
   public LucentOriginalCallInfo getOriginalCallInfo()
   {
     return this.originalCallInfo;
   }
   public void setOriginalCallInfo(LucentOriginalCallInfo _info) {
     this.originalCallInfo = _info;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentTransferredEvent
 * JD-Core Version:    0.5.4
 */