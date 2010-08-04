/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAQueryMwiConfEvent extends CSTAConfirmation
/*    */ {
/*    */   boolean messages;
/*    */   public static final int PDU = 28;
/*    */ 
/*    */   public static CSTAQueryMwiConfEvent decode(InputStream in)
/*    */   {
/* 17 */     CSTAQueryMwiConfEvent _this = new CSTAQueryMwiConfEvent();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.messages = ASNBoolean.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 30 */     Collection lines = new ArrayList();
/*    */ 
/* 32 */     lines.add("CSTAQueryMwiConfEvent ::=");
/* 33 */     lines.add("{");
/*    */ 
/* 35 */     String indent = "  ";
/*    */ 
/* 37 */     lines.addAll(ASNBoolean.print(this.messages, "messages", indent));
/*    */ 
/* 39 */     lines.add("}");
/* 40 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 45 */     return 28;
/*    */   }
/*    */ 
/*    */   public boolean isMessages()
/*    */   {
/* 51 */     return this.messages;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryMwiConfEvent
 * JD-Core Version:    0.5.4
 */