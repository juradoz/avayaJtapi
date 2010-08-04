/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentSetAdviceOfChargeConfEvent extends LucentPrivateData
/*    */ {
/*    */   static final int PDU = 100;
/*    */ 
/*    */   static LucentSetAdviceOfChargeConfEvent decode(InputStream in)
/*    */   {
/* 13 */     LucentSetAdviceOfChargeConfEvent _this = new LucentSetAdviceOfChargeConfEvent();
/* 14 */     _this.doDecode(in);
/*    */ 
/* 16 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 21 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 26 */     Collection lines = new ArrayList();
/*    */ 
/* 28 */     lines.add("LucentSetAdviceOfChargeConfEvent ::=");
/* 29 */     lines.add("{");
/*    */ 
/* 31 */     String indent = "  ";
/*    */ 
/* 33 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 35 */     lines.add("}");
/* 36 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 41 */     return 100;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSetAdviceOfChargeConfEvent
 * JD-Core Version:    0.5.4
 */