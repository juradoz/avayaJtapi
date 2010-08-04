/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentMonitorConfEvent extends LucentPrivateData
/*    */ {
/*    */   int usedFilter;
/*    */   static final int PDU = 30;
/*    */ 
/*    */   static LucentMonitorConfEvent decode(InputStream in)
/*    */   {
/* 14 */     LucentMonitorConfEvent _this = new LucentMonitorConfEvent();
/* 15 */     _this.doDecode(in);
/*    */ 
/* 17 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 22 */     this.usedFilter = LucentPrivateFilter.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 27 */     Collection lines = new ArrayList();
/*    */ 
/* 29 */     lines.add("LucentMonitorConfEvent ::=");
/* 30 */     lines.add("{");
/*    */ 
/* 32 */     String indent = "  ";
/*    */ 
/* 34 */     lines.addAll(LucentPrivateFilter.print(this.usedFilter, "usedFilter", indent));
/*    */ 
/* 36 */     lines.add("}");
/* 37 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 42 */     return 30;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMonitorConfEvent
 * JD-Core Version:    0.5.4
 */