/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentConsultationCallConfEvent extends LucentPrivateData
/*    */   implements HasUCID
/*    */ {
/*    */   String ucid;
/*    */   static final int PDU = 84;
/*    */ 
/*    */   public static LucentConsultationCallConfEvent decode(InputStream in)
/*    */   {
/* 14 */     LucentConsultationCallConfEvent _this = new LucentConsultationCallConfEvent();
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
/*    */   public Collection<String> print()
/*    */   {
/* 31 */     Collection lines = new ArrayList();
/*    */ 
/* 33 */     lines.add("LucentConsultationCallConfEvent ::=");
/* 34 */     lines.add("{");
/*    */ 
/* 36 */     String indent = "  ";
/*    */ 
/* 38 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/*    */ 
/* 40 */     lines.add("}");
/* 41 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 46 */     return 84;
/*    */   }
/*    */ 
/*    */   public String getUcid()
/*    */   {
/* 52 */     return this.ucid;
/*    */   }
/*    */ 
/*    */   public void setUcid(String ucid) {
/* 56 */     this.ucid = ucid;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentConsultationCallConfEvent
 * JD-Core Version:    0.5.4
 */