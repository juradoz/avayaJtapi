/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAQueryLastNumberConfEvent extends CSTAConfirmation
/*    */ {
/*    */   String lastNumber;
/*    */   static final int PDU = 36;
/*    */ 
/*    */   public static CSTAQueryLastNumberConfEvent decode(InputStream in)
/*    */   {
/* 16 */     CSTAQueryLastNumberConfEvent _this = new CSTAQueryLastNumberConfEvent();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 24 */     this.lastNumber = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 29 */     Collection lines = new ArrayList();
/*    */ 
/* 31 */     lines.add("CSTAQueryLastNumberConfEvent ::=");
/* 32 */     lines.add("{");
/*    */ 
/* 34 */     String indent = "  ";
/*    */ 
/* 36 */     lines.addAll(DeviceID.print(this.lastNumber, "lastNumber", indent));
/*    */ 
/* 38 */     lines.add("}");
/* 39 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 44 */     return 36;
/*    */   }
/*    */ 
/*    */   public String getLastNumber()
/*    */   {
/* 50 */     return this.lastNumber;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryLastNumberConfEvent
 * JD-Core Version:    0.5.4
 */