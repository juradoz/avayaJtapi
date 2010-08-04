/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAMonitorFilter extends ASNSequence
/*     */ {
/*     */   int call;
/*     */   int feature;
/*     */   int agent;
/*     */   int maintenance;
/*     */   int privateFilter;
/*     */ 
/*     */   public CSTAMonitorFilter()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CSTAMonitorFilter(int _call, int _feature, int _agent, int _maintenance, int _privateFilter)
/*     */   {
/*  29 */     this.call = _call;
/*  30 */     this.feature = _feature;
/*  31 */     this.agent = _agent;
/*  32 */     this.maintenance = _maintenance;
/*  33 */     this.privateFilter = _privateFilter;
/*     */   }
/*     */ 
/*     */   public static CSTAMonitorFilter decode(InputStream in)
/*     */   {
/*  38 */     CSTAMonitorFilter _this = new CSTAMonitorFilter();
/*  39 */     _this.doDecode(in);
/*     */ 
/*  41 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  48 */     this.call = CSTACallFilter.decode(memberStream);
/*  49 */     this.feature = CSTAFeatureFilter.decode(memberStream);
/*  50 */     this.agent = CSTAAgentFilter.decode(memberStream);
/*  51 */     this.maintenance = CSTAMaintenanceFilter.decode(memberStream);
/*  52 */     this.privateFilter = ASNInteger.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  57 */     CSTACallFilter.encode(this.call, memberStream);
/*  58 */     CSTAFeatureFilter.encode(this.feature, memberStream);
/*  59 */     CSTAAgentFilter.encode(this.agent, memberStream);
/*  60 */     CSTAMaintenanceFilter.encode(this.maintenance, memberStream);
/*  61 */     ASNInteger.encode(this.privateFilter, memberStream);
/*     */   }
/*     */ 
/*     */   public static Collection<String> print(CSTAMonitorFilter _this, String name, String _indent)
/*     */   {
/*  66 */     Collection lines = new ArrayList();
/*  67 */     if (_this == null)
/*     */     {
/*  69 */       lines.add(_indent + name + " <null>");
/*  70 */       return lines;
/*     */     }
/*  72 */     if (name != null) {
/*  73 */       lines.add(_indent + name);
/*     */     }
/*  75 */     lines.add(_indent + "{");
/*     */ 
/*  77 */     String indent = _indent + "  ";
/*     */ 
/*  79 */     lines.addAll(CSTACallFilter.print(_this.call, "call", indent));
/*  80 */     lines.addAll(CSTAFeatureFilter.print(_this.feature, "feature", indent));
/*  81 */     lines.addAll(CSTAAgentFilter.print(_this.agent, "agent", indent));
/*  82 */     lines.addAll(CSTAMaintenanceFilter.print(_this.maintenance, "maintenance", indent));
/*  83 */     lines.addAll(ASNInteger.print(_this.privateFilter, "privateFilter", indent));
/*     */ 
/*  85 */     lines.add(_indent + "}");
/*  86 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getAgent()
/*     */   {
/*  93 */     return this.agent;
/*     */   }
/*     */ 
/*     */   public int getCall()
/*     */   {
/* 101 */     return this.call;
/*     */   }
/*     */ 
/*     */   public int getFeature()
/*     */   {
/* 109 */     return this.feature;
/*     */   }
/*     */ 
/*     */   public int getMaintenance()
/*     */   {
/* 117 */     return this.maintenance;
/*     */   }
/*     */ 
/*     */   public int getPrivateFilter()
/*     */   {
/* 125 */     return this.privateFilter;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMonitorFilter
 * JD-Core Version:    0.5.4
 */