/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentQueryDeviceInfoConfEvent extends LucentPrivateData
/*    */ {
/*    */   short extensionClass;
/*    */   static final int PDU = 20;
/*    */ 
/*    */   public static LucentQueryDeviceInfoConfEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentQueryDeviceInfoConfEvent _this = new LucentQueryDeviceInfoConfEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 23 */     this.extensionClass = LucentExtensionClass.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 28 */     LucentExtensionClass.encode(this.extensionClass, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 33 */     Collection lines = new ArrayList();
/*    */ 
/* 35 */     lines.add("LucentQueryDeviceInfoConfEvent ::=");
/* 36 */     lines.add("{");
/*    */ 
/* 38 */     String indent = "  ";
/*    */ 
/* 40 */     lines.addAll(LucentExtensionClass.print(this.extensionClass, "extensionClass", indent));
/*    */ 
/* 42 */     lines.add("}");
/* 43 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 48 */     return 20;
/*    */   }
/*    */ 
/*    */   public short getExtensionClass()
/*    */   {
/* 54 */     return this.extensionClass;
/*    */   }
/*    */ 
/*    */   public void setExtensionClass(short extensionClass) {
/* 58 */     this.extensionClass = extensionClass;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceInfoConfEvent
 * JD-Core Version:    0.5.4
 */