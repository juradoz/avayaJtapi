/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTATrunkInfo extends ASNSequence
/*     */ {
/*     */   CSTAConnectionID connection_asn;
/*     */   String trunkGroup;
/*     */   String trunkMember;
/*     */ 
/*     */   public static CSTATrunkInfo decode(InputStream in)
/*     */   {
/*  26 */     CSTATrunkInfo _this = new CSTATrunkInfo();
/*  27 */     _this.doDecode(in);
/*  28 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  33 */     CSTAConnectionID.encode(this.connection_asn, memberStream);
/*  34 */     DeviceID.encode(this.trunkGroup, memberStream);
/*  35 */     DeviceID.encode(this.trunkMember, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  40 */     this.connection_asn = CSTAConnectionID.decode(memberStream);
/*  41 */     this.trunkGroup = DeviceID.decode(memberStream);
/*  42 */     this.trunkMember = DeviceID.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public static Collection<String> print(CSTATrunkInfo _this, String name, String _indent)
/*     */   {
/*  47 */     Collection lines = new ArrayList();
/*     */ 
/*  49 */     if (_this == null) {
/*  50 */       lines.add(_indent + name + " <null>");
/*  51 */       return lines;
/*     */     }
/*  53 */     if (name != null) {
/*  54 */       lines.add(_indent + name);
/*     */     }
/*  56 */     lines.add(_indent + "{");
/*     */ 
/*  58 */     String indent = _indent + "  ";
/*     */ 
/*  60 */     lines.addAll(CSTAConnectionID.print(_this.connection_asn, "connection", indent));
/*  61 */     lines.addAll(DeviceID.print(_this.trunkGroup, "trunkGroup", indent));
/*  62 */     lines.addAll(DeviceID.print(_this.trunkMember, "trunkMember", indent));
/*     */ 
/*  64 */     lines.add(_indent + "}");
/*  65 */     return lines;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getConnection_asn()
/*     */   {
/*  72 */     return this.connection_asn;
/*     */   }
/*     */ 
/*     */   public String getTrunkGroup()
/*     */   {
/*  80 */     return this.trunkGroup;
/*     */   }
/*     */ 
/*     */   public String getTrunkMember()
/*     */   {
/*  88 */     return this.trunkMember;
/*     */   }
/*     */ 
/*     */   public void setConnection_asn(CSTAConnectionID _connection_asn) {
/*  92 */     this.connection_asn = _connection_asn;
/*     */   }
/*     */ 
/*     */   public void setTrunkGroup(String _trunkGroup) {
/*  96 */     this.trunkGroup = _trunkGroup;
/*     */   }
/*     */ 
/*     */   public void setTrunkMember(String _trunkMember) {
/* 100 */     this.trunkMember = _trunkMember;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTATrunkInfo
 * JD-Core Version:    0.5.4
 */