/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTARouteUsedExtEventReport extends CSTAEventReport
/*     */ {
/*     */   int routeRegisterReqID;
/*     */   int routingCrossRefID;
/*     */   CSTAExtendedDeviceID routeUsed;
/*     */   CSTAExtendedDeviceID callingDevice;
/*     */   boolean domain;
/*     */   public static final int PDU = 131;
/*     */ 
/*     */   public static CSTARouteUsedExtEventReport decode(InputStream in)
/*     */   {
/*  22 */     CSTARouteUsedExtEventReport _this = new CSTARouteUsedExtEventReport();
/*  23 */     _this.doDecode(in);
/*     */ 
/*  25 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  30 */     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
/*  31 */     this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
/*  32 */     this.routeUsed = CSTAExtendedDeviceID.decode(memberStream);
/*  33 */     this.callingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  34 */     this.domain = ASNBoolean.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  39 */     RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
/*  40 */     RoutingCrossRefID.encode(this.routingCrossRefID, memberStream);
/*  41 */     CSTAExtendedDeviceID.encode(this.routeUsed, memberStream);
/*  42 */     CSTAExtendedDeviceID.encode(this.callingDevice, memberStream);
/*  43 */     ASNBoolean.encode(this.domain, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  48 */     Collection lines = new ArrayList();
/*     */ 
/*  50 */     lines.add("CSTARouteUsedExtEventReport ::=");
/*  51 */     lines.add("{");
/*     */ 
/*  53 */     String indent = "  ";
/*     */ 
/*  55 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/*  56 */     lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID, "routingCrossRefID", indent));
/*  57 */     lines.addAll(CSTAExtendedDeviceID.print(this.routeUsed, "routeUsed", indent));
/*  58 */     lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice, "callingDevice", indent));
/*  59 */     lines.addAll(ASNBoolean.print(this.domain, "domain", indent));
/*     */ 
/*  61 */     lines.add("}");
/*  62 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  67 */     return 131;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCallingDevice()
/*     */   {
/*  73 */     return this.callingDevice;
/*     */   }
/*     */ 
/*     */   public boolean isDomain()
/*     */   {
/*  81 */     return this.domain;
/*     */   }
/*     */ 
/*     */   public int getRouteRegisterReqID()
/*     */   {
/*  89 */     return this.routeRegisterReqID;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getRouteUsed()
/*     */   {
/*  97 */     return this.routeUsed;
/*     */   }
/*     */ 
/*     */   public int getRoutingCrossRefID()
/*     */   {
/* 105 */     return this.routingCrossRefID;
/*     */   }
/*     */ 
/*     */   public void setCallingDevice(CSTAExtendedDeviceID callingDevice) {
/* 109 */     this.callingDevice = callingDevice;
/*     */   }
/*     */ 
/*     */   public void setDomain(boolean domain) {
/* 113 */     this.domain = domain;
/*     */   }
/*     */ 
/*     */   public void setRouteRegisterReqID(int routeRegisterReqID) {
/* 117 */     this.routeRegisterReqID = routeRegisterReqID;
/*     */   }
/*     */ 
/*     */   public void setRouteUsed(CSTAExtendedDeviceID routeUsed) {
/* 121 */     this.routeUsed = routeUsed;
/*     */   }
/*     */ 
/*     */   public void setRoutingCrossRefID(int routingCrossRefID) {
/* 125 */     this.routingCrossRefID = routingCrossRefID;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteUsedExtEventReport
 * JD-Core Version:    0.5.4
 */