/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentV5OriginalCallInfo extends LucentOriginalCallInfo
/*     */ {
/*     */   String ucid;
/*     */   CSTACallOriginatorInfo callOriginatorInfo;
/*     */   boolean flexibleBilling;
/*     */ 
/*     */   public String getUCID()
/*     */   {
/*  24 */     return this.ucid;
/*     */   }
/*     */ 
/*     */   public void setUCID(String _ucid)
/*     */   {
/*  32 */     this.ucid = _ucid;
/*     */   }
/*     */ 
/*     */   public int getCallOriginatorType()
/*     */   {
/*  69 */     if (hasCallOriginatorType()) {
/*  70 */       return this.callOriginatorInfo.getCallOriginatorType();
/*     */     }
/*  72 */     return -1;
/*     */   }
/*     */ 
/*     */   public boolean hasCallOriginatorType()
/*     */   {
/*  80 */     return this.callOriginatorInfo != null;
/*     */   }
/*     */ 
/*     */   public boolean canSetBillRate()
/*     */   {
/*  89 */     return this.flexibleBilling;
/*     */   }
/*     */ 
/*     */   public static LucentOriginalCallInfo decode(InputStream in)
/*     */   {
/*  94 */     LucentV5OriginalCallInfo _this = new LucentV5OriginalCallInfo();
/*  95 */     _this.doDecode(in);
/*  96 */     if ((_this.callingDevice_asn == null) && (_this.calledDevice_asn == null) && (_this.trunkGroup == null) && (_this.trunkMember == null) && (_this.lookaheadInfo == null) && (_this.userEnteredCode == null) && (_this.userInfo == null) && (_this.ucid == null) && (_this.callOriginatorInfo == null))
/*     */     {
/* 101 */       return null;
/*     */     }
/* 103 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/* 108 */     super.encodeMembers(memberStream);
/* 109 */     UCID.encode(this.ucid, memberStream);
/* 110 */     CSTACallOriginatorInfo.encode(this.callOriginatorInfo, memberStream);
/* 111 */     ASNBoolean.encode(this.flexibleBilling, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/* 116 */     super.decodeMembers(memberStream);
/* 117 */     this.ucid = UCID.decode(memberStream);
/* 118 */     this.callOriginatorInfo = CSTACallOriginatorInfo.decode(memberStream);
/* 119 */     this.flexibleBilling = ASNBoolean.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public LucentLookaheadInfo decodeLookahead(InputStream memberStream)
/*     */   {
/* 124 */     return LucentV5LookaheadInfo.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public static Collection<String> print(LucentV5OriginalCallInfo _this, String name, String _indent)
/*     */   {
/* 129 */     Collection lines = new ArrayList();
/*     */ 
/* 131 */     if (_this == null)
/*     */     {
/* 133 */       lines.add(_indent + name + " <null>");
/* 134 */       return lines;
/*     */     }
/* 136 */     if (name != null) {
/* 137 */       lines.add(_indent + name);
/*     */     }
/* 139 */     lines.add(_indent + "{");
/*     */ 
/* 141 */     String indent = _indent + "  ";
/*     */ 
/* 143 */     lines.addAll(ReasonForCallInfo.print(_this.reason, "reason", indent));
/* 144 */     lines.addAll(CSTAExtendedDeviceID.print(_this.callingDevice_asn, "callingDevice", indent));
/* 145 */     lines.addAll(CSTAExtendedDeviceID.print(_this.calledDevice_asn, "calledDevice", indent));
/* 146 */     lines.addAll(DeviceID.print(_this.trunkGroup, "trunkGroup", indent));
/* 147 */     lines.addAll(DeviceID.print(_this.trunkMember, "trunkMember", indent));
/* 148 */     lines.addAll(LucentV5LookaheadInfo.print((LucentV5LookaheadInfo)_this.lookaheadInfo, "lookaheadInfo", indent));
/* 149 */     lines.addAll(LucentUserEnteredCode.print(_this.userEnteredCode, "userEnteredCode", indent));
/* 150 */     lines.addAll(LucentUserToUserInfo.print(_this.userInfo, "userInfo", indent));
/* 151 */     lines.addAll(UCID.print(_this.ucid, "ucid", indent));
/* 152 */     lines.addAll(CSTACallOriginatorInfo.print(_this.callOriginatorInfo, "callOriginatorInfo", indent));
/* 153 */     lines.addAll(ASNBoolean.print(_this.flexibleBilling, "flexibleBilling", indent));
/*     */ 
/* 155 */     lines.add(_indent + "}");
/* 156 */     return lines;
/*     */   }
/*     */ 
/*     */   public void setCallOriginatorInfo(CSTACallOriginatorInfo _callOriginatorInfo) {
/* 160 */     this.callOriginatorInfo = _callOriginatorInfo;
/*     */   }
/*     */ 
/*     */   public CSTACallOriginatorInfo getCallOriginatorInfo()
/*     */   {
/* 167 */     return this.callOriginatorInfo;
/*     */   }
/*     */ 
/*     */   public void setFlexibleBilling(boolean flexibleBilling)
/*     */   {
/* 174 */     this.flexibleBilling = flexibleBilling;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5OriginalCallInfo
 * JD-Core Version:    0.5.4
 */