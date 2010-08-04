/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV6MakePredictiveCall extends LucentMakePredictiveCall
/*    */ {
/*    */   public static final int PDU = 112;
/*    */ 
/*    */   public LucentV6MakePredictiveCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentV6MakePredictiveCall(boolean _priorityCalling, int _maxRings, short _answerTreat, String _destRoute, LucentUserToUserInfo _userInfo)
/*    */   {
/* 30 */     super(_priorityCalling, _maxRings, _answerTreat, _destRoute, _userInfo);
/*    */   }
/*    */ 
/*    */   public static LucentMakePredictiveCall decode(InputStream in) {
/* 34 */     LucentV6MakePredictiveCall _this = new LucentV6MakePredictiveCall();
/* 35 */     _this.doDecode(in);
/*    */ 
/* 37 */     return _this;
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 42 */     Collection lines = new ArrayList();
/*    */ 
/* 44 */     lines.add("LucentV6MakePredictiveCall ::=");
/* 45 */     lines.add("{");
/*    */ 
/* 47 */     String indent = "  ";
/*    */ 
/* 49 */     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
/* 50 */     lines.addAll(ASNInteger.print(this.maxRings, "maxRings", indent));
/* 51 */     lines.addAll(LucentAnswerTreat.print(this.answerTreat, "answerTreat", indent));
/* 52 */     lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
/* 53 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 55 */     lines.add("}");
/* 56 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 61 */     return 112;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6MakePredictiveCall
 * JD-Core Version:    0.5.4
 */