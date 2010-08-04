/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAQueryCallMonitorConfEvent extends CSTAConfirmation
/*    */ {
/*    */   boolean callMonitor;
/*    */   public static final int PDU = 129;
/*    */ 
/*    */   public static CSTAQueryCallMonitorConfEvent decode(InputStream in)
/*    */   {
/* 17 */     CSTAQueryCallMonitorConfEvent _this = new CSTAQueryCallMonitorConfEvent();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.callMonitor = ASNBoolean.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 30 */     Collection lines = new ArrayList();
/* 31 */     lines.add("CSTAQueryCallMonitorConfEvent ::=");
/* 32 */     lines.add("{");
/*    */ 
/* 34 */     String indent = "  ";
/*    */ 
/* 36 */     lines.addAll(ASNBoolean.print(this.callMonitor, "callMonitor", indent));
/*    */ 
/* 38 */     lines.add("}");
/* 39 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 44 */     return 129;
/*    */   }
/*    */ 
/*    */   public boolean isCallMonitor()
/*    */   {
/* 50 */     return this.callMonitor;
/*    */   }
/*    */ 
/*    */   public void setCallMonitor(boolean callMonitor) {
/* 54 */     this.callMonitor = callMonitor;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryCallMonitorConfEvent
 * JD-Core Version:    0.5.4
 */