/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class LucentV7GetAPICapsConfEvent extends LucentV5GetAPICapsConfEvent
/*     */ {
/*     */   private int maxDeviceHistoryCount;
/*     */   private String administeredSwitchSoftwareVersion;
/*     */   private String switchSoftwareVersion;
/*     */   private String offerType;
/*     */   private String serverType;
/*     */   static final int PDU = 127;
/*     */ 
/*     */   static LucentGetAPICapsConfEvent decode(InputStream in)
/*     */   {
/*  22 */     LucentV7GetAPICapsConfEvent _this = new LucentV7GetAPICapsConfEvent();
/*  23 */     _this.doDecode(in);
/*     */ 
/*  25 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  30 */     super.decodeMembers(memberStream);
/*  31 */     this.maxDeviceHistoryCount = ASNInteger.decode(memberStream);
/*  32 */     this.administeredSwitchSoftwareVersion = ASNIA5String.decode(memberStream);
/*  33 */     this.switchSoftwareVersion = ASNIA5String.decode(memberStream);
/*  34 */     this.offerType = ASNIA5String.decode(memberStream);
/*  35 */     this.serverType = ASNIA5String.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  40 */     super.encodeMembers(memberStream);
/*  41 */     ASNInteger.encode(this.maxDeviceHistoryCount, memberStream);
/*  42 */     ASNIA5String.encode(this.administeredSwitchSoftwareVersion, memberStream);
/*  43 */     ASNIA5String.encode(this.switchSoftwareVersion, memberStream);
/*  44 */     ASNIA5String.encode(this.offerType, memberStream);
/*  45 */     ASNIA5String.encode(this.serverType, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  50 */     Collection lines = new ArrayList();
/*     */ 
/*  52 */     lines.add("LucentV7GetAPICapsConfEvent ::=");
/*  53 */     lines.add("{");
/*     */ 
/*  55 */     String indent = "  ";
/*     */ 
/*  57 */     lines.addAll(ASNIA5String.print(this.switchVersion, "switchVersion", indent));
/*  58 */     lines.addAll(ASNBoolean.print(this.sendDTMFTone, "sendDTMFTone", indent));
/*  59 */     lines.addAll(ASNBoolean.print(this.enteredDigitsEvent, "enteredDigitsEvent", indent));
/*     */ 
/*  63 */     lines.addAll(ASNBoolean.print(this.queryDeviceName, "queryDeviceName", indent));
/*  64 */     lines.addAll(ASNBoolean.print(this.queryAgentMeas, "queryAgentMeas", indent));
/*  65 */     lines.addAll(ASNBoolean.print(this.querySplitSkillMeas, "querySplitSkillMeas", indent));
/*     */ 
/*  69 */     lines.addAll(ASNBoolean.print(this.queryTrunkGroupMeas, "queryTrunkGroupMeas", indent));
/*     */ 
/*  73 */     lines.addAll(ASNBoolean.print(this.queryVdnMeas, "queryVdnMeas", indent));
/*  74 */     lines.addAll(ASNBoolean.print(this.singleStepConference, "singleStepConference", indent));
/*     */ 
/*  78 */     lines.addAll(ASNBoolean.print(this.selectiveListeningHold, "selectiveListeningHold", indent));
/*     */ 
/*  82 */     lines.addAll(ASNBoolean.print(this.selectiveListeningRetrieve, "selectiveListeningRetrieve", indent));
/*     */ 
/*  86 */     lines.addAll(ASNBoolean.print(this.setBillingRate, "setBillingRate", indent));
/*  87 */     lines.addAll(ASNBoolean.print(this.queryUCID, "queryUCID", indent));
/*  88 */     lines.addAll(ASNBoolean.print(this.chargeAdviceEvent, "chargeAdviceEvent", indent));
/*     */ 
/*  92 */     lines.addAll(ASNBoolean.print(this.reserved1, "reserved1(singleStepTransfer)", indent));
/*  93 */     lines.addAll(ASNBoolean.print(this.reserved2, "reserved2(monitorCallsViaDevice)", indent));
/*  94 */     lines.addAll(ASNInteger.print(this.maxDeviceHistoryCount, "maxDeviceHistoryCount", indent));
/*     */ 
/*  98 */     lines.addAll(ASNIA5String.print(this.administeredSwitchSoftwareVersion, "administeredSwitchSoftwareVersion", indent));
/*     */ 
/* 102 */     lines.addAll(ASNIA5String.print(this.switchSoftwareVersion, "switchSoftwareVersion", indent));
/*     */ 
/* 106 */     lines.addAll(ASNIA5String.print(this.offerType, "offerType", indent));
/* 107 */     lines.addAll(ASNIA5String.print(this.serverType, "serverType", indent));
/*     */ 
/* 109 */     lines.add("}");
/* 110 */     return lines;
/*     */   }
/*     */ 
/*     */   public String getOfferType()
/*     */   {
/* 118 */     return this.offerType;
/*     */   }
/*     */ 
/*     */   public String getServerType()
/*     */   {
/* 126 */     return this.serverType;
/*     */   }
/*     */ 
/*     */   public String getSwitchSoftwareVersion()
/*     */   {
/* 134 */     return this.switchSoftwareVersion;
/*     */   }
/*     */ 
/*     */   public String getAdministeredSwitchSoftwareVersion()
/*     */   {
/* 142 */     return this.administeredSwitchSoftwareVersion;
/*     */   }
/*     */ 
/*     */   public boolean getSingleStepTransfer()
/*     */   {
/* 160 */     return this.reserved1;
/*     */   }
/*     */ 
/*     */   public boolean getMonitorCallsViaDevice()
/*     */   {
/* 178 */     return this.reserved2;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/* 185 */     return 127;
/*     */   }
/*     */ 
/*     */   public int getMaxDeviceHistoryCount()
/*     */   {
/* 190 */     return this.maxDeviceHistoryCount;
/*     */   }
/*     */ 
/*     */   public void setMaxDeviceHistoryCount(int maxDeviceHistoryCount)
/*     */   {
/* 199 */     this.maxDeviceHistoryCount = maxDeviceHistoryCount;
/*     */   }
/*     */ 
/*     */   public void setAdministeredSwitchSoftwareVersion(String administeredSwitchSoftwareVersion)
/*     */   {
/* 209 */     this.administeredSwitchSoftwareVersion = administeredSwitchSoftwareVersion;
/*     */   }
/*     */ 
/*     */   public void setSwitchSoftwareVersion(String switchSoftwareVersion)
/*     */   {
/* 217 */     this.switchSoftwareVersion = switchSoftwareVersion;
/*     */   }
/*     */ 
/*     */   public void setOfferType(String offerType)
/*     */   {
/* 225 */     this.offerType = offerType;
/*     */   }
/*     */ 
/*     */   public void setServerType(String serverType)
/*     */   {
/* 233 */     this.serverType = serverType;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7GetAPICapsConfEvent
 * JD-Core Version:    0.5.4
 */