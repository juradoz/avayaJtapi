 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class CSTAEscapeSvcReqConf extends CSTARequest
 {
   short errorValue;
   static final int PDU = 92;
 
   public CSTAEscapeSvcReqConf(short _errorValue)
   {
     this.errorValue = _errorValue;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAUniversalFailure.encode(this.errorValue, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAEscapeSvcReqConf ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAUniversalFailure.print(this.errorValue, "errorValue", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 92;
   }
 
   public short getErrorValue()
   {
     return this.errorValue;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAEscapeSvcReqConf
 * JD-Core Version:    0.5.4
 */