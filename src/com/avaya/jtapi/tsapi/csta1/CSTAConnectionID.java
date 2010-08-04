/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAConnectionID extends ASNSequence
/*     */   implements LucentConnectionID
/*     */ {
/*     */   public static final short STATIC_ID = 0;
/*     */   public static final short DYNAMIC_ID = 1;
/*     */   int callID;
/*     */   String deviceID;
/*     */   short devIDType;
/*     */ 
/*     */   public CSTAConnectionID()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID(int _callID, String _deviceID, short _devIDType)
/*     */   {
/*  34 */     this.callID = _callID;
/*  35 */     this.deviceID = _deviceID;
/*  36 */     this.devIDType = _devIDType;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  41 */     if (this.deviceID == null)
/*     */     {
/*  43 */       return this.callID + (this.devIDType << 31);
/*     */     }
/*     */ 
/*  47 */     return this.callID + this.deviceID.hashCode() + (this.devIDType << 31);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object anObject)
/*     */   {
/*  53 */     if (anObject instanceof CSTAConnectionID)
/*     */     {
/*  55 */       CSTAConnectionID anotherConnID = (CSTAConnectionID)anObject;
/*  56 */       if (this.deviceID == null)
/*     */       {
/*  58 */         return (this.callID == anotherConnID.callID) && (this.devIDType == anotherConnID.devIDType) && (anotherConnID.deviceID == null);
/*     */       }
/*     */ 
/*  64 */       return (this.callID == anotherConnID.callID) && (this.devIDType == anotherConnID.devIDType) && (this.deviceID.equals(anotherConnID.deviceID));
/*     */     }
/*     */ 
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  74 */     return "ConnectionID(" + this.callID + "," + this.deviceID + "," + this.devIDType + ")";
/*     */   }
/*     */ 
/*     */   public static CSTAConnectionID decode(InputStream in)
/*     */   {
/*  79 */     CSTAConnectionID _this = new CSTAConnectionID();
/*  80 */     _this.doDecode(in);
/*     */ 
/*  82 */     return _this;
/*     */   }
/*     */ 
/*     */   public static void encode(CSTAConnectionID _this, OutputStream out)
/*     */   {
/*  87 */     if (_this == null)
/*     */     {
/*  89 */       _this = new CSTAConnectionID();
/*     */     }
/*  91 */     _this.encode(out);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  96 */     this.callID = ASNInteger.decode(memberStream);
/*  97 */     this.deviceID = DeviceID.decode(memberStream);
/*  98 */     this.devIDType = ConnectionIDDevice.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/* 103 */     ASNInteger.encode(this.callID, memberStream);
/* 104 */     DeviceID.encode(this.deviceID, memberStream);
/* 105 */     ConnectionIDDevice.encode(this.devIDType, memberStream);
/*     */   }
/*     */ 
/*     */   public static Collection<String> print(CSTAConnectionID _this, String name, String _indent)
/*     */   {
/* 110 */     Collection lines = new ArrayList();
/* 111 */     if (_this == null)
/*     */     {
/* 113 */       lines.add(_indent + name + " <null>");
/* 114 */       return lines;
/*     */     }
/* 116 */     if (name != null) {
/* 117 */       lines.add(_indent + name);
/*     */     }
/* 119 */     lines.add(_indent + "{");
/*     */ 
/* 121 */     String indent = _indent + "  ";
/*     */ 
/* 123 */     lines.addAll(ASNInteger.print(_this.callID, "callID", indent));
/* 124 */     lines.addAll(DeviceID.print(_this.deviceID, "deviceID", indent));
/* 125 */     lines.addAll(ConnectionIDDevice.print(_this.devIDType, "devIDType", indent));
/*     */ 
/* 127 */     lines.add(_indent + "}");
/* 128 */     return lines;
/*     */   }
/*     */ 
/*     */   public static short getDYNAMIC_ID()
/*     */   {
/* 135 */     return 1;
/*     */   }
/*     */ 
/*     */   public static short getSTATIC_ID()
/*     */   {
/* 143 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getCallID()
/*     */   {
/* 151 */     return this.callID;
/*     */   }
/*     */ 
/*     */   public String getDeviceID()
/*     */   {
/* 159 */     return this.deviceID;
/*     */   }
/*     */ 
/*     */   public int getDevIDType()
/*     */   {
/* 166 */     return this.devIDType;
/*     */   }
/*     */ 
/*     */   public void setCallID(int callID)
/*     */   {
/* 173 */     this.callID = callID;
/*     */   }
/*     */ 
/*     */   public void setDeviceID(String deviceID)
/*     */   {
/* 180 */     this.deviceID = deviceID;
/*     */   }
/*     */ 
/*     */   public void setdevIDType(short devIDType)
/*     */   {
/* 187 */     this.devIDType = devIDType;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConnectionID
 * JD-Core Version:    0.5.4
 */