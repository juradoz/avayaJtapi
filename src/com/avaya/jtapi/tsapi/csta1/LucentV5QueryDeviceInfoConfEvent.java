/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV5QueryDeviceInfoConfEvent extends LucentQueryDeviceInfoConfEvent
/*    */ {
/*    */   short associatedClass;
/*    */   String associatedDevice;
/*    */   static final int PDU = 98;
/*    */ 
/*    */   public static LucentQueryDeviceInfoConfEvent decode(InputStream in)
/*    */   {
/* 16 */     LucentV5QueryDeviceInfoConfEvent _this = new LucentV5QueryDeviceInfoConfEvent();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 24 */     super.decodeMembers(memberStream);
/* 25 */     this.associatedClass = LucentExtensionClass.decode(memberStream);
/* 26 */     this.associatedDevice = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 31 */     super.encodeMembers(memberStream);
/* 32 */     LucentExtensionClass.encode(this.associatedClass, memberStream);
/* 33 */     DeviceID.encode(this.associatedDevice, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 39 */     Collection lines = new ArrayList();
/*    */ 
/* 41 */     lines.add("LucentV5QueryDeviceInfoConfEvent ::=");
/* 42 */     lines.add("{");
/*    */ 
/* 44 */     String indent = "  ";
/*    */ 
/* 46 */     lines.addAll(LucentExtensionClass.print(this.extensionClass, "extensionClass", indent));
/* 47 */     lines.addAll(LucentExtensionClass.print(this.associatedClass, "associatedClass", indent));
/* 48 */     lines.addAll(DeviceID.print(this.associatedDevice, "associatedDevice", indent));
/*    */ 
/* 50 */     lines.add("}");
/* 51 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 56 */     return 98;
/*    */   }
/*    */ 
/*    */   public short getAssociatedClass()
/*    */   {
/* 62 */     return this.associatedClass;
/*    */   }
/*    */ 
/*    */   public String getAssociatedDevice()
/*    */   {
/* 70 */     return this.associatedDevice;
/*    */   }
/*    */ 
/*    */   public void setAssociatedClass(short associatedClass) {
/* 74 */     this.associatedClass = associatedClass;
/*    */   }
/*    */ 
/*    */   public void setAssociatedDevice(String associatedDevice) {
/* 78 */     this.associatedDevice = associatedDevice;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5QueryDeviceInfoConfEvent
 * JD-Core Version:    0.5.4
 */