/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentV5QueryAgentStateConfEvent extends LucentQueryAgentStateConfEvent
/*    */ {
/*    */   int reasonCode;
/*    */   static final int PDU = 88;
/*    */ 
/*    */   public static LucentQueryAgentStateConfEvent decode(InputStream in, CSTATSProvider prov)
/*    */   {
/* 16 */     LucentV5QueryAgentStateConfEvent _this = new LucentV5QueryAgentStateConfEvent();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 24 */     super.decodeMembers(memberStream);
/* 25 */     this.reasonCode = ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 29 */     super.encodeMembers(memberStream);
/* 30 */     ASNInteger.encode(this.reasonCode, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 35 */     Collection lines = new ArrayList();
/*    */ 
/* 37 */     lines.add("LucentV5QueryAgentStateConfEvent ::=");
/* 38 */     lines.add("{");
/*    */ 
/* 40 */     String indent = "  ";
/*    */ 
/* 42 */     lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));
/* 43 */     lines.addAll(LucentTalkState.print(this.talkState, "talkState", indent));
/* 44 */     lines.addAll(ASNInteger.print(this.reasonCode, "reasonCode", indent));
/*    */ 
/* 46 */     lines.add("}");
/* 47 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 52 */     return 88;
/*    */   }
/*    */ 
/*    */   public int getReasonCode()
/*    */   {
/* 58 */     return this.reasonCode;
/*    */   }
/*    */ 
/*    */   public void setReasonCode(int reasonCode) {
/* 62 */     this.reasonCode = reasonCode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5QueryAgentStateConfEvent
 * JD-Core Version:    0.5.4
 */