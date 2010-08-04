/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTASetMwi extends CSTARequest
/*    */ {
/*    */   String device;
/*    */   boolean messages;
/*    */   static final int PDU = 43;
/*    */ 
/*    */   public CSTASetMwi(String _device, boolean _messages)
/*    */   {
/* 19 */     this.device = _device;
/* 20 */     this.messages = _messages;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 25 */     DeviceID.encode(this.device, memberStream);
/* 26 */     ASNBoolean.encode(this.messages, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 31 */     Collection lines = new ArrayList();
/*    */ 
/* 33 */     lines.add("CSTASetMwi ::=");
/* 34 */     lines.add("{");
/*    */ 
/* 36 */     String indent = "  ";
/*    */ 
/* 38 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/* 39 */     lines.addAll(ASNBoolean.print(this.messages, "messages", indent));
/*    */ 
/* 41 */     lines.add("}");
/* 42 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 47 */     return 43;
/*    */   }
/*    */ 
/*    */   public String getDevice()
/*    */   {
/* 53 */     return this.device;
/*    */   }
/*    */ 
/*    */   public boolean isMessages()
/*    */   {
/* 61 */     return this.messages;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASetMwi
 * JD-Core Version:    0.5.4
 */