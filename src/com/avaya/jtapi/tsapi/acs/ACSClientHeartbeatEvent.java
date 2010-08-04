/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSClientHeartbeatEvent extends ACSUnsolicited
/*    */ {
/*    */   public static final int PDU = 16;
/*    */ 
/*    */   public static ACSClientHeartbeatEvent decode(InputStream in)
/*    */   {
/* 36 */     ACSClientHeartbeatEvent _this = new ACSClientHeartbeatEvent();
/* 37 */     _this.doDecode(in);
/*    */ 
/* 39 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 49 */     Collection lines = new ArrayList();
/* 50 */     lines.add("ACSClientHeartbeatEvent ::=");
/* 51 */     lines.add("{");
/* 52 */     lines.add("}");
/* 53 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 58 */     return 16;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSClientHeartbeatEvent
 * JD-Core Version:    0.5.4
 */