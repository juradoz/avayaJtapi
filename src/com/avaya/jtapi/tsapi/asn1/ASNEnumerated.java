package com.avaya.jtapi.tsapi.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public abstract class ASNEnumerated extends ASN1 {
	public static final short decode(final InputStream in) {
		short value;
		try {
			if (in.read() != 10)
				throw new ASN1Exception("Decoder: expected ENUMERATED tag");
			value = (short) ASN1.decodeInt(in);
		} catch (final IOException e) {
			throw new ASN1Exception("Decoder: ENUMERATED got unexpected EOF");
		}
		return value;
	}

	public static final void encode(final short value, final OutputStream out) {
		try {
			out.write(10);
			ASN1.encodeInt(value, out);
		} catch (final IOException e) {
			throw new ASN1Exception(
					"Encoder: ENUMERATED got unexpected IO error");
		}
	}

	public static Collection<String> print(final short value, final String str,
			final String name, final String indent) {
		final Collection<String> lines = new ArrayList<String>();
		final StringBuffer buffer = new StringBuffer();
		buffer.append(indent);
		if (name != null)
			buffer.append(name + " ");
		buffer.append(value + " < " + str + " >");
		lines.add(buffer.toString());
		return lines;
	}
}
