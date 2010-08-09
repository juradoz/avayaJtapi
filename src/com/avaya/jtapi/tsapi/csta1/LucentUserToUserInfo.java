 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
 import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentUserToUserInfo extends LucentPrivateData
 {
   protected short type;
   protected byte[] data;
 
   public LucentUserToUserInfo(String _data)
   {
     this.type = 4;
     this.data = _data.getBytes();
   }
 
   public LucentUserToUserInfo(byte[] _data)
   {
     this.type = 0;
     this.data = _data;
   }
 
   public String getString()
   {
     return new String(this.data);
   }
 
   public byte[] getBytes()
   {
     return this.data;
   }
 
   public boolean isAscii()
   {
     return this.type == 4;
   }
 
   public LucentUserToUserInfo()
   {
     this.type = -1;
   }
 
   public LucentUserToUserInfo(byte[] _data, short _type)
   {
     this.type = _type; this.data = _data;
   }
 
   public static LucentUserToUserInfo decode(InputStream in) {
     LucentUserToUserInfo _this = new LucentUserToUserInfo();
     _this.doDecode(in);
     if ((_this.type == -1) || (_this.data == null))
     {
       return null;
     }
     return _this;
   }
 
   public static void encode(LucentUserToUserInfo _this, OutputStream out)
   {
     if (_this == null)
     {
       _this = new LucentUserToUserInfo();
     }
     _this.encode(out);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.type = UUIProtocolType.decode(memberStream);
     this.data = ASNOctetString.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     UUIProtocolType.encode(this.type, memberStream);
     ASNOctetString.encode(this.data, memberStream);
   }
 
   public static Collection<String> print(LucentUserToUserInfo _this, String name, String _indent)
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
 
     lines.addAll(UUIProtocolType.print(_this.type, "type", indent));
     if (_this.type == 4)
     {
       lines.addAll(ASNIA5String.print(new String(_this.data), "data", indent));
     }
     else
     {
       lines.addAll(ASNOctetString.print(_this.data, "data", indent));
     }
 
     lines.add(_indent + "}");
     return lines;
   }
 
   public short getType() {
     return this.type;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo
 * JD-Core Version:    0.5.4
 */