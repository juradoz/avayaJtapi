package com.avaya.jtapi.tsapi.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public abstract class ASNBitString extends ASN1 {
	public static final int decode(final InputStream in) {
		int bits = 0;
		try {
			if (in.read() != 3)
				throw new ASN1Exception("Decoder: expected BITSTRING tag");
			final int length = ASN1.decodeLength(in) - 1;
			final int unused = in.read();
			final int numBits = length * 8 - unused;

			for (int i = 0; i < 4; ++i)
				bits = (bits << 8) + (i < length ? in.read() : 0);
			bits &= (1 << 32 - numBits) - 1 ^ 0xFFFFFFFF;
		} catch (final IOException e) {
			throw new ASN1Exception("Decoder: BITSTRING got unexpected EOF");
		}
		return bits;
	}

	public static final void encode(final int bits, final int numBits,
			final OutputStream out) {
		try {
			final int length = (numBits + 7) / 8;
			final int unused = length * 8 - numBits;

			out.write(3);
			ASN1.encodeLength(out, length + 1);
			out.write(unused);

			for (int i = 0; i < length; ++i)
				out.write(bits >>> (3 - i) * 8 & 0xFF);
		} catch (final IOException e) {
			throw new ASN1Exception(
					"Encoder: BITSTRING got unexpected IO error");
		}
	}

	public static Collection<String> print(final int bits, final String str,
			final String name, final String indent) {
		final Collection<String> lines = new ArrayList<String>();
		final StringBuffer line = new StringBuffer(indent);
		if (name != null)
			line.append(name + " ");
		line.append("0x" + Long.toString(bits & 0xFFFFFFFF, 16) + " <" + str
				+ ">");
		lines.add(line.toString());
		return lines;
	}
}
