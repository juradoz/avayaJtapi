package com.avaya.jtapi.tsapi.tsapiInterface;

import java.util.ArrayList;
import java.util.List;

final class TsapiAlternateTlinkEntry {
	// private static final int MAX_TLINK_ALTERNATES_PER_ENTRY = 4;
	// private static final int ACS_MAX_SERVICEID = 48;
	private String preferredTlinkName;
	private final List<String> alternateTlinks;

	public TsapiAlternateTlinkEntry(String propertyName, String valueString)
			throws TsapiPropertiesException {
		alternateTlinks = new ArrayList<String>();

		parsePropertyName(propertyName);
		parseValueString(valueString);
	}

	public List<String> getAlternateTlinkNames() {
		return alternateTlinks;
	}

	public String getPreferredTlinkName() {
		return preferredTlinkName;
	}

	private void parsePropertyName(String propertyName)
			throws TsapiPropertiesException {
		String tlinkName;
		try {
			if (!propertyName.regionMatches(true, 0, "Alternates", 0, 10)) {
				throw new TsapiAlternateTlinkPropertyNameSyntaxException(
						"property name must begin with \"Alternates\".");
			}

			propertyName = propertyName.substring(10);

			propertyName = propertyName.trim();

			if (!propertyName.startsWith("(")) {
				throw new TsapiAlternateTlinkPropertyNameSyntaxException(
						"expected opening parenthesis after \"Alternates\".");
			}

			propertyName = propertyName.substring(1);

			int index = propertyName.indexOf(')');
			if (index == -1) {
				throw new TsapiAlternateTlinkPropertyNameSyntaxException(
						"the preferred Tlink name must be enclosed by parentheses.");
			}

			if (index < 1) {
				throw new TsapiAlternateTlinkPropertyNameSyntaxException(
						"no preferred Tlink name specified.");
			}

			tlinkName = propertyName.substring(0, index);

			tlinkName = trimTlinkName(tlinkName);

			propertyName = propertyName.substring(index);

			if (!propertyName.equals(")")) {
				throw new TsapiAlternateTlinkPropertyNameSyntaxException(
						"unexpected character(s) after closing parenthesis.");
			}
		} catch (IndexOutOfBoundsException e) {
			throw new TsapiPropertiesException("Error parsing property name");
		}

		preferredTlinkName = new String(tlinkName);
	}

	private void parseValueString(String valueString)
			throws TsapiPropertiesException {
		try {
			valueString = valueString.trim();

			if (valueString.equals("")) {
				throw new TsapiAlternateTlinkPropertyValueSyntaxException(
						"no Alternate Tlinks specified.");
			}

			String[] tokens = valueString.split(":", 5);

			int numTokens = tokens.length;

			for (int i = 0; (i < 4) && (i < numTokens); ++i) {
				String token = tokens[i];

				token = trimTlinkName(token);

				if (token.equals("")) {
					throw new TsapiAlternateTlinkPropertyValueSyntaxException(
							"zero-length token for Alternate Tlink name.");
				}

				alternateTlinks.add(token);
			}
		} catch (IllegalArgumentException e) {
			throw new TsapiPropertiesException("Error parsing property value");
		}
	}

	private String trimTlinkName(String tlinkName) {
		tlinkName = tlinkName.trim();

		if (tlinkName.length() > 48) {
			try {
				tlinkName = tlinkName.substring(0, 47);
			} catch (IndexOutOfBoundsException e) {
			}

		}

		return new String(tlinkName);
	}
}

