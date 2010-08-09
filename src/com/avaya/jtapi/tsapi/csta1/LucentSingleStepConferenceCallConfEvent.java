 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentSingleStepConferenceCallConfEvent extends LucentPrivateData
   implements HasUCID
 {
   CSTAConnectionID newCall;
   CSTAConnection[] connList;
   String ucid;
   static final int PDU = 66;
 
   public LucentSingleStepConferenceCallConfEvent(CSTAConnectionID _newCall, CSTAConnection[] _connList, String _ucid)
   {
     this.newCall = _newCall;
     this.connList = _connList;
     this.ucid = _ucid;
   }
 
   public LucentSingleStepConferenceCallConfEvent()
   {
   }
 
   public static LucentSingleStepConferenceCallConfEvent decode(InputStream in) {
     LucentSingleStepConferenceCallConfEvent _this = new LucentSingleStepConferenceCallConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.newCall, memberStream);
     ConnectionList.encode(this.connList, memberStream);
     UCID.encode(this.ucid, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.newCall = CSTAConnectionID.decode(memberStream);
     this.connList = ConnectionList.decode(memberStream);
     this.ucid = UCID.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentSingleStepConferenceCallConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.newCall, "newCall", indent));
     lines.addAll(ConnectionList.print(this.connList, "connList", indent));
     lines.addAll(UCID.print(this.ucid, "ucid", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 66;
   }
 
   public CSTAConnection[] getConnList()
   {
     return this.connList;
   }
 
   public CSTAConnectionID getNewCall()
   {
     return this.newCall;
   }
 
   public String getUcid()
   {
     return this.ucid;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSingleStepConferenceCallConfEvent
 * JD-Core Version:    0.5.4
 */