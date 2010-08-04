/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTARouteRequestExtEv extends CSTARequest
/*     */ {
/*     */   int routeRegisterReqID;
/*     */   int routingCrossRefID;
/*     */   CSTAExtendedDeviceID currentRoute;
/*     */   CSTAExtendedDeviceID callingDevice;
/*     */   CSTAConnectionID routedCall;
/*     */   short routedSelAlgorithm;
/*     */   boolean priority;
/*     */   byte[] setupInformation;
/*     */   public static final int PDU = 130;
/*     */ 
/*     */   public static CSTARouteRequestExtEv decode(InputStream in)
/*     */   {
/*  25 */     CSTARouteRequestExtEv _this = new CSTARouteRequestExtEv();
/*  26 */     _this.doDecode(in);
/*     */ 
/*  28 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  33 */     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
/*  34 */     this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
/*  35 */     this.currentRoute = CSTAExtendedDeviceID.decode(memberStream);
/*  36 */     this.callingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  37 */     this.routedCall = CSTAConnectionID.decode(memberStream);
/*  38 */     this.routedSelAlgorithm = SelectValue.decode(memberStream);
/*  39 */     this.priority = ASNBoolean.decode(memberStream);
/*  40 */     this.setupInformation = SetUpValues.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream) {
/*  44 */     RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
/*  45 */     RoutingCrossRefID.encode(this.routingCrossRefID, memberStream);
/*  46 */     CSTAExtendedDeviceID.encode(this.currentRoute, memberStream);
/*  47 */     CSTAExtendedDeviceID.encode(this.callingDevice, memberStream);
/*  48 */     CSTAConnectionID.encode(this.routedCall, memberStream);
/*  49 */     SelectValue.encode(this.routedSelAlgorithm, memberStream);
/*  50 */     ASNBoolean.encode(this.priority, memberStream);
/*  51 */     SetUpValues.encode(this.setupInformation, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print() {
/*  55 */     Collection lines = new ArrayList();
/*     */ 
/*  57 */     lines.add("CSTARouteRequestExtEv ::=");
/*  58 */     lines.add("{");
/*     */ 
/*  60 */     String indent = "  ";
/*     */ 
/*  62 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/*  63 */     lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID, "routingCrossRefID", indent));
/*  64 */     lines.addAll(CSTAExtendedDeviceID.print(this.currentRoute, "currentRoute", indent));
/*  65 */     lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice, "callingDevice", indent));
/*  66 */     lines.addAll(CSTAConnectionID.print(this.routedCall, "routedCall", indent));
/*  67 */     lines.addAll(SelectValue.print(this.routedSelAlgorithm, "routedSelAlgorithm", indent));
/*  68 */     lines.addAll(ASNBoolean.print(this.priority, "priority", indent));
/*  69 */     lines.addAll(SetUpValues.print(this.setupInformation, "setupInformation", indent));
/*     */ 
/*  71 */     lines.add("}");
/*  72 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  77 */     return 130;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCallingDevice()
/*     */   {
/*  83 */     return this.callingDevice;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCurrentRoute()
/*     */   {
/*  91 */     return this.currentRoute;
/*     */   }
/*     */ 
/*     */   public boolean isPriority()
/*     */   {
/*  99 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getRoutedCall()
/*     */   {
/* 107 */     return this.routedCall;
/*     */   }
/*     */ 
/*     */   public short getRoutedSelAlgorithm()
/*     */   {
/* 115 */     return this.routedSelAlgorithm;
/*     */   }
/*     */ 
/*     */   public int getRouteRegisterReqID()
/*     */   {
/* 123 */     return this.routeRegisterReqID;
/*     */   }
/*     */ 
/*     */   public int getRoutingCrossRefID()
/*     */   {
/* 131 */     return this.routingCrossRefID;
/*     */   }
/*     */ 
/*     */   public byte[] getSetupInformation()
/*     */   {
/* 139 */     return this.setupInformation;
/*     */   }
/*     */ 
/*     */   public void setCallingDevice(CSTAExtendedDeviceID callingDevice) {
/* 143 */     this.callingDevice = callingDevice;
/*     */   }
/*     */ 
/*     */   public void setCurrentRoute(CSTAExtendedDeviceID currentRoute) {
/* 147 */     this.currentRoute = currentRoute;
/*     */   }
/*     */ 
/*     */   public void setPriority(boolean priority) {
/* 151 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public void setRoutedCall(CSTAConnectionID routedCall) {
/* 155 */     this.routedCall = routedCall;
/*     */   }
/*     */ 
/*     */   public void setRoutedSelAlgorithm(short routedSelAlgorithm) {
/* 159 */     this.routedSelAlgorithm = routedSelAlgorithm;
/*     */   }
/*     */ 
/*     */   public void setRouteRegisterReqID(int routeRegisterReqID) {
/* 163 */     this.routeRegisterReqID = routeRegisterReqID;
/*     */   }
/*     */ 
/*     */   public void setRoutingCrossRefID(int routingCrossRefID) {
/* 167 */     this.routingCrossRefID = routingCrossRefID;
/*     */   }
/*     */ 
/*     */   public void setSetupInformation(byte[] setupInformation) {
/* 171 */     this.setupInformation = setupInformation;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteRequestExtEv
 * JD-Core Version:    0.5.4
 */