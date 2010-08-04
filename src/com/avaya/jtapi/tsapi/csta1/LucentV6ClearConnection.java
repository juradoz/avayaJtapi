/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV6ClearConnection extends LucentClearConnection
/*    */ {
/*    */   static final int PDU = 108;
/*    */ 
/*    */   public LucentV6ClearConnection()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentV6ClearConnection(short _dropResource, LucentUserToUserInfo _userInfo)
/*    */   {
/* 26 */     super(_dropResource, _userInfo);
/*    */   }
/*    */ 
/*    */   public static LucentClearConnection decode(InputStream in)
/*    */   {
/* 31 */     LucentV6ClearConnection _this = new LucentV6ClearConnection();
/* 32 */     _this.doDecode(in);
/*    */ 
/* 34 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 39 */     super.decodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 43 */     super.encodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 48 */     Collection lines = new ArrayList();
/*    */ 
/* 50 */     lines.add("LucentV6ClearConnection ::=");
/* 51 */     lines.add("{");
/*    */ 
/* 53 */     String indent = "  ";
/*    */ 
/* 55 */     lines.addAll(LucentDropResource.print(this.dropResource, "dropResource", indent));
/* 56 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 58 */     lines.add("}");
/* 59 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 64 */     return 108;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6ClearConnection
 * JD-Core Version:    0.5.4
 */