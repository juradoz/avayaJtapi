/*    */ package com.avaya.jtapi.tsapi.impl.events.terminal;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.TsapiListenerCallControlEvent;
/*    */ import javax.telephony.Terminal;
/*    */ import javax.telephony.callcontrol.CallControlTerminalEvent;
/*    */ 
/*    */ public class CallControlTerminalEventImpl extends TsapiListenerCallControlEvent
/*    */   implements CallControlTerminalEvent
/*    */ {
/*    */   private Terminal terminal;
/* 33 */   private boolean state = false;
/*    */ 
/*    */   public CallControlTerminalEventImpl(TerminalEventParams terminalEventParams, boolean state) {
/* 36 */     super(terminalEventParams.getEventId(), terminalEventParams.getCause(), terminalEventParams.getMetaEvent(), terminalEventParams.getSource(), terminalEventParams.getPrivateData());
/*    */ 
/* 39 */     this.terminal = terminalEventParams.getTerminal();
/* 40 */     this.state = state;
/*    */   }
/*    */ 
/*    */   public Terminal getTerminal() {
/* 44 */     return this.terminal;
/*    */   }
/*    */ 
/*    */   public boolean getDoNotDisturbState() {
/* 48 */     return this.state;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.terminal.CallControlTerminalEventImpl
 * JD-Core Version:    0.5.4
 */