 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNSequence;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTATrunkInfo extends ASNSequence
 {
   CSTAConnectionID connection_asn;
   String trunkGroup;
   String trunkMember;
 
   public static CSTATrunkInfo decode(InputStream in)
   {
     CSTATrunkInfo _this = new CSTATrunkInfo();
     _this.doDecode(in);
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.connection_asn, memberStream);
     DeviceID.encode(this.trunkGroup, memberStream);
     DeviceID.encode(this.trunkMember, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.connection_asn = CSTAConnectionID.decode(memberStream);
     this.trunkGroup = DeviceID.decode(memberStream);
     this.trunkMember = DeviceID.decode(memberStream);
   }
 
   public static Collection<String> print(CSTATrunkInfo _this, String name, String _indent)
   {
     Collection lines = new ArrayList();
 
     if (_this == null) {
       lines.add(_indent + name + " <null>");
       return lines;
     }
     if (name != null) {
       lines.add(_indent + name);
     }
     lines.add(_indent + "{");
 
     String indent = _indent + "  ";
 
     lines.addAll(CSTAConnectionID.print(_this.connection_asn, "connection", indent));
     lines.addAll(DeviceID.print(_this.trunkGroup, "trunkGroup", indent));
     lines.addAll(DeviceID.print(_this.trunkMember, "trunkMember", indent));
 
     lines.add(_indent + "}");
     return lines;
   }
 
   public CSTAConnectionID getConnection_asn()
   {
     return this.connection_asn;
   }
 
   public String getTrunkGroup()
   {
     return this.trunkGroup;
   }
 
   public String getTrunkMember()
   {
     return this.trunkMember;
   }
 
   public void setConnection_asn(CSTAConnectionID _connection_asn) {
     this.connection_asn = _connection_asn;
   }
 
   public void setTrunkGroup(String _trunkGroup) {
     this.trunkGroup = _trunkGroup;
   }
 
   public void setTrunkMember(String _trunkMember) {
     this.trunkMember = _trunkMember;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTATrunkInfo
 * JD-Core Version:    0.5.4
 */