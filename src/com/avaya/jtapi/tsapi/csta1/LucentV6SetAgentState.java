/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV6SetAgentState extends LucentV5SetAgentState
/*    */ {
/*    */   boolean enablePending;
/*    */   public static final int PDU = 102;
/*    */ 
/*    */   public LucentV6SetAgentState()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentV6SetAgentState(short _workMode, int _reasonCode, boolean _enablePending)
/*    */   {
/* 35 */     super(_workMode, _reasonCode);
/* 36 */     this.enablePending = _enablePending;
/*    */   }
/*    */ 
/*    */   public static LucentSetAgentState decode(InputStream in, CSTATSProvider prov)
/*    */   {
/* 41 */     LucentV6SetAgentState _this = new LucentV6SetAgentState();
/* 42 */     _this.doDecode(in);
/*    */ 
/* 44 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 49 */     super.encodeMembers(memberStream);
/* 50 */     ASNBoolean.encode(this.enablePending, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 55 */     super.decodeMembers(memberStream);
/* 56 */     this.enablePending = ASNBoolean.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 61 */     Collection lines = new ArrayList();
/*    */ 
/* 63 */     lines.add("LucentV6SetAgentState ::=");
/* 64 */     lines.add("{");
/*    */ 
/* 66 */     String indent = "  ";
/*    */ 
/* 68 */     lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));
/* 69 */     lines.addAll(ASNInteger.print(this.reasonCode, "reasonCode", indent));
/* 70 */     lines.addAll(ASNBoolean.print(this.enablePending, "enablePending", indent));
/*    */ 
/* 72 */     lines.add("}");
/* 73 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 78 */     return 102;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6SetAgentState
 * JD-Core Version:    0.5.4
 */