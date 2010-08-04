/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentV6DeliveredEvent extends LucentV5DeliveredEvent
/*    */ {
/*    */   static final int PDU = 117;
/*    */ 
/*    */   public static LucentDeliveredEvent decode(InputStream in)
/*    */   {
/* 23 */     LucentV6DeliveredEvent _this = new LucentV6DeliveredEvent();
/* 24 */     _this.doDecode(in);
/*    */ 
/* 26 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 30 */     super.decodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 35 */     super.encodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 39 */     Collection lines = new ArrayList();
/*    */ 
/* 41 */     lines.add("LucentV6DeliveredEvent ::=");
/* 42 */     lines.add("{");
/*    */ 
/* 44 */     String indent = "  ";
/*    */ 
/* 46 */     lines.addAll(LucentDeliveredType.print(this.deliveredType, "deliveredType", indent));
/* 47 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/* 48 */     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
/* 49 */     lines.addAll(DeviceID.print(this.split_asn, "split", indent));
/* 50 */     lines.addAll(LucentLookaheadInfo.print(this.lookaheadInfo, "lookaheadInfo", indent));
/* 51 */     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
/* 52 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/* 53 */     lines.addAll(LucentReasonCode.print(this.reason, "reason", indent));
/* 54 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/* 55 */     lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo, "callOriginatorInfo", indent));
/* 56 */     lines.addAll(LucentOriginalCallInfo.print(this.originalCallInfo, "originalCallInfo", indent));
/* 57 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/* 58 */     lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling", indent));
/* 59 */     lines.add("}");
/* 60 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 65 */     return 117;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6DeliveredEvent
 * JD-Core Version:    0.5.4
 */