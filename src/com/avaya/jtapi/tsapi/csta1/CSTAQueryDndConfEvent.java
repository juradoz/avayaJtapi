/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAQueryDndConfEvent extends CSTAConfirmation
/*    */ {
/*    */   boolean doNotDisturb;
/*    */   public static final int PDU = 30;
/*    */ 
/*    */   public static CSTAQueryDndConfEvent decode(InputStream in)
/*    */   {
/* 18 */     CSTAQueryDndConfEvent _this = new CSTAQueryDndConfEvent();
/* 19 */     _this.doDecode(in);
/*    */ 
/* 21 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 26 */     this.doNotDisturb = ASNBoolean.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 31 */     ASNBoolean.encode(this.doNotDisturb, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 36 */     Collection lines = new ArrayList();
/*    */ 
/* 38 */     lines.add("CSTAQueryDndConfEvent ::=");
/* 39 */     lines.add("{");
/*    */ 
/* 41 */     String indent = "  ";
/*    */ 
/* 43 */     lines.addAll(ASNBoolean.print(this.doNotDisturb, "doNotDisturb", indent));
/*    */ 
/* 45 */     lines.add("}");
/* 46 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 51 */     return 30;
/*    */   }
/*    */ 
/*    */   public boolean isDoNotDisturb()
/*    */   {
/* 57 */     return this.doNotDisturb;
/*    */   }
/*    */ 
/*    */   public void setDoNotDisturb(boolean doNotDisturb) {
/* 61 */     this.doNotDisturb = doNotDisturb;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryDndConfEvent
 * JD-Core Version:    0.5.4
 */