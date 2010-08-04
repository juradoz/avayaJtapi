/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV6OriginatedEvent extends LucentOriginatedEvent
/*    */ {
/*    */   static final int PDU = 119;
/*    */ 
/*    */   public static LucentOriginatedEvent decode(InputStream in)
/*    */   {
/* 21 */     LucentV6OriginatedEvent _this = new LucentV6OriginatedEvent();
/* 22 */     _this.doDecode(in);
/*    */ 
/* 24 */     return _this;
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 29 */     Collection lines = new ArrayList();
/*    */ 
/* 31 */     lines.add("LucentV6OriginatedEvent ::=");
/* 32 */     lines.add("{");
/*    */ 
/* 34 */     String indent = "  ";
/*    */ 
/* 36 */     lines.addAll(DeviceID.print(this.physicalTerminal_asn, "physicalTerminal", indent));
/* 37 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 39 */     lines.add("}");
/* 40 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 45 */     return 119;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6OriginatedEvent
 * JD-Core Version:    0.5.4
 */