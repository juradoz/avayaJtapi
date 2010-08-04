/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentQueryAgentLoginConfEvent extends LucentPrivateData
/*    */ {
/*    */   int privEventCrossRefID;
/*    */   public static final int PDU = 14;
/*    */ 
/*    */   public static LucentQueryAgentLoginConfEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentQueryAgentLoginConfEvent _this = new LucentQueryAgentLoginConfEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 23 */     this.privEventCrossRefID = LucentPrivEventCrossRefID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 27 */     LucentPrivEventCrossRefID.encode(this.privEventCrossRefID, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 32 */     Collection lines = new ArrayList();
/*    */ 
/* 34 */     lines.add("LucentQueryAgentLoginConfEvent ::=");
/* 35 */     lines.add("{");
/*    */ 
/* 37 */     String indent = "  ";
/*    */ 
/* 39 */     lines.addAll(LucentPrivEventCrossRefID.print(this.privEventCrossRefID, "privEventCrossRefID", indent));
/*    */ 
/* 41 */     lines.add("}");
/* 42 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 47 */     return 14;
/*    */   }
/*    */ 
/*    */   public int getPrivEventCrossRefID()
/*    */   {
/* 53 */     return this.privEventCrossRefID;
/*    */   }
/*    */ 
/*    */   public void setPrivEventCrossRefID(int privEventCrossRefID) {
/* 57 */     this.privEventCrossRefID = privEventCrossRefID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginConfEvent
 * JD-Core Version:    0.5.4
 */