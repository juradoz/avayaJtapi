/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTARouteEndRequestInv extends CSTARequest
/*    */ {
/*    */   int routeRegisterReqID;
/*    */   int routingCrossRefID;
/*    */   short errorValue;
/*    */   public static final int PDU = 133;
/*    */ 
/*    */   public CSTARouteEndRequestInv()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTARouteEndRequestInv(int _routeRegisterReqID, int _routingCrossRefID, short _errorValue)
/*    */   {
/* 22 */     this.routeRegisterReqID = _routeRegisterReqID;
/* 23 */     this.routingCrossRefID = _routingCrossRefID;
/* 24 */     this.errorValue = _errorValue;
/*    */   }
/*    */ 
/*    */   public static CSTARouteEndRequestInv decode(InputStream in) {
/* 28 */     CSTARouteEndRequestInv _this = new CSTARouteEndRequestInv();
/* 29 */     _this.doDecode(in);
/*    */ 
/* 31 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 36 */     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
/* 37 */     this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
/* 38 */     this.errorValue = CSTAUniversalFailure.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 42 */     RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
/* 43 */     RoutingCrossRefID.encode(this.routingCrossRefID, memberStream);
/* 44 */     CSTAUniversalFailure.encode(this.errorValue, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 49 */     Collection lines = new ArrayList();
/* 50 */     lines.add("CSTARouteEndRequestInv ::=");
/* 51 */     lines.add("{");
/*    */ 
/* 53 */     String indent = "  ";
/*    */ 
/* 55 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/* 56 */     lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID, "routingCrossRefID", indent));
/* 57 */     lines.addAll(CSTAUniversalFailure.print(this.errorValue, "errorValue", indent));
/*    */ 
/* 59 */     lines.add("}");
/* 60 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 65 */     return 133;
/*    */   }
/*    */ 
/*    */   public short getErrorValue()
/*    */   {
/* 71 */     return this.errorValue;
/*    */   }
/*    */ 
/*    */   public int getRouteRegisterReqID()
/*    */   {
/* 79 */     return this.routeRegisterReqID;
/*    */   }
/*    */ 
/*    */   public int getRoutingCrossRefID()
/*    */   {
/* 87 */     return this.routingCrossRefID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteEndRequestInv
 * JD-Core Version:    0.5.4
 */