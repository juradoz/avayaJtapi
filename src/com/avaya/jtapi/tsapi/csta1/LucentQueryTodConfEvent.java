/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class LucentQueryTodConfEvent extends LucentPrivateData
/*     */ {
/*     */   int year;
/*     */   int month;
/*     */   int day;
/*     */   int hour;
/*     */   int minute;
/*     */   int second;
/*     */   static final int PDU = 25;
/*     */ 
/*     */   public static LucentQueryTodConfEvent decode(InputStream in)
/*     */   {
/*  20 */     LucentQueryTodConfEvent _this = new LucentQueryTodConfEvent();
/*  21 */     _this.doDecode(in);
/*     */ 
/*  23 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  28 */     this.year = ASNInteger.decode(memberStream);
/*  29 */     this.month = ASNInteger.decode(memberStream);
/*  30 */     this.day = ASNInteger.decode(memberStream);
/*  31 */     this.hour = ASNInteger.decode(memberStream);
/*  32 */     this.minute = ASNInteger.decode(memberStream);
/*  33 */     this.second = ASNInteger.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  38 */     Collection lines = new ArrayList();
/*     */ 
/*  40 */     lines.add("LucentQueryTodConfEvent ::=");
/*  41 */     lines.add("{");
/*     */ 
/*  43 */     String indent = "  ";
/*     */ 
/*  45 */     lines.addAll(ASNInteger.print(this.year, "year", indent));
/*  46 */     lines.addAll(ASNInteger.print(this.month, "month", indent));
/*  47 */     lines.addAll(ASNInteger.print(this.day, "day", indent));
/*  48 */     lines.addAll(ASNInteger.print(this.hour, "hour", indent));
/*  49 */     lines.addAll(ASNInteger.print(this.minute, "minute", indent));
/*  50 */     lines.addAll(ASNInteger.print(this.second, "second", indent));
/*     */ 
/*  52 */     lines.add("}");
/*  53 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  58 */     return 25;
/*     */   }
/*     */ 
/*     */   public int getDay()
/*     */   {
/*  64 */     return this.day;
/*     */   }
/*     */ 
/*     */   public void setDay(int day)
/*     */   {
/*  72 */     this.day = day;
/*     */   }
/*     */ 
/*     */   public int getHour()
/*     */   {
/*  80 */     return this.hour;
/*     */   }
/*     */ 
/*     */   public void setHour(int hour)
/*     */   {
/*  88 */     this.hour = hour;
/*     */   }
/*     */ 
/*     */   public int getMinute()
/*     */   {
/*  96 */     return this.minute;
/*     */   }
/*     */ 
/*     */   public void setMinute(int minute)
/*     */   {
/* 104 */     this.minute = minute;
/*     */   }
/*     */ 
/*     */   public int getMonth()
/*     */   {
/* 112 */     return this.month;
/*     */   }
/*     */ 
/*     */   public void setMonth(int month)
/*     */   {
/* 120 */     this.month = month;
/*     */   }
/*     */ 
/*     */   public int getSecond()
/*     */   {
/* 128 */     return this.second;
/*     */   }
/*     */ 
/*     */   public void setSecond(int second)
/*     */   {
/* 136 */     this.second = second;
/*     */   }
/*     */ 
/*     */   public int getYear()
/*     */   {
/* 144 */     return this.year;
/*     */   }
/*     */ 
/*     */   public void setYear(int year)
/*     */   {
/* 152 */     this.year = year;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryTodConfEvent
 * JD-Core Version:    0.5.4
 */