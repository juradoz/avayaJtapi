 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.OriginalCallInfo;
 import com.avaya.jtapi.tsapi.TsapiTrunk;
 import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
 import com.avaya.jtapi.tsapi.csta1.CSTACallOriginatorInfo;
 import com.avaya.jtapi.tsapi.csta1.LucentOriginalCallInfo;
 import com.avaya.jtapi.tsapi.csta1.LucentV5OriginalCallInfo;
 import com.avaya.jtapi.tsapi.csta1.LucentV7OriginalCallInfo;
 import com.avaya.jtapi.tsapi.impl.TsapiAddress;
 import com.avaya.jtapi.tsapi.impl.beans.OriginalCallInfoImpl;
 import com.avaya.jtapi.tsapi.impl.beans.V5OriginalCallInfoImpl;
 import com.avaya.jtapi.tsapi.impl.beans.V7OriginalCallInfoImpl;
 
 class JtapiOriginalCallInfoFactory
 {
   static OriginalCallInfo createOriginalCallInfo(TSProviderImpl provider, LucentOriginalCallInfo csta_obj)
   {
     if (csta_obj == null)
     {
       return null;
     }
     if (csta_obj instanceof LucentV7OriginalCallInfo)
     {
       return promoteV7OriginalCallInfo(provider, (LucentV7OriginalCallInfo)csta_obj, null);
     }
     if (csta_obj instanceof LucentV5OriginalCallInfo)
     {
       return promoteV5OriginalCallInfo(provider, (LucentV5OriginalCallInfo)csta_obj, null);
     }
 
     return promoteOriginalCallInfo(provider, csta_obj, null);
   }
 
   static OriginalCallInfo promoteOriginalCallInfo(TSProviderImpl provider, LucentOriginalCallInfo oci_csta, OriginalCallInfoImpl obj)
   {
     if (oci_csta == null)
     {
       return null;
     }
 
     OriginalCallInfoImpl jtapi_obj = null;
 
     TsapiAddress callingDevice = null;
     TsapiAddress calledDevice = null;
     TsapiTrunk trunk = null;
 
     if (oci_csta.getCallingDevice_asn() != null)
     {
       callingDevice = TsapiPromoter.promoteDeviceIDToAddress(provider, oci_csta.getCallingDevice_asn());
     }
 
     if (oci_csta.getCalledDevice_asn() != null)
     {
       calledDevice = TsapiPromoter.promoteDeviceIDToAddress(provider, oci_csta.getCalledDevice_asn());
     }
 
     trunk = TsapiPromoter.promoteTrunk(provider, oci_csta.getTrunkGroup(), oci_csta.getTrunkMember());
 
     if ((obj == null) && (callingDevice == null) && (calledDevice == null) && (trunk == null))
     {
       return null;
     }
 
     if (obj == null)
     {
       jtapi_obj = new OriginalCallInfoImpl();
     }
     else
     {
       jtapi_obj = obj;
     }
     if (oci_csta.getLookaheadInfo() != null) {
       jtapi_obj.setLookaheadInfo(TsapiPromoter.promoteLookaheadInfo(oci_csta.getLookaheadInfo()));
     }
     if (oci_csta.getUserEnteredCode() != null) {
       jtapi_obj.setUserEnteredCode(TsapiPromoter.promoteUserEnteredCode(provider, oci_csta.getUserEnteredCode()));
     }
     if (oci_csta.getUserToUserInfo() != null) {
       jtapi_obj.setUserInfo(TsapiPromoter.promoteUserToUserInfo(oci_csta.getUserToUserInfo()));
     }
 
     jtapi_obj.setCallingDevice(callingDevice);
     jtapi_obj.setCalledDevice(calledDevice);
     jtapi_obj.setTrunk(trunk);
     jtapi_obj.setReason(oci_csta.getReason());
 
     return jtapi_obj;
   }
 
   static OriginalCallInfo promoteV5OriginalCallInfo(TSProviderImpl provider, LucentV5OriginalCallInfo oci_csta, V5OriginalCallInfoImpl obj)
   {
     if (oci_csta == null)
     {
       return null;
     }
 
     String ucid = null;
     CSTACallOriginatorInfo cinfo = null;
     boolean flexBilling = false;
 
     ucid = oci_csta.getUCID();
     cinfo = oci_csta.getCallOriginatorInfo();
     flexBilling = oci_csta.canSetBillRate();
 
     if ((obj == null) && (ucid == null) && (cinfo == null) && (!flexBilling))
     {
       return promoteOriginalCallInfo(provider, oci_csta, null);
     }
     V5OriginalCallInfoImpl jtapi_obj;
     if (obj == null)
     {
       jtapi_obj = new V5OriginalCallInfoImpl();
     }
     else
     {
       jtapi_obj = obj;
     }
 
     jtapi_obj.setUCID(ucid);
     jtapi_obj.setFlexibleBilling(flexBilling);
 
     if (cinfo != null) {
       jtapi_obj.setHasCallOriginatorType(true);
       jtapi_obj.setCallOriginatorType(cinfo.getCallOriginatorType());
     }
 
     return promoteOriginalCallInfo(provider, oci_csta, jtapi_obj);
   }
 
   static OriginalCallInfo promoteV7OriginalCallInfo(TSProviderImpl provider, LucentV7OriginalCallInfo oci_csta, V7OriginalCallInfoImpl obj)
   {
     if (oci_csta == null)
     {
       return null;
     }
 
     V7DeviceHistoryEntry[] deviceHistory = TsapiPromoter.promoteDeviceHistory(oci_csta.getDeviceHistory());
 
     if ((obj == null) && (deviceHistory == null))
     {
       return promoteV5OriginalCallInfo(provider, oci_csta, null);
     }
     V7OriginalCallInfoImpl jtapi_obj;
     if (obj == null)
     {
       jtapi_obj = new V7OriginalCallInfoImpl();
     }
     else
     {
       jtapi_obj = obj;
     }
 
     jtapi_obj.setDeviceHistory(deviceHistory);
 
     return promoteV5OriginalCallInfo(provider, oci_csta, jtapi_obj);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.JtapiOriginalCallInfoFactory
 * JD-Core Version:    0.5.4
 */