/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAReRouteRequest extends CSTARequest
/*    */ {
/*    */   int routeRegisterReqID;
/*    */   int routingCrossRefID;
/*    */   public static final int PDU = 85;
/*    */ 
/*    */   public static CSTAReRouteRequest decode(InputStream in)
/*    */   {
/* 17 */     CSTAReRouteRequest _this = new CSTAReRouteRequest();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
/* 26 */     this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 31 */     Collection lines = new ArrayList();
/*    */ 
/* 33 */     lines.add("CSTAReRouteRequest ::=");
/* 34 */     lines.add("{");
/*    */ 
/* 36 */     String indent = "  ";
/*    */ 
/* 38 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/* 39 */     lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID, "routingCrossRefID", indent));
/*    */ 
/* 41 */     lines.add("}");
/* 42 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 47 */     return 85;
/*    */   }
/*    */ 
/*    */   public int getRouteRegisterReqID()
/*    */   {
/* 53 */     return this.routeRegisterReqID;
/*    */   }
/*    */ 
/*    */   public int getRoutingCrossRefID()
/*    */   {
/* 61 */     return this.routingCrossRefID;
/*    */   }
/*    */ 
/*    */   public void setRouteRegisterReqID(int routeRegisterReqID) {
/* 65 */     this.routeRegisterReqID = routeRegisterReqID;
/*    */   }
/*    */ 
/*    */   public void setRoutingCrossRefID(int routingCrossRefID) {
/* 69 */     this.routingCrossRefID = routingCrossRefID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAReRouteRequest
 * JD-Core Version:    0.5.4
 */