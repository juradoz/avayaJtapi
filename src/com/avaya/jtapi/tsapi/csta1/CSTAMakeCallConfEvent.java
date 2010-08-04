/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAMakeCallConfEvent extends CSTAConfirmation
/*    */ {
/*    */   CSTAConnectionID newCall;
/*    */   public static final int PDU = 24;
/*    */ 
/*    */   public CSTAMakeCallConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAMakeCallConfEvent(CSTAConnectionID _newCall)
/*    */   {
/* 16 */     this.newCall = _newCall;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 21 */     CSTAConnectionID.encode(this.newCall, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAMakeCallConfEvent decode(InputStream in)
/*    */   {
/* 26 */     CSTAMakeCallConfEvent _this = new CSTAMakeCallConfEvent();
/* 27 */     _this.doDecode(in);
/*    */ 
/* 29 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 34 */     this.newCall = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 39 */     Collection lines = new ArrayList();
/* 40 */     lines.add("CSTAMakeCallConfEvent ::=");
/* 41 */     lines.add("{");
/*    */ 
/* 43 */     String indent = "  ";
/*    */ 
/* 45 */     lines.addAll(CSTAConnectionID.print(this.newCall, "newCall", indent));
/*    */ 
/* 47 */     lines.add("}");
/* 48 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 53 */     return 24;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getNewCall()
/*    */   {
/* 59 */     return this.newCall;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMakeCallConfEvent
 * JD-Core Version:    0.5.4
 */