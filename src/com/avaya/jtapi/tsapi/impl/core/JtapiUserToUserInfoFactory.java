/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.Q931UserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentQ931UserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
/*     */ 
/*     */ class JtapiUserToUserInfoFactory
/*     */ {
/*     */   static UserToUserInfo createUserToUserInfo(LucentUserToUserInfo csta_obj)
/*     */   {
/* 479 */     if (csta_obj == null)
/*     */     {
/* 481 */       return null;
/*     */     }
/*     */ 
/* 484 */     if (csta_obj instanceof LucentQ931UserToUserInfo)
/*     */     {
/* 486 */       return new Q931UserToUserInfo(csta_obj.getBytes());
/*     */     }
/*     */ 
/* 490 */     return new UserToUserInfo(csta_obj.getBytes(), csta_obj.getType());
/*     */   }
/*     */ 
/*     */   static LucentUserToUserInfo createUserToUserInfo(UserToUserInfo uui)
/*     */   {
/* 497 */     if (uui == null)
/*     */     {
/* 499 */       return null;
/*     */     }
/* 501 */     if (uui instanceof Q931UserToUserInfo)
/*     */     {
/* 503 */       return new LucentQ931UserToUserInfo(uui.getBytes());
/*     */     }
/*     */ 
/* 507 */     return new LucentUserToUserInfo(uui.getBytes(), uui.getType());
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.JtapiUserToUserInfoFactory
 * JD-Core Version:    0.5.4
 */