/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentSupervisorAssistCall extends LucentPrivateData
/*    */ {
/*    */   String split;
/*    */   LucentUserToUserInfo userInfo;
/*    */   static final int PDU = 6;
/*    */ 
/*    */   public LucentSupervisorAssistCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentSupervisorAssistCall(String _split, LucentUserToUserInfo _userInfo)
/*    */   {
/* 20 */     this.split = _split;
/* 21 */     this.userInfo = _userInfo;
/*    */   }
/*    */ 
/*    */   public static LucentSupervisorAssistCall decode(InputStream in) {
/* 25 */     LucentSupervisorAssistCall _this = new LucentSupervisorAssistCall();
/* 26 */     _this.doDecode(in);
/*    */ 
/* 28 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 33 */     this.split = DeviceID.decode(memberStream);
/* 34 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 39 */     DeviceID.encode(this.split, memberStream);
/* 40 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 45 */     Collection lines = new ArrayList();
/*    */ 
/* 47 */     lines.add("LucentSupervisorAssistCall ::=");
/* 48 */     lines.add("{");
/*    */ 
/* 50 */     String indent = "  ";
/*    */ 
/* 52 */     lines.addAll(DeviceID.print(this.split, "split", indent));
/* 53 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 55 */     lines.add("}");
/* 56 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 61 */     return 6;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSupervisorAssistCall
 * JD-Core Version:    0.5.4
 */