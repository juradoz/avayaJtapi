/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTASysStatStartConfEvent extends CSTAConfirmation
/*    */ {
/*    */   private int filter;
/*    */   public static final int PDU = 101;
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 21 */     return 101;
/*    */   }
/*    */ 
/*    */   public static CSTASysStatStartConfEvent decode(InputStream in)
/*    */   {
/* 26 */     CSTASysStatStartConfEvent _this = new CSTASysStatStartConfEvent();
/* 27 */     _this.doDecode(in);
/*    */ 
/* 29 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 34 */     this.filter = SystemStatusFilter.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 39 */     SystemStatusFilter.encode(this.filter, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 44 */     Collection lines = new ArrayList();
/* 45 */     lines.add("CSTASysStatStartConfEvent ::=");
/* 46 */     lines.add("{");
/* 47 */     String indent = "  ";
/* 48 */     lines.addAll(SystemStatusFilter.print(this.filter, "filter", indent));
/* 49 */     lines.add("}");
/* 50 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getFilter() {
/* 54 */     return this.filter;
/*    */   }
/*    */ 
/*    */   public void setFilter(int filter) {
/* 58 */     this.filter = filter;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASysStatStartConfEvent
 * JD-Core Version:    0.5.4
 */