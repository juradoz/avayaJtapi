/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentV5ConferencedEvent extends LucentConferencedEvent
/*    */   implements HasUCID
/*    */ {
/*    */   String ucid;
/*    */   static final int PDU = 78;
/*    */ 
/*    */   public static LucentConferencedEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentV5ConferencedEvent _this = new LucentV5ConferencedEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 23 */     super.encodeMembers(memberStream);
/* 24 */     UCID.encode(this.ucid, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 29 */     super.decodeMembers(memberStream);
/* 30 */     this.ucid = UCID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeOCI(LucentOriginalCallInfo callInfo, OutputStream memberStream) {
/* 34 */     LucentV5OriginalCallInfo.encode(callInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public LucentOriginalCallInfo decodeOCI(InputStream memberStream)
/*    */   {
/* 39 */     return LucentV5OriginalCallInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 44 */     Collection lines = new ArrayList();
/*    */ 
/* 46 */     lines.add("LucentV5ConferencedEvent ::=");
/* 47 */     lines.add("{");
/*    */ 
/* 49 */     String indent = "  ";
/*    */ 
/* 51 */     lines.addAll(LucentV5OriginalCallInfo.print((LucentV5OriginalCallInfo)this.originalCallInfo, "originalCallInfo", indent));
/* 52 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/* 53 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/*    */ 
/* 55 */     lines.add("}");
/* 56 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 61 */     return 78;
/*    */   }
/*    */ 
/*    */   public String getUcid()
/*    */   {
/* 67 */     return this.ucid;
/*    */   }
/*    */ 
/*    */   public void setUcid(String ucid) {
/* 71 */     this.ucid = ucid;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5ConferencedEvent
 * JD-Core Version:    0.5.4
 */