/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentLoggedOnEvent extends LucentPrivateData
/*    */ {
/*    */   short workMode;
/*    */   static final int PDU = 48;
/*    */ 
/*    */   public static LucentLoggedOnEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentLoggedOnEvent _this = new LucentLoggedOnEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 23 */     this.workMode = LucentWorkMode.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 27 */     LucentWorkMode.encode(this.workMode, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 31 */     Collection lines = new ArrayList();
/*    */ 
/* 33 */     lines.add("LucentLoggedOnEvent ::=");
/* 34 */     lines.add("{");
/*    */ 
/* 36 */     String indent = "  ";
/*    */ 
/* 38 */     lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));
/*    */ 
/* 40 */     lines.add("}");
/* 41 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 46 */     return 48;
/*    */   }
/*    */ 
/*    */   public short getWorkMode()
/*    */   {
/* 52 */     return this.workMode;
/*    */   }
/*    */ 
/*    */   public void setWorkMode(short workMode) {
/* 56 */     this.workMode = workMode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentLoggedOnEvent
 * JD-Core Version:    0.5.4
 */