package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.impl.core.TSAgent;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public final class TsapiCreateObject {
	public static Object getTsapiObject(Object _object, boolean isAddress) {
		Object retval = null;
		TsapiTrace.traceEntry(
				"getTsapiObject[Object _object, boolean isAddress]", null);
		if (_object instanceof TSProviderImpl) {
			TSProviderImpl TSProviderImpl = (TSProviderImpl) _object;
			if (TSProviderImpl.isLucentV7()) {
				retval = new LucentV7ProviderImpl(TSProviderImpl);
			} else if (TSProviderImpl.isLucentV5()) {
				retval = new LucentV5ProviderImpl(TSProviderImpl);
			} else if (TSProviderImpl.isLucent()) {
				retval = new LucentProviderImpl(TSProviderImpl);
			} else {
				retval = new TsapiProvider(TSProviderImpl);
			}
		} else if (_object instanceof TSCall) {
			TSCall tsCall = (TSCall) _object;
			if (tsCall.getTSProviderImpl().isLucentV7()) {
				retval = new LucentV7CallImpl(tsCall);
			} else if (tsCall.getTSProviderImpl().isLucentV5()) {
				retval = new LucentV5CallImpl(tsCall);
			} else if (tsCall.getTSProviderImpl().isLucent()) {
				retval = new LucentCallEx2Impl(tsCall);
			} else {
				retval = new TsapiCall(tsCall);
			}
		} else if (_object instanceof TSConnection) {
			TSConnection tsConn = (TSConnection) _object;
			if (tsConn.getTSProviderImpl().isLucentV6()) {
				if (isAddress) {
					if (tsConn.getTSDevice().getDeviceType() == 1) {
						retval = new LucentV5ACDManagerConnectionImpl(tsConn);
					} else if (tsConn.getTSDevice().getDeviceType() == 2) {
						retval = new LucentV5ACDConnectionImpl(tsConn);
					} else {
						retval = new LucentV6ConnectionImpl(tsConn);
					}
				} else {
					retval = new LucentV5TerminalConnectionExImpl(tsConn);
				}
			} else if (tsConn.getTSProviderImpl().isLucentV5()) {
				if (isAddress) {
					if (tsConn.getTSDevice().getDeviceType() == 1) {
						retval = new LucentV5ACDManagerConnectionImpl(tsConn);
					} else if (tsConn.getTSDevice().getDeviceType() == 2) {
						retval = new LucentV5ACDConnectionImpl(tsConn);
					} else {
						retval = new LucentV5ConnectionImpl(tsConn);
					}
				} else {
					retval = new LucentV5TerminalConnectionExImpl(tsConn);
				}
			} else if (tsConn.getTSProviderImpl().isLucent()) {
				if (isAddress) {
					if (tsConn.getTSDevice().getDeviceType() == 1) {
						retval = new LucentACDManagerConnectionImpl(tsConn);
					} else if (tsConn.getTSDevice().getDeviceType() == 2) {
						retval = new LucentACDConnectionImpl(tsConn);
					} else {
						retval = new LucentConnectionImpl(tsConn);
					}
				} else {
					retval = new LucentTerminalConnectionImpl(tsConn);
				}

			} else if (isAddress) {
				if (tsConn.getTSDevice().getDeviceType() == 1) {
					retval = new TsapiACDManagerConnection(tsConn);
				} else if (tsConn.getTSDevice().getDeviceType() == 2) {
					retval = new TsapiACDConnection(tsConn);
				} else {
					retval = new TsapiConnection(tsConn);
				}
			} else {
				retval = new TsapiTerminalConnection(tsConn);
			}

		} else if (_object instanceof TSDevice) {
			TSDevice tsDevice = (TSDevice) _object;
			if (tsDevice.getTSProviderImpl().isLucentV7()) {
				if (isAddress) {
					if (tsDevice.getDeviceType() == 1) {
						if (tsDevice.getTSProviderImpl()
								.getMonitorCallsViaDevice() == true) {
							retval = new LucentV7ACDManagerAddressImpl(tsDevice);
						} else {
							retval = new LucentACDManagerAddressImpl(tsDevice);
						}
					} else if (tsDevice.getDeviceType() == 2) {
						retval = new LucentACDAddressImpl(tsDevice);
					} else {
						retval = new LucentAddressImpl(tsDevice);
					}

				} else if (tsDevice.isTerminal()) {
					retval = new LucentV5TerminalExImpl(tsDevice);
				} else {
					retval = null;
				}
			} else if (tsDevice.getTSProviderImpl().isLucentV5()) {
				if (isAddress) {
					if (tsDevice.getDeviceType() == 1) {
						retval = new LucentACDManagerAddressImpl(tsDevice);
					} else if (tsDevice.getDeviceType() == 2) {
						retval = new LucentACDAddressImpl(tsDevice);
					} else {
						retval = new LucentAddressImpl(tsDevice);
					}

				} else if (tsDevice.isTerminal()) {
					retval = new LucentV5TerminalExImpl(tsDevice);
				} else {
					retval = null;
				}
			} else if (tsDevice.getTSProviderImpl().isLucent()) {
				if (isAddress) {
					if (tsDevice.getDeviceType() == 1) {
						retval = new LucentACDManagerAddressImpl(tsDevice);
					} else if (tsDevice.getDeviceType() == 2) {
						retval = new LucentACDAddressImpl(tsDevice);
					} else {
						retval = new LucentAddressImpl(tsDevice);
					}

				} else if (tsDevice.isTerminal()) {
					retval = new LucentTerminalImpl(tsDevice);
				} else {
					retval = null;
				}

			} else if (isAddress) {
				if (tsDevice.getDeviceType() == 1) {
					retval = new TsapiACDManagerAddress(tsDevice);
				} else if (tsDevice.getDeviceType() == 2) {
					retval = new TsapiACDAddress(tsDevice);
				} else {
					retval = new TsapiAddress(tsDevice);
				}

			} else if (tsDevice.isTerminal()) {
				retval = new TsapiTerminal(tsDevice);
			} else {
				retval = null;
			}

		} else if (_object instanceof TSAgent) {
			TSAgent tsAgent = (TSAgent) _object;
			if (tsAgent.getTSProviderImpl().isLucentV7()) {
				retval = new LucentV7AgentImpl(tsAgent);
			} else if (tsAgent.getTSProviderImpl().isLucentV6()) {
				retval = new LucentV6AgentImpl(tsAgent);
			} else if (tsAgent.getTSProviderImpl().isLucent()) {
				retval = new LucentAgentImpl(tsAgent);
			} else {
				retval = new TsapiAgent(tsAgent);
			}
		} else if (_object instanceof TSRouteSession) {
			TSRouteSession tsSession = (TSRouteSession) _object;
			if (tsSession.getTSProviderImpl().isLucentV7()) {
				retval = new LucentV7RouteSessionImpl(tsSession);
			} else if (tsSession.getTSProviderImpl().isLucentV5()) {
				retval = new LucentV5RouteSessionImpl(tsSession);
			} else if (tsSession.getTSProviderImpl().isLucent()) {
				retval = new LucentRouteSessionImpl(tsSession);
			} else {
				retval = new TsapiRouteSession(tsSession);
			}
		} else if (_object instanceof TSTrunk) {
			TSTrunk tsTrunk = (TSTrunk) _object;
			if (tsTrunk.getTSProviderImpl().isLucentV6()) {
				retval = new LucentTrunkImpl(tsTrunk);
			} else {
				retval = new TsapiTrunkImpl(tsTrunk);
			}
		} else {
			retval = null;
		}
		TsapiTrace.traceExit(
				"getTsapiObject[Object _object, boolean isAddress]", null);
		return retval;
	}

	TsapiCreateObject() {
		TsapiTrace.traceConstruction(this, TsapiCreateObject.class);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiCreateObject.class);
	}
}

