/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBitString;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTACallFilter extends ASNBitString
/*    */ {
/*    */   static final int CF_CALL_CLEARED = -2147483648;
/*    */   static final int CF_CONFERENCED = 1073741824;
/*    */   static final int CF_CONNECTION_CLEARED = 536870912;
/*    */   static final int CF_DELIVERED = 268435456;
/*    */   static final int CF_DIVERTED = 134217728;
/*    */   static final int CF_ESTABLISHED = 67108864;
/*    */   static final int CF_FAILED = 33554432;
/*    */   static final int CF_HELD = 16777216;
/*    */   static final int CF_NETWORK_REACHED = 8388608;
/*    */   static final int CF_ORIGINATED = 4194304;
/*    */   static final int CF_QUEUED = 2097152;
/*    */   static final int CF_RETRIEVED = 1048576;
/*    */   static final int CF_SERVICE_INITIATED = 524288;
/*    */   static final int CF_TRANSFERRED = 262144;
/*    */   static final int numBits = 14;
/*    */ 
/*    */   static void encode(int bits, OutputStream out)
/*    */   {
/* 29 */     encode(bits, 14, out);
/*    */   }
/*    */ 
/*    */   static Collection<String> print(int bits, String name, String indent)
/*    */   {
/* 34 */     String str = " ";
/*    */ 
/* 36 */     if ((bits & 0x80000000) != 0) {
/* 37 */       str = str + "CF_CALL_CLEARED ";
/*    */     }
/* 39 */     if ((bits & 0x40000000) != 0) {
/* 40 */       str = str + "CF_CONFERENCED ";
/*    */     }
/* 42 */     if ((bits & 0x20000000) != 0) {
/* 43 */       str = str + "CF_CONNECTION_CLEARED ";
/*    */     }
/* 45 */     if ((bits & 0x10000000) != 0) {
/* 46 */       str = str + "CF_DELIVERED ";
/*    */     }
/* 48 */     if ((bits & 0x8000000) != 0) {
/* 49 */       str = str + "CF_DIVERTED ";
/*    */     }
/* 51 */     if ((bits & 0x4000000) != 0) {
/* 52 */       str = str + "CF_ESTABLISHED ";
/*    */     }
/* 54 */     if ((bits & 0x2000000) != 0) {
/* 55 */       str = str + "CF_FAILED ";
/*    */     }
/* 57 */     if ((bits & 0x1000000) != 0) {
/* 58 */       str = str + "CF_HELD ";
/*    */     }
/* 60 */     if ((bits & 0x800000) != 0) {
/* 61 */       str = str + "CF_NETWORK_REACHED ";
/*    */     }
/* 63 */     if ((bits & 0x400000) != 0) {
/* 64 */       str = str + "CF_ORIGINATED ";
/*    */     }
/* 66 */     if ((bits & 0x200000) != 0) {
/* 67 */       str = str + "CF_QUEUED ";
/*    */     }
/* 69 */     if ((bits & 0x100000) != 0) {
/* 70 */       str = str + "CF_RETRIEVED ";
/*    */     }
/* 72 */     if ((bits & 0x80000) != 0) {
/* 73 */       str = str + "CF_SERVICE_INITIATED ";
/*    */     }
/* 75 */     if ((bits & 0x40000) != 0) {
/* 76 */       str = str + "CF_TRANSFERRED ";
/*    */     }
/*    */ 
/* 79 */     return print(bits, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTACallFilter
 * JD-Core Version:    0.5.4
 */