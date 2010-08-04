/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTACallCompletion extends CSTARequest
/*    */ {
/*    */   short feature;
/*    */   CSTAConnectionID call;
/*    */   static final int PDU = 5;
/*    */ 
/*    */   public CSTACallCompletion(short _feature, CSTAConnectionID _call)
/*    */   {
/* 18 */     this.feature = _feature;
/* 19 */     this.call = _call;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 24 */     Feature.encode(this.feature, memberStream);
/* 25 */     CSTAConnectionID.encode(this.call, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 30 */     Collection lines = new ArrayList();
/* 31 */     lines.add("CSTACallCompletion ::=");
/* 32 */     lines.add("{");
/*    */ 
/* 34 */     String indent = "  ";
/*    */ 
/* 36 */     lines.addAll(Feature.print(this.feature, "feature", indent));
/* 37 */     lines.addAll(CSTAConnectionID.print(this.call, "call", indent));
/*    */ 
/* 39 */     lines.add("}");
/* 40 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 45 */     return 5;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getCall()
/*    */   {
/* 51 */     return this.call;
/*    */   }
/*    */ 
/*    */   public short getFeature()
/*    */   {
/* 59 */     return this.feature;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTACallCompletion
 * JD-Core Version:    0.5.4
 */