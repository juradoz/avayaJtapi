/*    */ package com.avaya.jtapi.tsapi.impl.events.route;
/*    */ 
/*    */ import javax.telephony.callcenter.RouteSession;
/*    */ import javax.telephony.callcenter.events.RouteEndEvent;
/*    */ 
/*    */ public final class TsapiRouteEndEvent extends TsapiRouteSessionEvent
/*    */   implements RouteEndEvent
/*    */ {
/*    */   public TsapiRouteEndEvent(RouteSession routeSession)
/*    */   {
/* 15 */     super(routeSession);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteEndEvent
 * JD-Core Version:    0.5.4
 */