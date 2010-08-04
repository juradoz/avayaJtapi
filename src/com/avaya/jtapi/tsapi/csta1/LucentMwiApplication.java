/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBitString;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentMwiApplication extends ASNBitString
/*    */ {
/*    */   static final int AT_MCS = 16777216;
/*    */   static final int AT_VOICE = 33554432;
/*    */   static final int AT_PROPMGT = 67108864;
/*    */   static final int AT_LWC = 134217728;
/*    */   static final int AT_CTI = 268435456;
/*    */   static final int numBits = 8;
/*    */ 
/*    */   static void encode(int bits, OutputStream out)
/*    */   {
/* 20 */     encode(bits, 8, out);
/*    */   }
/*    */ 
/*    */   static Collection<String> print(int bits, String name, String indent)
/*    */   {
/* 26 */     String str = " ";
/*    */ 
/* 28 */     if ((bits & 0x1000000) != 0) {
/* 29 */       str = str + "AT_MCS ";
/*    */     }
/* 31 */     if ((bits & 0x2000000) != 0) {
/* 32 */       str = str + "AT_VOICE ";
/*    */     }
/* 34 */     if ((bits & 0x4000000) != 0) {
/* 35 */       str = str + "AT_PROPMGT ";
/*    */     }
/* 37 */     if ((bits & 0x8000000) != 0) {
/* 38 */       str = str + "AT_LWC ";
/*    */     }
/* 40 */     if ((bits & 0x10000000) != 0) {
/* 41 */       str = str + "AT_CTI ";
/*    */     }
/*    */ 
/* 44 */     return print(bits, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMwiApplication
 * JD-Core Version:    0.5.4
 */