/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSSetHeartbeatInterval extends ACSRequest
/*    */ {
/*    */   short heartbeatInterval;
/*    */   public static final int PDU = 14;
/*    */ 
/*    */   public ACSSetHeartbeatInterval(short heartbeatInterval)
/*    */   {
/* 39 */     this.heartbeatInterval = heartbeatInterval;
/*    */   }
/*    */ 
/*    */   public ACSSetHeartbeatInterval()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 48 */     ASNInteger.encode(this.heartbeatInterval, memberStream);
/*    */   }
/*    */ 
/*    */   public static ACSSetHeartbeatInterval decode(InputStream in)
/*    */   {
/* 53 */     ACSSetHeartbeatInterval _this = new ACSSetHeartbeatInterval();
/* 54 */     _this.doDecode(in);
/*    */ 
/* 56 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 61 */     this.heartbeatInterval = (short)ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 67 */     Collection lines = new ArrayList();
/* 68 */     lines.add("ACSSetHeartbeatInterval ::=");
/* 69 */     lines.add("{");
/*    */ 
/* 71 */     String indent = "  ";
/*    */ 
/* 73 */     lines.addAll(ASNInteger.print(this.heartbeatInterval, "heartbeatInterval", indent));
/*    */ 
/* 75 */     lines.add("}");
/* 76 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 81 */     return 14;
/*    */   }
/*    */ 
/*    */   public synchronized short getHeartbeatInterval()
/*    */   {
/* 87 */     return this.heartbeatInterval;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSSetHeartbeatInterval
 * JD-Core Version:    0.5.4
 */