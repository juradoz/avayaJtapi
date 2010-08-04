/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSSetHeartbeatIntervalConfEvent extends ACSConfirmation
/*    */ {
/*    */   short heartbeatInterval;
/*    */   public static final int PDU = 15;
/*    */ 
/*    */   public void setHeartbeatInterval(short heartbeatInterval)
/*    */   {
/* 39 */     this.heartbeatInterval = heartbeatInterval;
/*    */   }
/*    */ 
/*    */   public ACSSetHeartbeatIntervalConfEvent(short heartbeatInterval)
/*    */   {
/* 44 */     this.heartbeatInterval = heartbeatInterval;
/*    */   }
/*    */ 
/*    */   public ACSSetHeartbeatIntervalConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 53 */     ASNInteger.encode(this.heartbeatInterval, memberStream);
/*    */   }
/*    */ 
/*    */   public static ACSSetHeartbeatIntervalConfEvent decode(InputStream in)
/*    */   {
/* 58 */     ACSSetHeartbeatIntervalConfEvent _this = new ACSSetHeartbeatIntervalConfEvent();
/*    */ 
/* 60 */     _this.doDecode(in);
/*    */ 
/* 62 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 67 */     this.heartbeatInterval = (short)ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 73 */     Collection lines = new ArrayList();
/* 74 */     lines.add("ACSSetHeartbeatIntervalConfEvent ::=");
/* 75 */     lines.add("{");
/*    */ 
/* 77 */     String indent = "  ";
/*    */ 
/* 79 */     lines.addAll(ASNInteger.print(this.heartbeatInterval, "heartbeatInterval", indent));
/*    */ 
/* 81 */     lines.add("}");
/* 82 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 87 */     return 15;
/*    */   }
/*    */ 
/*    */   public synchronized short getHeartbeatInterval()
/*    */   {
/* 93 */     return this.heartbeatInterval;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSSetHeartbeatIntervalConfEvent
 * JD-Core Version:    0.5.4
 */