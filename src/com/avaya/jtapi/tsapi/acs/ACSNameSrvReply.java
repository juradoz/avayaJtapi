/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSNameSrvReply extends ACSConfirmation
/*    */ {
/*    */   boolean more;
/*    */   ACSNameAddr[] list;
/*    */   public static final int PDU = 11;
/*    */ 
/*    */   public ACSNameSrvReply()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ACSNameSrvReply(boolean _more, ACSNameAddr[] _list)
/*    */   {
/* 18 */     this.more = _more;
/* 19 */     this.list = _list;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 24 */     ASNBoolean.encode(this.more, memberStream);
/* 25 */     ACSNameAddrList.encode(this.list, memberStream);
/*    */   }
/*    */ 
/*    */   public static ACSNameSrvReply decode(InputStream in)
/*    */   {
/* 30 */     ACSNameSrvReply _this = new ACSNameSrvReply();
/* 31 */     _this.doDecode(in);
/*    */ 
/* 33 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 38 */     this.more = ASNBoolean.decode(memberStream);
/* 39 */     this.list = ACSNameAddrList.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 45 */     Collection lines = new ArrayList();
/* 46 */     lines.add("ACSNameSrvReply ::=");
/* 47 */     lines.add("{");
/*    */ 
/* 49 */     String indent = "  ";
/*    */ 
/* 51 */     lines.addAll(ASNBoolean.print(this.more, "more", indent));
/* 52 */     lines.addAll(ACSNameAddrList.print(this.list, "list", indent));
/*    */ 
/* 54 */     lines.add("}");
/* 55 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 60 */     return 11;
/*    */   }
/*    */ 
/*    */   public ACSNameAddr[] getList()
/*    */   {
/* 66 */     return this.list;
/*    */   }
/*    */ 
/*    */   public boolean isMore()
/*    */   {
/* 74 */     return this.more;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSNameSrvReply
 * JD-Core Version:    0.5.4
 */