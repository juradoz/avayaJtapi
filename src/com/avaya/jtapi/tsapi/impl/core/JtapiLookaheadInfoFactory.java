package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.V5LookaheadInfo;
import com.avaya.jtapi.tsapi.csta1.LucentLookaheadInfo;
import com.avaya.jtapi.tsapi.csta1.LucentV5LookaheadInfo;

class JtapiLookaheadInfoFactory {
	static LookaheadInfo createLookaheadInfo(LucentLookaheadInfo csta_obj) {
		if (csta_obj == null) {
			return null;
		}
		if (csta_obj instanceof LucentV5LookaheadInfo) {
			return promoteV5LookaheadInfo((LucentV5LookaheadInfo) csta_obj,
					null);
		}

		return promoteLookaheadInfo(csta_obj, null);
	}

	static LookaheadInfo promoteLookaheadInfo(LucentLookaheadInfo csta_obj,
			LookaheadInfo obj) {
		if (csta_obj == null) {
			return null;
		}

		LookaheadInfo jtapi_obj = null;

		short type = csta_obj.getType();
		short priority = csta_obj.getPriority();
		int hours = csta_obj.getHours();
		int minutes = csta_obj.getMinutes();
		int seconds = csta_obj.getSeconds();
		String sourceVDN = csta_obj.getSourceVDN();

		if (type == -1) {
			return null;
		}

		if (obj == null) {
			jtapi_obj = new LookaheadInfo();
		} else {
			jtapi_obj = obj;
		}

		jtapi_obj.setType(type);
		jtapi_obj.setPriority(priority);
		jtapi_obj.setHours(hours);
		jtapi_obj.setMinutes(minutes);
		jtapi_obj.setSeconds(seconds);
		jtapi_obj.setSourceVDN(sourceVDN);

		return jtapi_obj;
	}

	static LookaheadInfo promoteV5LookaheadInfo(LucentV5LookaheadInfo csta_obj,
			V5LookaheadInfo obj) {
		if (csta_obj == null) {
			return null;
		}
		V5LookaheadInfo jtapi_obj;
		if (obj == null) {
			jtapi_obj = new V5LookaheadInfo();
		} else {
			jtapi_obj = obj;
		}

		return promoteLookaheadInfo(csta_obj, jtapi_obj);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.JtapiLookaheadInfoFactory JD-Core Version:
 * 0.5.4
 */