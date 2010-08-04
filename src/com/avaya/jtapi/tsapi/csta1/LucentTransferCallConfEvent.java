/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentTransferCallConfEvent extends LucentPrivateData
/*    */   implements HasUCID
/*    */ {
/*    */   String ucid;
/*    */   static final int PDU = 91;
/*    */ 
/*    */   public static LucentTransferCallConfEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentTransferCallConfEvent _this = new LucentTransferCallConfEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 23 */     this.ucid = UCID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 27 */     UCID.encode(this.ucid, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 32 */     Collection lines = new ArrayList();
/*    */ 
/* 34 */     lines.add("LucentTransferCallConfEvent ::=");
/* 35 */     lines.add("{");
/*    */ 
/* 37 */     String indent = "  ";
/*    */ 
/* 39 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/*    */ 
/* 41 */     lines.add("}");
/* 42 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 47 */     return 91;
/*    */   }
/*    */ 
/*    */   public String getUcid()
/*    */   {
/* 53 */     return this.ucid;
/*    */   }
/*    */   public void setUcid(String ucid) {
/* 56 */     this.ucid = ucid;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentTransferCallConfEvent
 * JD-Core Version:    0.5.4
 */