/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV7SnapshotCallInfoConfEvent extends LucentPrivateData
/*    */ {
/*    */   LucentDeviceHistoryEntry[] deviceHistory;
/*    */   static final int PDU = 138;
/*    */ 
/*    */   static LucentV7SnapshotCallInfoConfEvent decode(InputStream in)
/*    */   {
/* 16 */     LucentV7SnapshotCallInfoConfEvent _this = new LucentV7SnapshotCallInfoConfEvent();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 30 */     Collection lines = new ArrayList();
/*    */ 
/* 32 */     lines.add("LucentV7SnapshotCallInfoConfEvent ::=");
/* 33 */     lines.add("{");
/*    */ 
/* 35 */     String indent = "  ";
/*    */ 
/* 37 */     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
/*    */ 
/* 39 */     lines.add("}");
/* 40 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 45 */     return 138;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7SnapshotCallInfoConfEvent
 * JD-Core Version:    0.5.4
 */