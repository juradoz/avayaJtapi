package com.avaya.jtapi.tsapi.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public abstract class ASNEnumerated extends ASN1 {
	public static final short decode(InputStream in) {
		short value;
		try {
			if (in.read() != 10) {
				throw new ASN1Exception("Decoder: expected ENUMERATED tag");
			}
			value = (short) decodeInt(in);
		} catch (IOException e) {
			throw new ASN1Exception("Decoder: ENUMERATED got unexpected EOF");
		}
		return value;
	}

	public static final void encode(short value, OutputStream out) {
		try {
			out.write(10);
			encodeInt(value, out);
		} catch (IOException e) {
			throw new ASN1Exception(
					"Encoder: ENUMERATED got unexpected IO error");
		}
	}

	public static Collection<String> print(short value, String str,
			String name, String indent) {
		Collection<String> lines = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(indent);
		if (name != null) {
			buffer.append(name + " ");
		}
		buffer.append(value + " < " + str + " >");
		lines.add(buffer.toString());
		return lines;
	}
}
