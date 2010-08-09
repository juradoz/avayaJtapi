 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAAlternateCall extends CSTARequest
 {
   CSTAConnectionID activeCall;
   CSTAConnectionID otherCall;
   public static final int PDU = 1;
 
   public CSTAConnectionID getActiveCall()
   {
     return this.activeCall;
   }
 
   public CSTAConnectionID getOtherCall()
   {
     return this.otherCall;
   }
 
   public CSTAAlternateCall()
   {
   }
 
   public CSTAAlternateCall(CSTAConnectionID _activeCall, CSTAConnectionID _otherCall)
   {
     this.activeCall = _activeCall;
     this.otherCall = _otherCall;
   }
 
   public static CSTAAlternateCall decode(InputStream in)
   {
     CSTAAlternateCall _this = new CSTAAlternateCall();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.activeCall = CSTAConnectionID.decode(memberStream);
     this.otherCall = CSTAConnectionID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.activeCall, memberStream);
     CSTAConnectionID.encode(this.otherCall, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAAlternateCall ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall", indent));
     lines.addAll(CSTAConnectionID.print(this.otherCall, "otherCall", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 1;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAAlternateCall
 * JD-Core Version:    0.5.4
 */