/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentV5NetworkProgressInfo extends LucentNetworkProgressInfo
/*     */ {
/*     */   String trunkGroup;
/*     */   String trunkMember;
/*     */   public static final int PDU = 101;
/*     */ 
/*     */   public static LucentNetworkProgressInfo decode(InputStream in)
/*     */   {
/*  23 */     LucentV5NetworkProgressInfo _this = new LucentV5NetworkProgressInfo();
/*  24 */     _this.doDecode(in);
/*     */ 
/*  27 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  32 */     super.decodeMembers(memberStream);
/*  33 */     this.trunkGroup = DeviceID.decode(memberStream);
/*  34 */     this.trunkMember = DeviceID.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  39 */     super.encodeMembers(memberStream);
/*  40 */     DeviceID.encode(this.trunkGroup, memberStream);
/*  41 */     DeviceID.encode(this.trunkMember, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  66 */     Collection lines = new ArrayList();
/*  67 */     lines.add("V5NetworkProgressInfo ::=");
/*  68 */     lines.add("{");
/*     */ 
/*  70 */     String indent = "  ";
/*     */ 
/*  72 */     lines.addAll(ProgressLocation.print(this.progressLocation, "progressLocation", indent));
/*     */ 
/*  74 */     lines.addAll(ProgressDescription.print(this.progressDescription, "progressDescription", indent));
/*     */ 
/*  76 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/*  77 */     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
/*     */ 
/*  79 */     lines.add("}");
/*  80 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  85 */     return 101;
/*     */   }
/*     */ 
/*     */   public String getTrunkGroup()
/*     */   {
/*  91 */     return this.trunkGroup;
/*     */   }
/*     */ 
/*     */   public String getTrunkMember()
/*     */   {
/*  99 */     return this.trunkMember;
/*     */   }
/*     */ 
/*     */   public void setTrunkGroup(String trunkGroup) {
/* 103 */     this.trunkGroup = trunkGroup;
/*     */   }
/*     */ 
/*     */   public void setTrunkMember(String trunkMember) {
/* 107 */     this.trunkMember = trunkMember;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5NetworkProgressInfo
 * JD-Core Version:    0.5.4
 */