/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV6QueryAgentStateConfEvent extends LucentV5QueryAgentStateConfEvent
/*    */ {
/*    */   short pendingWorkMode;
/*    */   int pendingReasonCode;
/*    */   public static final int PDU = 104;
/*    */ 
/*    */   public static LucentQueryAgentStateConfEvent decode(InputStream in)
/*    */   {
/* 31 */     LucentV6QueryAgentStateConfEvent _this = new LucentV6QueryAgentStateConfEvent();
/* 32 */     _this.doDecode(in);
/*    */ 
/* 34 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 39 */     super.decodeMembers(memberStream);
/* 40 */     this.pendingWorkMode = LucentWorkMode.decode(memberStream);
/* 41 */     this.pendingReasonCode = ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 46 */     super.encodeMembers(memberStream);
/* 47 */     LucentWorkMode.encode(this.pendingWorkMode, memberStream);
/* 48 */     ASNInteger.encode(this.pendingReasonCode, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 53 */     Collection lines = new ArrayList();
/*    */ 
/* 55 */     lines.add("LucentV6QueryAgentStateConfEvent ::=");
/* 56 */     lines.add("{");
/*    */ 
/* 58 */     String indent = "  ";
/*    */ 
/* 60 */     lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));
/* 61 */     lines.addAll(LucentTalkState.print(this.talkState, "talkState", indent));
/* 62 */     lines.addAll(ASNInteger.print(this.reasonCode, "reasonCode", indent));
/* 63 */     lines.addAll(LucentWorkMode.print(this.pendingWorkMode, "pendingWorkMode", indent));
/* 64 */     lines.addAll(ASNInteger.print(this.pendingReasonCode, "pendingReasonCode", indent));
/*    */ 
/* 66 */     lines.add("}");
/* 67 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 72 */     return 104;
/*    */   }
/*    */ 
/*    */   public int getPendingReasonCode()
/*    */   {
/* 78 */     return this.pendingReasonCode;
/*    */   }
/*    */ 
/*    */   public short getPendingWorkMode()
/*    */   {
/* 86 */     return this.pendingWorkMode;
/*    */   }
/*    */ 
/*    */   public void setPendingReasonCode(int pendingReasonCode) {
/* 90 */     this.pendingReasonCode = pendingReasonCode;
/*    */   }
/*    */ 
/*    */   public void setPendingWorkMode(short pendingWorkMode) {
/* 94 */     this.pendingWorkMode = pendingWorkMode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6QueryAgentStateConfEvent
 * JD-Core Version:    0.5.4
 */