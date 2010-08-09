 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class CSTASetMwi extends CSTARequest
 {
   String device;
   boolean messages;
   static final int PDU = 43;
 
   public CSTASetMwi(String _device, boolean _messages)
   {
     this.device = _device;
     this.messages = _messages;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     DeviceID.encode(this.device, memberStream);
     ASNBoolean.encode(this.messages, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTASetMwi ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.device, "device", indent));
     lines.addAll(ASNBoolean.print(this.messages, "messages", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 43;
   }
 
   public String getDevice()
   {
     return this.device;
   }
 
   public boolean isMessages()
   {
     return this.messages;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASetMwi
 * JD-Core Version:    0.5.4
 */