/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentMakePredictiveCall extends LucentPrivateData
/*    */ {
/*    */   boolean priorityCalling;
/*    */   int maxRings;
/*    */   short answerTreat;
/*    */   String destRoute;
/*    */   LucentUserToUserInfo userInfo;
/*    */   static final int PDU = 5;
/*    */ 
/*    */   public LucentMakePredictiveCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentMakePredictiveCall(boolean _priorityCalling, int _maxRings, short _answerTreat, String _destRoute, LucentUserToUserInfo _userInfo)
/*    */   {
/* 28 */     this.priorityCalling = _priorityCalling;
/* 29 */     this.maxRings = _maxRings;
/* 30 */     this.answerTreat = _answerTreat;
/* 31 */     this.destRoute = _destRoute;
/* 32 */     this.userInfo = _userInfo;
/*    */   }
/*    */ 
/*    */   public static LucentMakePredictiveCall decode(InputStream in) {
/* 36 */     LucentMakePredictiveCall _this = new LucentMakePredictiveCall();
/* 37 */     _this.doDecode(in);
/*    */ 
/* 39 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 43 */     this.priorityCalling = ASNBoolean.decode(memberStream);
/* 44 */     this.maxRings = ASNInteger.decode(memberStream);
/* 45 */     this.answerTreat = LucentAnswerTreat.decode(memberStream);
/* 46 */     this.destRoute = DeviceID.decode(memberStream);
/* 47 */     this.userInfo = LucentUserToUserInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 51 */     ASNBoolean.encode(this.priorityCalling, memberStream);
/* 52 */     ASNInteger.encode(this.maxRings, memberStream);
/* 53 */     LucentAnswerTreat.encode(this.answerTreat, memberStream);
/* 54 */     DeviceID.encode(this.destRoute, memberStream);
/* 55 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 60 */     Collection lines = new ArrayList();
/*    */ 
/* 62 */     lines.add("LucentMakePredictiveCall ::=");
/* 63 */     lines.add("{");
/*    */ 
/* 65 */     String indent = "  ";
/*    */ 
/* 67 */     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
/* 68 */     lines.addAll(ASNInteger.print(this.maxRings, "maxRings", indent));
/* 69 */     lines.addAll(LucentAnswerTreat.print(this.answerTreat, "answerTreat", indent));
/* 70 */     lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
/* 71 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 73 */     lines.add("}");
/* 74 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 79 */     return 5;
/*    */   }
/*    */   public short getAnswerTreat() {
/* 82 */     return this.answerTreat;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMakePredictiveCall
 * JD-Core Version:    0.5.4
 */