/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTARouteRegisterReq extends CSTARequest
/*    */ {
/*    */   String routingDevice;
/*    */   public static final int PDU = 78;
/*    */ 
/*    */   public CSTARouteRegisterReq()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTARouteRegisterReq(String _routingDevice)
/*    */   {
/* 18 */     this.routingDevice = _routingDevice;
/*    */   }
/*    */ 
/*    */   public static CSTARouteRegisterReq decode(InputStream in)
/*    */   {
/* 23 */     CSTARouteRegisterReq _this = new CSTARouteRegisterReq();
/* 24 */     _this.doDecode(in);
/*    */ 
/* 26 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 30 */     this.routingDevice = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 35 */     DeviceID.encode(this.routingDevice, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 40 */     Collection lines = new ArrayList();
/*    */ 
/* 42 */     lines.add("CSTARouteRegisterReq ::=");
/* 43 */     lines.add("{");
/*    */ 
/* 45 */     String indent = "  ";
/*    */ 
/* 47 */     lines.addAll(DeviceID.print(this.routingDevice, "routingDevice", indent));
/*    */ 
/* 49 */     lines.add("}");
/* 50 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 55 */     return 78;
/*    */   }
/*    */ 
/*    */   public String getRoutingDevice()
/*    */   {
/* 61 */     return this.routingDevice;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterReq
 * JD-Core Version:    0.5.4
 */