/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBitString;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentPrivateFilter extends ASNBitString
/*    */ {
/*    */   static final int ATT_ENTERED_DIGITS_FILTER = -2147483648;
/*    */   static final int ATT_CHARGE_ADVICE_FILTER = 1073741824;
/*    */   static final int numBits = 2;
/*    */ 
/*    */   static void encode(int bits, OutputStream out)
/*    */   {
/* 17 */     encode(bits, 2, out);
/*    */   }
/*    */ 
/*    */   static Collection<String> print(int bits, String name, String indent)
/*    */   {
/* 22 */     String str = " ";
/*    */ 
/* 24 */     if ((bits & 0x80000000) != 0) {
/* 25 */       str = str + "ATT_ENTERED_DIGITS_FILTER ";
/*    */     }
/* 27 */     if ((bits & 0x40000000) != 0) {
/* 28 */       str = str + "ATT_CHARGE_ADVICE_FILTER ";
/*    */     }
/*    */ 
/* 31 */     return print(bits, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentPrivateFilter
 * JD-Core Version:    0.5.4
 */