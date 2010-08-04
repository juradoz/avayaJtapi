/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentV6ConferencedEvent extends LucentV5ConferencedEvent
/*    */   implements LucentTrunkConnectionMapping
/*    */ {
/*    */   CSTATrunkInfo[] lucentTrunkInfo;
/*    */   static final int PDU = 107;
/*    */ 
/*    */   public static LucentConferencedEvent decode(InputStream in)
/*    */   {
/* 31 */     LucentV6ConferencedEvent _this = new LucentV6ConferencedEvent();
/* 32 */     _this.doDecode(in);
/*    */ 
/* 34 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 39 */     super.encodeMembers(memberStream);
/* 40 */     LucentTrunkInfoList.encode(this.lucentTrunkInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 46 */     super.decodeMembers(memberStream);
/* 47 */     this.lucentTrunkInfo = LucentTrunkInfoList.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public CSTATrunkInfo[] getLucentTrunkInfo()
/*    */   {
/* 54 */     return this.lucentTrunkInfo;
/*    */   }
/*    */ 
/*    */   public void setLucentTrunkInfo(CSTATrunkInfo[] _trunkList) {
/* 58 */     this.lucentTrunkInfo = _trunkList;
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 62 */     Collection lines = new ArrayList();
/* 63 */     lines.add("LucentV6ConferencedEvent ::=");
/* 64 */     lines.add("{");
/*    */ 
/* 66 */     String indent = "  ";
/*    */ 
/* 68 */     lines.addAll(LucentV5OriginalCallInfo.print(this.originalCallInfo, "originalCallInfo", indent));
/*    */ 
/* 70 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/*    */ 
/* 72 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/* 73 */     lines.addAll(LucentTrunkInfoList.print(this.lucentTrunkInfo, "lucentTrunkInfo", indent));
/*    */ 
/* 75 */     lines.add("}");
/* 76 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 81 */     return 107;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6ConferencedEvent
 * JD-Core Version:    0.5.4
 */