package com.avaya.jtapi.tsapi.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public abstract class ASNInteger extends ASN1 {
	public static final int decode(final InputStream in) {
		int value;
		try {
			if (in.read() != 2)
				throw new ASN1Exception("Decoder: expected INTEGER tag");
			value = ASN1.decodeInt(in);
		} catch (final IOException e) {
			throw new ASN1Exception("Decoder: INTEGER got unexpected EOF");
		}
		return value;
	}

	public static final void encode(final int value, final OutputStream out) {
		try {
			out.write(2);
			ASN1.encodeInt(value, out);
		} catch (final IOException e) {
			throw new ASN1Exception("Encoder: INTEGER got unexpected IO error");
		}
	}

	public static Collection<String> print(final int value, final String name,
			final String indent) {
		final Collection<String> lines = new ArrayList<String>();
		final StringBuffer buffer = new StringBuffer();
		buffer.append(indent);
		if (name != null)
			buffer.append(name + " ");
		buffer.append(value);
		lines.add(buffer.toString());
		return lines;
	}

	public static final Collection<String> print(final String str,
			final String name, final String indent) {
		final Collection<String> lines = new ArrayList<String>();
		final StringBuffer buffer = new StringBuffer();
		buffer.append(indent);
		if (name != null)
			buffer.append(name + " ");
		buffer.append(str);
		lines.add(buffer.toString());
		return lines;
	}
}
