package com.avaya.jtapi.tsapi.tsapiInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class TsapiAlternateTlinkEntriesList {
	private static final int MAX_TLINK_ALTERNATE_ENTRIES = 16;
	private static TsapiAlternateTlinkEntriesList _instance = null;

	public static synchronized TsapiAlternateTlinkEntriesList Instance() {
		if (_instance == null) {
			_instance = new TsapiAlternateTlinkEntriesList();
		}

		return _instance;
	}

	private final List<TsapiAlternateTlinkEntry> entries;

	private TsapiAlternateTlinkEntriesList() {
		entries = new ArrayList();
	}

	public synchronized void addAlternateTlinkEntry(String propertyName,
			String valueString) throws TsapiPropertiesException {
		try {
			if (entries.size() >= 16) {
				throw new TsapiPropertiesException("Ignoring property \""
						+ propertyName
						+ "\": the maximum number of Alternate Tlink entries ("
						+ 16 + ") has already been processed.");
			}

			entries
					.add(new TsapiAlternateTlinkEntry(propertyName, valueString));
		} catch (TsapiPropertySyntaxException e) {
			throw new TsapiPropertiesException("Error processing property \""
					+ propertyName + "\": " + e.getMessage());
		}
	}

	public synchronized int getAlternateTlinkIndex(String preferredTlinkName,
			String tlinkName) {
		try {
			Iterator entryIterator = entries.iterator();

			while (entryIterator.hasNext()) {
				TsapiAlternateTlinkEntry entry = (TsapiAlternateTlinkEntry) entryIterator
						.next();
				if (entry.getPreferredTlinkName().compareToIgnoreCase(
						preferredTlinkName) == 0) {
					Iterator alternatesIterator = entry
							.getAlternateTlinkNames().iterator();

					for (int index = 0; alternatesIterator.hasNext(); ++index) {
						String alternate = (String) alternatesIterator.next();
						if (alternate.compareToIgnoreCase(tlinkName) == 0) {
							return index;
						}
					}
				}

			}

		} catch (Exception e) {
		}

		return -1;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.TsapiAlternateTlinkEntriesList JD-Core
 * Version: 0.5.4
 */