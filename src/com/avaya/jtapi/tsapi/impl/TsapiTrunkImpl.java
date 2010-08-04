/*     */ package com.avaya.jtapi.tsapi.impl;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*     */ import com.avaya.jtapi.tsapi.TsapiTrunk;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSCall;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import javax.telephony.Call;
/*     */ import javax.telephony.Connection;
/*     */ 
/*     */ public class TsapiTrunkImpl extends TsapiTrunk
/*     */ {
/*     */   TSTrunk tsTrunk;
/*     */   String name;
/*     */ 
/*     */   public String getName()
/*     */   {
/*  22 */     TsapiTrace.traceEntry("getName[]", this);
/*  23 */     TsapiTrace.traceExit("getName[]", this);
/*  24 */     return this.name;
/*     */   }
/*     */ 
/*     */   public int getState()
/*     */   {
/*  30 */     TsapiTrace.traceEntry("getState[]", this);
/*  31 */     TsapiTrace.traceExit("getState[]", this);
/*  32 */     return this.tsTrunk.getState();
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/*  37 */     TsapiTrace.traceEntry("getType[]", this);
/*  38 */     int type = this.tsTrunk.getType();
/*  39 */     TsapiTrace.traceExit("getType[]", this);
/*  40 */     return type;
/*     */   }
/*     */ 
/*     */   public Call getCall()
/*     */   {
/*  45 */     TsapiTrace.traceEntry("getCall[]", this);
/*  46 */     TSCall tsCall = this.tsTrunk.getTSCall();
/*  47 */     Call call = null;
/*  48 */     if (tsCall != null)
/*     */     {
/*  50 */       call = (Call)TsapiCreateObject.getTsapiObject(tsCall, false);
/*     */     }
/*  52 */     TsapiTrace.traceExit("getCall[]", this);
/*  53 */     return call;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  58 */     return this.tsTrunk.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  64 */     if (obj instanceof TsapiTrunkImpl)
/*     */     {
/*  66 */       return this.tsTrunk.equals(((TsapiTrunkImpl)obj).tsTrunk);
/*     */     }
/*     */ 
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   TsapiTrunkImpl(TSProviderImpl tsProvider, String trkName)
/*     */   {
/*  88 */     this.tsTrunk = tsProvider.createTSTrunk(trkName);
/*  89 */     if (this.tsTrunk == null)
/*     */     {
/*  91 */       throw new TsapiPlatformException(4, 0, "could not create trunk");
/*     */     }
/*  93 */     this.name = trkName;
/*  94 */     TsapiTrace.traceConstruction(this, TsapiTrunkImpl.class);
/*     */   }
/*     */ 
/*     */   TsapiTrunkImpl(TSTrunk _tsTrunk)
/*     */   {
/* 100 */     this.tsTrunk = _tsTrunk;
/*     */ 
/* 102 */     this.name = this.tsTrunk.getName();
/* 103 */     TsapiTrace.traceConstruction(this, TsapiTrunkImpl.class);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 109 */     super.finalize();
/* 110 */     TsapiTrace.traceDestruction(this, TsapiTrunkImpl.class);
/*     */   }
/*     */ 
/*     */   public TSTrunk getTSTrunk()
/*     */   {
/* 118 */     TsapiTrace.traceEntry("getTSTrunk[]", this);
/* 119 */     TsapiTrace.traceExit("getTSTrunk[]", this);
/* 120 */     return this.tsTrunk;
/*     */   }
/*     */ 
/*     */   public Connection getConnection()
/*     */   {
/* 132 */     TsapiTrace.traceEntry("getConnection[]", this);
/* 133 */     TSConnection tsconn = this.tsTrunk.getTSConnection();
/* 134 */     Connection conn = null;
/* 135 */     if (tsconn != null)
/*     */     {
/* 137 */       conn = (Connection)TsapiCreateObject.getTsapiObject(tsconn, true);
/*     */     }
/* 139 */     TsapiTrace.traceEntry("getConnection[]", this);
/* 140 */     return conn;
/*     */   }
/*     */ 
/*     */   public String getGroupName()
/*     */   {
/* 149 */     TsapiTrace.traceEntry("getGroupName[]", this);
/* 150 */     String name = this.tsTrunk.getGroupName();
/* 151 */     TsapiTrace.traceExit("getGroupName[]", this);
/* 152 */     return name;
/*     */   }
/*     */ 
/*     */   public String getMemberName()
/*     */   {
/* 160 */     TsapiTrace.traceEntry("getMemberName[]", this);
/* 161 */     String name = this.tsTrunk.getMemberName();
/* 162 */     TsapiTrace.traceExit("getMemberName[]", this);
/* 163 */     return name;
/*     */   }
/*     */ 
/*     */   public static String makeTrunkName(String groupName, String memberName)
/*     */   {
/* 179 */     TsapiTrace.traceEntry("makeTrunkName[String groupName, String memberName]", null);
/* 180 */     String name = null;
/* 181 */     if (groupName == null) {
/* 182 */       name = null;
/*     */     }
/* 184 */     else if (memberName == null) {
/* 185 */       name = groupName + ":0";
/*     */     }
/*     */     else {
/* 188 */       name = groupName + ":" + memberName;
/*     */     }
/* 190 */     TsapiTrace.traceExit("makeTrunkName[String groupName, String memberName]", null);
/* 191 */     return name;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiTrunkImpl
 * JD-Core Version:    0.5.4
 */