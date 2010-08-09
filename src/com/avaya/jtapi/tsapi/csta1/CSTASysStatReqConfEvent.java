 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class CSTASysStatReqConfEvent extends CSTAConfirmation
 {
   public static final int PDU = 99;
   private short systemStatus;
 
   public int getPDU()
   {
     return 99;
   }
 
   public static CSTASysStatReqConfEvent decode(InputStream in) {
     CSTASysStatReqConfEvent _this = new CSTASysStatReqConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.systemStatus = SystemStatus.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     SystemStatus.encode(this.systemStatus, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTASysStatReqConfEvent ::=");
     lines.add("{");
     String indent = "  ";
     lines.addAll(SystemStatus.print(this.systemStatus, "systemStatus", indent));
     lines.add("}");
     return lines;
   }
 
   public short getSystemStatus() {
     return this.systemStatus;
   }
   public void setSystemStatus(short systemStatus) {
     this.systemStatus = systemStatus;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASysStatReqConfEvent
 * JD-Core Version:    0.5.4
 */