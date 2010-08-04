/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSSetPrivileges extends ACSRequest
/*    */ {
/*    */   String xmlData;
/*    */   public static final int PDU = 19;
/*    */ 
/*    */   public ACSSetPrivileges(String _xmlData)
/*    */   {
/* 15 */     this.xmlData = _xmlData;
/*    */   }
/*    */ 
/*    */   public ACSSetPrivileges() {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 22 */     XmlData.encode(this.xmlData, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 27 */     this.xmlData = XmlData.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public static ACSSetPrivileges decode(InputStream in)
/*    */   {
/* 32 */     ACSSetPrivileges _this = new ACSSetPrivileges();
/* 33 */     _this.doDecode(in);
/*    */ 
/* 35 */     return _this;
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 42 */     Collection lines = new ArrayList();
/* 43 */     lines.add("ACSSetPrivileges ::=");
/* 44 */     lines.add("{");
/*    */ 
/* 46 */     String indent = "  ";
/*    */ 
/* 48 */     lines.addAll(XmlData.print(this.xmlData, "xmlData", indent));
/*    */ 
/* 50 */     lines.add("}");
/* 51 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 56 */     return 19;
/*    */   }
/*    */ 
/*    */   public String getxmlData() {
/* 60 */     return this.xmlData;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSSetPrivileges
 * JD-Core Version:    0.5.4
 */