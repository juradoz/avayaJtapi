/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAForwardingInfo extends ASNSequence
/*    */ {
/*    */   short forwardingType;
/*    */   boolean forwardingOn;
/*    */   String forwardDN;
/*    */ 
/*    */   public CSTAForwardingInfo()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAForwardingInfo(short _forwardingType, boolean _forwardingOn, String _forwardDN)
/*    */   {
/* 25 */     this.forwardingType = _forwardingType;
/* 26 */     this.forwardingOn = _forwardingOn;
/* 27 */     this.forwardDN = _forwardDN;
/*    */   }
/*    */ 
/*    */   public static CSTAForwardingInfo decode(InputStream in)
/*    */   {
/* 32 */     CSTAForwardingInfo _this = new CSTAForwardingInfo();
/* 33 */     _this.doDecode(in);
/*    */ 
/* 35 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 42 */     this.forwardingType = ForwardingType.decode(memberStream);
/* 43 */     this.forwardingOn = ASNBoolean.decode(memberStream);
/* 44 */     this.forwardDN = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 49 */     ForwardingType.encode(this.forwardingType, memberStream);
/* 50 */     ASNBoolean.encode(this.forwardingOn, memberStream);
/* 51 */     DeviceID.encode(this.forwardDN, memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(CSTAForwardingInfo _this, String name, String _indent)
/*    */   {
/* 56 */     Collection lines = new ArrayList();
/*    */ 
/* 58 */     if (_this == null)
/*    */     {
/* 60 */       lines.add(_indent + name + " <null>");
/* 61 */       return lines;
/*    */     }
/* 63 */     if (name != null) {
/* 64 */       lines.add(_indent + name);
/*    */     }
/* 66 */     lines.add(_indent + "{");
/*    */ 
/* 68 */     String indent = _indent + "  ";
/*    */ 
/* 70 */     lines.addAll(ForwardingType.print(_this.forwardingType, "forwardingType", indent));
/* 71 */     lines.addAll(ASNBoolean.print(_this.forwardingOn, "forwardingOn", indent));
/* 72 */     lines.addAll(DeviceID.print(_this.forwardDN, "forwardDN", indent));
/*    */ 
/* 74 */     lines.add(_indent + "}");
/* 75 */     return lines;
/*    */   }
/*    */ 
/*    */   public String getForwardDN()
/*    */   {
/* 82 */     return this.forwardDN;
/*    */   }
/*    */ 
/*    */   public boolean isForwardingOn()
/*    */   {
/* 90 */     return this.forwardingOn;
/*    */   }
/*    */ 
/*    */   public short getForwardingType()
/*    */   {
/* 98 */     return this.forwardingType;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAForwardingInfo
 * JD-Core Version:    0.5.4
 */