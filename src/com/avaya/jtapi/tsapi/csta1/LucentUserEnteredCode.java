/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class LucentUserEnteredCode extends LucentPrivateData
/*     */ {
/*     */   public static final short UE_ANY = 0;
/*     */   public static final short UE_LOGIN_DIGITS = 2;
/*     */   public static final short UE_CALL_PROMPTER = 5;
/*     */   public static final short UE_DATA_BASE_PROVIDED = 17;
/*     */   public static final short UE_TONE_DETECTOR = 32;
/*     */   public static final short UE_COLLECT = 0;
/*     */   public static final short UE_ENTERED = 1;
/*     */   short type;
/*     */   short indicator;
/*     */   String data;
/*     */   String collectVDN_asn;
/*     */ 
/*     */   public short getType()
/*     */   {
/* 182 */     return this.type;
/*     */   }
/*     */ 
/*     */   public short getIndicator()
/*     */   {
/* 190 */     return this.indicator;
/*     */   }
/*     */ 
/*     */   public String getDigits()
/*     */   {
/* 198 */     return this.data;
/*     */   }
/*     */ 
/*     */   public static LucentUserEnteredCode decode(InputStream in)
/*     */   {
/* 204 */     LucentUserEnteredCode _this = new LucentUserEnteredCode();
/* 205 */     _this.doDecode(in);
/* 206 */     if (_this.type == -1)
/*     */     {
/* 208 */       return null;
/*     */     }
/* 210 */     return _this;
/*     */   }
/*     */ 
/*     */   public static void encode(LucentUserEnteredCode _this, OutputStream out) {
/* 214 */     if (_this == null)
/*     */     {
/* 216 */       _this = new LucentUserEnteredCode();
/*     */     }
/* 218 */     _this.encode(out);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream) {
/* 222 */     UserEnteredCodeType.encode(this.type, memberStream);
/* 223 */     UserEnteredCodeIndicator.encode(this.indicator, memberStream);
/* 224 */     ASNIA5String.encode(this.data, memberStream);
/* 225 */     DeviceID.encode(this.collectVDN_asn, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/* 230 */     this.type = UserEnteredCodeType.decode(memberStream);
/* 231 */     this.indicator = UserEnteredCodeIndicator.decode(memberStream);
/* 232 */     this.data = ASNIA5String.decode(memberStream);
/* 233 */     this.collectVDN_asn = DeviceID.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public static Collection<String> print(LucentUserEnteredCode _this, String name, String _indent)
/*     */   {
/* 238 */     Collection lines = new ArrayList();
/*     */ 
/* 240 */     if (_this == null)
/*     */     {
/* 242 */       lines.add(_indent + name + " <null>");
/* 243 */       return lines;
/*     */     }
/* 245 */     if (name != null) {
/* 246 */       lines.add(_indent + name);
/*     */     }
/* 248 */     lines.add(_indent + "{");
/*     */ 
/* 250 */     String indent = _indent + "  ";
/*     */ 
/* 252 */     lines.addAll(UserEnteredCodeType.print(_this.type, "type", indent));
/* 253 */     lines.addAll(UserEnteredCodeIndicator.print(_this.indicator, "indicator", indent));
/* 254 */     lines.addAll(ASNIA5String.print(_this.data, "data", indent));
/* 255 */     lines.addAll(DeviceID.print(_this.collectVDN_asn, "collectVDN", indent));
/*     */ 
/* 257 */     lines.add(_indent + "}");
/* 258 */     return lines;
/*     */   }
/*     */ 
/*     */   public void setCollectVDN_asn(String _collectVDN_asn) {
/* 262 */     this.collectVDN_asn = _collectVDN_asn;
/*     */   }
/*     */ 
/*     */   public void setData(String _data) {
/* 266 */     this.data = _data;
/*     */   }
/*     */ 
/*     */   public void setIndicator(short _indicator) {
/* 270 */     this.indicator = _indicator;
/*     */   }
/*     */ 
/*     */   public void setType(short _type) {
/* 274 */     this.type = _type;
/*     */   }
/*     */ 
/*     */   public String getCollectVDN_asn()
/*     */   {
/* 281 */     return this.collectVDN_asn;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentUserEnteredCode
 * JD-Core Version:    0.5.4
 */