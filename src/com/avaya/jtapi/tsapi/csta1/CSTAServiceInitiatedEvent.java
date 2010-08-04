/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAServiceInitiatedEvent extends CSTAUnsolicited
/*    */ {
/*    */   CSTAConnectionID initiatedConnection;
/*    */   short localConnectionInfo;
/*    */   short cause;
/*    */   public static final int PDU = 66;
/*    */ 
/*    */   public static CSTAServiceInitiatedEvent decode(InputStream in)
/*    */   {
/* 19 */     CSTAServiceInitiatedEvent _this = new CSTAServiceInitiatedEvent();
/* 20 */     _this.doDecode(in);
/*    */ 
/* 22 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 27 */     this.initiatedConnection = CSTAConnectionID.decode(memberStream);
/* 28 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/* 29 */     this.cause = CSTAEventCause.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 33 */     CSTAConnectionID.encode(this.initiatedConnection, memberStream);
/* 34 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/* 35 */     CSTAEventCause.encode(this.cause, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 40 */     Collection lines = new ArrayList();
/*    */ 
/* 42 */     lines.add("CSTAServiceInitiatedEvent ::=");
/* 43 */     lines.add("{");
/*    */ 
/* 45 */     String indent = "  ";
/* 46 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/* 47 */     lines.addAll(CSTAConnectionID.print(this.initiatedConnection, "initiatedConnection", indent));
/* 48 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/* 49 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*    */ 
/* 51 */     lines.add("}");
/* 52 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 57 */     return 66;
/*    */   }
/*    */ 
/*    */   public short getCause()
/*    */   {
/* 63 */     return this.cause;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getInitiatedConnection()
/*    */   {
/* 71 */     return this.initiatedConnection;
/*    */   }
/*    */ 
/*    */   public short getLocalConnectionInfo()
/*    */   {
/* 79 */     return this.localConnectionInfo;
/*    */   }
/*    */ 
/*    */   public void setCause(short cause) {
/* 83 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */   public void setInitiatedConnection(CSTAConnectionID initiatedConnection) {
/* 87 */     this.initiatedConnection = initiatedConnection;
/*    */   }
/*    */ 
/*    */   public void setLocalConnectionInfo(short localConnectionInfo) {
/* 91 */     this.localConnectionInfo = localConnectionInfo;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAServiceInitiatedEvent
 * JD-Core Version:    0.5.4
 */