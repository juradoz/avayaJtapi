/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAQueryAgentState extends CSTARequest
/*    */ {
/*    */   String device;
/*    */   public static final int PDU = 33;
/*    */ 
/*    */   public CSTAQueryAgentState()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAQueryAgentState(String _device)
/*    */   {
/* 18 */     this.device = _device;
/*    */   }
/*    */ 
/*    */   public static CSTAQueryAgentState decode(InputStream in) {
/* 22 */     CSTAQueryAgentState _this = new CSTAQueryAgentState();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 25 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 29 */     this.device = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 33 */     DeviceID.encode(this.device, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 38 */     Collection lines = new ArrayList();
/*    */ 
/* 40 */     lines.add("CSTAQueryAgentState ::=");
/* 41 */     lines.add("{");
/*    */ 
/* 43 */     String indent = "  ";
/*    */ 
/* 45 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/*    */ 
/* 47 */     lines.add("}");
/* 48 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 53 */     return 33;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryAgentState
 * JD-Core Version:    0.5.4
 */