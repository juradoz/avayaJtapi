/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentQueryAgentLogin extends LucentPrivateData
/*    */ {
/*    */   String device;
/*    */   public static final int PDU = 13;
/*    */ 
/*    */   public LucentQueryAgentLogin()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentQueryAgentLogin(String _device)
/*    */   {
/* 18 */     this.device = _device;
/*    */   }
/*    */ 
/*    */   public static LucentQueryAgentLogin decode(InputStream in) {
/* 22 */     LucentQueryAgentLogin _this = new LucentQueryAgentLogin();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 25 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 29 */     this.device = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 34 */     DeviceID.encode(this.device, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 39 */     Collection lines = new ArrayList();
/*    */ 
/* 41 */     lines.add("LucentQueryAgentLogin ::=");
/* 42 */     lines.add("{");
/*    */ 
/* 44 */     String indent = "  ";
/*    */ 
/* 46 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/*    */ 
/* 48 */     lines.add("}");
/* 49 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 54 */     return 13;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLogin
 * JD-Core Version:    0.5.4
 */