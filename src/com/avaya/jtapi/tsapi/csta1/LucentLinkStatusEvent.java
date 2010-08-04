/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentLinkStatusEvent extends LucentPrivateData
/*    */ {
/*    */   private CSTALinkStatus linkStatus;
/*    */   static final int PDU = 73;
/*    */ 
/*    */   public static LucentLinkStatusEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentLinkStatusEvent _this = new LucentLinkStatusEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 22 */     this.linkStatus = CSTALinkStatus.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 28 */     CSTALinkStatus.encode(this.linkStatus, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 34 */     Collection lines = new ArrayList();
/*    */ 
/* 36 */     lines.add("LucentLinkStatusEvent ::=");
/* 37 */     lines.add("{");
/*    */ 
/* 39 */     String indent = "  ";
/* 40 */     lines.addAll(CSTALinkStatus.print(this.linkStatus, "linkStatus", indent));
/*    */ 
/* 43 */     lines.add("}");
/* 44 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 50 */     return 73;
/*    */   }
/*    */ 
/*    */   public CSTALinkStatus getLinkStatus() {
/* 54 */     return this.linkStatus;
/*    */   }
/*    */ 
/*    */   public void setLinkStatus(CSTALinkStatus linkStatus) {
/* 58 */     this.linkStatus = linkStatus;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentLinkStatusEvent
 * JD-Core Version:    0.5.4
 */