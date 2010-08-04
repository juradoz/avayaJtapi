/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTASnapshotCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID snapshotObject;
/*    */   public static final int PDU = 120;
/*    */ 
/*    */   public CSTASnapshotCall(CSTAConnectionID _snapshotObject)
/*    */   {
/* 17 */     this.snapshotObject = _snapshotObject;
/*    */   }
/*    */ 
/*    */   public CSTASnapshotCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 25 */     CSTAConnectionID.encode(this.snapshotObject, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTASnapshotCall decode(InputStream in)
/*    */   {
/* 30 */     CSTASnapshotCall _this = new CSTASnapshotCall();
/* 31 */     _this.doDecode(in);
/*    */ 
/* 33 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 38 */     this.snapshotObject = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 43 */     Collection lines = new ArrayList();
/*    */ 
/* 45 */     lines.add("CSTASnapshotCall ::=");
/* 46 */     lines.add("{");
/*    */ 
/* 48 */     String indent = "  ";
/*    */ 
/* 50 */     lines.addAll(CSTAConnectionID.print(this.snapshotObject, "snapshotObject", indent));
/*    */ 
/* 52 */     lines.add("}");
/* 53 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 58 */     return 120;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getSnapshotObject()
/*    */   {
/* 64 */     return this.snapshotObject;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASnapshotCall
 * JD-Core Version:    0.5.4
 */