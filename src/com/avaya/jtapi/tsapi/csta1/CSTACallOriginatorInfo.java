/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTACallOriginatorInfo extends ASNSequence
/*    */ {
/*    */   boolean hasInfo;
/*    */   int callOriginatorType;
/*    */ 
/*    */   public static CSTACallOriginatorInfo decode(InputStream in)
/*    */   {
/* 21 */     CSTACallOriginatorInfo _this = new CSTACallOriginatorInfo();
/* 22 */     _this.doDecode(in);
/*    */ 
/* 24 */     if (_this.hasInfo) {
/* 25 */       return _this;
/*    */     }
/* 27 */     return null;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 31 */     ASNBoolean.encode(this.hasInfo, memberStream);
/* 32 */     ASNInteger.encode(this.callOriginatorType, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 36 */     this.hasInfo = ASNBoolean.decode(memberStream);
/* 37 */     this.callOriginatorType = ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(CSTACallOriginatorInfo _this, String name, String _indent)
/*    */   {
/* 42 */     Collection lines = new ArrayList();
/*    */ 
/* 44 */     if (_this == null)
/*    */     {
/* 46 */       lines.add(_indent + name + " <none>");
/* 47 */       return lines;
/*    */     }
/* 49 */     if (name != null) {
/* 50 */       lines.add(_indent + name);
/*    */     }
/* 52 */     lines.add(_indent + "{");
/*    */ 
/* 54 */     String indent = _indent + "  ";
/*    */ 
/* 56 */     lines.addAll(ASNBoolean.print(_this.hasInfo, "hasInfo", indent));
/* 57 */     lines.addAll(ASNInteger.print(_this.callOriginatorType, "callOriginatorType", indent));
/*    */ 
/* 59 */     lines.add(_indent + "}");
/* 60 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getCallOriginatorType()
/*    */   {
/* 67 */     return this.callOriginatorType;
/*    */   }
/*    */ 
/*    */   public void setCallOriginatorType(int _callOriginatorType)
/*    */   {
/* 72 */     this.callOriginatorType = _callOriginatorType;
/*    */   }
/*    */ 
/*    */   public void setHasInfo(boolean hasInfo) {
/* 76 */     this.hasInfo = hasInfo;
/*    */   }
/*    */ 
/*    */   public boolean isHasInfo()
/*    */   {
/* 83 */     return this.hasInfo;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTACallOriginatorInfo
 * JD-Core Version:    0.5.4
 */