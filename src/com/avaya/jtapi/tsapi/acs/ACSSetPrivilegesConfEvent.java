/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSSetPrivilegesConfEvent extends ACSConfirmation
/*    */ {
/*    */   public static final int PDU = 20;
/*    */ 
/*    */   public static ACSSetPrivilegesConfEvent decode(InputStream in)
/*    */   {
/* 41 */     ACSSetPrivilegesConfEvent _this = new ACSSetPrivilegesConfEvent();
/*    */ 
/* 43 */     _this.doDecode(in);
/*    */ 
/* 45 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 59 */     Collection lines = new ArrayList();
/* 60 */     lines.add("ACSSetPrivilegesConfEvent ::=");
/* 61 */     lines.add("{");
/* 62 */     lines.add("}");
/* 63 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 68 */     return 20;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSSetPrivilegesConfEvent
 * JD-Core Version:    0.5.4
 */