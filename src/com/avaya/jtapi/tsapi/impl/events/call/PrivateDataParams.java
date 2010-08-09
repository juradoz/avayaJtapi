 package com.avaya.jtapi.tsapi.impl.events.call;
 
 import com.avaya.jtapi.tsapi.ITsapiAddress;
 import com.avaya.jtapi.tsapi.LookaheadInfo;
 import com.avaya.jtapi.tsapi.OriginalCallInfo;
 import com.avaya.jtapi.tsapi.UserEnteredCode;
 import com.avaya.jtapi.tsapi.UserToUserInfo;
 import javax.telephony.callcenter.ACDAddress;
 import javax.telephony.callcenter.CallCenterTrunk;
 
 public class PrivateDataParams
 {
   private CallCenterTrunk trunk;
   private short reason;
   private UserToUserInfo userToUserInfo;
   private LookaheadInfo lookaheadInfo;
   private UserEnteredCode userEnteredCode;
   private OriginalCallInfo originalCallInfo;
   private String ucid;
   private int callOriginatorType;
   private boolean hasCallOriginatorType;
   private ACDAddress split;
   private ITsapiAddress distributingDevice;
   private boolean flexibleBilling;
 
   public PrivateDataParams()
   {
     this.trunk = null;
     this.reason = 0;
     this.userToUserInfo = null;
     this.lookaheadInfo = null;
     this.userEnteredCode = null;
     this.originalCallInfo = null;
     this.ucid = null;
     this.callOriginatorType = -1;
     this.hasCallOriginatorType = false;
     this.split = null;
     this.distributingDevice = null;
     this.flexibleBilling = false;
   }
   public CallCenterTrunk getTrunk() {
     return this.trunk;
   }
 
   public void setTrunk(CallCenterTrunk trunk) {
     this.trunk = trunk;
   }
 
   public short getReason() {
     return this.reason;
   }
 
   public void setReason(short reason) {
     this.reason = reason;
   }
 
   public int getCallOriginatorType() {
     return this.callOriginatorType;
   }
 
   public void setCallOriginatorType(int callOriginatorType) {
     this.callOriginatorType = callOriginatorType;
   }
 
   public boolean hasCallOriginatorType() {
     return this.hasCallOriginatorType;
   }
 
   public void setHasCallOriginatorType(boolean hasCallOriginatorType) {
     this.hasCallOriginatorType = hasCallOriginatorType;
   }
 
   public UserToUserInfo getUserToUserInfo() {
     return this.userToUserInfo;
   }
 
   public void setUserToUserInfo(UserToUserInfo userToUserInfo) {
     this.userToUserInfo = userToUserInfo;
   }
 
   public LookaheadInfo getLookaheadInfo() {
     return this.lookaheadInfo;
   }
 
   public void setLookaheadInfo(LookaheadInfo lookaheadInfo) {
     this.lookaheadInfo = lookaheadInfo;
   }
 
   public UserEnteredCode getUserEnteredCode() {
     return this.userEnteredCode;
   }
 
   public void setUserEnteredCode(UserEnteredCode userEnteredCode) {
     this.userEnteredCode = userEnteredCode;
   }
 
   public OriginalCallInfo getOriginalCallInfo() {
     return this.originalCallInfo;
   }
 
   public void setOriginalCallInfo(OriginalCallInfo originalCallInfo) {
     this.originalCallInfo = originalCallInfo;
   }
 
   public String getUcid() {
     return this.ucid;
   }
 
   public void setUcid(String ucid) {
     this.ucid = ucid;
   }
 
   public ACDAddress getSplit() {
     return this.split;
   }
 
   public void setSplit(ACDAddress split) {
     this.split = split;
   }
 
   public ITsapiAddress getDistributingDevice() {
     return this.distributingDevice;
   }
 
   public void setDistributingDevice(ITsapiAddress distributingDevice) {
     this.distributingDevice = distributingDevice;
   }
 
   public boolean isFlexibleBilling() {
     return this.flexibleBilling;
   }
 
   public void setFlexibleBilling(boolean flexibleBilling) {
     this.flexibleBilling = flexibleBilling;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.PrivateDataParams
 * JD-Core Version:    0.5.4
 */