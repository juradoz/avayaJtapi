/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTASnapshotDevice extends CSTARequest
/*    */ {
/*    */   String snapshotObject;
/*    */   public static final int PDU = 122;
/*    */ 
/*    */   public CSTASnapshotDevice(String _snapshotObject)
/*    */   {
/* 17 */     this.snapshotObject = _snapshotObject;
/*    */   }
/*    */ 
/*    */   public CSTASnapshotDevice()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 25 */     DeviceID.encode(this.snapshotObject, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTASnapshotDevice decode(InputStream in)
/*    */   {
/* 30 */     CSTASnapshotDevice _this = new CSTASnapshotDevice();
/* 31 */     _this.doDecode(in);
/*    */ 
/* 33 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 40 */     this.snapshotObject = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 45 */     Collection lines = new ArrayList();
/*    */ 
/* 47 */     lines.add("CSTASnapshotDevice ::=");
/* 48 */     lines.add("{");
/*    */ 
/* 50 */     String indent = "  ";
/*    */ 
/* 52 */     lines.addAll(DeviceID.print(this.snapshotObject, "snapshotObject", indent));
/*    */ 
/* 54 */     lines.add("}");
/* 55 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 60 */     return 122;
/*    */   }
/*    */ 
/*    */   public String getSnapshotObject()
/*    */   {
/* 66 */     return this.snapshotObject;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASnapshotDevice
 * JD-Core Version:    0.5.4
 */