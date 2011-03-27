package com.avaya.jtapi.tsapi.tsapiInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class TsapiAlternateTlinkEntriesList {
	// private static final int MAX_TLINK_ALTERNATE_ENTRIES = 16;
	private static TsapiAlternateTlinkEntriesList _instance = null;

	public static synchronized TsapiAlternateTlinkEntriesList Instance() {
		if (TsapiAlternateTlinkEntriesList._instance == null)
			TsapiAlternateTlinkEntriesList._instance = new TsapiAlternateTlinkEntriesList();

		return TsapiAlternateTlinkEntriesList._instance;
	}

	private final List<TsapiAlternateTlinkEntry> entries;

	private TsapiAlternateTlinkEntriesList() {
		entries = new ArrayList<TsapiAlternateTlinkEntry>();
	}

	public synchronized void addAlternateTlinkEntry(final String propertyName,
			final String valueString) throws TsapiPropertiesException {
		try {
			if (entries.size() >= 16)
				throw new TsapiPropertiesException("Ignoring property \""
						+ propertyName
						+ "\": the maximum number of Alternate Tlink entries ("
						+ 16 + ") has already been processed.");

			entries.add(new TsapiAlternateTlinkEntry(propertyName, valueString));
		} catch (final TsapiPropertySyntaxException e) {
			throw new TsapiPropertiesException("Error processing property \""
					+ propertyName + "\": " + e.getMessage());
		}
	}

	public synchronized int getAlternateTlinkIndex(
			final String preferredTlinkName, final String tlinkName) {
		try {
			final Iterator<TsapiAlternateTlinkEntry> entryIterator = entries
					.iterator();

			while (entryIterator.hasNext()) {
				final TsapiAlternateTlinkEntry entry = entryIterator.next();
				if (entry.getPreferredTlinkName().compareToIgnoreCase(
						preferredTlinkName) == 0) {
					final Iterator<String> alternatesIterator = entry
							.getAlternateTlinkNames().iterator();

					for (int index = 0; alternatesIterator.hasNext(); ++index) {
						final String alternate = alternatesIterator.next();
						if (alternate.compareToIgnoreCase(tlinkName) == 0)
							return index;
					}
				}

			}

		} catch (final Exception e) {
		}

		return -1;
	}
}
