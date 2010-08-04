/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTARouteSelectRequest extends CSTARequest
/*     */ {
/*     */   int routeRegisterReqID;
/*     */   int routingCrossRefID;
/*     */   String routeSelected;
/*     */   int remainRetry;
/*     */   byte[] setupInformation;
/*     */   boolean routeUsedReq;
/*     */   public static final int PDU = 84;
/*     */ 
/*     */   public CSTARouteSelectRequest()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CSTARouteSelectRequest(int _routeRegisterReqID, int _routingCrossRefID, String _routeSelected, int _remainRetry, byte[] _setupInformation, boolean _routeUsedReq)
/*     */   {
/*  29 */     this.routeRegisterReqID = _routeRegisterReqID;
/*  30 */     this.routingCrossRefID = _routingCrossRefID;
/*  31 */     this.routeSelected = _routeSelected;
/*  32 */     this.remainRetry = _remainRetry;
/*  33 */     this.setupInformation = _setupInformation;
/*  34 */     this.routeUsedReq = _routeUsedReq;
/*     */   }
/*     */ 
/*     */   public static CSTARouteSelectRequest decode(InputStream in) {
/*  38 */     CSTARouteSelectRequest _this = new CSTARouteSelectRequest();
/*  39 */     _this.doDecode(in);
/*     */ 
/*  41 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  46 */     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
/*  47 */     this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
/*  48 */     this.routeSelected = DeviceID.decode(memberStream);
/*  49 */     this.remainRetry = RetryValue.decode(memberStream);
/*  50 */     this.setupInformation = SetUpValues.decode(memberStream);
/*  51 */     this.routeUsedReq = ASNBoolean.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  56 */     RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
/*  57 */     RoutingCrossRefID.encode(this.routingCrossRefID, memberStream);
/*  58 */     DeviceID.encode(this.routeSelected, memberStream);
/*  59 */     RetryValue.encode(this.remainRetry, memberStream);
/*  60 */     SetUpValues.encode(this.setupInformation, memberStream);
/*  61 */     ASNBoolean.encode(this.routeUsedReq, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  66 */     Collection lines = new ArrayList();
/*     */ 
/*  68 */     lines.add("CSTARouteSelectRequest ::=");
/*  69 */     lines.add("{");
/*     */ 
/*  71 */     String indent = "  ";
/*     */ 
/*  73 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/*  74 */     lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID, "routingCrossRefID", indent));
/*  75 */     lines.addAll(DeviceID.print(this.routeSelected, "routeSelected", indent));
/*  76 */     lines.addAll(RetryValue.print(this.remainRetry, "remainRetry", indent));
/*  77 */     lines.addAll(SetUpValues.print(this.setupInformation, "setupInformation", indent));
/*  78 */     lines.addAll(ASNBoolean.print(this.routeUsedReq, "routeUsedReq", indent));
/*     */ 
/*  80 */     lines.add("}");
/*  81 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  86 */     return 84;
/*     */   }
/*     */ 
/*     */   public int getRemainRetry()
/*     */   {
/*  92 */     return this.remainRetry;
/*     */   }
/*     */ 
/*     */   public int getRouteRegisterReqID()
/*     */   {
/* 100 */     return this.routeRegisterReqID;
/*     */   }
/*     */ 
/*     */   public String getRouteSelected()
/*     */   {
/* 108 */     return this.routeSelected;
/*     */   }
/*     */ 
/*     */   public boolean isRouteUsedReq()
/*     */   {
/* 116 */     return this.routeUsedReq;
/*     */   }
/*     */ 
/*     */   public int getRoutingCrossRefID()
/*     */   {
/* 124 */     return this.routingCrossRefID;
/*     */   }
/*     */ 
/*     */   public byte[] getSetupInformation()
/*     */   {
/* 132 */     return this.setupInformation;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteSelectRequest
 * JD-Core Version:    0.5.4
 */