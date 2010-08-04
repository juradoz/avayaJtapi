/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTAGroupPickupCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID deflectCall;
/*    */   String pickupDevice;
/*    */   static final int PDU = 19;
/*    */ 
/*    */   public CSTAGroupPickupCall(CSTAConnectionID _deflectCall, String _pickupDevice)
/*    */   {
/* 18 */     this.deflectCall = _deflectCall;
/* 19 */     this.pickupDevice = _pickupDevice;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 24 */     CSTAConnectionID.encode(this.deflectCall, memberStream);
/* 25 */     DeviceID.encode(this.pickupDevice, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 30 */     Collection lines = new ArrayList();
/* 31 */     lines.add("CSTAGroupPickupCall ::=");
/* 32 */     lines.add("{");
/*    */ 
/* 34 */     String indent = "  ";
/*    */ 
/* 36 */     lines.addAll(CSTAConnectionID.print(this.deflectCall, "deflectCall", indent));
/* 37 */     lines.addAll(DeviceID.print(this.pickupDevice, "pickupDevice", indent));
/*    */ 
/* 39 */     lines.add("}");
/* 40 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 45 */     return 19;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getDeflectCall()
/*    */   {
/* 51 */     return this.deflectCall;
/*    */   }
/*    */ 
/*    */   public String getPickupDevice()
/*    */   {
/* 59 */     return this.pickupDevice;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAGroupPickupCall
 * JD-Core Version:    0.5.4
 */