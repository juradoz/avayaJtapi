/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBitString;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class DeviceClass extends ASNBitString
/*    */ {
/*    */   static final int DC_VOICE = -2147483648;
/*    */   static final int DC_DATA = 1073741824;
/*    */   static final int DC_IMAGE = 536870912;
/*    */   static final int DC_OTHER = 268435456;
/*    */   static final int numBits = 4;
/*    */ 
/*    */   static void encode(int bits, OutputStream out)
/*    */   {
/* 19 */     encode(bits, 4, out);
/*    */   }
/*    */ 
/*    */   static Collection<String> print(int bits, String name, String indent)
/*    */   {
/* 24 */     String str = " ";
/*    */ 
/* 26 */     if ((bits & 0x80000000) != 0) {
/* 27 */       str = str + "DC_VOICE ";
/*    */     }
/* 29 */     if ((bits & 0x40000000) != 0) {
/* 30 */       str = str + "DC_DATA ";
/*    */     }
/* 32 */     if ((bits & 0x20000000) != 0) {
/* 33 */       str = str + "DC_IMAGE ";
/*    */     }
/* 35 */     if ((bits & 0x10000000) != 0) {
/* 36 */       str = str + "DC_OTHER ";
/*    */     }
/*    */ 
/* 39 */     return print(bits, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.DeviceClass
 * JD-Core Version:    0.5.4
 */