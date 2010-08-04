/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentConferencedEvent extends LucentPrivateData
/*    */ {
/*    */   LucentOriginalCallInfo originalCallInfo;
/*    */   CSTAExtendedDeviceID distributingDevice_asn;
/*    */   static final int PDU = 59;
/*    */ 
/*    */   static LucentConferencedEvent decode(InputStream in)
/*    */   {
/* 18 */     LucentConferencedEvent _this = new LucentConferencedEvent();
/* 19 */     _this.doDecode(in);
/*    */ 
/* 21 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 26 */     encodeOCI(this.originalCallInfo, memberStream);
/* 27 */     CSTAExtendedDeviceID.encode(this.distributingDevice_asn, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 32 */     this.originalCallInfo = decodeOCI(memberStream);
/* 33 */     this.distributingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 38 */     Collection lines = new ArrayList();
/*    */ 
/* 40 */     lines.add("LucentConferencedEvent ::=");
/* 41 */     lines.add("{");
/*    */ 
/* 43 */     String indent = "  ";
/*    */ 
/* 45 */     lines.addAll(LucentOriginalCallInfo.print(this.originalCallInfo, "originalCallInfo", indent));
/* 46 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/*    */ 
/* 48 */     lines.add("}");
/* 49 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 54 */     return 59;
/*    */   }
/*    */ 
/*    */   public CSTAExtendedDeviceID getDistributingDevice_asn()
/*    */   {
/* 60 */     return this.distributingDevice_asn;
/*    */   }
/*    */ 
/*    */   public void setDistributingDevice_asn(CSTAExtendedDeviceID _distributingDevice_asn) {
/* 64 */     this.distributingDevice_asn = _distributingDevice_asn;
/*    */   }
/*    */ 
/*    */   public LucentOriginalCallInfo getOriginalCallInfo()
/*    */   {
/* 71 */     return this.originalCallInfo;
/*    */   }
/*    */ 
/*    */   public void setOriginalCallInfo(LucentOriginalCallInfo _info) {
/* 75 */     this.originalCallInfo = _info;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentConferencedEvent
 * JD-Core Version:    0.5.4
 */