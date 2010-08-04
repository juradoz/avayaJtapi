/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAHoldCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID activeCall;
/*    */   boolean reservation;
/*    */   public static final int PDU = 21;
/*    */ 
/*    */   public CSTAHoldCall(CSTAConnectionID _activeCall, boolean _reservation)
/*    */   {
/* 20 */     this.activeCall = _activeCall;
/* 21 */     this.reservation = _reservation;
/*    */   }
/*    */ 
/*    */   public CSTAHoldCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 29 */     CSTAConnectionID.encode(this.activeCall, memberStream);
/* 30 */     ASNBoolean.encode(this.reservation, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAHoldCall decode(InputStream in)
/*    */   {
/* 35 */     CSTAHoldCall _this = new CSTAHoldCall();
/* 36 */     _this.doDecode(in);
/*    */ 
/* 38 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 43 */     this.activeCall = CSTAConnectionID.decode(memberStream);
/* 44 */     this.reservation = ASNBoolean.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 49 */     Collection lines = new ArrayList();
/* 50 */     lines.add("CSTAHoldCall ::=");
/* 51 */     lines.add("{");
/*    */ 
/* 53 */     String indent = "  ";
/*    */ 
/* 55 */     lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall", indent));
/* 56 */     lines.addAll(ASNBoolean.print(this.reservation, "reservation", indent));
/*    */ 
/* 58 */     lines.add("}");
/* 59 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 64 */     return 21;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getActiveCall()
/*    */   {
/* 70 */     return this.activeCall;
/*    */   }
/*    */ 
/*    */   public boolean isReservation()
/*    */   {
/* 78 */     return this.reservation;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAHoldCall
 * JD-Core Version:    0.5.4
 */