 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAFailedEvent extends CSTAUnsolicited
 {
   CSTAConnectionID failedConnection;
   CSTAExtendedDeviceID failingDevice;
   CSTAExtendedDeviceID calledDevice;
   short localConnectionInfo;
   short cause;
   public static final int PDU = 60;
 
   public static CSTAFailedEvent decode(InputStream in)
   {
     CSTAFailedEvent _this = new CSTAFailedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.failedConnection, memberStream);
     CSTAExtendedDeviceID.encode(this.failingDevice, memberStream);
     CSTAExtendedDeviceID.encode(this.calledDevice, memberStream);
     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
     CSTAEventCause.encode(this.cause, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.failedConnection = CSTAConnectionID.decode(memberStream);
     this.failingDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.calledDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
     this.cause = CSTAEventCause.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAFailedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAConnectionID.print(this.failedConnection, "failedConnection", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.failingDevice, "failingDevice", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.calledDevice, "calledDevice", indent));
     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 60;
   }
 
   public CSTAExtendedDeviceID getCalledDevice()
   {
     return this.calledDevice;
   }
 
   public short getCause()
   {
     return this.cause;
   }
 
   public CSTAConnectionID getFailedConnection()
   {
     return this.failedConnection;
   }
 
   public CSTAExtendedDeviceID getFailingDevice()
   {
     return this.failingDevice;
   }
 
   public short getLocalConnectionInfo()
   {
     return this.localConnectionInfo;
   }
 
   public void setFailingDevice(CSTAExtendedDeviceID alertingDevice)
   {
     this.failingDevice = alertingDevice;
   }
 
   public void setCalledDevice(CSTAExtendedDeviceID calledDevice) {
     this.calledDevice = calledDevice;
   }
 
   public void setCause(short cause) {
     this.cause = cause;
   }
 
   public void setFailedConnection(CSTAConnectionID connection) {
     this.failedConnection = connection;
   }
 
   public void setLocalConnectionInfo(short localConnectionInfo) {
     this.localConnectionInfo = localConnectionInfo;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAFailedEvent
 * JD-Core Version:    0.5.4
 */