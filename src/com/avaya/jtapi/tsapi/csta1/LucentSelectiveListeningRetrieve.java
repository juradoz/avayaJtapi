/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentSelectiveListeningRetrieve extends LucentPrivateData
/*    */ {
/*    */   CSTAConnectionID subjectConnection;
/*    */   boolean allParties;
/*    */   CSTAConnectionID selectedParty;
/*    */   public static final int PDU = 69;
/*    */ 
/*    */   public LucentSelectiveListeningRetrieve()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentSelectiveListeningRetrieve(CSTAConnectionID _subjectConnection, boolean _allParties, CSTAConnectionID _selectedParty)
/*    */   {
/* 23 */     this.subjectConnection = _subjectConnection;
/* 24 */     this.allParties = _allParties;
/* 25 */     this.selectedParty = _selectedParty;
/*    */   }
/*    */ 
/*    */   public static LucentSelectiveListeningRetrieve decode(InputStream in)
/*    */   {
/* 30 */     LucentSelectiveListeningRetrieve _this = new LucentSelectiveListeningRetrieve();
/* 31 */     _this.doDecode(in);
/*    */ 
/* 33 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 37 */     this.subjectConnection = CSTAConnectionID.decode(memberStream);
/* 38 */     this.allParties = ASNBoolean.decode(memberStream);
/* 39 */     this.selectedParty = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 43 */     CSTAConnectionID.encode(this.subjectConnection, memberStream);
/* 44 */     ASNBoolean.encode(this.allParties, memberStream);
/* 45 */     CSTAConnectionID.encode(this.selectedParty, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 50 */     Collection lines = new ArrayList();
/*    */ 
/* 52 */     lines.add("LucentSelectiveListeningRetrieve ::=");
/* 53 */     lines.add("{");
/*    */ 
/* 55 */     String indent = "  ";
/*    */ 
/* 57 */     lines.addAll(CSTAConnectionID.print(this.subjectConnection, "subjectConnection", indent));
/* 58 */     lines.addAll(ASNBoolean.print(this.allParties, "allParties", indent));
/* 59 */     lines.addAll(CSTAConnectionID.print(this.selectedParty, "selectedParty", indent));
/*    */ 
/* 61 */     lines.add("}");
/* 62 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 67 */     return 69;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSelectiveListeningRetrieve
 * JD-Core Version:    0.5.4
 */