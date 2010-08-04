/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTARouteEndEventReport extends CSTAEventReport
/*    */ {
/*    */   int routeRegisterReqID;
/*    */   int routingCrossRefID;
/*    */   short cause;
/*    */   public static final int PDU = 87;
/*    */ 
/*    */   public static CSTARouteEndEventReport decode(InputStream in)
/*    */   {
/* 19 */     CSTARouteEndEventReport _this = new CSTARouteEndEventReport();
/* 20 */     _this.doDecode(in);
/*    */ 
/* 22 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 27 */     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
/* 28 */     this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
/* 29 */     this.cause = CSTAUniversalFailure.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 33 */     RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
/* 34 */     RoutingCrossRefID.encode(this.routingCrossRefID, memberStream);
/* 35 */     CSTAUniversalFailure.encode(this.cause, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 39 */     Collection lines = new ArrayList();
/*    */ 
/* 41 */     lines.add("CSTARouteEndEventReport ::=");
/* 42 */     lines.add("{");
/*    */ 
/* 44 */     String indent = "  ";
/*    */ 
/* 46 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/* 47 */     lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID, "routingCrossRefID", indent));
/* 48 */     lines.addAll(CSTAUniversalFailure.print(this.cause, "cause", indent));
/*    */ 
/* 50 */     lines.add("}");
/* 51 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 56 */     return 87;
/*    */   }
/*    */ 
/*    */   public short getCause()
/*    */   {
/* 62 */     return this.cause;
/*    */   }
/*    */ 
/*    */   public int getRouteRegisterReqID()
/*    */   {
/* 70 */     return this.routeRegisterReqID;
/*    */   }
/*    */ 
/*    */   public int getRoutingCrossRefID()
/*    */   {
/* 78 */     return this.routingCrossRefID;
/*    */   }
/*    */ 
/*    */   public void setCause(short cause) {
/* 82 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */   public void setRouteRegisterReqID(int routeRegisterReqID) {
/* 86 */     this.routeRegisterReqID = routeRegisterReqID;
/*    */   }
/*    */ 
/*    */   public void setRoutingCrossRefID(int routingCrossRefID) {
/* 90 */     this.routingCrossRefID = routingCrossRefID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteEndEventReport
 * JD-Core Version:    0.5.4
 */