/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV6EstablishedEvent extends LucentV5EstablishedEvent
/*    */ {
/*    */   static final int PDU = 118;
/*    */ 
/*    */   public static LucentEstablishedEvent decode(InputStream in)
/*    */   {
/* 22 */     LucentV6EstablishedEvent _this = new LucentV6EstablishedEvent();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 25 */     return _this;
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 30 */     Collection lines = new ArrayList();
/*    */ 
/* 32 */     lines.add("LucentV6EstablishedEvent ::=");
/* 33 */     lines.add("{");
/*    */ 
/* 35 */     String indent = "  ";
/*    */ 
/* 37 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/* 38 */     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
/* 39 */     lines.addAll(DeviceID.print(this.split_asn, "split", indent));
/* 40 */     lines.addAll(LucentLookaheadInfo.print(this.lookaheadInfo, "lookaheadInfo", indent));
/* 41 */     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
/* 42 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/* 43 */     lines.addAll(LucentReasonCode.print(this.reason, "reason", indent));
/* 44 */     lines.addAll(LucentOriginalCallInfo.print(this.originalCallInfo, "originalCallInfo", indent));
/* 45 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/* 46 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/* 47 */     lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo, "callOriginatorInfo", indent));
/* 48 */     lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling", indent));
/* 49 */     lines.add("}");
/* 50 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 55 */     return 118;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6EstablishedEvent
 * JD-Core Version:    0.5.4
 */