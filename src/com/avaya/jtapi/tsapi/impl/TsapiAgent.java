/*     */ package com.avaya.jtapi.tsapi.impl;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ITsapiAgent;
/*     */ import com.avaya.jtapi.tsapi.LucentAgentStateInfoEx;
/*     */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*     */ import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
/*     */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSAgent;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import javax.telephony.Address;
/*     */ import javax.telephony.callcenter.ACDAddress;
/*     */ import javax.telephony.callcenter.AgentTerminal;
/*     */ 
/*     */ class TsapiAgent
/*     */   implements ITsapiAgent
/*     */ {
/*     */   TSAgent tsAgent;
/*     */ 
/*     */   public final void setState(int state)
/*     */     throws TsapiInvalidArgumentException, TsapiInvalidStateException
/*     */   {
/*  35 */     TsapiTrace.traceEntry("setState[int state]", this);
/*  36 */     setState(state, 0);
/*  37 */     TsapiTrace.traceExit("setState[int state]", this);
/*     */   }
/*     */ 
/*     */   public void setState(int state, int workMode)
/*     */     throws TsapiInvalidArgumentException, TsapiInvalidStateException
/*     */   {
/*  51 */     TsapiTrace.traceEntry("setState[int state, int workMode]", this);
/*  52 */     setState(state, workMode, 0);
/*  53 */     TsapiTrace.traceExit("setState[int state, int workMode]", this);
/*     */   }
/*     */ 
/*     */   public void setState(int state, int workMode, int reasonCode)
/*     */     throws TsapiInvalidArgumentException, TsapiInvalidStateException
/*     */   {
/*  69 */     TsapiTrace.traceEntry("setState[int state, int workMode, int reasonCode]", this);
/*  70 */     setState(state, workMode, reasonCode, false);
/*  71 */     TsapiTrace.traceExit("setState[int state, int workMode, int reasonCode]", this);
/*     */   }
/*     */ 
/*     */   public boolean setState(int state, int workMode, int reasonCode, boolean enablePending)
/*     */     throws TsapiInvalidArgumentException, TsapiInvalidStateException
/*     */   {
/*  88 */     TsapiTrace.traceEntry("setState[int state, int workMode, int reasonCode, boolean enablePending]", this);
/*  89 */     if ((state != 4) && (state != 3) && (state != 6) && (state != 5))
/*     */     {
/*  92 */       throw new TsapiInvalidArgumentException(3, 0, "state '" + state + "' not valid. Valid states are Agent.READY, Agent.NOT_READY" + ", Agent.WORK_READY and Agent.WORK_NOT_READY");
/*     */     }
/*     */ 
/*  97 */     if ((workMode != 0) && (workMode != 1) && (workMode != 2))
/*     */     {
/* 100 */       throw new TsapiInvalidArgumentException(3, 0, "workMode '" + workMode + "' not valid. Valid work modes are LucentAgent.MODE_NONE, LucentAgent.MODE_AUTO_IN and " + "LucentAgent.MODE_MANUAL_IN");
/*     */     }
/*     */ 
/* 106 */     if (this.tsAgent.getTSProviderImpl().isLucentV7())
/*     */     {
/* 109 */       if ((reasonCode < 0) || (reasonCode > 99))
/*     */       {
/* 111 */         throw new TsapiInvalidArgumentException(3, 0, "reasonCode not valid - must be in range 0-99 [" + reasonCode + "]");
/*     */       }
/*     */ 
/*     */     }
/* 118 */     else if ((reasonCode < 0) || (reasonCode > 9))
/*     */     {
/* 120 */       throw new TsapiInvalidArgumentException(3, 0, "reasonCode not valid - must be in range 0-9 [" + reasonCode + "]");
/*     */     }
/*     */ 
/* 124 */     boolean done = this.tsAgent.setState(state, workMode, reasonCode, enablePending);
/* 125 */     TsapiTrace.traceExit("setState[int state, int workMode, int reasonCode, boolean enablePending]", this);
/* 126 */     return done;
/*     */   }
/*     */ 
/*     */   public final int getState()
/*     */   {
/* 135 */     TsapiTrace.traceEntry("getState[]", this);
/* 136 */     int state = this.tsAgent.getState();
/* 137 */     TsapiTrace.traceExit("getState[]", this);
/* 138 */     return state;
/*     */   }
/*     */ 
/*     */   public final LucentAgentStateInfoEx getStateInfo()
/*     */   {
/* 146 */     TsapiTrace.traceEntry("getStateInfo[]", this);
/* 147 */     LucentAgentStateInfoEx state = this.tsAgent.getStateInfo();
/* 148 */     TsapiTrace.traceExit("getStateInfo[]", this);
/* 149 */     return state;
/*     */   }
/*     */ 
/*     */   public final String getAgentID()
/*     */   {
/* 157 */     TsapiTrace.traceEntry("getAgentID[]", this);
/* 158 */     String id = this.tsAgent.getAgentID();
/* 159 */     TsapiTrace.traceExit("getAgentID[]", this);
/* 160 */     return id;
/*     */   }
/*     */ 
/*     */   public final ACDAddress getACDAddress()
/*     */   {
/* 168 */     TsapiTrace.traceEntry("getACDAddress[]", this);
/* 169 */     TSDevice tsDevice = this.tsAgent.getTSACDDevice();
/* 170 */     if (tsDevice != null)
/*     */     {
/* 172 */       ACDAddress addr = (ACDAddress)TsapiCreateObject.getTsapiObject(tsDevice, true);
/* 173 */       TsapiTrace.traceExit("getACDAddress[]", this);
/* 174 */       return addr;
/*     */     }
/*     */ 
/* 178 */     TsapiTrace.traceExit("getACDAddress[]", this);
/* 179 */     return null;
/*     */   }
/*     */ 
/*     */   public final Address getAgentAddress()
/*     */   {
/* 189 */     TsapiTrace.traceEntry("getAgentAddress[]", this);
/* 190 */     TSDevice tsDevice = this.tsAgent.getTSAgentDevice();
/* 191 */     if (tsDevice != null)
/*     */     {
/* 193 */       Address addr = (Address)TsapiCreateObject.getTsapiObject(tsDevice, true);
/* 194 */       TsapiTrace.traceExit("getAgentAddress[]", this);
/* 195 */       return addr;
/*     */     }
/*     */ 
/* 199 */     throw new TsapiPlatformException(4, 0, "could not locate address");
/*     */   }
/*     */ 
/*     */   public final AgentTerminal getAgentTerminal()
/*     */   {
/* 211 */     TsapiTrace.traceEntry("getAgentTerminal[]", this);
/* 212 */     TSDevice tsDevice = this.tsAgent.getTSAgentDevice();
/* 213 */     if (tsDevice != null)
/*     */     {
/* 215 */       AgentTerminal term = (AgentTerminal)TsapiCreateObject.getTsapiObject(tsDevice, false);
/* 216 */       TsapiTrace.traceExit("getAgentTerminal[]", this);
/* 217 */       return term;
/*     */     }
/*     */ 
/* 221 */     throw new TsapiPlatformException(4, 0, "could not locate terminal");
/*     */   }
/*     */ 
/*     */   final TSAgent getTSAgent()
/*     */   {
/* 228 */     TsapiTrace.traceEntry("getTSAgent[]", this);
/* 229 */     TsapiTrace.traceExit("getTSAgent[]", this);
/* 230 */     return this.tsAgent;
/*     */   }
/*     */ 
/*     */   public final int hashCode()
/*     */   {
/* 235 */     return this.tsAgent.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 241 */     if (obj instanceof TsapiAgent)
/*     */     {
/* 243 */       return this.tsAgent.equals(((TsapiAgent)obj).tsAgent);
/*     */     }
/*     */ 
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */   TsapiAgent(TSAgent _tsAgent)
/*     */   {
/* 255 */     this.tsAgent = _tsAgent;
/* 256 */     if (this.tsAgent != null)
/*     */     {
/* 258 */       this.tsAgent.referenced();
/*     */     }
/* 260 */     TsapiTrace.traceConstruction(this, TsapiAgent.class);
/*     */   }
/*     */ 
/*     */   protected void finalize() throws Throwable
/*     */   {
/*     */     try
/*     */     {
/* 267 */       if (this.tsAgent != null)
/*     */       {
/* 269 */         this.tsAgent.unreferenced();
/* 270 */         this.tsAgent = null;
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 275 */       super.finalize();
/*     */     }
/* 277 */     TsapiTrace.traceDestruction(this, TsapiAgent.class);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiAgent
 * JD-Core Version:    0.5.4
 */