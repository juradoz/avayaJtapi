/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAExtendedDeviceID extends ASNSequence
/*     */ {
/*     */   String deviceID;
/*     */   short deviceIDType;
/*     */   short deviceIDStatus;
/*     */   public static final short DEVICE_IDENTIFIER = 0;
/*     */   public static final short IMPLICIT_PUBLIC = 20;
/*     */   public static final short EXPLICIT_PUBLIC_UNKNOWN = 30;
/*     */   public static final short EXPLICIT_PUBLIC_INTERNATIONAL = 31;
/*     */   public static final short EXPLICIT_PUBLIC_NATIONAL = 32;
/*     */   public static final short EXPLICIT_PUBLIC_NETWORK_SPECIFIC = 33;
/*     */   public static final short EXPLICIT_PUBLIC_SUBSCRIBER = 34;
/*     */   public static final short EXPLICIT_PUBLIC_ABBREVIATED = 35;
/*     */   public static final short IMPLICIT_PRIVATE = 40;
/*     */   public static final short EXPLICIT_PRIVATE_UNKNOWN = 50;
/*     */   public static final short EXPLICIT_PRIVATE_LEVEL3_REGIONAL_NUMBER = 51;
/*     */   public static final short EXPLICIT_PRIVATE_LEVEL2_REGIONAL_NUMBER = 52;
/*     */   public static final short EXPLICIT_PRIVATE_LEVEL1_REGIONAL_NUMBER = 53;
/*     */   public static final short EXPLICIT_PRIVATE_PTN_SPECIFIC_NUMBER = 54;
/*     */   public static final short EXPLICIT_PRIVATE_LOCAL_NUMBER = 55;
/*     */   public static final short EXPLICIT_PRIVATE_ABBREVIATED = 56;
/*     */   public static final short OTHER_PLAN = 60;
/*     */   public static final short TRUNK_IDENTIFIER = 70;
/*     */   public static final short TRUNK_GROUP_IDENTIFIER = 71;
/*     */   public static final short ID_PROVIDED = 0;
/*     */   public static final short ID_NOT_KNOWN = 1;
/*     */   public static final short ID_NOT_REQUIRED = 2;
/*     */ 
/*     */   public CSTAExtendedDeviceID()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID(String _deviceID, short _deviceIDType, short _deviceIDStatus)
/*     */   {
/*  94 */     this.deviceID = _deviceID;
/*  95 */     this.deviceIDType = _deviceIDType;
/*  96 */     this.deviceIDStatus = _deviceIDStatus;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 101 */     return "ExtendedDeviceID(" + this.deviceID + "," + this.deviceIDType + "," + this.deviceIDStatus + ")";
/*     */   }
/*     */ 
/*     */   public static CSTAExtendedDeviceID decode(InputStream in)
/*     */   {
/* 106 */     CSTAExtendedDeviceID _this = new CSTAExtendedDeviceID();
/* 107 */     _this.doDecode(in);
/*     */ 
/* 116 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/* 121 */     this.deviceID = DeviceID.decode(memberStream);
/* 122 */     this.deviceIDType = DeviceIDType.decode(memberStream);
/* 123 */     this.deviceIDStatus = DeviceIDStatus.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public static void encode(CSTAConnectionID _this, OutputStream out)
/*     */   {
/* 128 */     if (_this == null)
/*     */     {
/* 130 */       _this = new CSTAConnectionID();
/*     */     }
/* 132 */     _this.encode(out);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/* 137 */     DeviceID.encode(this.deviceID, memberStream);
/* 138 */     DeviceIDType.encode(this.deviceIDType, memberStream);
/* 139 */     DeviceIDStatus.encode(this.deviceIDStatus, memberStream);
/*     */   }
/*     */ 
/*     */   public static Collection<String> print(CSTAExtendedDeviceID _this, String name, String _indent)
/*     */   {
/* 144 */     Collection lines = new ArrayList();
/* 145 */     if (_this == null)
/*     */     {
/* 147 */       lines.add(_indent + name + " <null>");
/* 148 */       return lines;
/*     */     }
/* 150 */     if (name != null) {
/* 151 */       lines.add(_indent + name);
/*     */     }
/* 153 */     lines.add(_indent + "{");
/*     */ 
/* 155 */     String indent = _indent + "  ";
/*     */ 
/* 157 */     lines.addAll(DeviceID.print(_this.deviceID, "deviceID", indent));
/* 158 */     lines.addAll(DeviceIDType.print(_this.deviceIDType, "deviceIDType", indent));
/* 159 */     lines.addAll(DeviceIDStatus.print(_this.deviceIDStatus, "deviceIDStatus", indent));
/*     */ 
/* 161 */     lines.add(_indent + "}");
/* 162 */     return lines;
/*     */   }
/*     */ 
/*     */   public String getDeviceID()
/*     */   {
/* 169 */     return this.deviceID;
/*     */   }
/*     */ 
/*     */   public short getDeviceIDStatus()
/*     */   {
/* 177 */     return this.deviceIDStatus;
/*     */   }
/*     */ 
/*     */   public short getDeviceIDType()
/*     */   {
/* 185 */     return this.deviceIDType;
/*     */   }
/*     */ 
/*     */   public boolean hasPrivateDeviceIDType()
/*     */   {
/* 201 */     switch (getDeviceIDType())
/*     */     {
/*     */     case 0:
/*     */     case 40:
/*     */     case 50:
/*     */     case 51:
/*     */     case 52:
/*     */     case 53:
/*     */     case 54:
/*     */     case 55:
/*     */     case 56:
/* 211 */       return true;
/*     */     }
/*     */ 
/* 215 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean hasPublicDeviceIDType()
/*     */   {
/* 221 */     switch (getDeviceIDType())
/*     */     {
/*     */     case 20:
/*     */     case 30:
/*     */     case 31:
/*     */     case 32:
/*     */     case 33:
/*     */     case 34:
/*     */     case 35:
/*     */     case 60:
/*     */     case 70:
/* 231 */       return true;
/*     */     }
/*     */ 
/* 235 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID
 * JD-Core Version:    0.5.4
 */