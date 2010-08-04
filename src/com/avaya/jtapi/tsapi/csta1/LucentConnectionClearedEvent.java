/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentConnectionClearedEvent extends LucentPrivateData
/*    */ {
/*    */   LucentUserToUserInfo userInfo;
/*    */   static final int PDU = 36;
/*    */ 
/*    */   static LucentConnectionClearedEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentConnectionClearedEvent _this = new LucentConnectionClearedEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 23 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 27 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 32 */     Collection lines = new ArrayList();
/*    */ 
/* 34 */     lines.add("LucentConnectionClearedEvent ::=");
/* 35 */     lines.add("{");
/*    */ 
/* 37 */     String indent = "  ";
/*    */ 
/* 39 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 41 */     lines.add("}");
/* 42 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 47 */     return 36;
/*    */   }
/*    */ 
/*    */   public LucentUserToUserInfo getUserInfo()
/*    */   {
/* 53 */     return this.userInfo;
/*    */   }
/*    */   public void setUserInfo(LucentUserToUserInfo userInfo) {
/* 56 */     this.userInfo = userInfo;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentConnectionClearedEvent
 * JD-Core Version:    0.5.4
 */