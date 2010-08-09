 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTARetrievedEvent extends CSTAUnsolicited
 {
   CSTAConnectionID retrievedConnection;
   CSTAExtendedDeviceID retrievingDevice;
   short localConnectionInfo;
   short cause;
   public static final int PDU = 65;
 
   public static CSTARetrievedEvent decode(InputStream in)
   {
     CSTARetrievedEvent _this = new CSTARetrievedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.retrievedConnection = CSTAConnectionID.decode(memberStream);
     this.retrievingDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
     this.cause = CSTAEventCause.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.retrievedConnection, memberStream);
     CSTAExtendedDeviceID.encode(this.retrievingDevice, memberStream);
     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
     CSTAEventCause.encode(this.cause, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTARetrievedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAConnectionID.print(this.retrievedConnection, "retrievedConnection", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.retrievingDevice, "retrievingDevice", indent));
     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 65;
   }
 
   public short getCause()
   {
     return this.cause;
   }
 
   public short getLocalConnectionInfo()
   {
     return this.localConnectionInfo;
   }
 
   public CSTAConnectionID getRetrievedConnection()
   {
     return this.retrievedConnection;
   }
 
   public CSTAExtendedDeviceID getRetrievingDevice()
   {
     return this.retrievingDevice;
   }
 
   public void setLocalConnectionInfo(short localConnectionInfo) {
     this.localConnectionInfo = localConnectionInfo;
   }
 
   public void setRetrievedConnection(CSTAConnectionID retrievedConnection) {
     this.retrievedConnection = retrievedConnection;
   }
 
   public void setRetrievingDevice(CSTAExtendedDeviceID retrievingDevice) {
     this.retrievingDevice = retrievingDevice;
   }
 
   public void setCause(short cause) {
     this.cause = cause;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARetrievedEvent
 * JD-Core Version:    0.5.4
 */