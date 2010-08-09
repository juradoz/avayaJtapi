 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTADoNotDisturbEvent extends CSTAUnsolicited
 {
   CSTAExtendedDeviceID device;
   boolean doNotDisturbOn;
   public static final int PDU = 69;
 
   public static CSTADoNotDisturbEvent decode(InputStream in)
   {
     CSTADoNotDisturbEvent _this = new CSTADoNotDisturbEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.device = CSTAExtendedDeviceID.decode(memberStream);
     this.doNotDisturbOn = ASNBoolean.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAExtendedDeviceID.encode(this.device, memberStream);
     ASNBoolean.encode(this.doNotDisturbOn, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTADoNotDisturbEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAExtendedDeviceID.print(this.device, "device", indent));
     lines.addAll(ASNBoolean.print(this.doNotDisturbOn, "doNotDisturbOn", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 69;
   }
 
   public CSTAExtendedDeviceID getDevice()
   {
     return this.device;
   }
   public void setDevice(CSTAExtendedDeviceID device) {
     this.device = device;
   }
 
   public boolean isDoNotDisturbOn()
   {
     return this.doNotDisturbOn;
   }
   public void setDoNotDisturbOn(boolean doNotDisturb) {
     this.doNotDisturbOn = doNotDisturb;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTADoNotDisturbEvent
 * JD-Core Version:    0.5.4
 */