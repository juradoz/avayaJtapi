package com.avaya.jtapi.tsapi.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public abstract class ASNOctetString extends ASN1 {
	public static final byte[] decode(InputStream in) {
		byte[] buf;
		try {
			if (in.read() != 4) {
				throw new ASN1Exception("Decoder: expected OCTETSTRING tag");
			}
			int length = decodeLength(in);

			if (length == 0) {
				return null;
			}

			buf = new byte[length];
			in.read(buf, 0, length);
		} catch (IOException e) {
			throw new ASN1Exception("Decoder: OCTETSTRING got unexpected EOF");
		}
		return buf;
	}

	public static final void encode(byte[] str, OutputStream out) {
		try {
			out.write(4);
			if (str == null) {
				encodeLength(out, 0);
			} else {
				encodeLength(out, str.length);
				out.write(str);
			}
		} catch (IOException e) {
			throw new ASN1Exception(
					"Encoder: OCTETSTRING got unexpected IO error");
		}
	}

	public static final Collection<String> print(byte[] str, String name,
			String indent) {
		Collection<String> lines = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(indent);
		if (name != null) {
			buffer.append(name + " ");
		}
		if (str == null) {
			buffer.append("<null>");
		} else {
			for (int i = 0; i < str.length; ++i) {
				buffer.append(Integer.toHexString(str[i] & 0xFF) + " ");
			}
		}
		lines.add(buffer.toString());
		return lines;
	}
}
