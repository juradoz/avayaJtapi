 package com.avaya.jtapi.tsapi.impl.events.call;
 
 import com.avaya.jtapi.tsapi.ITsapiCallEvent;
 import com.avaya.jtapi.tsapi.LookaheadInfo;
 import com.avaya.jtapi.tsapi.OriginalCallInfo;
 import com.avaya.jtapi.tsapi.UserEnteredCode;
 import com.avaya.jtapi.tsapi.UserToUserInfo;
 import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;
 import javax.telephony.Address;
 import javax.telephony.Call;
 import javax.telephony.Terminal;
 import javax.telephony.callcenter.ACDAddress;
 import javax.telephony.callcenter.CallCenterAddress;
 import javax.telephony.callcenter.CallCenterTrunk;
 import javax.telephony.privatedata.events.PrivateCallEv;
 
 public abstract class TsapiCallEvent extends TsapiObserverEvent
   implements PrivateCallEv, ITsapiCallEvent
 {
   protected CallEventParams params = null;
 
   public final Call getCall()
   {
     return this.params.getCall();
   }
 
   public final Address getCallingAddress()
   {
     return this.params.getCallingAddress();
   }
 
   public final Terminal getCallingTerminal()
   {
     return this.params.getCallingTerminal();
   }
 
   public final Address getCalledAddress()
   {
     return this.params.getCalledAddress();
   }
 
   public final Address getLastRedirectedAddress()
   {
     return this.params.getLastRedirectionAddress();
   }
 
   public CallCenterTrunk[] getTrunks()
   {
     return this.params.getTrunks();
   }
 
   public final UserToUserInfo getUserToUserInfo()
   {
     return this.params.getUserToUserInfo();
   }
 
   public final LookaheadInfo getLookaheadInfo()
   {
     return this.params.getLookaheadInfo();
   }
 
   public final UserEnteredCode getUserEnteredCode()
   {
     return this.params.getUserEnteredCode();
   }
 
   public final OriginalCallInfo getOriginalCallInfo()
   {
     return this.params.getOriginalCallInfo();
   }
 
   public final String getUCID()
   {
     return this.params.getUcid();
   }
 
   public final int getCallOriginatorType()
   {
     return this.params.getCallOriginatorType();
   }
 
   public final boolean hasCallOriginatorType()
   {
     return this.params.hasCallOriginatorType();
   }
 
   public final boolean canSetBillRate()
   {
     return this.params.isFlexibleBilling();
   }
 
   public final CallCenterAddress getDistributingAddress()
   {
     return this.params.getDistributingDevice();
   }
 
   public final ACDAddress getDeliveringACDAddress()
   {
     return this.params.getSplit();
   }
 
   public CallCenterTrunk getTrunk()
   {
     return this.params.getTrunk();
   }
 
   public final short getReason()
   {
     return this.params.getReason();
   }
 
   public short getCSTACause()
   {
     return this.params.getCstaCause();
   }
 
   public TsapiCallEvent(CallEventParams params, int _eventPackage)
   {
     super(params.getCause(), params.getMetaCode(), params.getPrivateData(), _eventPackage);
     this.params = params;
   }
 
   public TsapiCallEvent(CallEventParams params)
   {
     this(params, 0);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.TsapiCallEvent
 * JD-Core Version:    0.5.4
 */