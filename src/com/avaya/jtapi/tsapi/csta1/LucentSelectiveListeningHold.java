/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentSelectiveListeningHold extends LucentPrivateData
/*    */ {
/*    */   CSTAConnectionID subjectConnection;
/*    */   boolean allParties;
/*    */   CSTAConnectionID selectedParty;
/*    */   public static final int PDU = 67;
/*    */ 
/*    */   public LucentSelectiveListeningHold()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentSelectiveListeningHold(CSTAConnectionID _subjectConnection, boolean _allParties, CSTAConnectionID _selectedParty)
/*    */   {
/* 23 */     this.subjectConnection = _subjectConnection;
/* 24 */     this.allParties = _allParties;
/* 25 */     this.selectedParty = _selectedParty;
/*    */   }
/*    */ 
/*    */   public static LucentSelectiveListeningHold decode(InputStream in) {
/* 29 */     LucentSelectiveListeningHold _this = new LucentSelectiveListeningHold();
/* 30 */     _this.doDecode(in);
/*    */ 
/* 32 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 36 */     this.subjectConnection = CSTAConnectionID.decode(memberStream);
/* 37 */     this.allParties = ASNBoolean.decode(memberStream);
/* 38 */     this.selectedParty = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 42 */     CSTAConnectionID.encode(this.subjectConnection, memberStream);
/* 43 */     ASNBoolean.encode(this.allParties, memberStream);
/* 44 */     CSTAConnectionID.encode(this.selectedParty, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 49 */     Collection lines = new ArrayList();
/*    */ 
/* 51 */     lines.add("LucentSelectiveListeningHold ::=");
/* 52 */     lines.add("{");
/*    */ 
/* 54 */     String indent = "  ";
/*    */ 
/* 56 */     lines.addAll(CSTAConnectionID.print(this.subjectConnection, "subjectConnection", indent));
/* 57 */     lines.addAll(ASNBoolean.print(this.allParties, "allParties", indent));
/* 58 */     lines.addAll(CSTAConnectionID.print(this.selectedParty, "selectedParty", indent));
/*    */ 
/* 60 */     lines.add("}");
/* 61 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 66 */     return 67;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSelectiveListeningHold
 * JD-Core Version:    0.5.4
 */