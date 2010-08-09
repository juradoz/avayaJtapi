 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import com.avaya.jtapi.tsapi.asn1.ASNSequence;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class CSTALinkStatus extends ASNSequence
 {
   private int linkID;
   private short linkState;
 
   static CSTALinkStatus decode(InputStream in)
   {
     CSTALinkStatus _this = new CSTALinkStatus();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream) {
     this.linkID = ASNInteger.decode(memberStream);
     this.linkState = LinkState.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     ASNInteger.encode(this.linkID, memberStream);
     LinkState.encode(this.linkState, memberStream);
   }
 
   public static Collection<String> print(CSTALinkStatus _this, String name, String _indent)
   {
     Collection lines = new ArrayList();
     if (_this == null) {
       lines.add(_indent + name + " <null>");
       return lines;
     }
     if (name != null) {
       lines.add(_indent + name);
     }
     lines.add(_indent + "{");
 
     String indent = _indent + "  ";
 
     lines.addAll(ASNInteger.print(_this.linkID, "linkID", indent));
     lines.addAll(LinkState.print(_this.linkState, "linkState", indent));
 
     lines.add(_indent + "}");
     return lines;
   }
 
   public int getLinkID() {
     return this.linkID;
   }
 
   public void setLinkID(int linkID) {
     this.linkID = linkID;
   }
 
   public short getLinkState() {
     return this.linkState;
   }
 
   public void setLinkState(short linkState) {
     this.linkState = linkState;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTALinkStatus
 * JD-Core Version:    0.5.4
 */