 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentSelectiveListeningHold extends LucentPrivateData
 {
   CSTAConnectionID subjectConnection;
   boolean allParties;
   CSTAConnectionID selectedParty;
   public static final int PDU = 67;
 
   public LucentSelectiveListeningHold()
   {
   }
 
   public LucentSelectiveListeningHold(CSTAConnectionID _subjectConnection, boolean _allParties, CSTAConnectionID _selectedParty)
   {
     this.subjectConnection = _subjectConnection;
     this.allParties = _allParties;
     this.selectedParty = _selectedParty;
   }
 
   public static LucentSelectiveListeningHold decode(InputStream in) {
     LucentSelectiveListeningHold _this = new LucentSelectiveListeningHold();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream) {
     this.subjectConnection = CSTAConnectionID.decode(memberStream);
     this.allParties = ASNBoolean.decode(memberStream);
     this.selectedParty = CSTAConnectionID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTAConnectionID.encode(this.subjectConnection, memberStream);
     ASNBoolean.encode(this.allParties, memberStream);
     CSTAConnectionID.encode(this.selectedParty, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentSelectiveListeningHold ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.subjectConnection, "subjectConnection", indent));
     lines.addAll(ASNBoolean.print(this.allParties, "allParties", indent));
     lines.addAll(CSTAConnectionID.print(this.selectedParty, "selectedParty", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 67;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSelectiveListeningHold
 * JD-Core Version:    0.5.4
 */