/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentV6ConnectionClearedEvent extends LucentConnectionClearedEvent
/*    */ {
/*    */   static final int PDU = 115;
/*    */ 
/*    */   static LucentConnectionClearedEvent decode(InputStream in)
/*    */   {
/* 22 */     LucentV6ConnectionClearedEvent _this = new LucentV6ConnectionClearedEvent();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 25 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 29 */     super.decodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 34 */     super.encodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 39 */     Collection lines = new ArrayList();
/*    */ 
/* 41 */     lines.add("LucentV6ConnectionClearedEvent ::=");
/* 42 */     lines.add("{");
/*    */ 
/* 44 */     String indent = "  ";
/*    */ 
/* 46 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 48 */     lines.add("}");
/* 49 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 54 */     return 115;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6ConnectionClearedEvent
 * JD-Core Version:    0.5.4
 */