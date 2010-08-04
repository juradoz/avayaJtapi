/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentV6RouteRequestEvent extends LucentV5RouteRequestEvent
/*    */ {
/*    */   String trunkMember;
/*    */   static final int PDU = 105;
/*    */ 
/*    */   public static LucentRouteRequestEvent decode(InputStream in)
/*    */   {
/* 31 */     LucentV6RouteRequestEvent _this = new LucentV6RouteRequestEvent();
/* 32 */     _this.doDecode(in);
/*    */ 
/* 34 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 39 */     super.decodeMembers(memberStream);
/* 40 */     this.trunkMember = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 44 */     super.encodeMembers(memberStream);
/* 45 */     DeviceID.encode(this.trunkMember, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 64 */     Collection lines = new ArrayList();
/*    */ 
/* 66 */     lines.add("LucentV6RouteRequestEvent ::=");
/* 67 */     lines.add("{");
/*    */ 
/* 69 */     String indent = "  ";
/*    */ 
/* 71 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/* 72 */     lines.addAll(LucentV5LookaheadInfo.print((LucentV5LookaheadInfo)this.lookaheadInfo, "lookaheadInfo", indent));
/* 73 */     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
/* 74 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/* 75 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/* 76 */     lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo, "callOriginatorInfo", indent));
/* 77 */     lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling", indent));
/* 78 */     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
/*    */ 
/* 80 */     lines.add("}");
/* 81 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 86 */     return 105;
/*    */   }
/*    */   public String getTrunkMember() {
/* 89 */     return this.trunkMember;
/*    */   }
/*    */ 
/*    */   public void setTrunkMember(String trunkMember) {
/* 93 */     this.trunkMember = trunkMember;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6RouteRequestEvent
 * JD-Core Version:    0.5.4
 */