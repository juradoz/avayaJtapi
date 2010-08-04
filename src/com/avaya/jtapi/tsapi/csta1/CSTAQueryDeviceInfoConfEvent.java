/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAQueryDeviceInfoConfEvent extends CSTAConfirmation
/*    */ {
/*    */   String device;
/*    */   short deviceType;
/*    */   int deviceClass;
/*    */   public static final int PDU = 38;
/*    */ 
/*    */   public static CSTAQueryDeviceInfoConfEvent decode(InputStream in)
/*    */   {
/* 19 */     CSTAQueryDeviceInfoConfEvent _this = new CSTAQueryDeviceInfoConfEvent();
/* 20 */     _this.doDecode(in);
/*    */ 
/* 22 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 27 */     DeviceID.encode(this.device, memberStream);
/* 28 */     DeviceType.encode(this.deviceType, memberStream);
/* 29 */     DeviceClass.encode(this.deviceClass, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 34 */     this.device = DeviceID.decode(memberStream);
/* 35 */     this.deviceType = DeviceType.decode(memberStream);
/* 36 */     this.deviceClass = DeviceClass.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 41 */     Collection lines = new ArrayList();
/* 42 */     lines.add("CSTAQueryDeviceInfoConfEvent ::=");
/* 43 */     lines.add("{");
/*    */ 
/* 45 */     String indent = "  ";
/*    */ 
/* 47 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/* 48 */     lines.addAll(DeviceType.print(this.deviceType, "deviceType", indent));
/* 49 */     lines.addAll(DeviceClass.print(this.deviceClass, "deviceClass", indent));
/*    */ 
/* 51 */     lines.add("}");
/* 52 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 57 */     return 38;
/*    */   }
/*    */ 
/*    */   public String getDevice()
/*    */   {
/* 63 */     return this.device;
/*    */   }
/*    */ 
/*    */   public int getDeviceClass()
/*    */   {
/* 71 */     return this.deviceClass;
/*    */   }
/*    */ 
/*    */   public short getDeviceType()
/*    */   {
/* 79 */     return this.deviceType;
/*    */   }
/*    */ 
/*    */   public void setDevice(String device) {
/* 83 */     this.device = device;
/*    */   }
/*    */ 
/*    */   public void setDeviceClass(int deviceClass) {
/* 87 */     this.deviceClass = deviceClass;
/*    */   }
/*    */ 
/*    */   public void setDeviceType(short deviceType) {
/* 91 */     this.deviceType = deviceType;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryDeviceInfoConfEvent
 * JD-Core Version:    0.5.4
 */