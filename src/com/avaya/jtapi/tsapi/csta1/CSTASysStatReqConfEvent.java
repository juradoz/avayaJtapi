/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTASysStatReqConfEvent extends CSTAConfirmation
/*    */ {
/*    */   public static final int PDU = 99;
/*    */   private short systemStatus;
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 20 */     return 99;
/*    */   }
/*    */ 
/*    */   public static CSTASysStatReqConfEvent decode(InputStream in) {
/* 24 */     CSTASysStatReqConfEvent _this = new CSTASysStatReqConfEvent();
/* 25 */     _this.doDecode(in);
/*    */ 
/* 27 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 32 */     this.systemStatus = SystemStatus.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 37 */     SystemStatus.encode(this.systemStatus, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 42 */     Collection lines = new ArrayList();
/* 43 */     lines.add("CSTASysStatReqConfEvent ::=");
/* 44 */     lines.add("{");
/* 45 */     String indent = "  ";
/* 46 */     lines.addAll(SystemStatus.print(this.systemStatus, "systemStatus", indent));
/* 47 */     lines.add("}");
/* 48 */     return lines;
/*    */   }
/*    */ 
/*    */   public short getSystemStatus() {
/* 52 */     return this.systemStatus;
/*    */   }
/*    */   public void setSystemStatus(short systemStatus) {
/* 55 */     this.systemStatus = systemStatus;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASysStatReqConfEvent
 * JD-Core Version:    0.5.4
 */