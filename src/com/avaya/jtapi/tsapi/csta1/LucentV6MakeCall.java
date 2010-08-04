/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV6MakeCall extends LucentMakeCall
/*    */ {
/*    */   public static final int PDU = 110;
/*    */ 
/*    */   public LucentV6MakeCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentV6MakeCall(String _destRoute, boolean _priorityCalling, LucentUserToUserInfo _userInfo)
/*    */   {
/* 29 */     super(_destRoute, _priorityCalling, _userInfo);
/*    */   }
/*    */ 
/*    */   public static LucentMakeCall decode(InputStream in)
/*    */   {
/* 34 */     LucentV6MakeCall _this = new LucentV6MakeCall();
/* 35 */     _this.doDecode(in);
/*    */ 
/* 37 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 41 */     super.encodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 46 */     super.decodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 52 */     Collection lines = new ArrayList();
/*    */ 
/* 54 */     lines.add("LucentV6MakeCall ::=");
/* 55 */     lines.add("{");
/*    */ 
/* 57 */     String indent = "  ";
/*    */ 
/* 59 */     lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
/* 60 */     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
/* 61 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 63 */     lines.add("}");
/* 64 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 69 */     return 110;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6MakeCall
 * JD-Core Version:    0.5.4
 */