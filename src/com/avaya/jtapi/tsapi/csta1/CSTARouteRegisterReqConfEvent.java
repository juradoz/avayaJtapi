/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTARouteRegisterReqConfEvent extends CSTAConfirmation
/*    */ {
/*    */   int registerReqID;
/*    */   public static final int PDU = 79;
/*    */ 
/*    */   public static CSTARouteRegisterReqConfEvent decode(InputStream in)
/*    */   {
/* 17 */     CSTARouteRegisterReqConfEvent _this = new CSTARouteRegisterReqConfEvent();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.registerReqID = RouteRegisterReqID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 29 */     RouteRegisterReqID.encode(this.registerReqID, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 34 */     Collection lines = new ArrayList();
/*    */ 
/* 36 */     lines.add("CSTARouteRegisterReqConfEvent ::=");
/* 37 */     lines.add("{");
/*    */ 
/* 39 */     String indent = "  ";
/*    */ 
/* 41 */     lines.addAll(RouteRegisterReqID.print(this.registerReqID, "registerReqID", indent));
/*    */ 
/* 43 */     lines.add("}");
/* 44 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 49 */     return 79;
/*    */   }
/*    */ 
/*    */   public int getRegisterReqID()
/*    */   {
/* 55 */     return this.registerReqID;
/*    */   }
/*    */ 
/*    */   public void setRegisterReqID(int registerReqID) {
/* 59 */     this.registerReqID = registerReqID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterReqConfEvent
 * JD-Core Version:    0.5.4
 */