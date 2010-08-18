package com.avaya.jtapi.tsapi.tsapiInterface;

import java.util.Vector;

public class TsapiFactory {
	// private static final String OVERRIDE_IMPL = "tsapi_impl_class_name";

	@SuppressWarnings("unchecked")
	public static Tsapi getTsapi(final String tlink, final String login,
			final String passwd, final Vector<TsapiVendor> vendors,
			final TsapiUnsolicitedHandler handler) {
		Tsapi tsapi = null;
		String className = null;

		if (vendors != null && !vendors.isEmpty()
				&& vendors.get(0) instanceof TsapiVendor) {
			final TsapiVendor vendor = vendors.get(0);

			if (vendor.name.equals("tsapi_impl_class_name"))
				className = vendor.versions;
			else
				className = Tsapi.class.getName();
		} else
			className = Tsapi.class.getName();
		try {
			final Class theClass = Class.forName(className);
			tsapi = (Tsapi) theClass.newInstance();
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException("Class not found", e);
		} catch (final InstantiationException e) {
			throw new RuntimeException("Could not instantiate", e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException("Could not access", e);
		}

		tsapi.init(tlink, login, passwd, vendors, handler);

		return tsapi;
	}
}
