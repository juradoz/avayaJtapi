/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTASetDnd extends CSTARequest
/*    */ {
/*    */   String device;
/*    */   boolean doNotDisturb;
/*    */   public static final int PDU = 45;
/*    */ 
/*    */   public CSTASetDnd()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTASetDnd(String _device, boolean _doNotDisturb)
/*    */   {
/* 19 */     this.device = _device;
/* 20 */     this.doNotDisturb = _doNotDisturb;
/*    */   }
/*    */ 
/*    */   public static CSTASetDnd decode(InputStream in)
/*    */   {
/* 25 */     CSTASetDnd _this = new CSTASetDnd();
/* 26 */     _this.doDecode(in);
/*    */ 
/* 28 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 33 */     this.device = DeviceID.decode(memberStream);
/* 34 */     this.doNotDisturb = ASNBoolean.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 39 */     DeviceID.encode(this.device, memberStream);
/* 40 */     ASNBoolean.encode(this.doNotDisturb, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 45 */     Collection lines = new ArrayList();
/*    */ 
/* 47 */     lines.add("CSTASetDnd ::=");
/* 48 */     lines.add("{");
/*    */ 
/* 50 */     String indent = "  ";
/*    */ 
/* 52 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/* 53 */     lines.addAll(ASNBoolean.print(this.doNotDisturb, "doNotDisturb", indent));
/*    */ 
/* 55 */     lines.add("}");
/* 56 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 61 */     return 45;
/*    */   }
/*    */ 
/*    */   public String getDevice()
/*    */   {
/* 67 */     return this.device;
/*    */   }
/*    */ 
/*    */   public boolean isDoNotDisturb()
/*    */   {
/* 75 */     return this.doNotDisturb;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASetDnd
 * JD-Core Version:    0.5.4
 */