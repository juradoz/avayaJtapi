/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAChangeMonitorFilter extends CSTARequest
/*    */ {
/*    */   int monitorCrossRefID;
/*    */   CSTAMonitorFilter monitorFilter;
/*    */   public static final int PDU = 115;
/*    */ 
/*    */   public CSTAChangeMonitorFilter(int _monitorCrossRefID, CSTAMonitorFilter _monitorFilter)
/*    */   {
/* 19 */     this.monitorCrossRefID = _monitorCrossRefID;
/* 20 */     this.monitorFilter = _monitorFilter;
/*    */   }
/*    */   public CSTAChangeMonitorFilter() {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 26 */     CSTAMonitorCrossRefID.encode(this.monitorCrossRefID, memberStream);
/* 27 */     CSTAMonitorFilter.encode(this.monitorFilter, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAChangeMonitorFilter decode(InputStream in)
/*    */   {
/* 32 */     CSTAChangeMonitorFilter _this = new CSTAChangeMonitorFilter();
/* 33 */     _this.doDecode(in);
/*    */ 
/* 35 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 39 */     this.monitorCrossRefID = CSTAMonitorCrossRefID.decode(memberStream);
/* 40 */     this.monitorFilter = CSTAMonitorFilter.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 45 */     Collection lines = new ArrayList();
/* 46 */     lines.add("CSTAChangeMonitorFilter ::=");
/* 47 */     lines.add("{");
/*    */ 
/* 49 */     String indent = "  ";
/*    */ 
/* 51 */     lines.addAll(CSTAMonitorCrossRefID.print(this.monitorCrossRefID, "monitorCrossRefID", indent));
/* 52 */     lines.addAll(CSTAMonitorFilter.print(this.monitorFilter, "monitorFilter", indent));
/*    */ 
/* 54 */     lines.add("}");
/* 55 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 60 */     return 115;
/*    */   }
/*    */ 
/*    */   public int getMonitorCrossRefID()
/*    */   {
/* 66 */     return this.monitorCrossRefID;
/*    */   }
/*    */ 
/*    */   public CSTAMonitorFilter getMonitorFilter()
/*    */   {
/* 74 */     return this.monitorFilter;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAChangeMonitorFilter
 * JD-Core Version:    0.5.4
 */