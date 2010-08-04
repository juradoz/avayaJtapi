/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentCallClassifierInfo extends LucentPrivateData
/*    */ {
/*    */   public int numAvailPorts;
/*    */   public int numInUsePorts;
/*    */   static final int PDU = 19;
/*    */ 
/*    */   static LucentCallClassifierInfo decode(InputStream in)
/*    */   {
/* 23 */     LucentCallClassifierInfo _this = new LucentCallClassifierInfo();
/* 24 */     _this.doDecode(in);
/*    */ 
/* 26 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 31 */     this.numAvailPorts = ASNInteger.decode(memberStream);
/* 32 */     this.numInUsePorts = ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 37 */     Collection lines = new ArrayList();
/* 38 */     lines.add("CallClassifierInfo ::=");
/* 39 */     lines.add("{");
/*    */ 
/* 41 */     String indent = "  ";
/*    */ 
/* 43 */     lines.addAll(ASNInteger.print(this.numAvailPorts, "numAvailPorts", indent));
/* 44 */     lines.addAll(ASNInteger.print(this.numInUsePorts, "numInUsePorts", indent));
/*    */ 
/* 46 */     lines.add("}");
/* 47 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 52 */     return 19;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentCallClassifierInfo
 * JD-Core Version:    0.5.4
 */