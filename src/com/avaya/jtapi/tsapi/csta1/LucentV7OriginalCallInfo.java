/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV7OriginalCallInfo extends LucentV5OriginalCallInfo
/*    */ {
/*    */   private LucentDeviceHistoryEntry[] asn_deviceHistory;
/*    */ 
/*    */   public LucentDeviceHistoryEntry[] getDeviceHistory()
/*    */   {
/* 26 */     return this.asn_deviceHistory;
/*    */   }
/*    */ 
/*    */   public void setDeviceHistory(LucentDeviceHistoryEntry[] asn_deviceHistory) {
/* 30 */     this.asn_deviceHistory = asn_deviceHistory;
/*    */   }
/*    */ 
/*    */   public boolean hasDeviceHistory()
/*    */   {
/* 37 */     return this.asn_deviceHistory != null;
/*    */   }
/*    */ 
/*    */   public static LucentOriginalCallInfo decode(InputStream in)
/*    */   {
/* 43 */     LucentV7OriginalCallInfo _this = new LucentV7OriginalCallInfo();
/* 44 */     _this.doDecode(in);
/* 45 */     if ((_this.callingDevice_asn == null) && (_this.calledDevice_asn == null) && (_this.trunkGroup == null) && (_this.trunkMember == null) && (_this.lookaheadInfo == null) && (_this.userEnteredCode == null) && (_this.userInfo == null) && (_this.ucid == null) && (_this.callOriginatorInfo == null) && (_this.asn_deviceHistory == null))
/*    */     {
/* 51 */       return null;
/*    */     }
/* 53 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 58 */     super.decodeMembers(memberStream);
/* 59 */     this.asn_deviceHistory = CSTADeviceHistoryData.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 64 */     super.encodeMembers(memberStream);
/* 65 */     CSTADeviceHistoryData.encode(this.asn_deviceHistory, memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(LucentV7OriginalCallInfo _this, String name, String _indent)
/*    */   {
/* 70 */     Collection lines = new ArrayList();
/*    */ 
/* 72 */     if (_this == null)
/*    */     {
/* 74 */       lines.add(_indent + name + " <null>");
/* 75 */       return lines;
/*    */     }
/* 77 */     if (name != null) {
/* 78 */       lines.add(_indent + name);
/*    */     }
/* 80 */     lines.add(_indent + "{");
/*    */ 
/* 82 */     String indent = _indent + "  ";
/*    */ 
/* 84 */     lines.addAll(ReasonForCallInfo.print(_this.reason, "reason", indent));
/* 85 */     lines.addAll(CSTAExtendedDeviceID.print(_this.callingDevice_asn, "callingDevice", indent));
/* 86 */     lines.addAll(CSTAExtendedDeviceID.print(_this.calledDevice_asn, "calledDevice", indent));
/* 87 */     lines.addAll(DeviceID.print(_this.trunkGroup, "trunkGroup", indent));
/* 88 */     lines.addAll(DeviceID.print(_this.trunkMember, "trunkMember", indent));
/* 89 */     lines.addAll(LucentV5LookaheadInfo.print((LucentV5LookaheadInfo)_this.lookaheadInfo, "lookaheadInfo", indent));
/* 90 */     lines.addAll(LucentUserEnteredCode.print(_this.userEnteredCode, "userEnteredCode", indent));
/* 91 */     lines.addAll(LucentUserToUserInfo.print(_this.userInfo, "userInfo", indent));
/* 92 */     lines.addAll(UCID.print(_this.ucid, "ucid", indent));
/* 93 */     lines.addAll(CSTACallOriginatorInfo.print(_this.callOriginatorInfo, "callOriginatorInfo", indent));
/* 94 */     lines.addAll(ASNBoolean.print(_this.flexibleBilling, "flexibleBilling", indent));
/* 95 */     lines.addAll(CSTADeviceHistoryData.print(_this.asn_deviceHistory, "deviceHistoryEntry", indent));
/*    */ 
/* 97 */     lines.add(_indent + "}");
/* 98 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7OriginalCallInfo
 * JD-Core Version:    0.5.4
 */