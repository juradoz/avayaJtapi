/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public final class LucentTrunkInfoList extends ASNSequenceOf
/*    */ {
/*    */   CSTATrunkInfo[] array;
/*    */ 
/*    */   public LucentTrunkInfoList()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentTrunkInfoList(CSTATrunkInfo[] _array)
/*    */   {
/* 21 */     this.array = _array;
/*    */   }
/*    */ 
/*    */   public static CSTATrunkInfo[] decode(InputStream in)
/*    */   {
/* 26 */     LucentTrunkInfoList _this = new LucentTrunkInfoList();
/* 27 */     _this.doDecode(in);
/* 28 */     return _this.array;
/*    */   }
/*    */ 
/*    */   public void doDecode(InputStream in)
/*    */   {
/* 33 */     super.doDecode(in);
/*    */ 
/* 35 */     this.array = new CSTATrunkInfo[this.vec.size()];
/*    */ 
/* 37 */     for (int i = 0; i < this.array.length; ++i)
/*    */     {
/* 39 */       this.array[i] = ((CSTATrunkInfo)this.vec.elementAt(i));
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object decodeMember(InputStream memberStream)
/*    */   {
/* 45 */     return CSTATrunkInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public static void encode(CSTATrunkInfo[] _array, OutputStream out) {
/* 49 */     LucentTrunkInfoList _this = new LucentTrunkInfoList(_array);
/* 50 */     _this.doEncode(_array.length, out);
/*    */   }
/*    */   public void encodeMember(int idx, OutputStream memberStream) {
/* 53 */     CSTATrunkInfo.encode(this.array[idx], memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(CSTATrunkInfo[] array, String name, String _indent) {
/* 57 */     Collection lines = new ArrayList();
/*    */ 
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
/* 72 */       lines.addAll(CSTATrunkInfo.print(array[i], null, indent));
/*    */     }
/* 74 */     lines.add(_indent + "}");
/* 75 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentTrunkInfoList
 * JD-Core Version:    0.5.4
 */