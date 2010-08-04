/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAMonitorCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID call;
/*    */   CSTAMonitorFilter monitorFilter;
/*    */   public static final int PDU = 112;
/*    */ 
/*    */   public CSTAMonitorCall(CSTAConnectionID _call, CSTAMonitorFilter _monitorFilter)
/*    */   {
/* 19 */     this.call = _call;
/* 20 */     this.monitorFilter = _monitorFilter;
/*    */   }
/*    */ 
/*    */   public CSTAMonitorCall() {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 27 */     CSTAConnectionID.encode(this.call, memberStream);
/* 28 */     CSTAMonitorFilter.encode(this.monitorFilter, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAMonitorCall decode(InputStream in) {
/* 32 */     CSTAMonitorCall _this = new CSTAMonitorCall();
/* 33 */     _this.doDecode(in);
/*    */ 
/* 35 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 40 */     this.call = CSTAConnectionID.decode(memberStream);
/* 41 */     this.monitorFilter = CSTAMonitorFilter.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 46 */     Collection lines = new ArrayList();
/* 47 */     lines.add("CSTAMonitorCall ::=");
/* 48 */     lines.add("{");
/*    */ 
/* 50 */     String indent = "  ";
/*    */ 
/* 52 */     lines.addAll(CSTAConnectionID.print(this.call, "call", indent));
/* 53 */     lines.addAll(CSTAMonitorFilter.print(this.monitorFilter, "monitorFilter", indent));
/*    */ 
/* 55 */     lines.add("}");
/* 56 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 61 */     return 112;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getCall()
/*    */   {
/* 67 */     return this.call;
/*    */   }
/*    */ 
/*    */   public CSTAMonitorFilter getMonitorFilter()
/*    */   {
/* 75 */     return this.monitorFilter;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMonitorCall
 * JD-Core Version:    0.5.4
 */