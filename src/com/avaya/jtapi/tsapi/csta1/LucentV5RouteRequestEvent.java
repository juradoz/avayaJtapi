/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentV5RouteRequestEvent extends LucentRouteRequestEvent
/*     */   implements HasUCID
/*     */ {
/*     */   String ucid;
/*     */   CSTACallOriginatorInfo callOriginatorInfo;
/*     */   boolean flexibleBilling;
/*     */   public static final int PDU = 83;
/*     */ 
/*     */   public static LucentRouteRequestEvent decode(InputStream in)
/*     */   {
/*  18 */     LucentV5RouteRequestEvent _this = new LucentV5RouteRequestEvent();
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
/*     */   public LucentLookaheadInfo decodeLookahead(InputStream memberStream) {
/*  40 */     return LucentV5LookaheadInfo.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  45 */     Collection lines = new ArrayList();
/*     */ 
/*  47 */     lines.add("LucentV5RouteRequestEvent ::=");
/*  48 */     lines.add("{");
/*     */ 
/*  50 */     String indent = "  ";
/*     */ 
/*  52 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/*  53 */     lines.addAll(LucentV5LookaheadInfo.print((LucentV5LookaheadInfo)this.lookaheadInfo, "lookaheadInfo", indent));
/*  54 */     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
/*  55 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*  56 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/*  57 */     lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo, "callOriginatorInfo", indent));
/*  58 */     lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling", indent));
/*     */ 
/*  60 */     lines.add("}");
/*  61 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  66 */     return 83;
/*     */   }
/*     */ 
/*     */   public CSTACallOriginatorInfo getCallOriginatorInfo()
/*     */   {
/*  72 */     return this.callOriginatorInfo;
/*     */   }
/*     */ 
/*     */   public boolean isFlexibleBilling()
/*     */   {
/*  80 */     return this.flexibleBilling;
/*     */   }
/*     */ 
/*     */   public String getUcid()
/*     */   {
/*  88 */     return this.ucid;
/*     */   }
/*     */ 
/*     */   public void setCallOriginatorInfo(CSTACallOriginatorInfo callOriginatorInfo) {
/*  92 */     this.callOriginatorInfo = callOriginatorInfo;
/*     */   }
/*     */ 
/*     */   public void setFlexibleBilling(boolean flexibleBilling) {
/*  96 */     this.flexibleBilling = flexibleBilling;
/*     */   }
/*     */ 
/*     */   public void setUcid(String ucid) {
/* 100 */     this.ucid = ucid;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5RouteRequestEvent
 * JD-Core Version:    0.5.4
 */