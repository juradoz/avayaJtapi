/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentQueryStationStatusConfEvent extends LucentPrivateData
/*    */ {
/*    */   boolean stationStatus;
/*    */   static final int PDU = 23;
/*    */ 
/*    */   static LucentQueryStationStatusConfEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentQueryStationStatusConfEvent _this = new LucentQueryStationStatusConfEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 23 */     this.stationStatus = ASNBoolean.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 28 */     Collection lines = new ArrayList();
/*    */ 
/* 30 */     lines.add("LucentQueryStationStatusConfEvent ::=");
/* 31 */     lines.add("{");
/*    */ 
/* 33 */     String indent = "  ";
/*    */ 
/* 35 */     lines.addAll(ASNBoolean.print(this.stationStatus, "stationStatus", indent));
/*    */ 
/* 37 */     lines.add("}");
/* 38 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 43 */     return 23;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryStationStatusConfEvent
 * JD-Core Version:    0.5.4
 */