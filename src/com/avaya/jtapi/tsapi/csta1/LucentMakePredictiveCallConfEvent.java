/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentMakePredictiveCallConfEvent extends LucentPrivateData
/*    */   implements HasUCID
/*    */ {
/*    */   String ucid;
/*    */   static final int PDU = 86;
/*    */ 
/*    */   public static LucentMakePredictiveCallConfEvent decode(InputStream in)
/*    */   {
/* 14 */     LucentMakePredictiveCallConfEvent _this = new LucentMakePredictiveCallConfEvent();
/* 15 */     _this.doDecode(in);
/*    */ 
/* 17 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 22 */     this.ucid = UCID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 26 */     UCID.encode(this.ucid, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 30 */     Collection lines = new ArrayList();
/*    */ 
/* 32 */     lines.add("LucentMakePredictiveCallConfEvent ::=");
/* 33 */     lines.add("{");
/*    */ 
/* 35 */     String indent = "  ";
/*    */ 
/* 37 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/*    */ 
/* 39 */     lines.add("}");
/* 40 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 45 */     return 86;
/*    */   }
/*    */ 
/*    */   public String getUcid()
/*    */   {
/* 51 */     return this.ucid;
/*    */   }
/*    */ 
/*    */   public void setUcid(String ucid) {
/* 55 */     this.ucid = ucid;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMakePredictiveCallConfEvent
 * JD-Core Version:    0.5.4
 */