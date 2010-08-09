 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTACallClearedEvent extends CSTAUnsolicited
 {
   CSTAConnectionID clearedCall;
   short localConnectionInfo;
   short cause;
   public static final int PDU = 54;
 
   public static CSTACallClearedEvent decode(InputStream in)
   {
     CSTACallClearedEvent _this = new CSTACallClearedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.clearedCall = CSTAConnectionID.decode(memberStream);
     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
     this.cause = CSTAEventCause.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.clearedCall, memberStream);
     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
     CSTAEventCause.encode(this.cause, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTACallClearedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAConnectionID.print(this.clearedCall, "clearedCall", indent));
     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 54;
   }
 
   public short getCause()
   {
     return this.cause;
   }
 
   public CSTAConnectionID getClearedCall()
   {
     return this.clearedCall;
   }
 
   public short getLocalConnectionInfo()
   {
     return this.localConnectionInfo;
   }
 
   public void setCause(short cause) {
     this.cause = cause;
   }
 
   public void setClearedCall(CSTAConnectionID clearedCall) {
     this.clearedCall = clearedCall;
   }
 
   public void setLocalConnectionInfo(short localConnectionInfo) {
     this.localConnectionInfo = localConnectionInfo;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTACallClearedEvent
 * JD-Core Version:    0.5.4
 */