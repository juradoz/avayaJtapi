/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV6DirectAgentCall extends LucentDirectAgentCall
/*    */ {
/*    */   public static final int PDU = 111;
/*    */ 
/*    */   public LucentV6DirectAgentCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentV6DirectAgentCall(String _split, boolean _priorityCalling, LucentUserToUserInfo _userInfo)
/*    */   {
/* 28 */     super(_split, _priorityCalling, _userInfo);
/*    */   }
/*    */ 
/*    */   public static LucentDirectAgentCall decode(InputStream in)
/*    */   {
/* 33 */     LucentV6DirectAgentCall _this = new LucentV6DirectAgentCall();
/* 34 */     _this.doDecode(in);
/*    */ 
/* 36 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 41 */     super.encodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 47 */     super.decodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 53 */     Collection lines = new ArrayList();
/*    */ 
/* 55 */     lines.add("LucentV6DirectAgentCall ::=");
/* 56 */     lines.add("{");
/*    */ 
/* 58 */     String indent = "  ";
/*    */ 
/* 60 */     lines.addAll(DeviceID.print(this.split, "split", indent));
/* 61 */     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
/* 62 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 64 */     lines.add("}");
/* 65 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 70 */     return 111;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6DirectAgentCall
 * JD-Core Version:    0.5.4
 */