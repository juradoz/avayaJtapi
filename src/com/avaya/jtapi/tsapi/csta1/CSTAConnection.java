 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNSequence;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAConnection extends ASNSequence
 {
   CSTAConnectionID party;
   CSTAExtendedDeviceID staticDevice;
 
   public CSTAConnection()
   {
   }
 
   public CSTAConnection(CSTAConnectionID _party, CSTAExtendedDeviceID _staticDevice)
   {
     this.party = _party;
     this.staticDevice = _staticDevice;
   }
 
   public static CSTAConnection decode(InputStream in)
   {
     CSTAConnection _this = new CSTAConnection();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.party = CSTAConnectionID.decode(memberStream);
     this.staticDevice = CSTAExtendedDeviceID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.party, memberStream);
     CSTAExtendedDeviceID.encode(this.staticDevice, memberStream);
   }
 
   public static Collection<String> print(CSTAConnection _this, String name, String _indent)
   {
     Collection lines = new ArrayList();
     if (_this == null)
     {
       lines.add(_indent + name + " <null>");
       return lines;
     }
     if (name != null) {
       lines.add(_indent + name);
     }
     lines.add(_indent + "{");
 
     String indent = _indent + "  ";
 
     lines.addAll(CSTAConnectionID.print(_this.party, "party", indent));
     lines.addAll(CSTAExtendedDeviceID.print(_this.staticDevice, "staticDevice", indent));
 
     lines.add(_indent + "}");
     return lines;
   }
 
   public CSTAConnectionID getParty()
   {
     return this.party;
   }
 
   public CSTAExtendedDeviceID getStaticDevice()
   {
     return this.staticDevice;
   }
 
   public void setParty(CSTAConnectionID _party) {
     this.party = _party;
   }
 
   public void setStaticDevice(CSTAExtendedDeviceID _staticDevice) {
     this.staticDevice = _staticDevice;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConnection
 * JD-Core Version:    0.5.4
 */