/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAConferenceCallConfEvent extends CSTAConfirmation
/*    */ {
/*    */   CSTAConnectionID newCall;
/*    */   CSTAConnection[] connList;
/*    */   public static final int PDU = 12;
/*    */ 
/*    */   public CSTAConferenceCallConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAConferenceCallConfEvent(CSTAConnectionID _newCall, CSTAConnection[] _connList)
/*    */   {
/* 18 */     this.newCall = _newCall;
/* 19 */     this.connList = _connList;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 24 */     CSTAConnectionID.encode(this.newCall, memberStream);
/* 25 */     ConnectionList.encode(this.connList, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAConferenceCallConfEvent decode(InputStream in)
/*    */   {
/* 30 */     CSTAConferenceCallConfEvent _this = new CSTAConferenceCallConfEvent();
/* 31 */     _this.doDecode(in);
/*    */ 
/* 33 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 38 */     this.newCall = CSTAConnectionID.decode(memberStream);
/* 39 */     this.connList = ConnectionList.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 44 */     Collection lines = new ArrayList();
/* 45 */     lines.add("CSTAConferenceCallConfEvent ::=");
/* 46 */     lines.add("{");
/*    */ 
/* 48 */     String indent = "  ";
/*    */ 
/* 50 */     lines.addAll(CSTAConnectionID.print(this.newCall, "newCall", indent));
/* 51 */     lines.addAll(ConnectionList.print(this.connList, "connList", indent));
/*    */ 
/* 53 */     lines.add("}");
/* 54 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 59 */     return 12;
/*    */   }
/*    */ 
/*    */   public CSTAConnection[] getConnList()
/*    */   {
/* 65 */     return this.connList;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getNewCall()
/*    */   {
/* 73 */     return this.newCall;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConferenceCallConfEvent
 * JD-Core Version:    0.5.4
 */