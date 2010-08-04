/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentUserCollectCode extends LucentPrivateData
/*    */ {
/*    */   short type;
/*    */   int digitsToBeCollected;
/*    */   int timeout;
/*    */   CSTAConnectionID collectParty;
/*    */   short specificEvent;
/*    */ 
/*    */   public LucentUserCollectCode()
/*    */   {
/* 17 */     this.type = 0;
/*    */   }
/*    */ 
/*    */   public LucentUserCollectCode(short _type, int _digitsToBeCollected, int _timeout, CSTAConnectionID _collectParty, short _specificEvent)
/*    */   {
/* 27 */     this.type = _type;
/* 28 */     this.digitsToBeCollected = _digitsToBeCollected;
/* 29 */     this.timeout = _timeout;
/* 30 */     this.collectParty = _collectParty;
/* 31 */     this.specificEvent = _specificEvent;
/*    */   }
/*    */ 
/*    */   public static void encode(LucentUserCollectCode _this, OutputStream out)
/*    */   {
/* 36 */     if (_this == null)
/*    */     {
/* 38 */       _this = new LucentUserCollectCode();
/*    */     }
/* 40 */     _this.encode(out);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 45 */     CollectCodeType.encode(this.type, memberStream);
/* 46 */     ASNInteger.encode(this.digitsToBeCollected, memberStream);
/* 47 */     ASNInteger.encode(this.timeout, memberStream);
/* 48 */     CSTAConnectionID.encode(this.collectParty, memberStream);
/* 49 */     SpecificEvent.encode(this.specificEvent, memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(LucentUserCollectCode _this, String name, String _indent)
/*    */   {
/* 54 */     Collection lines = new ArrayList();
/*    */ 
/* 56 */     if (_this == null)
/*    */     {
/* 58 */       lines.add(_indent + name + " <null>");
/* 59 */       return lines;
/*    */     }
/* 61 */     if (name != null) {
/* 62 */       lines.add(_indent + name);
/*    */     }
/* 64 */     lines.add(_indent + "{");
/*    */ 
/* 66 */     String indent = _indent + "  ";
/*    */ 
/* 68 */     lines.addAll(CollectCodeType.print(_this.type, "type", indent));
/* 69 */     lines.addAll(ASNInteger.print(_this.digitsToBeCollected, "digitsToBeCollected", indent));
/* 70 */     lines.addAll(ASNInteger.print(_this.timeout, "timeout", indent));
/* 71 */     lines.addAll(CSTAConnectionID.print(_this.collectParty, "collectParty", indent));
/* 72 */     lines.addAll(SpecificEvent.print(_this.specificEvent, "specificEvent", indent));
/*    */ 
/* 74 */     lines.add(_indent + "}");
/* 75 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentUserCollectCode
 * JD-Core Version:    0.5.4
 */