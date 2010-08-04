/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public final class CSTASnapshotDeviceData extends ASNSequenceOf
/*    */ {
/*    */   CSTASnapshotDeviceResponseInfo[] array;
/*    */ 
/*    */   public CSTASnapshotDeviceData()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTASnapshotDeviceData(CSTASnapshotDeviceResponseInfo[] _array)
/*    */   {
/* 18 */     this.array = _array;
/*    */   }
/*    */ 
/*    */   public static void encode(CSTASnapshotDeviceResponseInfo[] array, OutputStream out)
/*    */   {
/* 23 */     CSTASnapshotDeviceData _this = new CSTASnapshotDeviceData(array);
/* 24 */     _this.doEncode(array.length, out);
/*    */   }
/*    */ 
/*    */   public void encodeMember(int index, OutputStream memberStream)
/*    */   {
/* 29 */     CSTASnapshotDeviceResponseInfo.encode(this.array[index], memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTASnapshotDeviceResponseInfo[] decode(InputStream in)
/*    */   {
/* 34 */     CSTASnapshotDeviceData _this = new CSTASnapshotDeviceData();
/* 35 */     _this.doDecode(in);
/* 36 */     return _this.array;
/*    */   }
/*    */ 
/*    */   public void doDecode(InputStream in)
/*    */   {
/* 41 */     super.doDecode(in);
/*    */ 
/* 43 */     this.array = new CSTASnapshotDeviceResponseInfo[this.vec.size()];
/*    */ 
/* 45 */     for (int i = 0; i < this.array.length; ++i)
/*    */     {
/* 47 */       this.array[i] = ((CSTASnapshotDeviceResponseInfo)this.vec.elementAt(i));
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object decodeMember(InputStream memberStream)
/*    */   {
/* 53 */     return CSTASnapshotDeviceResponseInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(CSTASnapshotDeviceResponseInfo[] array, String name, String _indent)
/*    */   {
/* 58 */     Collection lines = new ArrayList();
/*    */ 
/* 60 */     if (array == null)
/*    */     {
/* 62 */       lines.add(_indent + name + " <null>");
/* 63 */       return lines;
/*    */     }
/* 65 */     if (name != null) {
/* 66 */       lines.add(_indent + name);
/*    */     }
/* 68 */     lines.add(_indent + "{");
/*    */ 
/* 70 */     String indent = _indent + "  ";
/*    */ 
/* 72 */     for (int i = 0; i < array.length; ++i) {
/* 73 */       lines.addAll(CSTASnapshotDeviceResponseInfo.print(array[i], null, indent));
/*    */     }
/* 75 */     lines.add(_indent + "}");
/* 76 */     return lines;
/*    */   }
/*    */ 
/*    */   public CSTASnapshotDeviceResponseInfo[] getArray()
/*    */   {
/* 83 */     return this.array;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceData
 * JD-Core Version:    0.5.4
 */