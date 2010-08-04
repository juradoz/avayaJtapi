/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ITsapiException;
/*     */ import com.avaya.jtapi.tsapi.LucentAgentStateInfoEx;
/*     */ import com.avaya.jtapi.tsapi.LucentV5AgentStateInfo;
/*     */ import com.avaya.jtapi.tsapi.LucentV6AgentStateInfoEx;
/*     */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*     */ import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
/*     */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentState;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentSetAgentState;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV5SetAgentState;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV6SetAgentState;
/*     */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
/*     */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
/*     */ import java.util.Vector;
import org.apache.log4j.Logger;
/*     */ 
/*     */ public final class TSAgent
/*     */ {
/*  37 */   private static Logger log = Logger.getLogger(TSAgent.class);
/*     */   private TSProviderImpl provider;
/*     */   TSDevice agentDevice;
/*     */   private int state;
/*     */   private int lucentWorkMode;
/*     */   private int workMode;
/*     */   private int reasonCode;
/*     */   private int pendingState;
/*     */   private int pendingReasonCode;
/*     */   private String agentID;
/*     */   private TSDevice acdDevice;
/*     */   private String passwd;
/*     */   private TSAgentKey agentKey;
/* 687 */   boolean constructed = false;
/*     */   private Vector<TSDevice> skillsVector;
/* 689 */   private int refCount = 0;
/*     */ 
/*     */   void dump(String indent)
/*     */   {
/*  41 */     log.trace(indent + "***** AGENT DUMP *****");
/*  42 */     log.trace(indent + "TSAgent: " + this);
/*  43 */     log.trace(indent + "TSAgent ID: " + this.agentID);
/*  44 */     log.trace(indent + "TSAgent key: " + this.agentKey);
/*  45 */     log.trace(indent + "TSAgent state: " + this.state);
/*  46 */     log.trace(indent + "TSAgent workMode: " + this.workMode);
/*  47 */     log.trace(indent + "TSAgent agentDevice: " + this.agentDevice);
/*  48 */     log.trace(indent + "TSAgent acdDevice: " + this.acdDevice);
/*  49 */     log.trace(indent + "***** AGENT DUMP END *****");
/*     */   }
/*     */ 
/*     */   public TSProviderImpl getTSProviderImpl()
/*     */   {
/*  54 */     return this.provider;
/*     */   }
/*     */ 
/*     */   Vector<TsapiTerminalMonitor> getTerminalObservers()
/*     */   {
/*  59 */     return this.agentDevice.getTerminalObservers();
/*     */   }
/*     */ 
/*     */   void updateState(int _state, int _workMode, int _reasonCode, Vector<TSEvent> eventList)
/*     */   {
/*  67 */     updateState(_state, _workMode, _reasonCode, 0, 0, eventList);
/*     */   }
/*     */ 
/*     */   void updateState(int _state, int _workMode, int _reasonCode, int _pendingState, int _pendingReasonCode, Vector<TSEvent> eventList)
/*     */   {
/*  86 */     updateState(_state, _workMode, _reasonCode, _pendingState, _pendingReasonCode, -1, eventList);
/*     */   }
/*     */ 
/*     */   void updateState(int _state, int _workMode, int _reasonCode, int _pendingState, int _pendingReasonCode, int _lucentworkmode, Vector<TSEvent> eventList)
/*     */   {
/* 100 */     boolean stateChange = false;
/* 101 */     synchronized (this)
/*     */     {
/* 106 */       if (((this.state == _state) && (this.workMode == _workMode) && (this.reasonCode == _reasonCode) && (this.pendingState == _pendingState) && (this.pendingReasonCode == _pendingReasonCode) && (this.lucentWorkMode == _lucentworkmode)) || (this.state == 2))
/*     */       {
/* 113 */         return;
/*     */       }
/*     */ 
/* 116 */       if (this.state != _state)
/*     */       {
/* 118 */         stateChange = true;
/*     */       }
/* 120 */       this.state = _state;
/* 121 */       this.workMode = _workMode;
/*     */     }
/*     */ 
/* 124 */     this.reasonCode = _reasonCode;
/* 125 */     this.pendingState = _pendingState;
/* 126 */     this.pendingReasonCode = _pendingReasonCode;
/* 127 */     this.lucentWorkMode = _lucentworkmode;
/*     */ 
/* 129 */     if (stateChange)
/*     */     {
/* 131 */       Vector localEventList = new Vector();
/*     */ 
/* 133 */       getEvent(this.state, localEventList);
/* 134 */       if (eventList == null)
/*     */       {
/* 136 */         if (localEventList.size() > 0)
/*     */         {
/* 138 */           Vector observers = null;
/* 139 */           if (this.acdDevice != null)
/*     */           {
/* 141 */             observers = this.acdDevice.getAddressObservers();
/* 142 */             for (int j = 0; j < observers.size(); ++j)
/*     */             {
/* 144 */               TsapiAddressMonitor callback = (TsapiAddressMonitor)observers.elementAt(j);
/* 145 */               callback.deliverEvents(localEventList, false);
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 151 */             for (int i = 0; i < this.skillsVector.size(); ++i)
/*     */             {
/* 153 */               TSDevice skillDevice = (TSDevice)this.skillsVector.elementAt(i);
/* 154 */               observers = skillDevice.getAddressObservers();
/* 155 */               for (int j = 0; j < localEventList.size(); ++j)
/*     */               {
/* 157 */                 TSEvent ev = (TSEvent)localEventList.elementAt(j);
/* 158 */                 Object tsTarget = ev.getEventTarget();
/* 159 */                 if (!(tsTarget instanceof TSAgent))
/*     */                   continue;
/* 161 */                 ev.setSkillDevice(skillDevice);
/*     */               }
/*     */ 
/* 164 */               for (int j = 0; j < observers.size(); ++j)
/*     */               {
/* 166 */                 TsapiAddressMonitor callback = (TsapiAddressMonitor)observers.elementAt(j);
/* 167 */                 callback.deliverEvents(localEventList, false);
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 172 */           Vector tObservers = getTerminalObservers();
/* 173 */           for (int j = 0; j < tObservers.size(); ++j)
/*     */           {
/* 175 */             TsapiTerminalMonitor callback = (TsapiTerminalMonitor)tObservers.elementAt(j);
/* 176 */             callback.deliverEvents(localEventList, false);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*     */         int i;
/* 182 */         for ( i = 0; i < localEventList.size(); ++i)
/*     */         {
/* 184 */           eventList.addElement((TSEvent) localEventList.elementAt(i));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 189 */     if (this.state != 2)
/*     */       return;
/* 191 */     this.agentDevice.removeFromAgentTermVector(this);
/* 192 */     if (this.acdDevice != null) {
/* 193 */       this.acdDevice.removeFromACDVector(this);
/*     */     }
/*     */     else
/*     */     {
/* 197 */       for (int i = 0; i < this.skillsVector.size(); ++i)
/*     */       {
/* 199 */         ((TSDevice)this.skillsVector.elementAt(i)).removeFromACDVector(this);
/*     */       }
/*     */     }
/* 202 */     delete();
/*     */   }
/*     */ 
/*     */   void getEvent(int state, Vector<TSEvent> localEventList)
/*     */   {
/* 209 */     switch (state)
/*     */     {
/*     */     case 1:
/* 212 */       localEventList.addElement(new TSEvent(40, this));
/* 213 */       localEventList.addElement(new TSEvent(47, this));
/* 214 */       break;
/*     */     case 2:
/* 216 */       localEventList.addElement(new TSEvent(41, this));
/* 217 */       localEventList.addElement(new TSEvent(48, this));
/* 218 */       break;
/*     */     case 4:
/* 220 */       localEventList.addElement(new TSEvent(42, this));
/* 221 */       localEventList.addElement(new TSEvent(49, this));
/* 222 */       break;
/*     */     case 3:
/* 224 */       localEventList.addElement(new TSEvent(43, this));
/* 225 */       localEventList.addElement(new TSEvent(50, this));
/* 226 */       break;
/*     */     case 6:
/* 228 */       localEventList.addElement(new TSEvent(44, this));
/* 229 */       localEventList.addElement(new TSEvent(51, this));
/* 230 */       break;
/*     */     case 5:
/* 232 */       localEventList.addElement(new TSEvent(45, this));
/* 233 */       localEventList.addElement(new TSEvent(52, this));
/* 234 */       break;
/*     */     case 7:
/* 236 */       localEventList.addElement(new TSEvent(46, this));
/* 237 */       localEventList.addElement(new TSEvent(53, this));
/*     */     }
/*     */   }
/*     */ 
/*     */   private LucentSetAgentState createLucentSetAgentState(int workMode, int reasonCode, boolean enablePending)
/*     */   {
/* 249 */     if (this.provider.isLucentV6())
/* 250 */       return new LucentV6SetAgentState((short)workMode, reasonCode, enablePending);
/* 251 */     if (this.provider.isLucentV5()) {
/* 252 */       return new LucentV5SetAgentState((short)workMode, reasonCode);
/*     */     }
/* 254 */     return new LucentSetAgentState((short)workMode);
/*     */   }
/*     */ 
/*     */   private LucentSetAgentState createLucentSetAgentState(int workMode, int reasonCode)
/*     */   {
/* 259 */     return createLucentSetAgentState(workMode, reasonCode, false);
/*     */   }
/*     */ 
/*     */   private LucentSetAgentState createLucentSetAgentState(int workMode)
/*     */   {
/* 264 */     return createLucentSetAgentState(workMode, 0, false);
/*     */   }
/*     */ 
/*     */   void setState(int _state, int _workMode, int _reasonCode)
/*     */     throws TsapiInvalidArgumentException, TsapiInvalidStateException
/*     */   {
/* 276 */     setState(_state, _workMode, _reasonCode, false);
/*     */   }
/*     */ 
/*     */   public boolean setState(int _state, int _workMode, int _reasonCode, boolean _enablePending)
/*     */     throws TsapiInvalidArgumentException, TsapiInvalidStateException
/*     */   {
/* 298 */     int effectivePendingReasonCode = 0;
/*     */ 
/* 303 */     if (((this.state == _state) && (this.workMode == _workMode) && (this.reasonCode == _reasonCode)) || (this.state == 2))
/*     */     {
/* 307 */       return false;
/*     */     }
/*     */ 
/* 311 */     if ((_state != 1) && (_state != 2) && (_state != 4) && (_state != 3) && (_state != 6) && (_state != 5))
/*     */     {
/* 315 */       throw new TsapiInvalidArgumentException(3, 0, "state not valid");
/*     */     }
/*     */ 
/* 319 */     CSTAPrivate reqPriv = null;
/* 320 */     if (this.provider.isLucent())
/*     */     {
/* 322 */       LucentSetAgentState lsas = null;
/*     */ 
/* 324 */       if (_state == 3)
/*     */       {
/* 330 */         lsas = createLucentSetAgentState(-1, _reasonCode, _enablePending);
/*     */ 
/* 333 */         effectivePendingReasonCode = _reasonCode;
/*     */       }
/* 335 */       if ((_state == 4) && (_workMode != 0))
/*     */       {
/* 337 */         if (_workMode == 1)
/* 338 */           lsas = createLucentSetAgentState(3);
/* 339 */         else if (_workMode == 2)
/* 340 */           lsas = createLucentSetAgentState(4);
/*     */       }
/* 342 */       if (_state == 5)
/*     */       {
/* 349 */         lsas = createLucentSetAgentState(-1, 0, _enablePending);
/*     */       }
/* 351 */       if ((_state == 2) && (this.provider.isLucentV5()))
/*     */       {
/* 353 */         lsas = createLucentSetAgentState(-1, _reasonCode);
/*     */       }
/* 355 */       if (lsas != null) {
/* 356 */         reqPriv = lsas.makeTsapiPrivate();
/*     */       }
/*     */     }
/* 359 */     if (this.acdDevice != null)
/* 360 */       this.agentDevice.setTSAgent(this.acdDevice.getName(), _state, this.agentID, this.passwd, reqPriv);
/*     */     else {
/* 362 */       this.agentDevice.setTSAgent(null, _state, this.agentID, this.passwd, reqPriv);
/*     */     }
/* 364 */     if (this.agentDevice.wereChangesHeldPending())
/*     */     {
/* 374 */       updateState(this.state, this.workMode, this.reasonCode, _state, effectivePendingReasonCode, null);
/*     */     }
/*     */     else
/*     */     {
/* 379 */       updateState(_state, _workMode, _reasonCode, null);
/*     */     }
/*     */ 
/* 384 */     return this.agentDevice.wereChangesHeldPending();
/*     */   }
/*     */ 
/*     */   int getInternalState()
/*     */   {
/* 390 */     return this.state;
/*     */   }
/*     */ 
/*     */   public int getState()
/*     */   {
/* 399 */     if ((this.state == 2) || ((monitorIsSet()) && (this.provider.getCapabilities().getReadyEvent() != 0)))
/*     */     {
/* 407 */       return this.state;
/*     */     }
/* 409 */     if (this.provider.getCapabilities().getQueryAgentState() != 0)
/*     */     {
/* 412 */       CSTAPrivate priv = null;
/*     */ 
/* 415 */       if ((this.provider.isLucent()) && (this.acdDevice != null))
/*     */       {
/* 417 */         LucentQueryAgentState lqas = new LucentQueryAgentState(this.acdDevice.getName());
/* 418 */         priv = lqas.makeTsapiPrivate();
/*     */       }
/*     */ 
/* 422 */       ConfHandler handler = new QueryAgentStateConfHandler(this);
/*     */       try
/*     */       {
/* 425 */         this.provider.tsapi.queryAgentState(this.agentDevice.getName(), priv, handler);
/*     */       }
/*     */       catch (TsapiPlatformException e)
/*     */       {
/* 429 */         throw e;
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 433 */         if (e instanceof ITsapiException) {
/* 434 */           throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "queryAgentState failure");
/*     */         }
/* 436 */         throw new TsapiPlatformException(4, 0, "queryAgentState failure");
/*     */       }
/* 438 */       return this.state;
/*     */     }
/*     */ 
/* 443 */     return this.state;
/*     */   }
/*     */ 
/*     */   boolean monitorIsSet()
/*     */   {
/* 449 */     if (this.acdDevice != null)
/*     */     {
/* 451 */       if (this.acdDevice.getAddressObservers().size() > 0) {
/* 452 */         return true;
/*     */       }
/*     */     }
/*     */     else {
/* 456 */       for (int i = 0; i < this.skillsVector.size(); ++i)
/*     */       {
/* 458 */         if (((TSDevice)this.skillsVector.elementAt(i)).getAddressObservers().size() > 0) {
/* 459 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 463 */     return this.agentDevice.getTerminalObservers().size() > 0;
/*     */   }
/*     */ 
/*     */   void getSnapshot(Vector<TSEvent> eventList)
/*     */   {
/* 470 */     if (eventList == null)
/*     */     {
/* 472 */       return;
/*     */     }
/* 474 */     getEvent(this.state, eventList);
/*     */   }
/*     */ 
/*     */   public LucentAgentStateInfoEx getStateInfo()
/*     */   {
/* 483 */     getState();
/*     */ 
/* 485 */     if (this.provider.isLucentV5())
/*     */     {
/* 487 */       if (this.provider.isLucentV6()) {
/* 488 */         return new LucentV6AgentStateInfoEx(this.state, this.workMode, this.reasonCode, this.pendingState, this.pendingReasonCode, this.lucentWorkMode);
/*     */       }
/* 490 */       return new LucentV5AgentStateInfo(this.state, this.workMode, this.reasonCode);
/*     */     }
/*     */ 
/* 493 */     return new LucentAgentStateInfoEx(this.state, this.workMode);
/*     */   }
/*     */ 
/*     */   public TSDevice getTSAgentDevice()
/*     */   {
/* 499 */     return this.agentDevice;
/*     */   }
/*     */ 
/*     */   public TSDevice getTSACDDevice()
/*     */   {
/* 505 */     return this.acdDevice;
/*     */   }
/*     */ 
/*     */   public String getAgentID()
/*     */   {
/* 511 */     return this.agentID;
/*     */   }
/*     */ 
/*     */   TSAgentKey getAgentKey()
/*     */   {
/* 516 */     return this.agentKey;
/*     */   }
/*     */ 
/*     */   TSAgent(TSProviderImpl _provider, TSAgentKey _agentKey, String _passwd)
/*     */   {
/* 523 */     this.constructed = false;
/* 524 */     this.provider = _provider;
/* 525 */     this.passwd = _passwd;
/* 526 */     this.agentKey = _agentKey;
/* 527 */     this.lucentWorkMode = -1;
/* 528 */     this.state = 0;
/* 529 */     this.workMode = 0;
/* 530 */     this.reasonCode = 0;
/* 531 */     this.pendingState = 0;
/* 532 */     this.pendingReasonCode = 0;
/*     */ 
/* 534 */     log.info("constructing TSAgent with agentKey=" + this.agentKey + " for " + this.provider);
/*     */   }
/*     */ 
/*     */   synchronized void finishConstruction()
/*     */   {
/* 539 */     this.agentDevice = this.provider.createDevice(this.agentKey.agentDeviceID);
/* 540 */     if (this.agentKey.acdDeviceID != null)
/*     */     {
/* 542 */       this.acdDevice = this.provider.createDevice(this.agentKey.acdDeviceID);
/* 543 */       this.acdDevice.addToACDVector(this);
/*     */     }
/* 545 */     if (this.agentKey.agentID != null)
/* 546 */       this.agentID = this.agentKey.agentID;
/* 547 */     this.agentDevice.addToAgentTermVector(this);
/* 548 */     this.skillsVector = new Vector();
/* 549 */     this.constructed = true;
/* 550 */     super.notifyAll();
/*     */   }
/*     */ 
/*     */   synchronized void waitForConstruction()
/*     */   {
/* 555 */     if (this.constructed)
/*     */       return;
/*     */     try
/*     */     {
/* 559 */       super.wait(TSProviderImpl.DEFAULT_TIMEOUT);
/*     */     } catch (InterruptedException e) {
/*     */     }
/* 562 */     if (this.constructed)
/*     */       return;
/* 564 */     throw new TsapiPlatformException(4, 0, "could not finish agent construction");
/*     */   }
/*     */ 
/*     */   TSDevice getACDDeviceID()
/*     */   {
/* 570 */     return this.acdDevice;
/*     */   }
/*     */ 
/*     */   void addToSkillsVector(String acdDeviceID)
/*     */   {
/* 575 */     TSDevice skillDevice = this.provider.createDevice(acdDeviceID);
/* 576 */     if (this.skillsVector.contains(skillDevice))
/*     */       return;
/* 578 */     sendAgentSnapshot(skillDevice);
/* 579 */     this.skillsVector.addElement(this.provider.createDevice(acdDeviceID));
/* 580 */     skillDevice.addToACDVector(this);
/*     */   }
/*     */ 
/*     */   Vector<TSDevice> getSkillsVector()
/*     */   {
/* 586 */     return this.skillsVector;
/*     */   }
/*     */ 
/*     */   void sendAgentSnapshot(TSDevice skillDevice)
/*     */   {
/* 591 */     Vector localEventList = new Vector();
/* 592 */     getEvent(this.state, localEventList);
/*     */ 
/* 594 */     for (int i = 0; i < localEventList.size(); ++i)
/*     */     {
/* 596 */       TSEvent ev = (TSEvent)localEventList.elementAt(i);
/* 597 */       ev.setSkillDevice(skillDevice);
/*     */     }
/* 599 */     Vector observers = skillDevice.getAddressObservers();
/*     */ 
/* 601 */     for (int j = 0; j < observers.size(); ++j)
/*     */     {
/* 603 */       TsapiAddressMonitor callback = (TsapiAddressMonitor)observers.elementAt(j);
/* 604 */       callback.deliverEvents(localEventList, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   synchronized void delete()
/*     */   {
/* 612 */     log.info("Agent object=" + this + "being deleted" + " for " + this.provider);
/*     */ 
/* 614 */     if (this.agentKey == null)
/*     */       return;
/* 616 */     this.provider.deleteAgentFromHash(this.agentKey);
/* 617 */     this.provider.addAgentToSaveHash(this);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 625 */     return "TSAgent" + getMyCustomString() + "@" + Integer.toHexString(super.hashCode());
/*     */   }
/*     */ 
/*     */   private String getMyCustomString()
/*     */   {
/* 632 */     return "[" + this.agentKey + "]";
/*     */   }
/*     */ 
/*     */   public void referenced()
/*     */   {
/* 643 */     this.refCount += 1;
/*     */   }
/*     */ 
/*     */   public void unreferenced()
/*     */   {
/* 651 */     if (isReferenced())
/*     */     {
/* 653 */       this.refCount -= 1;
/*     */     }
/*     */ 
/* 658 */     if ((isReferenced()) || 
/* 660 */       (this.agentDevice == null))
/*     */       return;
/* 662 */     this.agentDevice.testDelete();
/*     */   }
/*     */ 
/*     */   boolean isReferenced()
/*     */   {
/* 672 */     return this.refCount > 0;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSAgent
 * JD-Core Version:    0.5.4
 */