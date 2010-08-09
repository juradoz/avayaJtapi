 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTATransferCallConfEvent extends CSTAConfirmation
 {
   CSTAConnectionID newCall;
   CSTAConnection[] connList;
   public static final int PDU = 52;
 
   public CSTATransferCallConfEvent()
   {
   }
 
   public CSTATransferCallConfEvent(CSTAConnectionID _newCall, CSTAConnection[] _connList)
   {
     this.newCall = _newCall;
     this.connList = _connList;
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTAConnectionID.encode(this.newCall, memberStream);
     ConnectionList.encode(this.connList, memberStream);
   }
 
   public static CSTATransferCallConfEvent decode(InputStream in) {
     CSTATransferCallConfEvent _this = new CSTATransferCallConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.newCall = CSTAConnectionID.decode(memberStream);
     this.connList = ConnectionList.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTATransferCallConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.newCall, "newCall", indent));
     lines.addAll(ConnectionList.print(this.connList, "connList", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 52;
   }
 
   public CSTAConnection[] getConnList()
   {
     return this.connList;
   }
 
   public CSTAConnectionID getNewCall()
   {
     return this.newCall;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTATransferCallConfEvent
 * JD-Core Version:    0.5.4
 */