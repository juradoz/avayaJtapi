/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentLoggedOffEvent extends LucentPrivateData
/*    */ {
/*    */   int reasonCode;
/*    */   static final int PDU = 79;
/*    */ 
/*    */   static LucentLoggedOffEvent decode(InputStream in)
/*    */   {
/* 16 */     LucentLoggedOffEvent _this = new LucentLoggedOffEvent();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 24 */     this.reasonCode = ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 28 */     ASNInteger.encode(this.reasonCode, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 33 */     Collection lines = new ArrayList();
/*    */ 
/* 35 */     lines.add("LucentLoggedOffEvent ::=");
/* 36 */     lines.add("{");
/*    */ 
/* 38 */     String indent = "  ";
/*    */ 
/* 40 */     lines.addAll(ASNInteger.print(this.reasonCode, "reasonCode", indent));
/*    */ 
/* 42 */     lines.add("}");
/* 43 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 48 */     return 79;
/*    */   }
/*    */ 
/*    */   public int getReasonCode()
/*    */   {
/* 54 */     return this.reasonCode;
/*    */   }
/*    */ 
/*    */   public void setReasonCode(int reasonCode) {
/* 58 */     this.reasonCode = reasonCode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentLoggedOffEvent
 * JD-Core Version:    0.5.4
 */