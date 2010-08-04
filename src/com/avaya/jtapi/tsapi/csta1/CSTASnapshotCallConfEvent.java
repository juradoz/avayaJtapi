/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTASnapshotCallConfEvent extends CSTAConfirmation
/*    */ {
/*    */   CSTASnapshotCallResponseInfo[] snapshotData;
/*    */   public static final int PDU = 121;
/*    */ 
/*    */   public CSTASnapshotCallConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTASnapshotCallConfEvent(CSTASnapshotCallResponseInfo[] _snapshotData)
/*    */   {
/* 18 */     this.snapshotData = _snapshotData;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 23 */     CSTASnapshotCallData.encode(this.snapshotData, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTASnapshotCallConfEvent decode(InputStream in)
/*    */   {
/* 28 */     CSTASnapshotCallConfEvent _this = new CSTASnapshotCallConfEvent();
/* 29 */     _this.doDecode(in);
/*    */ 
/* 31 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 36 */     this.snapshotData = CSTASnapshotCallData.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 41 */     Collection lines = new ArrayList();
/*    */ 
/* 43 */     lines.add("CSTASnapshotCallConfEvent ::=");
/* 44 */     lines.add("{");
/*    */ 
/* 46 */     String indent = "  ";
/*    */ 
/* 48 */     lines.addAll(CSTASnapshotCallData.print(this.snapshotData, "snapshotData", indent));
/*    */ 
/* 50 */     lines.add("}");
/* 51 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 56 */     return 121;
/*    */   }
/*    */ 
/*    */   public CSTASnapshotCallResponseInfo[] getSnapshotData()
/*    */   {
/* 62 */     return this.snapshotData;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASnapshotCallConfEvent
 * JD-Core Version:    0.5.4
 */