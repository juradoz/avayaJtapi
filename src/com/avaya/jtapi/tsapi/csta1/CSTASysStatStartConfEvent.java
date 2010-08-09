 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class CSTASysStatStartConfEvent extends CSTAConfirmation
 {
   private int filter;
   public static final int PDU = 101;
 
   public int getPDU()
   {
     return 101;
   }
 
   public static CSTASysStatStartConfEvent decode(InputStream in)
   {
     CSTASysStatStartConfEvent _this = new CSTASysStatStartConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.filter = SystemStatusFilter.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     SystemStatusFilter.encode(this.filter, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTASysStatStartConfEvent ::=");
     lines.add("{");
     String indent = "  ";
     lines.addAll(SystemStatusFilter.print(this.filter, "filter", indent));
     lines.add("}");
     return lines;
   }
 
   public int getFilter() {
     return this.filter;
   }
 
   public void setFilter(int filter) {
     this.filter = filter;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASysStatStartConfEvent
 * JD-Core Version:    0.5.4
 */