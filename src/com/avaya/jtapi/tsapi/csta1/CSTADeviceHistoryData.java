/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public final class CSTADeviceHistoryData extends ASNSequenceOf
/*    */ {
/*    */   LucentDeviceHistoryEntry[] array;
/*    */ 
/*    */   CSTADeviceHistoryData()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTADeviceHistoryData(LucentDeviceHistoryEntry[] _array)
/*    */   {
/* 18 */     this.array = _array;
/*    */   }
/*    */ 
/*    */   static void encode(LucentDeviceHistoryEntry[] array, OutputStream out)
/*    */   {
/* 23 */     CSTADeviceHistoryData _this = new CSTADeviceHistoryData(array);
/* 24 */     _this.doEncode(array.length, out);
/*    */   }
/*    */ 
/*    */   public void encodeMember(int index, OutputStream memberStream)
/*    */   {
/* 29 */     LucentDeviceHistoryEntry.encode(this.array[index], memberStream);
/*    */   }
/*    */ 
/*    */   public static LucentDeviceHistoryEntry[] decode(InputStream in)
/*    */   {
/* 34 */     CSTADeviceHistoryData _this = new CSTADeviceHistoryData();
/* 35 */     _this.doDecode(in);
/* 36 */     return _this.array;
/*    */   }
/*    */ 
/*    */   public void doDecode(InputStream in)
/*    */   {
/* 41 */     super.doDecode(in);
/*    */ 
/* 43 */     this.array = new LucentDeviceHistoryEntry[this.vec.size()];
/* 44 */     for (int i = 0; i < this.array.length; ++i)
/*    */     {
/* 46 */       this.array[i] = ((LucentDeviceHistoryEntry)this.vec.elementAt(i));
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object decodeMember(InputStream memberStream)
/*    */   {
/* 52 */     return LucentDeviceHistoryEntry.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(LucentDeviceHistoryEntry[] array, String name, String _indent)
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
/* 71 */       lines.addAll(LucentDeviceHistoryEntry.print(array[i], null, indent));
/*    */     }
/* 73 */     lines.add(_indent + "}");
/* 74 */     return lines;
/*    */   }
/*    */ 
/*    */   public LucentDeviceHistoryEntry[] getArray()
/*    */   {
/* 81 */     return this.array;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTADeviceHistoryData
 * JD-Core Version:    0.5.4
 */