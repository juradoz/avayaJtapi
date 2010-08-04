/*    */ package com.avaya.jtapi.tsapi.impl.events.route;
/*    */ 
/*    */ import javax.telephony.callcenter.RouteAddress;
/*    */ import javax.telephony.callcenter.events.RouteCallbackEndedEvent;
/*    */ 
/*    */ public final class TsapiRouteCallbackEndedEvent
/*    */   implements RouteCallbackEndedEvent
/*    */ {
/* 11 */   private RouteAddress routeAddress = null;
/*    */ 
/*    */   public RouteAddress getRouteAddress()
/*    */   {
/* 16 */     return this.routeAddress;
/*    */   }
/*    */ 
/*    */   public TsapiRouteCallbackEndedEvent(RouteAddress routeAddress)
/*    */   {
/* 22 */     this.routeAddress = routeAddress;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteCallbackEndedEvent
 * JD-Core Version:    0.5.4
 */