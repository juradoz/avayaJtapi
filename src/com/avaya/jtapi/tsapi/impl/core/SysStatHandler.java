/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTALinkStatus;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASysStatReqConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASysStatStartConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASysStatStopConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LinkState;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentLinkStatusEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.SystemStatusFilter;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.HandleConfOnCurrentThread;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ final class SysStatHandler
/*      */   implements ConfHandler, HandleConfOnCurrentThread
/*      */ {
/* 3822 */   private static final Logger log = Logger.getLogger(SysStatHandler.class);
/*      */   private short systemStatus;
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 3825 */     if (event == null) {
/* 3826 */       return;
/*      */     }
/* 3828 */     if (event.getEvent() instanceof CSTASysStatStartConfEvent) {
/* 3829 */       log.info("Filter set at " + SystemStatusFilter.print(((CSTASysStatStartConfEvent)event.getEvent()).getFilter(), "", ""));
/*      */ 
/* 3831 */       if (event.getPrivData() instanceof LucentLinkStatusEvent) {
/* 3832 */         short state = ((LucentLinkStatusEvent)event.getPrivData()).getLinkStatus().getLinkState();
/* 3833 */         log.info("Link state is " + LinkState.print(state, "", ""));
/*      */       }
/*      */     }
/* 3836 */     else if (event.getEvent() instanceof CSTASysStatStopConfEvent) {
/* 3837 */       log.info("Event monitoring was ended successfully");
/*      */     }
/* 3839 */     else if (event.getEvent() instanceof CSTASysStatReqConfEvent) {
/* 3840 */       this.systemStatus = ((CSTASysStatReqConfEvent)event.getEvent()).getSystemStatus();
/*      */     }
/*      */   }
/*      */ 
/*      */   public short getSystemStatus() {
/* 3844 */     return this.systemStatus;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.SysStatHandler
 * JD-Core Version:    0.5.4
 */