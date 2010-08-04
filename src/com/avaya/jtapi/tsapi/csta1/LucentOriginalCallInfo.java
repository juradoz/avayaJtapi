/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentOriginalCallInfo extends LucentPrivateData
/*     */ {
/*     */   public static final short OR_NONE = 0;
/*     */   public static final short OR_CONSULTATION = 1;
/*     */   public static final short OR_CONFERENCED = 2;
/*     */   public static final short OR_TRANSFERRED = 3;
/*     */   public static final short OR_NEW_CALL = 4;
/*     */   short reason;
/*     */   CSTAExtendedDeviceID callingDevice_asn;
/*     */   CSTAExtendedDeviceID calledDevice_asn;
/*     */   String trunkGroup;
/*     */   String trunkMember;
/*     */   LucentLookaheadInfo lookaheadInfo;
/*     */   LucentUserEnteredCode userEnteredCode;
/*     */   LucentUserToUserInfo userInfo;
/*     */ 
/*     */   public short getReason()
/*     */   {
/*  43 */     return this.reason;
/*     */   }
/*     */ 
/*     */   public void setReason(short _reason) {
/*  47 */     this.reason = _reason;
/*     */   }
/*     */ 
/*     */   public LucentUserToUserInfo getUserToUserInfo()
/*     */   {
/*  55 */     return this.userInfo;
/*     */   }
/*     */ 
/*     */   public LucentLookaheadInfo getLookaheadInfo()
/*     */   {
/*  63 */     return this.lookaheadInfo;
/*     */   }
/*     */ 
/*     */   public LucentUserEnteredCode getUserEnteredCode()
/*     */   {
/*  71 */     return this.userEnteredCode;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCalledDevice_asn()
/*     */   {
/*  78 */     return this.calledDevice_asn;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCallingDevice_asn()
/*     */   {
/*  85 */     return this.callingDevice_asn;
/*     */   }
/*     */ 
/*     */   public String getTrunkGroup()
/*     */   {
/*  92 */     return this.trunkGroup;
/*     */   }
/*     */ 
/*     */   public String getTrunkMember()
/*     */   {
/*  99 */     return this.trunkMember;
/*     */   }
/*     */ 
/*     */   public static LucentOriginalCallInfo decode(InputStream in)
/*     */   {
/* 104 */     LucentOriginalCallInfo _this = new LucentOriginalCallInfo();
/* 105 */     _this.doDecode(in);
/* 106 */     if ((_this.callingDevice_asn == null) && (_this.calledDevice_asn == null) && (_this.trunkGroup == null) && (_this.trunkMember == null) && (_this.lookaheadInfo == null) && (_this.userEnteredCode == null) && (_this.userInfo == null))
/*     */     {
/* 111 */       return null;
/*     */     }
/* 113 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/* 119 */     ReasonForCallInfo.encode(this.reason, memberStream);
/* 120 */     CSTAExtendedDeviceID.encode(this.callingDevice_asn, memberStream);
/* 121 */     CSTAExtendedDeviceID.encode(this.calledDevice_asn, memberStream);
/* 122 */     DeviceID.encode(this.trunkGroup, memberStream);
/* 123 */     DeviceID.encode(this.trunkMember, memberStream);
/* 124 */     encodeLookahead(this.lookaheadInfo, memberStream);
/* 125 */     LucentUserEnteredCode.encode(this.userEnteredCode, memberStream);
/* 126 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/* 131 */     this.reason = ReasonForCallInfo.decode(memberStream);
/* 132 */     this.callingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
/* 133 */     this.calledDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
/* 134 */     this.trunkGroup = DeviceID.decode(memberStream);
/* 135 */     this.trunkMember = DeviceID.decode(memberStream);
/* 136 */     this.lookaheadInfo = decodeLookahead(memberStream);
/* 137 */     this.userEnteredCode = LucentUserEnteredCode.decode(memberStream);
/* 138 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public static Collection<String> print(LucentOriginalCallInfo _this, String name, String _indent)
/*     */   {
/* 143 */     Collection lines = new ArrayList();
/*     */ 
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
/* 157 */     lines.addAll(ReasonForCallInfo.print(_this.reason, "reason", indent));
/* 158 */     lines.addAll(CSTAExtendedDeviceID.print(_this.callingDevice_asn, "callingDevice", indent));
/* 159 */     lines.addAll(CSTAExtendedDeviceID.print(_this.calledDevice_asn, "calledDevice", indent));
/* 160 */     lines.addAll(DeviceID.print(_this.trunkGroup, "trunkGroup", indent));
/* 161 */     lines.addAll(DeviceID.print(_this.trunkMember, "trunkMember", indent));
/* 162 */     lines.addAll(LucentLookaheadInfo.print(_this.lookaheadInfo, "lookaheadInfo", indent));
/* 163 */     lines.addAll(LucentUserEnteredCode.print(_this.userEnteredCode, "userEnteredCode", indent));
/* 164 */     lines.addAll(LucentUserToUserInfo.print(_this.userInfo, "userInfo", indent));
/*     */ 
/* 166 */     lines.add(_indent + "}");
/* 167 */     return lines;
/*     */   }
/*     */ 
/*     */   public void setCalledDevice_asn(CSTAExtendedDeviceID _calledDevice_asn) {
/* 171 */     this.calledDevice_asn = _calledDevice_asn;
/*     */   }
/*     */   public void setCallingDevice_asn(CSTAExtendedDeviceID _callingDevice_asn) {
/* 174 */     this.callingDevice_asn = _callingDevice_asn;
/*     */   }
/*     */   public void setLookaheadInfo(LucentLookaheadInfo _lookaheadInfo) {
/* 177 */     this.lookaheadInfo = _lookaheadInfo;
/*     */   }
/*     */ 
/*     */   public void setTrunkGroup(String _trunkGroup) {
/* 181 */     this.trunkGroup = _trunkGroup;
/*     */   }
/*     */   public void setTrunkMember(String _trunkMember) {
/* 184 */     this.trunkMember = _trunkMember;
/*     */   }
/*     */   public void setUserEnteredCode(LucentUserEnteredCode _userEnteredCode) {
/* 187 */     this.userEnteredCode = _userEnteredCode;
/*     */   }
/*     */   public void setUserInfo(LucentUserToUserInfo _userInfo) {
/* 190 */     this.userInfo = _userInfo;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentOriginalCallInfo
 * JD-Core Version:    0.5.4
 */