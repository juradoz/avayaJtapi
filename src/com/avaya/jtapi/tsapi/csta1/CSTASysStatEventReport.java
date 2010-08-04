/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTASysStatEventReport extends CSTAEventReport
/*    */ {
/*    */   public static final int PDU = 106;
/*    */   private short state;
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 22 */     return 106;
/*    */   }
/*    */ 
/*    */   public static CSTASysStatEventReport decode(InputStream in) {
/* 26 */     CSTASysStatEventReport _this = new CSTASysStatEventReport();
/* 27 */     _this.doDecode(in);
/* 28 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 32 */     SystemStatus.encode(this.state, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 36 */     this.state = SystemStatus.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 40 */     Collection lines = new ArrayList();
/*    */ 
/* 42 */     lines.add("CSTASysStatEvent ::=");
/* 43 */     lines.add("{");
/*    */ 
/* 45 */     String indent = "  ";
/* 46 */     lines.addAll(SystemStatus.print(this.state, "state", indent));
/* 47 */     lines.add("}");
/* 48 */     return lines;
/*    */   }
/*    */ 
/*    */   public short getState() {
/* 52 */     return this.state;
/*    */   }
/*    */ 
/*    */   public void setState(short state) {
/* 56 */     this.state = state;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASysStatEventReport
 * JD-Core Version:    0.5.4
 */