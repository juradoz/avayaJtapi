/*    */ package com.avaya.jtapi.tsapi.impl.events.terminal;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;
/*    */ import javax.telephony.Terminal;
/*    */ import javax.telephony.privatedata.events.PrivateTermEv;
/*    */ 
/*    */ public abstract class TsapiTerminalEvent extends TsapiObserverEvent
/*    */   implements PrivateTermEv
/*    */ {
/* 25 */   private Terminal terminal = null;
/*    */ 
/*    */   public final Terminal getTerminal()
/*    */   {
/* 21 */     return this.terminal;
/*    */   }
/*    */ 
/*    */   public TsapiTerminalEvent(Terminal _terminal, int _cause, int _metaCode, Object _privateData, int _eventPackage)
/*    */   {
/* 32 */     super(_cause, _metaCode, _privateData, _eventPackage);
/* 33 */     this.terminal = _terminal;
/*    */   }
/*    */ 
/*    */   public TsapiTerminalEvent(Terminal _terminal, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 40 */     this(_terminal, _cause, _metaCode, _privateData, 0);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTerminalEvent
 * JD-Core Version:    0.5.4
 */