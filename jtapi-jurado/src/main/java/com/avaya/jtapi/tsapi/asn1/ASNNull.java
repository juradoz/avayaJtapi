package com.avaya.jtapi.tsapi.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public abstract class ASNNull extends ASN1 {
	public static final void decode(InputStream in) {
		try {
			if (in.read() != 5) {
				throw new ASN1Exception("Decoder: expected NULL tag");
			}
			if (decodeLength(in) != 0) {
				throw new ASN1Exception(
						"Decoder: expected NULL to be zero length");
			}
		} catch (IOException e) {
			throw new ASN1Exception("Decoder: NULL got unexpected EOF");
		}
	}

	public static final void encode(OutputStream out) {
		try {
			out.write(5);
			encodeLength(out, 0);
		} catch (IOException e) {
			throw new ASN1Exception("Encoder: NULL got unexpected IO error");
		}
	}

	public static final Collection<String> print(String indent) {
		Collection<String> lines = new ArrayList<String>();
		lines.add(indent + "NULL");
		return lines;
	}
}