/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV6ReconnectCall extends LucentReconnectCall
/*    */ {
/*    */   public static final int PDU = 114;
/*    */ 
/*    */   LucentV6ReconnectCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentV6ReconnectCall(short _dropResource, LucentUserToUserInfo _userInfo)
/*    */   {
/* 26 */     super(_dropResource, _userInfo);
/*    */   }
/*    */ 
/*    */   public static LucentReconnectCall decode(InputStream in)
/*    */   {
/* 31 */     LucentV6ReconnectCall _this = new LucentV6ReconnectCall();
/* 32 */     _this.doDecode(in);
/*    */ 
/* 34 */     return _this;
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 39 */     Collection lines = new ArrayList();
/*    */ 
/* 41 */     lines.add("LucentV6ReconnectCall ::=");
/* 42 */     lines.add("{");
/*    */ 
/* 44 */     String indent = "  ";
/*    */ 
/* 46 */     lines.addAll(LucentDropResource.print(this.dropResource, "dropResource", indent));
/* 47 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 49 */     lines.add("}");
/* 50 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 55 */     return 114;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6ReconnectCall
 * JD-Core Version:    0.5.4
 */