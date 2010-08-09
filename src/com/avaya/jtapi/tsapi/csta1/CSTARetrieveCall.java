 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTARetrieveCall extends CSTARequest
 {
   CSTAConnectionID heldCall;
   public static final int PDU = 41;
 
   public CSTARetrieveCall(CSTAConnectionID _heldCall)
   {
     this.heldCall = _heldCall;
   }
 
   public CSTARetrieveCall()
   {
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTAConnectionID.encode(this.heldCall, memberStream);
   }
 
   public static CSTARetrieveCall decode(InputStream in)
   {
     CSTARetrieveCall _this = new CSTARetrieveCall();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.heldCall = CSTAConnectionID.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTARetrieveCall ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.heldCall, "heldCall", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 41;
   }
 
   public CSTAConnectionID getHeldCall()
   {
     return this.heldCall;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARetrieveCall
 * JD-Core Version:    0.5.4
 */