/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentQueryAcdSplit extends LucentPrivateData
/*    */ {
/*    */   String device;
/*    */   public static final int PDU = 11;
/*    */ 
/*    */   public LucentQueryAcdSplit()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentQueryAcdSplit(String _device)
/*    */   {
/* 17 */     this.device = _device;
/*    */   }
/*    */ 
/*    */   public static LucentQueryAcdSplit decode(InputStream in)
/*    */   {
/* 22 */     LucentQueryAcdSplit _this = new LucentQueryAcdSplit();
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
/* 35 */     DeviceID.encode(this.device, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 40 */     Collection lines = new ArrayList();
/* 41 */     lines.add("LucentQueryAcdSplit ::=");
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
/* 54 */     return 11;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryAcdSplit
 * JD-Core Version:    0.5.4
 */