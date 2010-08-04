/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public final class CSTACallState extends ASNSequenceOf
/*    */ {
/*    */   short[] array;
/*    */ 
/*    */   public CSTACallState()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTACallState(short[] _array)
/*    */   {
/* 18 */     this.array = _array;
/*    */   }
/*    */ 
/*    */   public static short[] decode(InputStream in)
/*    */   {
/* 23 */     CSTACallState _this = new CSTACallState();
/* 24 */     _this.doDecode(in);
/* 25 */     return _this.array;
/*    */   }
/*    */ 
/*    */   public void doDecode(InputStream in)
/*    */   {
/* 30 */     super.doDecode(in);
/*    */ 
/* 32 */     this.array = new short[this.vec.size()];
/*    */ 
/* 34 */     for (int i = 0; i < this.array.length; ++i)
/*    */     {
/* 36 */       this.array[i] = (short)((Integer)this.vec.elementAt(i)).intValue();
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object decodeMember(InputStream memberStream)
/*    */   {
/* 42 */     return new Integer(LocalConnectionState.decode(memberStream));
/*    */   }
/*    */ 
/*    */   public static void encode(short[] array, OutputStream out)
/*    */   {
/* 47 */     CSTACallState _this = new CSTACallState(array);
/* 48 */     _this.doEncode(array.length, out);
/*    */   }
/*    */ 
/*    */   public void encodeMember(int index, OutputStream memberStream)
/*    */   {
/* 53 */     LocalConnectionState.encode(this.array[index], memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(short[] array, String name, String _indent)
/*    */   {
/* 58 */     Collection lines = new ArrayList();
/* 59 */     if (array == null)
/*    */     {
/* 61 */       lines.add(_indent + name + " <null>");
/* 62 */       return lines;
/*    */     }
/* 64 */     if (name != null) {
/* 65 */       lines.add(_indent + name);
/*    */     }
/* 67 */     lines.add(_indent + "{");
/*    */ 
/* 69 */     String indent = _indent + "  ";
/*    */ 
/* 71 */     for (int i = 0; i < array.length; ++i) {
/* 72 */       lines.addAll(LocalConnectionState.print(array[i], null, indent));
/*    */     }
/* 74 */     lines.add(_indent + "}");
/* 75 */     return lines;
/*    */   }
/*    */ 
/*    */   public short[] getArray()
/*    */   {
/* 82 */     return this.array;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTACallState
 * JD-Core Version:    0.5.4
 */