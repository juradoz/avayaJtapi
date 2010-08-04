/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAMonitorConfEvent extends CSTAConfirmation
/*    */ {
/*    */   int monitorCrossRefID;
/*    */   CSTAMonitorFilter monitorFilter;
/*    */   public static final int PDU = 114;
/*    */ 
/*    */   public static CSTAMonitorConfEvent decode(InputStream in)
/*    */   {
/* 18 */     CSTAMonitorConfEvent _this = new CSTAMonitorConfEvent();
/* 19 */     _this.doDecode(in);
/*    */ 
/* 21 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 25 */     ASNInteger.encode(this.monitorCrossRefID, memberStream);
/* 26 */     CSTAMonitorFilter.encode(this.monitorFilter, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 30 */     this.monitorCrossRefID = CSTAMonitorCrossRefID.decode(memberStream);
/* 31 */     this.monitorFilter = CSTAMonitorFilter.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 36 */     Collection lines = new ArrayList();
/*    */ 
/* 38 */     lines.add("CSTAMonitorConfEvent ::=");
/* 39 */     lines.add("{");
/*    */ 
/* 41 */     String indent = "  ";
/*    */ 
/* 43 */     lines.addAll(CSTAMonitorCrossRefID.print(this.monitorCrossRefID, "monitorCrossRefID", indent));
/* 44 */     lines.addAll(CSTAMonitorFilter.print(this.monitorFilter, "monitorFilter", indent));
/*    */ 
/* 46 */     lines.add("}");
/* 47 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 52 */     return 114;
/*    */   }
/*    */ 
/*    */   public int getMonitorCrossRefID()
/*    */   {
/* 58 */     return this.monitorCrossRefID;
/*    */   }
/*    */ 
/*    */   public CSTAMonitorFilter getMonitorFilter()
/*    */   {
/* 66 */     return this.monitorFilter;
/*    */   }
/*    */ 
/*    */   public void setMonitorFilter(CSTAMonitorFilter filter)
/*    */   {
/* 71 */     this.monitorFilter = filter;
/*    */   }
/*    */   public void setMonitorCrossRefID(int crossRef) {
/* 74 */     this.monitorCrossRefID = crossRef;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMonitorConfEvent
 * JD-Core Version:    0.5.4
 */