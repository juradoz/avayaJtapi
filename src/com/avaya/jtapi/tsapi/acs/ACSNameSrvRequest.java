/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSNameSrvRequest extends ACSRequest
/*    */ {
/*    */   short streamType;
/*    */   public static final int PDU = 10;
/*    */ 
/*    */   public ACSNameSrvRequest(short _streamType)
/*    */   {
/* 17 */     this.streamType = _streamType;
/*    */   }
/*    */ 
/*    */   public ACSNameSrvRequest()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 26 */     StreamType.encode(this.streamType, memberStream);
/*    */   }
/*    */ 
/*    */   public static ACSNameSrvRequest decode(InputStream in)
/*    */   {
/* 31 */     ACSNameSrvRequest _this = new ACSNameSrvRequest();
/* 32 */     _this.doDecode(in);
/*    */ 
/* 34 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 40 */     StreamType.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 46 */     Collection lines = new ArrayList();
/* 47 */     lines.add("ACSNameSrvRequest ::=");
/* 48 */     lines.add("{");
/*    */ 
/* 50 */     String indent = "  ";
/*    */ 
/* 52 */     lines.addAll(StreamType.print(this.streamType, "streamType", indent));
/*    */ 
/* 54 */     lines.add("}");
/* 55 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 60 */     return 10;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSNameSrvRequest
 * JD-Core Version:    0.5.4
 */