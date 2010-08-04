/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class LucentSingleStepConferenceCall extends LucentPrivateData
/*     */ {
/*     */   CSTAConnectionID activeCall;
/*     */   String deviceToBeJoin;
/*     */   short participationType;
/*     */   boolean alertDestination;
/*     */   public static final int PDU = 65;
/*     */ 
/*     */   public LucentSingleStepConferenceCall(CSTAConnectionID _activeCall, String _deviceToBeJoin, short _participationType, boolean _alertDestination)
/*     */   {
/*  24 */     this.activeCall = _activeCall;
/*  25 */     this.deviceToBeJoin = _deviceToBeJoin;
/*  26 */     this.participationType = _participationType;
/*  27 */     this.alertDestination = _alertDestination;
/*     */   }
/*     */ 
/*     */   public LucentSingleStepConferenceCall()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream) {
/*  35 */     CSTAConnectionID.encode(this.activeCall, memberStream);
/*  36 */     DeviceID.encode(this.deviceToBeJoin, memberStream);
/*  37 */     ParticipationType.encode(this.participationType, memberStream);
/*  38 */     ASNBoolean.encode(this.alertDestination, memberStream);
/*     */   }
/*     */ 
/*     */   public static LucentSingleStepConferenceCall decode(InputStream in)
/*     */   {
/*  43 */     LucentSingleStepConferenceCall _this = new LucentSingleStepConferenceCall();
/*     */ 
/*  45 */     _this.doDecode(in);
/*     */ 
/*  47 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  52 */     this.activeCall = CSTAConnectionID.decode(memberStream);
/*  53 */     this.deviceToBeJoin = DeviceID.decode(memberStream);
/*  54 */     this.participationType = ParticipationType.decode(memberStream);
/*  55 */     this.alertDestination = ASNBoolean.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  60 */     Collection lines = new ArrayList();
/*     */ 
/*  62 */     lines.add("LucentSingleStepConferenceCall ::=");
/*  63 */     lines.add("{");
/*     */ 
/*  65 */     String indent = "  ";
/*     */ 
/*  67 */     lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall", indent));
/*  68 */     lines.addAll(DeviceID.print(this.deviceToBeJoin, "deviceToBeJoin", indent));
/*  69 */     lines.addAll(ParticipationType.print(this.participationType, "participationType", indent));
/*  70 */     lines.addAll(ASNBoolean.print(this.alertDestination, "alertDestination", indent));
/*     */ 
/*  72 */     lines.add("}");
/*  73 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  78 */     return 65;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getActiveCall()
/*     */   {
/*  84 */     return this.activeCall;
/*     */   }
/*     */ 
/*     */   public boolean isAlertDestination()
/*     */   {
/*  92 */     return this.alertDestination;
/*     */   }
/*     */ 
/*     */   public String getDeviceToBeJoin()
/*     */   {
/* 100 */     return this.deviceToBeJoin;
/*     */   }
/*     */ 
/*     */   public short getParticipationType()
/*     */   {
/* 108 */     return this.participationType;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSingleStepConferenceCall
 * JD-Core Version:    0.5.4
 */