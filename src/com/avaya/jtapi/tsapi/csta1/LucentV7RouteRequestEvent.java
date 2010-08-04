/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV7RouteRequestEvent extends LucentV6RouteRequestEvent
/*    */ {
/*    */   LucentDeviceHistoryEntry[] deviceHistory;
/*    */   public static final int PDU = 131;
/*    */ 
/*    */   public static LucentRouteRequestEvent decode(InputStream in)
/*    */   {
/* 29 */     LucentV7RouteRequestEvent _this = new LucentV7RouteRequestEvent();
/* 30 */     _this.doDecode(in);
/*    */ 
/* 33 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 38 */     super.decodeMembers(memberStream);
/* 39 */     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 44 */     super.encodeMembers(memberStream);
/* 45 */     CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 50 */     Collection lines = new ArrayList();
/*    */ 
/* 52 */     lines.add("LucentV7RouteRequestEvent ::=");
/* 53 */     lines.add("{");
/*    */ 
/* 55 */     String indent = "  ";
/*    */ 
/* 57 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/* 58 */     lines.addAll(LucentV5LookaheadInfo.print((LucentV5LookaheadInfo)this.lookaheadInfo, "lookaheadInfo", indent));
/* 59 */     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
/* 60 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/* 61 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/* 62 */     lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo, "callOriginatorInfo", indent));
/* 63 */     lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling", indent));
/* 64 */     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
/* 65 */     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
/*    */ 
/* 67 */     lines.add("}");
/* 68 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 73 */     return 131;
/*    */   }
/*    */ 
/*    */   public LucentDeviceHistoryEntry[] getDeviceHistory()
/*    */   {
/* 80 */     return this.deviceHistory;
/*    */   }
/*    */ 
/*    */   public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
/* 84 */     this.deviceHistory = deviceHistory;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7RouteRequestEvent
 * JD-Core Version:    0.5.4
 */