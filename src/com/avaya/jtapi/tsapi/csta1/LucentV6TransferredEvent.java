/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentV6TransferredEvent extends LucentV5TransferredEvent
/*    */   implements LucentTrunkConnectionMapping
/*    */ {
/*    */   CSTATrunkInfo[] lucentTrunkInfo;
/*    */   static final int PDU = 106;
/*    */ 
/*    */   public static LucentTransferredEvent decode(InputStream in)
/*    */   {
/* 31 */     LucentV6TransferredEvent _this = new LucentV6TransferredEvent();
/* 32 */     _this.doDecode(in);
/*    */ 
/* 34 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 38 */     super.encodeMembers(memberStream);
/* 39 */     LucentTrunkInfoList.encode(this.lucentTrunkInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 44 */     super.decodeMembers(memberStream);
/* 45 */     this.lucentTrunkInfo = LucentTrunkInfoList.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public CSTATrunkInfo[] getLucentTrunkInfo()
/*    */   {
/* 51 */     return this.lucentTrunkInfo;
/*    */   }
/*    */ 
/*    */   public void setLucentTrunkInfo(CSTATrunkInfo[] _trunkList) {
/* 55 */     this.lucentTrunkInfo = _trunkList;
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 59 */     Collection lines = new ArrayList();
/* 60 */     lines.add("LucentV6TransferredEvent ::=");
/* 61 */     lines.add("{");
/*    */ 
/* 63 */     String indent = "  ";
/*    */ 
/* 65 */     lines.addAll(LucentV5OriginalCallInfo.print(this.originalCallInfo, "originalCallInfo", indent));
/*    */ 
/* 67 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/*    */ 
/* 69 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/* 70 */     lines.addAll(LucentTrunkInfoList.print(this.lucentTrunkInfo, "lucentTrunkInfo", indent));
/*    */ 
/* 72 */     lines.add("}");
/* 73 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 78 */     return 106;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6TransferredEvent
 * JD-Core Version:    0.5.4
 */