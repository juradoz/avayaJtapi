 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentMakePredictiveCall extends LucentPrivateData
 {
   boolean priorityCalling;
   int maxRings;
   short answerTreat;
   String destRoute;
   LucentUserToUserInfo userInfo;
   static final int PDU = 5;
 
   public LucentMakePredictiveCall()
   {
   }
 
   public LucentMakePredictiveCall(boolean _priorityCalling, int _maxRings, short _answerTreat, String _destRoute, LucentUserToUserInfo _userInfo)
   {
     this.priorityCalling = _priorityCalling;
     this.maxRings = _maxRings;
     this.answerTreat = _answerTreat;
     this.destRoute = _destRoute;
     this.userInfo = _userInfo;
   }
 
   public static LucentMakePredictiveCall decode(InputStream in) {
     LucentMakePredictiveCall _this = new LucentMakePredictiveCall();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream) {
     this.priorityCalling = ASNBoolean.decode(memberStream);
     this.maxRings = ASNInteger.decode(memberStream);
     this.answerTreat = LucentAnswerTreat.decode(memberStream);
     this.destRoute = DeviceID.decode(memberStream);
     this.userInfo = LucentUserToUserInfo.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     ASNBoolean.encode(this.priorityCalling, memberStream);
     ASNInteger.encode(this.maxRings, memberStream);
     LucentAnswerTreat.encode(this.answerTreat, memberStream);
     DeviceID.encode(this.destRoute, memberStream);
     LucentUserToUserInfo.encode(this.userInfo, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentMakePredictiveCall ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
     lines.addAll(ASNInteger.print(this.maxRings, "maxRings", indent));
     lines.addAll(LucentAnswerTreat.print(this.answerTreat, "answerTreat", indent));
     lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 5;
   }
   public short getAnswerTreat() {
     return this.answerTreat;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMakePredictiveCall
 * JD-Core Version:    0.5.4
 */