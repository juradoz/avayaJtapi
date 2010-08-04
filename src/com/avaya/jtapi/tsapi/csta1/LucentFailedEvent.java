/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentFailedEvent extends LucentPrivateData
/*    */ {
/*    */   LucentDeviceHistoryEntry[] deviceHistory;
/*    */   static final int PDU = 137;
/*    */ 
/*    */   LucentFailedEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   LucentFailedEvent(LucentDeviceHistoryEntry[] _deviceHistory)
/*    */   {
/* 23 */     this.deviceHistory = _deviceHistory;
/*    */   }
/*    */ 
/*    */   static LucentFailedEvent decode(InputStream in) {
/* 27 */     LucentFailedEvent _this = new LucentFailedEvent();
/* 28 */     _this.doDecode(in);
/*    */ 
/* 31 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 36 */     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 41 */     CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 45 */     Collection lines = new ArrayList();
/*    */ 
/* 47 */     lines.add("LucentFailedEvent ::=");
/* 48 */     lines.add("{");
/*    */ 
/* 50 */     String indent = "  ";
/*    */ 
/* 52 */     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
/*    */ 
/* 54 */     lines.add("}");
/* 55 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 60 */     return 137;
/*    */   }
/*    */ 
/*    */   public LucentDeviceHistoryEntry[] getDeviceHistory()
/*    */   {
/* 66 */     return this.deviceHistory;
/*    */   }
/*    */   public void setDeviceHistory(LucentDeviceHistoryEntry[] entry) {
/* 69 */     this.deviceHistory = entry;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentFailedEvent
 * JD-Core Version:    0.5.4
 */