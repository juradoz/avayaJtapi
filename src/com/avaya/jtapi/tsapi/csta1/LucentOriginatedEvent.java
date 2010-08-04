/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentOriginatedEvent extends LucentPrivateData
/*    */ {
/*    */   String physicalTerminal_asn;
/*    */   LucentUserToUserInfo userInfo;
/*    */   static final int PDU = 47;
/*    */ 
/*    */   public static LucentOriginatedEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentOriginatedEvent _this = new LucentOriginatedEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 23 */     this.physicalTerminal_asn = DeviceID.decode(memberStream);
/* 24 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 29 */     DeviceID.encode(this.physicalTerminal_asn, memberStream);
/* 30 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 35 */     Collection lines = new ArrayList();
/*    */ 
/* 37 */     lines.add("LucentOriginatedEvent ::=");
/* 38 */     lines.add("{");
/*    */ 
/* 40 */     String indent = "  ";
/*    */ 
/* 42 */     lines.addAll(DeviceID.print(this.physicalTerminal_asn, "physicalTerminal", indent));
/* 43 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 45 */     lines.add("}");
/* 46 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 51 */     return 47;
/*    */   }
/*    */ 
/*    */   public String getPhysicalTerminal_asn()
/*    */   {
/* 57 */     return this.physicalTerminal_asn;
/*    */   }
/*    */ 
/*    */   public LucentUserToUserInfo getUserInfo()
/*    */   {
/* 65 */     return this.userInfo;
/*    */   }
/*    */ 
/*    */   public void setPhysicalTerminal_asn(String physicalTerminal_asn) {
/* 69 */     this.physicalTerminal_asn = physicalTerminal_asn;
/*    */   }
/*    */ 
/*    */   public void setUserInfo(LucentUserToUserInfo userInfo) {
/* 73 */     this.userInfo = userInfo;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentOriginatedEvent
 * JD-Core Version:    0.5.4
 */