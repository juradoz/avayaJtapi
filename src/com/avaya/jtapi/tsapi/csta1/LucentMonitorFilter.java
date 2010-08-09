 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentMonitorFilter extends LucentPrivateData
 {
   int privateFilter;
   static final int PDU = 29;
 
   LucentMonitorFilter(int _privateFilter)
   {
     this.privateFilter = _privateFilter;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     LucentPrivateFilter.encode(this.privateFilter, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentMonitorFilter ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(LucentPrivateFilter.print(this.privateFilter, "privateFilter", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 29;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMonitorFilter
 * JD-Core Version:    0.5.4
 */