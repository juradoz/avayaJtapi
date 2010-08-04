/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentQueryTgConfEvent extends LucentPrivateData
/*    */ {
/*    */   int idleTrunks;
/*    */   int usedTrunks;
/*    */   static final int PDU = 27;
/*    */ 
/*    */   static LucentQueryTgConfEvent decode(InputStream in)
/*    */   {
/* 16 */     LucentQueryTgConfEvent _this = new LucentQueryTgConfEvent();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 24 */     this.idleTrunks = ASNInteger.decode(memberStream);
/* 25 */     this.usedTrunks = ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 30 */     Collection lines = new ArrayList();
/*    */ 
/* 32 */     lines.add("LucentQueryTgConfEvent ::=");
/* 33 */     lines.add("{");
/*    */ 
/* 35 */     String indent = "  ";
/*    */ 
/* 37 */     lines.addAll(ASNInteger.print(this.idleTrunks, "idleTrunks", indent));
/* 38 */     lines.addAll(ASNInteger.print(this.usedTrunks, "usedTrunks", indent));
/*    */ 
/* 40 */     lines.add("}");
/* 41 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 46 */     return 27;
/*    */   }
/*    */ 
/*    */   public void setIdleTrunks(int idleTrunks) {
/* 50 */     this.idleTrunks = idleTrunks;
/*    */   }
/*    */ 
/*    */   public void setUsedTrunks(int usedTrunks)
/*    */   {
/* 55 */     this.usedTrunks = usedTrunks;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryTgConfEvent
 * JD-Core Version:    0.5.4
 */