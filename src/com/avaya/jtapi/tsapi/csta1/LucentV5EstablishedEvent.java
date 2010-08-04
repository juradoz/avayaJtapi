/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentV5EstablishedEvent extends LucentEstablishedEvent
/*     */   implements HasUCID
/*     */ {
/*     */   String ucid;
/*     */   CSTACallOriginatorInfo callOriginatorInfo;
/*     */   boolean flexibleBilling;
/*     */   static final int PDU = 81;
/*     */ 
/*     */   public static LucentEstablishedEvent decode(InputStream in)
/*     */   {
/*  18 */     LucentV5EstablishedEvent _this = new LucentV5EstablishedEvent();
/*  19 */     _this.doDecode(in);
/*     */ 
/*  21 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  26 */     super.decodeMembers(memberStream);
/*  27 */     this.ucid = UCID.decode(memberStream);
/*  28 */     this.callOriginatorInfo = CSTACallOriginatorInfo.decode(memberStream);
/*  29 */     this.flexibleBilling = ASNBoolean.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  34 */     super.encodeMembers(memberStream);
/*  35 */     UCID.encode(this.ucid, memberStream);
/*  36 */     CSTACallOriginatorInfo.encode(this.callOriginatorInfo, memberStream);
/*  37 */     ASNBoolean.encode(this.flexibleBilling, memberStream);
/*     */   }
/*     */ 
/*     */   public LucentLookaheadInfo decodeLookahead(InputStream memberStream)
/*     */   {
/*  42 */     return LucentV5LookaheadInfo.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeLookahead(LucentLookaheadInfo lookaheadInfo, OutputStream memberStream) {
/*  46 */     LucentV5LookaheadInfo.encode(lookaheadInfo, memberStream);
/*     */   }
/*     */ 
/*     */   public LucentOriginalCallInfo decodeOCI(InputStream memberStream)
/*     */   {
/*  51 */     return LucentV5OriginalCallInfo.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  56 */     Collection lines = new ArrayList();
/*     */ 
/*  58 */     lines.add("LucentV5EstablishedEvent ::=");
/*  59 */     lines.add("{");
/*     */ 
/*  61 */     String indent = "  ";
/*     */ 
/*  63 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/*  64 */     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
/*  65 */     lines.addAll(DeviceID.print(this.split_asn, "split", indent));
/*  66 */     lines.addAll(LucentV5LookaheadInfo.print((LucentV5LookaheadInfo)this.lookaheadInfo, "lookaheadInfo", indent));
/*  67 */     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
/*  68 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*  69 */     lines.addAll(LucentReasonCode.print(this.reason, "reason", indent));
/*  70 */     lines.addAll(LucentV5OriginalCallInfo.print((LucentV5OriginalCallInfo)this.originalCallInfo, "originalCallInfo", indent));
/*  71 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/*  72 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/*  73 */     lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo, "callOriginatorInfo", indent));
/*  74 */     lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling", indent));
/*     */ 
/*  76 */     lines.add("}");
/*  77 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  82 */     return 81;
/*     */   }
/*     */ 
/*     */   public CSTACallOriginatorInfo getCallOriginatorInfo()
/*     */   {
/*  88 */     return this.callOriginatorInfo;
/*     */   }
/*     */ 
/*     */   public boolean isFlexibleBilling()
/*     */   {
/*  96 */     return this.flexibleBilling;
/*     */   }
/*     */ 
/*     */   public String getUcid()
/*     */   {
/* 104 */     return this.ucid;
/*     */   }
/*     */ 
/*     */   public void setCallOriginatorInfo(CSTACallOriginatorInfo callOriginatorInfo) {
/* 108 */     this.callOriginatorInfo = callOriginatorInfo;
/*     */   }
/*     */ 
/*     */   public void setFlexibleBilling(boolean flexibleBilling) {
/* 112 */     this.flexibleBilling = flexibleBilling;
/*     */   }
/*     */ 
/*     */   public void setUcid(String ucid) {
/* 116 */     this.ucid = ucid;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5EstablishedEvent
 * JD-Core Version:    0.5.4
 */