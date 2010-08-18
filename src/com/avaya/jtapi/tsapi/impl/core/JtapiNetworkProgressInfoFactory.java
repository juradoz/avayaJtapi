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
			final TSProviderImpl provider,
			final LucentNetworkProgressInfo csta_obj) {
		if (csta_obj == null)
			return null;
		if (csta_obj instanceof LucentV7NetworkProgressInfo)
			return JtapiNetworkProgressInfoFactory
					.promoteV7NetworkProgressInfo(provider,
							(LucentV7NetworkProgressInfo) csta_obj, null);
		if (csta_obj instanceof LucentV5NetworkProgressInfo)
			return JtapiNetworkProgressInfoFactory
					.promoteV5NetworkProgressInfo(provider,
							(LucentV5NetworkProgressInfo) csta_obj, null);

		return JtapiNetworkProgressInfoFactory.promoteNetworkProgressInfo(
				provider, csta_obj, null);
	}

	static NetworkProgressInfo promoteNetworkProgressInfo(
			final TSProviderImpl provider,
			final LucentNetworkProgressInfo csta_obj,
			final NetworkProgressInfo obj) {
		if (csta_obj == null)
			return null;

		NetworkProgressInfo jtapi_obj = null;

		if (obj == null)
			jtapi_obj = new NetworkProgressInfo();
		else
			jtapi_obj = obj;

		jtapi_obj.progressLocation = csta_obj.progressLocation;
		jtapi_obj.progressDescription = csta_obj.progressDescription;

		return jtapi_obj;
	}

	static NetworkProgressInfo promoteV5NetworkProgressInfo(
			final TSProviderImpl provider,
			final LucentV5NetworkProgressInfo oci_csta,
			final V5NetworkProgressInfo obj) {
		if (oci_csta == null)
			return null;

		String trunkGroup = null;
		String trunkMember = null;
		TsapiTrunk trunk = null;

		trunkGroup = oci_csta.getTrunkGroup();
		trunkMember = oci_csta.getTrunkMember();
		trunk = TsapiPromoter.promoteTrunk(provider, oci_csta.getTrunkGroup(),
				oci_csta.getTrunkMember(), 2);

		if (obj == null && trunkGroup == null && trunkMember == null
				&& trunk == null)
			return JtapiNetworkProgressInfoFactory.promoteNetworkProgressInfo(
					provider, oci_csta, null);
		if (obj == null && trunkGroup.equals("0") && trunkMember.equals("0")
				&& trunk == null)
			return JtapiNetworkProgressInfoFactory.promoteNetworkProgressInfo(
					provider, oci_csta, null);
		V5NetworkProgressInfo jtapi_obj;
		if (obj == null)
			jtapi_obj = new V5NetworkProgressInfo();
		else
			jtapi_obj = obj;

		jtapi_obj.setTrunkGroup(trunkGroup);
		jtapi_obj.setTrunkMember(trunkMember);
		jtapi_obj.setTsapiTrunk(trunk);

		return JtapiNetworkProgressInfoFactory.promoteNetworkProgressInfo(
				provider, oci_csta, jtapi_obj);
	}

	static NetworkProgressInfo promoteV7NetworkProgressInfo(
			final TSProviderImpl provider,
			final LucentV7NetworkProgressInfo oci_csta,
			final V7NetworkProgressInfo obj) {
		if (oci_csta == null)
			return null;

		final V7DeviceHistoryEntry[] deviceHistory = TsapiPromoter
				.promoteDeviceHistory(oci_csta.getDeviceHistory());

		if (obj == null && deviceHistory == null)
			return JtapiNetworkProgressInfoFactory
					.promoteV5NetworkProgressInfo(provider, oci_csta, null);
		V7NetworkProgressInfo jtapi_obj;
		if (obj == null)
			jtapi_obj = new V7NetworkProgressInfo();
		else
			jtapi_obj = obj;

		jtapi_obj.setDeviceHistory(deviceHistory);

		return JtapiNetworkProgressInfoFactory.promoteV5NetworkProgressInfo(
				provider, oci_csta, jtapi_obj);
	}
}
