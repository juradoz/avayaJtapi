/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentRouteSelect extends LucentPrivateData
/*    */ {
/*    */   String callingDevice;
/*    */   String directAgentCallSplit;
/*    */   boolean priorityCalling;
/*    */   String destRoute;
/*    */   LucentUserCollectCode collectCode;
/*    */   LucentUserProvidedCode userProvidedCode;
/*    */   LucentUserToUserInfo userInfo;
/*    */   static final int PDU = 43;
/*    */ 
/*    */   public LucentRouteSelect()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentRouteSelect(String _callingDevice, String _directAgentCallSplit, boolean _priorityCalling, String _destRoute, LucentUserCollectCode _collectCode, LucentUserProvidedCode _userProvidedCode, LucentUserToUserInfo _userInfo)
/*    */   {
/* 30 */     this.callingDevice = _callingDevice;
/* 31 */     this.directAgentCallSplit = _directAgentCallSplit;
/* 32 */     this.priorityCalling = _priorityCalling;
/* 33 */     this.destRoute = _destRoute;
/* 34 */     this.collectCode = _collectCode;
/* 35 */     this.userProvidedCode = _userProvidedCode;
/* 36 */     this.userInfo = _userInfo;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 41 */     DeviceID.encode(this.callingDevice, memberStream);
/* 42 */     DeviceID.encode(this.directAgentCallSplit, memberStream);
/* 43 */     ASNBoolean.encode(this.priorityCalling, memberStream);
/* 44 */     DeviceID.encode(this.destRoute, memberStream);
/* 45 */     LucentUserCollectCode.encode(this.collectCode, memberStream);
/* 46 */     LucentUserProvidedCode.encode(this.userProvidedCode, memberStream);
/* 47 */     LucentUserToUserInfo.encode(this.userInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 52 */     Collection lines = new ArrayList();
/*    */ 
/* 54 */     lines.add("LucentRouteSelect ::=");
/* 55 */     lines.add("{");
/*    */ 
/* 57 */     String indent = "  ";
/*    */ 
/* 59 */     lines.addAll(DeviceID.print(this.callingDevice, "callingDevice", indent));
/* 60 */     lines.addAll(DeviceID.print(this.directAgentCallSplit, "directAgentCallSplit", indent));
/* 61 */     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
/* 62 */     lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
/* 63 */     lines.addAll(LucentUserCollectCode.print(this.collectCode, "collectCode", indent));
/* 64 */     lines.addAll(LucentUserProvidedCode.print(this.userProvidedCode, "userProvidedCode", indent));
/* 65 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 67 */     lines.add("}");
/* 68 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 73 */     return 43;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentRouteSelect
 * JD-Core Version:    0.5.4
 */