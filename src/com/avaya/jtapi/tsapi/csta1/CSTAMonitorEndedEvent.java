/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAMonitorEndedEvent extends CSTAUnsolicited
/*    */ {
/*    */   short cause;
/*    */   public static final int PDU = 119;
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 17 */     CSTAEventCause.encode(this.cause, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAMonitorEndedEvent decode(InputStream in)
/*    */   {
/* 22 */     CSTAMonitorEndedEvent _this = new CSTAMonitorEndedEvent();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 25 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 30 */     this.cause = CSTAEventCause.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 35 */     Collection lines = new ArrayList();
/*    */ 
/* 37 */     lines.add("CSTAMonitorEndedEvent ::=");
/* 38 */     lines.add("{");
/*    */ 
/* 40 */     String indent = "  ";
/* 41 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/* 42 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*    */ 
/* 44 */     lines.add("}");
/* 45 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 50 */     return 119;
/*    */   }
/*    */ 
/*    */   public short getCause()
/*    */   {
/* 56 */     return this.cause;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMonitorEndedEvent
 * JD-Core Version:    0.5.4
 */