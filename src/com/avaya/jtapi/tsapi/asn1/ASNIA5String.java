package com.avaya.jtapi.tsapi.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.log4j.Logger;

public abstract class ASNIA5String extends ASN1 {
	private static Logger log = Logger.getLogger(ASNIA5String.class);

	public static final String decode(InputStream in) {
		byte[] buf;
		try {
			if (in.read() != 22) {
				throw new ASN1Exception("Decoder: expected IA5String tag");
			}
			int length = decodeLength(in);

			if (length == 0)
				return null;

			buf = new byte[length];
			int count = in.read(buf, 0, length);
			if (count != length)
				log.error("expected " + length + "bytes but read " + count
						+ "bytes");
		} catch (IOException e) {
			throw new ASN1Exception("Decoder: IA5String got unexpected EOF");
		}
		return new String(buf);
	}

	public static final void encode(String str, OutputStream out) {
		try {
			out.write(22);
			if (str == null) {
				encodeLength(out, 0);
			} else {
				int length = str.length();

				encodeLength(out, length);
				byte[] buf = new byte[length];
				buf = str.getBytes();
				out.write(buf);
			}
		} catch (IOException e) {
			throw new ASN1Exception(
					"Encoder: IA5String got unexpected IO error");
		}
	}

	public static final Collection<String> print(String str, String name,
			String indent) {
		Collection<String> lines = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(indent);
		if (name != null) {
			buffer.append(name + " ");
		}
		if (str == null) {
			lines.add(buffer.toString() + "<null>");
		} else {
			lines.add(buffer.toString() + "\"" + str + "\"");
		}
		return lines;
	}
}