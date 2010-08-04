/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTASetFwd extends CSTARequest
/*    */ {
/*    */   String device;
/*    */   CSTAForwardingInfo forward;
/*    */   public static final int PDU = 47;
/*    */ 
/*    */   public CSTASetFwd()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTASetFwd(String _device, CSTAForwardingInfo _forward)
/*    */   {
/* 17 */     this.device = _device;
/* 18 */     this.forward = _forward;
/*    */   }
/*    */ 
/*    */   public static CSTASetFwd decode(InputStream in)
/*    */   {
/* 23 */     CSTASetFwd _this = new CSTASetFwd();
/* 24 */     _this.doDecode(in);
/*    */ 
/* 26 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 31 */     this.device = DeviceID.decode(memberStream);
/* 32 */     this.forward = CSTAForwardingInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 37 */     DeviceID.encode(this.device, memberStream);
/* 38 */     CSTAForwardingInfo.encode(this.forward, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 43 */     Collection lines = new ArrayList();
/*    */ 
/* 45 */     lines.add("CSTASetFwd ::=");
/* 46 */     lines.add("{");
/*    */ 
/* 48 */     String indent = "  ";
/*    */ 
/* 50 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/* 51 */     lines.addAll(CSTAForwardingInfo.print(this.forward, "forward", indent));
/*    */ 
/* 53 */     lines.add("}");
/* 54 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 59 */     return 47;
/*    */   }
/*    */ 
/*    */   public String getDevice()
/*    */   {
/* 65 */     return this.device;
/*    */   }
/*    */ 
/*    */   public CSTAForwardingInfo getForward()
/*    */   {
/* 73 */     return this.forward;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASetFwd
 * JD-Core Version:    0.5.4
 */