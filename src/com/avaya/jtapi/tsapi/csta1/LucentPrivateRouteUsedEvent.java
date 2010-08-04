/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentPrivateRouteUsedEvent extends LucentPrivateData
/*    */ {
/*    */   String destRoute_asn;
/*    */   public static final int PDU = 44;
/*    */ 
/*    */   static LucentPrivateRouteUsedEvent decode(InputStream in)
/*    */   {
/* 17 */     LucentPrivateRouteUsedEvent _this = new LucentPrivateRouteUsedEvent();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.destRoute_asn = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 30 */     DeviceID.encode(this.destRoute_asn, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 36 */     Collection lines = new ArrayList();
/* 37 */     lines.add("LucentPrivateRouteUsedEvent ::=");
/* 38 */     lines.add("{");
/*    */ 
/* 40 */     String indent = "  ";
/*    */ 
/* 42 */     lines.addAll(DeviceID.print(this.destRoute_asn, "destRoute", indent));
/*    */ 
/* 44 */     lines.add("}");
/* 45 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 50 */     return 44;
/*    */   }
/*    */   public String getDestRoute_asn() {
/* 53 */     return this.destRoute_asn;
/*    */   }
/*    */ 
/*    */   public void setDestRoute_asn(String destRoute_asn) {
/* 57 */     this.destRoute_asn = destRoute_asn;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentPrivateRouteUsedEvent
 * JD-Core Version:    0.5.4
 */