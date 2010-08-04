/*    */ package com.avaya.jtapi.tsapi.impl.events.terminal;
/*    */ 
/*    */ import javax.telephony.Terminal;
/*    */ import javax.telephony.privatedata.events.PrivateTermEv;
/*    */ 
/*    */ public final class TsapiPrivateTerminalEvent extends TsapiTerminalEvent
/*    */   implements PrivateTermEv
/*    */ {
/*    */   public int getID()
/*    */   {
/* 19 */     return 603;
/*    */   }
/*    */ 
/*    */   public TsapiPrivateTerminalEvent(Terminal _terminal, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 28 */     super(_terminal, _cause, _metaCode, _privateData, 5);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.terminal.TsapiPrivateTerminalEvent
 * JD-Core Version:    0.5.4
 */