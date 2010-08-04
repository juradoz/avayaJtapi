/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentQueryAgentLoginResp extends LucentPrivateData
/*    */ {
/*    */   int privEventCrossRefID;
/*    */   String[] devices;
/*    */   public static final int PDU = 15;
/*    */ 
/*    */   public static LucentQueryAgentLoginResp decode(InputStream in)
/*    */   {
/* 16 */     LucentQueryAgentLoginResp _this = new LucentQueryAgentLoginResp();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 24 */     this.privEventCrossRefID = LucentPrivEventCrossRefID.decode(memberStream);
/* 25 */     this.devices = LucentQueryAgentLoginRespList.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 29 */     LucentPrivEventCrossRefID.encode(this.privEventCrossRefID, memberStream);
/* 30 */     LucentQueryAgentLoginRespList.encode(this.devices, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 35 */     Collection lines = new ArrayList();
/*    */ 
/* 37 */     lines.add("LucentQueryAgentLoginResp ::=");
/* 38 */     lines.add("{");
/*    */ 
/* 40 */     String indent = "  ";
/*    */ 
/* 42 */     lines.addAll(LucentPrivEventCrossRefID.print(this.privEventCrossRefID, "privEventCrossRefID", indent));
/* 43 */     lines.addAll(LucentQueryAgentLoginRespList.print(this.devices, "devices", indent));
/*    */ 
/* 45 */     lines.add("}");
/* 46 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 51 */     return 15;
/*    */   }
/*    */ 
/*    */   public String[] getDevices()
/*    */   {
/* 57 */     return this.devices;
/*    */   }
/*    */ 
/*    */   public int getPrivEventCrossRefID()
/*    */   {
/* 65 */     return this.privEventCrossRefID;
/*    */   }
/*    */ 
/*    */   public void setDevices(String[] devices) {
/* 69 */     this.devices = devices;
/*    */   }
/*    */ 
/*    */   public void setPrivEventCrossRefID(int privEventCrossRefID) {
/* 73 */     this.privEventCrossRefID = privEventCrossRefID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginResp
 * JD-Core Version:    0.5.4
 */