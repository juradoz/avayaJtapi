/*     */ package com.avaya.jtapi.tsapi.acs;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class ACSNameAddr extends ASNSequence
/*     */ {
/*     */   String serverName;
/*     */   byte[] serverAddr;
/*     */ 
/*     */   public ACSNameAddr()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ACSNameAddr(String _serverName, byte[] _serverAddr)
/*     */   {
/*  29 */     this.serverName = _serverName;
/*  30 */     this.serverAddr = _serverAddr;
/*     */   }
/*     */ 
/*     */   public static ACSNameAddr decode(InputStream in)
/*     */   {
/*  35 */     ACSNameAddr _this = new ACSNameAddr();
/*  36 */     _this.doDecode(in);
/*     */ 
/*  38 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  45 */     this.serverName = ServerID.decode(memberStream);
/*  46 */     this.serverAddr = ASNOctetString.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  51 */     ServerID.encode(this.serverName, memberStream);
/*  52 */     ASNOctetString.encode(this.serverAddr, memberStream);
/*     */   }
/*     */ 
/*     */   public static Collection<String> print(ACSNameAddr _this, String name, String _indent)
/*     */   {
/*  57 */     Collection lines = new ArrayList();
/*  58 */     if (_this == null)
/*     */     {
/*  60 */       lines.add(_indent + name + " <null>");
/*  61 */       return lines;
/*     */     }
/*  63 */     if (name != null) {
/*  64 */       lines.add(_indent + name);
/*     */     }
/*  66 */     lines.add(_indent + "{");
/*     */ 
/*  68 */     String indent = _indent + "  ";
/*     */ 
/*  70 */     lines.addAll(ServerID.print(_this.serverName, "serverName", indent));
/*  71 */     lines.addAll(ASNOctetString.print(_this.serverAddr, "serverAddr", indent));
/*     */ 
/*  73 */     lines.add(_indent + "}");
/*  74 */     return lines;
/*     */   }
/*     */ 
/*     */   public byte[] getServerAddr()
/*     */   {
/*  81 */     return this.serverAddr;
/*     */   }
/*     */ 
/*     */   public String getServerName()
/*     */   {
/*  89 */     return this.serverName;
/*     */   }
/*     */ 
/*     */   public InetSocketAddress createInetSocketAddress()
/*     */   {
/* 102 */     String hostname = (this.serverAddr[4] & 0xFF) + "." + (this.serverAddr[5] & 0xFF) + "." + (this.serverAddr[6] & 0xFF) + "." + (this.serverAddr[7] & 0xFF);
/*     */ 
/* 107 */     int port = ((this.serverAddr[2] & 0xFF) << 8) + (this.serverAddr[3] & 0xFF);
/*     */ 
/* 110 */     return new InetSocketAddress(hostname, port);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSNameAddr
 * JD-Core Version:    0.5.4
 */