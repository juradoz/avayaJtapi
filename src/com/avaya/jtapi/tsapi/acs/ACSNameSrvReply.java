 package com.avaya.jtapi.tsapi.acs;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class ACSNameSrvReply extends ACSConfirmation
 {
   boolean more;
   ACSNameAddr[] list;
   public static final int PDU = 11;
 
   public ACSNameSrvReply()
   {
   }
 
   public ACSNameSrvReply(boolean _more, ACSNameAddr[] _list)
   {
     this.more = _more;
     this.list = _list;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     ASNBoolean.encode(this.more, memberStream);
     ACSNameAddrList.encode(this.list, memberStream);
   }
 
   public static ACSNameSrvReply decode(InputStream in)
   {
     ACSNameSrvReply _this = new ACSNameSrvReply();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.more = ASNBoolean.decode(memberStream);
     this.list = ACSNameAddrList.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("ACSNameSrvReply ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNBoolean.print(this.more, "more", indent));
     lines.addAll(ACSNameAddrList.print(this.list, "list", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 11;
   }
 
   public ACSNameAddr[] getList()
   {
     return this.list;
   }
 
   public boolean isMore()
   {
     return this.more;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSNameSrvReply
 * JD-Core Version:    0.5.4
 */