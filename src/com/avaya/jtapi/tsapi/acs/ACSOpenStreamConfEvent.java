/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSOpenStreamConfEvent extends ACSConfirmation
/*    */ {
/*    */   String apiVer;
/*    */   String libVer;
/*    */   String tsrvVer;
/*    */   String drvrVer;
/*    */   public static final int PDU = 2;
/*    */ 
/*    */   public ACSOpenStreamConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ACSOpenStreamConfEvent(String _apiVer, String _libVer, String _tsrvVer, String _drvrVer)
/*    */   {
/* 19 */     this.apiVer = _apiVer;
/* 20 */     this.libVer = _libVer;
/* 21 */     this.tsrvVer = _tsrvVer;
/* 22 */     this.drvrVer = _drvrVer;
/*    */   }
/*    */ 
/*    */   public static ACSOpenStreamConfEvent decode(InputStream in)
/*    */   {
/* 27 */     ACSOpenStreamConfEvent _this = new ACSOpenStreamConfEvent();
/* 28 */     _this.doDecode(in);
/*    */ 
/* 30 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 35 */     Version.encode(this.apiVer, memberStream);
/* 36 */     Version.encode(this.libVer, memberStream);
/* 37 */     Version.encode(this.tsrvVer, memberStream);
/* 38 */     Version.encode(this.drvrVer, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 43 */     this.apiVer = Version.decode(memberStream);
/* 44 */     this.libVer = Version.decode(memberStream);
/* 45 */     this.tsrvVer = Version.decode(memberStream);
/* 46 */     this.drvrVer = Version.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 52 */     Collection lines = new ArrayList();
/* 53 */     lines.add("ACSOpenStreamConfEvent ::=");
/* 54 */     lines.add("{");
/*    */ 
/* 56 */     String indent = "  ";
/*    */ 
/* 58 */     lines.addAll(Version.print(this.apiVer, "apiVer", indent));
/* 59 */     lines.addAll(Version.print(this.libVer, "libVer", indent));
/* 60 */     lines.addAll(Version.print(this.tsrvVer, "tsrvVer", indent));
/* 61 */     lines.addAll(Version.print(this.drvrVer, "drvrVer", indent));
/*    */ 
/* 63 */     lines.add("}");
/* 64 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 69 */     return 2;
/*    */   }
/*    */ 
/*    */   public String getApiVer()
/*    */   {
/* 75 */     return this.apiVer;
/*    */   }
/*    */ 
/*    */   public String getDrvrVer()
/*    */   {
/* 83 */     return this.drvrVer;
/*    */   }
/*    */ 
/*    */   public String getLibVer()
/*    */   {
/* 91 */     return this.libVer;
/*    */   }
/*    */ 
/*    */   public String getTsrvVer()
/*    */   {
/* 99 */     return this.tsrvVer;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSOpenStreamConfEvent
 * JD-Core Version:    0.5.4
 */