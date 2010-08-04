/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentQueryDeviceNameConfEvent extends LucentPrivateData
/*     */ {
/*     */   short deviceType;
/*     */   String device_asn;
/*     */   String name;
/*     */   static final int PDU = 50;
/*     */ 
/*     */   public static LucentQueryDeviceNameConfEvent decode(InputStream in)
/*     */   {
/*  20 */     LucentQueryDeviceNameConfEvent _this = new LucentQueryDeviceNameConfEvent();
/*  21 */     _this.doDecode(in);
/*     */ 
/*  24 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  29 */     this.deviceType = LucentDeviceType.decode(memberStream);
/*  30 */     this.device_asn = DeviceID.decode(memberStream);
/*  31 */     this.name = ASNIA5String.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream) {
/*  35 */     LucentDeviceType.encode(this.deviceType, memberStream);
/*  36 */     DeviceID.encode(this.device_asn, memberStream);
/*  37 */     ASNIA5String.encode(this.name, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  50 */     Collection lines = new ArrayList();
/*     */ 
/*  52 */     lines.add("LucentQueryDeviceNameConfEvent ::=");
/*  53 */     lines.add("{");
/*     */ 
/*  55 */     String indent = "  ";
/*     */ 
/*  57 */     lines.addAll(LucentDeviceType.print(this.deviceType, "deviceType", indent));
/*  58 */     lines.addAll(DeviceID.print(this.device_asn, "device", indent));
/*  59 */     lines.addAll(ASNIA5String.print(this.name, "name", indent));
/*     */ 
/*  61 */     lines.add("}");
/*  62 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  67 */     return 50;
/*     */   }
/*     */ 
/*     */   public String getDevice_asn()
/*     */   {
/*  81 */     return this.device_asn;
/*     */   }
/*     */ 
/*     */   public short getDeviceType()
/*     */   {
/*  89 */     return this.deviceType;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  97 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setDevice_asn(String device_asn) {
/* 101 */     this.device_asn = device_asn;
/*     */   }
/*     */ 
/*     */   public void setDeviceType(short deviceType) {
/* 105 */     this.deviceType = deviceType;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 109 */     this.name = name;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceNameConfEvent
 * JD-Core Version:    0.5.4
 */