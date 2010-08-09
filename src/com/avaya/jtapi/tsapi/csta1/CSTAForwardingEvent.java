 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAForwardingEvent extends CSTAUnsolicited
 {
   CSTAExtendedDeviceID device;
   CSTAForwardingInfo forwardingInformation;
   public static final int PDU = 70;
 
   public static CSTAForwardingEvent decode(InputStream in)
   {
     CSTAForwardingEvent _this = new CSTAForwardingEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.device = CSTAExtendedDeviceID.decode(memberStream);
     this.forwardingInformation = CSTAForwardingInfo.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTAExtendedDeviceID.encode(this.device, memberStream);
     CSTAForwardingInfo.encode(this.forwardingInformation, memberStream);
   }
 
   public Collection<String> print() {
     Collection lines = new ArrayList();
     lines.add("CSTAForwardingEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAExtendedDeviceID.print(this.device, "device", indent));
     lines.addAll(CSTAForwardingInfo.print(this.forwardingInformation, "forwardingInformation", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 70;
   }
 
   public CSTAExtendedDeviceID getDevice()
   {
     return this.device;
   }
   public void setDevice(CSTAExtendedDeviceID device) {
     this.device = device;
   }
 
   public CSTAForwardingInfo getForwardingInformation()
   {
     return this.forwardingInformation;
   }
   public void setForwardingInformation(CSTAForwardingInfo fwdInfo) {
     this.forwardingInformation = fwdInfo;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAForwardingEvent
 * JD-Core Version:    0.5.4
 */