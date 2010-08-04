/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.LookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.V5LookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentLookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV5LookaheadInfo;
/*     */ 
/*     */ class JtapiLookaheadInfoFactory
/*     */ {
/*     */   static LookaheadInfo createLookaheadInfo(LucentLookaheadInfo csta_obj)
/*     */   {
/* 766 */     if (csta_obj == null)
/*     */     {
/* 768 */       return null;
/*     */     }
/* 770 */     if (csta_obj instanceof LucentV5LookaheadInfo)
/*     */     {
/* 772 */       return promoteV5LookaheadInfo((LucentV5LookaheadInfo)csta_obj, null);
/*     */     }
/*     */ 
/* 776 */     return promoteLookaheadInfo(csta_obj, null);
/*     */   }
/*     */ 
/*     */   static LookaheadInfo promoteLookaheadInfo(LucentLookaheadInfo csta_obj, LookaheadInfo obj)
/*     */   {
/* 797 */     if (csta_obj == null)
/*     */     {
/* 799 */       return null;
/*     */     }
/*     */ 
/* 802 */     LookaheadInfo jtapi_obj = null;
/*     */ 
/* 813 */     short type = csta_obj.getType();
/* 814 */     short priority = csta_obj.getPriority();
/* 815 */     int hours = csta_obj.getHours();
/* 816 */     int minutes = csta_obj.getMinutes();
/* 817 */     int seconds = csta_obj.getSeconds();
/* 818 */     String sourceVDN = csta_obj.getSourceVDN();
/*     */ 
/* 820 */     if (type == -1)
/*     */     {
/* 823 */       return null;
/*     */     }
/*     */ 
/* 826 */     if (obj == null)
/*     */     {
/* 829 */       jtapi_obj = new LookaheadInfo();
/*     */     }
/*     */     else
/*     */     {
/* 834 */       jtapi_obj = obj;
/*     */     }
/*     */ 
/* 838 */     jtapi_obj.setType(type);
/* 839 */     jtapi_obj.setPriority(priority);
/* 840 */     jtapi_obj.setHours(hours);
/* 841 */     jtapi_obj.setMinutes(minutes);
/* 842 */     jtapi_obj.setSeconds(seconds);
/* 843 */     jtapi_obj.setSourceVDN(sourceVDN);
/*     */ 
/* 846 */     return jtapi_obj;
/*     */   }
/*     */ 
/*     */   static LookaheadInfo promoteV5LookaheadInfo(LucentV5LookaheadInfo csta_obj, V5LookaheadInfo obj)
/*     */   {
/* 871 */     if (csta_obj == null)
/*     */     {
/* 874 */       return null;
/*     */     }
/*     */     V5LookaheadInfo jtapi_obj;
/* 895 */     if (obj == null)
/*     */     {
/* 903 */       jtapi_obj = new V5LookaheadInfo();
/*     */     }
/*     */     else
/*     */     {
/* 908 */       jtapi_obj = obj;
/*     */     }
/*     */ 
/* 914 */     return promoteLookaheadInfo(csta_obj, jtapi_obj);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.JtapiLookaheadInfoFactory
 * JD-Core Version:    0.5.4
 */