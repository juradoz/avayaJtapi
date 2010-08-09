 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAClearCall extends CSTARequest
 {
   CSTAConnectionID call;
   public static final int PDU = 7;
 
   public CSTAClearCall(CSTAConnectionID _call)
   {
     this.call = _call;
   }
 
   public CSTAClearCall()
   {
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTAConnectionID.encode(this.call, memberStream);
   }
 
   public static CSTAClearCall decode(InputStream in)
   {
     CSTAClearCall _this = new CSTAClearCall();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.call = CSTAConnectionID.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAClearCall ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.call, "call", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 7;
   }
 
   public CSTAConnectionID getCall()
   {
     return this.call;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAClearCall
 * JD-Core Version:    0.5.4
 */