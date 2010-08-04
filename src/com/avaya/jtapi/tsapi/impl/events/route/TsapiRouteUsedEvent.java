/*    */ package com.avaya.jtapi.tsapi.impl.events.route;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.ITsapiRouteUsedEvent;
/*    */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.Terminal;
/*    */ import javax.telephony.callcenter.RouteSession;
/*    */ 
/*    */ public class TsapiRouteUsedEvent extends TsapiRouteSessionEvent
/*    */   implements ITsapiRouteUsedEvent
/*    */ {
/*    */   private Address routeUsedAddress;
/*    */   private Terminal routeUsedTerminal;
/*    */   private Terminal callingTerminal;
/*    */   private Address callingAddress;
/*    */   private boolean outOfDomain;
/*    */ 
/*    */   public Terminal getRouteUsed()
/*    */   {
/* 38 */     if (this.routeUsedTerminal == null)
/*    */     {
/* 40 */       throw new TsapiPlatformException(3, 0, "Could not return a Terminal for RouteUsed (" + this.routeUsedAddress.getName() + ") - was probably off-switch");
/*    */     }
/*    */ 
/* 48 */     return this.routeUsedTerminal;
/*    */   }
/*    */ 
/*    */   public Terminal getCallingTerminal()
/*    */   {
/* 55 */     return this.callingTerminal;
/*    */   }
/*    */ 
/*    */   public Address getCallingAddress()
/*    */   {
/* 61 */     return this.callingAddress;
/*    */   }
/*    */ 
/*    */   public boolean getDomain()
/*    */   {
/* 67 */     return this.outOfDomain;
/*    */   }
/*    */ 
/*    */   public Address getRouteUsedAddress()
/*    */   {
/* 75 */     return this.routeUsedAddress;
/*    */   }
/*    */ 
/*    */   public TsapiRouteUsedEvent(RouteSession routeSession, Address routeUsedAddress, Terminal routeUsedTerminal, Address callingAddress, Terminal callingTerminal, boolean outOfDomain)
/*    */   {
/* 87 */     super(routeSession);
/* 88 */     this.routeUsedAddress = routeUsedAddress;
/* 89 */     this.routeUsedTerminal = routeUsedTerminal;
/* 90 */     this.callingAddress = callingAddress;
/* 91 */     this.callingTerminal = callingTerminal;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteUsedEvent
 * JD-Core Version:    0.5.4
 */