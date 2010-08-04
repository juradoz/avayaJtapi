/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTARouteRegisterAbortEventReport extends CSTAEventReport
/*    */ {
/*    */   int routeRegisterReqID;
/*    */   public static final int PDU = 82;
/*    */ 
/*    */   public static CSTARouteRegisterAbortEventReport decode(InputStream in)
/*    */   {
/* 17 */     CSTARouteRegisterAbortEventReport _this = new CSTARouteRegisterAbortEventReport();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 29 */     RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 33 */     Collection lines = new ArrayList();
/*    */ 
/* 35 */     lines.add("CSTARouteRegisterAbortEventReport ::=");
/* 36 */     lines.add("{");
/*    */ 
/* 38 */     String indent = "  ";
/*    */ 
/* 40 */     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
/*    */ 
/* 42 */     lines.add("}");
/* 43 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 48 */     return 82;
/*    */   }
/*    */ 
/*    */   public int getRouteRegisterReqID()
/*    */   {
/* 54 */     return this.routeRegisterReqID;
/*    */   }
/*    */ 
/*    */   public void setRouteRegisterReqID(int routeRegisterReqID) {
/* 58 */     this.routeRegisterReqID = routeRegisterReqID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterAbortEventReport
 * JD-Core Version:    0.5.4
 */