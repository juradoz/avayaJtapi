/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryAgentStateConfEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentStateConfEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV5QueryAgentStateConfEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV6QueryAgentStateConfEvent;
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*     */ 
/*     */ final class QueryAgentStateConfHandler
/*     */   implements ConfHandler
/*     */ {
/*     */   TSAgent agent;
/*     */ 
/*     */   QueryAgentStateConfHandler(TSAgent _agent)
/*     */   {
/* 698 */     this.agent = _agent;
/*     */   }
/*     */ 
/*     */   public void handleConf(CSTAEvent event)
/*     */   {
/* 703 */     if ((event == null) || (!(event.getEvent() instanceof CSTAQueryAgentStateConfEvent)))
/*     */     {
/* 706 */       return;
/*     */     }
/*     */ 
/* 710 */     CSTAQueryAgentStateConfEvent agentStateConf = (CSTAQueryAgentStateConfEvent)event.getEvent();
/* 711 */     int agentState = agentStateConf.getAgentState();
/* 712 */     int _workMode = 0;
/* 713 */     int _lucentworkmode = -1;
/* 714 */     int _reasonCode = 0;
/* 715 */     int _pendingState = 0;
/* 716 */     int _pendingReasonCode = 0;
/* 717 */     boolean agentIsBusy = false;
/* 718 */     if (event.getPrivData() instanceof LucentQueryAgentStateConfEvent)
/*     */     {
/* 720 */       short tsapiWorkMode = ((LucentQueryAgentStateConfEvent)event.getPrivData()).getWorkMode();
/*     */ 
/* 723 */       _lucentworkmode = tsapiWorkMode;
/*     */ 
/* 726 */       if (tsapiWorkMode == 3)
/* 727 */         _workMode = 1;
/* 728 */       else if (tsapiWorkMode == 4)
/* 729 */         _workMode = 2;
/* 730 */       short talkState = ((LucentQueryAgentStateConfEvent)event.getPrivData()).getTalkState();
/* 731 */       if (talkState == 0) {
/* 732 */         agentIsBusy = true;
/*     */       }
/* 734 */       if (event.getPrivData() instanceof LucentV5QueryAgentStateConfEvent)
/*     */       {
/* 736 */         _reasonCode = ((LucentV5QueryAgentStateConfEvent)event.getPrivData()).getReasonCode();
/* 737 */         if (event.getPrivData() instanceof LucentV6QueryAgentStateConfEvent)
/*     */         {
/* 741 */           int pendingWorkMode = ((LucentV6QueryAgentStateConfEvent)event.getPrivData()).getPendingWorkMode();
/* 742 */           if (pendingWorkMode == 1)
/*     */           {
/* 744 */             _pendingState = 3;
/*     */           }
/* 746 */           else if (pendingWorkMode == 2)
/*     */           {
/* 748 */             _pendingState = 5;
/*     */           }
/* 750 */           _pendingReasonCode = ((LucentV6QueryAgentStateConfEvent)event.getPrivData()).getPendingReasonCode();
/*     */         }
/*     */       }
/*     */     }
/* 754 */     if (agentIsBusy)
/*     */     {
/* 756 */       this.agent.updateState(7, _workMode, _reasonCode, _pendingState, _pendingReasonCode, _lucentworkmode, null);
/*     */     }
/*     */     else
/*     */     {
/* 760 */       switch (agentState)
/*     */       {
/*     */       case 0:
/* 763 */         this.agent.updateState(3, _workMode, _reasonCode, _pendingState, _pendingReasonCode, _lucentworkmode, null);
/* 764 */         break;
/*     */       case 1:
/* 766 */         this.agent.updateState(2, _workMode, _reasonCode, _pendingState, _pendingReasonCode, _lucentworkmode, null);
/* 767 */         break;
/*     */       case 2:
/* 769 */         this.agent.updateState(4, _workMode, _reasonCode, _pendingState, _pendingReasonCode, _lucentworkmode, null);
/* 770 */         break;
/*     */       case 3:
/* 772 */         this.agent.updateState(5, _workMode, _reasonCode, _pendingState, _pendingReasonCode, _lucentworkmode, null);
/* 773 */         break;
/*     */       case 4:
/* 775 */         this.agent.updateState(6, _workMode, _reasonCode, _pendingState, _pendingReasonCode, _lucentworkmode, null);
/* 776 */         break;
/*     */       default:
/* 778 */         this.agent.updateState(0, _workMode, _reasonCode, _pendingState, _pendingReasonCode, _lucentworkmode, null);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.QueryAgentStateConfHandler
 * JD-Core Version:    0.5.4
 */