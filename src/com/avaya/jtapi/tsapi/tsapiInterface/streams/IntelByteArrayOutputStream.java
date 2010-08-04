/*    */ package com.avaya.jtapi.tsapi.tsapiInterface.streams;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public final class IntelByteArrayOutputStream extends ByteArrayOutputStream
/*    */ {
/*    */   public IntelByteArrayOutputStream()
/*    */   {
/*    */   }
/*    */ 
/*    */   public IntelByteArrayOutputStream(int size)
/*    */   {
/* 39 */     super(size);
/*    */   }
/*    */ 
/*    */   public void writeInt(int v)
/*    */     throws IOException
/*    */   {
/* 47 */     write(v >>> 0 & 0xFF);
/* 48 */     write(v >>> 8 & 0xFF);
/* 49 */     write(v >>> 16 & 0xFF);
/* 50 */     write(v >>> 24 & 0xFF);
/*    */   }
/*    */ 
/*    */   public void writeShort(int v)
/*    */     throws IOException
/*    */   {
/* 58 */     write(v >>> 0 & 0xFF);
/* 59 */     write(v >>> 8 & 0xFF);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.streams.IntelByteArrayOutputStream
 * JD-Core Version:    0.5.4
 */