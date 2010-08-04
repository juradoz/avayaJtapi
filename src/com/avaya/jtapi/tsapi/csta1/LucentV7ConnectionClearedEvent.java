/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV7ConnectionClearedEvent extends LucentV6ConnectionClearedEvent
/*    */ {
/*    */   private LucentDeviceHistoryEntry[] deviceHistory;
/*    */   static final int PDU = 134;
/*    */ 
/*    */   public static LucentConnectionClearedEvent decode(InputStream in)
/*    */   {
/* 28 */     LucentV7ConnectionClearedEvent _this = new LucentV7ConnectionClearedEvent();
/* 29 */     _this.doDecode(in);
/*    */ 
/* 31 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 36 */     super.decodeMembers(memberStream);
/* 37 */     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 41 */     super.encodeMembers(memberStream);
/* 42 */     CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 47 */     Collection lines = new ArrayList();
/*    */ 
/* 49 */     lines.add("LucentV7ConnectionClearedEvent ::=");
/* 50 */     lines.add("{");
/*    */ 
/* 52 */     String indent = "  ";
/*    */ 
/* 54 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/* 55 */     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
/*    */ 
/* 57 */     lines.add("}");
/* 58 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 63 */     return 134;
/*    */   }
/*    */ 
/*    */   public LucentDeviceHistoryEntry[] getDeviceHistory()
/*    */   {
/* 69 */     return this.deviceHistory;
/*    */   }
/*    */ 
/*    */   public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
/* 73 */     this.deviceHistory = deviceHistory;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7ConnectionClearedEvent
 * JD-Core Version:    0.5.4
 */