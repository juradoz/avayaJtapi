/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public final class CSTASnapshotCallData extends ASNSequenceOf
/*    */ {
/*    */   CSTASnapshotCallResponseInfo[] array;
/*    */ 
/*    */   public CSTASnapshotCallData()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTASnapshotCallData(CSTASnapshotCallResponseInfo[] _array)
/*    */   {
/* 18 */     this.array = _array;
/*    */   }
/*    */ 
/*    */   public static void encode(CSTASnapshotCallResponseInfo[] array, OutputStream out)
/*    */   {
/* 23 */     CSTASnapshotCallData _this = new CSTASnapshotCallData(array);
/* 24 */     _this.doEncode(array.length, out);
/*    */   }
/*    */ 
/*    */   public void encodeMember(int index, OutputStream memberStream)
/*    */   {
/* 29 */     CSTASnapshotCallResponseInfo.encode(this.array[index], memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTASnapshotCallResponseInfo[] decode(InputStream in)
/*    */   {
/* 34 */     CSTASnapshotCallData _this = new CSTASnapshotCallData();
/* 35 */     _this.doDecode(in);
/* 36 */     return _this.array;
/*    */   }
/*    */ 
/*    */   public void doDecode(InputStream in)
/*    */   {
/* 41 */     super.doDecode(in);
/*    */ 
/* 43 */     this.array = new CSTASnapshotCallResponseInfo[this.vec.size()];
/*    */ 
/* 45 */     for (int i = 0; i < this.array.length; ++i)
/*    */     {
/* 47 */       this.array[i] = ((CSTASnapshotCallResponseInfo)this.vec.elementAt(i));
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object decodeMember(InputStream memberStream)
/*    */   {
/* 53 */     return CSTASnapshotCallResponseInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(CSTASnapshotCallResponseInfo[] array, String name, String _indent)
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
/* 72 */       lines.addAll(CSTASnapshotCallResponseInfo.print(array[i], null, indent));
/*    */     }
/* 74 */     lines.add(_indent + "}");
/* 75 */     return lines;
/*    */   }
/*    */ 
/*    */   public CSTASnapshotCallResponseInfo[] getArray()
/*    */   {
/* 82 */     return this.array;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASnapshotCallData
 * JD-Core Version:    0.5.4
 */