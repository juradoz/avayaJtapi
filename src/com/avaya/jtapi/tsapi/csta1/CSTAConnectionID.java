 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import com.avaya.jtapi.tsapi.asn1.ASNSequence;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAConnectionID extends ASNSequence
   implements LucentConnectionID
 {
   public static final short STATIC_ID = 0;
   public static final short DYNAMIC_ID = 1;
   int callID;
   String deviceID;
   short devIDType;
 
   public CSTAConnectionID()
   {
   }
 
   public CSTAConnectionID(int _callID, String _deviceID, short _devIDType)
   {
     this.callID = _callID;
     this.deviceID = _deviceID;
     this.devIDType = _devIDType;
   }
 
   public int hashCode()
   {
     if (this.deviceID == null)
     {
       return this.callID + (this.devIDType << 31);
     }
 
     return this.callID + this.deviceID.hashCode() + (this.devIDType << 31);
   }
 
   public boolean equals(Object anObject)
   {
     if (anObject instanceof CSTAConnectionID)
     {
       CSTAConnectionID anotherConnID = (CSTAConnectionID)anObject;
       if (this.deviceID == null)
       {
         return (this.callID == anotherConnID.callID) && (this.devIDType == anotherConnID.devIDType) && (anotherConnID.deviceID == null);
       }
 
       return (this.callID == anotherConnID.callID) && (this.devIDType == anotherConnID.devIDType) && (this.deviceID.equals(anotherConnID.deviceID));
     }
 
     return false;
   }
 
   public String toString()
   {
     return "ConnectionID(" + this.callID + "," + this.deviceID + "," + this.devIDType + ")";
   }
 
   public static CSTAConnectionID decode(InputStream in)
   {
     CSTAConnectionID _this = new CSTAConnectionID();
     _this.doDecode(in);
 
     return _this;
   }
 
   public static void encode(CSTAConnectionID _this, OutputStream out)
   {
     if (_this == null)
     {
       _this = new CSTAConnectionID();
     }
     _this.encode(out);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.callID = ASNInteger.decode(memberStream);
     this.deviceID = DeviceID.decode(memberStream);
     this.devIDType = ConnectionIDDevice.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     ASNInteger.encode(this.callID, memberStream);
     DeviceID.encode(this.deviceID, memberStream);
     ConnectionIDDevice.encode(this.devIDType, memberStream);
   }
 
   public static Collection<String> print(CSTAConnectionID _this, String name, String _indent)
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
 
     lines.addAll(ASNInteger.print(_this.callID, "callID", indent));
     lines.addAll(DeviceID.print(_this.deviceID, "deviceID", indent));
     lines.addAll(ConnectionIDDevice.print(_this.devIDType, "devIDType", indent));
 
     lines.add(_indent + "}");
     return lines;
   }
 
   public static short getDYNAMIC_ID()
   {
     return 1;
   }
 
   public static short getSTATIC_ID()
   {
     return 0;
   }
 
   public int getCallID()
   {
     return this.callID;
   }
 
   public String getDeviceID()
   {
     return this.deviceID;
   }
 
   public int getDevIDType()
   {
     return this.devIDType;
   }
 
   public void setCallID(int callID)
   {
     this.callID = callID;
   }
 
   public void setDeviceID(String deviceID)
   {
     this.deviceID = deviceID;
   }
 
   public void setdevIDType(short devIDType)
   {
     this.devIDType = devIDType;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConnectionID
 * JD-Core Version:    0.5.4
 */