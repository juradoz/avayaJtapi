 package com.avaya.jtapi.tsapi;
 
 public class LucentV5AgentStateInfo extends LucentAgentStateInfoEx
 {
   public int reasonCode;
 
   public LucentV5AgentStateInfo(int _state, int _workMode, int _reasonCode)
   {
     super(_state, _workMode);
     this.reasonCode = _reasonCode;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentV5AgentStateInfo
 * JD-Core Version:    0.5.4
 */