 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import com.avaya.jtapi.tsapi.asn1.ASNSequence;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAForwardingInfo extends ASNSequence
 {
   short forwardingType;
   boolean forwardingOn;
   String forwardDN;
 
   public CSTAForwardingInfo()
   {
   }
 
   public CSTAForwardingInfo(short _forwardingType, boolean _forwardingOn, String _forwardDN)
   {
     this.forwardingType = _forwardingType;
     this.forwardingOn = _forwardingOn;
     this.forwardDN = _forwardDN;
   }
 
   public static CSTAForwardingInfo decode(InputStream in)
   {
     CSTAForwardingInfo _this = new CSTAForwardingInfo();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.forwardingType = ForwardingType.decode(memberStream);
     this.forwardingOn = ASNBoolean.decode(memberStream);
     this.forwardDN = DeviceID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     ForwardingType.encode(this.forwardingType, memberStream);
     ASNBoolean.encode(this.forwardingOn, memberStream);
     DeviceID.encode(this.forwardDN, memberStream);
   }
 
   public static Collection<String> print(CSTAForwardingInfo _this, String name, String _indent)
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
 
     lines.addAll(ForwardingType.print(_this.forwardingType, "forwardingType", indent));
     lines.addAll(ASNBoolean.print(_this.forwardingOn, "forwardingOn", indent));
     lines.addAll(DeviceID.print(_this.forwardDN, "forwardDN", indent));
 
     lines.add(_indent + "}");
     return lines;
   }
 
   public String getForwardDN()
   {
     return this.forwardDN;
   }
 
   public boolean isForwardingOn()
   {
     return this.forwardingOn;
   }
 
   public short getForwardingType()
   {
     return this.forwardingType;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAForwardingInfo
 * JD-Core Version:    0.5.4
 */