/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentQueuedEvent extends LucentPrivateData
/*    */ {
/*    */   private LucentDeviceHistoryEntry[] deviceHistory;
/*    */   static final int PDU = 130;
/*    */ 
/*    */   static LucentQueuedEvent decode(InputStream in)
/*    */   {
/* 22 */     LucentQueuedEvent _this = new LucentQueuedEvent();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 26 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 31 */     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 35 */     CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 40 */     Collection lines = new ArrayList();
/*    */ 
/* 42 */     lines.add("LucentQueuedEvent ::=");
/* 43 */     lines.add("{");
/*    */ 
/* 45 */     String indent = "  ";
/*    */ 
/* 47 */     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
/*    */ 
/* 49 */     lines.add("}");
/* 50 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 55 */     return 130;
/*    */   }
/*    */ 
/*    */   public LucentDeviceHistoryEntry[] getDeviceHistory()
/*    */   {
/* 61 */     return this.deviceHistory;
/*    */   }
/*    */ 
/*    */   public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
/* 65 */     this.deviceHistory = deviceHistory;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueuedEvent
 * JD-Core Version:    0.5.4
 */