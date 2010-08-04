/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentMonitorFilter extends LucentPrivateData
/*    */ {
/*    */   int privateFilter;
/*    */   static final int PDU = 29;
/*    */ 
/*    */   LucentMonitorFilter(int _privateFilter)
/*    */   {
/* 16 */     this.privateFilter = _privateFilter;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 21 */     LucentPrivateFilter.encode(this.privateFilter, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 26 */     Collection lines = new ArrayList();
/*    */ 
/* 28 */     lines.add("LucentMonitorFilter ::=");
/* 29 */     lines.add("{");
/*    */ 
/* 31 */     String indent = "  ";
/*    */ 
/* 33 */     lines.addAll(LucentPrivateFilter.print(this.privateFilter, "privateFilter", indent));
/*    */ 
/* 35 */     lines.add("}");
/* 36 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 41 */     return 29;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMonitorFilter
 * JD-Core Version:    0.5.4
 */