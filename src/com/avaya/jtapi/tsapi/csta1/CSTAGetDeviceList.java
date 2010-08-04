/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTAGetDeviceList extends CSTARequest
/*    */ {
/*    */   int index;
/*    */   short level;
/*    */   static final int PDU = 126;
/*    */ 
/*    */   public CSTAGetDeviceList(int _index, short _level)
/*    */   {
/* 19 */     this.index = _index;
/* 20 */     this.level = _level;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 25 */     ASNInteger.encode(this.index, memberStream);
/* 26 */     CSTALevel.encode(this.level, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 31 */     Collection lines = new ArrayList();
/* 32 */     lines.add("CSTAGetDeviceList ::=");
/* 33 */     lines.add("{");
/*    */ 
/* 35 */     String indent = "  ";
/*    */ 
/* 37 */     lines.addAll(ASNInteger.print(this.index, "index", indent));
/* 38 */     lines.addAll(CSTALevel.print(this.level, "level", indent));
/*    */ 
/* 40 */     lines.add("}");
/* 41 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 46 */     return 126;
/*    */   }
/*    */ 
/*    */   public int getIndex()
/*    */   {
/* 52 */     return this.index;
/*    */   }
/*    */ 
/*    */   public short getLevel()
/*    */   {
/* 60 */     return this.level;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAGetDeviceList
 * JD-Core Version:    0.5.4
 */