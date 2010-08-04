/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentUserToUserInfo extends LucentPrivateData
/*     */ {
/*     */   protected short type;
/*     */   protected byte[] data;
/*     */ 
/*     */   public LucentUserToUserInfo(String _data)
/*     */   {
/*  37 */     this.type = 4;
/*  38 */     this.data = _data.getBytes();
/*     */   }
/*     */ 
/*     */   public LucentUserToUserInfo(byte[] _data)
/*     */   {
/*  48 */     this.type = 0;
/*  49 */     this.data = _data;
/*     */   }
/*     */ 
/*     */   public String getString()
/*     */   {
/*  57 */     return new String(this.data);
/*     */   }
/*     */ 
/*     */   public byte[] getBytes()
/*     */   {
/*  67 */     return this.data;
/*     */   }
/*     */ 
/*     */   public boolean isAscii()
/*     */   {
/*  75 */     return this.type == 4;
/*     */   }
/*     */ 
/*     */   public LucentUserToUserInfo()
/*     */   {
/*  83 */     this.type = -1;
/*     */   }
/*     */ 
/*     */   public LucentUserToUserInfo(byte[] _data, short _type)
/*     */   {
/*  92 */     this.type = _type; this.data = _data;
/*     */   }
/*     */ 
/*     */   public static LucentUserToUserInfo decode(InputStream in) {
/*  96 */     LucentUserToUserInfo _this = new LucentUserToUserInfo();
/*  97 */     _this.doDecode(in);
/*  98 */     if ((_this.type == -1) || (_this.data == null))
/*     */     {
/* 100 */       return null;
/*     */     }
/* 102 */     return _this;
/*     */   }
/*     */ 
/*     */   public static void encode(LucentUserToUserInfo _this, OutputStream out)
/*     */   {
/* 108 */     if (_this == null)
/*     */     {
/* 110 */       _this = new LucentUserToUserInfo();
/*     */     }
/* 112 */     _this.encode(out);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/* 117 */     this.type = UUIProtocolType.decode(memberStream);
/* 118 */     this.data = ASNOctetString.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/* 123 */     UUIProtocolType.encode(this.type, memberStream);
/* 124 */     ASNOctetString.encode(this.data, memberStream);
/*     */   }
/*     */ 
/*     */   public static Collection<String> print(LucentUserToUserInfo _this, String name, String _indent)
/*     */   {
/* 129 */     Collection lines = new ArrayList();
/*     */ 
/* 131 */     if (_this == null)
/*     */     {
/* 133 */       lines.add(_indent + name + " <null>");
/* 134 */       return lines;
/*     */     }
/* 136 */     if (name != null) {
/* 137 */       lines.add(_indent + name);
/*     */     }
/* 139 */     lines.add(_indent + "{");
/*     */ 
/* 141 */     String indent = _indent + "  ";
/*     */ 
/* 143 */     lines.addAll(UUIProtocolType.print(_this.type, "type", indent));
/* 144 */     if (_this.type == 4)
/*     */     {
/* 147 */       lines.addAll(ASNIA5String.print(new String(_this.data), "data", indent));
/*     */     }
/*     */     else
/*     */     {
/* 151 */       lines.addAll(ASNOctetString.print(_this.data, "data", indent));
/*     */     }
/*     */ 
/* 154 */     lines.add(_indent + "}");
/* 155 */     return lines;
/*     */   }
/*     */ 
/*     */   public short getType() {
/* 159 */     return this.type;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo
 * JD-Core Version:    0.5.4
 */