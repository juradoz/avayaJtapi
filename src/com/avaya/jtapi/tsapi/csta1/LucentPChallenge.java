/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
/*    */ 
/*    */ class LucentPChallenge extends LucentPrivateData
/*    */ {
/*    */   byte[] value;
/*    */   static final int PDU = 121;
/*    */ 
/*    */   static LucentPChallenge decode(InputStream in)
/*    */   {
/* 18 */     LucentPChallenge _this = new LucentPChallenge();
/* 19 */     _this.doDecode(in);
/*    */ 
/* 21 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 26 */     this.value = ASNOctetString.decode(memberStream); } 
/*    */   public Collection<String> print() { return Collections.emptyList(); } 
/* 45 */   public int getPDU() { return 121; }
/*    */ 
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentPChallenge
 * JD-Core Version:    0.5.4
 */