/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTAQueryMwi extends CSTARequest
/*    */ {
/*    */   String device;
/*    */   static final int PDU = 27;
/*    */ 
/*    */   public CSTAQueryMwi(String _device)
/*    */   {
/* 16 */     this.device = _device;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 21 */     DeviceID.encode(this.device, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 26 */     Collection lines = new ArrayList();
/* 27 */     lines.add("CSTAQueryMwi ::=");
/* 28 */     lines.add("{");
/*    */ 
/* 30 */     String indent = "  ";
/*    */ 
/* 32 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/*    */ 
/* 34 */     lines.add("}");
/* 35 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 40 */     return 27;
/*    */   }
/*    */ 
/*    */   public String getDevice()
/*    */   {
/* 46 */     return this.device;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryMwi
 * JD-Core Version:    0.5.4
 */