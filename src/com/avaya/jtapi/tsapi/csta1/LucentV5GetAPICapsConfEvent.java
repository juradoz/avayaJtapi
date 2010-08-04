/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class LucentV5GetAPICapsConfEvent extends LucentGetAPICapsConfEvent
/*     */ {
/*     */   boolean singleStepConference;
/*     */   boolean selectiveListeningHold;
/*     */   boolean selectiveListeningRetrieve;
/*     */   boolean setBillingRate;
/*     */   boolean queryUCID;
/*     */   boolean chargeAdviceEvent;
/*     */   static final int PDU = 97;
/*     */ 
/*     */   static LucentGetAPICapsConfEvent decode(InputStream in)
/*     */   {
/*  22 */     LucentV5GetAPICapsConfEvent _this = new LucentV5GetAPICapsConfEvent();
/*  23 */     _this.doDecode(in);
/*     */ 
/*  25 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  30 */     this.switchVersion = ASNIA5String.decode(memberStream);
/*  31 */     this.sendDTMFTone = ASNBoolean.decode(memberStream);
/*  32 */     this.enteredDigitsEvent = ASNBoolean.decode(memberStream);
/*  33 */     this.queryDeviceName = ASNBoolean.decode(memberStream);
/*  34 */     this.queryAgentMeas = ASNBoolean.decode(memberStream);
/*  35 */     this.querySplitSkillMeas = ASNBoolean.decode(memberStream);
/*  36 */     this.queryTrunkGroupMeas = ASNBoolean.decode(memberStream);
/*  37 */     this.queryVdnMeas = ASNBoolean.decode(memberStream);
/*  38 */     this.singleStepConference = ASNBoolean.decode(memberStream);
/*  39 */     this.selectiveListeningHold = ASNBoolean.decode(memberStream);
/*  40 */     this.selectiveListeningRetrieve = ASNBoolean.decode(memberStream);
/*  41 */     this.setBillingRate = ASNBoolean.decode(memberStream);
/*  42 */     this.queryUCID = ASNBoolean.decode(memberStream);
/*  43 */     this.chargeAdviceEvent = ASNBoolean.decode(memberStream);
/*  44 */     this.reserved1 = ASNBoolean.decode(memberStream);
/*  45 */     this.reserved2 = ASNBoolean.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  50 */     ASNIA5String.encode(this.switchVersion, memberStream);
/*  51 */     ASNBoolean.encode(this.sendDTMFTone, memberStream);
/*  52 */     ASNBoolean.encode(this.enteredDigitsEvent, memberStream);
/*  53 */     ASNBoolean.encode(this.queryDeviceName, memberStream);
/*  54 */     ASNBoolean.encode(this.queryAgentMeas, memberStream);
/*  55 */     ASNBoolean.encode(this.querySplitSkillMeas, memberStream);
/*  56 */     ASNBoolean.encode(this.queryTrunkGroupMeas, memberStream);
/*  57 */     ASNBoolean.encode(this.queryVdnMeas, memberStream);
/*  58 */     ASNBoolean.encode(this.singleStepConference, memberStream);
/*  59 */     ASNBoolean.encode(this.selectiveListeningHold, memberStream);
/*  60 */     ASNBoolean.encode(this.selectiveListeningRetrieve, memberStream);
/*  61 */     ASNBoolean.encode(this.setBillingRate, memberStream);
/*  62 */     ASNBoolean.encode(this.queryUCID, memberStream);
/*  63 */     ASNBoolean.encode(this.chargeAdviceEvent, memberStream);
/*  64 */     ASNBoolean.encode(this.reserved1, memberStream);
/*  65 */     ASNBoolean.encode(this.reserved2, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  71 */     Collection lines = new ArrayList();
/*     */ 
/*  73 */     lines.add("LucentV5GetAPICapsConfEvent ::=");
/*  74 */     lines.add("{");
/*     */ 
/*  76 */     String indent = "  ";
/*     */ 
/*  78 */     lines.addAll(ASNIA5String.print(this.switchVersion, "switchVersion", indent));
/*  79 */     lines.addAll(ASNBoolean.print(this.sendDTMFTone, "sendDTMFTone", indent));
/*  80 */     lines.addAll(ASNBoolean.print(this.enteredDigitsEvent, "enteredDigitsEvent", indent));
/*     */ 
/*  84 */     lines.addAll(ASNBoolean.print(this.queryDeviceName, "queryDeviceName", indent));
/*  85 */     lines.addAll(ASNBoolean.print(this.queryAgentMeas, "queryAgentMeas", indent));
/*  86 */     lines.addAll(ASNBoolean.print(this.querySplitSkillMeas, "querySplitSkillMeas", indent));
/*     */ 
/*  90 */     lines.addAll(ASNBoolean.print(this.queryTrunkGroupMeas, "queryTrunkGroupMeas", indent));
/*     */ 
/*  94 */     lines.addAll(ASNBoolean.print(this.queryVdnMeas, "queryVdnMeas", indent));
/*  95 */     lines.addAll(ASNBoolean.print(this.singleStepConference, "singleStepConference", indent));
/*     */ 
/*  99 */     lines.addAll(ASNBoolean.print(this.selectiveListeningHold, "selectiveListeningHold", indent));
/*     */ 
/* 103 */     lines.addAll(ASNBoolean.print(this.selectiveListeningRetrieve, "selectiveListeningRetrieve", indent));
/*     */ 
/* 107 */     lines.addAll(ASNBoolean.print(this.setBillingRate, "setBillingRate", indent));
/* 108 */     lines.addAll(ASNBoolean.print(this.queryUCID, "queryUCID", indent));
/* 109 */     lines.addAll(ASNBoolean.print(this.chargeAdviceEvent, "chargeAdviceEvent", indent));
/*     */ 
/* 113 */     lines.addAll(ASNBoolean.print(this.reserved1, "reserved1", indent));
/* 114 */     lines.addAll(ASNBoolean.print(this.reserved2, "reserved2", indent));
/*     */ 
/* 116 */     lines.add("}");
/* 117 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/* 124 */     return 97;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5GetAPICapsConfEvent
 * JD-Core Version:    0.5.4
 */