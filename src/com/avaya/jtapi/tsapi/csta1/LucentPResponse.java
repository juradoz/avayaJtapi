/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
/*    */ 
/*    */ class LucentPResponse extends LucentPrivateData
/*    */ {
/*    */   byte[] value;
/*    */   static final int PDU = 122;
/*    */ 
/*    */   LucentPResponse(byte[] _val)
/*    */   {
/* 14 */     this.value = _val;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 19 */     ASNOctetString.encode(this.value, memberStream); } 
/*    */   public Collection<String> print() { return Collections.emptyList(); } 
/* 38 */   public int getPDU() { return 122; }
/*    */ 
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentPResponse
 * JD-Core Version:    0.5.4
 */