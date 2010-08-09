 package com.avaya.jtapi.tsapi.acs;
 
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class ACSSetHeartbeatIntervalConfEvent extends ACSConfirmation
 {
   short heartbeatInterval;
   public static final int PDU = 15;
 
   public void setHeartbeatInterval(short heartbeatInterval)
   {
     this.heartbeatInterval = heartbeatInterval;
   }
 
   public ACSSetHeartbeatIntervalConfEvent(short heartbeatInterval)
   {
     this.heartbeatInterval = heartbeatInterval;
   }
 
   public ACSSetHeartbeatIntervalConfEvent()
   {
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     ASNInteger.encode(this.heartbeatInterval, memberStream);
   }
 
   public static ACSSetHeartbeatIntervalConfEvent decode(InputStream in)
   {
     ACSSetHeartbeatIntervalConfEvent _this = new ACSSetHeartbeatIntervalConfEvent();
 
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.heartbeatInterval = (short)ASNInteger.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("ACSSetHeartbeatIntervalConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNInteger.print(this.heartbeatInterval, "heartbeatInterval", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 15;
   }
 
   public synchronized short getHeartbeatInterval()
   {
     return this.heartbeatInterval;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSSetHeartbeatIntervalConfEvent
 * JD-Core Version:    0.5.4
 */