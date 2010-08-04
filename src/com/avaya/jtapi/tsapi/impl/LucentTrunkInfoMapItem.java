/*     */ package com.avaya.jtapi.tsapi.impl;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.TsapiTrunk;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTATrunkInfo;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSCall;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class LucentTrunkInfoMapItem
/*     */ {
/*     */   private TSConnection tsConnection;
/*     */   private TSTrunk tsTrunk;
/*     */ 
/*     */   private LucentTrunkInfoMapItem(CSTATrunkInfo info, TSProviderImpl provider)
/*     */   {
/*  46 */     String trunkName = TsapiTrunk.makeTrunkName(info.getTrunkGroup(), info.getTrunkMember());
/*  47 */     if (trunkName != null)
/*     */     {
/*  49 */       this.tsTrunk = provider.createTSTrunk(trunkName);
/*  50 */       if (this.tsTrunk != null)
/*     */       {
/*  53 */         this.tsTrunk.setGroupName(info.getTrunkGroup());
/*  54 */         this.tsTrunk.setMemberName(info.getTrunkMember());
/*     */       }
/*     */     }
/*  57 */     this.tsConnection = provider.getConnection(info.getConnection_asn());
/*  58 */     TsapiTrace.traceConstruction(this, LucentTrunkInfoMapItem.class);
/*     */   }
/*     */ 
/*     */   public static LucentTrunkInfoMapItem[] createLucentTrunkInfoMapItemArray(CSTATrunkInfo[] input_array, TSProviderImpl prov)
/*     */   {
/*  66 */     TsapiTrace.traceEntry("createLucentTrunkInfoMapItemArray[LucentTrunkInfo[] input_array, TSProviderImpl prov]", LucentTrunkInfoMapItem.class);
/*     */ 
/*  69 */     LucentTrunkInfoMapItem[] items = new LucentTrunkInfoMapItem[input_array.length];
/*  70 */     for (int i = 0; i < items.length; ++i)
/*     */     {
/*  72 */       items[i] = createLucentTrunkInfo(input_array[i], prov);
/*     */     }
/*  74 */     TsapiTrace.traceExit("createLucentTrunkInfoMapItemArray[LucentTrunkInfo[] input_array, TSProviderImpl prov]", LucentTrunkInfoMapItem.class);
/*  75 */     return items;
/*     */   }
/*     */ 
/*     */   public static LucentTrunkInfoMapItem createLucentTrunkInfo(CSTATrunkInfo info, TSProviderImpl prov)
/*     */   {
/*  81 */     TsapiTrace.traceEntry("createLucentTrunkInfo[LucentTrunkInfo info, TSProviderImpl prov]", LucentTrunkInfoMapItem.class);
/*  82 */     LucentTrunkInfoMapItem _this = new LucentTrunkInfoMapItem(info, prov);
/*  83 */     TsapiTrace.traceExit("createLucentTrunkInfo[LucentTrunkInfo info, TSProviderImpl prov]", LucentTrunkInfoMapItem.class);
/*  84 */     return _this;
/*     */   }
/*     */ 
/*     */   public void interLinkConnectionCallAndTrunk(Vector<TSEvent> eventList)
/*     */   {
/*  93 */     TsapiTrace.traceEntry("interLinkConnectionCallAndTrunk[Vector<TSEvent> eventList]", this);
/*  94 */     if ((this.tsTrunk != null) && (this.tsConnection != null))
/*     */     {
/*  97 */       this.tsConnection.setTrunk(this.tsTrunk);
/*     */ 
/*  99 */       this.tsTrunk.setTSConnection(this.tsConnection);
/*     */ 
/* 101 */       TSCall call = this.tsConnection.getTSCall();
/* 102 */       if (call != null)
/*     */       {
/* 104 */         call.addTrunk(this.tsTrunk, eventList);
/*     */       }
/*     */     }
/* 107 */     TsapiTrace.traceExit("interLinkConnectionCallAndTrunk[Vector<TSEvent> eventList]", this);
/*     */   }
/*     */ 
/*     */   public TSConnection getTSConnection()
/*     */   {
/* 113 */     TsapiTrace.traceEntry("getTSConnection[]", this);
/* 114 */     TsapiTrace.traceExit("getTSConnection[]", this);
/* 115 */     return this.tsConnection;
/*     */   }
/*     */ 
/*     */   public TSTrunk getTSTrunk()
/*     */   {
/* 121 */     TsapiTrace.traceEntry("getTSTrunk[]", this);
/* 122 */     TsapiTrace.traceExit("getTSTrunk[]", this);
/* 123 */     return this.tsTrunk;
/*     */   }
/*     */ 
/*     */   protected void finalize() throws Throwable
/*     */   {
/* 128 */     super.finalize();
/* 129 */     TsapiTrace.traceDestruction(this, LucentTrunkInfoMapItem.class);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentTrunkInfoMapItem
 * JD-Core Version:    0.5.4
 */