 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNNull;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAQueryCallMonitor extends CSTARequest
 {
   static final int PDU = 128;
 
   public void encodeMembers(OutputStream memberStream)
   {
     ASNNull.encode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTAQueryCallMonitor ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNNull.print(indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 128;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryCallMonitor
 * JD-Core Version:    0.5.4
 */