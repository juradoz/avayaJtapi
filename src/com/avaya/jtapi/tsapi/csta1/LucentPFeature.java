 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Collection;
import java.util.Collections;
import java.util.Random;
 
 class LucentPFeature extends LucentPrivateData
 {
   byte[] value;
   static final int PDU = 120;
 
   LucentPFeature()
   {
     this.value = new byte[32];
   }
 
   public void encodeMembers(OutputStream memberStream) {
     Random r = new Random();
     r.nextBytes(this.value);
     ASNOctetString.encode(this.value, memberStream); } 
   public Collection<String> print() { return Collections.emptyList();} 
   public int getPDU() { return 120; }
 
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentPFeature
 * JD-Core Version:    0.5.4
 */