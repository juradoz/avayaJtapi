/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTARouteRequestEv extends CSTARequest
/*     */ {
/*     */   int routeRegisterReqID;
/*     */   int routingCrossRefID;
/*     */   String currentRoute;
/*     */   String callingDevice;
/*     */   CSTAConnectionID routedCall;
/*     */   short routedSelAlgorithm;
/*     */   boolean priority;
/*     */   byte[] setupInformation;
/*     */   public static final int PDU = 83;
/*     */ 
/*     */   public static CSTARouteRequestEv decode(InputStream in)
/*     */   {
/*  24 */     CSTARouteRequestEv _this = new CSTARouteRequestEv();
/*  25 */     _this.doDecode(in);
/*     */ 
/*  27 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  32 */     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
/*  33 */     this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
/*  34 */     this.currentRoute = DeviceID.decode(memberStream);
/*  35 */     this.callingDevice = DeviceID.decode(memberStream);
/*  36 */     this.routedCall = CSTAConnectionID.decode(memberStream);
/*  37 */     this.routedSelAlgorithm = SelectValue.decode(memberStream);
/*  38 */     this.priority = ASNBoolean.decode(memberStream);
/*  39 */     this.setupInformation = SetUpValues.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  44 */     Collection lines = new ArrayList();
/*     */ 
/*  46 */     lines.add("CSTARouteRequestEv ::=");
/*  47 */     lines.add("{");
/*     */ 
/*  49 */     String indent = "  ";
/*     */ 
/*  51 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/*  52 */     lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID, "routingCrossRefID", indent));
/*  53 */     lines.addAll(DeviceID.print(this.currentRoute, "currentRoute", indent));
/*  54 */     lines.addAll(DeviceID.print(this.callingDevice, "callingDevice", indent));
/*  55 */     lines.addAll(CSTAConnectionID.print(this.routedCall, "routedCall", indent));
/*  56 */     lines.addAll(SelectValue.print(this.routedSelAlgorithm, "routedSelAlgorithm", indent));
/*  57 */     lines.addAll(ASNBoolean.print(this.priority, "priority", indent));
/*  58 */     lines.addAll(SetUpValues.print(this.setupInformation, "setupInformation", indent));
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
/*     */   public String getCallingDevice()
/*     */   {
/*  72 */     return this.callingDevice;
/*     */   }
/*     */ 
/*     */   public String getCurrentRoute()
/*     */   {
/*  80 */     return this.currentRoute;
/*     */   }
/*     */ 
/*     */   public boolean isPriority()
/*     */   {
/*  88 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getRoutedCall()
/*     */   {
/*  96 */     return this.routedCall;
/*     */   }
/*     */ 
/*     */   public short getRoutedSelAlgorithm()
/*     */   {
/* 104 */     return this.routedSelAlgorithm;
/*     */   }
/*     */ 
/*     */   public int getRouteRegisterReqID()
/*     */   {
/* 112 */     return this.routeRegisterReqID;
/*     */   }
/*     */ 
/*     */   public int getRoutingCrossRefID()
/*     */   {
/* 120 */     return this.routingCrossRefID;
/*     */   }
/*     */ 
/*     */   public byte[] getSetupInformation()
/*     */   {
/* 128 */     return this.setupInformation;
/*     */   }
/*     */ 
/*     */   public void setRouteRegisterReqID(int routeRegisterReqID) {
/* 132 */     this.routeRegisterReqID = routeRegisterReqID;
/*     */   }
/*     */ 
/*     */   public void setRoutingCrossRefID(int routingCrossRefID) {
/* 136 */     this.routingCrossRefID = routingCrossRefID;
/*     */   }
/*     */ 
/*     */   public void setCurrentRoute(String currentRoute) {
/* 140 */     this.currentRoute = currentRoute;
/*     */   }
/*     */ 
/*     */   public void setRoutedCall(CSTAConnectionID routedCall) {
/* 144 */     this.routedCall = routedCall;
/*     */   }
/*     */ 
/*     */   public void setRoutedSelAlgorithm(short routedSelAlgorithm) {
/* 148 */     this.routedSelAlgorithm = routedSelAlgorithm;
/*     */   }
/*     */ 
/*     */   public void setPriority(boolean priority) {
/* 152 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public void setCallingDevice(String callingDevice) {
/* 156 */     this.callingDevice = callingDevice;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteRequestEv
 * JD-Core Version:    0.5.4
 */