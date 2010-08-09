 package com.avaya.jtapi.tsapi.acs;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class ACSOpenStream extends ACSRequest
 {
   short streamType;
   String serverID;
   String loginID;
   byte[] cryptPass;
   String applicationName;
   short level;
   String apiVer;
   String libVer;
   String tsrvVer;
   public static final int PDU = 1;
 
   public ACSOpenStream(short _streamType, String _serverID, String _loginID, byte[] _cryptPass, String _applicationName, short _level, String _apiVer, String _libVer, String _tsrvVer)
   {
     this.streamType = _streamType;
     this.serverID = _serverID;
     this.loginID = _loginID;
     this.cryptPass = _cryptPass;
     this.applicationName = _applicationName;
     this.level = _level;
     this.apiVer = _apiVer;
     this.libVer = _libVer;
     this.tsrvVer = _tsrvVer;
   }
 
   public ACSOpenStream()
   {
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     StreamType.encode(this.streamType, memberStream);
     ServerID.encode(this.serverID, memberStream);
     LoginID.encode(this.loginID, memberStream);
     CryptPasswd.encode(this.cryptPass, memberStream);
     AppName.encode(this.applicationName, memberStream);
     Level.encode(this.level, memberStream);
     Version.encode(this.apiVer, memberStream);
     Version.encode(this.libVer, memberStream);
     Version.encode(this.tsrvVer, memberStream);
   }
 
   public static ACSOpenStream decode(InputStream in)
   {
     ACSOpenStream _this = new ACSOpenStream();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.streamType = StreamType.decode(memberStream);
     this.serverID = ServerID.decode(memberStream);
     this.loginID = LoginID.decode(memberStream);
     this.cryptPass = CryptPasswd.decode(memberStream);
     this.applicationName = AppName.decode(memberStream);
     this.level = Level.decode(memberStream);
     this.apiVer = Version.decode(memberStream);
     this.libVer = Version.decode(memberStream);
     this.tsrvVer = Version.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("ACSOpenStream ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(StreamType.print(this.streamType, "streamType", indent));
     lines.addAll(ServerID.print(this.serverID, "serverID", indent));
     lines.addAll(LoginID.print(this.loginID, "loginID", indent));
     lines.addAll(CryptPasswd.print(this.cryptPass, "cryptPass", indent));
     lines.addAll(AppName.print(this.applicationName, "applicationName", indent));
     lines.addAll(Level.print(this.level, "level", indent));
     lines.addAll(Version.print(this.apiVer, "apiVer", indent));
     lines.addAll(Version.print(this.libVer, "libVer", indent));
     lines.addAll(Version.print(this.tsrvVer, "tsrvVer", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 1;
   }
 
   public synchronized String getApiVer()
   {
     return this.apiVer;
   }
 
   public synchronized String getApplicationName()
   {
     return this.applicationName;
   }
 
   public synchronized byte[] getCryptPass()
   {
     return this.cryptPass;
   }
 
   public synchronized short getLevel()
   {
     return this.level;
   }
 
   public synchronized String getLibVer()
   {
     return this.libVer;
   }
 
   public synchronized String getLoginID()
   {
     return this.loginID;
   }
 
   public synchronized String getServerID()
   {
     return this.serverID;
   }
 
   public synchronized short getStreamType()
   {
     return this.streamType;
   }
 
   public synchronized String getTsrvVer()
   {
     return this.tsrvVer;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSOpenStream
 * JD-Core Version:    0.5.4
 */