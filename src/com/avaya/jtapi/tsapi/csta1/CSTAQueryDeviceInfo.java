/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAQueryDeviceInfo extends CSTARequest
/*    */ {
/*    */   String device;
/*    */   public static final int PDU = 37;
/*    */ 
/*    */   public CSTAQueryDeviceInfo(String _device)
/*    */   {
/* 16 */     this.device = _device;
/*    */   }
/*    */   public CSTAQueryDeviceInfo() {
/*    */   }
/*    */ 
/*    */   public static CSTAQueryDeviceInfo decode(InputStream in) {
/* 22 */     CSTAQueryDeviceInfo _this = new CSTAQueryDeviceInfo();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 25 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 30 */     DeviceID.encode(this.device, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 34 */     this.device = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 39 */     Collection lines = new ArrayList();
/* 40 */     lines.add("CSTAQueryDeviceInfo ::=");
/* 41 */     lines.add("{");
/*    */ 
/* 43 */     String indent = "  ";
/*    */ 
/* 45 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/*    */ 
/* 47 */     lines.add("}");
/* 48 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 53 */     return 37;
/*    */   }
/*    */ 
/*    */   public String getDevice()
/*    */   {
/* 59 */     return this.device;
/*    */   }
/*    */   public void setDevice(String device) {
/* 62 */     this.device = device;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryDeviceInfo
 * JD-Core Version:    0.5.4
 */