/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentSnapshotCallInfoConfEvent extends LucentPrivateData
/*    */ {
/*    */   LucentDeviceHistoryEntry[] deviceHistory;
/*    */   static final int PDU = 138;
/*    */ 
/*    */   static LucentSnapshotCallInfoConfEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentSnapshotCallInfoConfEvent _this = new LucentSnapshotCallInfoConfEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 24 */     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 29 */     Collection lines = new ArrayList();
/*    */ 
/* 31 */     lines.add("LucentSnapshotCallInfoConfEvent ::=");
/* 32 */     lines.add("{");
/*    */ 
/* 34 */     String indent = "  ";
/*    */ 
/* 36 */     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
/*    */ 
/* 38 */     lines.add("}");
/* 39 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 44 */     return 138;
/*    */   }
/*    */   public LucentDeviceHistoryEntry[] getDeviceHistory() {
/* 47 */     return this.deviceHistory;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSnapshotCallInfoConfEvent
 * JD-Core Version:    0.5.4
 */