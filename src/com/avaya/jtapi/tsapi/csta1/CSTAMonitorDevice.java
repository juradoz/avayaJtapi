/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAMonitorDevice extends CSTARequest
/*    */ {
/*    */   String deviceID;
/*    */   CSTAMonitorFilter monitorFilter;
/*    */   public static final int PDU = 111;
/*    */ 
/*    */   public CSTAMonitorDevice(String _deviceID, CSTAMonitorFilter _monitorFilter)
/*    */   {
/* 19 */     this.deviceID = _deviceID;
/* 20 */     this.monitorFilter = _monitorFilter;
/*    */   }
/*    */ 
/*    */   public CSTAMonitorDevice() {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 27 */     DeviceID.encode(this.deviceID, memberStream);
/* 28 */     CSTAMonitorFilter.encode(this.monitorFilter, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAMonitorDevice decode(InputStream in)
/*    */   {
/* 33 */     CSTAMonitorDevice _this = new CSTAMonitorDevice();
/* 34 */     _this.doDecode(in);
/*    */ 
/* 36 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 41 */     this.deviceID = DeviceID.decode(memberStream);
/* 42 */     this.monitorFilter = CSTAMonitorFilter.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 47 */     Collection lines = new ArrayList();
/*    */ 
/* 49 */     lines.add("CSTAMonitorDevice ::=");
/* 50 */     lines.add("{");
/*    */ 
/* 52 */     String indent = "  ";
/*    */ 
/* 54 */     lines.addAll(DeviceID.print(this.deviceID, "deviceID", indent));
/* 55 */     lines.addAll(CSTAMonitorFilter.print(this.monitorFilter, "monitorFilter", indent));
/*    */ 
/* 57 */     lines.add("}");
/* 58 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 63 */     return 111;
/*    */   }
/*    */ 
/*    */   public String getDeviceID()
/*    */   {
/* 69 */     return this.deviceID;
/*    */   }
/*    */ 
/*    */   public CSTAMonitorFilter getMonitorFilter()
/*    */   {
/* 77 */     return this.monitorFilter;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMonitorDevice
 * JD-Core Version:    0.5.4
 */