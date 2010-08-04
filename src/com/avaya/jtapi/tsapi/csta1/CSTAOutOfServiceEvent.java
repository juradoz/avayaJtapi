/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAOutOfServiceEvent extends CSTAUnsolicited
/*    */ {
/*    */   String device;
/*    */   short cause;
/*    */   static final int PDU = 97;
/*    */ 
/*    */   static CSTAOutOfServiceEvent decode(InputStream in)
/*    */   {
/* 17 */     CSTAOutOfServiceEvent _this = new CSTAOutOfServiceEvent();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.device = DeviceID.decode(memberStream);
/* 26 */     this.cause = CSTAEventCause.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 31 */     Collection lines = new ArrayList();
/* 32 */     lines.add("CSTAOutOfServiceEvent ::=");
/* 33 */     lines.add("{");
/*    */ 
/* 35 */     String indent = "  ";
/* 36 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/* 37 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/* 38 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*    */ 
/* 40 */     lines.add("}");
/* 41 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 46 */     return 97;
/*    */   }
/*    */ 
/*    */   public short getCause()
/*    */   {
/* 52 */     return this.cause;
/*    */   }
/*    */ 
/*    */   public String getDevice()
/*    */   {
/* 60 */     return this.device;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAOutOfServiceEvent
 * JD-Core Version:    0.5.4
 */