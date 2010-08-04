/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentSingleStepConferenceCallConfEvent extends LucentPrivateData
/*    */   implements HasUCID
/*    */ {
/*    */   CSTAConnectionID newCall;
/*    */   CSTAConnection[] connList;
/*    */   String ucid;
/*    */   static final int PDU = 66;
/*    */ 
/*    */   public LucentSingleStepConferenceCallConfEvent(CSTAConnectionID _newCall, CSTAConnection[] _connList, String _ucid)
/*    */   {
/* 15 */     this.newCall = _newCall;
/* 16 */     this.connList = _connList;
/* 17 */     this.ucid = _ucid;
/*    */   }
/*    */ 
/*    */   public LucentSingleStepConferenceCallConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public static LucentSingleStepConferenceCallConfEvent decode(InputStream in) {
/* 25 */     LucentSingleStepConferenceCallConfEvent _this = new LucentSingleStepConferenceCallConfEvent();
/* 26 */     _this.doDecode(in);
/*    */ 
/* 28 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 33 */     CSTAConnectionID.encode(this.newCall, memberStream);
/* 34 */     ConnectionList.encode(this.connList, memberStream);
/* 35 */     UCID.encode(this.ucid, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 40 */     this.newCall = CSTAConnectionID.decode(memberStream);
/* 41 */     this.connList = ConnectionList.decode(memberStream);
/* 42 */     this.ucid = UCID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 47 */     Collection lines = new ArrayList();
/*    */ 
/* 49 */     lines.add("LucentSingleStepConferenceCallConfEvent ::=");
/* 50 */     lines.add("{");
/*    */ 
/* 52 */     String indent = "  ";
/*    */ 
/* 54 */     lines.addAll(CSTAConnectionID.print(this.newCall, "newCall", indent));
/* 55 */     lines.addAll(ConnectionList.print(this.connList, "connList", indent));
/* 56 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/*    */ 
/* 58 */     lines.add("}");
/* 59 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 64 */     return 66;
/*    */   }
/*    */ 
/*    */   public CSTAConnection[] getConnList()
/*    */   {
/* 70 */     return this.connList;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getNewCall()
/*    */   {
/* 78 */     return this.newCall;
/*    */   }
/*    */ 
/*    */   public String getUcid()
/*    */   {
/* 86 */     return this.ucid;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSingleStepConferenceCallConfEvent
 * JD-Core Version:    0.5.4
 */