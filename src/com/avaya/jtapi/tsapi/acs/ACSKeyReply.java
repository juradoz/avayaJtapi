 package com.avaya.jtapi.tsapi.acs;
 
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class ACSKeyReply extends ACSConfirmation
 {
   int objectID;
   byte[] key;
   public static final int PDU = 9;
 
   public static ACSKeyReply decode(InputStream in)
   {
     ACSKeyReply _this = new ACSKeyReply();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.objectID = ASNInteger.decode(memberStream);
     this.key = ChallengeKey.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("ACSKeyReply ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNInteger.print(this.objectID, "objectID", indent));
     lines.addAll(ChallengeKey.print(this.key, "key", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 9;
   }
 
   public byte[] getKey()
   {
     return this.key;
   }
 
   public int getObjectID()
   {
     return this.objectID;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSKeyReply
 * JD-Core Version:    0.5.4
 */