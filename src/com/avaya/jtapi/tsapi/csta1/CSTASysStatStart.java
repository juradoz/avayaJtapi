/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTASysStatStart extends CSTARequest
/*    */ {
/*    */   private int statusFilter;
/*    */   public static final int PDU = 100;
/*    */ 
/*    */   public CSTASysStatStart()
/*    */   {
/* 19 */     this.statusFilter = 0;
/*    */   }
/*    */ 
/*    */   public CSTASysStatStart(int filter) {
/* 23 */     this.statusFilter = filter;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 31 */     return 100;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 35 */     SystemStatusFilter.encode(this.statusFilter, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTASysStatStart decode(InputStream in) {
/* 39 */     CSTASysStatStart _this = new CSTASysStatStart();
/* 40 */     _this.doDecode(in);
/* 41 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 45 */     this.statusFilter = SystemStatusFilter.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 49 */     Collection lines = new ArrayList();
/* 50 */     lines.add("CSTASysStatStart ::=");
/* 51 */     lines.add("{");
/*    */ 
/* 53 */     String indent = "  ";
/*    */ 
/* 55 */     lines.addAll(SystemStatusFilter.print(this.statusFilter, "statusFilter", indent));
/*    */ 
/* 57 */     lines.add("}");
/* 58 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASysStatStart
 * JD-Core Version:    0.5.4
 */