/*    */ package com.avaya.jtapi.tsapi.tsapiInterface.streams;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.EOFException;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public final class IntelByteArrayInputStream extends ByteArrayInputStream
/*    */ {
/*    */   public IntelByteArrayInputStream(byte[] buf)
/*    */   {
/* 38 */     super(buf);
/*    */   }
/*    */ 
/*    */   public int readInt()
/*    */     throws IOException
/*    */   {
/* 46 */     int ch1 = read();
/* 47 */     int ch2 = read();
/* 48 */     int ch3 = read();
/* 49 */     int ch4 = read();
/*    */ 
/* 51 */     if ((ch1 | ch2 | ch3 | ch4) < 0) {
/* 52 */       throw new EOFException();
/*    */     }
/* 54 */     return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0);
/*    */   }
/*    */ 
/*    */   public short readShort()
/*    */     throws IOException
/*    */   {
/* 62 */     int ch1 = read();
/* 63 */     int ch2 = read();
/*    */ 
/* 65 */     if ((ch1 | ch2) < 0) {
/* 66 */       throw new EOFException();
/*    */     }
/* 68 */     return (short)((ch2 << 8) + (ch1 << 0));
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.streams.IntelByteArrayInputStream
 * JD-Core Version:    0.5.4
 */