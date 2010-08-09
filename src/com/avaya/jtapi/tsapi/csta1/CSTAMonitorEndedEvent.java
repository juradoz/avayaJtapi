 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAMonitorEndedEvent extends CSTAUnsolicited
 {
   short cause;
   public static final int PDU = 119;
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAEventCause.encode(this.cause, memberStream);
   }
 
   public static CSTAMonitorEndedEvent decode(InputStream in)
   {
     CSTAMonitorEndedEvent _this = new CSTAMonitorEndedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.cause = CSTAEventCause.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTAMonitorEndedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 119;
   }
 
   public short getCause()
   {
     return this.cause;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMonitorEndedEvent
 * JD-Core Version:    0.5.4
 */