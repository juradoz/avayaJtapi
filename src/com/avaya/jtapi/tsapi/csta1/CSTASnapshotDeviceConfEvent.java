/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTASnapshotDeviceConfEvent extends CSTAConfirmation
/*    */ {
/*    */   CSTASnapshotDeviceResponseInfo[] snapshotData;
/*    */   public static final int PDU = 123;
/*    */ 
/*    */   public CSTASnapshotDeviceConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTASnapshotDeviceConfEvent(CSTASnapshotDeviceResponseInfo[] _snapshotData)
/*    */   {
/* 18 */     this.snapshotData = _snapshotData;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 23 */     CSTASnapshotDeviceData.encode(this.snapshotData, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTASnapshotDeviceConfEvent decode(InputStream in)
/*    */   {
/* 28 */     CSTASnapshotDeviceConfEvent _this = new CSTASnapshotDeviceConfEvent();
/* 29 */     _this.doDecode(in);
/*    */ 
/* 31 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 36 */     this.snapshotData = CSTASnapshotDeviceData.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 41 */     Collection lines = new ArrayList();
/*    */ 
/* 43 */     lines.add("CSTASnapshotDeviceConfEvent ::=");
/* 44 */     lines.add("{");
/*    */ 
/* 46 */     String indent = "  ";
/*    */ 
/* 48 */     lines.addAll(CSTASnapshotDeviceData.print(this.snapshotData, "snapshotData", indent));
/*    */ 
/* 50 */     lines.add("}");
/* 51 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 56 */     return 123;
/*    */   }
/*    */ 
/*    */   public CSTASnapshotDeviceResponseInfo[] getSnapshotData()
/*    */   {
/* 62 */     return this.snapshotData;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceConfEvent
 * JD-Core Version:    0.5.4
 */