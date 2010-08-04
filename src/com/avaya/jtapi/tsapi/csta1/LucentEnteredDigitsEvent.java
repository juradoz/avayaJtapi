/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class LucentEnteredDigitsEvent extends LucentPrivateData
/*     */ {
/*     */   CSTAConnectionID connection_asn;
/*     */   String digits;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   static final int PDU = 38;
/*     */ 
/*     */   public static LucentEnteredDigitsEvent decode(InputStream in)
/*     */   {
/*  19 */     LucentEnteredDigitsEvent _this = new LucentEnteredDigitsEvent();
/*  20 */     _this.doDecode(in);
/*     */ 
/*  22 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  27 */     this.connection_asn = CSTAConnectionID.decode(memberStream);
/*  28 */     this.digits = ASNIA5String.decode(memberStream);
/*  29 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  30 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream) {
/*  34 */     CSTAConnectionID.encode(this.connection_asn, memberStream);
/*  35 */     ASNIA5String.encode(this.digits, memberStream);
/*  36 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  37 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print() {
/*  41 */     Collection lines = new ArrayList();
/*     */ 
/*  43 */     lines.add("LucentEnteredDigitsEvent ::=");
/*  44 */     lines.add("{");
/*     */ 
/*  46 */     String indent = "  ";
/*     */ 
/*  48 */     lines.addAll(CSTAConnectionID.print(this.connection_asn, "connection", indent));
/*  49 */     lines.addAll(ASNIA5String.print(this.digits, "digits", indent));
/*  50 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/*  51 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*     */ 
/*  53 */     lines.add("}");
/*  54 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  59 */     return 38;
/*     */   }
/*     */ 
/*     */   public short getCause()
/*     */   {
/*  65 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getConnection_asn()
/*     */   {
/*  72 */     return this.connection_asn;
/*     */   }
/*     */ 
/*     */   public String getDigits()
/*     */   {
/*  80 */     return this.digits;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/*  88 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public void setCause(short cause) {
/*  92 */     this.cause = cause;
/*     */   }
/*     */ 
/*     */   public void setConnection_asn(CSTAConnectionID connection_asn) {
/*  96 */     this.connection_asn = connection_asn;
/*     */   }
/*     */ 
/*     */   public void setDigits(String digits) {
/* 100 */     this.digits = digits;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short localConnectionInfo) {
/* 104 */     this.localConnectionInfo = localConnectionInfo;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentEnteredDigitsEvent
 * JD-Core Version:    0.5.4
 */