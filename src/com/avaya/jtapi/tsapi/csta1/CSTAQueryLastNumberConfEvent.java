 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAQueryLastNumberConfEvent extends CSTAConfirmation
 {
   String lastNumber;
   static final int PDU = 36;
 
   public static CSTAQueryLastNumberConfEvent decode(InputStream in)
   {
     CSTAQueryLastNumberConfEvent _this = new CSTAQueryLastNumberConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.lastNumber = DeviceID.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTAQueryLastNumberConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.lastNumber, "lastNumber", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 36;
   }
 
   public String getLastNumber()
   {
     return this.lastNumber;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryLastNumberConfEvent
 * JD-Core Version:    0.5.4
 */