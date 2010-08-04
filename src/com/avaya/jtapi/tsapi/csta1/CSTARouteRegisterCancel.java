/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTARouteRegisterCancel extends CSTARequest
/*    */ {
/*    */   int routeRegisterReqID;
/*    */   public static final int PDU = 80;
/*    */ 
/*    */   public CSTARouteRegisterCancel()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTARouteRegisterCancel(int _routeRegisterReqID)
/*    */   {
/* 18 */     this.routeRegisterReqID = _routeRegisterReqID;
/*    */   }
/*    */ 
/*    */   public static CSTARouteRegisterCancel decode(InputStream in) {
/* 22 */     CSTARouteRegisterCancel _this = new CSTARouteRegisterCancel();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 25 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 29 */     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 33 */     RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 38 */     Collection lines = new ArrayList();
/*    */ 
/* 40 */     lines.add("CSTARouteRegisterCancel ::=");
/* 41 */     lines.add("{");
/*    */ 
/* 43 */     String indent = "  ";
/*    */ 
/* 45 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/*    */ 
/* 47 */     lines.add("}");
/* 48 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 53 */     return 80;
/*    */   }
/*    */ 
/*    */   public int getRouteRegisterReqID()
/*    */   {
/* 59 */     return this.routeRegisterReqID;
/*    */   }
/*    */   public void setRouteRegisterReqID(int routeRegisterReqID) {
/* 62 */     this.routeRegisterReqID = routeRegisterReqID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterCancel
 * JD-Core Version:    0.5.4
 */