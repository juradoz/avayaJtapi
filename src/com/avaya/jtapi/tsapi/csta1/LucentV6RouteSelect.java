/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentV6RouteSelect extends LucentRouteSelect
/*    */ {
/*    */   static final int PDU = 116;
/*    */ 
/*    */   public LucentV6RouteSelect(String _callingDevice, String _directAgentCallSplit, boolean _priorityCalling, String _destRoute, LucentUserCollectCode _collectCode, LucentUserProvidedCode _userProvidedCode, LucentUserToUserInfo _userInfo)
/*    */   {
/* 29 */     super(_callingDevice, _directAgentCallSplit, _priorityCalling, _destRoute, _collectCode, _userProvidedCode, _userInfo);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 34 */     Collection lines = new ArrayList();
/*    */ 
/* 36 */     lines.add("LucentV6RouteSelect ::=");
/* 37 */     lines.add("{");
/*    */ 
/* 39 */     String indent = "  ";
/*    */ 
/* 41 */     lines.addAll(DeviceID.print(this.callingDevice, "callingDevice", indent));
/* 42 */     lines.addAll(DeviceID.print(this.directAgentCallSplit, "directAgentCallSplit", indent));
/* 43 */     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
/* 44 */     lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
/* 45 */     lines.addAll(LucentUserCollectCode.print(this.collectCode, "collectCode", indent));
/* 46 */     lines.addAll(LucentUserProvidedCode.print(this.userProvidedCode, "userProvidedCode", indent));
/* 47 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 49 */     lines.add("}");
/* 50 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 55 */     return 116;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6RouteSelect
 * JD-Core Version:    0.5.4
 */