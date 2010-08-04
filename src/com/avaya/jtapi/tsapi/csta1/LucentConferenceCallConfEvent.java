/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentConferenceCallConfEvent extends LucentPrivateData
/*    */   implements HasUCID
/*    */ {
/*    */   String ucid;
/*    */   static final int PDU = 90;
/*    */ 
/*    */   public static LucentConferenceCallConfEvent decode(InputStream in)
/*    */   {
/* 16 */     LucentConferenceCallConfEvent _this = new LucentConferenceCallConfEvent();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 24 */     this.ucid = UCID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 29 */     UCID.encode(this.ucid, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 34 */     Collection lines = new ArrayList();
/*    */ 
/* 36 */     lines.add("LucentConferenceCallConfEvent ::=");
/* 37 */     lines.add("{");
/*    */ 
/* 39 */     String indent = "  ";
/*    */ 
/* 41 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/*    */ 
/* 43 */     lines.add("}");
/* 44 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 49 */     return 90;
/*    */   }
/*    */ 
/*    */   public String getUcid()
/*    */   {
/* 55 */     return this.ucid;
/*    */   }
/*    */   public void setUcid(String ucid) {
/* 58 */     this.ucid = ucid;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentConferenceCallConfEvent
 * JD-Core Version:    0.5.4
 */