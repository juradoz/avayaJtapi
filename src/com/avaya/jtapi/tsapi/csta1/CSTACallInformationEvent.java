/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTACallInformationEvent extends CSTAUnsolicited
/*    */ {
/*    */   CSTAConnectionID connection;
/*    */   CSTAExtendedDeviceID device;
/*    */   String accountInfo;
/*    */   String authorisationCode;
/*    */   static final int PDU = 68;
/*    */ 
/*    */   public static CSTACallInformationEvent decode(InputStream in)
/*    */   {
/* 19 */     CSTACallInformationEvent _this = new CSTACallInformationEvent();
/* 20 */     _this.doDecode(in);
/*    */ 
/* 22 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 27 */     this.connection = CSTAConnectionID.decode(memberStream);
/* 28 */     this.device = CSTAExtendedDeviceID.decode(memberStream);
/* 29 */     this.accountInfo = AccountInfo.decode(memberStream);
/* 30 */     this.authorisationCode = AuthCode.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 35 */     Collection lines = new ArrayList();
/* 36 */     lines.add("CSTACallInformationEvent ::=");
/* 37 */     lines.add("{");
/*    */ 
/* 39 */     String indent = "  ";
/* 40 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/* 41 */     lines.addAll(CSTAConnectionID.print(this.connection, "connection", indent));
/* 42 */     lines.addAll(CSTAExtendedDeviceID.print(this.device, "device", indent));
/* 43 */     lines.addAll(AccountInfo.print(this.accountInfo, "accountInfo", indent));
/* 44 */     lines.addAll(AuthCode.print(this.authorisationCode, "authorisationCode", indent));
/*    */ 
/* 46 */     lines.add("}");
/* 47 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 52 */     return 68;
/*    */   }
/*    */ 
/*    */   public String getAccountInfo()
/*    */   {
/* 58 */     return this.accountInfo;
/*    */   }
/*    */ 
/*    */   public String getAuthorisationCode()
/*    */   {
/* 66 */     return this.authorisationCode;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getConnection()
/*    */   {
/* 74 */     return this.connection;
/*    */   }
/*    */ 
/*    */   public CSTAExtendedDeviceID getDevice()
/*    */   {
/* 82 */     return this.device;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTACallInformationEvent
 * JD-Core Version:    0.5.4
 */