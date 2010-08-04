/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public final class ConnectionList extends ASNSequenceOf
/*    */ {
/*    */   CSTAConnection[] array;
/*    */ 
/*    */   public ConnectionList()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ConnectionList(CSTAConnection[] _array)
/*    */   {
/* 18 */     this.array = _array;
/*    */   }
/*    */ 
/*    */   public static CSTAConnection[] decode(InputStream in)
/*    */   {
/* 23 */     ConnectionList _this = new ConnectionList();
/* 24 */     _this.doDecode(in);
/* 25 */     return _this.array;
/*    */   }
/*    */ 
/*    */   public void doDecode(InputStream in)
/*    */   {
/* 30 */     super.doDecode(in);
/*    */ 
/* 32 */     this.array = new CSTAConnection[this.vec.size()];
/*    */ 
/* 34 */     for (int i = 0; i < this.array.length; ++i)
/*    */     {
/* 36 */       this.array[i] = ((CSTAConnection)this.vec.elementAt(i));
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object decodeMember(InputStream memberStream)
/*    */   {
/* 42 */     return CSTAConnection.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public static void encode(CSTAConnection[] array, OutputStream out)
/*    */   {
/* 47 */     ConnectionList _this = new ConnectionList(array);
/* 48 */     _this.doEncode(array.length, out);
/*    */   }
/*    */ 
/*    */   public void encodeMember(int index, OutputStream memberStream)
/*    */   {
/* 53 */     CSTAConnection.encode(this.array[index], memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(CSTAConnection[] array, String name, String _indent)
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
/* 72 */       lines.addAll(CSTAConnection.print(array[i], null, indent));
/*    */     }
/* 74 */     lines.add(_indent + "}");
/* 75 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.ConnectionList
 * JD-Core Version:    0.5.4
 */