/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentDirectAgentCall extends LucentPrivateData
/*    */ {
/*    */   String split;
/*    */   boolean priorityCalling;
/*    */   LucentUserToUserInfo userInfo;
/*    */   static final int PDU = 4;
/*    */ 
/*    */   public LucentDirectAgentCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentDirectAgentCall(String _split, boolean _priorityCalling, LucentUserToUserInfo _userInfo)
/*    */   {
/* 23 */     this.split = _split;
/* 24 */     this.priorityCalling = _priorityCalling;
/* 25 */     this.userInfo = _userInfo;
/*    */   }
/*    */ 
/*    */   public static LucentDirectAgentCall decode(InputStream in) {
/* 29 */     LucentDirectAgentCall _this = new LucentDirectAgentCall();
/* 30 */     _this.doDecode(in);
/*    */ 
/* 32 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 37 */     this.split = DeviceID.decode(memberStream);
/* 38 */     this.priorityCalling = ASNBoolean.decode(memberStream);
/* 39 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 44 */     DeviceID.encode(this.split, memberStream);
/* 45 */     ASNBoolean.encode(this.priorityCalling, memberStream);
/* 46 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 51 */     Collection lines = new ArrayList();
/*    */ 
/* 53 */     lines.add("LucentDirectAgentCall ::=");
/* 54 */     lines.add("{");
/*    */ 
/* 56 */     String indent = "  ";
/*    */ 
/* 58 */     lines.addAll(DeviceID.print(this.split, "split", indent));
/* 59 */     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
/* 60 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 62 */     lines.add("}");
/* 63 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 68 */     return 4;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentDirectAgentCall
 * JD-Core Version:    0.5.4
 */