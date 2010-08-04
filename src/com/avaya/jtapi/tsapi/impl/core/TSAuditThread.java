/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ final class TSAuditThread extends Thread
/*     */ {
/*  19 */   private static Logger log = Logger.getLogger(TSAuditThread.class);
/*     */   TSProviderImpl provider;
/*     */   boolean keepRunning;
/*     */   boolean isSleeping;
/*     */   Hashtable<Integer, SavedCall> saveCallHash;
/*     */   Hashtable<CSTAConnectionID, SavedConn> saveConnHash;
/*     */   Hashtable<TSAgentKey, SavedAgent> saveAgentHash;
/*  92 */   static int SLEEP_TIME = 10000;
/*     */   static final long DIFF_TIME = 5000L;
/*     */ 
/*     */   void dump(String indent)
/*     */   {
/*  24 */     log.trace(indent + "***** AUDIT DUMP *****");
/*  25 */     log.trace(indent + "TSAuditThread: " + this);
/*  26 */     log.trace(indent + "TSAuditThread calls: ");
/*  27 */     Enumeration callEnum = this.saveCallHash.elements();
/*     */ 
/*  29 */     while (callEnum.hasMoreElements())
/*     */     {
/*     */       SavedCall call;
/*     */       try {
/*  33 */         call = (SavedCall)callEnum.nextElement();
/*     */       }
/*     */       catch (NoSuchElementException e)
/*     */       {
/*  37 */         log.error(e.getMessage(), e);
continue;
/*  38 */       }
/*     */ 
/*  40 */       call.call.dump(indent + " ");
/*     */     }
/*  42 */     log.trace(indent + "TSAuditThread conns: ");
/*  43 */     Enumeration connEnum = this.saveConnHash.elements();
/*     */ 
/*  45 */     while (connEnum.hasMoreElements())
/*     */     {
/*     */       SavedConn conn;
/*     */       try {
/*  49 */         conn = (SavedConn)connEnum.nextElement();
/*     */       }
/*     */       catch (NoSuchElementException e)
/*     */       {
/*  53 */         log.error(e.getMessage(), e);
continue;
/*  54 */       }
/*     */ 
/*  56 */       conn.conn.dump(indent + " ");
/*     */     }
/*  58 */     log.trace(indent + "TSAuditThread agents: ");
/*  59 */     Enumeration agentEnum = this.saveAgentHash.elements();
/*     */ 
/*  61 */     while (agentEnum.hasMoreElements())
/*     */     {
/*     */       SavedAgent agent;
/*     */       try {
/*  65 */         agent = (SavedAgent)agentEnum.nextElement();
/*     */       }
/*     */       catch (NoSuchElementException e)
/*     */       {
/*  69 */         log.error(e.getMessage(), e);
continue;
/*  70 */       }
/*     */ 
/*  72 */       agent.agent.dump(indent + " ");
/*     */     }
/*  74 */     log.trace(indent + "TSAuditThread Referenced/NotReferenced dump for TSDevice");
/*  75 */     if (TSDevice.g_RefCnt != TSDevice.g_CreationCnt)
/*     */     {
/*  77 */       log.trace(indent + "Reference dump: TSDevice Created Count " + TSDevice.g_CreationCnt);
/*  78 */       log.trace(indent + "Reference dump: TSDevice Referenced Count " + TSDevice.g_RefCnt);
/*  79 */       log.trace(indent + "Reference dump: TSDevice Registered Count " + this.provider.xrefHash.size());
/*     */     }
/*  81 */     log.trace(indent + "***** AUDIT DUMP END *****");
/*     */   }
/*     */ 
/*     */   TSAuditThread(TSProviderImpl _provider)
/*     */   {
/* 100 */     super("AuditThread");
/* 101 */     this.provider = _provider;
/* 102 */     this.keepRunning = true;
/* 103 */     this.saveConnHash = new Hashtable(20);
/* 104 */     this.saveCallHash = new Hashtable(10);
/* 105 */     this.saveAgentHash = new Hashtable(10);
/* 106 */     this.isSleeping = false;
/*     */   }
/*     */ 
/*     */   void stopRunning()
/*     */   {
/* 117 */     this.keepRunning = false;
/* 118 */     if (!this.isSleeping)
/*     */       return;
/* 120 */     interrupt();
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 128 */     int secondsSinceLastCallCleanup = 0;
/* 129 */     int secondsSinceLastProviderDump = 0;
/*     */     try
/*     */     {
/* 133 */       while (this.keepRunning)
/*     */       {
/*     */         try
/*     */         {
/* 137 */           this.isSleeping = true;
/* 138 */           Thread.sleep(SLEEP_TIME);
/*     */         } catch (InterruptedException e) {
/*     */         }
/* 141 */         this.isSleeping = false;
/*     */ 
/* 143 */         if (!this.keepRunning)
/*     */         {
/*     */           break;
/*     */         }
/*     */ 
/* 148 */         long curTime = System.currentTimeMillis();
/*     */ 
/* 150 */         synchronized (this.saveCallHash)
/*     */         {
/* 153 */           Hashtable keepHash = new Hashtable(20);
/* 154 */           Enumeration my_enum = this.saveCallHash.elements();
/* 155 */           while (my_enum.hasMoreElements())
/*     */           {
/*     */             SavedCall sCall;
/*     */             try {
/* 159 */               sCall = (SavedCall)my_enum.nextElement();
/*     */ 
/* 165 */               if ((!sCall.call.needsSnapshot()) && (curTime - sCall.saveTime > 3600000L))
/*     */               {
/* 168 */                 log.info("Setting NeedSnapshot for LONG call: " + sCall.call);
/* 169 */                 sCall.call.setNeedSnapshot(true);
/*     */               }
/*     */             }
/*     */             catch (NoSuchElementException e)
/*     */             {
/* 174 */               log.error(e.getMessage(), e);
continue;
/* 175 */             }
/*     */ 
/* 177 */             if (curTime - sCall.saveTime < 5000L)
/*     */             {
/* 179 */               Object oldObj = keepHash.put(new Integer(sCall.call.getCallID()), sCall);
/* 180 */               if (oldObj != null)
/* 181 */                 log.info("NOTICE: keepHash.put() replaced " + oldObj + " for " + this.provider);
/*     */             }
/* 183 */             log.info("AUDIT: removing call " + sCall.call + " for " + this.provider);
/*     */           }
/*     */ 
/* 186 */           this.saveCallHash = keepHash;
/*     */         }
/*     */ 
/* 189 */         synchronized (this.saveConnHash)
/*     */         {
/* 192 */           Hashtable keepHash = new Hashtable(20);
/* 193 */           Enumeration my_enum = this.saveConnHash.elements();
/* 194 */           while (my_enum.hasMoreElements())
/*     */           {
/*     */             SavedConn sConn;
/*     */             try {
/* 198 */               sConn = (SavedConn)my_enum.nextElement();
/*     */             }
/*     */             catch (NoSuchElementException e)
/*     */             {
/* 202 */               log.error(e.getMessage(), e);
continue;
/* 203 */             }
/*     */ 
/* 205 */             if (curTime - sConn.saveTime < 5000L)
/*     */             {
/*     */               try
/*     */               {
/* 209 */                 Object oldObj = keepHash.put(sConn.conn.getConnID(), sConn);
/* 210 */                 if (oldObj != null)
/* 211 */                   log.info("NOTICE: keepHash.put() replaced " + oldObj + " for " + this.provider);
/*     */               }
/*     */               catch (TsapiPlatformException e)
/*     */               {
/* 215 */                 log.error("Ignoring exception: " + e);
/* 216 */                 log.info("AUDIT: removing conn " + sConn.conn + " for " + this.provider);
/*     */               }
/*     */             }
/* 219 */             log.info("AUDIT: removing conn " + sConn.conn + " for " + this.provider);
/*     */           }
/*     */ 
/* 222 */           this.saveConnHash = keepHash;
/*     */         }
/*     */ 
/* 226 */         synchronized (this.saveAgentHash)
/*     */         {
/* 229 */           Hashtable keepHash = new Hashtable(20);
/* 230 */           Enumeration my_enum = this.saveAgentHash.elements();
/* 231 */           while (my_enum.hasMoreElements())
/*     */           {
/*     */             SavedAgent sAgent;
/*     */             try {
/* 235 */               sAgent = (SavedAgent)my_enum.nextElement();
/*     */             }
/*     */             catch (NoSuchElementException e)
/*     */             {
/* 239 */               log.error(e.getMessage(), e);
continue;
/* 240 */             }
/*     */ 
/* 242 */             if (curTime - sAgent.saveTime < 5000L)
/*     */             {
/* 244 */               Object oldObj = keepHash.put(sAgent.agent.getAgentKey(), sAgent);
/* 245 */               if (oldObj != null)
/* 246 */                 log.info("NOTICE: keepHash.put() replaced " + oldObj + " for " + this.provider);
/*     */             }
/* 248 */             log.info("AUDIT: removing agent " + sAgent.agent + " for " + this.provider);
/*     */           }
/*     */ 
/* 251 */           this.saveAgentHash = keepHash;
/*     */         }
/*     */ 
/* 255 */         secondsSinceLastCallCleanup += SLEEP_TIME / 1000;
/* 256 */         if (secondsSinceLastCallCleanup >= Tsapi.getCallCleanupRate())
/*     */         {
/* 259 */           this.provider.callCleanup();
/* 260 */           secondsSinceLastCallCleanup = 0;
/*     */         }
/*     */ 
/* 269 */         secondsSinceLastProviderDump += SLEEP_TIME / 1000;
/* 270 */         if (secondsSinceLastProviderDump / 60 < Tsapi.getAuditDumpInterval())
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/* 275 */         if ((log.isTraceEnabled()) && (Tsapi.isEnableAuditDump()))
/*     */         {
/* 277 */           log.trace("STARTING DUMPS, date = " + new Date() + " for " + this.provider);
/* 278 */           this.provider.dump("");
/*     */         }
/* 280 */         secondsSinceLastProviderDump = 0;
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 286 */       log.info("AUDIT Thread Exception - on provider " + this.provider);
/* 287 */       log.error(e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   void dumpCall(int callID)
/*     */   {
/* 293 */     SavedCall sCall = (SavedCall)this.saveCallHash.remove(new Integer(callID));
/* 294 */     if (sCall != null) {
/* 295 */       log.info("AUDIT (dumpCall): removing call " + sCall.call + " for " + this.provider);
/*     */     }
/* 297 */     Hashtable keepHash = new Hashtable(20);
/*     */ 
/* 299 */     synchronized (this.saveConnHash)
/*     */     {
/* 301 */       Enumeration my_enum = this.saveConnHash.elements();
/*     */ 
/* 303 */       while (my_enum.hasMoreElements())
/*     */       {
/*     */         SavedConn sConn;
/*     */         try {
/* 307 */           sConn = (SavedConn)my_enum.nextElement();
/*     */         }
/*     */         catch (NoSuchElementException e)
/*     */         {
/* 311 */           log.error(e.getMessage(), e);
continue;
/* 312 */         }
/*     */         try
/*     */         {
/* 316 */           if (sConn.conn.getConnID().getCallID() != callID)
/*     */           {
/* 318 */             Object oldObj = keepHash.put(sConn.conn.getConnID(), sConn);
/* 319 */             if (oldObj != null)
/* 320 */               log.info("NOTICE: keepHash.put() replaced " + oldObj + " for " + this.provider);
/*     */           } else {
/* 322 */             log.info("AUDIT (dumpCall): removing conn " + sConn.conn + " for " + this.provider);
/*     */           }
/*     */         }
/*     */         catch (TsapiPlatformException e)
/*     */         {
/* 327 */           log.error("Ignoring exception: " + e);
/* 328 */           log.info("AUDIT (dumpCall): removing conn " + sConn.conn + " for " + this.provider);
/*     */         }
/*     */       }
/* 331 */       this.saveConnHash = keepHash;
/*     */     }
/*     */   }
/*     */ 
/*     */   void putCall(TSCall call)
/*     */   {
/* 337 */     Object oldObj = this.saveCallHash.put(new Integer(call.getCallID()), new SavedCall(call));
/* 338 */     if (oldObj != null)
/* 339 */       log.info("NOTICE: saveCallHash.put() replaced " + oldObj + " for " + this.provider);
/*     */   }
/*     */ 
/*     */   TSCall getCall(int callID) {
/* 343 */     SavedCall sCall = (SavedCall)this.saveCallHash.get(new Integer(callID));
/* 344 */     if (sCall != null)
/*     */     {
/* 346 */       log.info("Found call in audit hash: " + sCall.call + " for " + this.provider);
/* 347 */       return sCall.call;
/*     */     }
/*     */ 
/* 350 */     return null;
/*     */   }
/*     */ 
/*     */   void dumpConn(CSTAConnectionID connID) {
/* 354 */     SavedConn sConn = (SavedConn)this.saveConnHash.remove(connID);
/* 355 */     if (sConn != null)
/* 356 */       log.info("AUDIT (dumpConn): removing conn " + sConn.conn + " for " + this.provider);
/*     */   }
/*     */ 
/*     */   void putConn(TSConnection conn)
/*     */   {
/*     */     try {
/* 362 */       CSTAConnectionID connID = conn.getConnID();
/* 363 */       if (connID != null)
/*     */       {
/* 365 */         Object oldObj = this.saveConnHash.put(connID, new SavedConn(conn));
/* 366 */         if (oldObj != null)
/* 367 */           log.info("NOTICE: saveConnHash.put() replaced " + oldObj + " for " + this.provider);
/*     */       }
/*     */     }
/*     */     catch (TsapiPlatformException e)
/*     */     {
/* 372 */       log.error("Ignoring exception: " + e);
/* 373 */       log.info("AUDIT: removing conn " + conn + " for " + this.provider);
/*     */     }
/*     */   }
/*     */ 
/*     */   TSConnection getConn(CSTAConnectionID connID) {
/* 378 */     SavedConn sConn = (SavedConn)this.saveConnHash.get(connID);
/* 379 */     if (sConn != null)
/*     */     {
/* 381 */       log.info("Found conn in audit hash: " + sConn.conn + " for " + this.provider);
/* 382 */       return sConn.conn;
/*     */     }
/*     */ 
/* 385 */     return null;
/*     */   }
/*     */ 
/*     */   void dumpAgent(TSAgentKey agentKey) {
/* 389 */     SavedAgent sAgent = (SavedAgent)this.saveAgentHash.remove(agentKey);
/* 390 */     if (sAgent != null)
/* 391 */       log.info("AUDIT (dumpAgent): removing agent " + sAgent.agent + " for " + this.provider);
/*     */   }
/*     */ 
/*     */   void putAgent(TSAgent agent) {
/* 395 */     TSAgentKey agentKey = agent.getAgentKey();
/* 396 */     if (agentKey == null)
/*     */       return;
/* 398 */     Object oldObj = this.saveAgentHash.put(agentKey, new SavedAgent(agent));
/* 399 */     if (oldObj != null)
/* 400 */       log.info("NOTICE: saveAgentHash.put() replaced " + oldObj + " for " + this.provider);
/*     */   }
/*     */ 
/*     */   TSAgent getAgent(TSAgentKey agentKey)
/*     */   {
/* 405 */     SavedAgent sAgent = (SavedAgent)this.saveAgentHash.get(agentKey);
/* 406 */     if (sAgent != null)
/*     */     {
/* 408 */       log.info("Found agent in audit hash: " + sAgent.agent + " for " + this.provider);
/* 409 */       return sAgent.agent;
/*     */     }
/*     */ 
/* 412 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSAuditThread
 * JD-Core Version:    0.5.4
 */