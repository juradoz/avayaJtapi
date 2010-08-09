 package com.avaya.jtapi.tsapi.impl.events.conn;
 
 import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
 import javax.telephony.Address;
 import javax.telephony.MetaEvent;
 import javax.telephony.Terminal;
 import javax.telephony.callcontrol.CallControlConnectionEvent;
 
 public class CallControlConnectionEventImpl extends ConnectionEventImpl
   implements CallControlConnectionEvent
 {
   private String digits;
   private int numInQueue;
 
   public CallControlConnectionEventImpl(CallEventParams params, MetaEvent event, int eventId, int numInQueue, String digits)
   {
     super(params, event, eventId);
     this.numInQueue = numInQueue;
     this.digits = digits;
   }
 
   public String getDigits()
   {
     return this.digits;
   }
 
   public int getNumberInQueue()
   {
     return this.numInQueue;
   }
 
   public Address getCalledAddress()
   {
     return this.callEventParams.getCalledAddress();
   }
 
   public Address getCallingAddress()
   {
     return this.callEventParams.getCallingAddress();
   }
 
   public Terminal getCallingTerminal()
   {
     return this.callEventParams.getCallingTerminal();
   }
 
   public Address getLastRedirectedAddress()
   {
     return this.callEventParams.getLastRedirectionAddress();
   }
 
   public int getCallControlCause()
   {
     return this.callEventParams.getCause();
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.CallControlConnectionEventImpl
 * JD-Core Version:    0.5.4
 */