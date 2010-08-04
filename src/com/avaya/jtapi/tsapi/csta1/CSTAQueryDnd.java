/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAQueryDnd extends CSTARequest
/*    */ {
/*    */   String device;
/*    */   public static final int PDU = 29;
/*    */ 
/*    */   public CSTAQueryDnd()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAQueryDnd(String _device)
/*    */   {
/* 17 */     this.device = _device;
/*    */   }
/*    */ 
/*    */   public static CSTAQueryDnd decode(InputStream in)
/*    */   {
/* 22 */     CSTAQueryDnd _this = new CSTAQueryDnd();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 25 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 30 */     this.device = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 35 */     DeviceID.encode(this.device, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 40 */     Collection lines = new ArrayList();
/*    */ 
/* 42 */     lines.add("CSTAQueryDnd ::=");
/* 43 */     lines.add("{");
/*    */ 
/* 45 */     String indent = "  ";
/*    */ 
/* 47 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/*    */ 
/* 49 */     lines.add("}");
/* 50 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 55 */     return 29;
/*    */   }
/*    */ 
/*    */   public String getDevice()
/*    */   {
/* 61 */     return this.device;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryDnd
 * JD-Core Version:    0.5.4
 */