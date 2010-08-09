 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentSingleStepTransferCall extends LucentPrivateData
 {
   CSTAConnectionID activeCall;
   String transferredTo;
   public static final int PDU = 142;
 
   public LucentSingleStepTransferCall(CSTAConnectionID _activeCall, String _transferredTo)
   {
     this.activeCall = _activeCall;
     this.transferredTo = _transferredTo;
   }
 
   public LucentSingleStepTransferCall()
   {
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTAConnectionID.encode(this.activeCall, memberStream);
     DeviceID.encode(this.transferredTo, memberStream);
   }
 
   public static LucentSingleStepTransferCall decode(InputStream in)
   {
     LucentSingleStepTransferCall _this = new LucentSingleStepTransferCall();
 
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.activeCall = CSTAConnectionID.decode(memberStream);
     this.transferredTo = DeviceID.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentSingleStepTransferCall ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall", indent));
     lines.addAll(DeviceID.print(this.transferredTo, "transferredTo", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 142;
   }
 
   public CSTAConnectionID getActiveCall()
   {
     return this.activeCall;
   }
 
   public String getTransferredTo() {
     return this.transferredTo;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSingleStepTransferCall
 * JD-Core Version:    0.5.4
 */