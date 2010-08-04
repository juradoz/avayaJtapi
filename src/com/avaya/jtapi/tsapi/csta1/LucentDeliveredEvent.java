/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentDeliveredEvent extends LucentPrivateData
/*     */ {
/*     */   short deliveredType;
/*     */   String trunkGroup;
/*     */   String trunkMember;
/*     */   String split_asn;
/*     */   LucentLookaheadInfo lookaheadInfo;
/*     */   LucentUserEnteredCode userEnteredCode;
/*     */   LucentUserToUserInfo userInfo;
/*     */   short reason;
/*     */   LucentOriginalCallInfo originalCallInfo;
/*     */   CSTAExtendedDeviceID distributingDevice_asn;
/*     */   static final int PDU = 60;
/*     */ 
/*     */   public static LucentDeliveredEvent decode(InputStream in)
/*     */   {
/*  26 */     LucentDeliveredEvent _this = new LucentDeliveredEvent();
/*  27 */     _this.doDecode(in);
/*     */ 
/*  29 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  34 */     this.deliveredType = LucentDeliveredType.decode(memberStream);
/*  35 */     this.trunkGroup = DeviceID.decode(memberStream);
/*  36 */     this.trunkMember = DeviceID.decode(memberStream);
/*  37 */     this.split_asn = DeviceID.decode(memberStream);
/*  38 */     this.lookaheadInfo = decodeLookahead(memberStream);
/*  39 */     this.userEnteredCode = LucentUserEnteredCode.decode(memberStream);
/*  40 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*  41 */     this.reason = LucentReasonCode.decode(memberStream);
/*  42 */     this.originalCallInfo = decodeOCI(memberStream);
/*  43 */     this.distributingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  48 */     LucentDeliveredType.encode(this.deliveredType, memberStream);
/*  49 */     DeviceID.encode(this.trunkGroup, memberStream);
/*  50 */     DeviceID.encode(this.trunkMember, memberStream);
/*  51 */     DeviceID.encode(this.split_asn, memberStream);
/*  52 */     encodeLookahead(this.lookaheadInfo, memberStream);
/*  53 */     LucentUserEnteredCode.encode(this.userEnteredCode, memberStream);
/*  54 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*  55 */     LucentReasonCode.encode(this.reason, memberStream);
/*  56 */     encodeOCI(this.originalCallInfo, memberStream);
/*  57 */     CSTAExtendedDeviceID.encode(this.distributingDevice_asn, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  62 */     Collection lines = new ArrayList();
/*     */ 
/*  64 */     lines.add("LucentDeliveredEvent ::=");
/*  65 */     lines.add("{");
/*     */ 
/*  67 */     String indent = "  ";
/*     */ 
/*  69 */     lines.addAll(LucentDeliveredType.print(this.deliveredType, "deliveredType", indent));
/*  70 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/*  71 */     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
/*  72 */     lines.addAll(DeviceID.print(this.split_asn, "split", indent));
/*  73 */     lines.addAll(LucentLookaheadInfo.print(this.lookaheadInfo, "lookaheadInfo", indent));
/*  74 */     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
/*  75 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*  76 */     lines.addAll(LucentReasonCode.print(this.reason, "reason", indent));
/*  77 */     lines.addAll(LucentOriginalCallInfo.print(this.originalCallInfo, "originalCallInfo", indent));
/*  78 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/*     */ 
/*  80 */     lines.add("}");
/*  81 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  86 */     return 60;
/*     */   }
/*     */ 
/*     */   public short getDeliveredType()
/*     */   {
/*  92 */     return this.deliveredType;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getDistributingDevice_asn()
/*     */   {
/* 100 */     return this.distributingDevice_asn;
/*     */   }
/*     */ 
/*     */   public LucentLookaheadInfo getLookaheadInfo()
/*     */   {
/* 108 */     return this.lookaheadInfo;
/*     */   }
/*     */ 
/*     */   public LucentOriginalCallInfo getOriginalCallInfo()
/*     */   {
/* 116 */     return this.originalCallInfo;
/*     */   }
/*     */ 
/*     */   public short getReason()
/*     */   {
/* 124 */     return this.reason;
/*     */   }
/*     */ 
/*     */   public String getSplit_asn()
/*     */   {
/* 132 */     return this.split_asn;
/*     */   }
/*     */ 
/*     */   public String getTrunkGroup()
/*     */   {
/* 140 */     return this.trunkGroup;
/*     */   }
/*     */ 
/*     */   public String getTrunkMember()
/*     */   {
/* 148 */     return this.trunkMember;
/*     */   }
/*     */ 
/*     */   public LucentUserEnteredCode getUserEnteredCode()
/*     */   {
/* 156 */     return this.userEnteredCode;
/*     */   }
/*     */ 
/*     */   public LucentUserToUserInfo getUserInfo()
/*     */   {
/* 164 */     return this.userInfo;
/*     */   }
/*     */ 
/*     */   public void setDeliveredType(short deliveredType) {
/* 168 */     this.deliveredType = deliveredType;
/*     */   }
/*     */ 
/*     */   public void setDistributingDevice_asn(CSTAExtendedDeviceID distributingDevice_asn) {
/* 172 */     this.distributingDevice_asn = distributingDevice_asn;
/*     */   }
/*     */ 
/*     */   public void setLookaheadInfo(LucentLookaheadInfo lookaheadInfo) {
/* 176 */     this.lookaheadInfo = lookaheadInfo;
/*     */   }
/*     */ 
/*     */   public void setOriginalCallInfo(LucentOriginalCallInfo originalCallInfo) {
/* 180 */     this.originalCallInfo = originalCallInfo;
/*     */   }
/*     */ 
/*     */   public void setReason(short reason) {
/* 184 */     this.reason = reason;
/*     */   }
/*     */ 
/*     */   public void setSplit_asn(String split_asn) {
/* 188 */     this.split_asn = split_asn;
/*     */   }
/*     */ 
/*     */   public void setTrunkGroup(String trunkGroup) {
/* 192 */     this.trunkGroup = trunkGroup;
/*     */   }
/*     */ 
/*     */   public void setTrunkMember(String trunkMember) {
/* 196 */     this.trunkMember = trunkMember;
/*     */   }
/*     */ 
/*     */   public void setUserEnteredCode(LucentUserEnteredCode userEnteredCode) {
/* 200 */     this.userEnteredCode = userEnteredCode;
/*     */   }
/*     */ 
/*     */   public void setUserInfo(LucentUserToUserInfo userInfo) {
/* 204 */     this.userInfo = userInfo;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentDeliveredEvent
 * JD-Core Version:    0.5.4
 */