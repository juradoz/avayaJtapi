/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTATransferCallConfEvent extends CSTAConfirmation
/*    */ {
/*    */   CSTAConnectionID newCall;
/*    */   CSTAConnection[] connList;
/*    */   public static final int PDU = 52;
/*    */ 
/*    */   public CSTATransferCallConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTATransferCallConfEvent(CSTAConnectionID _newCall, CSTAConnection[] _connList)
/*    */   {
/* 17 */     this.newCall = _newCall;
/* 18 */     this.connList = _connList;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 22 */     CSTAConnectionID.encode(this.newCall, memberStream);
/* 23 */     ConnectionList.encode(this.connList, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTATransferCallConfEvent decode(InputStream in) {
/* 27 */     CSTATransferCallConfEvent _this = new CSTATransferCallConfEvent();
/* 28 */     _this.doDecode(in);
/*    */ 
/* 30 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 35 */     this.newCall = CSTAConnectionID.decode(memberStream);
/* 36 */     this.connList = ConnectionList.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 41 */     Collection lines = new ArrayList();
/*    */ 
/* 43 */     lines.add("CSTATransferCallConfEvent ::=");
/* 44 */     lines.add("{");
/*    */ 
/* 46 */     String indent = "  ";
/*    */ 
/* 48 */     lines.addAll(CSTAConnectionID.print(this.newCall, "newCall", indent));
/* 49 */     lines.addAll(ConnectionList.print(this.connList, "connList", indent));
/*    */ 
/* 51 */     lines.add("}");
/* 52 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 57 */     return 52;
/*    */   }
/*    */ 
/*    */   public CSTAConnection[] getConnList()
/*    */   {
/* 63 */     return this.connList;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getNewCall()
/*    */   {
/* 71 */     return this.newCall;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTATransferCallConfEvent
 * JD-Core Version:    0.5.4
 */