 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTADeliveredEvent extends CSTAUnsolicited
 {
   CSTAConnectionID connection;
   CSTAExtendedDeviceID alertingDevice;
   CSTAExtendedDeviceID callingDevice;
   CSTAExtendedDeviceID calledDevice;
   CSTAExtendedDeviceID lastRedirectionDevice;
   short localConnectionInfo;
   short cause;
   public static final int PDU = 57;
 
   public static CSTADeliveredEvent decode(InputStream in)
   {
     CSTADeliveredEvent _this = new CSTADeliveredEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.connection, memberStream);
     CSTAExtendedDeviceID.encode(this.alertingDevice, memberStream);
     CSTAExtendedDeviceID.encode(this.callingDevice, memberStream);
     CSTAExtendedDeviceID.encode(this.calledDevice, memberStream);
     CSTAExtendedDeviceID.encode(this.lastRedirectionDevice, memberStream);
     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
     CSTAEventCause.encode(this.cause, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.connection = CSTAConnectionID.decode(memberStream);
     this.alertingDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.callingDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.calledDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.lastRedirectionDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
     this.cause = CSTAEventCause.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTADeliveredEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAConnectionID.print(this.connection, "connection", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.alertingDevice, "alertingDevice", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice, "callingDevice", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.calledDevice, "calledDevice", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.lastRedirectionDevice, "lastRedirectionDevice", indent));
     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 57;
   }
 
   public CSTAExtendedDeviceID getAlertingDevice()
   {
     return this.alertingDevice;
   }
 
   public CSTAExtendedDeviceID getCalledDevice()
   {
     return this.calledDevice;
   }
 
   public CSTAExtendedDeviceID getCallingDevice()
   {
     return this.callingDevice;
   }
 
   public short getCause()
   {
     return this.cause;
   }
 
   public CSTAConnectionID getConnection()
   {
     return this.connection;
   }
 
   public CSTAExtendedDeviceID getLastRedirectionDevice()
   {
     return this.lastRedirectionDevice;
   }
 
   public short getLocalConnectionInfo()
   {
     return this.localConnectionInfo;
   }
 
   public void setAlertingDevice(CSTAExtendedDeviceID alertingDevice) {
     this.alertingDevice = alertingDevice;
   }
 
   public void setCalledDevice(CSTAExtendedDeviceID calledDevice) {
     this.calledDevice = calledDevice;
   }
 
   public void setCallingDevice(CSTAExtendedDeviceID callingDevice) {
     this.callingDevice = callingDevice;
   }
 
   public void setCause(short cause) {
     this.cause = cause;
   }
 
   public void setConnection(CSTAConnectionID connection) {
     this.connection = connection;
   }
 
   public void setLastRedirectionDevice(CSTAExtendedDeviceID lastRedirectionDevice) {
     this.lastRedirectionDevice = lastRedirectionDevice;
   }
 
   public void setLocalConnectionInfo(short localConnectionInfo) {
     this.localConnectionInfo = localConnectionInfo;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTADeliveredEvent
 * JD-Core Version:    0.5.4
 */