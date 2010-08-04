/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentClearConnection extends LucentPrivateData
/*    */ {
/*    */   short dropResource;
/*    */   LucentUserToUserInfo userInfo;
/*    */   static final int PDU = 1;
/*    */ 
/*    */   public LucentClearConnection()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentClearConnection(short _dropResource, LucentUserToUserInfo _userInfo)
/*    */   {
/* 20 */     this.dropResource = _dropResource;
/* 21 */     this.userInfo = _userInfo;
/*    */   }
/*    */ 
/*    */   public static LucentClearConnection decode(InputStream in)
/*    */   {
/* 26 */     LucentClearConnection _this = new LucentClearConnection();
/* 27 */     _this.doDecode(in);
/*    */ 
/* 29 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 33 */     this.dropResource = LucentDropResource.decode(memberStream);
/* 34 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 38 */     LucentDropResource.encode(this.dropResource, memberStream);
/* 39 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 44 */     Collection lines = new ArrayList();
/*    */ 
/* 46 */     lines.add("LucentClearConnection ::=");
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
/* 60 */     return 1;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentClearConnection
 * JD-Core Version:    0.5.4
 */