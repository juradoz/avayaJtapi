/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentEstablishedEvent extends LucentPrivateData
/*     */ {
/*     */   String trunkGroup;
/*     */   String trunkMember;
/*     */   String split_asn;
/*     */   LucentLookaheadInfo lookaheadInfo;
/*     */   LucentUserEnteredCode userEnteredCode;
/*     */   LucentUserToUserInfo userInfo;
/*     */   short reason;
/*     */   LucentOriginalCallInfo originalCallInfo;
/*     */   CSTAExtendedDeviceID distributingDevice_asn;
/*     */   static final int PDU = 61;
/*     */ 
/*     */   static LucentEstablishedEvent decode(InputStream in)
/*     */   {
/*  22 */     LucentEstablishedEvent _this = new LucentEstablishedEvent();
/*  23 */     _this.doDecode(in);
/*     */ 
/*  25 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  30 */     this.trunkGroup = DeviceID.decode(memberStream);
/*  31 */     this.trunkMember = DeviceID.decode(memberStream);
/*  32 */     this.split_asn = DeviceID.decode(memberStream);
/*  33 */     this.lookaheadInfo = decodeLookahead(memberStream);
/*  34 */     this.userEnteredCode = LucentUserEnteredCode.decode(memberStream);
/*  35 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*  36 */     this.reason = LucentReasonCode.decode(memberStream);
/*  37 */     this.originalCallInfo = decodeOCI(memberStream);
/*  38 */     this.distributingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  43 */     DeviceID.encode(this.trunkGroup, memberStream);
/*  44 */     DeviceID.encode(this.trunkMember, memberStream);
/*  45 */     DeviceID.encode(this.split_asn, memberStream);
/*  46 */     encodeLookahead(this.lookaheadInfo, memberStream);
/*  47 */     LucentUserEnteredCode.encode(this.userEnteredCode, memberStream);
/*  48 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*  49 */     LucentReasonCode.encode(this.reason, memberStream);
/*  50 */     encodeOCI(this.originalCallInfo, memberStream);
/*  51 */     CSTAExtendedDeviceID.encode(this.distributingDevice_asn, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  56 */     Collection lines = new ArrayList();
/*     */ 
/*  58 */     lines.add("LucentEstablishedEvent ::=");
/*  59 */     lines.add("{");
/*     */ 
/*  61 */     String indent = "  ";
/*     */ 
/*  63 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/*  64 */     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
/*  65 */     lines.addAll(DeviceID.print(this.split_asn, "split", indent));
/*  66 */     lines.addAll(LucentLookaheadInfo.print(this.lookaheadInfo, "lookaheadInfo", indent));
/*  67 */     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
/*  68 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*  69 */     lines.addAll(LucentReasonCode.print(this.reason, "reason", indent));
/*  70 */     lines.addAll(LucentOriginalCallInfo.print(this.originalCallInfo, "originalCallInfo", indent));
/*  71 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/*     */ 
/*  73 */     lines.add("}");
/*  74 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  79 */     return 61;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getDistributingDevice_asn()
/*     */   {
/*  85 */     return this.distributingDevice_asn;
/*     */   }
/*     */ 
/*     */   public LucentLookaheadInfo getLookaheadInfo()
/*     */   {
/*  93 */     return this.lookaheadInfo;
/*     */   }
/*     */ 
/*     */   public LucentOriginalCallInfo getOriginalCallInfo()
/*     */   {
/* 101 */     return this.originalCallInfo;
/*     */   }
/*     */ 
/*     */   public short getReason()
/*     */   {
/* 109 */     return this.reason;
/*     */   }
/*     */ 
/*     */   public String getSplit_asn()
/*     */   {
/* 117 */     return this.split_asn;
/*     */   }
/*     */ 
/*     */   public String getTrunkGroup()
/*     */   {
/* 125 */     return this.trunkGroup;
/*     */   }
/*     */ 
/*     */   public String getTrunkMember()
/*     */   {
/* 133 */     return this.trunkMember;
/*     */   }
/*     */ 
/*     */   public LucentUserEnteredCode getUserEnteredCode()
/*     */   {
/* 141 */     return this.userEnteredCode;
/*     */   }
/*     */ 
/*     */   public LucentUserToUserInfo getUserInfo()
/*     */   {
/* 149 */     return this.userInfo;
/*     */   }
/*     */ 
/*     */   public void setDistributingDevice_asn(CSTAExtendedDeviceID distributingDevice_asn) {
/* 153 */     this.distributingDevice_asn = distributingDevice_asn;
/*     */   }
/*     */ 
/*     */   public void setLookaheadInfo(LucentLookaheadInfo lookaheadInfo) {
/* 157 */     this.lookaheadInfo = lookaheadInfo;
/*     */   }
/*     */ 
/*     */   public void setOriginalCallInfo(LucentOriginalCallInfo originalCallInfo) {
/* 161 */     this.originalCallInfo = originalCallInfo;
/*     */   }
/*     */ 
/*     */   public void setReason(short reason) {
/* 165 */     this.reason = reason;
/*     */   }
/*     */ 
/*     */   public void setSplit_asn(String split_asn) {
/* 169 */     this.split_asn = split_asn;
/*     */   }
/*     */ 
/*     */   public void setTrunkGroup(String trunkGroup) {
/* 173 */     this.trunkGroup = trunkGroup;
/*     */   }
/*     */ 
/*     */   public void setTrunkMember(String trunkMember) {
/* 177 */     this.trunkMember = trunkMember;
/*     */   }
/*     */ 
/*     */   public void setUserEnteredCode(LucentUserEnteredCode userEnteredCode) {
/* 181 */     this.userEnteredCode = userEnteredCode;
/*     */   }
/*     */ 
/*     */   public void setUserInfo(LucentUserToUserInfo userInfo) {
/* 185 */     this.userInfo = userInfo;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentEstablishedEvent
 * JD-Core Version:    0.5.4
 */