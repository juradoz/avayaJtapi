/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Collection;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public final class UnicodeDeviceID extends ASNSequenceOf
/*    */ {
/*    */   char[] array;
/*    */ 
/*    */   public UnicodeDeviceID()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UnicodeDeviceID(char[] _array)
/*    */   {
/* 18 */     this.array = _array;
/*    */   }
/*    */ 
/*    */   public static String decode(InputStream in)
/*    */   {
/* 23 */     UnicodeDeviceID _this = new UnicodeDeviceID();
/* 24 */     _this.doDecode(in);
/* 25 */     if (_this.array.length > 0) {
/* 26 */       return new String(_this.array);
/*    */     }
/* 28 */     return null;
/*    */   }
/*    */ 
/*    */   public void doDecode(InputStream in)
/*    */   {
/* 33 */     super.doDecode(in);
/*    */ 
/* 35 */     this.array = new char[this.vec.size()];
/*    */ 
/* 37 */     for (int i = 0; i < this.array.length; ++i)
/*    */     {
/* 39 */       this.array[i] = ((Character)this.vec.elementAt(i)).charValue();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void encode(String device, OutputStream memberStream)
/*    */   {
/* 45 */     char[] deviceArray = device.toCharArray();
/* 46 */     UnicodeDeviceID _this = new UnicodeDeviceID(deviceArray);
/* 47 */     _this.doEncode(deviceArray.length, memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMember(int idx, OutputStream memberStream)
/*    */   {
/* 52 */     ASNInteger.encode(this.array[idx], memberStream);
/*    */   }
/*    */ 
/*    */   public Object decodeMember(InputStream memberStream)
/*    */   {
/* 57 */     return new Character((char)ASNInteger.decode(memberStream));
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(String str, String name, String indent)
/*    */   {
/* 62 */     return ASNIA5String.print(str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.UnicodeDeviceID
 * JD-Core Version:    0.5.4
 */