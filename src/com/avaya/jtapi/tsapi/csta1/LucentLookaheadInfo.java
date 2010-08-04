/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentLookaheadInfo extends LucentPrivateData
/*     */ {
/*     */   public static final short LAI_ALL_INTERFLOW = 0;
/*     */   public static final short LAI_THRESHOLD_INTERFLOW = 1;
/*     */   public static final short LAI_VECTORING_INTERFLOW = 2;
/*     */   public static final short LAI_NOT_IN_QUEUE = 0;
/*     */   public static final short LAI_LOW = 1;
/*     */   public static final short LAI_MEDIUM = 2;
/*     */   public static final short LAI_HIGH = 3;
/*     */   public static final short LAI_TOP = 4;
/*     */   private short type;
/*     */   private short priority;
/*     */   private int hours;
/*     */   private int minutes;
/*     */   private int seconds;
/*     */   protected String sourceVDN;
/*     */ 
/*     */   public short getType()
/*     */   {
/*  47 */     return this.type;
/*     */   }
/*     */ 
/*     */   public short getPriority()
/*     */   {
/*  56 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public int getHours()
/*     */   {
/*  64 */     return this.hours;
/*     */   }
/*     */ 
/*     */   public int getMinutes()
/*     */   {
/*  72 */     return this.minutes;
/*     */   }
/*     */ 
/*     */   public int getSeconds()
/*     */   {
/*  80 */     return this.seconds;
/*     */   }
/*     */ 
/*     */   public String getSourceVDN()
/*     */   {
/*  88 */     return this.sourceVDN;
/*     */   }
/*     */ 
/*     */   public void setSourceVDN(String _sourceVDN) {
/*  92 */     this.sourceVDN = _sourceVDN;
/*     */   }
/*     */ 
/*     */   public static LucentLookaheadInfo decode(InputStream in)
/*     */   {
/*  98 */     LucentLookaheadInfo _this = new LucentLookaheadInfo();
/*  99 */     _this.doDecode(in);
/* 100 */     if (_this.type == -1)
/*     */     {
/* 102 */       return null;
/*     */     }
/* 104 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream) {
/* 108 */     Interflow.encode(this.type, memberStream);
/* 109 */     Priority.encode(this.priority, memberStream);
/* 110 */     ASNInteger.encode(this.hours, memberStream);
/* 111 */     ASNInteger.encode(this.minutes, memberStream);
/* 112 */     ASNInteger.encode(this.seconds, memberStream);
/* 113 */     DeviceID.encode(this.sourceVDN, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream) {
/* 117 */     this.type = Interflow.decode(memberStream);
/* 118 */     this.priority = Priority.decode(memberStream);
/* 119 */     this.hours = ASNInteger.decode(memberStream);
/* 120 */     this.minutes = ASNInteger.decode(memberStream);
/* 121 */     this.seconds = ASNInteger.decode(memberStream);
/* 122 */     this.sourceVDN = DeviceID.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public static Collection<String> print(LucentLookaheadInfo _this, String name, String _indent)
/*     */   {
/* 127 */     Collection lines = new ArrayList();
/*     */ 
/* 129 */     if (_this == null)
/*     */     {
/* 131 */       lines.add(_indent + name + " <null>");
/* 132 */       return lines;
/*     */     }
/* 134 */     if (name != null) {
/* 135 */       lines.add(_indent + name);
/*     */     }
/* 137 */     lines.add(_indent + "{");
/*     */ 
/* 139 */     String indent = _indent + "  ";
/*     */ 
/* 141 */     lines.addAll(Interflow.print(_this.type, "type", indent));
/* 142 */     lines.addAll(Priority.print(_this.priority, "priority", indent));
/* 143 */     lines.addAll(ASNInteger.print(_this.hours, "hours", indent));
/* 144 */     lines.addAll(ASNInteger.print(_this.minutes, "minutes", indent));
/* 145 */     lines.addAll(ASNInteger.print(_this.seconds, "seconds", indent));
/* 146 */     lines.addAll(DeviceID.print(_this.sourceVDN, "sourceVDN", indent));
/*     */ 
/* 148 */     lines.add(_indent + "}");
/* 149 */     return lines;
/*     */   }
/*     */ 
/*     */   public void setHours(int hours) {
/* 153 */     this.hours = hours;
/*     */   }
/*     */ 
/*     */   public void setMinutes(int minutes) {
/* 157 */     this.minutes = minutes;
/*     */   }
/*     */ 
/*     */   public void setPriority(short priority) {
/* 161 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public void setSeconds(int seconds) {
/* 165 */     this.seconds = seconds;
/*     */   }
/*     */ 
/*     */   public void setType(short type) {
/* 169 */     this.type = type;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentLookaheadInfo
 * JD-Core Version:    0.5.4
 */