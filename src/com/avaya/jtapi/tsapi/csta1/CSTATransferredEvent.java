 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTATransferredEvent extends CSTAUnsolicited
 {
   CSTAConnectionID primaryOldCall;
   CSTAConnectionID secondaryOldCall;
   CSTAExtendedDeviceID transferringDevice;
   CSTAExtendedDeviceID transferredDevice;
   CSTAConnection[] transferredConnections;
   short localConnectionInfo;
   short cause;
   public static final int PDU = 67;
 
   public static CSTATransferredEvent decode(InputStream in)
   {
     CSTATransferredEvent _this = new CSTATransferredEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTAConnectionID.encode(this.primaryOldCall, memberStream);
     CSTAConnectionID.encode(this.secondaryOldCall, memberStream);
     CSTAExtendedDeviceID.encode(this.transferringDevice, memberStream);
     CSTAExtendedDeviceID.encode(this.transferredDevice, memberStream);
     ConnectionList.encode(this.transferredConnections, memberStream);
     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
     CSTAEventCause.encode(this.cause, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream) {
     this.primaryOldCall = CSTAConnectionID.decode(memberStream);
     this.secondaryOldCall = CSTAConnectionID.decode(memberStream);
     this.transferringDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.transferredDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.transferredConnections = ConnectionList.decode(memberStream);
     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
     this.cause = CSTAEventCause.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTATransferredEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAConnectionID.print(this.primaryOldCall, "primaryOldCall", indent));
     lines.addAll(CSTAConnectionID.print(this.secondaryOldCall, "secondaryOldCall", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.transferringDevice, "transferringDevice", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.transferredDevice, "transferredDevice", indent));
     lines.addAll(ConnectionList.print(this.transferredConnections, "transferredConnections", indent));
     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 67;
   }
 
   public short getCause()
   {
     return this.cause;
   }
 
   public short getLocalConnectionInfo()
   {
     return this.localConnectionInfo;
   }
 
   public CSTAConnectionID getPrimaryOldCall()
   {
     return this.primaryOldCall;
   }
 
   public CSTAConnectionID getSecondaryOldCall()
   {
     return this.secondaryOldCall;
   }
 
   public CSTAConnection[] getTransferredConnections()
   {
     return this.transferredConnections;
   }
 
   public CSTAExtendedDeviceID getTransferredDevice()
   {
     return this.transferredDevice;
   }
 
   public CSTAExtendedDeviceID getTransferringDevice()
   {
     return this.transferringDevice;
   }
 
   public void setCause(short _cause) {
     this.cause = _cause;
   }
 
   public void setLocalConnectionInfo(short _localConnectionInfo) {
     this.localConnectionInfo = _localConnectionInfo;
   }
 
   public void setPrimaryOldCall(CSTAConnectionID _primaryOldCall) {
     this.primaryOldCall = _primaryOldCall;
   }
 
   public void setSecondaryOldCall(CSTAConnectionID _secondaryOldCall) {
     this.secondaryOldCall = _secondaryOldCall;
   }
 
   public void setTransferredConnections(CSTAConnection[] _transferredConnections) {
     this.transferredConnections = _transferredConnections;
   }
 
   public void setTransferredDevice(CSTAExtendedDeviceID _transferredDevice) {
     this.transferredDevice = _transferredDevice;
   }
 
   public void setTransferringDevice(CSTAExtendedDeviceID _transferringDevice) {
     this.transferringDevice = _transferringDevice;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTATransferredEvent
 * JD-Core Version:    0.5.4
 */