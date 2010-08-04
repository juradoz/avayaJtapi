/*     */ package com.avaya.jtapi.tsapi.impl.events.call;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ITsapiAddress;
/*     */ import com.avaya.jtapi.tsapi.LookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.LucentCall;
/*     */ import com.avaya.jtapi.tsapi.LucentV5Call;
/*     */ import com.avaya.jtapi.tsapi.LucentV5CallInfo;
/*     */ import com.avaya.jtapi.tsapi.OriginalCallInfo;
/*     */ import com.avaya.jtapi.tsapi.UserEnteredCode;
/*     */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*     */ import java.util.ArrayList;
/*     */ import javax.telephony.Address;
/*     */ import javax.telephony.Call;
/*     */ import javax.telephony.Terminal;
/*     */ import javax.telephony.callcenter.ACDAddress;
/*     */ import javax.telephony.callcenter.CallCenterTrunk;
/*     */ 
/*     */ public class CallEventParams
/*     */ {
/*     */   private Call call;
/*     */   private CallCenterTrunk trunk;
/*     */   private CallCenterTrunk[] trunks;
/*     */   private Address callingAddress;
/*     */   private Terminal callingTerminal;
/*     */   private Address calledAddress;
/*     */   private Address lastRedirectionAddress;
/*     */   private ArrayList<Call> oldCalls;
/*     */   private int cause;
/*     */   private int metaCode;
/*     */   private short cstaCause;
/*     */   private Object privateData;
/*     */   private PrivateDataParams privDataParams;
/*     */ 
/*     */   public CallEventParams()
/*     */   {
/*  30 */     this.call = null;
/*     */ 
/*  34 */     this.trunk = null;
/*  35 */     this.trunks = null;
/*  36 */     this.callingAddress = null;
/*  37 */     this.callingTerminal = null;
/*  38 */     this.calledAddress = null;
/*  39 */     this.lastRedirectionAddress = null;
/*     */ 
/*  55 */     this.cause = 0;
/*  56 */     this.metaCode = 0;
/*     */ 
/*  59 */     this.cstaCause = -1;
/*  60 */     this.privateData = null;
/*     */   }
/*     */ 
/*     */   public ArrayList<Call> getOldCalls()
/*     */   {
/*  45 */     return this.oldCalls;
/*     */   }
/*     */ 
/*     */   public void setOldCalls(ArrayList<Call> oldCalls)
/*     */   {
/*  52 */     this.oldCalls = oldCalls;
/*     */   }
/*     */ 
/*     */   public Call getCall()
/*     */   {
/*  65 */     return this.call;
/*     */   }
/*     */ 
/*     */   public void setCall(Call call) {
/*  69 */     this.call = call;
/*     */   }
/*     */ 
/*     */   public CallCenterTrunk getTrunk() {
/*  73 */     return this.trunk;
/*     */   }
/*     */ 
/*     */   public void setTrunk(CallCenterTrunk trunk) {
/*  77 */     this.trunk = trunk;
/*     */   }
/*     */ 
/*     */   public Address getCallingAddress() {
/*  81 */     return this.callingAddress;
/*     */   }
/*     */ 
/*     */   public void setCallingAddress(Address callingAddress) {
/*  85 */     this.callingAddress = callingAddress;
/*     */   }
/*     */ 
/*     */   public Terminal getCallingTerminal() {
/*  89 */     return this.callingTerminal;
/*     */   }
/*     */ 
/*     */   public void setCallingTerminal(Terminal callingTerminal) {
/*  93 */     this.callingTerminal = callingTerminal;
/*     */   }
/*     */ 
/*     */   public Address getCalledAddress() {
/*  97 */     return this.calledAddress;
/*     */   }
/*     */ 
/*     */   public void setCalledAddress(Address calledAddress) {
/* 101 */     this.calledAddress = calledAddress;
/*     */   }
/*     */ 
/*     */   public Address getLastRedirectionAddress() {
/* 105 */     return this.lastRedirectionAddress;
/*     */   }
/*     */ 
/*     */   public void setLastRedirectionAddress(Address lastRedirectionAddress) {
/* 109 */     this.lastRedirectionAddress = lastRedirectionAddress;
/*     */   }
/*     */ 
/*     */   public int getCause() {
/* 113 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public void setCause(int cause) {
/* 117 */     this.cause = cause;
/*     */   }
/*     */ 
/*     */   public int getMetaCode() {
/* 121 */     return this.metaCode;
/*     */   }
/*     */ 
/*     */   public void setMetaCode(int metaCode) {
/* 125 */     this.metaCode = metaCode;
/*     */   }
/*     */ 
/*     */   public Object getPrivateData() {
/* 129 */     return this.privateData;
/*     */   }
/*     */ 
/*     */   public void setPrivateData(Object privateData)
/*     */   {
/* 137 */     this.privateData = privateData;
/* 138 */     if (privateData instanceof PrivateDataParams) {
/* 139 */       this.privDataParams = ((PrivateDataParams)privateData);
/* 140 */       if (this.privDataParams.getTrunk() != null)
/* 141 */         this.trunk = this.privDataParams.getTrunk();
/*     */     }
/*     */   }
/*     */ 
/*     */   public CallCenterTrunk[] getTrunks()
/*     */   {
/* 147 */     return this.trunks;
/*     */   }
/*     */ 
/*     */   public void setTrunks(CallCenterTrunk[] _trunks) {
/* 151 */     this.trunks = _trunks;
/*     */   }
/*     */ 
/*     */   public short getCstaCause() {
/* 155 */     return this.cstaCause;
/*     */   }
/*     */ 
/*     */   public void setCstaCause(short cstaCause) {
/* 159 */     this.cstaCause = cstaCause;
/*     */   }
/*     */ 
/*     */   public short getReason() {
/* 163 */     short reason = 0;
/* 164 */     if (this.privDataParams != null) {
/* 165 */       reason = this.privDataParams.getReason();
/*     */     }
/*     */     else {
/* 168 */       reason = ((LucentCall)this.call).getReason();
/*     */     }
/* 170 */     return reason;
/*     */   }
/*     */ 
/*     */   public int getCallOriginatorType() {
/* 174 */     int cot = 0;
/* 175 */     if (this.privDataParams != null) {
/* 176 */       cot = this.privDataParams.getCallOriginatorType();
/*     */     }
/* 178 */     else if (this.call instanceof LucentV5CallInfo) {
/* 179 */       cot = ((LucentV5CallInfo)this.call).getCallOriginatorType();
/*     */     }
/* 181 */     return cot;
/*     */   }
/*     */ 
/*     */   public boolean hasCallOriginatorType() {
/* 185 */     boolean has = false;
/* 186 */     if (this.privDataParams != null) {
/* 187 */       has = this.privDataParams.hasCallOriginatorType();
/*     */     }
/* 189 */     else if (this.call instanceof LucentV5CallInfo) {
/* 190 */       has = ((LucentV5CallInfo)this.call).hasCallOriginatorType();
/*     */     }
/* 192 */     return has;
/*     */   }
/*     */ 
/*     */   public UserToUserInfo getUserToUserInfo() {
/* 196 */     UserToUserInfo uui = null;
/* 197 */     if (this.privDataParams != null) {
/* 198 */       uui = this.privDataParams.getUserToUserInfo();
/*     */     }
/*     */     else {
/* 201 */       uui = ((LucentCall)this.call).getUserToUserInfo();
/*     */     }
/* 203 */     return uui;
/*     */   }
/*     */ 
/*     */   public LookaheadInfo getLookaheadInfo() {
/* 207 */     LookaheadInfo lai = null;
/* 208 */     if (this.privDataParams != null) {
/* 209 */       lai = this.privDataParams.getLookaheadInfo();
/*     */     }
/*     */     else {
/* 212 */       lai = ((LucentCall)this.call).getLookaheadInfo();
/*     */     }
/* 214 */     return lai;
/*     */   }
/*     */ 
/*     */   public UserEnteredCode getUserEnteredCode() {
/* 218 */     UserEnteredCode uec = null;
/* 219 */     if (this.privDataParams != null) {
/* 220 */       uec = this.privDataParams.getUserEnteredCode();
/*     */     }
/*     */     else {
/* 223 */       uec = ((LucentCall)this.call).getUserEnteredCode();
/*     */     }
/* 225 */     return uec;
/*     */   }
/*     */ 
/*     */   public OriginalCallInfo getOriginalCallInfo() {
/* 229 */     OriginalCallInfo originalCallInfo = null;
/* 230 */     if (this.privDataParams != null) {
/* 231 */       originalCallInfo = this.privDataParams.getOriginalCallInfo();
/*     */     }
/*     */     else {
/* 234 */       originalCallInfo = ((LucentCall)this.call).getOriginalCallInfo();
/*     */     }
/* 236 */     return originalCallInfo;
/*     */   }
/*     */ 
/*     */   public String getUcid() {
/* 240 */     String ucid = null;
/* 241 */     if (this.privDataParams != null) {
/* 242 */       ucid = this.privDataParams.getUcid();
/*     */     }
/* 244 */     else if (this.call instanceof LucentV5Call) {
/* 245 */       ucid = ((LucentV5Call)this.call).getUCID();
/*     */     }
/* 247 */     return ucid;
/*     */   }
/*     */ 
/*     */   public ACDAddress getSplit() {
/* 251 */     ACDAddress address = null;
/* 252 */     if (this.privDataParams != null) {
/* 253 */       address = this.privDataParams.getSplit();
/*     */     }
/*     */     else {
/* 256 */       address = ((LucentCall)this.call).getDeliveringACDAddress();
/*     */     }
/* 258 */     return address;
/*     */   }
/*     */ 
/*     */   public ITsapiAddress getDistributingDevice() {
/* 262 */     ITsapiAddress address = null;
/* 263 */     if (this.privDataParams != null) {
/* 264 */       address = this.privDataParams.getDistributingDevice();
/*     */     }
/*     */     else {
/* 267 */       address = (ITsapiAddress)((LucentCall)this.call).getDistributingAddress();
/*     */     }
/* 269 */     return address;
/*     */   }
/*     */ 
/*     */   public boolean isFlexibleBilling() {
/* 273 */     boolean flexible = false;
/* 274 */     if (this.privDataParams != null) {
/* 275 */       flexible = this.privDataParams.isFlexibleBilling();
/*     */     }
/* 277 */     return flexible;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.CallEventParams
 * JD-Core Version:    0.5.4
 */