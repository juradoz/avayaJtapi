 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentMonitorCallsViaDevice extends LucentPrivateData
 {
   boolean flowPredictiveCallEvents;
   int privateFilter;
   static final int PDU = 144;
 
   public LucentMonitorCallsViaDevice(boolean _flowPredictiveCallEvents, int _privateFilter)
   {
     this.flowPredictiveCallEvents = _flowPredictiveCallEvents;
     this.privateFilter = _privateFilter;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     LucentPrivateFilter.encode(this.privateFilter, memberStream);
     ASNBoolean.encode(this.flowPredictiveCallEvents, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentMonitorCallsViaDevice ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(LucentPrivateFilter.print(this.privateFilter, "privateFilter", indent));
     lines.addAll(ASNBoolean.print(this.flowPredictiveCallEvents, "flowPredictiveCallEvents", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 144;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMonitorCallsViaDevice
 * JD-Core Version:    0.5.4
 */