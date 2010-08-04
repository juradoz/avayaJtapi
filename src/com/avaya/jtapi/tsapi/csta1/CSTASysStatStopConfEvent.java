/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CSTASysStatStopConfEvent extends CSTAConfirmation
/*    */ {
/*    */   public static final int PDU = 103;
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 22 */     return 103;
/*    */   }
/*    */ 
/*    */   public static CSTASysStatStopConfEvent decode(InputStream in)
/*    */   {
/* 27 */     CSTASysStatStopConfEvent _this = new CSTASysStatStopConfEvent();
/* 28 */     _this.doDecode(in);
/*    */ 
/* 30 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 35 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 40 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 45 */     Collection lines = new ArrayList();
/* 46 */     lines.add("CSTASysStatStopConfEvent ::=");
/* 47 */     lines.add("{");
/* 48 */     String indent = "  ";
/* 49 */     lines.addAll(ASNNull.print(indent));
/* 50 */     lines.add("}");
/* 51 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASysStatStopConfEvent
 * JD-Core Version:    0.5.4
 */