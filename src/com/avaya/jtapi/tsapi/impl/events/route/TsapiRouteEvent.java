/*    */ package com.avaya.jtapi.tsapi.impl.events.route;
/*    */ 
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.Terminal;
/*    */ import javax.telephony.callcenter.RouteAddress;
/*    */ import javax.telephony.callcenter.RouteSession;
/*    */ import javax.telephony.callcenter.events.RouteEvent;
/*    */ 
/*    */ public final class TsapiRouteEvent extends TsapiRouteSessionEvent
/*    */   implements RouteEvent
/*    */ {
/*    */   private RouteAddress currentRouteAddress;
/*    */   private Address callingAddress;
/*    */   private Terminal callingTerminal;
/*    */   private int routeSelectAlgorithm;
/*    */   private String isdnSetupMessage;
/*    */ 
/*    */   public RouteAddress getCurrentRouteAddress()
/*    */   {
/* 24 */     return this.currentRouteAddress;
/*    */   }
/*    */ 
/*    */   public Address getCallingAddress()
/*    */   {
/* 30 */     return this.callingAddress;
/*    */   }
/*    */ 
/*    */   public Terminal getCallingTerminal()
/*    */   {
/* 36 */     return this.callingTerminal;
/*    */   }
/*    */ 
/*    */   public int getRouteSelectAlgorithm()
/*    */   {
/* 42 */     return this.routeSelectAlgorithm;
/*    */   }
/*    */ 
/*    */   public String getSetupInformation()
/*    */   {
/* 48 */     return this.isdnSetupMessage;
/*    */   }
/*    */ 
/*    */   public TsapiRouteEvent(RouteSession routeSession, RouteAddress currentRouteAddress, Address callingAddress, Terminal callingTerminal, int routeSelectAlgorithm, String isdnSetupMessage)
/*    */   {
/* 60 */     super(routeSession);
/* 61 */     this.currentRouteAddress = currentRouteAddress;
/* 62 */     this.callingAddress = callingAddress;
/* 63 */     this.callingTerminal = callingTerminal;
/* 64 */     this.routeSelectAlgorithm = routeSelectAlgorithm;
/* 65 */     this.isdnSetupMessage = isdnSetupMessage;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteEvent
 * JD-Core Version:    0.5.4
 */