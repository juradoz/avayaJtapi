package com.avaya.jtapi.tsapi.tsapiInterface;

import java.util.Vector;

public class TsapiFactory {
	private static final String OVERRIDE_IMPL = "tsapi_impl_class_name";

	public static Tsapi getTsapi(String tlink, String login, String passwd,
			Vector<TsapiVendor> vendors, TsapiUnsolicitedHandler handler) {
		Tsapi tsapi = null;
		String className = null;

		if ((vendors != null) && (!vendors.isEmpty())
				&& (vendors.get(0) instanceof TsapiVendor)) {
			TsapiVendor vendor = vendors.get(0);

			if (vendor.name.equals("tsapi_impl_class_name")) {
				className = vendor.versions;
			} else {
				className = Tsapi.class.getName();
			}
		} else {
			className = Tsapi.class.getName();
		}
		try {
			Class theClass = Class.forName(className);
			tsapi = (Tsapi) theClass.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Class not found", e);
		} catch (InstantiationException e) {
			throw new RuntimeException("Could not instantiate", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Could not access", e);
		}

		tsapi.init(tlink, login, passwd, vendors, handler);

		return tsapi;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.TsapiFactory JD-Core Version: 0.5.4
 */