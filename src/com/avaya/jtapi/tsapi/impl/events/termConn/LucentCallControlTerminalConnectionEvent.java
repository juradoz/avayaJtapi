 package com.avaya.jtapi.tsapi.impl.events.termConn;
 
 import com.avaya.jtapi.tsapi.LookaheadInfo;
 import com.avaya.jtapi.tsapi.LucentCallInfo;
 import com.avaya.jtapi.tsapi.OriginalCallInfo;
 import com.avaya.jtapi.tsapi.UserEnteredCode;
 import com.avaya.jtapi.tsapi.UserToUserInfo;
 import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
 import javax.telephony.MetaEvent;
 import javax.telephony.callcenter.ACDAddress;
 import javax.telephony.callcenter.CallCenterAddress;
 import javax.telephony.callcenter.CallCenterTrunk;
 
 public class LucentCallControlTerminalConnectionEvent extends CallControlTerminalConnectionEventImpl
   implements LucentCallInfo
 {
   public LucentCallControlTerminalConnectionEvent(CallEventParams params, MetaEvent event, int eventId)
   {
     super(params, event, eventId);
   }
 
   public ACDAddress getDeliveringACDAddress()
   {
     return this.callEventParams.getSplit();
   }
 
   public CallCenterAddress getDistributingAddress()
   {
     return this.callEventParams.getDistributingDevice();
   }
 
   public LookaheadInfo getLookaheadInfo()
   {
     return this.callEventParams.getLookaheadInfo();
   }
 
   public OriginalCallInfo getOriginalCallInfo()
   {
     return this.callEventParams.getOriginalCallInfo();
   }
 
   public short getReason()
   {
     return this.callEventParams.getReason();
   }
 
   public CallCenterTrunk getTrunk()
   {
     return this.callEventParams.getTrunk();
   }
 
   public UserEnteredCode getUserEnteredCode()
   {
     return this.callEventParams.getUserEnteredCode();
   }
 
   public UserToUserInfo getUserToUserInfo()
   {
     return this.callEventParams.getUserToUserInfo();
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.LucentCallControlTerminalConnectionEvent
 * JD-Core Version:    0.5.4
 */