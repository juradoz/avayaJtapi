 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAConferencedEvent extends CSTAUnsolicited
 {
   CSTAConnectionID primaryOldCall;
   CSTAConnectionID secondaryOldCall;
   CSTAExtendedDeviceID confController;
   CSTAExtendedDeviceID addedParty;
   CSTAConnection[] conferenceConnections;
   short localConnectionInfo;
   short cause;
   public static final int PDU = 55;
 
   public static CSTAConferencedEvent decode(InputStream in)
   {
     CSTAConferencedEvent _this = new CSTAConferencedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.primaryOldCall, memberStream);
     CSTAConnectionID.encode(this.secondaryOldCall, memberStream);
     CSTAExtendedDeviceID.encode(this.confController, memberStream);
     CSTAExtendedDeviceID.encode(this.addedParty, memberStream);
     ConnectionList.encode(this.conferenceConnections, memberStream);
     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
     CSTAEventCause.encode(this.cause, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream) {
     this.primaryOldCall = CSTAConnectionID.decode(memberStream);
     this.secondaryOldCall = CSTAConnectionID.decode(memberStream);
     this.confController = CSTAExtendedDeviceID.decode(memberStream);
     this.addedParty = CSTAExtendedDeviceID.decode(memberStream);
     this.conferenceConnections = ConnectionList.decode(memberStream);
     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
     this.cause = CSTAEventCause.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAConferencedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAConnectionID.print(this.primaryOldCall, "primaryOldCall", indent));
     lines.addAll(CSTAConnectionID.print(this.secondaryOldCall, "secondaryOldCall", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.confController, "confController", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.addedParty, "addedParty", indent));
     lines.addAll(ConnectionList.print(this.conferenceConnections, "conferenceConnections", indent));
     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 55;
   }
 
   public CSTAExtendedDeviceID getAddedParty()
   {
     return this.addedParty;
   }
 
   public short getCause()
   {
     return this.cause;
   }
 
   public CSTAExtendedDeviceID getConfController()
   {
     return this.confController;
   }
 
   public CSTAConnection[] getConferenceConnections()
   {
     return this.conferenceConnections;
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
 
   public void setAddedParty(CSTAExtendedDeviceID _conferencedDevice)
   {
     this.addedParty = _conferencedDevice;
   }
 
   public void setConfController(CSTAExtendedDeviceID _conferencingDevice) {
     this.confController = _conferencingDevice;
   }
 
   public void setCause(short _cause) {
     this.cause = _cause;
   }
 
   public void setConferencedConnections(CSTAConnection[] _conferenceConnections)
   {
     this.conferenceConnections = _conferenceConnections;
   }
 
   public void setLocalConnectionInfo(short _localConnectionInfo)
   {
     this.localConnectionInfo = _localConnectionInfo;
   }
 
   public void setPrimaryOldCall(CSTAConnectionID _primaryOldCall)
   {
     this.primaryOldCall = _primaryOldCall;
   }
 
   public void setSecondaryOldCall(CSTAConnectionID _secondaryOldCall)
   {
     this.secondaryOldCall = _secondaryOldCall;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConferencedEvent
 * JD-Core Version:    0.5.4
 */