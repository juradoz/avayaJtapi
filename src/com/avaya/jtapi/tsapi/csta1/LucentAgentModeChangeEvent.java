/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentAgentModeChangeEvent extends LucentPrivateData
/*    */ {
/*    */   int reasonCode;
/*    */   static final int PDU = 124;
/*    */ 
/*    */   static LucentAgentModeChangeEvent decode(InputStream in)
/*    */   {
/* 17 */     LucentAgentModeChangeEvent _this = new LucentAgentModeChangeEvent();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.reasonCode = ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 29 */     ASNInteger.encode(this.reasonCode, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 33 */     Collection lines = new ArrayList();
/*    */ 
/* 35 */     lines.add("LucentAgentModeChangeEvent ::=");
/* 36 */     lines.add("{");
/*    */ 
/* 38 */     String indent = "  ";
/*    */ 
/* 40 */     lines.addAll(ASNInteger.print(this.reasonCode, "reasonCode", indent));
/* 41 */     lines.add("}");
/* 42 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 47 */     return 124;
/*    */   }
/*    */   public int getReasonCode() {
/* 50 */     return this.reasonCode;
/*    */   }
/*    */ 
/*    */   public void setReasonCode(int reasonCode) {
/* 54 */     this.reasonCode = reasonCode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentAgentModeChangeEvent
 * JD-Core Version:    0.5.4
 */