package com.avaya.jtapi.tsapi.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public abstract class ASNNull extends ASN1 {
	public static final void decode(final InputStream in) {
		try {
			if (in.read() != 5)
				throw new ASN1Exception("Decoder: expected NULL tag");
			if (ASN1.decodeLength(in) != 0)
				throw new ASN1Exception(
						"Decoder: expected NULL to be zero length");
		} catch (final IOException e) {
			throw new ASN1Exception("Decoder: NULL got unexpected EOF");
		}
	}

	public static final void encode(final OutputStream out) {
		try {
			out.write(5);
			ASN1.encodeLength(out, 0);
		} catch (final IOException e) {
			throw new ASN1Exception("Encoder: NULL got unexpected IO error");
		}
	}

	public static final Collection<String> print(final String indent) {
		final Collection<String> lines = new ArrayList<String>();
		lines.add(indent + "NULL");
		return lines;
	}
}
