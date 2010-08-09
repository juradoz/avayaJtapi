 package com.avaya.jtapi.tsapi.acs;
 
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class ACSAuthReplyTwo extends ACSConfirmation
 {
   int objectID;
   byte[] key;
   ACSAuthInfo authInfo;
   short encodeType;
   String pipe;
   public static final int PDU = 13;
 
   public ACSAuthReplyTwo()
   {
   }
 
   public ACSAuthReplyTwo(int _objectID, byte[] _key, ACSAuthInfo _authInfo, short _encodeType, String _pipe)
   {
     this.objectID = _objectID;
     this.key = _key;
     this.authInfo = _authInfo;
     this.encodeType = _encodeType;
     this.pipe = _pipe;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     ASNInteger.encode(this.objectID, memberStream);
     ChallengeKey.encode(this.key, memberStream);
     ACSAuthInfo.encode(this.authInfo, memberStream);
     ACSEncodeType.encode(this.encodeType, memberStream);
     WinNTPipe.encode(this.pipe, memberStream);
   }
 
   public static ACSAuthReplyTwo decode(InputStream in)
   {
     ACSAuthReplyTwo _this = new ACSAuthReplyTwo();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.objectID = ASNInteger.decode(memberStream);
     this.key = ChallengeKey.decode(memberStream);
     this.authInfo = ACSAuthInfo.decode(memberStream);
     this.encodeType = ACSEncodeType.decode(memberStream);
     this.pipe = WinNTPipe.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("ACSAuthReplyTwo ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNInteger.print(this.objectID, "objectID", indent));
     lines.addAll(ChallengeKey.print(this.key, "key", indent));
     lines.addAll(ACSAuthInfo.print(this.authInfo, "authInfo", indent));
     lines.addAll(ACSEncodeType.print(this.encodeType, "encodeType", indent));
     lines.addAll(WinNTPipe.print(this.pipe, "pipe", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 13;
   }
 
   public ACSAuthInfo getAuthInfo()
   {
     return this.authInfo;
   }
 
   public short getEncodeType()
   {
     return this.encodeType;
   }
 
   public byte[] getKey()
   {
     return this.key;
   }
 
   public int getObjectID()
   {
     return this.objectID;
   }
 
   public String getPipe()
   {
     return this.pipe;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSAuthReplyTwo
 * JD-Core Version:    0.5.4
 */