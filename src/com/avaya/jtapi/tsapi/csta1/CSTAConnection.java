/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAConnection extends ASNSequence
/*    */ {
/*    */   CSTAConnectionID party;
/*    */   CSTAExtendedDeviceID staticDevice;
/*    */ 
/*    */   public CSTAConnection()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAConnection(CSTAConnectionID _party, CSTAExtendedDeviceID _staticDevice)
/*    */   {
/* 22 */     this.party = _party;
/* 23 */     this.staticDevice = _staticDevice;
/*    */   }
/*    */ 
/*    */   public static CSTAConnection decode(InputStream in)
/*    */   {
/* 28 */     CSTAConnection _this = new CSTAConnection();
/* 29 */     _this.doDecode(in);
/*    */ 
/* 31 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 38 */     this.party = CSTAConnectionID.decode(memberStream);
/* 39 */     this.staticDevice = CSTAExtendedDeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 44 */     CSTAConnectionID.encode(this.party, memberStream);
/* 45 */     CSTAExtendedDeviceID.encode(this.staticDevice, memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(CSTAConnection _this, String name, String _indent)
/*    */   {
/* 50 */     Collection lines = new ArrayList();
/* 51 */     if (_this == null)
/*    */     {
/* 53 */       lines.add(_indent + name + " <null>");
/* 54 */       return lines;
/*    */     }
/* 56 */     if (name != null) {
/* 57 */       lines.add(_indent + name);
/*    */     }
/* 59 */     lines.add(_indent + "{");
/*    */ 
/* 61 */     String indent = _indent + "  ";
/*    */ 
/* 63 */     lines.addAll(CSTAConnectionID.print(_this.party, "party", indent));
/* 64 */     lines.addAll(CSTAExtendedDeviceID.print(_this.staticDevice, "staticDevice", indent));
/*    */ 
/* 66 */     lines.add(_indent + "}");
/* 67 */     return lines;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getParty()
/*    */   {
/* 74 */     return this.party;
/*    */   }
/*    */ 
/*    */   public CSTAExtendedDeviceID getStaticDevice()
/*    */   {
/* 82 */     return this.staticDevice;
/*    */   }
/*    */ 
/*    */   public void setParty(CSTAConnectionID _party) {
/* 86 */     this.party = _party;
/*    */   }
/*    */ 
/*    */   public void setStaticDevice(CSTAExtendedDeviceID _staticDevice) {
/* 90 */     this.staticDevice = _staticDevice;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConnection
 * JD-Core Version:    0.5.4
 */