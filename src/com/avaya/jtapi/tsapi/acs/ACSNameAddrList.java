/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public final class ACSNameAddrList extends ASNSequenceOf
/*    */ {
/*    */   ACSNameAddr[] array;
/*    */ 
/*    */   ACSNameAddrList()
/*    */   {
/*    */   }
/*    */ 
/*    */   ACSNameAddrList(ACSNameAddr[] _array)
/*    */   {
/* 17 */     this.array = _array;
/*    */   }
/*    */ 
/*    */   static ACSNameAddr[] decode(InputStream in)
/*    */   {
/* 22 */     ACSNameAddrList _this = new ACSNameAddrList();
/* 23 */     _this.doDecode(in);
/* 24 */     return _this.array;
/*    */   }
/*    */ 
/*    */   public void doDecode(InputStream in)
/*    */   {
/* 29 */     super.doDecode(in);
/*    */ 
/* 31 */     this.array = new ACSNameAddr[this.vec.size()];
/*    */ 
/* 33 */     for (int i = 0; i < this.array.length; ++i)
/*    */     {
/* 35 */       this.array[i] = ((ACSNameAddr)this.vec.elementAt(i));
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object decodeMember(InputStream memberStream)
/*    */   {
/* 41 */     return ACSNameAddr.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public static void encode(ACSNameAddr[] array, OutputStream out)
/*    */   {
/* 46 */     ACSNameAddrList _this = new ACSNameAddrList(array);
/* 47 */     _this.doEncode(array.length, out);
/*    */   }
/*    */ 
/*    */   public void encodeMember(int index, OutputStream memberStream)
/*    */   {
/* 52 */     ACSNameAddr.encode(this.array[index], memberStream);
/*    */   }
/*    */ 
/*    */   static Collection<String> print(ACSNameAddr[] array, String name, String _indent)
/*    */   {
/* 57 */     Collection lines = new ArrayList();
/* 58 */     if (array == null)
/*    */     {
/* 60 */       lines.add(_indent + name + " <null>");
/* 61 */       return lines;
/*    */     }
/* 63 */     if (name != null) {
/* 64 */       lines.add(_indent + name);
/*    */     }
/* 66 */     lines.add(_indent + "{");
/*    */ 
/* 68 */     String indent = _indent + "  ";
/*    */ 
/* 70 */     for (int i = 0; i < array.length; ++i) {
/* 71 */       lines.addAll(ACSNameAddr.print(array[i], null, indent));
/*    */     }
/* 73 */     lines.add(_indent + "}");
/* 74 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSNameAddrList
 * JD-Core Version:    0.5.4
 */