/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentQueryAcdSplitConfEvent extends LucentPrivateData
/*    */ {
/*    */   int availableAgents;
/*    */   int callsInQueue;
/*    */   int agentsLoggedIn;
/*    */   public static final int PDU = 12;
/*    */ 
/*    */   public static LucentQueryAcdSplitConfEvent decode(InputStream in)
/*    */   {
/* 18 */     LucentQueryAcdSplitConfEvent _this = new LucentQueryAcdSplitConfEvent();
/* 19 */     _this.doDecode(in);
/*    */ 
/* 21 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 26 */     this.availableAgents = ASNInteger.decode(memberStream);
/* 27 */     this.callsInQueue = ASNInteger.decode(memberStream);
/* 28 */     this.agentsLoggedIn = ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 33 */     ASNInteger.encode(this.availableAgents, memberStream);
/* 34 */     ASNInteger.encode(this.callsInQueue, memberStream);
/* 35 */     ASNInteger.encode(this.agentsLoggedIn, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 40 */     Collection lines = new ArrayList();
/*    */ 
/* 42 */     lines.add("LucentQueryAcdSplitConfEvent ::=");
/* 43 */     lines.add("{");
/*    */ 
/* 45 */     String indent = "  ";
/*    */ 
/* 47 */     lines.addAll(ASNInteger.print(this.availableAgents, "availableAgents", indent));
/* 48 */     lines.addAll(ASNInteger.print(this.callsInQueue, "callsInQueue", indent));
/* 49 */     lines.addAll(ASNInteger.print(this.agentsLoggedIn, "agentsLoggedIn", indent));
/*    */ 
/* 51 */     lines.add("}");
/* 52 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 57 */     return 12;
/*    */   }
/*    */ 
/*    */   public int getAgentsLoggedIn()
/*    */   {
/* 63 */     return this.agentsLoggedIn;
/*    */   }
/*    */ 
/*    */   public int getAvailableAgents()
/*    */   {
/* 71 */     return this.availableAgents;
/*    */   }
/*    */ 
/*    */   public int getCallsInQueue()
/*    */   {
/* 79 */     return this.callsInQueue;
/*    */   }
/*    */ 
/*    */   public void setAgentsLoggedIn(int agentsLoggedIn) {
/* 83 */     this.agentsLoggedIn = agentsLoggedIn;
/*    */   }
/*    */ 
/*    */   public void setAvailableAgents(int availableAgents) {
/* 87 */     this.availableAgents = availableAgents;
/*    */   }
/*    */ 
/*    */   public void setCallsInQueue(int callsInQueue) {
/* 91 */     this.callsInQueue = callsInQueue;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryAcdSplitConfEvent
 * JD-Core Version:    0.5.4
 */