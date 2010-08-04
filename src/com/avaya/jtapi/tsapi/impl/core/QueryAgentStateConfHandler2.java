/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryAgentStateConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentStateConfEvent;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ 
/*      */ final class QueryAgentStateConfHandler2
/*      */   implements ConfHandler
/*      */ {
/*      */   TSDevice device;
/*      */   String acdName;
/*      */   String agentID;
/* 4634 */   int agentState = -1;
/* 4635 */   int workMode = 0;
/* 4636 */   int state = 0;
/*      */ 
/*      */   QueryAgentStateConfHandler2(TSDevice _device, String _acdName, String _agentID)
/*      */   {
/* 4640 */     this.device = _device;
/* 4641 */     this.acdName = _acdName;
/* 4642 */     this.agentID = _agentID;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 4647 */     if ((event == null) || (!(event.getEvent() instanceof CSTAQueryAgentStateConfEvent)))
/*      */     {
/* 4650 */       return;
/*      */     }
/*      */ 
/* 4654 */     CSTAQueryAgentStateConfEvent agentStateConf = (CSTAQueryAgentStateConfEvent)event.getEvent();
/* 4655 */     this.agentState = agentStateConf.getAgentState();
/* 4656 */     if ((this.agentState == 1) || (!(event.getPrivData() instanceof LucentQueryAgentStateConfEvent))) {
/*      */       return;
/*      */     }
/* 4659 */     short tsapiWorkMode = ((LucentQueryAgentStateConfEvent)event.getPrivData()).getWorkMode();
/* 4660 */     if (tsapiWorkMode == 3)
/* 4661 */       this.workMode = 1;
/* 4662 */     else if (tsapiWorkMode == 4)
/* 4663 */       this.workMode = 2;
/* 4664 */     short talkState = ((LucentQueryAgentStateConfEvent)event.getPrivData()).getTalkState();
/* 4665 */     if (talkState == 0)
/* 4666 */       this.state = 7;
/*      */   }
/*      */ 
/*      */   int getAgentState()
/*      */   {
/* 4673 */     return this.agentState;
/*      */   }
/*      */ 
/*      */   int getWorkMode()
/*      */   {
/* 4678 */     return this.workMode;
/*      */   }
/*      */ 
/*      */   int getState()
/*      */   {
/* 4683 */     return this.state;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.QueryAgentStateConfHandler2
 * JD-Core Version:    0.5.4
 */