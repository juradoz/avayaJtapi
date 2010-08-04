/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTAEscapeSvcReqConf extends CSTARequest
/*    */ {
/*    */   short errorValue;
/*    */   static final int PDU = 92;
/*    */ 
/*    */   public CSTAEscapeSvcReqConf(short _errorValue)
/*    */   {
/* 16 */     this.errorValue = _errorValue;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 21 */     CSTAUniversalFailure.encode(this.errorValue, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 26 */     Collection lines = new ArrayList();
/* 27 */     lines.add("CSTAEscapeSvcReqConf ::=");
/* 28 */     lines.add("{");
/*    */ 
/* 30 */     String indent = "  ";
/*    */ 
/* 32 */     lines.addAll(CSTAUniversalFailure.print(this.errorValue, "errorValue", indent));
/*    */ 
/* 34 */     lines.add("}");
/* 35 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 40 */     return 92;
/*    */   }
/*    */ 
/*    */   public short getErrorValue()
/*    */   {
/* 46 */     return this.errorValue;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAEscapeSvcReqConf
 * JD-Core Version:    0.5.4
 */