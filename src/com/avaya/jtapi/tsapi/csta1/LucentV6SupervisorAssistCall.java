/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV6SupervisorAssistCall extends LucentSupervisorAssistCall
/*    */ {
/*    */   public static final int PDU = 113;
/*    */ 
/*    */   public LucentV6SupervisorAssistCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentV6SupervisorAssistCall(String _split, LucentUserToUserInfo _userInfo)
/*    */   {
/* 27 */     super(_split, _userInfo);
/*    */   }
/*    */ 
/*    */   public static LucentSupervisorAssistCall decode(InputStream in)
/*    */   {
/* 33 */     LucentV6SupervisorAssistCall _this = new LucentV6SupervisorAssistCall();
/* 34 */     _this.doDecode(in);
/*    */ 
/* 36 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 40 */     super.encodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 45 */     super.decodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 50 */     Collection lines = new ArrayList();
/* 51 */     lines.add("LucentV6SupervisorAssistCall ::=");
/* 52 */     lines.add("{");
/*    */ 
/* 54 */     String indent = "  ";
/*    */ 
/* 56 */     lines.addAll(DeviceID.print(this.split, "split", indent));
/* 57 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 59 */     lines.add("}");
/* 60 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 65 */     return 113;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6SupervisorAssistCall
 * JD-Core Version:    0.5.4
 */