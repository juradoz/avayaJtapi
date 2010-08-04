/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentSelectiveListeningHoldConfEvent extends LucentPrivateData
/*    */ {
/*    */   public static final int PDU = 68;
/*    */ 
/*    */   static LucentSelectiveListeningHoldConfEvent decode(InputStream in)
/*    */   {
/* 14 */     LucentSelectiveListeningHoldConfEvent _this = new LucentSelectiveListeningHoldConfEvent();
/* 15 */     _this.doDecode(in);
/*    */ 
/* 17 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 22 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 27 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 32 */     Collection lines = new ArrayList();
/*    */ 
/* 34 */     lines.add("LucentSelectiveListeningHoldConfEvent ::=");
/* 35 */     lines.add("{");
/*    */ 
/* 37 */     String indent = "  ";
/*    */ 
/* 39 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 41 */     lines.add("}");
/* 42 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 47 */     return 68;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSelectiveListeningHoldConfEvent
 * JD-Core Version:    0.5.4
 */