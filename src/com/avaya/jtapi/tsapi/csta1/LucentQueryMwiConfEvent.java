/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentQueryMwiConfEvent extends LucentPrivateData
/*    */ {
/*    */   int applicationType;
/*    */   static final int PDU = 21;
/*    */ 
/*    */   public int getApplicationType()
/*    */   {
/* 16 */     return this.applicationType;
/*    */   }
/*    */ 
/*    */   public static LucentQueryMwiConfEvent decode(InputStream in)
/*    */   {
/* 21 */     LucentQueryMwiConfEvent _this = new LucentQueryMwiConfEvent();
/* 22 */     _this.doDecode(in);
/*    */ 
/* 24 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 29 */     this.applicationType = LucentMwiApplication.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 34 */     Collection lines = new ArrayList();
/*    */ 
/* 36 */     lines.add("LucentQueryMwiConfEvent ::=");
/* 37 */     lines.add("{");
/*    */ 
/* 39 */     String indent = "  ";
/*    */ 
/* 41 */     lines.addAll(LucentMwiApplication.print(this.applicationType, "applicationType", indent));
/*    */ 
/* 43 */     lines.add("}");
/* 44 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 49 */     return 21;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryMwiConfEvent
 * JD-Core Version:    0.5.4
 */