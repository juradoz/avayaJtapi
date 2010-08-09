 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNNull;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAHoldCallConfEvent extends CSTAConfirmation
 {
   public static final int PDU = 22;
 
   public static CSTAHoldCallConfEvent decode(InputStream in)
   {
     CSTAHoldCallConfEvent _this = new CSTAHoldCallConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream) {
     ASNNull.encode(memberStream);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     ASNNull.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAHoldCallConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNNull.print(indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 22;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAHoldCallConfEvent
 * JD-Core Version:    0.5.4
 */