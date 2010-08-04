/*    */ package com.avaya.jtapi.tsapi.impl.events.terminal;
/*    */ 
/*    */ import javax.telephony.Terminal;
/*    */ import javax.telephony.callcontrol.events.CallCtlTermDoNotDisturbEv;
/*    */ 
/*    */ public final class TsapiTerminalDNDEvent extends TsapiCallCtlTerminalEvent
/*    */   implements CallCtlTermDoNotDisturbEv
/*    */ {
/* 27 */   private boolean state = false;
/*    */ 
/*    */   public boolean getDoNotDisturbState()
/*    */   {
/* 17 */     return this.state;
/*    */   }
/*    */ 
/*    */   public int getID()
/*    */   {
/* 23 */     return 221;
/*    */   }
/*    */ 
/*    */   public TsapiTerminalDNDEvent(Terminal _device, boolean _state, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 34 */     super(_device, _cause, _metaCode, _privateData);
/* 35 */     this.state = _state;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTerminalDNDEvent
 * JD-Core Version:    0.5.4
 */