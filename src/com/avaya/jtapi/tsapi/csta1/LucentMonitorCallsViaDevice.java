/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentMonitorCallsViaDevice extends LucentPrivateData
/*    */ {
/*    */   boolean flowPredictiveCallEvents;
/*    */   int privateFilter;
/*    */   static final int PDU = 144;
/*    */ 
/*    */   public LucentMonitorCallsViaDevice(boolean _flowPredictiveCallEvents, int _privateFilter)
/*    */   {
/* 16 */     this.flowPredictiveCallEvents = _flowPredictiveCallEvents;
/* 17 */     this.privateFilter = _privateFilter;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 22 */     LucentPrivateFilter.encode(this.privateFilter, memberStream);
/* 23 */     ASNBoolean.encode(this.flowPredictiveCallEvents, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 28 */     Collection lines = new ArrayList();
/*    */ 
/* 30 */     lines.add("LucentMonitorCallsViaDevice ::=");
/* 31 */     lines.add("{");
/*    */ 
/* 33 */     String indent = "  ";
/*    */ 
/* 35 */     lines.addAll(LucentPrivateFilter.print(this.privateFilter, "privateFilter", indent));
/* 36 */     lines.addAll(ASNBoolean.print(this.flowPredictiveCallEvents, "flowPredictiveCallEvents", indent));
/*    */ 
/* 38 */     lines.add("}");
/* 39 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 44 */     return 144;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMonitorCallsViaDevice
 * JD-Core Version:    0.5.4
 */