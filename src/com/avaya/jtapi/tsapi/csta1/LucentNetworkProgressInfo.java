/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentNetworkProgressInfo extends LucentPrivateData
/*    */ {
/*    */   public static final short PL_USER = 0;
/*    */   public static final short PL_PUB_LOCAL = 1;
/*    */   public static final short PL_PUB_REMOTE = 4;
/*    */   public static final short PL_PRIV_REMOTE = 5;
/*    */   public static final short PD_CALL_OFF_ISDN = 1;
/*    */   public static final short PD_DEST_NOT_ISDN = 2;
/*    */   public static final short PD_ORIG_NOT_ISDN = 3;
/*    */   public static final short PD_CALL_ON_ISDN = 4;
/*    */   public static final short PD_INBAND = 8;
/*    */   public short progressLocation;
/*    */   public short progressDescription;
/*    */   static final int PDU = 40;
/*    */ 
/*    */   static LucentNetworkProgressInfo decode(InputStream in)
/*    */   {
/* 46 */     LucentNetworkProgressInfo _this = new LucentNetworkProgressInfo();
/* 47 */     _this.doDecode(in);
/*    */ 
/* 49 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 54 */     this.progressLocation = ProgressLocation.decode(memberStream);
/* 55 */     this.progressDescription = ProgressDescription.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 60 */     ProgressLocation.encode(this.progressLocation, memberStream);
/* 61 */     ProgressDescription.encode(this.progressDescription, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 66 */     Collection lines = new ArrayList();
/*    */ 
/* 68 */     lines.add("NetworkProgressInfo ::=");
/* 69 */     lines.add("{");
/*    */ 
/* 71 */     String indent = "  ";
/*    */ 
/* 73 */     lines.addAll(ProgressLocation.print(this.progressLocation, "progressLocation", indent));
/* 74 */     lines.addAll(ProgressDescription.print(this.progressDescription, "progressDescription", indent));
/*    */ 
/* 76 */     lines.add("}");
/* 77 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 82 */     return 40;
/*    */   }
/*    */   public short getProgressDescription() {
/* 85 */     return this.progressDescription;
/*    */   }
/*    */ 
/*    */   public void setProgressDescription(short progressDescription) {
/* 89 */     this.progressDescription = progressDescription;
/*    */   }
/*    */ 
/*    */   public short getProgressLocation() {
/* 93 */     return this.progressLocation;
/*    */   }
/*    */ 
/*    */   public void setProgressLocation(short progressLocation) {
/* 97 */     this.progressLocation = progressLocation;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentNetworkProgressInfo
 * JD-Core Version:    0.5.4
 */