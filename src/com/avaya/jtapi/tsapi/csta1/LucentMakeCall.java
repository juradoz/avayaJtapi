/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentMakeCall extends LucentPrivateData
/*    */ {
/*    */   String destRoute;
/*    */   boolean priorityCalling;
/*    */   LucentUserToUserInfo userInfo;
/*    */   static final int PDU = 3;
/*    */ 
/*    */   public LucentMakeCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentMakeCall(String _destRoute, boolean _priorityCalling, LucentUserToUserInfo _userInfo)
/*    */   {
/* 23 */     this.destRoute = _destRoute;
/* 24 */     this.priorityCalling = _priorityCalling;
/* 25 */     this.userInfo = _userInfo;
/*    */   }
/*    */ 
/*    */   public static LucentMakeCall decode(InputStream in) {
/* 29 */     LucentMakeCall _this = new LucentMakeCall();
/* 30 */     _this.doDecode(in);
/*    */ 
/* 32 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 36 */     this.destRoute = DeviceID.decode(memberStream);
/* 37 */     this.priorityCalling = ASNBoolean.decode(memberStream);
/* 38 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 42 */     DeviceID.encode(this.destRoute, memberStream);
/* 43 */     ASNBoolean.encode(this.priorityCalling, memberStream);
/* 44 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 49 */     Collection lines = new ArrayList();
/*    */ 
/* 51 */     lines.add("LucentMakeCall ::=");
/* 52 */     lines.add("{");
/*    */ 
/* 54 */     String indent = "  ";
/*    */ 
/* 56 */     lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
/* 57 */     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
/* 58 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 60 */     lines.add("}");
/* 61 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 66 */     return 3;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMakeCall
 * JD-Core Version:    0.5.4
 */