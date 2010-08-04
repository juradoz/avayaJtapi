/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTARouteEndRequest extends CSTARequest
/*    */ {
/*    */   int routeRegisterReqID;
/*    */   int routingCrossRefID;
/*    */   short errorValue;
/*    */   public static final int PDU = 88;
/*    */ 
/*    */   public CSTARouteEndRequest()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTARouteEndRequest(int _routeRegisterReqID, int _routingCrossRefID, short _errorValue)
/*    */   {
/* 22 */     this.routeRegisterReqID = _routeRegisterReqID;
/* 23 */     this.routingCrossRefID = _routingCrossRefID;
/* 24 */     this.errorValue = _errorValue;
/*    */   }
/*    */ 
/*    */   public static CSTARouteEndRequest decode(InputStream in) {
/* 28 */     CSTARouteEndRequest _this = new CSTARouteEndRequest();
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
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 43 */     RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
/* 44 */     RoutingCrossRefID.encode(this.routingCrossRefID, memberStream);
/* 45 */     CSTAUniversalFailure.encode(this.errorValue, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 50 */     Collection lines = new ArrayList();
/* 51 */     lines.add("CSTARouteEndRequest ::=");
/* 52 */     lines.add("{");
/*    */ 
/* 54 */     String indent = "  ";
/*    */ 
/* 56 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/* 57 */     lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID, "routingCrossRefID", indent));
/* 58 */     lines.addAll(CSTAUniversalFailure.print(this.errorValue, "errorValue", indent));
/*    */ 
/* 60 */     lines.add("}");
/* 61 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 66 */     return 88;
/*    */   }
/*    */ 
/*    */   public short getErrorValue()
/*    */   {
/* 72 */     return this.errorValue;
/*    */   }
/*    */ 
/*    */   public int getRouteRegisterReqID()
/*    */   {
/* 80 */     return this.routeRegisterReqID;
/*    */   }
/*    */ 
/*    */   public int getRoutingCrossRefID()
/*    */   {
/* 88 */     return this.routingCrossRefID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteEndRequest
 * JD-Core Version:    0.5.4
 */