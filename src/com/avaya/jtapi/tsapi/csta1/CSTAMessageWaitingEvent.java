 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAMessageWaitingEvent extends CSTAUnsolicited
 {
   CSTAExtendedDeviceID deviceForMessage;
   CSTAExtendedDeviceID invokingDevice;
   boolean messageWaitingOn;
   public static final int PDU = 71;
 
   public static CSTAMessageWaitingEvent decode(InputStream in)
   {
     CSTAMessageWaitingEvent _this = new CSTAMessageWaitingEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.deviceForMessage = CSTAExtendedDeviceID.decode(memberStream);
     this.invokingDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.messageWaitingOn = ASNBoolean.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAExtendedDeviceID.encode(this.deviceForMessage, memberStream);
     CSTAExtendedDeviceID.encode(this.invokingDevice, memberStream);
     ASNBoolean.encode(this.messageWaitingOn, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTAMessageWaitingEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAExtendedDeviceID.print(this.deviceForMessage, "deviceForMessage", indent));
 
     lines.addAll(CSTAExtendedDeviceID.print(this.invokingDevice, "invokingDevice", indent));
 
     lines.addAll(ASNBoolean.print(this.messageWaitingOn, "messageWaitingOn", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 71;
   }
 
   public CSTAExtendedDeviceID getDeviceForMessage()
   {
     return this.deviceForMessage;
   }
 
   public CSTAExtendedDeviceID getInvokingDevice()
   {
     return this.invokingDevice;
   }
 
   public boolean isMessageWaitingOn()
   {
     return this.messageWaitingOn;
   }
 
   public void setDeviceForMessage(CSTAExtendedDeviceID deviceForMessage)
   {
     this.deviceForMessage = deviceForMessage;
   }
 
   public void setInvokingDevice(CSTAExtendedDeviceID invokingDevice)
   {
     this.invokingDevice = invokingDevice;
   }
 
   public void setMessageWaitingOn(boolean messageWaitingOn)
   {
     this.messageWaitingOn = messageWaitingOn;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMessageWaitingEvent
 * JD-Core Version:    0.5.4
 */