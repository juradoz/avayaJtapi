package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.ConnectionID;
import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
import com.avaya.jtapi.tsapi.NetworkProgressInfo;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiPrivate;
import com.avaya.jtapi.tsapi.TsapiTrunk;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
import com.avaya.jtapi.tsapi.csta1.CSTACallOriginatorInfo;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.LucentChargeAdvice;
import com.avaya.jtapi.tsapi.csta1.LucentConferencedEvent;
import com.avaya.jtapi.tsapi.csta1.LucentConnectionClearedEvent;
import com.avaya.jtapi.tsapi.csta1.LucentDeliveredEvent;
import com.avaya.jtapi.tsapi.csta1.LucentDeviceHistoryEntry;
import com.avaya.jtapi.tsapi.csta1.LucentEstablishedEvent;
import com.avaya.jtapi.tsapi.csta1.LucentLookaheadInfo;
import com.avaya.jtapi.tsapi.csta1.LucentNetworkProgressInfo;
import com.avaya.jtapi.tsapi.csta1.LucentOriginalCallInfo;
import com.avaya.jtapi.tsapi.csta1.LucentTransferredEvent;
import com.avaya.jtapi.tsapi.csta1.LucentUserEnteredCode;
import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
import com.avaya.jtapi.tsapi.csta1.LucentV5ConferencedEvent;
import com.avaya.jtapi.tsapi.csta1.LucentV5DeliveredEvent;
import com.avaya.jtapi.tsapi.csta1.LucentV5EstablishedEvent;
import com.avaya.jtapi.tsapi.csta1.LucentV5TransferredEvent;
import com.avaya.jtapi.tsapi.impl.LucentACDAddressImpl;
import com.avaya.jtapi.tsapi.impl.LucentACDManagerAddressImpl;
import com.avaya.jtapi.tsapi.impl.LucentCallImpl;
import com.avaya.jtapi.tsapi.impl.LucentTerminalImpl;
import com.avaya.jtapi.tsapi.impl.LucentV7CallImpl;
import com.avaya.jtapi.tsapi.impl.TsapiAddress;
import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
import com.avaya.jtapi.tsapi.impl.TsapiTrunkImpl;
import com.avaya.jtapi.tsapi.impl.beans.LucentChargeAdviceEventImpl;
import com.avaya.jtapi.tsapi.impl.beans.V7DeviceHistoryEntryImpl;
import com.avaya.jtapi.tsapi.impl.events.call.PrivateDataParams;

public final class TsapiPromoter {
	public static CSTAPrivate demoteTsapiPrivate(TsapiPrivate jtapi_obj) {
		return new CSTAPrivate(jtapi_obj.vendor, jtapi_obj.data,
				jtapi_obj.tsType);
	}

	public static LucentUserToUserInfo demoteUserToUserInfo(UserToUserInfo uui) {
		return JtapiUserToUserInfoFactory.createUserToUserInfo(uui);
	}

	static LucentACDAddressImpl promoteACDAddress(TSProviderImpl provider,
			String split) {
		if (split == null) {
			return null;
		}
		try {
			return new LucentACDAddressImpl(provider, split);
		} catch (TsapiInvalidArgumentException e) {
		}
		return null;
	}

	static LucentChargeAdviceEvent promoteChargeAdvice(TSProviderImpl provider,
			LucentChargeAdvice csta_obj) {
		CSTAConnectionID connection_asn = csta_obj.getConnection_asn();
		LucentV7CallImpl call = null;
		TsapiAddress calledDevice = null;
		String calledDevice_asn = csta_obj.getCalledDevice_asn();
		String chargingDevice_asn = csta_obj.getChargingDevice_asn();
		TsapiAddress chargingDevice = null;
		TsapiTrunk trunk = null;

		if (connection_asn != null) {
			call = new LucentV7CallImpl(provider, connection_asn);
		}

		if (calledDevice_asn != null) {
			TSDevice device = provider.createDevice(calledDevice_asn);
			calledDevice = (TsapiAddress) TsapiCreateObject.getTsapiObject(
					device, true);
		}
		if (chargingDevice_asn != null) {
			TSDevice device = provider.createDevice(chargingDevice_asn);
			chargingDevice = (TsapiAddress) TsapiCreateObject.getTsapiObject(
					device, true);
		}

		trunk = promoteTrunk(provider, csta_obj.getTrunkGroup(), csta_obj
				.getTrunkMember(), 2);

		return new LucentChargeAdviceEventImpl(csta_obj.getChargeType(),
				csta_obj.getCharge(), call, calledDevice, chargingDevice,
				csta_obj.getChargeError(), trunk);
	}

	static LucentCallImpl promoteConnection(TSProviderImpl provider,
			CSTAConnectionID connectionID) {
		if (connectionID == null) {
			return null;
		}
		return new LucentCallImpl(provider, connectionID);
	}

	static V7DeviceHistoryEntry[] promoteDeviceHistory(
			LucentDeviceHistoryEntry[] array) {
		if ((array == null) || (array.length == 0)) {
			return null;
		}
		V7DeviceHistoryEntry[] local = new V7DeviceHistoryEntry[array.length];

		for (int i = 0; i < array.length; ++i) {
			CSTAConnectionID cid = array[i].getOldConnectionID();
			local[i] = new V7DeviceHistoryEntryImpl(array[i].getOldDeviceID(),
					array[i].getEventCause(), new ConnectionID(cid.getCallID(),
							cid.getDeviceID(), (short) cid.getDevIDType()));
		}

		return local;
	}

	static TsapiAddress promoteDeviceIDToAddress(TSProviderImpl provider,
			CSTAExtendedDeviceID deviceID) {
		TSDevice device = provider.createDevice(deviceID);
		return (TsapiAddress) TsapiCreateObject.getTsapiObject(device, true);
	}

	static TSDevice promoteExtendedDeviceIDToTSDevice(TSProviderImpl provider,
			CSTAExtendedDeviceID id) {
		if (id == null) {
			return null;
		}

		return provider.createDevice(id);
	}

	static LookaheadInfo promoteLookaheadInfo(LucentLookaheadInfo csta_obj) {
		return JtapiLookaheadInfoFactory.createLookaheadInfo(csta_obj);
	}

	private static PrivateDataParams promoteLucentConferencedEvent(
			TSProviderImpl provider, LucentConferencedEvent csta_obj) {
		PrivateDataParams params = new PrivateDataParams();
		params.setDistributingDevice(promoteDeviceIDToAddress(provider,
				csta_obj.getDistributingDevice_asn()));

		params.setOriginalCallInfo(promoteOriginalCallInfo(provider, csta_obj
				.getOriginalCallInfo()));

		if (csta_obj instanceof LucentV5ConferencedEvent) {
			params.setUcid(((LucentV5ConferencedEvent) csta_obj).getUcid());
		}

		return params;
	}

	private static PrivateDataParams promoteLucentConnectionClearedEvent(
			LucentConnectionClearedEvent csta_obj) {
		PrivateDataParams params = new PrivateDataParams();
		params.setUserToUserInfo(promoteUserToUserInfo(csta_obj.getUserInfo()));
		return params;
	}

	private static PrivateDataParams promoteLucentDeliveredEvent(
			TSProviderImpl provider, LucentDeliveredEvent csta_obj) {
		PrivateDataParams params = new PrivateDataParams();

		params.setDistributingDevice(promoteDeviceIDToAddress(provider,
				csta_obj.getDistributingDevice_asn()));

		params.setLookaheadInfo(promoteLookaheadInfo(csta_obj
				.getLookaheadInfo()));
		params.setOriginalCallInfo(promoteOriginalCallInfo(provider, csta_obj
				.getOriginalCallInfo()));

		params.setReason(csta_obj.getReason());
		params.setSplit(promoteACDAddress(provider, csta_obj.getSplit_asn()));
		params.setTrunk(promoteTrunk(provider, csta_obj.getTrunkGroup(),
				csta_obj.getTrunkMember()));
		params.setUserEnteredCode(promoteUserEnteredCode(provider, csta_obj
				.getUserEnteredCode()));
		params.setUserToUserInfo(promoteUserToUserInfo(csta_obj.getUserInfo()));

		if (csta_obj instanceof LucentV5DeliveredEvent) {
			LucentV5DeliveredEvent v5Obj = (LucentV5DeliveredEvent) csta_obj;

			params.setUcid(v5Obj.getUcid());
			params.setFlexibleBilling(v5Obj.isFlexibleBilling());

			CSTACallOriginatorInfo info = v5Obj.getCallOriginatorInfo();
			if (info != null) {
				params.setHasCallOriginatorType(true);
				params.setCallOriginatorType(v5Obj.getCallOriginatorInfo()
						.getCallOriginatorType());
			}
		}
		return params;
	}

	private static PrivateDataParams promoteLucentEstablishedEvent(
			TSProviderImpl provider, LucentEstablishedEvent csta_obj) {
		PrivateDataParams params = new PrivateDataParams();
		params.setDistributingDevice(promoteDeviceIDToAddress(provider,
				csta_obj.getDistributingDevice_asn()));

		params.setLookaheadInfo(promoteLookaheadInfo(csta_obj
				.getLookaheadInfo()));
		params.setOriginalCallInfo(promoteOriginalCallInfo(provider, csta_obj
				.getOriginalCallInfo()));

		params.setReason(csta_obj.getReason());
		params.setSplit(promoteACDAddress(provider, csta_obj.getSplit_asn()));
		params.setTrunk(promoteTrunk(provider, csta_obj.getTrunkGroup(),
				csta_obj.getTrunkMember()));
		params.setUserEnteredCode(promoteUserEnteredCode(provider, csta_obj
				.getUserEnteredCode()));
		params.setUserToUserInfo(promoteUserToUserInfo(csta_obj.getUserInfo()));

		if (csta_obj instanceof LucentV5EstablishedEvent) {
			LucentV5EstablishedEvent v5Obj = (LucentV5EstablishedEvent) csta_obj;

			params.setUcid(v5Obj.getUcid());
			params.setFlexibleBilling(v5Obj.isFlexibleBilling());

			CSTACallOriginatorInfo info = v5Obj.getCallOriginatorInfo();
			if (info != null) {
				params.setHasCallOriginatorType(true);
				params.setCallOriginatorType(v5Obj.getCallOriginatorInfo()
						.getCallOriginatorType());
			}
		}
		return params;
	}

	private static PrivateDataParams promoteLucentTransferredEvent(
			TSProviderImpl provider, LucentTransferredEvent csta_obj) {
		PrivateDataParams params = new PrivateDataParams();
		params.setDistributingDevice(promoteDeviceIDToAddress(provider,
				csta_obj.getDistributingDevice_asn()));

		params.setOriginalCallInfo(promoteOriginalCallInfo(provider, csta_obj
				.getOriginalCallInfo()));

		if (csta_obj instanceof LucentV5TransferredEvent) {
			params.setUcid(((LucentV5TransferredEvent) csta_obj).getUcid());
		}

		return params;
	}

	static NetworkProgressInfo promoteNetworkProgressInfo(
			TSProviderImpl provider, LucentNetworkProgressInfo csta_obj) {
		return JtapiNetworkProgressInfoFactory.createNetworkProgressInfo(
				provider, csta_obj);
	}

	static OriginalCallInfo promoteOriginalCallInfo(TSProviderImpl provider,
			LucentOriginalCallInfo oci_csta) {
		return JtapiOriginalCallInfoFactory.createOriginalCallInfo(provider,
				oci_csta);
	}

	public static Object promotePrivateEvent(TSProviderImpl provider,
			Object csta_obj) {
		if (csta_obj instanceof LucentChargeAdvice) {
			return promoteChargeAdvice(provider, (LucentChargeAdvice) csta_obj);
		}
		if (csta_obj instanceof LucentNetworkProgressInfo) {
			return promoteNetworkProgressInfo(provider,
					(LucentNetworkProgressInfo) csta_obj);
		}
		if (csta_obj instanceof CSTAPrivate) {
			return promoteTsapiPrivate((CSTAPrivate) csta_obj);
		}
		if (csta_obj instanceof LucentConferencedEvent) {
			return promoteLucentConferencedEvent(provider,
					(LucentConferencedEvent) csta_obj);
		}

		if (csta_obj instanceof LucentConnectionClearedEvent) {
			return promoteLucentConnectionClearedEvent((LucentConnectionClearedEvent) csta_obj);
		}

		if (csta_obj instanceof LucentDeliveredEvent) {
			return promoteLucentDeliveredEvent(provider,
					(LucentDeliveredEvent) csta_obj);
		}

		if (csta_obj instanceof LucentEstablishedEvent) {
			return promoteLucentEstablishedEvent(provider,
					(LucentEstablishedEvent) csta_obj);
		}

		if (csta_obj instanceof LucentTransferredEvent) {
			return promoteLucentTransferredEvent(provider,
					(LucentTransferredEvent) csta_obj);
		}

		return csta_obj;
	}

	static LucentTerminalImpl promoteTerminal(TSProviderImpl provider,
			String terminalID) {
		if (terminalID == null) {
			return null;
		}
		try {
			return new LucentTerminalImpl(provider, terminalID);
		} catch (TsapiInvalidArgumentException e) {
		}
		return null;
	}

	static TsapiTrunkImpl promoteTrunk(TSProviderImpl provider,
			String trunkGroup, String trunkMember) {
		return promoteTrunk(provider, trunkGroup, trunkMember, 1);
	}

	static TsapiTrunkImpl promoteTrunk(TSProviderImpl provider,
			String trunkGroup, String trunkMember, int type) {
		String trkName = TsapiTrunk.makeTrunkName(trunkGroup, trunkMember);
		if (trkName != null) {
			TSTrunk tsTrunk = provider.createTrunk(trkName, type);
			if (tsTrunk != null) {
				tsTrunk.setGroupName(trunkGroup);
				tsTrunk.setMemberName(trunkMember);

				return (TsapiTrunkImpl) TsapiCreateObject.getTsapiObject(
						tsTrunk, false);
			}
		}
		return null;
	}

	public static TsapiPrivate promoteTsapiPrivate(CSTAPrivate csta_obj) {
		return new TsapiPrivate(csta_obj.vendor, csta_obj.data, csta_obj.tsType);
	}

	static UserEnteredCode promoteUserEnteredCode(TSProviderImpl provider,
			LucentUserEnteredCode csta_obj) {
		if (csta_obj != null) {
			UserEnteredCode uec = new UserEnteredCode();

			uec.setData(csta_obj.getDigits());
			uec.setIndicator(csta_obj.getIndicator());
			uec.setType(csta_obj.getType());
			try {
				if (csta_obj.getCollectVDN_asn() != null) {
					uec.setCollectVDN(new LucentACDManagerAddressImpl(provider,
							csta_obj.getCollectVDN_asn()));
				}
			} catch (TsapiInvalidArgumentException e) {
			}
			return uec;
		}
		return null;
	}

	static UserToUserInfo promoteUserToUserInfo(LucentUserToUserInfo csta_obj) {
		return JtapiUserToUserInfoFactory.createUserToUserInfo(csta_obj);
	}
}

