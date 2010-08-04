/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAGetDeviceListConfEvent extends CSTAConfirmation
/*     */ {
/*     */   short driverSdbLevel;
/*     */   short level;
/*     */   int index;
/*     */   String[] devList;
/*     */   public static final int PDU = 127;
/*     */   public static final int NO_SDB_CHECKING = -1;
/*     */   public static final int ACS_ONLY = 1;
/*     */ 
/*     */   public static CSTAGetDeviceListConfEvent decode(InputStream in)
/*     */   {
/*  21 */     CSTAGetDeviceListConfEvent _this = new CSTAGetDeviceListConfEvent();
/*  22 */     _this.doDecode(in);
/*     */ 
/*  24 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  29 */     SDBLevel.encode(this.driverSdbLevel, memberStream);
/*  30 */     CSTALevel.encode(this.level, memberStream);
/*  31 */     ASNInteger.encode(this.index, memberStream);
/*  32 */     DeviceList.encode(this.devList, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  37 */     this.driverSdbLevel = SDBLevel.decode(memberStream);
/*  38 */     this.level = CSTALevel.decode(memberStream);
/*  39 */     this.index = ASNInteger.decode(memberStream);
/*  40 */     this.devList = DeviceList.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  45 */     Collection lines = new ArrayList();
/*  46 */     lines.add("CSTAGetDeviceListConfEvent ::=");
/*  47 */     lines.add("{");
/*     */ 
/*  49 */     String indent = "  ";
/*     */ 
/*  51 */     lines.addAll(SDBLevel.print(this.driverSdbLevel, "driverSdbLevel", indent));
/*  52 */     lines.addAll(CSTALevel.print(this.level, "level", indent));
/*  53 */     lines.addAll(ASNInteger.print(this.index, "index", indent));
/*  54 */     lines.addAll(DeviceList.print(this.devList, "devList", indent));
/*     */ 
/*  56 */     lines.add("}");
/*  57 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  65 */     return 127;
/*     */   }
/*     */ 
/*     */   public String[] getDevList()
/*     */   {
/*  71 */     return this.devList;
/*     */   }
/*     */   public void setDevList(String[] devList) {
/*  74 */     this.devList = devList;
/*     */   }
/*     */ 
/*     */   public short getDriverSdbLevel()
/*     */   {
/*  81 */     return this.driverSdbLevel;
/*     */   }
/*     */   public void setDriverSdbLevel(short driverSdbLevel) {
/*  84 */     this.driverSdbLevel = driverSdbLevel;
/*     */   }
/*     */ 
/*     */   public int getIndex()
/*     */   {
/*  91 */     return this.index;
/*     */   }
/*     */   public void setIndex(int index) {
/*  94 */     this.index = index;
/*     */   }
/*     */ 
/*     */   public short getLevel()
/*     */   {
/* 101 */     return this.level;
/*     */   }
/*     */   public void setLevel(short level) {
/* 104 */     this.level = level;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAGetDeviceListConfEvent
 * JD-Core Version:    0.5.4
 */