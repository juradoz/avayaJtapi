/*     */ package com.avaya.jtapi.tsapi.impl;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*     */ import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSAgent;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSCall;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import java.util.Vector;
/*     */ import javax.telephony.Call;
/*     */ import javax.telephony.callcenter.ACDAddress;
/*     */ import javax.telephony.callcenter.ACDManagerAddress;
/*     */ import javax.telephony.callcenter.Agent;
/*     */ 
/*     */ class TsapiACDAddress extends TsapiAddress
/*     */   implements ACDAddress
/*     */ {
/*     */   public final Agent[] getLoggedOnAgents()
/*     */   {
/*  31 */     TsapiTrace.traceEntry("getLoggedOnAgents[]", this);
/*     */     try
/*     */     {
/*  34 */       Vector tsAgents = this.tsDevice.getTSAgentsForACDAddr();
/*     */       Object localObject1;
/*  36 */       if (tsAgents == null)
/*     */       {
/*  38 */         TsapiTrace.traceExit("getLoggedOnAgents[]", this);
/*  39 */         localObject1 = null;
/*     */ 
/*  61 */         this.privData = null;
/*     */       }
/*  42 */       synchronized (tsAgents)
/*     */       {
/*  44 */         if (tsAgents.size() == 0)
/*     */         {
/*  46 */           TsapiTrace.traceExit("getLoggedOnAgents[]", this);
/*     */ 
/*  61 */           this.privData = null; return null;
/*     */         }
/*  50 */         Agent[] tsapiAgent = new Agent[tsAgents.size()];
/*  51 */         for (int i = 0; i < tsAgents.size(); ++i)
/*     */         {
/*  53 */           tsapiAgent[i] = ((Agent)TsapiCreateObject.getTsapiObject((TSAgent)tsAgents.elementAt(i), false));
/*     */         }
/*  55 */         TsapiTrace.traceExit("getLoggedOnAgents[]", this);
/*     */ 
/*  61 */         this.privData = null; return tsapiAgent;
/*     */       } } finally { this.privData = null; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public final int getNumberQueued()
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/*  70 */     TsapiTrace.traceEntry("getNumberQueued[]", this);
/*     */     try
/*     */     {
/*  73 */       int numberQueued = this.tsDevice.getTSNumberQueued();
/*  74 */       TsapiTrace.traceExit("getNumberQueued[]", this);
/*  75 */       int i = numberQueued;
/*     */ 
/*  79 */       return i; } finally { this.privData = null; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public final Call getOldestCallQueued()
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/*  87 */     TsapiTrace.traceEntry("getOldestCallQueued[]", this);
/*     */     try
/*     */     {
/*  90 */       TSCall tsCall = this.tsDevice.getTSOldestCallQueued();
/*  91 */       if (tsCall != null)
/*     */       {
/*  93 */         TsapiTrace.traceExit("getOldestCallQueued[]", this);
/*     */         return (Call)TsapiCreateObject.getTsapiObject(tsCall, false);
/*     */       }
/*  98 */       TsapiTrace.traceExit("getOldestCallQueued[]", this);
/*  99 */       Call localCall = null;
/*     */ 
/* 105 */       return localCall; } finally { this.privData = null; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public final int getRelativeQueueLoad()
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/*     */     try
/*     */     {
/* 115 */       TsapiTrace.traceEntry("getRelativeQueueLoad[]", this);
/* 116 */       int relativeQueuedLoad = this.tsDevice.getTSRelativeQueueLoad();
/* 117 */       TsapiTrace.traceExit("getRelativeQueueLoad[]", this);
/* 118 */       int i = relativeQueuedLoad;
/*     */ 
/* 122 */       return i; } finally { this.privData = null; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public final int getQueueWaitTime()
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/* 130 */     TsapiTrace.traceEntry("getQueueWaitTime[]", this);
/*     */     try
/*     */     {
/* 133 */       int queueWaitTime = this.tsDevice.getTSQueueWaitTime();
/* 134 */       TsapiTrace.traceExit("getQueueWaitTime[]", this);
/* 135 */       int i = queueWaitTime;
/*     */ 
/* 139 */       return i; } finally { this.privData = null; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public final ACDManagerAddress getACDManagerAddress()
/*     */     throws TsapiMethodNotSupportedException
/*     */   {
/* 162 */     TsapiTrace.traceEntry("getACDManagerAddress[]", this);
/*     */     try
/*     */     {
/* 165 */       TSDevice[] acdManagerDevices = this.tsDevice.getTSACDManagerDevice();
/* 166 */       if (acdManagerDevices != null)
/*     */       {
/* 168 */         int number = acdManagerDevices.length;
/* 169 */         ACDManagerAddress[] acdManagerAddresses = new ACDManagerAddress[number];
/* 170 */         for (int i = 0; i < number; ++i)
/*     */         {
/* 172 */           acdManagerAddresses[i] = new TsapiACDManagerAddress(acdManagerDevices[i]);
/*     */         }
/* 174 */         ACDManagerAddress mgr = acdManagerAddresses[0];
/* 175 */         TsapiTrace.traceExit("getACDManagerAddress[]", this);
/* 176 */         ACDManagerAddress localACDManagerAddress1 = mgr;
/*     */         return localACDManagerAddress1;
/*     */       }
/* 180 */       TsapiTrace.traceExit("getACDManagerAddress[]", this);
/*     */ 
/* 186 */       return null; } finally { this.privData = null; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 193 */     if (obj instanceof TsapiACDAddress)
/*     */     {
/* 195 */       return this.tsDevice.equals(((TsapiACDAddress)obj).tsDevice);
/*     */     }
/*     */ 
/* 199 */     return false;
/*     */   }
/*     */ 
/*     */   TsapiACDAddress(TSProviderImpl tsProvider, String number)
/*     */     throws TsapiInvalidArgumentException
/*     */   {
/* 208 */     super(tsProvider, number);
/* 209 */     TsapiTrace.traceConstruction(this, TsapiACDAddress.class);
/*     */   }
/*     */ 
/*     */   TsapiACDAddress(TSDevice _tsDevice)
/*     */   {
/* 215 */     super(_tsDevice);
/* 216 */     TsapiTrace.traceConstruction(this, TsapiACDAddress.class);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 222 */     super.finalize();
/* 223 */     TsapiTrace.traceDestruction(this, TsapiACDAddress.class);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiACDAddress
 * JD-Core Version:    0.5.4
 */