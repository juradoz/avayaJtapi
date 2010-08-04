/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentQueryStationStatus extends LucentPrivateData
/*    */ {
/*    */   String device;
/*    */   static final int PDU = 22;
/*    */ 
/*    */   LucentQueryStationStatus(String _device)
/*    */   {
/* 16 */     this.device = _device;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 21 */     DeviceID.encode(this.device, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 26 */     Collection lines = new ArrayList();
/*    */ 
/* 28 */     lines.add("LucentQueryStationStatus ::=");
/* 29 */     lines.add("{");
/*    */ 
/* 31 */     String indent = "  ";
/*    */ 
/* 33 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/*    */ 
/* 35 */     lines.add("}");
/* 36 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 41 */     return 22;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryStationStatus
 * JD-Core Version:    0.5.4
 */