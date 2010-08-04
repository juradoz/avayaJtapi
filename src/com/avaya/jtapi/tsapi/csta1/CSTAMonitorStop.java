/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAMonitorStop extends CSTARequest
/*    */ {
/*    */   int monitorCrossRefID;
/*    */   public static final int PDU = 117;
/*    */ 
/*    */   public CSTAMonitorStop()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAMonitorStop(int _monitorCrossRefID)
/*    */   {
/* 18 */     this.monitorCrossRefID = _monitorCrossRefID;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 23 */     CSTAMonitorCrossRefID.encode(this.monitorCrossRefID, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAMonitorStop decode(InputStream in)
/*    */   {
/* 28 */     CSTAMonitorStop _this = new CSTAMonitorStop();
/* 29 */     _this.doDecode(in);
/*    */ 
/* 31 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 36 */     this.monitorCrossRefID = CSTAMonitorCrossRefID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 41 */     Collection lines = new ArrayList();
/*    */ 
/* 43 */     lines.add("CSTAMonitorStop ::=");
/* 44 */     lines.add("{");
/*    */ 
/* 46 */     String indent = "  ";
/*    */ 
/* 48 */     lines.addAll(CSTAMonitorCrossRefID.print(this.monitorCrossRefID, "monitorCrossRefID", indent));
/*    */ 
/* 50 */     lines.add("}");
/* 51 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 56 */     return 117;
/*    */   }
/*    */ 
/*    */   public int getMonitorCrossRefID()
/*    */   {
/* 62 */     return this.monitorCrossRefID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMonitorStop
 * JD-Core Version:    0.5.4
 */