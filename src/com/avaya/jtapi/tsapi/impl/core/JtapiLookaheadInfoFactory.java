package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.V5LookaheadInfo;
import com.avaya.jtapi.tsapi.csta1.LucentLookaheadInfo;
import com.avaya.jtapi.tsapi.csta1.LucentV5LookaheadInfo;

class JtapiLookaheadInfoFactory {
	static LookaheadInfo createLookaheadInfo(final LucentLookaheadInfo csta_obj) {
		if (csta_obj == null)
			return null;
		if (csta_obj instanceof LucentV5LookaheadInfo)
			return JtapiLookaheadInfoFactory.promoteV5LookaheadInfo(
					(LucentV5LookaheadInfo) csta_obj, null);

		return JtapiLookaheadInfoFactory.promoteLookaheadInfo(csta_obj, null);
	}

	static LookaheadInfo promoteLookaheadInfo(
			final LucentLookaheadInfo csta_obj, final LookaheadInfo obj) {
		if (csta_obj == null)
			return null;

		LookaheadInfo jtapi_obj = null;

		final short type = csta_obj.getType();
		final short priority = csta_obj.getPriority();
		final int hours = csta_obj.getHours();
		final int minutes = csta_obj.getMinutes();
		final int seconds = csta_obj.getSeconds();
		final String sourceVDN = csta_obj.getSourceVDN();

		if (type == -1)
			return null;

		if (obj == null)
			jtapi_obj = new LookaheadInfo();
		else
			jtapi_obj = obj;

		jtapi_obj.setType(type);
		jtapi_obj.setPriority(priority);
		jtapi_obj.setHours(hours);
		jtapi_obj.setMinutes(minutes);
		jtapi_obj.setSeconds(seconds);
		jtapi_obj.setSourceVDN(sourceVDN);

		return jtapi_obj;
	}

	static LookaheadInfo promoteV5LookaheadInfo(
			final LucentV5LookaheadInfo csta_obj, final V5LookaheadInfo obj) {
		if (csta_obj == null)
			return null;
		V5LookaheadInfo jtapi_obj;
		if (obj == null)
			jtapi_obj = new V5LookaheadInfo();
		else
			jtapi_obj = obj;

		return JtapiLookaheadInfoFactory.promoteLookaheadInfo(csta_obj,
				jtapi_obj);
	}
}
