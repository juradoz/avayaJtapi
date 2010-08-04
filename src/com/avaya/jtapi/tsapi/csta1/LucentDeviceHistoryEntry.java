/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class LucentDeviceHistoryEntry extends LucentPrivateData
/*     */ {
/*     */   private String oldDeviceID;
/*     */   private short eventCause;
/*     */   private CSTAConnectionID oldConnectionID;
/*     */ 
/*     */   LucentDeviceHistoryEntry()
/*     */   {
/*     */   }
/*     */ 
/*     */   public LucentDeviceHistoryEntry(String _oldDeviceID, short _eventCause, CSTAConnectionID _oldConnectionID)
/*     */   {
/*  22 */     this.oldDeviceID = _oldDeviceID;
/*  23 */     this.eventCause = _eventCause;
/*  24 */     this.oldConnectionID = _oldConnectionID;
/*     */   }
/*     */ 
/*     */   static void encode(LucentDeviceHistoryEntry _this, OutputStream out) {
/*  28 */     if (_this == null)
/*     */     {
/*  30 */       _this = new LucentDeviceHistoryEntry();
/*     */     }
/*  32 */     _this.encode(out);
/*     */   }
/*     */   public void encodeMembers(OutputStream memberStream) {
/*  35 */     DeviceID.encode(this.oldDeviceID, memberStream);
/*  36 */     EventCause.encode(this.eventCause, memberStream);
/*  37 */     CSTAConnectionID.encode(this.oldConnectionID, memberStream);
/*     */   }
/*     */ 
/*     */   public static LucentDeviceHistoryEntry decode(InputStream in)
/*     */   {
/*  42 */     LucentDeviceHistoryEntry _this = new LucentDeviceHistoryEntry();
/*  43 */     _this.doDecode(in);
/*  44 */     if ((_this.oldDeviceID == null) && (_this.oldConnectionID == null))
/*     */     {
/*  47 */       return null;
/*     */     }
/*     */ 
/*  50 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  55 */     this.oldDeviceID = DeviceID.decode(memberStream);
/*  56 */     this.eventCause = EventCause.decode(memberStream);
/*  57 */     this.oldConnectionID = CSTAConnectionID.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public String getOldDeviceID()
/*     */   {
/*  63 */     return this.oldDeviceID;
/*     */   }
/*     */ 
/*     */   public short getEventCause()
/*     */   {
/*  72 */     return this.eventCause;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getOldConnectionID()
/*     */   {
/*  78 */     return this.oldConnectionID;
/*     */   }
/*     */ 
/*     */   public static Collection<String> print(LucentDeviceHistoryEntry _this, String name, String _indent)
/*     */   {
/*  83 */     Collection lines = new ArrayList();
/*  84 */     if (_this == null)
/*     */     {
/*  86 */       lines.add(_indent + name + " <null>");
/*  87 */       return lines;
/*     */     }
/*  89 */     if (name != null) {
/*  90 */       lines.add(_indent + name);
/*     */     }
/*  92 */     lines.add(_indent + "{");
/*     */ 
/*  94 */     String indent = _indent + "  ";
/*     */ 
/*  96 */     lines.addAll(DeviceID.print(_this.oldDeviceID, "oldDeviceID", indent));
/*  97 */     lines.addAll(EventCause.print(_this.eventCause, "eventCause", indent));
/*  98 */     lines.addAll(CSTAConnectionID.print(_this.oldConnectionID, "oldConnectionID", indent));
/*     */ 
/* 100 */     lines.add(_indent + "}");
/* 101 */     return lines;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentDeviceHistoryEntry
 * JD-Core Version:    0.5.4
 */