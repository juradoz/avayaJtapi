/*     */ package com.avaya.jtapi.tsapi.impl.beans;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ConnectionID;
/*     */ import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
/*     */ 
/*     */ public final class V7DeviceHistoryEntryImpl
/*     */   implements V7DeviceHistoryEntry
/*     */ {
/*     */   private String oldDeviceID;
/*     */   private short eventCause;
/*     */   private ConnectionID oldConnectionID;
/*     */ 
/*     */   public V7DeviceHistoryEntryImpl(String _dev, short _ec, ConnectionID _cid)
/*     */   {
/*  38 */     this.oldDeviceID = _dev;
/*  39 */     this.eventCause = _ec;
/*  40 */     this.oldConnectionID = _cid;
/*     */   }
/*     */ 
/*     */   public String getOldDeviceID()
/*     */   {
/*  46 */     return this.oldDeviceID;
/*     */   }
/*     */ 
/*     */   public short getEventCause()
/*     */   {
/*  65 */     return this.eventCause;
/*     */   }
/*     */ 
/*     */   public ConnectionID getOldConnectionID()
/*     */   {
/*  79 */     return this.oldConnectionID;
/*     */   }
/*     */ 
/*     */   public void setEventCause(short eventCause)
/*     */   {
/*  86 */     this.eventCause = eventCause;
/*     */   }
/*     */ 
/*     */   public void setOldConnectionID(ConnectionID oldConnectionID)
/*     */   {
/*  93 */     this.oldConnectionID = oldConnectionID;
/*     */   }
/*     */ 
/*     */   public void setOldDeviceID(String oldDeviceID)
/*     */   {
/* 100 */     this.oldDeviceID = oldDeviceID;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.beans.V7DeviceHistoryEntryImpl
 * JD-Core Version:    0.5.4
 */