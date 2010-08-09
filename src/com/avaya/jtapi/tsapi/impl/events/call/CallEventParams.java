 package com.avaya.jtapi.tsapi.impl.events.call;
 
 import com.avaya.jtapi.tsapi.ITsapiAddress;
 import com.avaya.jtapi.tsapi.LookaheadInfo;
 import com.avaya.jtapi.tsapi.LucentCall;
 import com.avaya.jtapi.tsapi.LucentV5Call;
 import com.avaya.jtapi.tsapi.LucentV5CallInfo;
 import com.avaya.jtapi.tsapi.OriginalCallInfo;
 import com.avaya.jtapi.tsapi.UserEnteredCode;
 import com.avaya.jtapi.tsapi.UserToUserInfo;
 import java.util.ArrayList;
 import javax.telephony.Address;
 import javax.telephony.Call;
 import javax.telephony.Terminal;
 import javax.telephony.callcenter.ACDAddress;
 import javax.telephony.callcenter.CallCenterTrunk;
 
 public class CallEventParams
 {
   private Call call;
   private CallCenterTrunk trunk;
   private CallCenterTrunk[] trunks;
   private Address callingAddress;
   private Terminal callingTerminal;
   private Address calledAddress;
   private Address lastRedirectionAddress;
   private ArrayList<Call> oldCalls;
   private int cause;
   private int metaCode;
   private short cstaCause;
   private Object privateData;
   private PrivateDataParams privDataParams;
 
   public CallEventParams()
   {
     this.call = null;
 
     this.trunk = null;
     this.trunks = null;
     this.callingAddress = null;
     this.callingTerminal = null;
     this.calledAddress = null;
     this.lastRedirectionAddress = null;
 
     this.cause = 0;
     this.metaCode = 0;
 
     this.cstaCause = -1;
     this.privateData = null;
   }
 
   public ArrayList<Call> getOldCalls()
   {
     return this.oldCalls;
   }
 
   public void setOldCalls(ArrayList<Call> oldCalls)
   {
     this.oldCalls = oldCalls;
   }
 
   public Call getCall()
   {
     return this.call;
   }
 
   public void setCall(Call call) {
     this.call = call;
   }
 
   public CallCenterTrunk getTrunk() {
     return this.trunk;
   }
 
   public void setTrunk(CallCenterTrunk trunk) {
     this.trunk = trunk;
   }
 
   public Address getCallingAddress() {
     return this.callingAddress;
   }
 
   public void setCallingAddress(Address callingAddress) {
     this.callingAddress = callingAddress;
   }
 
   public Terminal getCallingTerminal() {
     return this.callingTerminal;
   }
 
   public void setCallingTerminal(Terminal callingTerminal) {
     this.callingTerminal = callingTerminal;
   }
 
   public Address getCalledAddress() {
     return this.calledAddress;
   }
 
   public void setCalledAddress(Address calledAddress) {
     this.calledAddress = calledAddress;
   }
 
   public Address getLastRedirectionAddress() {
     return this.lastRedirectionAddress;
   }
 
   public void setLastRedirectionAddress(Address lastRedirectionAddress) {
     this.lastRedirectionAddress = lastRedirectionAddress;
   }
 
   public int getCause() {
     return this.cause;
   }
 
   public void setCause(int cause) {
     this.cause = cause;
   }
 
   public int getMetaCode() {
     return this.metaCode;
   }
 
   public void setMetaCode(int metaCode) {
     this.metaCode = metaCode;
   }
 
   public Object getPrivateData() {
     return this.privateData;
   }
 
   public void setPrivateData(Object privateData)
   {
     this.privateData = privateData;
     if (privateData instanceof PrivateDataParams) {
       this.privDataParams = ((PrivateDataParams)privateData);
       if (this.privDataParams.getTrunk() != null)
         this.trunk = this.privDataParams.getTrunk();
     }
   }
 
   public CallCenterTrunk[] getTrunks()
   {
     return this.trunks;
   }
 
   public void setTrunks(CallCenterTrunk[] _trunks) {
     this.trunks = _trunks;
   }
 
   public short getCstaCause() {
     return this.cstaCause;
   }
 
   public void setCstaCause(short cstaCause) {
     this.cstaCause = cstaCause;
   }
 
   public short getReason() {
     short reason = 0;
     if (this.privDataParams != null) {
       reason = this.privDataParams.getReason();
     }
     else {
       reason = ((LucentCall)this.call).getReason();
     }
     return reason;
   }
 
   public int getCallOriginatorType() {
     int cot = 0;
     if (this.privDataParams != null) {
       cot = this.privDataParams.getCallOriginatorType();
     }
     else if (this.call instanceof LucentV5CallInfo) {
       cot = ((LucentV5CallInfo)this.call).getCallOriginatorType();
     }
     return cot;
   }
 
   public boolean hasCallOriginatorType() {
     boolean has = false;
     if (this.privDataParams != null) {
       has = this.privDataParams.hasCallOriginatorType();
     }
     else if (this.call instanceof LucentV5CallInfo) {
       has = ((LucentV5CallInfo)this.call).hasCallOriginatorType();
     }
     return has;
   }
 
   public UserToUserInfo getUserToUserInfo() {
     UserToUserInfo uui = null;
     if (this.privDataParams != null) {
       uui = this.privDataParams.getUserToUserInfo();
     }
     else {
       uui = ((LucentCall)this.call).getUserToUserInfo();
     }
     return uui;
   }
 
   public LookaheadInfo getLookaheadInfo() {
     LookaheadInfo lai = null;
     if (this.privDataParams != null) {
       lai = this.privDataParams.getLookaheadInfo();
     }
     else {
       lai = ((LucentCall)this.call).getLookaheadInfo();
     }
     return lai;
   }
 
   public UserEnteredCode getUserEnteredCode() {
     UserEnteredCode uec = null;
     if (this.privDataParams != null) {
       uec = this.privDataParams.getUserEnteredCode();
     }
     else {
       uec = ((LucentCall)this.call).getUserEnteredCode();
     }
     return uec;
   }
 
   public OriginalCallInfo getOriginalCallInfo() {
     OriginalCallInfo originalCallInfo = null;
     if (this.privDataParams != null) {
       originalCallInfo = this.privDataParams.getOriginalCallInfo();
     }
     else {
       originalCallInfo = ((LucentCall)this.call).getOriginalCallInfo();
     }
     return originalCallInfo;
   }
 
   public String getUcid() {
     String ucid = null;
     if (this.privDataParams != null) {
       ucid = this.privDataParams.getUcid();
     }
     else if (this.call instanceof LucentV5Call) {
       ucid = ((LucentV5Call)this.call).getUCID();
     }
     return ucid;
   }
 
   public ACDAddress getSplit() {
     ACDAddress address = null;
     if (this.privDataParams != null) {
       address = this.privDataParams.getSplit();
     }
     else {
       address = ((LucentCall)this.call).getDeliveringACDAddress();
     }
     return address;
   }
 
   public ITsapiAddress getDistributingDevice() {
     ITsapiAddress address = null;
     if (this.privDataParams != null) {
       address = this.privDataParams.getDistributingDevice();
     }
     else {
       address = (ITsapiAddress)((LucentCall)this.call).getDistributingAddress();
     }
     return address;
   }
 
   public boolean isFlexibleBilling() {
     boolean flexible = false;
     if (this.privDataParams != null) {
       flexible = this.privDataParams.isFlexibleBilling();
     }
     return flexible;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.CallEventParams
 * JD-Core Version:    0.5.4
 */