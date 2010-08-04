/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentSetAgentStateConfEvent extends LucentPrivateData
/*    */ {
/*    */   boolean isPending;
/*    */   public static final int PDU = 103;
/*    */ 
/*    */   public static LucentSetAgentStateConfEvent decode(InputStream in)
/*    */   {
/* 30 */     LucentSetAgentStateConfEvent _this = new LucentSetAgentStateConfEvent();
/* 31 */     _this.doDecode(in);
/*    */ 
/* 33 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 38 */     this.isPending = ASNBoolean.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 42 */     ASNBoolean.encode(this.isPending, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 47 */     Collection lines = new ArrayList();
/*    */ 
/* 49 */     lines.add("LucentSetAgentStateConfEvent ::=");
/* 50 */     lines.add("{");
/*    */ 
/* 52 */     String indent = "  ";
/*    */ 
/* 54 */     lines.addAll(ASNBoolean.print(this.isPending, "isPending", indent));
/*    */ 
/* 56 */     lines.add("}");
/* 57 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 62 */     return 103;
/*    */   }
/*    */ 
/*    */   public boolean isPending()
/*    */   {
/* 68 */     return this.isPending;
/*    */   }
/*    */ 
/*    */   public void setPending(boolean isPending) {
/* 72 */     this.isPending = isPending;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSetAgentStateConfEvent
 * JD-Core Version:    0.5.4
 */