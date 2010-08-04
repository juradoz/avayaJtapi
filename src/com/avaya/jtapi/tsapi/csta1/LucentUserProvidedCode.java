/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentUserProvidedCode extends LucentPrivateData
/*    */ {
/*    */   short type;
/*    */   String data;
/*    */ 
/*    */   public LucentUserProvidedCode()
/*    */   {
/* 14 */     this.type = 0;
/*    */   }
/*    */ 
/*    */   public LucentUserProvidedCode(short _type, String _data)
/*    */   {
/* 21 */     this.type = _type;
/* 22 */     this.data = _data;
/*    */   }
/*    */ 
/*    */   public static void encode(LucentUserProvidedCode _this, OutputStream out)
/*    */   {
/* 27 */     if (_this == null)
/*    */     {
/* 29 */       _this = new LucentUserProvidedCode();
/*    */     }
/* 31 */     _this.encode(out);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 36 */     ProvidedCodeType.encode(this.type, memberStream);
/* 37 */     ASNIA5String.encode(this.data, memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(LucentUserProvidedCode _this, String name, String _indent)
/*    */   {
/* 42 */     Collection lines = new ArrayList();
/*    */ 
/* 44 */     if (_this == null)
/*    */     {
/* 46 */       lines.add(_indent + name + " <null>");
/* 47 */       return lines;
/*    */     }
/* 49 */     if (name != null) {
/* 50 */       lines.add(_indent + name);
/*    */     }
/* 52 */     lines.add(_indent + "{");
/*    */ 
/* 54 */     String indent = _indent + "  ";
/*    */ 
/* 56 */     lines.addAll(ProvidedCodeType.print(_this.type, "type", indent));
/* 57 */     lines.addAll(ASNIA5String.print(_this.data, "data", indent));
/*    */ 
/* 59 */     lines.add(_indent + "}");
/* 60 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentUserProvidedCode
 * JD-Core Version:    0.5.4
 */