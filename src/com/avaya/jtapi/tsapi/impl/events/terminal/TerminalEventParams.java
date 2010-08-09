 package com.avaya.jtapi.tsapi.impl.events.terminal;
 
 import javax.telephony.MetaEvent;
 import javax.telephony.Terminal;
 
 public class TerminalEventParams
 {
   int eventId;
   int cause;
   MetaEvent metaEvent;
   Object source;
   Terminal terminal;
   Object privateData;
 
   public int getEventId()
   {
     return this.eventId;
   }
   public void setEventId(int eventId) {
     this.eventId = eventId;
   }
   public int getCause() {
     return this.cause;
   }
   public void setCause(int cause) {
     this.cause = cause;
   }
   public MetaEvent getMetaEvent() {
     return this.metaEvent;
   }
   public void setMetaEvent(MetaEvent metaEvent) {
     this.metaEvent = metaEvent;
   }
   public Object getSource() {
     return this.source;
   }
   public void setSource(Object source) {
     this.source = source;
   }
   public Terminal getTerminal() {
     return this.terminal;
   }
   public void setTerminal(Terminal terminal) {
     this.terminal = terminal;
   }
   public Object getPrivateData() {
     return this.privateData;
   }
   public void setPrivateData(Object o) {
     this.privateData = o;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.terminal.TerminalEventParams
 * JD-Core Version:    0.5.4
 */