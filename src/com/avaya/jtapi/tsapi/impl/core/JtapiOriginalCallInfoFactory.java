/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.OriginalCallInfo;
/*     */ import com.avaya.jtapi.tsapi.TsapiTrunk;
/*     */ import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTACallOriginatorInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentOriginalCallInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV5OriginalCallInfo;
/*     */ import com.avaya.jtapi.tsapi.csta1.LucentV7OriginalCallInfo;
/*     */ import com.avaya.jtapi.tsapi.impl.TsapiAddress;
/*     */ import com.avaya.jtapi.tsapi.impl.beans.OriginalCallInfoImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.beans.V5OriginalCallInfoImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.beans.V7OriginalCallInfoImpl;
/*     */ 
/*     */ class JtapiOriginalCallInfoFactory
/*     */ {
/*     */   static OriginalCallInfo createOriginalCallInfo(TSProviderImpl provider, LucentOriginalCallInfo csta_obj)
/*     */   {
/* 530 */     if (csta_obj == null)
/*     */     {
/* 532 */       return null;
/*     */     }
/* 534 */     if (csta_obj instanceof LucentV7OriginalCallInfo)
/*     */     {
/* 536 */       return promoteV7OriginalCallInfo(provider, (LucentV7OriginalCallInfo)csta_obj, null);
/*     */     }
/* 538 */     if (csta_obj instanceof LucentV5OriginalCallInfo)
/*     */     {
/* 540 */       return promoteV5OriginalCallInfo(provider, (LucentV5OriginalCallInfo)csta_obj, null);
/*     */     }
/*     */ 
/* 544 */     return promoteOriginalCallInfo(provider, csta_obj, null);
/*     */   }
/*     */ 
/*     */   static OriginalCallInfo promoteOriginalCallInfo(TSProviderImpl provider, LucentOriginalCallInfo oci_csta, OriginalCallInfoImpl obj)
/*     */   {
/* 565 */     if (oci_csta == null)
/*     */     {
/* 567 */       return null;
/*     */     }
/*     */ 
/* 570 */     OriginalCallInfoImpl jtapi_obj = null;
/*     */ 
/* 573 */     TsapiAddress callingDevice = null;
/* 574 */     TsapiAddress calledDevice = null;
/* 575 */     TsapiTrunk trunk = null;
/*     */ 
/* 577 */     if (oci_csta.getCallingDevice_asn() != null)
/*     */     {
/* 579 */       callingDevice = TsapiPromoter.promoteDeviceIDToAddress(provider, oci_csta.getCallingDevice_asn());
/*     */     }
/*     */ 
/* 583 */     if (oci_csta.getCalledDevice_asn() != null)
/*     */     {
/* 585 */       calledDevice = TsapiPromoter.promoteDeviceIDToAddress(provider, oci_csta.getCalledDevice_asn());
/*     */     }
/*     */ 
/* 588 */     trunk = TsapiPromoter.promoteTrunk(provider, oci_csta.getTrunkGroup(), oci_csta.getTrunkMember());
/*     */ 
/* 592 */     if ((obj == null) && (callingDevice == null) && (calledDevice == null) && (trunk == null))
/*     */     {
/* 595 */       return null;
/*     */     }
/*     */ 
/* 598 */     if (obj == null)
/*     */     {
/* 601 */       jtapi_obj = new OriginalCallInfoImpl();
/*     */     }
/*     */     else
/*     */     {
/* 606 */       jtapi_obj = obj;
/*     */     }
/* 608 */     if (oci_csta.getLookaheadInfo() != null) {
/* 609 */       jtapi_obj.setLookaheadInfo(TsapiPromoter.promoteLookaheadInfo(oci_csta.getLookaheadInfo()));
/*     */     }
/* 611 */     if (oci_csta.getUserEnteredCode() != null) {
/* 612 */       jtapi_obj.setUserEnteredCode(TsapiPromoter.promoteUserEnteredCode(provider, oci_csta.getUserEnteredCode()));
/*     */     }
/* 614 */     if (oci_csta.getUserToUserInfo() != null) {
/* 615 */       jtapi_obj.setUserInfo(TsapiPromoter.promoteUserToUserInfo(oci_csta.getUserToUserInfo()));
/*     */     }
/*     */ 
/* 618 */     jtapi_obj.setCallingDevice(callingDevice);
/* 619 */     jtapi_obj.setCalledDevice(calledDevice);
/* 620 */     jtapi_obj.setTrunk(trunk);
/* 621 */     jtapi_obj.setReason(oci_csta.getReason());
/*     */ 
/* 623 */     return jtapi_obj;
/*     */   }
/*     */ 
/*     */   static OriginalCallInfo promoteV5OriginalCallInfo(TSProviderImpl provider, LucentV5OriginalCallInfo oci_csta, V5OriginalCallInfoImpl obj)
/*     */   {
/* 645 */     if (oci_csta == null)
/*     */     {
/* 648 */       return null;
/*     */     }
/*     */ 
/* 654 */     String ucid = null;
/* 655 */     CSTACallOriginatorInfo cinfo = null;
/* 656 */     boolean flexBilling = false;
/*     */ 
/* 659 */     ucid = oci_csta.getUCID();
/* 660 */     cinfo = oci_csta.getCallOriginatorInfo();
/* 661 */     flexBilling = oci_csta.canSetBillRate();
/*     */ 
/* 664 */     if ((obj == null) && (ucid == null) && (cinfo == null) && (!flexBilling))
/*     */     {
/* 667 */       return promoteOriginalCallInfo(provider, oci_csta, null);
/*     */     }
/*     */     V5OriginalCallInfoImpl jtapi_obj;
/* 669 */     if (obj == null)
/*     */     {
/* 672 */       jtapi_obj = new V5OriginalCallInfoImpl();
/*     */     }
/*     */     else
/*     */     {
/* 677 */       jtapi_obj = obj;
/*     */     }
/*     */ 
/* 681 */     jtapi_obj.setUCID(ucid);
/* 682 */     jtapi_obj.setFlexibleBilling(flexBilling);
/*     */ 
/* 684 */     if (cinfo != null) {
/* 685 */       jtapi_obj.setHasCallOriginatorType(true);
/* 686 */       jtapi_obj.setCallOriginatorType(cinfo.getCallOriginatorType());
/*     */     }
/*     */ 
/* 690 */     return promoteOriginalCallInfo(provider, oci_csta, jtapi_obj);
/*     */   }
/*     */ 
/*     */   static OriginalCallInfo promoteV7OriginalCallInfo(TSProviderImpl provider, LucentV7OriginalCallInfo oci_csta, V7OriginalCallInfoImpl obj)
/*     */   {
/* 711 */     if (oci_csta == null)
/*     */     {
/* 714 */       return null;
/*     */     }
/*     */ 
/* 723 */     V7DeviceHistoryEntry[] deviceHistory = TsapiPromoter.promoteDeviceHistory(oci_csta.getDeviceHistory());
/*     */ 
/* 726 */     if ((obj == null) && (deviceHistory == null))
/*     */     {
/* 729 */       return promoteV5OriginalCallInfo(provider, oci_csta, null);
/*     */     }
/*     */     V7OriginalCallInfoImpl jtapi_obj;
/* 731 */     if (obj == null)
/*     */     {
/* 734 */       jtapi_obj = new V7OriginalCallInfoImpl();
/*     */     }
/*     */     else
/*     */     {
/* 739 */       jtapi_obj = obj;
/*     */     }
/*     */ 
/* 743 */     jtapi_obj.setDeviceHistory(deviceHistory);
/*     */ 
/* 746 */     return promoteV5OriginalCallInfo(provider, oci_csta, jtapi_obj);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.JtapiOriginalCallInfoFactory
 * JD-Core Version:    0.5.4
 */