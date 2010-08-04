/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV6ConsultationCall extends LucentConsultationCall
/*    */ {
/*    */   public static final int PDU = 109;
/*    */ 
/*    */   public LucentV6ConsultationCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentV6ConsultationCall(String _destRoute, boolean _priorityCalling, LucentUserToUserInfo _userInfo)
/*    */   {
/* 28 */     super(_destRoute, _priorityCalling, _userInfo);
/*    */   }
/*    */ 
/*    */   public static LucentConsultationCall decode(InputStream in)
/*    */   {
/* 34 */     LucentV6ConsultationCall _this = new LucentV6ConsultationCall();
/* 35 */     _this.doDecode(in);
/*    */ 
/* 37 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 42 */     super.decodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 46 */     super.encodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 51 */     Collection lines = new ArrayList();
/*    */ 
/* 53 */     lines.add("LucentV6ConsultationCall ::=");
/* 54 */     lines.add("{");
/*    */ 
/* 56 */     String indent = "  ";
/*    */ 
/* 58 */     lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
/* 59 */     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
/* 60 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/*    */ 
/* 62 */     lines.add("}");
/* 63 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 68 */     return 109;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6ConsultationCall
 * JD-Core Version:    0.5.4
 */