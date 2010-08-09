 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentV6MakePredictiveCall extends LucentMakePredictiveCall
 {
   public static final int PDU = 112;
 
   public LucentV6MakePredictiveCall()
   {
   }
 
   public LucentV6MakePredictiveCall(boolean _priorityCalling, int _maxRings, short _answerTreat, String _destRoute, LucentUserToUserInfo _userInfo)
   {
     super(_priorityCalling, _maxRings, _answerTreat, _destRoute, _userInfo);
   }
 
   public static LucentMakePredictiveCall decode(InputStream in) {
     LucentV6MakePredictiveCall _this = new LucentV6MakePredictiveCall();
     _this.doDecode(in);
 
     return _this;
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentV6MakePredictiveCall ::=");
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
     return 112;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6MakePredictiveCall
 * JD-Core Version:    0.5.4
 */