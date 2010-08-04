/*    */ package com.avaya.jtapi.tsapi.impl.events.route;
/*    */ 
/*    */ import javax.telephony.callcenter.RouteSession;
/*    */ import javax.telephony.callcenter.events.ReRouteEvent;
/*    */ 
/*    */ public final class TsapiReRouteEvent extends TsapiRouteSessionEvent
/*    */   implements ReRouteEvent
/*    */ {
/*    */   public TsapiReRouteEvent(RouteSession routeSession)
/*    */   {
/* 15 */     super(routeSession);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.route.TsapiReRouteEvent
 * JD-Core Version:    0.5.4
 */