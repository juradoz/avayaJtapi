 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAMonitorConfEvent extends CSTAConfirmation
 {
   int monitorCrossRefID;
   CSTAMonitorFilter monitorFilter;
   public static final int PDU = 114;
 
   public static CSTAMonitorConfEvent decode(InputStream in)
   {
     CSTAMonitorConfEvent _this = new CSTAMonitorConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream) {
     ASNInteger.encode(this.monitorCrossRefID, memberStream);
     CSTAMonitorFilter.encode(this.monitorFilter, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream) {
     this.monitorCrossRefID = CSTAMonitorCrossRefID.decode(memberStream);
     this.monitorFilter = CSTAMonitorFilter.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTAMonitorConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAMonitorCrossRefID.print(this.monitorCrossRefID, "monitorCrossRefID", indent));
     lines.addAll(CSTAMonitorFilter.print(this.monitorFilter, "monitorFilter", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 114;
   }
 
   public int getMonitorCrossRefID()
   {
     return this.monitorCrossRefID;
   }
 
   public CSTAMonitorFilter getMonitorFilter()
   {
     return this.monitorFilter;
   }
 
   public void setMonitorFilter(CSTAMonitorFilter filter)
   {
     this.monitorFilter = filter;
   }
   public void setMonitorCrossRefID(int crossRef) {
     this.monitorCrossRefID = crossRef;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMonitorConfEvent
 * JD-Core Version:    0.5.4
 */