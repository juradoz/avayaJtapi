/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBitString;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class SystemStatusFilter extends ASNBitString
/*    */ {
/*    */   public static final int SF_INITIALIZING = -2147483648;
/*    */   public static final int SF_ENABLED = 1073741824;
/*    */   public static final int SF_NORMAL = 536870912;
/*    */   public static final int SF_MESSAGES_LOST = 268435456;
/*    */   public static final int SF_DISABLED = 134217728;
/*    */   public static final int SF_OVERLOAD_IMMINENT = 67108864;
/*    */   public static final int SF_OVERLOAD_REACHED = 33554432;
/*    */   public static final int SF_OVERLOAD_RELIEVED = 16777216;
/*    */   static final int numBits = 8;
/*    */ 
/*    */   static void encode(int bits, OutputStream out)
/*    */   {
/* 33 */     encode(bits, 8, out);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(int bits, String name, String indent) {
/* 37 */     String str = " ";
/*    */ 
/* 39 */     if ((bits & 0x80000000) != 0) {
/* 40 */       str = str + "SF_INITIALIZING ";
/*    */     }
/* 42 */     if ((bits & 0x40000000) != 0) {
/* 43 */       str = str + "SF_ENABLED ";
/*    */     }
/* 45 */     if ((bits & 0x20000000) != 0) {
/* 46 */       str = str + "SF_NORMAL ";
/*    */     }
/* 48 */     if ((bits & 0x10000000) != 0) {
/* 49 */       str = str + "SF_MESSAGES_LOST ";
/*    */     }
/* 51 */     if ((bits & 0x8000000) != 0) {
/* 52 */       str = str + "SF_DISABLED ";
/*    */     }
/* 54 */     if ((bits & 0x4000000) != 0) {
/* 55 */       str = str + "SF_OVERLOAD_IMMINENT ";
/*    */     }
/* 57 */     if ((bits & 0x2000000) != 0) {
/* 58 */       str = str + "SF_OVERLOAD_REACHED ";
/*    */     }
/* 60 */     if ((bits & 0x1000000) != 0) {
/* 61 */       str = str + "SF_OVERLOAD_RELIEVED ";
/*    */     }
/* 63 */     return print(bits, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.SystemStatusFilter
 * JD-Core Version:    0.5.4
 */