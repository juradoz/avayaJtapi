package com.avaya.jtapi.tsapi.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public abstract class ASNBitString extends ASN1 {
	public static final int decode(InputStream in) {
		int bits = 0;
		try {
			if (in.read() != 3) {
				throw new ASN1Exception("Decoder: expected BITSTRING tag");
			}
			int length = decodeLength(in) - 1;
			int unused = in.read();
			int numBits = length * 8 - unused;

			for (int i = 0; i < 4; ++i) {
				bits = (bits << 8) + ((i < length) ? in.read() : 0);
			}
			bits &= ((1 << 32 - numBits) - 1 ^ 0xFFFFFFFF);
		} catch (IOException e) {
			throw new ASN1Exception("Decoder: BITSTRING got unexpected EOF");
		}
		return bits;
	}

	public static final void encode(int bits, int numBits, OutputStream out) {
		try {
			int length = (numBits + 7) / 8;
			int unused = length * 8 - numBits;

			out.write(3);
			encodeLength(out, length + 1);
			out.write(unused);

			for (int i = 0; i < length; ++i) {
				out.write(bits >>> (3 - i) * 8 & 0xFF);
			}
		} catch (IOException e) {
			throw new ASN1Exception(
					"Encoder: BITSTRING got unexpected IO error");
		}
	}

	public static Collection<String> print(int bits, String str, String name,
			String indent) {
		Collection lines = new ArrayList();
		StringBuffer line = new StringBuffer(indent);
		if (name != null) {
			line.append(name + " ");
		}
		line.append("0x" + Long.toString(bits & 0xFFFFFFFF, 16) + " <" + str
				+ ">");
		lines.add(line.toString());
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.asn1.ASNBitString JD-Core Version: 0.5.4
 */