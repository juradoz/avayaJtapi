 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class CSTAGroupPickupCall extends CSTARequest
 {
   CSTAConnectionID deflectCall;
   String pickupDevice;
   static final int PDU = 19;
 
   public CSTAGroupPickupCall(CSTAConnectionID _deflectCall, String _pickupDevice)
   {
     this.deflectCall = _deflectCall;
     this.pickupDevice = _pickupDevice;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.deflectCall, memberStream);
     DeviceID.encode(this.pickupDevice, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAGroupPickupCall ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.deflectCall, "deflectCall", indent));
     lines.addAll(DeviceID.print(this.pickupDevice, "pickupDevice", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 19;
   }
 
   public CSTAConnectionID getDeflectCall()
   {
     return this.deflectCall;
   }
 
   public String getPickupDevice()
   {
     return this.pickupDevice;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAGroupPickupCall
 * JD-Core Version:    0.5.4
 */