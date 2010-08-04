/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentTrunkGroupInfo extends LucentPrivateData
/*    */ {
/*    */   public int idleTrunks;
/*    */   public int usedTrunks;
/*    */   static final int PDU = 27;
/*    */ 
/*    */   static LucentTrunkGroupInfo decode(InputStream in)
/*    */   {
/* 24 */     LucentTrunkGroupInfo _this = new LucentTrunkGroupInfo();
/* 25 */     _this.doDecode(in);
/*    */ 
/* 27 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 32 */     this.idleTrunks = ASNInteger.decode(memberStream);
/* 33 */     this.usedTrunks = ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 38 */     Collection lines = new ArrayList();
/*    */ 
/* 40 */     lines.add("TrunkGroupInfo ::=");
/* 41 */     lines.add("{");
/*    */ 
/* 43 */     String indent = "  ";
/*    */ 
/* 45 */     lines.addAll(ASNInteger.print(this.idleTrunks, "idleTrunks", indent));
/* 46 */     lines.addAll(ASNInteger.print(this.usedTrunks, "usedTrunks", indent));
/*    */ 
/* 48 */     lines.add("}");
/* 49 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 54 */     return 27;
/*    */   }
/*    */ 
/*    */   public void setIdleTrunks(int idleTrunks) {
/* 58 */     this.idleTrunks = idleTrunks;
/*    */   }
/*    */ 
/*    */   public void setUsedTrunks(int usedTrunks)
/*    */   {
/* 63 */     this.usedTrunks = usedTrunks;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentTrunkGroupInfo
 * JD-Core Version:    0.5.4
 */