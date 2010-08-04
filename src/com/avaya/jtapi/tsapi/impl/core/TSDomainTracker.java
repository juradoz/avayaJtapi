/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ class TSDomainTracker
/*     */   implements IDomainTracker
/*     */ {
/*  31 */   private static Logger log = Logger.getLogger(TSDomainTracker.class);
/*     */   private IDomainContainer m_container;
/*  37 */   private final Map<Integer, IDomainDevice> m_callsToDomainDevices = new HashMap();
/*     */ 
/*     */   TSDomainTracker(IDomainContainer container)
/*     */   {
/*  41 */     this.m_container = container;
/*     */   }
/*     */ 
/*     */   public synchronized IDomainDevice addCallToDomain(IDomainDevice d, IDomainCall c) {
/*  45 */     Integer cid = new Integer(c.getDomainCallID());
/*     */ 
/*  47 */     IDomainDevice prior_domain = null;
/*     */ 
/*  49 */     if (this.m_callsToDomainDevices.containsKey(cid))
/*     */     {
/*  52 */       IDomainDevice oldd = (IDomainDevice)this.m_callsToDomainDevices.get(cid);
/*  53 */       if (oldd == d)
/*     */       {
/*  56 */         internallyLog("addCallToDomain: " + c.getDomainCallID() + " to domain " + d.getDomainName() + " - was already in that domain - done");
/*  57 */         prior_domain = d;
/*     */       }
/*     */       else
/*     */       {
/*  63 */         this.m_callsToDomainDevices.remove(cid);
/*  64 */         c.notifyCallRemoved(oldd);
/*  65 */         this.m_callsToDomainDevices.put(cid, d);
/*  66 */         c.notifyCallAdded(d);
/*  67 */         internallyLog("addCallToDomain: " + c.getDomainCallID() + " to domain " + d.getDomainName() + " - overrode prior other domain " + oldd.getDomainName());
/*  68 */         prior_domain = oldd;
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*  75 */       this.m_callsToDomainDevices.put(cid, d);
/*  76 */       c.notifyCallAdded(d);
/*  77 */       internallyLog("addCallToDomain: " + c.getDomainCallID() + " to domain " + d.getDomainName() + " and was under none before");
/*     */     }
/*     */ 
/*  80 */     return prior_domain;
/*     */   }
/*     */ 
/*     */   public synchronized void changeCallIDInDomain(int old_callid, int new_callid) {
/*  84 */     Integer oldcid = new Integer(old_callid);
/*  85 */     Integer newcid = new Integer(new_callid);
/*     */ 
/*  87 */     if (this.m_callsToDomainDevices.containsKey(oldcid))
/*     */     {
/*  89 */       IDomainDevice domain = (IDomainDevice)this.m_callsToDomainDevices.remove(oldcid);
/*  90 */       this.m_callsToDomainDevices.put(newcid, domain);
/*  91 */       internallyLog("changeCallIDInDomain: old cid " + old_callid + " was under domain " + domain + " - now recorded under new cid " + new_callid);
/*     */     }
/*     */     else
/*     */     {
/*  95 */       internallyLog("changeCallIDInDomain: old call was not in any domain, oldcid " + old_callid + " newcid " + new_callid + " - no action taken");
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized IDomainDevice getDomainCallIsIn(IDomainCall c) {
/* 100 */     Integer cid = new Integer(c.getDomainCallID());
/*     */ 
/* 102 */     if (this.m_callsToDomainDevices.containsKey(cid))
/*     */     {
/* 104 */       return (IDomainDevice)this.m_callsToDomainDevices.get(cid);
/*     */     }
/*     */ 
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */   public synchronized void removeCallFromDomain(IDomainCall c)
/*     */   {
/* 113 */     Integer cid = new Integer(c.getDomainCallID());
/*     */ 
/* 115 */     if (this.m_callsToDomainDevices.containsKey(cid))
/*     */     {
/* 117 */       IDomainDevice domain = (IDomainDevice)this.m_callsToDomainDevices.remove(cid);
/* 118 */       c.notifyCallRemoved(domain);
/* 119 */       internallyLog("removeCallFromDomain: cid " + cid + " was under domain " + domain);
/*     */     }
/*     */     else
/*     */     {
/* 123 */       internallyLog("removeCallFromDomain: cid " + cid + " was not in any domain - no action taken");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isCallInAnyDomain(IDomainCall c)
/*     */   {
/* 129 */     return getDomainCallIsIn(c) != null;
/*     */   }
/*     */ 
/*     */   private void internallyLog(String s)
/*     */   {
/* 135 */     this.m_container.logln("TSDomainTracker/" + this.m_container + ": " + s);
/*     */   }
/*     */ 
/*     */   public void dumpDomainData(String indent)
/*     */   {
/* 143 */     HashMap dup = null;
/*     */ 
/* 146 */     int found_count = 0;
/*     */ 
/* 148 */     synchronized (this)
/*     */     {
/* 151 */       dup = new HashMap(this.m_callsToDomainDevices);
/*     */     }
/*     */ 
/* 154 */     Set entries = dup.entrySet();
/* 155 */     Iterator current = entries.iterator();
/* 156 */     log.trace("DomainTracker begins:");
/* 157 */     while (current.hasNext())
/*     */     {
/* 159 */       Map.Entry an_entry = (Map.Entry)current.next();
/*     */ 
/* 161 */       IDomainDevice d = (IDomainDevice)an_entry.getValue();
/*     */ 
/* 163 */       int cid = ((Integer)an_entry.getKey()).intValue();
/* 164 */       IDomainCall call = this.m_container.getDomainCall(cid);
/*     */ 
/* 166 */       ++found_count;
/*     */ 
/* 170 */       log.trace(indent + found_count + ": Call(cid) " + call + "(" + cid + ") --> VDNDevice(" + d + ")");
/*     */     }
/*     */ 
/* 178 */     log.trace("DomainTracker ends(" + found_count + " entries)");
/*     */   }
/*     */ 
/*     */   public synchronized void removeAllCallsForDomain(IDomainDevice d)
/*     */   {
/* 183 */     int found_count = 0;
/*     */ 
/* 185 */     internallyLog("removeAllCallsForDomain: to clean calls for domain " + d);
/*     */ 
/* 189 */     if (this.m_callsToDomainDevices.containsValue(d))
/*     */     {
/* 197 */       Set entries = this.m_callsToDomainDevices.entrySet();
/* 198 */       Iterator current = entries.iterator();
/* 199 */       while (current.hasNext())
/*     */       {
/* 201 */         Map.Entry an_entry = (Map.Entry)current.next();
/*     */ 
/* 204 */         if (an_entry.getValue() == d)
/*     */         {
/* 207 */           current.remove();
/*     */ 
/* 210 */           int cid = ((Integer)an_entry.getKey()).intValue();
/* 211 */           IDomainCall call = this.m_container.getDomainCall(cid);
/* 212 */           call.notifyCallRemoved(d);
/*     */ 
/* 214 */           ++found_count;
/* 215 */           internallyLog("removeAllCallsForDomain: cid " + cid + " was under domain " + d);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 221 */     internallyLog("removeAllCallsForDomain: done, cleaned " + found_count + " call(s) for domain " + d);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSDomainTracker
 * JD-Core Version:    0.5.4
 */