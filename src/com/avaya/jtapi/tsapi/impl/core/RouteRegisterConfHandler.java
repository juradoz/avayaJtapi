/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterReqConfEvent;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ 
/*      */ final class RouteRegisterConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSDevice device;
/*      */ 
/*      */   RouteRegisterConfHandler(TSDevice _device)
/*      */   {
/* 4566 */     this.device = _device;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 4571 */     if ((event == null) || (!(event.getEvent() instanceof CSTARouteRegisterReqConfEvent)))
/*      */     {
/* 4574 */       return;
/*      */     }
/*      */ 
/* 4577 */     CSTARouteRegisterReqConfEvent routeRegisterConf = (CSTARouteRegisterReqConfEvent)event.getEvent();
/* 4578 */     this.device.registerReqID = routeRegisterConf.getRegisterReqID();
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.RouteRegisterConfHandler
 * JD-Core Version:    0.5.4
 */