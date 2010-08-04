/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentSendDTMFTone extends LucentPrivateData
/*    */ {
/*    */   CSTAConnectionID sender;
/*    */   CSTAConnectionID[] receivers;
/*    */   String tones;
/*    */   int toneDuration;
/*    */   int pauseDuration;
/*    */   static final int PDU = 8;
/*    */ 
/*    */   public LucentSendDTMFTone()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentSendDTMFTone(CSTAConnectionID _sender, CSTAConnectionID[] _receivers, String _tones, int _toneDuration, int _pauseDuration)
/*    */   {
/* 28 */     this.sender = _sender;
/* 29 */     this.receivers = _receivers;
/* 30 */     this.tones = _tones;
/* 31 */     this.toneDuration = _toneDuration;
/* 32 */     this.pauseDuration = _pauseDuration;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 37 */     CSTAConnectionID.encode(this.sender, memberStream);
/* 38 */     LucentConnIDList.encode(this.receivers, memberStream);
/* 39 */     ASNIA5String.encode(this.tones, memberStream);
/* 40 */     ASNInteger.encode(this.toneDuration, memberStream);
/* 41 */     ASNInteger.encode(this.pauseDuration, memberStream);
/*    */   }
/*    */ 
/*    */   public static LucentSendDTMFTone decode(InputStream in)
/*    */   {
/* 46 */     LucentSendDTMFTone _this = new LucentSendDTMFTone();
/* 47 */     _this.doDecode(in);
/*    */ 
/* 49 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 54 */     this.sender = CSTAConnectionID.decode(memberStream);
/* 55 */     this.receivers = LucentConnIDList.decode(memberStream);
/* 56 */     this.tones = ASNIA5String.decode(memberStream);
/* 57 */     this.toneDuration = ASNInteger.decode(memberStream);
/* 58 */     this.pauseDuration = ASNInteger.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 63 */     Collection lines = new ArrayList();
/*    */ 
/* 65 */     lines.add("LucentSendDTMFTone ::=");
/* 66 */     lines.add("{");
/*    */ 
/* 68 */     String indent = "  ";
/*    */ 
/* 70 */     lines.addAll(CSTAConnectionID.print(this.sender, "sender", indent));
/* 71 */     lines.addAll(LucentConnIDList.print(this.receivers, "receivers", indent));
/* 72 */     lines.addAll(ASNIA5String.print(this.tones, "tones", indent));
/* 73 */     lines.addAll(ASNInteger.print(this.toneDuration, "toneDuration", indent));
/* 74 */     lines.addAll(ASNInteger.print(this.pauseDuration, "pauseDuration", indent));
/*    */ 
/* 76 */     lines.add("}");
/* 77 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 82 */     return 8;
/*    */   }
/* 84 */   public int getPauseDuration() { return this.pauseDuration; }
/*    */ 
/*    */   public CSTAConnectionID[] getReceivers() {
/* 87 */     return this.receivers;
/*    */   }
/*    */   public CSTAConnectionID getSender() {
/* 90 */     return this.sender;
/*    */   }
/*    */   public int getToneDuration() {
/* 93 */     return this.toneDuration;
/*    */   }
/*    */   public String getTones() {
/* 96 */     return this.tones;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSendDTMFTone
 * JD-Core Version:    0.5.4
 */