/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentQueryAgentState extends LucentPrivateData
/*    */ {
/*    */   String split;
/*    */   public static final int PDU = 16;
/*    */ 
/*    */   public LucentQueryAgentState()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentQueryAgentState(String _split)
/*    */   {
/* 18 */     this.split = _split;
/*    */   }
/*    */ 
/*    */   public static LucentQueryAgentState decode(InputStream in) {
/* 22 */     LucentQueryAgentState _this = new LucentQueryAgentState();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 25 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 29 */     this.split = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 33 */     DeviceID.encode(this.split, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 38 */     Collection lines = new ArrayList();
/*    */ 
/* 40 */     lines.add("LucentQueryAgentState ::=");
/* 41 */     lines.add("{");
/*    */ 
/* 43 */     String indent = "  ";
/*    */ 
/* 45 */     lines.addAll(DeviceID.print(this.split, "split", indent));
/*    */ 
/* 47 */     lines.add("}");
/* 48 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 53 */     return 16;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryAgentState
 * JD-Core Version:    0.5.4
 */