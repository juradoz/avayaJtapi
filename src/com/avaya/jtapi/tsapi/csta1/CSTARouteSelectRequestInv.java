/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTARouteSelectRequestInv extends CSTARequest
/*     */ {
/*     */   int routeRegisterReqID;
/*     */   int routingCrossRefID;
/*     */   String routeSelected;
/*     */   int remainRetry;
/*     */   byte[] setupInformation;
/*     */   boolean routeUsedReq;
/*     */   public static final int PDU = 132;
/*     */ 
/*     */   public CSTARouteSelectRequestInv()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CSTARouteSelectRequestInv(int _routeRegisterReqID, int _routingCrossRefID, String _routeSelected, int _remainRetry, byte[] _setupInformation, boolean _routeUsedReq)
/*     */   {
/*  29 */     this.routeRegisterReqID = _routeRegisterReqID;
/*  30 */     this.routingCrossRefID = _routingCrossRefID;
/*  31 */     this.routeSelected = _routeSelected;
/*  32 */     this.remainRetry = _remainRetry;
/*  33 */     this.setupInformation = _setupInformation;
/*  34 */     this.routeUsedReq = _routeUsedReq;
/*     */   }
/*     */ 
/*     */   public static CSTARouteSelectRequestInv decode(InputStream in)
/*     */   {
/*  39 */     CSTARouteSelectRequestInv _this = new CSTARouteSelectRequestInv();
/*  40 */     _this.doDecode(in);
/*     */ 
/*  42 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  47 */     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
/*  48 */     this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
/*  49 */     this.routeSelected = DeviceID.decode(memberStream);
/*  50 */     this.remainRetry = RetryValue.decode(memberStream);
/*  51 */     this.setupInformation = SetUpValues.decode(memberStream);
/*  52 */     this.routeUsedReq = ASNBoolean.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  57 */     RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
/*  58 */     RoutingCrossRefID.encode(this.routingCrossRefID, memberStream);
/*  59 */     DeviceID.encode(this.routeSelected, memberStream);
/*  60 */     RetryValue.encode(this.remainRetry, memberStream);
/*  61 */     SetUpValues.encode(this.setupInformation, memberStream);
/*  62 */     ASNBoolean.encode(this.routeUsedReq, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  67 */     Collection lines = new ArrayList();
/*     */ 
/*  69 */     lines.add("CSTARouteSelectRequestInv ::=");
/*  70 */     lines.add("{");
/*     */ 
/*  72 */     String indent = "  ";
/*     */ 
/*  74 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/*  75 */     lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID, "routingCrossRefID", indent));
/*  76 */     lines.addAll(DeviceID.print(this.routeSelected, "routeSelected", indent));
/*  77 */     lines.addAll(RetryValue.print(this.remainRetry, "remainRetry", indent));
/*  78 */     lines.addAll(SetUpValues.print(this.setupInformation, "setupInformation", indent));
/*  79 */     lines.addAll(ASNBoolean.print(this.routeUsedReq, "routeUsedReq", indent));
/*     */ 
/*  81 */     lines.add("}");
/*  82 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  87 */     return 132;
/*     */   }
/*     */ 
/*     */   public int getRemainRetry()
/*     */   {
/*  93 */     return this.remainRetry;
/*     */   }
/*     */ 
/*     */   public int getRouteRegisterReqID()
/*     */   {
/* 101 */     return this.routeRegisterReqID;
/*     */   }
/*     */ 
/*     */   public String getRouteSelected()
/*     */   {
/* 109 */     return this.routeSelected;
/*     */   }
/*     */ 
/*     */   public boolean isRouteUsedReq()
/*     */   {
/* 117 */     return this.routeUsedReq;
/*     */   }
/*     */ 
/*     */   public int getRoutingCrossRefID()
/*     */   {
/* 125 */     return this.routingCrossRefID;
/*     */   }
/*     */ 
/*     */   public byte[] getSetupInformation()
/*     */   {
/* 133 */     return this.setupInformation;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteSelectRequestInv
 * JD-Core Version:    0.5.4
 */