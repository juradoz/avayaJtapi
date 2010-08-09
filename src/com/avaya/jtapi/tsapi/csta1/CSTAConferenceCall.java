 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAConferenceCall extends CSTARequest
 {
   CSTAConnectionID heldCall;
   CSTAConnectionID activeCall;
   public static final int PDU = 11;
 
   public CSTAConferenceCall()
   {
   }
 
   public CSTAConferenceCall(CSTAConnectionID _heldCall, CSTAConnectionID _activeCall)
   {
     this.heldCall = _heldCall;
     this.activeCall = _activeCall;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.heldCall, memberStream);
     CSTAConnectionID.encode(this.activeCall, memberStream);
   }
 
   public static CSTAConferenceCall decode(InputStream in)
   {
     CSTAConferenceCall _this = new CSTAConferenceCall();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.heldCall = CSTAConnectionID.decode(memberStream);
     this.activeCall = CSTAConnectionID.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAConferenceCall ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.heldCall, "heldCall", indent));
     lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 11;
   }
 
   public CSTAConnectionID getActiveCall()
   {
     return this.activeCall;
   }
 
   public CSTAConnectionID getHeldCall()
   {
     return this.heldCall;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConferenceCall
 * JD-Core Version:    0.5.4
 */