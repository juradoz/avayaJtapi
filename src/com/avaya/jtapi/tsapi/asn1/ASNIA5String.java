package com.avaya.jtapi.tsapi.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public abstract class ASNIA5String extends ASN1 {
	public static final String decode(final InputStream in) {
		byte[] buf;
		try {
			if (in.read() != 22)
				throw new ASN1Exception("Decoder: expected IA5String tag");
			final int length = ASN1.decodeLength(in);

			if (length == 0)
				return null;

			buf = new byte[length];
			in.read(buf, 0, length);
		} catch (final IOException e) {
			throw new ASN1Exception("Decoder: IA5String got unexpected EOF");
		}
		return new String(buf);
	}

	public static final void encode(final String str, final OutputStream out) {
		try {
			out.write(22);
			if (str == null)
				ASN1.encodeLength(out, 0);
			else {
				final int length = str.length();

				ASN1.encodeLength(out, length);
				byte[] buf = new byte[length];
				buf = str.getBytes();
				out.write(buf);
			}
		} catch (final IOException e) {
			throw new ASN1Exception(
					"Encoder: IA5String got unexpected IO error");
		}
	}

	public static final Collection<String> print(final String str,
			final String name, final String indent) {
		final Collection<String> lines = new ArrayList<String>();
		final StringBuffer buffer = new StringBuffer();
		buffer.append(indent);
		if (name != null)
			buffer.append(name + " ");
		if (str == null)
			lines.add(buffer.toString() + "<null>");
		else
			lines.add(buffer.toString() + "\"" + str + "\"");
		return lines;
	}
}
