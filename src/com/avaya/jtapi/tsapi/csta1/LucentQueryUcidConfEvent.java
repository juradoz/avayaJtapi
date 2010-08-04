/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentQueryUcidConfEvent extends LucentPrivateData
/*    */   implements HasUCID
/*    */ {
/*    */   String ucid;
/*    */   public static final int PDU = 77;
/*    */ 
/*    */   public static LucentQueryUcidConfEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentQueryUcidConfEvent _this = new LucentQueryUcidConfEvent();
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
/*    */   public Collection<String> print() {
/* 31 */     Collection lines = new ArrayList();
/*    */ 
/* 33 */     lines.add("LucentQueryUcidConfEvent ::=");
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
/* 46 */     return 77;
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
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryUcidConfEvent
 * JD-Core Version:    0.5.4
 */