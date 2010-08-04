/*    */ package com.avaya.jtapi.tsapi.impl.events.terminal;
/*    */ 
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.Terminal;
/*    */ 
/*    */ public class TerminalEventParams
/*    */ {
/*    */   int eventId;
/*    */   int cause;
/*    */   MetaEvent metaEvent;
/*    */   Object source;
/*    */   Terminal terminal;
/*    */   Object privateData;
/*    */ 
/*    */   public int getEventId()
/*    */   {
/* 35 */     return this.eventId;
/*    */   }
/*    */   public void setEventId(int eventId) {
/* 38 */     this.eventId = eventId;
/*    */   }
/*    */   public int getCause() {
/* 41 */     return this.cause;
/*    */   }
/*    */   public void setCause(int cause) {
/* 44 */     this.cause = cause;
/*    */   }
/*    */   public MetaEvent getMetaEvent() {
/* 47 */     return this.metaEvent;
/*    */   }
/*    */   public void setMetaEvent(MetaEvent metaEvent) {
/* 50 */     this.metaEvent = metaEvent;
/*    */   }
/*    */   public Object getSource() {
/* 53 */     return this.source;
/*    */   }
/*    */   public void setSource(Object source) {
/* 56 */     this.source = source;
/*    */   }
/*    */   public Terminal getTerminal() {
/* 59 */     return this.terminal;
/*    */   }
/*    */   public void setTerminal(Terminal terminal) {
/* 62 */     this.terminal = terminal;
/*    */   }
/*    */   public Object getPrivateData() {
/* 65 */     return this.privateData;
/*    */   }
/*    */   public void setPrivateData(Object o) {
/* 68 */     this.privateData = o;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.terminal.TerminalEventParams
 * JD-Core Version:    0.5.4
 */