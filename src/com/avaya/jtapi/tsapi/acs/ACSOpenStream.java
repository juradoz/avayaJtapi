/*     */ package com.avaya.jtapi.tsapi.acs;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class ACSOpenStream extends ACSRequest
/*     */ {
/*     */   short streamType;
/*     */   String serverID;
/*     */   String loginID;
/*     */   byte[] cryptPass;
/*     */   String applicationName;
/*     */   short level;
/*     */   String apiVer;
/*     */   String libVer;
/*     */   String tsrvVer;
/*     */   public static final int PDU = 1;
/*     */ 
/*     */   public ACSOpenStream(short _streamType, String _serverID, String _loginID, byte[] _cryptPass, String _applicationName, short _level, String _apiVer, String _libVer, String _tsrvVer)
/*     */   {
/*  32 */     this.streamType = _streamType;
/*  33 */     this.serverID = _serverID;
/*  34 */     this.loginID = _loginID;
/*  35 */     this.cryptPass = _cryptPass;
/*  36 */     this.applicationName = _applicationName;
/*  37 */     this.level = _level;
/*  38 */     this.apiVer = _apiVer;
/*  39 */     this.libVer = _libVer;
/*  40 */     this.tsrvVer = _tsrvVer;
/*     */   }
/*     */ 
/*     */   public ACSOpenStream()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  49 */     StreamType.encode(this.streamType, memberStream);
/*  50 */     ServerID.encode(this.serverID, memberStream);
/*  51 */     LoginID.encode(this.loginID, memberStream);
/*  52 */     CryptPasswd.encode(this.cryptPass, memberStream);
/*  53 */     AppName.encode(this.applicationName, memberStream);
/*  54 */     Level.encode(this.level, memberStream);
/*  55 */     Version.encode(this.apiVer, memberStream);
/*  56 */     Version.encode(this.libVer, memberStream);
/*  57 */     Version.encode(this.tsrvVer, memberStream);
/*     */   }
/*     */ 
/*     */   public static ACSOpenStream decode(InputStream in)
/*     */   {
/*  62 */     ACSOpenStream _this = new ACSOpenStream();
/*  63 */     _this.doDecode(in);
/*     */ 
/*  65 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  71 */     this.streamType = StreamType.decode(memberStream);
/*  72 */     this.serverID = ServerID.decode(memberStream);
/*  73 */     this.loginID = LoginID.decode(memberStream);
/*  74 */     this.cryptPass = CryptPasswd.decode(memberStream);
/*  75 */     this.applicationName = AppName.decode(memberStream);
/*  76 */     this.level = Level.decode(memberStream);
/*  77 */     this.apiVer = Version.decode(memberStream);
/*  78 */     this.libVer = Version.decode(memberStream);
/*  79 */     this.tsrvVer = Version.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  85 */     Collection lines = new ArrayList();
/*  86 */     lines.add("ACSOpenStream ::=");
/*  87 */     lines.add("{");
/*     */ 
/*  89 */     String indent = "  ";
/*     */ 
/*  91 */     lines.addAll(StreamType.print(this.streamType, "streamType", indent));
/*  92 */     lines.addAll(ServerID.print(this.serverID, "serverID", indent));
/*  93 */     lines.addAll(LoginID.print(this.loginID, "loginID", indent));
/*  94 */     lines.addAll(CryptPasswd.print(this.cryptPass, "cryptPass", indent));
/*  95 */     lines.addAll(AppName.print(this.applicationName, "applicationName", indent));
/*  96 */     lines.addAll(Level.print(this.level, "level", indent));
/*  97 */     lines.addAll(Version.print(this.apiVer, "apiVer", indent));
/*  98 */     lines.addAll(Version.print(this.libVer, "libVer", indent));
/*  99 */     lines.addAll(Version.print(this.tsrvVer, "tsrvVer", indent));
/*     */ 
/* 101 */     lines.add("}");
/* 102 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/* 107 */     return 1;
/*     */   }
/*     */ 
/*     */   public synchronized String getApiVer()
/*     */   {
/* 113 */     return this.apiVer;
/*     */   }
/*     */ 
/*     */   public synchronized String getApplicationName()
/*     */   {
/* 121 */     return this.applicationName;
/*     */   }
/*     */ 
/*     */   public synchronized byte[] getCryptPass()
/*     */   {
/* 129 */     return this.cryptPass;
/*     */   }
/*     */ 
/*     */   public synchronized short getLevel()
/*     */   {
/* 137 */     return this.level;
/*     */   }
/*     */ 
/*     */   public synchronized String getLibVer()
/*     */   {
/* 145 */     return this.libVer;
/*     */   }
/*     */ 
/*     */   public synchronized String getLoginID()
/*     */   {
/* 153 */     return this.loginID;
/*     */   }
/*     */ 
/*     */   public synchronized String getServerID()
/*     */   {
/* 161 */     return this.serverID;
/*     */   }
/*     */ 
/*     */   public synchronized short getStreamType()
/*     */   {
/* 169 */     return this.streamType;
/*     */   }
/*     */ 
/*     */   public synchronized String getTsrvVer()
/*     */   {
/* 177 */     return this.tsrvVer;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSOpenStream
 * JD-Core Version:    0.5.4
 */