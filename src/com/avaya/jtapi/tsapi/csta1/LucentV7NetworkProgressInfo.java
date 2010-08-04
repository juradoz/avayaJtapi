/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV7NetworkProgressInfo extends LucentV5NetworkProgressInfo
/*    */ {
/*    */   LucentDeviceHistoryEntry[] deviceHistory;
/*    */   static final int PDU = 136;
/*    */ 
/*    */   public static LucentNetworkProgressInfo decode(InputStream in)
/*    */   {
/* 20 */     LucentV7NetworkProgressInfo _this = new LucentV7NetworkProgressInfo();
/* 21 */     _this.doDecode(in);
/*    */ 
/* 24 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 29 */     super.decodeMembers(memberStream);
/* 30 */     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 34 */     super.encodeMembers(memberStream);
/* 35 */     CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 39 */     Collection lines = new ArrayList();
/* 40 */     lines.add("V7NetworkProgressInfo ::=");
/* 41 */     lines.add("{");
/*    */ 
/* 43 */     String indent = "  ";
/*    */ 
/* 45 */     lines.addAll(ProgressLocation.print(this.progressLocation, "progressLocation", indent));
/*    */ 
/* 47 */     lines.addAll(ProgressDescription.print(this.progressDescription, "progressDescription", indent));
/*    */ 
/* 49 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/* 50 */     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
/* 51 */     lines.addAll(CSTADeviceHistoryData.print(getDeviceHistory(), "deviceHistory", indent));
/*    */ 
/* 54 */     lines.add("}");
/* 55 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 60 */     return 136;
/*    */   }
/*    */ 
/*    */   public LucentDeviceHistoryEntry[] getDeviceHistory()
/*    */   {
/* 66 */     return this.deviceHistory;
/*    */   }
/*    */   public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
/* 69 */     this.deviceHistory = deviceHistory;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7NetworkProgressInfo
 * JD-Core Version:    0.5.4
 */