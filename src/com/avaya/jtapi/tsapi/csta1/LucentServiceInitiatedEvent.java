/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentServiceInitiatedEvent extends LucentPrivateData
/*    */   implements HasUCID
/*    */ {
/*    */   String ucid;
/*    */   static final int PDU = 95;
/*    */ 
/*    */   public static LucentServiceInitiatedEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentServiceInitiatedEvent _this = new LucentServiceInitiatedEvent();
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
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 28 */     UCID.encode(this.ucid, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 33 */     Collection lines = new ArrayList();
/*    */ 
/* 35 */     lines.add("LucentServiceInitiatedEvent ::=");
/* 36 */     lines.add("{");
/*    */ 
/* 38 */     String indent = "  ";
/*    */ 
/* 40 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/*    */ 
/* 42 */     lines.add("}");
/* 43 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 48 */     return 95;
/*    */   }
/*    */ 
/*    */   public String getUcid()
/*    */   {
/* 54 */     return this.ucid;
/*    */   }
/*    */ 
/*    */   public void setUcid(String ucid) {
/* 58 */     this.ucid = ucid;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentServiceInitiatedEvent
 * JD-Core Version:    0.5.4
 */