/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ public class LucentV6AgentStateInfo extends LucentV5AgentStateInfo
/*    */ {
/*    */   public int pendingState;
/*    */   public int pendingReasonCode;
/*    */ 
/*    */   public LucentV6AgentStateInfo(int _state, int _workMode, int _reasonCode, int _pendingState, int _pendingReasonCode)
/*    */   {
/* 46 */     super(_state, _workMode, _reasonCode);
/* 47 */     this.pendingState = _pendingState;
/* 48 */     this.pendingReasonCode = _pendingReasonCode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentV6AgentStateInfo
 * JD-Core Version:    0.5.4
 */