 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAMakePredictiveCallConfEvent extends CSTAConfirmation
 {
   CSTAConnectionID newCall;
   public static final int PDU = 26;
 
   public static CSTAMakePredictiveCallConfEvent decode(InputStream in)
   {
     CSTAMakePredictiveCallConfEvent _this = new CSTAMakePredictiveCallConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.newCall = CSTAConnectionID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTAConnectionID.encode(this.newCall, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAMakePredictiveCallConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.newCall, "newCall", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 26;
   }
 
   public CSTAConnectionID getNewCall()
   {
     return this.newCall;
   }
 
   public void setNewCall(CSTAConnectionID newCall) {
     this.newCall = newCall;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMakePredictiveCallConfEvent
 * JD-Core Version:    0.5.4
 */