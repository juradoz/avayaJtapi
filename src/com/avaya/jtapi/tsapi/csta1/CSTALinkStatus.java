/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTALinkStatus extends ASNSequence
/*    */ {
/*    */   private int linkID;
/*    */   private short linkState;
/*    */ 
/*    */   static CSTALinkStatus decode(InputStream in)
/*    */   {
/* 20 */     CSTALinkStatus _this = new CSTALinkStatus();
/* 21 */     _this.doDecode(in);
/*    */ 
/* 23 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 27 */     this.linkID = ASNInteger.decode(memberStream);
/* 28 */     this.linkState = LinkState.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 32 */     ASNInteger.encode(this.linkID, memberStream);
/* 33 */     LinkState.encode(this.linkState, memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(CSTALinkStatus _this, String name, String _indent)
/*    */   {
/* 38 */     Collection lines = new ArrayList();
/* 39 */     if (_this == null) {
/* 40 */       lines.add(_indent + name + " <null>");
/* 41 */       return lines;
/*    */     }
/* 43 */     if (name != null) {
/* 44 */       lines.add(_indent + name);
/*    */     }
/* 46 */     lines.add(_indent + "{");
/*    */ 
/* 48 */     String indent = _indent + "  ";
/*    */ 
/* 50 */     lines.addAll(ASNInteger.print(_this.linkID, "linkID", indent));
/* 51 */     lines.addAll(LinkState.print(_this.linkState, "linkState", indent));
/*    */ 
/* 53 */     lines.add(_indent + "}");
/* 54 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getLinkID() {
/* 58 */     return this.linkID;
/*    */   }
/*    */ 
/*    */   public void setLinkID(int linkID) {
/* 62 */     this.linkID = linkID;
/*    */   }
/*    */ 
/*    */   public short getLinkState() {
/* 66 */     return this.linkState;
/*    */   }
/*    */ 
/*    */   public void setLinkState(short linkState) {
/* 70 */     this.linkState = linkState;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTALinkStatus
 * JD-Core Version:    0.5.4
 */