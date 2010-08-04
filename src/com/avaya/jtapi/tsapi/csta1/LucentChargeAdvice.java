/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentChargeAdvice extends LucentPrivateData
/*     */ {
/*     */   private CSTAConnectionID connection_asn;
/*     */   private String calledDevice_asn;
/*     */   private String chargingDevice_asn;
/*     */   private String trunkGroup;
/*     */   private String trunkMember;
/*     */   private short chargeType;
/*     */   private int charge;
/*     */   private short error;
/*     */   static final int PDU = 96;
/*     */ 
/*     */   public final int getCharge()
/*     */   {
/*  33 */     return this.charge;
/*     */   }
/*     */ 
/*     */   public final short getChargeType()
/*     */   {
/*  42 */     return this.chargeType;
/*     */   }
/*     */ 
/*     */   public final short getChargeError()
/*     */   {
/*  51 */     return this.error;
/*     */   }
/*     */ 
/*     */   public static LucentChargeAdvice decode(InputStream in)
/*     */   {
/*  58 */     LucentChargeAdvice _this = new LucentChargeAdvice();
/*  59 */     _this.doDecode(in);
/*     */ 
/*  61 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  66 */     this.connection_asn = CSTAConnectionID.decode(memberStream);
/*  67 */     this.calledDevice_asn = DeviceID.decode(memberStream);
/*  68 */     this.chargingDevice_asn = DeviceID.decode(memberStream);
/*  69 */     this.trunkGroup = DeviceID.decode(memberStream);
/*  70 */     this.trunkMember = DeviceID.decode(memberStream);
/*  71 */     this.chargeType = ChargeType.decode(memberStream);
/*  72 */     this.charge = ASNInteger.decode(memberStream);
/*  73 */     this.error = ChargeError.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  78 */     CSTAConnectionID.encode(this.connection_asn, memberStream);
/*  79 */     DeviceID.encode(this.calledDevice_asn, memberStream);
/*  80 */     DeviceID.encode(this.chargingDevice_asn, memberStream);
/*  81 */     DeviceID.encode(this.trunkGroup, memberStream);
/*  82 */     DeviceID.encode(this.trunkMember, memberStream);
/*  83 */     ChargeType.encode(this.chargeType, memberStream);
/*  84 */     ASNInteger.encode(this.charge, memberStream);
/*  85 */     ChargeError.encode(this.error, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/* 114 */     Collection lines = new ArrayList();
/*     */ 
/* 116 */     lines.add("LucentChargeAdviceEvent ::=");
/* 117 */     lines.add("{");
/*     */ 
/* 119 */     String indent = "  ";
/*     */ 
/* 121 */     lines.addAll(CSTAConnectionID.print(this.connection_asn, "connection", indent));
/* 122 */     lines.addAll(DeviceID.print(this.calledDevice_asn, "calledDevice", indent));
/* 123 */     lines.addAll(DeviceID.print(this.chargingDevice_asn, "chargingDevice", indent));
/* 124 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/* 125 */     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
/* 126 */     lines.addAll(ChargeType.print(this.chargeType, "chargeType", indent));
/* 127 */     lines.addAll(ASNInteger.print(this.charge, "charge", indent));
/* 128 */     lines.addAll(ChargeError.print(this.error, "error", indent));
/*     */ 
/* 130 */     lines.add("}");
/* 131 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/* 136 */     return 96;
/*     */   }
/*     */ 
/*     */   public String getCalledDevice_asn()
/*     */   {
/* 142 */     return this.calledDevice_asn;
/*     */   }
/*     */ 
/*     */   public String getChargingDevice_asn()
/*     */   {
/* 158 */     return this.chargingDevice_asn;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getConnection_asn()
/*     */   {
/* 166 */     return this.connection_asn;
/*     */   }
/*     */ 
/*     */   public short getError()
/*     */   {
/* 174 */     return this.error;
/*     */   }
/*     */ 
/*     */   public String getTrunkGroup()
/*     */   {
/* 182 */     return this.trunkGroup;
/*     */   }
/*     */ 
/*     */   public String getTrunkMember()
/*     */   {
/* 190 */     return this.trunkMember;
/*     */   }
/*     */ 
/*     */   public void setCalledDevice_asn(String calledDevice_asn) {
/* 194 */     this.calledDevice_asn = calledDevice_asn;
/*     */   }
/*     */ 
/*     */   public void setCharge(int charge) {
/* 198 */     this.charge = charge;
/*     */   }
/*     */ 
/*     */   public void setChargeType(short chargeType) {
/* 202 */     this.chargeType = chargeType;
/*     */   }
/*     */ 
/*     */   public void setChargingDevice_asn(String chargingDevice_asn) {
/* 206 */     this.chargingDevice_asn = chargingDevice_asn;
/*     */   }
/*     */ 
/*     */   public void setConnection_asn(CSTAConnectionID connection_asn) {
/* 210 */     this.connection_asn = connection_asn;
/*     */   }
/*     */ 
/*     */   public void setError(short error) {
/* 214 */     this.error = error;
/*     */   }
/*     */ 
/*     */   public void setTrunkGroup(String trunkGroup) {
/* 218 */     this.trunkGroup = trunkGroup;
/*     */   }
/*     */ 
/*     */   public void setTrunkMember(String trunkMember) {
/* 222 */     this.trunkMember = trunkMember;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentChargeAdvice
 * JD-Core Version:    0.5.4
 */