/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentConsultationCall extends LucentPrivateData
/*    */ {
/*    */   String destRoute;
/*    */   boolean priorityCalling;
/*    */   LucentUserToUserInfo userInfo;
/*    */   static final int PDU = 2;
/*    */ 
/*    */   public LucentConsultationCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentConsultationCall(String _destRoute, boolean _priorityCalling, LucentUserToUserInfo _userInfo)
/*    */   {
/* 24 */     this.destRoute = _destRoute;
/* 25 */     this.priorityCalling = _priorityCalling;
/* 26 */     this.userInfo = _userInfo;
/*    */   }
/*    */ 
/*    */   public static LucentConsultationCall decode(InputStream in)
/*    */   {
/* 31 */     LucentConsultationCall _this = new LucentConsultationCall();
/* 32 */     _this.doDecode(in);
/*    */ 
/* 34 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 39 */     DeviceID.encode(this.destRoute, memberStream);
/* 40 */     ASNBoolean.encode(this.priorityCalling, memberStream);
/* 41 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 46 */     this.destRoute = DeviceID.decode(memberStream);
/* 47 */     this.priorityCalling = ASNBoolean.decode(memberStream);
/* 48 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 53 */     Collection lines = new ArrayList();
/*    */ 
/* 55 */     lines.add("LucentConsultationCall ::=");
/* 56 */     lines.add("{");
/*    */ 
/* 58 */     String indent = "  ";
/*    */ 
/* 60 */     lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
/* 61 */     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
/* 62 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 64 */     lines.add("}");
/* 65 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 70 */     return 2;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentConsultationCall
 * JD-Core Version:    0.5.4
 */