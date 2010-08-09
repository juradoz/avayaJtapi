 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAQueryMwiConfEvent extends CSTAConfirmation
 {
   boolean messages;
   public static final int PDU = 28;
 
   public static CSTAQueryMwiConfEvent decode(InputStream in)
   {
     CSTAQueryMwiConfEvent _this = new CSTAQueryMwiConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.messages = ASNBoolean.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTAQueryMwiConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNBoolean.print(this.messages, "messages", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 28;
   }
 
   public boolean isMessages()
   {
     return this.messages;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryMwiConfEvent
 * JD-Core Version:    0.5.4
 */