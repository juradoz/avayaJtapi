 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAConnectionClearedEvent extends CSTAUnsolicited
 {
   CSTAConnectionID droppedConnection;
   CSTAExtendedDeviceID releasingDevice;
   short localConnectionInfo;
   short cause;
   public static final int PDU = 56;
 
   public static CSTAConnectionClearedEvent decode(InputStream in)
   {
     CSTAConnectionClearedEvent _this = new CSTAConnectionClearedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.droppedConnection = CSTAConnectionID.decode(memberStream);
     this.releasingDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
     this.cause = CSTAEventCause.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTAConnectionID.encode(this.droppedConnection, memberStream);
     CSTAExtendedDeviceID.encode(this.releasingDevice, memberStream);
     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
     CSTAEventCause.encode(this.cause, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAConnectionClearedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAConnectionID.print(this.droppedConnection, "droppedConnection", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.releasingDevice, "releasingDevice", indent));
     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 56;
   }
 
   public short getCause()
   {
     return this.cause;
   }
 
   public CSTAConnectionID getDroppedConnection()
   {
     return this.droppedConnection;
   }
 
   public short getLocalConnectionInfo()
   {
     return this.localConnectionInfo;
   }
 
   public CSTAExtendedDeviceID getReleasingDevice()
   {
     return this.releasingDevice;
   }
 
   public void setCause(short cause) {
     this.cause = cause;
   }
 
   public void setDroppedConnection(CSTAConnectionID droppedConnection) {
     this.droppedConnection = droppedConnection;
   }
 
   public void setReleasingDevice(CSTAExtendedDeviceID releasingDevice) {
     this.releasingDevice = releasingDevice;
   }
 
   public void setLocalConnectionInfo(short localConnectionInfo) {
     this.localConnectionInfo = localConnectionInfo;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConnectionClearedEvent
 * JD-Core Version:    0.5.4
 */