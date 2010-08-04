/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentQueryUcid extends LucentPrivateData
/*    */ {
/*    */   CSTAConnectionID call;
/*    */   public static final int PDU = 76;
/*    */ 
/*    */   public LucentQueryUcid()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentQueryUcid(CSTAConnectionID _call)
/*    */   {
/* 16 */     this.call = _call;
/*    */   }
/*    */ 
/*    */   public static LucentQueryUcid decode(InputStream in) {
/* 20 */     LucentQueryUcid _this = new LucentQueryUcid();
/* 21 */     _this.doDecode(in);
/*    */ 
/* 23 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 28 */     this.call = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 32 */     CSTAConnectionID.encode(this.call, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 37 */     Collection lines = new ArrayList();
/*    */ 
/* 39 */     lines.add("LucentQueryUcid ::=");
/* 40 */     lines.add("{");
/*    */ 
/* 42 */     String indent = "  ";
/*    */ 
/* 44 */     lines.addAll(CSTAConnectionID.print(this.call, "call", indent));
/*    */ 
/* 46 */     lines.add("}");
/* 47 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 52 */     return 76;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryUcid
 * JD-Core Version:    0.5.4
 */