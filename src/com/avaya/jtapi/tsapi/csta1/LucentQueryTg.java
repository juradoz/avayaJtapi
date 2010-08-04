/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentQueryTg extends LucentPrivateData
/*    */ {
/*    */   String device;
/*    */   static final int PDU = 26;
/*    */ 
/*    */   public LucentQueryTg()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentQueryTg(String _device)
/*    */   {
/* 19 */     this.device = _device;
/*    */   }
/*    */ 
/*    */   public static LucentQueryTg decode(InputStream in)
/*    */   {
/* 24 */     LucentQueryTg _this = new LucentQueryTg();
/* 25 */     _this.doDecode(in);
/*    */ 
/* 27 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 33 */     DeviceID.encode(this.device, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 38 */     Collection lines = new ArrayList();
/*    */ 
/* 40 */     lines.add("LucentQueryTg ::=");
/* 41 */     lines.add("{");
/*    */ 
/* 43 */     String indent = "  ";
/*    */ 
/* 45 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/*    */ 
/* 47 */     lines.add("}");
/* 48 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 53 */     return 26;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryTg
 * JD-Core Version:    0.5.4
 */