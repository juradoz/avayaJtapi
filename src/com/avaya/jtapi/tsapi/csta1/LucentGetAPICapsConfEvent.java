/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentGetAPICapsConfEvent extends LucentPrivateData
/*    */ {
/*    */   String switchVersion;
/*    */   boolean sendDTMFTone;
/*    */   boolean enteredDigitsEvent;
/*    */   boolean queryDeviceName;
/*    */   boolean queryAgentMeas;
/*    */   boolean querySplitSkillMeas;
/*    */   boolean queryTrunkGroupMeas;
/*    */   boolean queryVdnMeas;
/*    */   boolean reserved1;
/*    */   boolean reserved2;
/*    */   static final int PDU = 64;
/*    */ 
/*    */   static LucentGetAPICapsConfEvent decode(InputStream in)
/*    */   {
/* 25 */     LucentGetAPICapsConfEvent _this = new LucentGetAPICapsConfEvent();
/* 26 */     _this.doDecode(in);
/*    */ 
/* 28 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 33 */     this.switchVersion = ASNIA5String.decode(memberStream);
/* 34 */     this.sendDTMFTone = ASNBoolean.decode(memberStream);
/* 35 */     this.enteredDigitsEvent = ASNBoolean.decode(memberStream);
/* 36 */     this.queryDeviceName = ASNBoolean.decode(memberStream);
/* 37 */     this.queryAgentMeas = ASNBoolean.decode(memberStream);
/* 38 */     this.querySplitSkillMeas = ASNBoolean.decode(memberStream);
/* 39 */     this.queryTrunkGroupMeas = ASNBoolean.decode(memberStream);
/* 40 */     this.queryVdnMeas = ASNBoolean.decode(memberStream);
/* 41 */     this.reserved1 = ASNBoolean.decode(memberStream);
/* 42 */     this.reserved2 = ASNBoolean.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 47 */     Collection lines = new ArrayList();
/*    */ 
/* 49 */     lines.add("LucentGetAPICapsConfEvent ::=");
/* 50 */     lines.add("{");
/*    */ 
/* 52 */     String indent = "  ";
/*    */ 
/* 54 */     lines.addAll(ASNIA5String.print(this.switchVersion, "switchVersion", indent));
/* 55 */     lines.addAll(ASNBoolean.print(this.sendDTMFTone, "sendDTMFTone", indent));
/* 56 */     lines.addAll(ASNBoolean.print(this.enteredDigitsEvent, "enteredDigitsEvent", indent));
/* 57 */     lines.addAll(ASNBoolean.print(this.queryDeviceName, "queryDeviceName", indent));
/* 58 */     lines.addAll(ASNBoolean.print(this.queryAgentMeas, "queryAgentMeas", indent));
/* 59 */     lines.addAll(ASNBoolean.print(this.querySplitSkillMeas, "querySplitSkillMeas", indent));
/* 60 */     lines.addAll(ASNBoolean.print(this.queryTrunkGroupMeas, "queryTrunkGroupMeas", indent));
/* 61 */     lines.addAll(ASNBoolean.print(this.queryVdnMeas, "queryVdnMeas", indent));
/* 62 */     lines.addAll(ASNBoolean.print(this.reserved1, "reserved1", indent));
/* 63 */     lines.addAll(ASNBoolean.print(this.reserved2, "reserved2", indent));
/*    */ 
/* 65 */     lines.add("}");
/* 66 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 71 */     return 64;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentGetAPICapsConfEvent
 * JD-Core Version:    0.5.4
 */