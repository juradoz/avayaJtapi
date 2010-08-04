/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentMonitorStopOnCall extends LucentPrivateData
/*    */ {
/*    */   int monitorCrossRefID;
/*    */   CSTAConnectionID call;
/*    */   static final int PDU = 31;
/*    */ 
/*    */   public LucentMonitorStopOnCall(int _monitorCrossRefID, CSTAConnectionID _call)
/*    */   {
/* 18 */     this.monitorCrossRefID = _monitorCrossRefID;
/* 19 */     this.call = _call;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 24 */     CSTAMonitorCrossRefID.encode(this.monitorCrossRefID, memberStream);
/* 25 */     CSTAConnectionID.encode(this.call, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 30 */     Collection lines = new ArrayList();
/*    */ 
/* 32 */     lines.add("LucentMonitorStopOnCall ::=");
/* 33 */     lines.add("{");
/*    */ 
/* 35 */     String indent = "  ";
/*    */ 
/* 37 */     lines.addAll(CSTAMonitorCrossRefID.print(this.monitorCrossRefID, "monitorCrossRefID", indent));
/* 38 */     lines.addAll(CSTAConnectionID.print(this.call, "call", indent));
/*    */ 
/* 40 */     lines.add("}");
/* 41 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 46 */     return 31;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMonitorStopOnCall
 * JD-Core Version:    0.5.4
 */