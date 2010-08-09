 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAHeldEvent extends CSTAUnsolicited
 {
   CSTAConnectionID heldConnection;
   CSTAExtendedDeviceID holdingDevice;
   short localConnectionInfo;
   short cause;
   public static final int PDU = 61;
 
   public static CSTAHeldEvent decode(InputStream in)
   {
     CSTAHeldEvent _this = new CSTAHeldEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTAConnectionID.encode(this.heldConnection, memberStream);
     CSTAExtendedDeviceID.encode(this.holdingDevice, memberStream);
     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
     CSTAEventCause.encode(this.cause, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream) {
     this.heldConnection = CSTAConnectionID.decode(memberStream);
     this.holdingDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
     this.cause = CSTAEventCause.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAHeldEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAConnectionID.print(this.heldConnection, "heldConnection", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.holdingDevice, "holdingDevice", indent));
     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 61;
   }
 
   public short getCause()
   {
     return this.cause;
   }
 
   public CSTAConnectionID getHeldConnection()
   {
     return this.heldConnection;
   }
 
   public CSTAExtendedDeviceID getHoldingDevice()
   {
     return this.holdingDevice;
   }
 
   public short getLocalConnectionInfo()
   {
     return this.localConnectionInfo;
   }
 
   public void setCause(short cause) {
     this.cause = cause;
   }
 
   public void setHeldConnection(CSTAConnectionID heldConnection) {
     this.heldConnection = heldConnection;
   }
 
   public void setHoldingDevice(CSTAExtendedDeviceID holdingDevice) {
     this.holdingDevice = holdingDevice;
   }
 
   public void setLocalConnectionInfo(short localConnectionInfo) {
     this.localConnectionInfo = localConnectionInfo;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAHeldEvent
 * JD-Core Version:    0.5.4
 */