/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAQueryAgentStateConfEvent extends CSTAConfirmation
/*    */ {
/*    */   short agentState;
/*    */   public static final int PDU = 34;
/*    */ 
/*    */   public static CSTAQueryAgentStateConfEvent decode(InputStream in)
/*    */   {
/* 17 */     CSTAQueryAgentStateConfEvent _this = new CSTAQueryAgentStateConfEvent();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.agentState = AgentState.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 29 */     AgentState.encode(this.agentState, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 34 */     Collection lines = new ArrayList();
/*    */ 
/* 36 */     lines.add("CSTAQueryAgentStateConfEvent ::=");
/* 37 */     lines.add("{");
/*    */ 
/* 39 */     String indent = "  ";
/*    */ 
/* 41 */     lines.addAll(AgentState.print(this.agentState, "agentState", indent));
/*    */ 
/* 43 */     lines.add("}");
/* 44 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 49 */     return 34;
/*    */   }
/*    */ 
/*    */   public short getAgentState()
/*    */   {
/* 55 */     return this.agentState;
/*    */   }
/*    */ 
/*    */   public void setAgentState(short agentState) {
/* 59 */     this.agentState = agentState;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryAgentStateConfEvent
 * JD-Core Version:    0.5.4
 */