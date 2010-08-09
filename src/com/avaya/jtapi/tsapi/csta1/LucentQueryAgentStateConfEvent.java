 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentQueryAgentStateConfEvent extends LucentPrivateData
 {
   short workMode;
   short talkState;
   static final int PDU = 17;
 
   static LucentQueryAgentStateConfEvent decode(InputStream in)
   {
     LucentQueryAgentStateConfEvent _this = new LucentQueryAgentStateConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.workMode = LucentWorkMode.decode(memberStream);
     this.talkState = LucentTalkState.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     LucentWorkMode.encode(this.workMode, memberStream);
     LucentTalkState.encode(this.talkState, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentQueryAgentStateConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));
     lines.addAll(LucentTalkState.print(this.talkState, "talkState", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 17;
   }
 
   public short getTalkState()
   {
     return this.talkState;
   }
 
   public short getWorkMode()
   {
     return this.workMode;
   }
 
   public void setTalkState(short talkState) {
     this.talkState = talkState;
   }
 
   public void setWorkMode(short workMode) {
     this.workMode = workMode;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryAgentStateConfEvent
 * JD-Core Version:    0.5.4
 */