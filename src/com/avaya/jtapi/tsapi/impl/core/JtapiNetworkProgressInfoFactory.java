/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.NetworkProgressInfo;
/*      */ import com.avaya.jtapi.tsapi.TsapiTrunk;
/*      */ import com.avaya.jtapi.tsapi.V5NetworkProgressInfo;
/*      */ import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
/*      */ import com.avaya.jtapi.tsapi.V7NetworkProgressInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentNetworkProgressInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV5NetworkProgressInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV7NetworkProgressInfo;
/*      */ 
/*      */ class JtapiNetworkProgressInfoFactory
/*      */ {
/*      */   static NetworkProgressInfo createNetworkProgressInfo(TSProviderImpl provider, LucentNetworkProgressInfo csta_obj)
/*      */   {
/*  936 */     if (csta_obj == null)
/*      */     {
/*  938 */       return null;
/*      */     }
/*  940 */     if (csta_obj instanceof LucentV7NetworkProgressInfo)
/*      */     {
/*  942 */       return promoteV7NetworkProgressInfo(provider, (LucentV7NetworkProgressInfo)csta_obj, null);
/*      */     }
/*  944 */     if (csta_obj instanceof LucentV5NetworkProgressInfo)
/*      */     {
/*  946 */       return promoteV5NetworkProgressInfo(provider, (LucentV5NetworkProgressInfo)csta_obj, null);
/*      */     }
/*      */ 
/*  950 */     return promoteNetworkProgressInfo(provider, csta_obj, null);
/*      */   }
/*      */ 
/*      */   static NetworkProgressInfo promoteNetworkProgressInfo(TSProviderImpl provider, LucentNetworkProgressInfo csta_obj, NetworkProgressInfo obj)
/*      */   {
/*  971 */     if (csta_obj == null)
/*      */     {
/*  973 */       return null;
/*      */     }
/*      */ 
/*  976 */     NetworkProgressInfo jtapi_obj = null;
/*      */ 
/*  979 */     if (obj == null)
/*      */     {
/*  982 */       jtapi_obj = new NetworkProgressInfo();
/*      */     }
/*      */     else
/*      */     {
/*  987 */       jtapi_obj = obj;
/*      */     }
/*      */ 
/*  991 */     jtapi_obj.progressLocation = csta_obj.progressLocation;
/*  992 */     jtapi_obj.progressDescription = csta_obj.progressDescription;
/*      */ 
/*  995 */     return jtapi_obj;
/*      */   }
/*      */ 
/*      */   static NetworkProgressInfo promoteV5NetworkProgressInfo(TSProviderImpl provider, LucentV5NetworkProgressInfo oci_csta, V5NetworkProgressInfo obj)
/*      */   {
/* 1015 */     if (oci_csta == null)
/*      */     {
/* 1018 */       return null;
/*      */     }
/*      */ 
/* 1024 */     String trunkGroup = null;
/* 1025 */     String trunkMember = null;
/* 1026 */     TsapiTrunk trunk = null;
/*      */ 
/* 1029 */     trunkGroup = oci_csta.getTrunkGroup();
/* 1030 */     trunkMember = oci_csta.getTrunkMember();
/* 1031 */     trunk = TsapiPromoter.promoteTrunk(provider, oci_csta.getTrunkGroup(), oci_csta.getTrunkMember(), 2);
/*      */ 
/* 1034 */     if ((obj == null) && (trunkGroup == null) && (trunkMember == null) && (trunk == null))
/*      */     {
/* 1037 */       return promoteNetworkProgressInfo(provider, oci_csta, null);
/* 1038 */     }if ((obj == null) && (trunkGroup.equals("0")) && (trunkMember.equals("0")) && (trunk == null))
/*      */     {
/* 1041 */       return promoteNetworkProgressInfo(provider, oci_csta, null);
/*      */     }
/*      */     V5NetworkProgressInfo jtapi_obj;
/* 1043 */     if (obj == null)
/*      */     {
/* 1046 */       jtapi_obj = new V5NetworkProgressInfo();
/*      */     }
/*      */     else
/*      */     {
/* 1051 */       jtapi_obj = obj;
/*      */     }
/*      */ 
/* 1055 */     jtapi_obj.setTrunkGroup(trunkGroup);
/* 1056 */     jtapi_obj.setTrunkMember(trunkMember);
/* 1057 */     jtapi_obj.setTsapiTrunk(trunk);
/*      */ 
/* 1060 */     return promoteNetworkProgressInfo(provider, oci_csta, jtapi_obj);
/*      */   }
/*      */ 
/*      */   static NetworkProgressInfo promoteV7NetworkProgressInfo(TSProviderImpl provider, LucentV7NetworkProgressInfo oci_csta, V7NetworkProgressInfo obj)
/*      */   {
/* 1081 */     if (oci_csta == null)
/*      */     {
/* 1084 */       return null;
/*      */     }
/*      */ 
/* 1093 */     V7DeviceHistoryEntry[] deviceHistory = TsapiPromoter.promoteDeviceHistory(oci_csta.getDeviceHistory());
/*      */ 
/* 1096 */     if ((obj == null) && (deviceHistory == null))
/*      */     {
/* 1099 */       return promoteV5NetworkProgressInfo(provider, oci_csta, null);
/*      */     }
/*      */     V7NetworkProgressInfo jtapi_obj;
/* 1101 */     if (obj == null)
/*      */     {
/* 1104 */       jtapi_obj = new V7NetworkProgressInfo();
/*      */     }
/*      */     else
/*      */     {
/* 1109 */       jtapi_obj = obj;
/*      */     }
/*      */ 
/* 1113 */     jtapi_obj.setDeviceHistory(deviceHistory);
/*      */ 
/* 1116 */     return promoteV5NetworkProgressInfo(provider, oci_csta, jtapi_obj);
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.JtapiNetworkProgressInfoFactory
 * JD-Core Version:    0.5.4
 */