/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentMonitorCallConfEvent extends LucentPrivateData
/*    */ {
/*    */   int usedFilter;
/*    */   CSTASnapshotCallResponseInfo[] snapshotCall;
/*    */   public static final int PDU = 33;
/*    */ 
/*    */   static LucentMonitorCallConfEvent decode(InputStream in)
/*    */   {
/* 16 */     LucentMonitorCallConfEvent _this = new LucentMonitorCallConfEvent();
/* 17 */     _this.doDecode(in);
/*    */ 
/* 19 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 24 */     this.usedFilter = LucentPrivateFilter.decode(memberStream);
/* 25 */     this.snapshotCall = LucentSnapshotCall.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 29 */     LucentPrivateFilter.encode(this.usedFilter, memberStream);
/* 30 */     LucentSnapshotCall.encode(this.snapshotCall, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 34 */     Collection lines = new ArrayList();
/*    */ 
/* 36 */     lines.add("LucentMonitorCallConfEvent ::=");
/* 37 */     lines.add("{");
/*    */ 
/* 39 */     String indent = "  ";
/*    */ 
/* 41 */     lines.addAll(LucentPrivateFilter.print(this.usedFilter, "usedFilter", indent));
/* 42 */     lines.addAll(LucentSnapshotCall.print(this.snapshotCall, "snapshotCall", indent));
/*    */ 
/* 44 */     lines.add("}");
/* 45 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 50 */     return 33;
/*    */   }
/*    */   public CSTASnapshotCallResponseInfo[] getSnapshotCall() {
/* 53 */     return this.snapshotCall;
/*    */   }
/*    */ 
/*    */   public void setSnapshotCall(CSTASnapshotCallResponseInfo[] snapshotCall) {
/* 57 */     this.snapshotCall = snapshotCall;
/*    */   }
/*    */ 
/*    */   public int getUsedFilter() {
/* 61 */     return this.usedFilter;
/*    */   }
/*    */ 
/*    */   public void setUsedFilter(int usedFilter) {
/* 65 */     this.usedFilter = usedFilter;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMonitorCallConfEvent
 * JD-Core Version:    0.5.4
 */