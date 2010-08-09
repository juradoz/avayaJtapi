 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAMakePredictiveCall extends CSTARequest
 {
   String callingDevice;
   String calledDevice;
   short allocationState;
   public static final int PDU = 25;
 
   public CSTAMakePredictiveCall()
   {
   }
 
   public CSTAMakePredictiveCall(String _callingDevice, String _calledDevice, short _allocationState)
   {
     this.callingDevice = _callingDevice;
     this.calledDevice = _calledDevice;
     this.allocationState = _allocationState;
   }
 
   public static CSTAMakePredictiveCall decode(InputStream in) {
     CSTAMakePredictiveCall _this = new CSTAMakePredictiveCall();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream) {
     this.callingDevice = DeviceID.decode(memberStream);
     this.calledDevice = DeviceID.decode(memberStream);
     this.allocationState = AllocationState.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     DeviceID.encode(this.callingDevice, memberStream);
     DeviceID.encode(this.calledDevice, memberStream);
     AllocationState.encode(this.allocationState, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAMakePredictiveCall ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.callingDevice, "callingDevice", indent));
     lines.addAll(DeviceID.print(this.calledDevice, "calledDevice", indent));
     lines.addAll(AllocationState.print(this.allocationState, "allocationState", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 25;
   }
 
   public short getAllocationState()
   {
     return this.allocationState;
   }
 
   public String getCalledDevice()
   {
     return this.calledDevice;
   }
 
   public String getCallingDevice()
   {
     return this.callingDevice;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMakePredictiveCall
 * JD-Core Version:    0.5.4
 */