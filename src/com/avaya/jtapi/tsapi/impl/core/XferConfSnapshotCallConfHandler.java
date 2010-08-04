/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ final class XferConfSnapshotCallConfHandler
/*     */   implements SnapshotCallExtraConfHandler
/*     */ {
/*  11 */   private static Logger log = Logger.getLogger(XferConfSnapshotCallConfHandler.class);
/*     */   TSEventHandler eventHandler;
/*     */   int jtapiCause;
/*     */   TSCall call;
/*     */   Object privateData;
/*     */   Vector<TSConnection> snapConnections;
/*     */ 
/*     */   XferConfSnapshotCallConfHandler(TSCall _call, int _jtapiCause, Object _privateData, Vector<TSConnection> _snapConnections)
/*     */   {
/*  22 */     this(null, _call, _jtapiCause, _privateData, _snapConnections);
/*     */   }
/*     */ 
/*     */   XferConfSnapshotCallConfHandler(TSEventHandler _eventHandler, TSCall _call, int _jtapiCause, Object _privateData, Vector<TSConnection> _snapConnections)
/*     */   {
/*  29 */     this.eventHandler = _eventHandler;
/*  30 */     this.call = _call;
/*  31 */     this.jtapiCause = _jtapiCause;
/*  32 */     this.privateData = _privateData;
/*     */ 
/*  34 */     this.snapConnections = _snapConnections;
/*     */   }
/*     */ 
/*     */   public Object handleConf(boolean rc, Vector<TSEvent> _eventList, Object _privateData)
/*     */   {
/*  46 */     if (this.call.getNeedRedoSnapshotCall())
/*     */     {
/*  48 */       this.call.setNeedRedoSnapshotCall(false);
/*  49 */       log.info("redo snapshot call");
/*  50 */       this.call.doSnapshot(((TSConnection)this.snapConnections.elementAt(0)).getConnID(), this, false);
/*  51 */       return null;
/*     */     }
/*     */ 
/*  54 */     this.call.setSnapshotCallConfPending(false);
/*     */ 
/*  56 */     this.call.setNeedSnapshot(false);
/*     */ 
/*  58 */     Vector eventList = new Vector();
/*     */ 
/*  60 */     if (rc)
/*     */     {
/*  62 */       for (int i = 0; i < this.snapConnections.size(); ++i)
/*     */       {
/*  64 */         TSConnection conn = (TSConnection)this.snapConnections.elementAt(i);
/*  65 */         conn.getSnapshot(eventList, false);
/*     */       }
/*     */ 
/*     */     }
/*     */     else {
/*  70 */       return this.privateData;
/*     */     }
/*     */ 
/*  81 */     if (this.eventHandler != null)
/*     */     {
/*  83 */       this.eventHandler.doCallMonitors(this.call, eventList, this.jtapiCause, this.privateData);
/*     */     }
/*  88 */     else if (eventList.size() > 0)
/*     */     {
/*  90 */       Vector observers = this.call.getObservers();
/*  91 */       for (int j = 0; j < observers.size(); ++j)
/*     */       {
/*  93 */         TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
/*     */ 
/*  99 */         callback.deliverEvents(eventList, this.jtapiCause, true);
/*     */       }
/*     */     }
/*     */ 
/* 103 */     return this.privateData;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.XferConfSnapshotCallConfHandler
 * JD-Core Version:    0.5.4
 */