/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentV5DeliveredEvent extends LucentDeliveredEvent
/*     */   implements HasUCID
/*     */ {
/*     */   String ucid;
/*     */   CSTACallOriginatorInfo callOriginatorInfo;
/*     */   boolean flexibleBilling;
/*     */   static final int PDU = 80;
/*     */ 
/*     */   public static LucentDeliveredEvent decode(InputStream in)
/*     */   {
/*  18 */     LucentV5DeliveredEvent _this = new LucentV5DeliveredEvent();
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
/*     */   public void encodeMembers(OutputStream memberStream) {
/*  33 */     super.encodeMembers(memberStream);
/*  34 */     UCID.encode(this.ucid, memberStream);
/*  35 */     CSTACallOriginatorInfo.encode(this.callOriginatorInfo, memberStream);
/*  36 */     ASNBoolean.encode(this.flexibleBilling, memberStream);
/*     */   }
/*     */ 
/*     */   public LucentLookaheadInfo decodeLookahead(InputStream memberStream)
/*     */   {
/*  41 */     return LucentV5LookaheadInfo.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public LucentOriginalCallInfo decodeOCI(InputStream memberStream)
/*     */   {
/*  46 */     return LucentV5OriginalCallInfo.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  51 */     Collection lines = new ArrayList();
/*     */ 
/*  53 */     lines.add("LucentV5DeliveredEvent ::=");
/*  54 */     lines.add("{");
/*     */ 
/*  56 */     String indent = "  ";
/*     */ 
/*  58 */     lines.addAll(LucentDeliveredType.print(this.deliveredType, "deliveredType", indent));
/*  59 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/*  60 */     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
/*  61 */     lines.addAll(DeviceID.print(this.split_asn, "split", indent));
/*  62 */     lines.addAll(LucentV5LookaheadInfo.print((LucentV5LookaheadInfo)this.lookaheadInfo, "lookaheadInfo", indent));
/*  63 */     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
/*  64 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*  65 */     lines.addAll(LucentReasonCode.print(this.reason, "reason", indent));
/*  66 */     lines.addAll(LucentV5OriginalCallInfo.print((LucentV5OriginalCallInfo)this.originalCallInfo, "originalCallInfo", indent));
/*  67 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/*  68 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/*  69 */     lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo, "callOriginatorInfo", indent));
/*  70 */     lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling", indent));
/*     */ 
/*  72 */     lines.add("}");
/*  73 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  78 */     return 80;
/*     */   }
/*     */ 
/*     */   public CSTACallOriginatorInfo getCallOriginatorInfo()
/*     */   {
/*  84 */     return this.callOriginatorInfo;
/*     */   }
/*     */ 
/*     */   public boolean isFlexibleBilling()
/*     */   {
/*  92 */     return this.flexibleBilling;
/*     */   }
/*     */ 
/*     */   public String getUcid()
/*     */   {
/* 100 */     return this.ucid;
/*     */   }
/*     */ 
/*     */   public void setCallOriginatorInfo(CSTACallOriginatorInfo callOriginatorInfo) {
/* 104 */     this.callOriginatorInfo = callOriginatorInfo;
/*     */   }
/*     */ 
/*     */   public void setFlexibleBilling(boolean flexibleBilling) {
/* 108 */     this.flexibleBilling = flexibleBilling;
/*     */   }
/*     */ 
/*     */   public void setUcid(String ucid) {
/* 112 */     this.ucid = ucid;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5DeliveredEvent
 * JD-Core Version:    0.5.4
 */