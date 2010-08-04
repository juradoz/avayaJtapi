/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV7RouteSelect extends LucentRouteSelect
/*    */ {
/*    */   short useNCR;
/*    */   public static final int PDU = 126;
/*    */ 
/*    */   public LucentV7RouteSelect()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentV7RouteSelect(String _callingDevice, String _directAgentCallSplit, boolean _priorityCalling, String _destRoute, LucentUserCollectCode _collectCode, LucentUserProvidedCode _userProvidedCode, LucentUserToUserInfo _userInfo, short _useNCR)
/*    */   {
/* 40 */     super(_callingDevice, _directAgentCallSplit, _priorityCalling, _destRoute, _collectCode, _userProvidedCode, _userInfo);
/* 41 */     this.useNCR = _useNCR;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 46 */     super.encodeMembers(memberStream);
/* 47 */     ASNEnumerated.encode(this.useNCR, memberStream);
/*    */   }
/*    */ 
/*    */   public static LucentV7RouteSelect decode(InputStream in) {
/* 51 */     LucentV7RouteSelect _this = new LucentV7RouteSelect();
/* 52 */     _this.doDecode(in);
/*    */ 
/* 54 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 59 */     super.decodeMembers(memberStream);
/* 60 */     this.useNCR = ASNEnumerated.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 65 */     Collection lines = new ArrayList();
/*    */ 
/* 67 */     lines.add("LucentV7RouteSelect ::=");
/* 68 */     lines.add("{");
/*    */ 
/* 70 */     String indent = "  ";
/*    */ 
/* 72 */     lines.addAll(DeviceID.print(this.callingDevice, "callingDevice", indent));
/* 73 */     lines.addAll(DeviceID.print(this.directAgentCallSplit, "directAgentCallSplit", indent));
/* 74 */     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
/* 75 */     lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
/* 76 */     lines.addAll(LucentUserCollectCode.print(this.collectCode, "collectCode", indent));
/* 77 */     lines.addAll(LucentUserProvidedCode.print(this.userProvidedCode, "userProvidedCode", indent));
/* 78 */     lines.addAll(LucentRedirectType.print(this.useNCR, "useNCR", indent));
/* 79 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/* 80 */     lines.add("}");
/* 81 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 86 */     return 126;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7RouteSelect
 * JD-Core Version:    0.5.4
 */