 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAOutOfServiceEvent extends CSTAUnsolicited
 {
   String device;
   short cause;
   static final int PDU = 97;
 
   static CSTAOutOfServiceEvent decode(InputStream in)
   {
     CSTAOutOfServiceEvent _this = new CSTAOutOfServiceEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.device = DeviceID.decode(memberStream);
     this.cause = CSTAEventCause.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAOutOfServiceEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(DeviceID.print(this.device, "device", indent));
     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 97;
   }
 
   public short getCause()
   {
     return this.cause;
   }
 
   public String getDevice()
   {
     return this.device;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAOutOfServiceEvent
 * JD-Core Version:    0.5.4
 */