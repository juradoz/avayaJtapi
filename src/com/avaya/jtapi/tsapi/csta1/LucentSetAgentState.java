/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentSetAgentState extends LucentPrivateData
/*    */ {
/*    */   short workMode;
/*    */   static final int PDU = 10;
/*    */ 
/*    */   public LucentSetAgentState()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentSetAgentState(short _workMode)
/*    */   {
/* 18 */     this.workMode = _workMode;
/*    */   }
/*    */ 
/*    */   public static LucentSetAgentState decode(InputStream in, CSTATSProvider prov) {
/* 22 */     LucentSetAgentState _this = new LucentSetAgentState();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 25 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 29 */     this.workMode = LucentWorkMode.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 34 */     LucentWorkMode.encode(this.workMode, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 39 */     Collection lines = new ArrayList();
/*    */ 
/* 41 */     lines.add("LucentSetAgentState ::=");
/* 42 */     lines.add("{");
/*    */ 
/* 44 */     String indent = "  ";
/*    */ 
/* 46 */     lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));
/*    */ 
/* 48 */     lines.add("}");
/* 49 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 54 */     return 10;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSetAgentState
 * JD-Core Version:    0.5.4
 */