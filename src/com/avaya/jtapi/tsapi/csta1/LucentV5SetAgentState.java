/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentV5SetAgentState extends LucentSetAgentState
/*    */ {
/*    */   int reasonCode;
/*    */   static final int PDU = 87;
/*    */ 
/*    */   public LucentV5SetAgentState()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentV5SetAgentState(short _workMode, int _reasonCode)
/*    */   {
/* 20 */     super(_workMode);
/* 21 */     this.reasonCode = _reasonCode;
/*    */   }
/*    */ 
/*    */   public static LucentSetAgentState decode(InputStream in, CSTATSProvider prov)
/*    */   {
/* 26 */     LucentV5SetAgentState _this = new LucentV5SetAgentState();
/* 27 */     _this.doDecode(in);
/*    */ 
/* 29 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 33 */     super.decodeMembers(memberStream);
/* 34 */     this.reasonCode = ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 38 */     super.encodeMembers(memberStream);
/* 39 */     ASNInteger.encode(this.reasonCode, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 44 */     Collection lines = new ArrayList();
/*    */ 
/* 46 */     lines.add("LucentV5SetAgentState ::=");
/* 47 */     lines.add("{");
/*    */ 
/* 49 */     String indent = "  ";
/*    */ 
/* 51 */     lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));
/* 52 */     lines.addAll(ASNInteger.print(this.reasonCode, "reasonCode", indent));
/*    */ 
/* 54 */     lines.add("}");
/* 55 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 60 */     return 87;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5SetAgentState
 * JD-Core Version:    0.5.4
 */