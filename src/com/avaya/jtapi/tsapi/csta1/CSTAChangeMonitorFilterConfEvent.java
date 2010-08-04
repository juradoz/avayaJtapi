/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAChangeMonitorFilterConfEvent extends CSTAConfirmation
/*    */ {
/*    */   CSTAMonitorFilter monitorFilter;
/*    */   public static final int PDU = 116;
/*    */ 
/*    */   public static CSTAChangeMonitorFilterConfEvent decode(InputStream in)
/*    */   {
/* 17 */     CSTAChangeMonitorFilterConfEvent _this = new CSTAChangeMonitorFilterConfEvent();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.monitorFilter = CSTAMonitorFilter.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 29 */     CSTAMonitorFilter.encode(this.monitorFilter, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 33 */     Collection lines = new ArrayList();
/* 34 */     lines.add("CSTAChangeMonitorFilterConfEvent ::=");
/* 35 */     lines.add("{");
/*    */ 
/* 37 */     String indent = "  ";
/*    */ 
/* 39 */     lines.addAll(CSTAMonitorFilter.print(this.monitorFilter, "monitorFilter", indent));
/*    */ 
/* 41 */     lines.add("}");
/* 42 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 47 */     return 116;
/*    */   }
/*    */ 
/*    */   public CSTAMonitorFilter getMonitorFilter()
/*    */   {
/* 53 */     return this.monitorFilter;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAChangeMonitorFilterConfEvent
 * JD-Core Version:    0.5.4
 */