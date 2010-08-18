package com.avaya.jtapi.tsapi.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public abstract class ASNBoolean extends ASN1 {
	public static final boolean decode(final InputStream in) {
		boolean value;
		try {
			if (in.read() != 1)
				throw new ASN1Exception("Decoder: expected BOOLEAN tag");
			if (ASN1.decodeLength(in) != 1)
				throw new ASN1Exception(
						"Decoder: expected BOOLEAN to contain 1 octet");

			value = in.read() > 0;
		} catch (final IOException e) {
			throw new ASN1Exception("Decoder: BOOLEAN got unexpected EOF");
		}
		return value;
	}

	public static final void encode(final boolean value, final OutputStream out) {
		try {
			out.write(1);
			ASN1.encodeLength(out, 1);
			out.write(value ? 255 : 0);
		} catch (final IOException e) {
			throw new ASN1Exception("Encoder: BOOLEAN got unexpected IO error");
		}
	}

	public static final Collection<String> print(final boolean value,
			final String name, final String indent) {
		final Collection<String> lines = new ArrayList<String>();
		final StringBuffer line = new StringBuffer(indent);
		if (name != null)
			line.append(name + " ");
		line.append(value ? "TRUE" : "FALSE");
		lines.add(line.toString());
		return lines;
	}
}
