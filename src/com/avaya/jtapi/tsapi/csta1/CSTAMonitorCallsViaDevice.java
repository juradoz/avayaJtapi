/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAMonitorCallsViaDevice extends CSTARequest
/*    */ {
/*    */   String deviceID;
/*    */   CSTAMonitorFilter monitorFilter;
/*    */   public static final int PDU = 113;
/*    */ 
/*    */   public CSTAMonitorCallsViaDevice(String _deviceID, CSTAMonitorFilter _monitorFilter)
/*    */   {
/* 19 */     this.deviceID = _deviceID;
/* 20 */     this.monitorFilter = _monitorFilter;
/*    */   }
/*    */ 
/*    */   public CSTAMonitorCallsViaDevice() {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 27 */     DeviceID.encode(this.deviceID, memberStream);
/* 28 */     CSTAMonitorFilter.encode(this.monitorFilter, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAMonitorCallsViaDevice decode(InputStream in)
/*    */   {
/* 33 */     CSTAMonitorCallsViaDevice _this = new CSTAMonitorCallsViaDevice();
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
/*    */   public Collection<String> print() {
/* 46 */     Collection lines = new ArrayList();
/*    */ 
/* 48 */     lines.add("CSTAMonitorCallsViaDevice ::=");
/* 49 */     lines.add("{");
/*    */ 
/* 51 */     String indent = "  ";
/*    */ 
/* 53 */     lines.addAll(DeviceID.print(this.deviceID, "deviceID", indent));
/* 54 */     lines.addAll(CSTAMonitorFilter.print(this.monitorFilter, "monitorFilter", indent));
/*    */ 
/* 56 */     lines.add("}");
/* 57 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 62 */     return 113;
/*    */   }
/*    */ 
/*    */   public String getDeviceID()
/*    */   {
/* 68 */     return this.deviceID;
/*    */   }
/*    */ 
/*    */   public CSTAMonitorFilter getMonitorFilter()
/*    */   {
/* 76 */     return this.monitorFilter;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMonitorCallsViaDevice
 * JD-Core Version:    0.5.4
 */