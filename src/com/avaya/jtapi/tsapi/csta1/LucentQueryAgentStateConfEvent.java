/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentQueryAgentStateConfEvent extends LucentPrivateData
/*    */ {
/*    */   short workMode;
/*    */   short talkState;
/*    */   static final int PDU = 17;
/*    */ 
/*    */   static LucentQueryAgentStateConfEvent decode(InputStream in)
/*    */   {
/* 16 */     LucentQueryAgentStateConfEvent _this = new LucentQueryAgentStateConfEvent();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 24 */     this.workMode = LucentWorkMode.decode(memberStream);
/* 25 */     this.talkState = LucentTalkState.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 29 */     LucentWorkMode.encode(this.workMode, memberStream);
/* 30 */     LucentTalkState.encode(this.talkState, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 35 */     Collection lines = new ArrayList();
/*    */ 
/* 37 */     lines.add("LucentQueryAgentStateConfEvent ::=");
/* 38 */     lines.add("{");
/*    */ 
/* 40 */     String indent = "  ";
/*    */ 
/* 42 */     lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));
/* 43 */     lines.addAll(LucentTalkState.print(this.talkState, "talkState", indent));
/*    */ 
/* 45 */     lines.add("}");
/* 46 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 51 */     return 17;
/*    */   }
/*    */ 
/*    */   public short getTalkState()
/*    */   {
/* 57 */     return this.talkState;
/*    */   }
/*    */ 
/*    */   public short getWorkMode()
/*    */   {
/* 65 */     return this.workMode;
/*    */   }
/*    */ 
/*    */   public void setTalkState(short talkState) {
/* 69 */     this.talkState = talkState;
/*    */   }
/*    */ 
/*    */   public void setWorkMode(short workMode) {
/* 73 */     this.workMode = workMode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryAgentStateConfEvent
 * JD-Core Version:    0.5.4
 */