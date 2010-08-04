/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentReconnectCall extends LucentPrivateData
/*    */ {
/*    */   short dropResource;
/*    */   LucentUserToUserInfo userInfo;
/*    */   static final int PDU = 7;
/*    */ 
/*    */   LucentReconnectCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   LucentReconnectCall(short _dropResource, LucentUserToUserInfo _userInfo)
/*    */   {
/* 20 */     this.dropResource = _dropResource;
/* 21 */     this.userInfo = _userInfo;
/*    */   }
/*    */ 
/*    */   public static LucentReconnectCall decode(InputStream in) {
/* 25 */     LucentReconnectCall _this = new LucentReconnectCall();
/* 26 */     _this.doDecode(in);
/*    */ 
/* 28 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 32 */     this.dropResource = LucentDropResource.decode(memberStream);
/* 33 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 38 */     LucentDropResource.encode(this.dropResource, memberStream);
/* 39 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 44 */     Collection lines = new ArrayList();
/*    */ 
/* 46 */     lines.add("LucentReconnectCall ::=");
/* 47 */     lines.add("{");
/*    */ 
/* 49 */     String indent = "  ";
/*    */ 
/* 51 */     lines.addAll(LucentDropResource.print(this.dropResource, "dropResource", indent));
/* 52 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 54 */     lines.add("}");
/* 55 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 60 */     return 7;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentReconnectCall
 * JD-Core Version:    0.5.4
 */