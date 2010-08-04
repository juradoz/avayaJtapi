/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTARouteRegisterCancelConfEvent extends CSTAConfirmation
/*    */ {
/*    */   int routeRegisterReqID;
/*    */   public static final int PDU = 81;
/*    */ 
/*    */   public static CSTARouteRegisterCancelConfEvent decode(InputStream in)
/*    */   {
/* 17 */     CSTARouteRegisterCancelConfEvent _this = new CSTARouteRegisterCancelConfEvent();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
/*    */   }
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 28 */     RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 32 */     Collection lines = new ArrayList();
/*    */ 
/* 34 */     lines.add("CSTARouteRegisterCancelConfEvent ::=");
/* 35 */     lines.add("{");
/*    */ 
/* 37 */     String indent = "  ";
/*    */ 
/* 39 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/*    */ 
/* 41 */     lines.add("}");
/* 42 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 47 */     return 81;
/*    */   }
/*    */ 
/*    */   public int getRouteRegisterReqID()
/*    */   {
/* 53 */     return this.routeRegisterReqID;
/*    */   }
/*    */   public void setRouteRegisterReqID(int routeRegisterReqID) {
/* 56 */     this.routeRegisterReqID = routeRegisterReqID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterCancelConfEvent
 * JD-Core Version:    0.5.4
 */