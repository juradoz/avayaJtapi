 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentQueryAcdSplit extends LucentPrivateData
 {
   String device;
   public static final int PDU = 11;
 
   public LucentQueryAcdSplit()
   {
   }
 
   public LucentQueryAcdSplit(String _device)
   {
     this.device = _device;
   }
 
   public static LucentQueryAcdSplit decode(InputStream in)
   {
     LucentQueryAcdSplit _this = new LucentQueryAcdSplit();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream) {
     this.device = DeviceID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     DeviceID.encode(this.device, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("LucentQueryAcdSplit ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.device, "device", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 11;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryAcdSplit
 * JD-Core Version:    0.5.4
 */