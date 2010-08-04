/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTARouteUsedEventReport extends CSTAEventReport
/*     */ {
/*     */   int routeRegisterReqID;
/*     */   int routingCrossRefID;
/*     */   String routeUsed;
/*     */   String callingDevice;
/*     */   boolean domain;
/*     */   public static final int PDU = 86;
/*     */ 
/*     */   public static CSTARouteUsedEventReport decode(InputStream in)
/*     */   {
/*  21 */     CSTARouteUsedEventReport _this = new CSTARouteUsedEventReport();
/*  22 */     _this.doDecode(in);
/*     */ 
/*  24 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  29 */     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
/*  30 */     this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
/*  31 */     this.routeUsed = DeviceID.decode(memberStream);
/*  32 */     this.callingDevice = DeviceID.decode(memberStream);
/*  33 */     this.domain = ASNBoolean.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  38 */     Collection lines = new ArrayList();
/*     */ 
/*  40 */     lines.add("CSTARouteUsedEventReport ::=");
/*  41 */     lines.add("{");
/*     */ 
/*  43 */     String indent = "  ";
/*     */ 
/*  45 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/*  46 */     lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID, "routingCrossRefID", indent));
/*  47 */     lines.addAll(DeviceID.print(this.routeUsed, "routeUsed", indent));
/*  48 */     lines.addAll(DeviceID.print(this.callingDevice, "callingDevice", indent));
/*  49 */     lines.addAll(ASNBoolean.print(this.domain, "domain", indent));
/*     */ 
/*  51 */     lines.add("}");
/*  52 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  57 */     return 86;
/*     */   }
/*     */ 
/*     */   public String getCallingDevice()
/*     */   {
/*  63 */     return this.callingDevice;
/*     */   }
/*     */ 
/*     */   public boolean isDomain()
/*     */   {
/*  71 */     return this.domain;
/*     */   }
/*     */ 
/*     */   public int getRouteRegisterReqID()
/*     */   {
/*  79 */     return this.routeRegisterReqID;
/*     */   }
/*     */ 
/*     */   public String getRouteUsed()
/*     */   {
/*  87 */     return this.routeUsed;
/*     */   }
/*     */ 
/*     */   public int getRoutingCrossRefID()
/*     */   {
/*  95 */     return this.routingCrossRefID;
/*     */   }
/*     */ 
/*     */   public void setCallingDevice(String callingDevice) {
/*  99 */     this.callingDevice = callingDevice;
/*     */   }
/*     */ 
/*     */   public void setDomain(boolean domain) {
/* 103 */     this.domain = domain;
/*     */   }
/*     */ 
/*     */   public void setRouteRegisterReqID(int routeRegisterReqID) {
/* 107 */     this.routeRegisterReqID = routeRegisterReqID;
/*     */   }
/*     */ 
/*     */   public void setRouteUsed(String routeUsed) {
/* 111 */     this.routeUsed = routeUsed;
/*     */   }
/*     */ 
/*     */   public void setRoutingCrossRefID(int routingCrossRefID) {
/* 115 */     this.routingCrossRefID = routingCrossRefID;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteUsedEventReport
 * JD-Core Version:    0.5.4
 */