/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentRouteRequestEvent extends LucentPrivateData
/*     */ {
/*     */   String trunkGroup;
/*     */   LucentLookaheadInfo lookaheadInfo;
/*     */   LucentUserEnteredCode userEnteredCode;
/*     */   LucentUserToUserInfo userInfo;
/*     */   static final int PDU = 42;
/*     */ 
/*     */   public static LucentRouteRequestEvent decode(InputStream in)
/*     */   {
/*  17 */     LucentRouteRequestEvent _this = new LucentRouteRequestEvent();
/*  18 */     _this.doDecode(in);
/*     */ 
/*  20 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  25 */     this.trunkGroup = DeviceID.decode(memberStream);
/*  26 */     this.lookaheadInfo = decodeLookahead(memberStream);
/*  27 */     this.userEnteredCode = LucentUserEnteredCode.decode(memberStream);
/*  28 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream) {
/*  32 */     super.encodeMembers(memberStream);
/*  33 */     DeviceID.encode(this.trunkGroup, memberStream);
/*  34 */     encodeLookahead(this.lookaheadInfo, memberStream);
/*  35 */     LucentUserEnteredCode.encode(this.userEnteredCode, memberStream);
/*  36 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print() {
/*  40 */     Collection lines = new ArrayList();
/*     */ 
/*  42 */     lines.add("LucentRouteRequestEvent ::=");
/*  43 */     lines.add("{");
/*     */ 
/*  45 */     String indent = "  ";
/*     */ 
/*  47 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/*  48 */     lines.addAll(LucentLookaheadInfo.print(this.lookaheadInfo, "lookaheadInfo", indent));
/*  49 */     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
/*  50 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*     */ 
/*  52 */     lines.add("}");
/*  53 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  58 */     return 42;
/*     */   }
/*     */ 
/*     */   public LucentLookaheadInfo getLookaheadInfo()
/*     */   {
/*  64 */     return this.lookaheadInfo;
/*     */   }
/*     */ 
/*     */   public String getTrunkGroup()
/*     */   {
/*  72 */     return this.trunkGroup;
/*     */   }
/*     */ 
/*     */   public LucentUserEnteredCode getUserEnteredCode()
/*     */   {
/*  80 */     return this.userEnteredCode;
/*     */   }
/*     */ 
/*     */   public LucentUserToUserInfo getUserInfo()
/*     */   {
/*  88 */     return this.userInfo;
/*     */   }
/*     */ 
/*     */   public void setLookaheadInfo(LucentLookaheadInfo lookaheadInfo) {
/*  92 */     this.lookaheadInfo = lookaheadInfo;
/*     */   }
/*     */ 
/*     */   public void setTrunkGroup(String trunkGroup) {
/*  96 */     this.trunkGroup = trunkGroup;
/*     */   }
/*     */ 
/*     */   public void setUserEnteredCode(LucentUserEnteredCode userEnteredCode) {
/* 100 */     this.userEnteredCode = userEnteredCode;
/*     */   }
/*     */ 
/*     */   public void setUserInfo(LucentUserToUserInfo userInfo) {
/* 104 */     this.userInfo = userInfo;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentRouteRequestEvent
 * JD-Core Version:    0.5.4
 */