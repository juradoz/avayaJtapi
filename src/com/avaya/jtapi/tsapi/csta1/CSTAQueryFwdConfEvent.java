 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAQueryFwdConfEvent extends CSTAConfirmation
 {
   CSTAForwardingInfo[] forward;
   public static final int PDU = 32;
 
   public static CSTAQueryFwdConfEvent decode(InputStream in)
   {
     CSTAQueryFwdConfEvent _this = new CSTAQueryFwdConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.forward = ListForwardParameters.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     ListForwardParameters.encode(this.forward, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTAQueryFwdConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ListForwardParameters.print(this.forward, "forward", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 32;
   }
 
   public CSTAForwardingInfo[] getForward()
   {
     return this.forward;
   }
 
   public void setForward(CSTAForwardingInfo[] forward) {
     this.forward = forward;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryFwdConfEvent
 * JD-Core Version:    0.5.4
 */