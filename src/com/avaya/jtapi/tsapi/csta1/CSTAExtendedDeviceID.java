 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNSequence;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAExtendedDeviceID extends ASNSequence
 {
   String deviceID;
   short deviceIDType;
   short deviceIDStatus;
   public static final short DEVICE_IDENTIFIER = 0;
   public static final short IMPLICIT_PUBLIC = 20;
   public static final short EXPLICIT_PUBLIC_UNKNOWN = 30;
   public static final short EXPLICIT_PUBLIC_INTERNATIONAL = 31;
   public static final short EXPLICIT_PUBLIC_NATIONAL = 32;
   public static final short EXPLICIT_PUBLIC_NETWORK_SPECIFIC = 33;
   public static final short EXPLICIT_PUBLIC_SUBSCRIBER = 34;
   public static final short EXPLICIT_PUBLIC_ABBREVIATED = 35;
   public static final short IMPLICIT_PRIVATE = 40;
   public static final short EXPLICIT_PRIVATE_UNKNOWN = 50;
   public static final short EXPLICIT_PRIVATE_LEVEL3_REGIONAL_NUMBER = 51;
   public static final short EXPLICIT_PRIVATE_LEVEL2_REGIONAL_NUMBER = 52;
   public static final short EXPLICIT_PRIVATE_LEVEL1_REGIONAL_NUMBER = 53;
   public static final short EXPLICIT_PRIVATE_PTN_SPECIFIC_NUMBER = 54;
   public static final short EXPLICIT_PRIVATE_LOCAL_NUMBER = 55;
   public static final short EXPLICIT_PRIVATE_ABBREVIATED = 56;
   public static final short OTHER_PLAN = 60;
   public static final short TRUNK_IDENTIFIER = 70;
   public static final short TRUNK_GROUP_IDENTIFIER = 71;
   public static final short ID_PROVIDED = 0;
   public static final short ID_NOT_KNOWN = 1;
   public static final short ID_NOT_REQUIRED = 2;
 
   public CSTAExtendedDeviceID()
   {
   }
 
   public CSTAExtendedDeviceID(String _deviceID, short _deviceIDType, short _deviceIDStatus)
   {
     this.deviceID = _deviceID;
     this.deviceIDType = _deviceIDType;
     this.deviceIDStatus = _deviceIDStatus;
   }
 
   public String toString()
   {
     return "ExtendedDeviceID(" + this.deviceID + "," + this.deviceIDType + "," + this.deviceIDStatus + ")";
   }
 
   public static CSTAExtendedDeviceID decode(InputStream in)
   {
     CSTAExtendedDeviceID _this = new CSTAExtendedDeviceID();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.deviceID = DeviceID.decode(memberStream);
     this.deviceIDType = DeviceIDType.decode(memberStream);
     this.deviceIDStatus = DeviceIDStatus.decode(memberStream);
   }
 
   public static void encode(CSTAConnectionID _this, OutputStream out)
   {
     if (_this == null)
     {
       _this = new CSTAConnectionID();
     }
     _this.encode(out);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     DeviceID.encode(this.deviceID, memberStream);
     DeviceIDType.encode(this.deviceIDType, memberStream);
     DeviceIDStatus.encode(this.deviceIDStatus, memberStream);
   }
 
   public static Collection<String> print(CSTAExtendedDeviceID _this, String name, String _indent)
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
 
     lines.addAll(DeviceID.print(_this.deviceID, "deviceID", indent));
     lines.addAll(DeviceIDType.print(_this.deviceIDType, "deviceIDType", indent));
     lines.addAll(DeviceIDStatus.print(_this.deviceIDStatus, "deviceIDStatus", indent));
 
     lines.add(_indent + "}");
     return lines;
   }
 
   public String getDeviceID()
   {
     return this.deviceID;
   }
 
   public short getDeviceIDStatus()
   {
     return this.deviceIDStatus;
   }
 
   public short getDeviceIDType()
   {
     return this.deviceIDType;
   }
 
   public boolean hasPrivateDeviceIDType()
   {
     switch (getDeviceIDType())
     {
     case 0:
     case 40:
     case 50:
     case 51:
     case 52:
     case 53:
     case 54:
     case 55:
     case 56:
       return true;
     }
 
     return false;
   }
 
   public boolean hasPublicDeviceIDType()
   {
     switch (getDeviceIDType())
     {
     case 20:
     case 30:
     case 31:
     case 32:
     case 33:
     case 34:
     case 35:
     case 60:
     case 70:
       return true;
     }
 
     return false;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID
 * JD-Core Version:    0.5.4
 */