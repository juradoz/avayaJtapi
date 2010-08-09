package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.NetworkProgressInfo;
import com.avaya.jtapi.tsapi.TsapiTrunk;
import com.avaya.jtapi.tsapi.V5NetworkProgressInfo;
import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
import com.avaya.jtapi.tsapi.V7NetworkProgressInfo;
import com.avaya.jtapi.tsapi.csta1.LucentNetworkProgressInfo;
import com.avaya.jtapi.tsapi.csta1.LucentV5NetworkProgressInfo;
import com.avaya.jtapi.tsapi.csta1.LucentV7NetworkProgressInfo;

class JtapiNetworkProgressInfoFactory {
	static NetworkProgressInfo createNetworkProgressInfo(
			TSProviderImpl provider, LucentNetworkProgressInfo csta_obj) {
		if (csta_obj == null) {
			return null;
		}
		if (csta_obj instanceof LucentV7NetworkProgressInfo) {
			return promoteV7NetworkProgressInfo(provider,
					(LucentV7NetworkProgressInfo) csta_obj, null);
		}
		if (csta_obj instanceof LucentV5NetworkProgressInfo) {
			return promoteV5NetworkProgressInfo(provider,
					(LucentV5NetworkProgressInfo) csta_obj, null);
		}

		return promoteNetworkProgressInfo(provider, csta_obj, null);
	}

	static NetworkProgressInfo promoteNetworkProgressInfo(
			TSProviderImpl provider, LucentNetworkProgressInfo csta_obj,
			NetworkProgressInfo obj) {
		if (csta_obj == null) {
			return null;
		}

		NetworkProgressInfo jtapi_obj = null;

		if (obj == null) {
			jtapi_obj = new NetworkProgressInfo();
		} else {
			jtapi_obj = obj;
		}

		jtapi_obj.progressLocation = csta_obj.progressLocation;
		jtapi_obj.progressDescription = csta_obj.progressDescription;

		return jtapi_obj;
	}

	static NetworkProgressInfo promoteV5NetworkProgressInfo(
			TSProviderImpl provider, LucentV5NetworkProgressInfo oci_csta,
			V5NetworkProgressInfo obj) {
		if (oci_csta == null) {
			return null;
		}

		String trunkGroup = null;
		String trunkMember = null;
		TsapiTrunk trunk = null;

		trunkGroup = oci_csta.getTrunkGroup();
		trunkMember = oci_csta.getTrunkMember();
		trunk = TsapiPromoter.promoteTrunk(provider, oci_csta.getTrunkGroup(),
				oci_csta.getTrunkMember(), 2);

		if ((obj == null) && (trunkGroup == null) && (trunkMember == null)
				&& (trunk == null)) {
			return promoteNetworkProgressInfo(provider, oci_csta, null);
		}
		if ((obj == null) && (trunkGroup.equals("0"))
				&& (trunkMember.equals("0")) && (trunk == null)) {
			return promoteNetworkProgressInfo(provider, oci_csta, null);
		}
		V5NetworkProgressInfo jtapi_obj;
		if (obj == null) {
			jtapi_obj = new V5NetworkProgressInfo();
		} else {
			jtapi_obj = obj;
		}

		jtapi_obj.setTrunkGroup(trunkGroup);
		jtapi_obj.setTrunkMember(trunkMember);
		jtapi_obj.setTsapiTrunk(trunk);

		return promoteNetworkProgressInfo(provider, oci_csta, jtapi_obj);
	}

	static NetworkProgressInfo promoteV7NetworkProgressInfo(
			TSProviderImpl provider, LucentV7NetworkProgressInfo oci_csta,
			V7NetworkProgressInfo obj) {
		if (oci_csta == null) {
			return null;
		}

		V7DeviceHistoryEntry[] deviceHistory = TsapiPromoter
				.promoteDeviceHistory(oci_csta.getDeviceHistory());

		if ((obj == null) && (deviceHistory == null)) {
			return promoteV5NetworkProgressInfo(provider, oci_csta, null);
		}
		V7NetworkProgressInfo jtapi_obj;
		if (obj == null) {
			jtapi_obj = new V7NetworkProgressInfo();
		} else {
			jtapi_obj = obj;
		}

		jtapi_obj.setDeviceHistory(deviceHistory);

		return promoteV5NetworkProgressInfo(provider, oci_csta, jtapi_obj);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.JtapiNetworkProgressInfoFactory JD-Core
 * Version: 0.5.4
 */