/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNReal;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentSetBillRate extends LucentPrivateData
/*    */ {
/*    */   CSTAConnectionID call;
/*    */   short billType;
/*    */   float billRate;
/*    */   static final int PDU = 74;
/*    */ 
/*    */   public LucentSetBillRate(CSTAConnectionID _call, short _billType, float _billRate)
/*    */   {
/* 21 */     this.call = _call;
/* 22 */     this.billType = _billType;
/* 23 */     this.billRate = _billRate;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 28 */     CSTAConnectionID.encode(this.call, memberStream);
/* 29 */     BillType.encode(this.billType, memberStream);
/* 30 */     ASNReal.encode(this.billRate, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 35 */     Collection lines = new ArrayList();
/*    */ 
/* 37 */     lines.add("LucentSetBillRate ::=");
/* 38 */     lines.add("{");
/*    */ 
/* 40 */     String indent = "  ";
/*    */ 
/* 42 */     lines.addAll(CSTAConnectionID.print(this.call, "call", indent));
/* 43 */     lines.addAll(BillType.print(this.billType, "billType", indent));
/* 44 */     lines.addAll(ASNReal.print(this.billRate, "billRate", indent));
/*    */ 
/* 46 */     lines.add("}");
/* 47 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 52 */     return 74;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSetBillRate
 * JD-Core Version:    0.5.4
 */